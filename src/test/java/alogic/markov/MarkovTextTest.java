package alogic.markov;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Multiset;

import alogic.markov.core.MarkovText;
import alogic.markov.core.Prefix;
import alogic.markov.core.Suffix;
import alogic.markov.core.exceptions.InvalidOrderException;

public class MarkovTextTest {

	@Test(expected=InvalidOrderException.class)
	public void testNoWords() throws InvalidOrderException {
		final int order = -1;
		new MarkovText(order);
	}

	@Test
	public void testGenerateNoPrefix() throws InvalidOrderException {
		final int order = 2;
		final int maxWords = 10;
		String[] input = "now he is gone she said he is gone for good".split("\\s+");
		MarkovText text = new MarkovText(order);
		text.addWords(input);
		String[] output = text.generateText(maxWords).split("\\s+");
		Prefix prefix = Prefix.createFirst(order);
		Map<Prefix, Multiset<Suffix>> chains = text.getDictionary().getChains();
		for (int i = 0; i < output.length; i++) {
			assertTrue(chains.containsKey(prefix));
			assertTrue(chains.get(prefix).contains(new Suffix(output[i])));
			prefix.add(new Suffix(output[i]));
		}
	}
	
	@Test
	public void testGenerateWithPrefix() throws InvalidOrderException {
		final int order = 2;
		final int maxWords = 10;
		final Prefix prefix = new Prefix(new String[]{"he", "is"});
		String[] input = "now he is gone she said he is gone for good".split("\\s+");
		MarkovText text = new MarkovText(order);
		text.addWords(input);
		String[] output = text.generateText(maxWords, prefix).split("\\s+");
		Map<Prefix, Multiset<Suffix>> chains = text.getDictionary().getChains();
		for (int i = 0; i < output.length; i++) {
			assertTrue(chains.containsKey(prefix));
			assertTrue(chains.get(prefix).contains(new Suffix(output[i])));
			prefix.add(new Suffix(output[i]));
		}
	}
	
}
