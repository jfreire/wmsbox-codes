package com.wmsbox.codes.string;

import com.wmsbox.codes.helpers.AbstractFormat;
import com.wmsbox.codes.helpers.CodePattern;
import com.wmsbox.codes.metainfo.FieldInfo;
import com.wmsbox.codes.metainfo.FieldsExtractor;

public abstract class StringFormat<C extends StringCode<C>> extends AbstractFormat<C> {

	private static final CodePattern buildPattern(FieldInfo[] fields) {
		StringBuilder builder = new StringBuilder();

		for (final FieldInfo field : fields) {
			char name = field.getName();

			for (int i = 0; i < field.getSize(); i++) {
				builder.append(name);
			}
		}

		return CodePattern.build(builder.toString(), fields);
	}

	public StringFormat(String name, Class<C> codeType) {
		this(name, FieldsExtractor.extract(codeType), (CodePattern) null);
	}

	public StringFormat(String name, Class<C> codeType, String pattern) {
		this(name, FieldsExtractor.extract(codeType), pattern);
	}

	public StringFormat(String name, FieldInfo[] fields, String pattern) {
		super(name, fields, CodePattern.build(pattern, fields));
	}

	public StringFormat(String name, FieldInfo[] fields, CodePattern pattern) {
		super(name, fields, pattern == null ? buildPattern(fields) : pattern);
	}

	@Override
	protected C parse(CodePattern pattern, String text, boolean calculateText) {
		final int length = text.length();

		if (length == pattern.length) {
			char[] fixChars = pattern.fixChars;
			Object[] split = new Object[this.fieldSizes];
			int fieldSize = pattern.sizes[0];
			boolean numericField = this.fields[0].isNumeric();
			int inFieldIndex = 0;
			int fieldIndex = 0;
			long numericFieldValue = 0;
			boolean nullNumber = false;

			for (int i = 0; i < length; i++) {
				final char ch = text.charAt(i);
				final char fixChar = fixChars[i];

				if (fixChar != 0) {
					if (fixChar != ch) {
						throw new IllegalArgumentException("Invalid code " + text);
					}
				} else {
					if (numericField) {
						if (ch >= '0' && ch <= '9') {
							if (nullNumber) {
								throw new IllegalArgumentException("Invalid code " + text);
							}

							numericFieldValue = numericFieldValue * 10 + (ch - '0');
							nullNumber = false;
						} else if (ch == ' ') {
							if (numericFieldValue != 0) {
								throw new IllegalArgumentException("Invalid code " + text);
							}

							nullNumber = true;
						} else {
							throw new IllegalArgumentException("Invalid code " + text);
						}
					}

					if (++inFieldIndex == fieldSize) {
						final Object fieldValue;

						if (nullNumber) {
							fieldValue = null;
						} else if (numericField) {
							final Class<?> type = this.fields[fieldIndex].getType();

							if (type == Integer.class || type == Integer.TYPE) {
								fieldValue = (int) numericFieldValue;
							} else {
								fieldValue = null; //TODO
							}
						} else {
							fieldValue = text.substring(i - fieldSize + 1, i + 1);
						}

						split[fieldIndex++] = fieldValue;

						if (fieldIndex < this.fieldSizes) {
							numericField = this.fields[fieldIndex].isNumeric();
							fieldSize = pattern.sizes[fieldIndex];
							inFieldIndex = 0;
							numericFieldValue = 0;
							nullNumber = false;
						}
					}
				}
			}

			return create(calculateText ? this.pattern.print(split) : text, split);
		}

		return null;
	}

	protected C create(Object[] values) {
		return create(this.pattern.print(values), values);
	}

	protected abstract C create(String toString, Object[] values);
}
