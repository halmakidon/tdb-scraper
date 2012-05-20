package sekky.tdb.scraper;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 帝国データバンクの検索を行う
 *
 * @author sekky
 *
 */
public class TdbScraper {

	private static Logger LOG = Logger.getLogger(TdbScraper.class.getName());

	/** デフォルトのタイムアウト値 */
	public static final int DEFAULT_TIMEOUT = 1000;

	/** デフォルトの接続施行回数(初回アクセス含む) */
	public static final int DEFAULT_RETRY_CNT = 2;

	private int timeout = DEFAULT_TIMEOUT;
	private int retryCnt = DEFAULT_RETRY_CNT;

	public TdbScraper() {}

	public TdbScraper(int timeout, int retryCnt) {
		this.timeout = timeout;
		this.retryCnt = retryCnt;
	}

	public Result search(String word, int page) throws IOException {

		String encodedWord = null;
		try {
			encodedWord = URLEncoder.encode(word, "Windows-31J");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}

		String requestURI = "http://www.tdb.co.jp/service/u/1005.jsp"
				+ "?page_count=" + page + "&companyName="
				+ encodedWord
				+ "&companyNameAccord=1&address_sikugun=&freeWord=";

		LOG.fine(requestURI);

		return searchCompany(requestURI, word, page);
	}

	protected Document getDocument(String url) throws IOException {
		Document doc = null;
		IOException exception = null;
		for (int i = 0; i < retryCnt; i++) {
			try {
				doc = Jsoup.connect(url).timeout(timeout).get();
			} catch (IOException e) {
				LOG.fine(e.toString());
				exception = e;
				continue;
			}
			// 例外が発生しない場合は即ブレイクする
			break;
		}

		if (doc == null) {
			throw exception;
		}
		return doc;
	}

	protected Result searchCompany(String url, String word, int page) throws IOException {

		Document doc = getDocument(url);

		// 検索が成功したかを検索件数ヒット領域から判定する
		Elements hitArea = doc.select(".searchHit:eq(0)");
		if (hitArea.isEmpty()) {
			return new Result(0, 0, 0, new Company[0]);
		}
		// 成功した場合は各行を取得する
		Elements rows = doc.select(".searchResult tr:gt(0)");
		ArrayList<Company> companyList = new ArrayList<Company>();
		for (Element row : rows) {
			Company company = new Company(
					row.select("td:eq(0)").text(),
					row.select("p.company").text(),
					row.select("p.companyPlace").text(),
					row.select("td:eq(2)").text());
			companyList.add(company);
		}
		Company[] resultList = companyList.toArray(new Company[companyList.size()]);

		// 以降ページ数を検索数ヒット領域から取得する
		int resultSearchHit = 0;
		String hitCount = hitArea.select("div.left > span").text();
		int endPoint = hitCount.indexOf("件までの");
		if (endPoint > -1) {
			resultSearchHit = Integer.parseInt(hitCount.substring(0, endPoint));
		}else{
			resultSearchHit = Integer.parseInt(hitCount);
		}

		// div.centerがない場合は1ページのみ
		int resultMaxPage = 0;
		int resultCurrentPage = 0;
		Elements divCenter = hitArea.select("div.center");
		if (!divCenter.isEmpty()) {
			String pageText = divCenter.text();
			int endIndex = pageText.indexOf("ページ中");
			int startIndex = pageText.indexOf("全") + 1;
			resultMaxPage = Integer.parseInt(pageText.substring(
					startIndex, endIndex));

			resultCurrentPage = Integer.parseInt(hitArea.select(
					"div.center select option[selected]").val());
		}

		return new Result(resultSearchHit, resultMaxPage, resultCurrentPage, resultList);
	}

	/**
	 * @return the timeout
	 */
	public final int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public final void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the retryCnt
	 */
	public final int getRetryCnt() {
		return retryCnt;
	}

	/**
	 * @param retryCnt the retryCnt to set
	 */
	public final void setRetryCnt(int retryCnt) {
		this.retryCnt = retryCnt;
	}
}
