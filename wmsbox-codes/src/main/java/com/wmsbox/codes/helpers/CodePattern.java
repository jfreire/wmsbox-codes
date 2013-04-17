package com.wmsbox.codes.helpers;

import java.util.concurrent.atomic.AtomicBoolean;

public class CodePattern {

	private final char[] pattern;
	private final int[] fieldsIndex;
	private final int[] sizes;
	private final int length;
	private final AtomicBoolean needClone = new AtomicBoolean();

	public CodePattern() {
		this.pattern = null;
		this.fieldsIndex = null;
		this.length = -1;
		this.sizes = null;
	}

	public char[] getPattern() {
		if (this.needClone.compareAndSet(false, true)) {
			return pattern;
		}

		char[] chars = new char[this.length];
		System.arraycopy(this.pattern, 0, chars, 0, this.length);

		return chars;
	}

	public String result(char[] chars) {
		if (chars == pattern) {
			this.needClone.set(false);
		}

		return new String(chars);
	}

	public void write(int index, int value, char[] chars) {
		int fieldIndex = this.fieldsIndex[index];

		for (int j = this.sizes[index] - 1; j >= 0; j--) {
			final int newFieldValue = value / 10;
			chars[fieldIndex--] = (char) ('0' + (value - (newFieldValue * 10)));
			value = newFieldValue;
		}
	}

	public void write(int index, String value, char[] chars) {
		int fieldIndex = this.fieldsIndex[index];

		for (int j = this.sizes[index] - 1; j >= 0; j--) {
			chars[fieldIndex--] = value.charAt(j);
		}
	}
}
