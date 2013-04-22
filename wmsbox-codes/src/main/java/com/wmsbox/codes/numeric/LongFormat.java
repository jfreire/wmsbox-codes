package com.wmsbox.codes.numeric;

import com.wmsbox.codes.helpers.CodePattern;
import com.wmsbox.codes.metainfo.FieldInfo;

public abstract class LongFormat<S extends LongCode> extends NumericFormat<S, Long> {

	private final long maxValue;

	public LongFormat(String name, FieldInfo[] fields) {
		super(name, fields);
		this.maxValue = (long) (Math.pow(10, this.pattern.length)) - 1;
	}

	@Override
	protected S parse(CodePattern pattern, String text, boolean calculateText) {
		final int length = text.length();

		if (length == pattern.length) {
			long value = 0;
			int[] split = new int[this.fieldSizes];
			FieldInfo field = this.fields[0];
			int fieldSize = field.getSize();
			boolean convert = field.isConvert();
			int inFieldIndex = 0;
			int fieldIndex = 0;
			int beginIndex = -1;
			int fieldValue = 0;
			char[] fixChars = pattern.fixChars;

			for (int i = 0; i < length; i++) {
				final char ch = text.charAt(i);
				final char fixChar = fixChars[i];

				if (fixChar != 0) {
					if (fixChar != ch) {
						throw new IllegalArgumentException("Invalid code " + text);
					}
				} else {
					if (convert) {
						if (beginIndex == -1) {
							beginIndex = i;
						}
					} else if (ch >= '0' && ch <= '9') {
						fieldValue = fieldValue * 10 + (ch - '0');
					} else {
						throw new IllegalArgumentException("Invalid code " + text);
					}
				}

				if (++inFieldIndex == fieldSize) {
					if (beginIndex != -1) {
						fieldValue = convertToValue(fieldIndex,
								text.substring(beginIndex, i + 1));
						beginIndex = -1;
					}

					value = value * this.masks[fieldIndex] + fieldValue;
					split[fieldIndex++] = fieldValue;

					if (fieldIndex < this.fieldSizes) {
						field = this.fields[fieldIndex];
						fieldSize = field.getSize();
						convert = field.isConvert();
						inFieldIndex = 0;
						fieldValue = 0;
					}
				}
			}

			return create(value, calculateText ? print(this.pattern, value) : text, split);
		}

		return null;
	}

	public S create(final int[] fieldValues) {
		if (fieldValues == null) {
			throw new IllegalArgumentException("Null value");
		}

		if (fieldValues.length != this.fieldSizes) {
			throw new IllegalArgumentException("Invalid fields number " + fieldValues.length);
		}

		char[] chars = this.pattern.start();
		long result = 0;

		for (int i = 0; i < this.fieldSizes; i++) {
			final FieldInfo field = this.fields[i];
			final int fieldValue = fieldValues[i];
			final int fieldSize = field.getSize();
			int stIndex = this.pattern.fieldEndIndexes[i];

			if (field.isConvert()) {
				final String code = convertToPrint(i, fieldValue);

				for (int j = fieldSize - 1; j >= 0; j--) {
					chars[stIndex--] = code.charAt(j);
				}
			} else {
				int currentFieldValue = fieldValue;

				for (int j = fieldSize - 1; j >= 0; j--) {
					int newFieldValue = currentFieldValue / 10;
					chars[stIndex--] = (char) ('0' + (currentFieldValue - (newFieldValue * 10)));
					currentFieldValue = newFieldValue;
				}
			}

			result = result * this.masks[i] + fieldValue;
		}

		return create(result, this.pattern.result(chars), fieldValues);
	}

	protected String print(CodePattern pattern, long value) {
		char[] chars = pattern.start();
		long currentValue = value;

		for (int i = this.fieldSizes - 1;  i >= 0; i--) {
			final long newValue = currentValue / this.masks[i];
			final int fieldValue = (int) (currentValue - (newValue * this.masks[i]));
			final FieldInfo field = this.fields[i];
			int stIndex = pattern.fieldEndIndexes[i];

			if (field.isConvert()) {
				final String fieldCode = convertToPrint(i, fieldValue);

				for (int j = field.getSize() - 1; j >= 0; j--) {
					chars[stIndex--] = fieldCode.charAt(j);
				}
			} else {
				int currentFieldValue = fieldValue;

				for (int j = field.getSize() - 1; j >= 0; j--) {
					int newFieldValue = currentFieldValue / 10;
					chars[stIndex--] = (char) ('0' + (currentFieldValue - (newFieldValue * 10)));
					currentFieldValue = newFieldValue;
				}
			}

			currentValue = newValue;
		}

		return pattern.result(chars);
	}

	@Override
	public S create(final Long value) {
		if (value == null) {
			throw new IllegalArgumentException("Null value");
		}

		if (value < 0 || value >= this.maxValue) {
			throw new IllegalArgumentException("Invalid code " + value);
		}

		long currentValue = value.longValue();
		int[] split = new int[this.fieldSizes];
		char[] chars = this.pattern.start();

		for (int i = this.fieldSizes - 1;  i >= 0; i--) {
			final long newValue = currentValue / this.masks[i];
			final int fieldValue = (int) (currentValue - (newValue * this.masks[i]));
			split[i] = fieldValue;
			final FieldInfo field = this.fields[i];
			int stIndex = this.pattern.fieldEndIndexes[i];

			if (field.isConvert()) {
				final String code = convertToPrint(i, fieldValue);

				for (int j = field.getSize() - 1; j >= 0; j--) {
					chars[stIndex--] = code.charAt(j);
				}
			} else {
				int currentFieldValue = fieldValue;

				for (int j = field.getSize() - 1; j >= 0; j--) {
					int newFieldValue = currentFieldValue / 10;
					chars[stIndex--] = (char) ('0' + (currentFieldValue - (newFieldValue * 10)));
					currentFieldValue = newFieldValue;
				}
			}

			currentValue = newValue;
		}

		return create(value, new String(chars), split);
	}

	protected abstract S create(long value, String toString, int[] values);
}
