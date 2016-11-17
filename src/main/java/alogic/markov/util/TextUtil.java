package alogic.markov.util;

import java.util.List;

import org.thymeleaf.util.ArrayUtils;

import com.google.common.collect.Lists;

public class TextUtil {

	public static List<String> extractWords(String corpus) {
		String[] lines = corpus.split("\\r?\\n");
		if (ArrayUtils.isEmpty(lines)) {
			return Lists.newArrayList();
		}
		List<String> result = Lists.newArrayList();
		for (int i = 0; i < lines.length; i++) {
			String[] words = lines[i].split("\\s+");
			for (int j = 0; j < words.length; j++) {
				result.add(words[j]);
			}
		}
		return result;
	}
}
