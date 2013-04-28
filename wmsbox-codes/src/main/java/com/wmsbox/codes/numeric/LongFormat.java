package com.wmsbox.codes.numeric;

import com.wmsbox.codes.helpers.CodePattern;
import com.wmsbox.codes.helpers.NumericCodePattern;
import com.wmsbox.codes.metainfo.FieldInfo;

public abstract class LongFormat<C extends LongCode<C>> extends NumericFormat<C, Long> {

	private final long maxValue;

	public LongFormat(String name, Class<C> codeType) {
		super(name, codeType);
		this.maxValue = (long) (Math.pow(10, this.digits)) - 1;
	}

	public LongFormat(String name, Class<C> codeType, String pattern) {
		super(name, codeType, pattern);
		this.maxValue = (long) (Math.pow(10, this.digits)) - 1;
	}

	public LongFormat(String name, Class<C> codeType, String pattern,
			NumericConversor conversor) {
		super(name, codeType, pattern, conversor);
		this.maxValue = (long) (Math.pow(10, this.digits)) - 1;
	}

	public LongFormat(String name, FieldInfo[] fields, String pattern,
			NumericConversor conversor) {
		super(name, fields, pattern, conversor);
		this.maxValue = (long) (Math.pow(10, this.digits)) - 1;
	}

	@Override
	protected C parse(CodePattern pattern, String text, boolean calculateText) {
		final int length = text.length();

		if (length == pattern.length) {
			int[] split = ((NumericCodePattern) pattern).parse(text);

			long result = 0;

			for (int i = 0; i < this.fieldSizes; i++) {
				result = result * this.masks[i] + split[i];
			}

			return create(result, calculateText ? ((NumericCodePattern) this.pattern).print(split)
					: text, split);
		}

		return null;
	}

	public C create(final int[] fieldValues) {
		if (fieldValues == null) {
			throw new IllegalArgumentException("Null value");
		}

		if (fieldValues.length != this.fieldSizes) {
			throw new IllegalArgumentException("Invalid fields number " + fieldValues.length);
		}

		long result = 0;

		for (int i = 0; i < this.fieldSizes; i++) {
			result = result * this.masks[i] + fieldValues[i];
		}

		return create(result, ((NumericCodePattern) this.pattern).print(fieldValues), fieldValues);
	}

	private int[] split(long value) {
		int[] split = new int[this.fieldSizes];
		long currentValue = value;

		for (int i = this.fieldSizes - 1;  i >= 0; i--) {
			final long newValue = currentValue / this.masks[i];
			split[i] = (int) (currentValue - (newValue * this.masks[i]));
			currentValue = newValue;
		}

		return split;
	}

	protected String print(NumericCodePattern pattern, long value) {
		return pattern.print(split(value));
	}

	@Override
	public C create(final Long value) {
		if (value == null) {
			throw new IllegalArgumentException("Null value");
		}

		if (value < 0 || value >= this.maxValue) {
			throw new IllegalArgumentException("Invalid code " + value);
		}

		int[] split = split(value);

		return create(value, ((NumericCodePattern) this.pattern).print(split), split);
	}

	protected abstract C create(long value, String toString, int[] values);
}
