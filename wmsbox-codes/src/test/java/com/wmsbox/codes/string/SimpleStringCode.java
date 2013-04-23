package com.wmsbox.codes.string;

import com.wmsbox.codes.Size;

public class SimpleStringCode extends StringCode {
	
	private static final long serialVersionUID = 3194500462299404465L;

	private final String field1;
	private final int field2;
	private final String field3;

	protected SimpleStringCode(String string,
			@Size(3) String field1, @Size(1) int field2, @Size(2) String field3) {
		super(string);
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}

	public String getField1() {
		return this.field1;
	}

	public int getField2() {
		return this.field2;
	}

	public String getField3() {
		return this.field3;
	}
}