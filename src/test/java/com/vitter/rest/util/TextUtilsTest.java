package com.vitter.rest.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextUtilsTest {
	
	private TextUtils tu;
	
	@Before
	public void setUp() throws Exception {
		tu = new TextUtils();
	}

	@After
	public void tearDown() throws Exception {
		tu = null;
	}

	@Test
	public void countLettersTest() {
        int result = tu.countLetters("test");
        assertEquals(4, result);
    }

    @Test
    public void countLettersEmpty() {
        int result = tu.countLetters("");
        assertEquals(0, result);
    }

    @Test
    public void countLettersWithSpaces() {
        int result = tu.countLetters("a b c");
        assertEquals(3, result);

        result = tu.countLetters("    abc");
        assertEquals(3, result);

        result = tu.countLetters("abc    ");
        assertEquals(3, result);
    }
}
