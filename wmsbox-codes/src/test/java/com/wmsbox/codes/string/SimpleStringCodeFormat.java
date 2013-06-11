package com.wmsbox.codes.string;

import com.wmsbox.codes.helpers.CodePattern;

public class SimpleStringCodeFormat extends StringFormat<SimpleStringCode> {

	private final CodePattern prettyPattern;

	public SimpleStringCodeFormat() {
		super("SimpleLong", SimpleStringCode.class);
		this.prettyPattern = CodePattern.build("AAA'-'BB'.'CC", this.fields);
	}

	@Override
	protected SimpleStringCode create(String toString, Object[] values) {
		return new SimpleStringCode(toString, ((String) values[0]).trim(),
				((Number) values[1]).intValue(), ((String) values[2]).trim());
	}

	public SimpleStringCode create(String field1, int field2, String field3) {
		return create(new Object[] { field1, field2, field3} );
	}

	@Override
	protected SimpleStringCode parseWithOutException(String text) {
		if (text.length() == this.prettyPattern.length) {
			return parse(this.prettyPattern, text, true);
		}

		return super.parseWithOutException(text);
	}

	@Override
	public int[] getAcceptedLengths() {
		return new int[] { this.pattern.length, this.prettyPattern.length };
	}

	public String prettyPrint(SimpleStringCode code) {
		return this.prettyPattern.print(new Object[] { code.getField1(), code.getField2(),
				code.getField3() } );
	}
}
