package test.com.yesu.extend;

import static org.junit.Assert.*;

import org.junit.Test;

import com.yesu.extend.CurseLanguageFilter;

public class CurseLanguageFilterTest {
	
	private final CurseLanguageFilter curseLanguagefilter = new CurseLanguageFilter();

	@Test
	public void isLanguageAllowed() {
		assertNotNull(curseLanguagefilter);
		assertTrue(curseLanguagefilter.isLanguageAllowed("good language"));
		assertTrue(curseLanguagefilter.isLanguageAllowed("clean language"));
		assertFalse(curseLanguagefilter.isLanguageAllowed("Bad language"));
		assertFalse(curseLanguagefilter.isLanguageAllowed("dirty language"));
		assertFalse(curseLanguagefilter.isLanguageAllowed("NeGative language"));
	}

}
