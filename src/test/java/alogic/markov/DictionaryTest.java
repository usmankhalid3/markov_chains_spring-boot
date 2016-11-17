package alogic.markov;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import alogic.markov.core.Dictionary;
import alogic.markov.core.Prefix;
import alogic.markov.core.Suffix;

public class DictionaryTest {

	@Test
	public void testEmptyCreation() {
		final int order = 3;
		Dictionary d = new Dictionary(order);
		Map<Prefix, Multiset<Suffix>> chains = d.getChains();
		assertEquals(chains.size(), 1);
		
		for (Prefix prefix : chains.keySet()) {
			assertEquals(prefix, Prefix.createFirst(order));
			assertEquals(0, chains.get(prefix).size());
		}
	}
	
	@Test
	public void testAdd() {
		final int order = 2;
		String[] input = "now he is gone she said he is gone for good".split("\\s+");
		Dictionary d = new Dictionary(order);
		d.addWords(input);
		
		Map<Prefix, Multiset<Suffix>> chains = d.getChains();
	
		assertTrue(chains.containsKey(Prefix.createFirst(order)));
		
		int index = order;
		int total = input.length - order + 1;
		for (int i = 0; i < total; i++) {
			String[] words = Arrays.copyOfRange(input, i, index);
			Prefix prefix = new Prefix(words);
			assertTrue(chains.containsKey(prefix));
			index++;
		}
		
		Prefix lastPrefix = new Prefix(Arrays.copyOfRange(input, input.length - order, input.length));
		assertEquals(1, chains.get(lastPrefix).elementSet().size());
	}
	
	@Test
	public void testFindRandomSuffix() {
		Map<String, Integer> input = new HashMap<String, Integer>(5);
		input.put("low", 4);
		input.put("high", 6);
		
		Multiset<Suffix> suffices = HashMultiset.create();
		for (String word : input.keySet()) {
			Integer freq = input.get(word);
			for (int i = 0; i < freq; i++) {
				suffices.add(new Suffix(word));
			}
		}
		assertEquals(input.keySet().size(), suffices.elementSet().size());
		Dictionary d = new Dictionary(2);
		Multiset<Suffix> result = HashMultiset.create();
		for (int i = 0; i < 100; i++) {
			Suffix randomSuffix = d.findRandomSuffix(suffices);
			if (randomSuffix != null) {
				result.add(randomSuffix);
			}
		}
		assertTrue(result.count(new Suffix("high")) > result.count(new Suffix("low")));
	}
}
