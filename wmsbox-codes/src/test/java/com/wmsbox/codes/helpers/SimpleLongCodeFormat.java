package com.wmsbox.codes.helpers;

import com.wmsbox.codes.numeric.LongFormat;

public class SimpleLongCodeFormat extends LongFormat<SimpleLongCode> {

	private final CodePattern prettyPattern;

	public SimpleLongCodeFormat() {
		super("SimpleLong", FieldsExtractor.extract(SimpleLongCode.class));
		this.prettyPattern = new CodePattern("{3}-{1}.{2}");
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

	public String prettyPrint(SimpleLongCode code) {
		return print(this.prettyPattern, code.longValue());
	}
}
