package com.wmsbox.codes.numeric;

import com.wmsbox.codes.helpers.AbstractFormat;
import com.wmsbox.codes.helpers.CodePattern;
import com.wmsbox.codes.metainfo.FieldInfo;

public abstract class LongFormat<S extends LongCode> extends AbstractFormat<S, Long> {

	private final long maxValue;
	private final int[] masks;
	private final CodePattern prettyPattern;

	public LongFormat(String name, FieldInfo[] fields) {
		super(name, fields);
		this.masks = new int[this.fieldsSize];
		long maxValue = 1l;

		for (int i = 0; i < this.fieldsSize; i++) {
			FieldInfo field = this.fields[i];
			this.masks[i] = (int) Math.pow(10, field.getSize());
			maxValue *= this.masks[i];
		}

		this.maxValue = maxValue;
		this.prettyPattern = null;
	}

	public S parse(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Empty code");
		}

		final int length = text.length();

		if (length == this.toStringPattern.length) {
			long value = 0;
			int[] split = new int[this.fieldsSize];
			FieldInfo field = this.fields[0];
			int fieldSize = field.getSize();
			boolean convert = field.isConvert();
			int inFieldIndex = 0;
			int fieldIndex = 0;
			int beginIndex = -1;
			int fieldValue = 0;

			for (int i = 0; i < length; i++) {
				final char ch = text.charAt(i);

				if (convert) {
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
						fieldValue = convert(fieldIndex, text.substring(beginIndex, i + 1));
						beginIndex = -1;
					}

					value = value * this.masks[fieldIndex] + fieldValue;
					split[fieldIndex++] = fieldValue;

					if (fieldIndex < this.fieldsSize) {
						field = this.fields[fieldIndex];
						fieldSize = field.getSize();
						convert = field.isConvert();
						inFieldIndex = 0;
						fieldValue = 0;
					}
				}
			}

			return create(value, text, split);
		}

		//TODO pretty
		throw new IllegalArgumentException("Invalid code " + text);
	}

	public S create(final int[] fieldValues) {
		if (fieldValues == null) {
			throw new IllegalArgumentException("Null value");
		}

		if (fieldValues.length != this.fieldsSize) {
			throw new IllegalArgumentException("Invalid fields number " + fieldValues.length);
		}

		final int length = this.toStringPattern.length;
		char[] chars = new char[length];
		System.arraycopy(this.toStringPattern, 0, chars, 0, length);
		int stIndex = 0;
		long result = 0;

		for (int i = 0; i < this.fieldsSize; i++) {
			final FieldInfo field = this.fields[i];
			final int fieldValue = fieldValues[i];
			final int fieldSize = field.getSize();
			stIndex += fieldSize;

			if (field.isConvert()) {
				final String code = convert(i, fieldValue);

				for (int j = fieldSize - 1; j >= 0; j--) {
					chars[--stIndex] = code.charAt(j);
				}
			} else {
				int currentFieldValue = fieldValue;

				for (int j = fieldSize - 1; j >= 0; j--) {
					int newFieldValue = currentFieldValue / 10;
					chars[--stIndex] = (char) ('0' + (currentFieldValue - (newFieldValue * 10)));
					currentFieldValue = newFieldValue;
				}
			}

			stIndex += fieldSize;
			result = result * this.masks[i] + fieldValue;
		}

		return create(result, new String(chars), fieldValues);
	}

	public String pretty(S code) {
		char[] chars = this.prettyPattern.getPattern();
		long currentValue = code.longValue();

		for (int i = this.fieldsSize - 1;  i >= 0; i--) {
			final long newValue = currentValue / this.masks[i];
			final int fieldValue = (int) (currentValue - (newValue * this.masks[i]));
			write(i, fieldValue, chars, this.prettyPattern);
			currentValue = newValue;
		}

		return this.prettyPattern.result(chars);
	}

	public void write(int index, int value, char[] chars, CodePattern pattern) {
		if (this.fields[index].isConvert()) {
			pattern.write(index, convert(index, value), chars);
		} else {
			pattern.write(index, value, chars);
		}
	}

	public S create(final Long value) {
		if (value == null) {
			throw new IllegalArgumentException("Null value");
		}

		if (value < 0 || value >= this.maxValue) {
			throw new IllegalArgumentException("Invalid code " + value);
		}

		long currentValue = value.longValue();
		int[] split = new int[this.fieldsSize];
		int length = this.toStringPattern.length;
		char[] chars = new char[length];
		System.arraycopy(this.toStringPattern, 0, chars, 0, length);
		int stIndex = length - 1;

		for (int i = this.fieldsSize - 1;  i >= 0; i--) {
			final long newValue = currentValue / this.masks[i];
			final int fieldValue = (int) (currentValue - (newValue * this.masks[i]));
			split[i] = fieldValue;
			final FieldInfo field = this.fields[i];

			if (field.isConvert()) {
				final String code = convert(i, fieldValue);

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

	protected int convert(int fieldIndex, String fieldCode) {
		throw new UnsupportedOperationException();
	}

	protected String convert(int fieldIndex, int fieldValue) {
		throw new UnsupportedOperationException();
	}
}
