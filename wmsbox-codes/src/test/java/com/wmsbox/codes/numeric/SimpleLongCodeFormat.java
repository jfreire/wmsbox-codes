package com.wmsbox.codes.numeric;

import com.wmsbox.codes.helpers.NumericCodePattern;

public class SimpleLongCodeFormat extends LongFormat<SimpleLongCode> {

	private final NumericCodePattern prettyPattern;

	public SimpleLongCodeFormat() {
		super("SimpleLong", SimpleLongCode.class);
		this.prettyPattern = NumericCodePattern.build("AAA'-'B'.'CC", this.fields);
	}

	@Override
	protected SimpleLongCode create(long value, String toString, int[] values) {
		return new SimpleLongCode(value, toString, values[0], values[1], values[2]);
	}

	public SimpleLongCode create(int field1, int field2, int field3) {
		return create(new int[] { field1, field2, field3} );
	}

	@Override
	protected SimpleLongCode parseWithOutException(String text) {
		if (text.length() == this.prettyPattern.length) {
			return parse(this.prettyPattern, text, true);
		}

		return super.parseWithOutException(text);
	}

	@Override
	public int[] getAcceptedLengths() {
		return new int[] { this.pattern.length, this.prettyPattern.length };
	}

	public String prettyPrint(SimpleLongCode code) {
		return print(this.prettyPattern, code.longValue());
	}
}
