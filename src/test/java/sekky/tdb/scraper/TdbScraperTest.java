package sekky.tdb.scraper;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TdbScraperTest {

	@Test
	public void testTdbScraper() {
		TdbScraper scraper = new TdbScraper(30000, 1000);
		assertEquals(30000, scraper.getTimeout());
		assertEquals(1000, scraper.getRetryCnt());
	}

	@Test
	public void testSearch() throws IOException {
		TdbScraper scraper = new TdbScraper();
		Result result = scraper.search("帝国", 1);

		for (Company company : result.getList()) {
			assertTrue(company.getName().contains("帝国"));
		}
	}

}
