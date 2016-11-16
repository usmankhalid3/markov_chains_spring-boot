package alogic.markov.core;

public class Suffix {

	private String word;
	private boolean isNonWord;
	
	public Suffix(String word) {
		this(word, false);
	}

	public Suffix(String word, boolean isNonWord) {
		super();
		this.word = word;
		this.isNonWord = isNonWord;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public boolean isNonWord() {
		return isNonWord;
	}

	public void setNonWord(boolean isNonWord) {
		this.isNonWord = isNonWord;
	}

	public static Suffix nonWord() {
		return new Suffix("", true);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
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
		Suffix other = (Suffix) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equalsIgnoreCase(other.word))
			return false;
		return true;
	}
	
	
}
