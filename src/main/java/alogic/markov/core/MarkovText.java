package alogic.markov.core;

import java.util.List;

import org.thymeleaf.util.ArrayUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import alogic.markov.core.exceptions.InvalidOrderException;

public class MarkovText {

	private int order;
	private Dictionary dictionary;
	private static final char space = ' ';
	
	public MarkovText(int order) throws InvalidOrderException {
		if (order <= 0) {
			throw new InvalidOrderException();
		}
		this.order = order;
		this.dictionary = new Dictionary(order);
	}
	
	public void addWords(String[] words) {
		if (ArrayUtils.isEmpty(words)) {
			return;
		}
		dictionary.addWords(words);
	}
	
	public void addWords(List<String> words) {
		addWords(words.toArray(new String[0]));
	}

	public String generateText(int maxWords) {
		return generateText(maxWords, null);
	}
	
	public String generateText(int maxWords, Prefix basePrefix) {
		if (maxWords <= 0) {
			return null;
		}
		Prefix prefix;
		if (basePrefix == null) {
			prefix = Prefix.createFirst(order);
		}
		else {
			prefix = new Prefix(basePrefix);
		}
		List<String> text = Lists.newArrayList();
		int words = 0;
		Suffix newWord;
		do {
			newWord = dictionary.getNewWord(prefix);
			if (newWord == null || newWord.isNonWord()) {
				break;
			}
			text.add(newWord.getWord());
			prefix.add(newWord);
			words++;
		} while (words < maxWords);
		return Joiner.on(space).join(text);
	}
	
	public Dictionary getDictionary() {
		return dictionary;
	}
}
