package com.wmsbox.codes.string;

import com.wmsbox.codes.helpers.AbstractFormat;
import com.wmsbox.codes.helpers.CodePattern;
import com.wmsbox.codes.metainfo.FieldInfo;

public abstract class StringFormat<C extends StringCode<C>> extends AbstractFormat<C> {

	//TODO otro patron distinto.
	public StringFormat(String name, FieldInfo[] fields) {
		super(name, fields);
	}

	@Override
	protected C parse(CodePattern pattern, String text, boolean calculateText) {
		final int length = text.length();

		if (length == pattern.length) {
			char[] fixChars = pattern.fixChars;
			String[] split = new String[this.fieldSizes];
			int fieldIndex = 0;
			char[] toString = calculateText ? this.pattern.chars() : null;
			int toStringIndex = 0;

			for (int i = 0; i < length; i++) {
				final char ch = text.charAt(i);
				final char fixChar = fixChars[i];

				if (fixChar != 0) {
					if (fixChar != ch) {
						throw new IllegalArgumentException("Invalid code " + text);
					}
				} else {
					final int fieldSize = pattern.sizes[fieldIndex];
					final String fieldValue = text.substring(i, i += fieldSize - 1);
					split[fieldIndex++] = fieldValue;

					if (calculateText) {
						for (int j = 0; j < fieldSize; j++) {
							toString[toStringIndex++] = fieldValue.charAt(j);
						}
					}
				}
			}

			return create(calculateText ? new String(toString) : text, split);
		}

		return null;
	}

	protected abstract C create(String toString, String[] values);
}
