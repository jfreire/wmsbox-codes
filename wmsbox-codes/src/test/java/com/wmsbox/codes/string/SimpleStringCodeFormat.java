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
		String field1 = ((String) values[0]);
		Number field2 = ((Number) values[1]);
		String field3 = ((String) values[2]);

		return new SimpleStringCode(toString, field1 == null ? null : field1.trim(),
				field2 == null ? null : field2.intValue(), field3 == null ? null : field3.trim());
	}

	public SimpleStringCode create(String field1, Integer field2, String field3) {
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
