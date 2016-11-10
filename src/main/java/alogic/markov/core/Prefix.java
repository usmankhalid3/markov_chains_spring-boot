package alogic.markov.core;

import java.util.Iterator;

import com.google.common.collect.EvictingQueue;

public class Prefix implements Cloneable {

	private EvictingQueue<Suffix> words;

	public Prefix(int order) {
		words = EvictingQueue.create(order);
	}
	
	public Prefix(String[] prefixes) {
		words = EvictingQueue.create(prefixes.length);
		for (String prefix : prefixes) {
			words.add(new Suffix(prefix));
		}
	}
	
	public Prefix(Prefix clone) {
		words = EvictingQueue.create(clone.words.size());
		for (Suffix suffix : clone.words) {
			words.add(suffix);
		}
	}

	public EvictingQueue<Suffix> getWords() {
		return words;
	}

	public void setWords(EvictingQueue<Suffix> words) {
		this.words = words;
	}

	public void add(Suffix suffix) {
		words.add(suffix);
	}

	public String toString() {
		String result = "";
		for (Suffix suffix : words) {
			result = result + suffix.getWord() + " ";
		}
		return result.trim();
	}

	public static Prefix createFirst(int order) {
		Prefix prefix = new Prefix(order);
		for (int i = 0; i < order; i++) {
			prefix.add(Suffix.nonWord());
		}
		return prefix;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (words == null || words.isEmpty()) {
			return result = prime * result;
		}
		for (Suffix suffix : words) {
			result = prime * result + suffix.getWord().hashCode();
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prefix other = (Prefix) obj;
		if (words == null) {
			if (other.words != null) {
				return false;
			}
		} else if (words.size() != other.words.size()) {
			return false;
		} else {
			Iterator<Suffix> it1 = words.iterator();
			Iterator<Suffix> it2 = other.words.iterator();

			while (it1.hasNext() && it2.hasNext()) {
				Suffix s1 = it1.next();
				Suffix s2 = it2.next();
				if (!s1.getWord().equalsIgnoreCase(s2.getWord())) {
					return false;
				}
			}

		}
		return true;
	}
	
	public int size() {
		return words.size();
	}
}
