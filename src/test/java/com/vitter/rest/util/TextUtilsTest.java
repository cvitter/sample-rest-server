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
    //count letters unit tests
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
    // count words unittests
    @Test
    public void countWordsTest() {
        int result = tu.countWords("hello this is a sentence");
        assertEquals(5, result);
    }

    public void countWordsEmpty() {
        int result = tu.countWords("");
        assertEquals(0, result);
    }
    @Test
    public void countWordsWithSpaces() {
        int result = tu.countWords("   test");
        assertEquals(1, result);

        result = tu.countWords("test     ");
        assertEquals(1, result);

        result = tu.countWords("   test1   test2    ");
        assertEquals(2, result);
    }

    //most repeated word unittests
    // example of a failing unit test
    @Test
    public void mostRepeatedWordTest() {
        String result = tu.mostRepeatedWord("This test string is for test purposes."); //expected return: test
        assertEquals("test", result); // should ignore uppercase and lowercase
        // result of this test: add uppercase and lowercase support
    }
}
