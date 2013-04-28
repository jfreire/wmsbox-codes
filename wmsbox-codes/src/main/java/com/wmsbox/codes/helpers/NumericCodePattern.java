package com.wmsbox.codes.helpers;

import java.util.ArrayList;
import java.util.List;

import com.wmsbox.codes.metainfo.FieldInfo;
import com.wmsbox.codes.numeric.NumericConversor;

/**
 * AAA'A'ZZ!II
 * Entre comillas literal
 * La \ escapa
 *
 * String: No acepta llaves. Chequea que el tamaño del campo sea mayor que size.??
 *
 * Numerico: Entre llaves es conversor y chequea max min si no hay conversion
 *
 * @author jfreire
 */
public class NumericCodePattern extends CodePattern {

	public static NumericCodePattern build(String patternText, FieldInfo[] fields) {
		return build(patternText, fields, null);
	}

	public static NumericCodePattern build(String patternText, FieldInfo[] fields,
			NumericConversor conversor) {
		final int length = patternText.length();
		final StringBuffer pattern = new StringBuffer();
		final List<Character> fixCharsList = new ArrayList<Character>();
		final int[] fieldEndIndexes = new int[fields.length];
		final int[] indexes = new int[fields.length]; //El indice del campo
		final int[] sizes = new int[fields.length];
		final boolean[] needConvert = new boolean[fields.length];

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
					if (ch == '!') {
						needConvert[field] = true;
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

		return new NumericCodePattern(pattern.toString().toCharArray(), fixChars, sizes,
				fieldEndIndexes, indexes, conversor, needConvert);
	}

	private final NumericConversor conversor;
	private final boolean[] needConvert;

	public NumericCodePattern(char[] pattern, char[] fixChars, int[] sizes, int[] fieldEndIndexes,
			int[] indexes, NumericConversor conversor, boolean[] needConvert) {
		super(pattern, fixChars, sizes, fieldEndIndexes, indexes);
		this.conversor = conversor;
		this.needConvert = needConvert;
	}

	public int[] parse(String text) {
		final int length = text.length();
		final int[] split = new int[this.fields];
		int fieldSize = this.sizes[0];
		int inFieldIndex = 0;
		int fieldIndex = 0;
		int beginIndex = -1;
		int fieldValue = 0;

		for (int i = 0; i < length; i++) {
			final char ch = text.charAt(i);
			final char fixChar = this.fixChars[i];

			if (fixChar != 0) {
				if (fixChar != ch) {
					throw new IllegalArgumentException("Invalid code " + text);
				}
			} else {
				if (this.needConvert[fieldIndex]) {
					if (beginIndex == -1) {
						beginIndex = i;
					}
				} else if (ch >= '0' && ch <= '9') {
					fieldValue = fieldValue * 10 + (ch - '0');
				} else {
					throw new IllegalArgumentException("Invalid code " + text);
				}

				if (++inFieldIndex == fieldSize) {
					if (beginIndex != -1) {
						fieldValue = this.conversor.convertToInt(fieldIndex,
								text.substring(beginIndex, i + 1));
						beginIndex = -1;
					}

					split[fieldIndex++] = fieldValue;

					if (fieldIndex < this.fields) {
						fieldSize = this.sizes[fieldIndex];
						inFieldIndex = 0;
						fieldValue = 0;
					}
				}
			}
		}

		return split;
	}

	public String print(int[] values) {
		char[] chars = chars();

		for (int i = this.fields - 1;  i >= 0; i--) {
			final int fieldValue = values[i];
			int stIndex = this.fieldEndIndexes[i];

			if (this.needConvert[i]) {
				final String fieldCode = this.conversor.convertToString(i, fieldValue);

				for (int j = this.sizes[i] - 1; j >= 0; j--) {
					chars[stIndex--] = fieldCode.charAt(j);
				}
			} else {
				int currentFieldValue = fieldValue;

				for (int j = this.sizes[i] - 1; j >= 0; j--) {
					int newFieldValue = currentFieldValue / 10;
					chars[stIndex--] = (char) ('0' + (currentFieldValue - (newFieldValue * 10)));
					currentFieldValue = newFieldValue;
				}
			}
		}

		return new String(chars);
	}
}
