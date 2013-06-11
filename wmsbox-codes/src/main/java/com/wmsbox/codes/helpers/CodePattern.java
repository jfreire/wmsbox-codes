package com.wmsbox.codes.helpers;

import java.util.ArrayList;
import java.util.List;

import com.wmsbox.codes.metainfo.FieldInfo;

/**
 * AAA'A'{ZZ}II
 * Entre comillas literal
 * La \ escapa
 *
 * String: No acepta llaves. Chequea que el tamaño del campo sea mayor que size.??
 *
 * Numerico: Entre llaves es conversor y chequea max min si no hay conversion
 *
 * @author jfreire
 */
public class CodePattern {

	public static CodePattern build(String patternText, FieldInfo[] fields) {
		final int length = patternText.length();
		final StringBuffer pattern = new StringBuffer();
		final List<Character> fixCharsList = new ArrayList<Character>();
		final int[] fieldEndIndexes = new int[fields.length];
		final int[] indexes = new int[fields.length]; //El indice del campo
		final int[] sizes = new int[fields.length];

		Character lastFieldName = null;
		boolean literal = false;
		int size = 0;
		int field = 0;

		for (int i = 0; i < length; i++) {
			final char ch = patternText.charAt(i);

			if (ch == '\'') {
				literal = !literal;

				if (literal && lastFieldName != null) {
					fieldEndIndexes[field] = pattern.length() - 1;
					sizes[field++] = size;
					size = 0;
					lastFieldName = null;
				}
			} else {
				if (literal) {
					fixCharsList.add(ch);
					pattern.append(ch);
				} else {
					if (lastFieldName == null || lastFieldName != ch) {
						if (lastFieldName != null) {
							fieldEndIndexes[field] = pattern.length() - 1;
							sizes[field++] = size;
							size = 0;
							lastFieldName = null;
						}

						Integer index = null;

						for (int j = 0; index == null && j < fields.length; j++) {
							if (fields[j].getName().equals(ch)) {
								index = j;
							}
						}

						if (index == null) {
							throw new IllegalArgumentException("Field not found " + ch);
						}

						indexes[field] = index;
						lastFieldName = ch;
					}

					fixCharsList.add(null);
					pattern.append(ch);
					size++;
				}
			}
		}

		if (lastFieldName != null) {
			fieldEndIndexes[field] = pattern.length() - 1;
			sizes[field++] = size;
			size = 0;
		}

		char[] fixChars = new char[fixCharsList.size()];

		for (int i = 0; i < fixCharsList.size(); i++) {
			Character ch = fixCharsList.get(i);
			fixChars[i] = ch == null ? 0 : ch;
		}

		return new CodePattern(pattern.toString().toCharArray(), fixChars, sizes,
				fieldEndIndexes, indexes);
	}

	public final int length;
	protected final int fields;
	public final char[] fixChars;
	public final int[] fieldEndIndexes;
	public final int[] sizes;
	public final int[] indexes;
	private final char[] pattern;

	public CodePattern(char[] pattern, char[] fixChars, int[] sizes, int[] fieldEndIndexes,
			int[] indexes) {
		this.fields = sizes.length;
		this.pattern = pattern;
		this.fixChars = fixChars;
		this.sizes = sizes;
		this.fieldEndIndexes = fieldEndIndexes;
		this.length = this.pattern.length;
		this.indexes = indexes;
	}

	public char[] chars() {
		char[] chars = new char[this.length];
		System.arraycopy(this.pattern, 0, chars, 0, this.length);

		return chars;
	}

	public String print(Object[] values) {
		char[] chars = chars();

		for (int i = this.fields - 1;  i >= 0; i--) {
			final Object fieldValue = values[i];
			int stIndex = this.fieldEndIndexes[i];

			if (fieldValue instanceof String) {
				String value = (String) fieldValue;
				int size = value.length();

				for (int j = this.sizes[i] - 1; j >= 0; j--) {
					chars[stIndex--] = j < size ? value.charAt(j) : ' ';
				}
			} else {
				long currentFieldValue = ((Number) fieldValue).longValue();

				for (int j = this.sizes[i] - 1; j >= 0; j--) {
					long newFieldValue = currentFieldValue / 10;
					chars[stIndex--] = (char) ('0' + (currentFieldValue - (newFieldValue * 10)));
					currentFieldValue = newFieldValue;
				}
			}
		}

		return new String(chars);
	}
}
