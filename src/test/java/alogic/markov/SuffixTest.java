package alogic.markov;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import alogic.markov.core.Suffix;

public class SuffixTest {

	@Test
	public void testNonWord() {
		Suffix s1 = new Suffix("hello");
		Suffix s2 = new Suffix("hello", true);
		
		assertFalse(s1.isNonWord());
		assertTrue(s2.isNonWord());
	}
	
	@Test
	public void testEquality() {
		Suffix s1 = new Suffix("hello");
		Suffix s2 = new Suffix("hellO");
		Suffix s3 = new Suffix("h3llO");
		
		assertEquals(s1, s2);
		assertNotEquals(s1, s3);
	}
}
