package com.wmsbox.codes.string;

import com.wmsbox.codes.helpers.CodePattern;
import com.wmsbox.codes.helpers.FieldsExtractor;
import com.wmsbox.codes.numeric.SimpleLongCode;

public class SimpleStringCodeFormat extends StringFormat<SimpleStringCode> {

	private final CodePattern prettyPattern;

	public SimpleStringCodeFormat() {
		super("SimpleLong", FieldsExtractor.extract(SimpleStringCode.class));
		this.prettyPattern = new CodePattern("{3}-{1}.{2}");
	}

	@Override
	protected SimpleStringCode create(String toString, String[] values) {
		return new SimpleStringCode(toString, values[0], Integer.parseInt(values[1]), values[2]);
	}

	public SimpleLongCode create(String field1, int field2, String field3) {
		//TODO return create(new String[] { field1, field2, field3} );
		return null;
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
		return null; //TODO return print(this.prettyPattern, code.longValue());
	}
}
