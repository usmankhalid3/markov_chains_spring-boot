package alogic.markov;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.EvictingQueue;

import alogic.markov.core.Prefix;
import alogic.markov.core.Suffix;

public class PrefixTest {

	@Test
	public void testOrderConstructor() {
		final int order = 2;
		Prefix prefix = new Prefix(order);
		EvictingQueue<Suffix> words = prefix.getWords();
		assertEquals(order, words.remainingCapacity());
		
		String[] input = {"hello", "world"};
		for (String word : input) {
			words.add(new Suffix(word));
		}
		checkSizeAndContents(prefix, input);
	}
	
	@Test
	public void testStringArrayConstructor() {
		String[] words = {"hello", "world", "this", "is", "usman"};
		Prefix prefix = new Prefix(words);
		checkSizeAndContents(prefix, words);
	}
	
	@Test
	public void testEviction() {
		final int order = 2;
		String[] input = {"hello", "world", "this", "is", "usman"};
		Prefix prefix = new Prefix(order);
		for (String word : input) {
			prefix.add(new Suffix(word));
		}
		
		EvictingQueue<Suffix> words = prefix.getWords();
		String[] containedWords = {"is", "usman"};
		for (String w1 : containedWords) {
			assertTrue(words.contains(new Suffix(w1)));
		}
		String[] evictedWords = {"hello", "world", "this"};
		for (String w2 : evictedWords) {
			assertFalse(words.contains(new Suffix(w2)));
		}		
	}
	
	@Test
	public void testEquality() {
		String[] input1 = {"good", "morning", "usman1"};
		String[] input2 = {"good", "morning", "usman!"};
		String[] input3 = {"Good", "morning", "usman1"};
		Prefix p1 = new Prefix(input1);
		Prefix p2 = new Prefix(input2);
		Prefix p3 = new Prefix(input3);
		
		assertFalse(p1.equals(p2));
		assertTrue(p1.equals(p3));
	}
	
	private void checkSizeAndContents(Prefix prefix, String[] input) {
		assertEquals(input.length, prefix.size());
		EvictingQueue<Suffix> words = prefix.getWords();
		for (String word : input) {
			assertTrue(words.contains(new Suffix(word)));
		}
	}
}
