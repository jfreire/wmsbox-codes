package com.wmsbox.codes.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * {4}-{2}
 * @author jfreire
 */
public class CodePattern {

	public final int length;
	public final char[] fixChars;
	public final int[] fieldEndIndexes;
	private final char[] pattern;

	public CodePattern(char[] pattern, int[] fieldEndIndexes) {
		this.pattern = pattern;
		this.fixChars = new char[pattern.length];
		this.length = pattern.length;
		this.fieldEndIndexes = fieldEndIndexes;
	}

	public CodePattern(String pattern) {
		final StringBuilder sbPattern = new StringBuilder();
		final List<Character> fixChars = new ArrayList<Character>();
		final List<Integer> fieldEndIndexes = new ArrayList<Integer>();
		int beginField = -1;

		for (int i = 0; i < pattern.length(); i++) {
			final char ch = pattern.charAt(i);

			if (beginField != -1) {
				if (ch == '}') {
					final int size = Integer.parseInt(pattern.substring(beginField, i));

					for (int j = 0; j < size; j++) {
						sbPattern.append(' ');
						fixChars.add(null);
					}

					fieldEndIndexes.add(sbPattern.length() - 1);
					beginField = -1;
				} else if (!Character.isDigit(ch)) {
					throw new IllegalArgumentException("Invalid pattern: " + pattern);
				}
			} else {
				if (ch == '{') {
					beginField = i + 1;
				} else {
					sbPattern.append(ch);
					fixChars.add(ch);
				}
			}
		}

		this.length = sbPattern.length();
		this.pattern = sbPattern.toString().toCharArray();
		this.fixChars = new char[this.length];

		for (int i = 0; i < this.length; i++) {
			Character ch = fixChars.get(i);
			this.fixChars[i] = ch == null ? 0 : ch;
		}

		this.fieldEndIndexes = new int[fieldEndIndexes.size()];

		for (int i = 0; i < fieldEndIndexes.size(); i++) {
			this.fieldEndIndexes[i] = fieldEndIndexes.get(i);
		}
	}

	public char[] start() {
		char[] chars = new char[this.length];
		System.arraycopy(this.pattern, 0, chars, 0, this.length);

		return chars;
	}
}
