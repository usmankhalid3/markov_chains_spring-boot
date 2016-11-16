package alogic.markov.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class Dictionary {

	private int order;
	
	private Map<Prefix, Multiset<Suffix>> chains = new HashMap<Prefix, Multiset<Suffix>>();
	
	public Dictionary(int order) {
		this.order = order;
		Multiset<Suffix> set = HashMultiset.create();
		chains.put(Prefix.createFirst(order), set);
	}
	
	private void createOrUpdate(Prefix p, Suffix suffix) {
		Multiset<Suffix> suffices;
		Prefix prefix = new Prefix(p);
		if (chains.containsKey(prefix)) {
			suffices = chains.get(prefix);
			suffices.add(suffix);
			chains.put(prefix, suffices);
		} else {
			suffices = HashMultiset.create();
			suffices.add(suffix);
			chains.put(prefix, suffices);
		}
	}
	
	public Suffix getNewWord(Prefix prefix) {
		if (chains.containsKey(prefix)) {
			Multiset<Suffix> candidates = chains.get(prefix);
			return findRandomSuffix(candidates);
		}
		return null;
	}
	
	public void addWords(String[] words) {
		if (words.length <= 0) {
			return;
		}
		Prefix prefix = Prefix.createFirst(order);
		for (int i = 0; i < words.length; i++) {
			Suffix suffix = new Suffix(words[i]);
			createOrUpdate(prefix, suffix);
			prefix.add(suffix);
		}
		createOrUpdate(prefix, Suffix.nonWord());
//		for (Prefix p : chains.keySet()) {
//			System.out.println(p.toString() + " = " + chains.get(p));
//		}
	}
	
	public Suffix findRandomSuffix(Multiset<Suffix> suffices) {
		int total = 0;
		for (Suffix suffix : suffices) {
			total += suffices.count(suffix);
		}
		int random = new Random().nextInt(total);
		int current = 0;
		for (Suffix suffix : suffices.elementSet()) {
			current += suffices.count(suffix);
			if (random < current) {
				return suffix;
			}
		}
		return null;
	}
	
	public Map<Prefix, Multiset<Suffix>> getChains() {
		return chains;
	}
}
