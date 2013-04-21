package com.wmsbox.codes.string;

import com.wmsbox.codes.helpers.AbstractFormat;
import com.wmsbox.codes.helpers.CodePattern;
import com.wmsbox.codes.metainfo.FieldInfo;

public abstract class StringFormat<S extends StringCode> extends AbstractFormat<S> {

	//TODO otro patron distinto.
	public StringFormat(String name, FieldInfo[] fields) {
		super(name, fields);
	}

	@Override
	protected S parse(CodePattern pattern, String text, boolean calculateText) {
		final int length = text.length();

		if (length == pattern.length) {
			char[] fixChars = pattern.fixChars;
			String[] split = new String[this.fieldSizes];
			int fieldIndex = 0;
			char[] toString = calculateText ? this.pattern.start() : null;
			int toStringIndex = 0;

			for (int i = 0; i < length; i++) {
				final char ch = text.charAt(i);
				final char fixChar = fixChars[i];

				if (fixChar != 0) {
					if (fixChar != ch) {
						throw new IllegalArgumentException("Invalid code " + text);
					}
				} else {
					final FieldInfo field = this.fields[fieldIndex];
					final int fieldSize = field.getSize();
					final String fieldValue = text.substring(i, i += fieldSize - 1);
					split[fieldIndex++] = fieldValue;

					if (calculateText) {
						for (int j = 0; j < fieldSize; j++) {
							toString[toStringIndex++] = fieldValue.charAt(j);
						}
					}
				}
			}

			return create(calculateText ? this.pattern.result(toString) : text, split);
		}

		return null;
	}

	protected abstract S create(String toString, String[] values);

	protected String convertToValue(int fieldIndex, String fieldValue) {
		throw new UnsupportedOperationException();
	}

	protected String convertToPrint(int fieldIndex, String fieldValue) {
		throw new UnsupportedOperationException();
	}
}
