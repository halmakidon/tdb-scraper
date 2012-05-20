package sekky.tdb.scraper;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 帝国データバンクの検索結果
 *
 * @author sekky
 *
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 7482782076465580525L;
	private int searchHit;
	private int maxPage;
	private int currentPage;
	private Company[] list;

	public Result (int searcHit, int maxPage, int currentPage, Company[] list) {
		this.searchHit = searcHit;
		this.maxPage = maxPage;
		this.currentPage = currentPage;
		this.list = list;
	}

	/**
	 * @return the searchHit
	 */
	public int getSearchHit() {
		return searchHit;
	}

	/**
	 * @param searchHit
	 *            the searchHit to set
	 */
	protected void setSearchHit(int searchHit) {
		this.searchHit = searchHit;
	}

	/**
	 * @return the maxPage
	 */
	public int getMaxPage() {
		return maxPage;
	}

	/**
	 * @param maxPage
	 *            the maxPage to set
	 */
	protected void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            the currentPage to set
	 */
	protected void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the list
	 */
	public Company[] getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	protected void setList(Company[] list) {
		this.list = list;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchResult [searchHit=" + searchHit + ", maxPage=" + maxPage
				+ ", currentPage=" + currentPage + ", list="
				+ Arrays.toString(list) + "]";
	}
}
