package com.wmsbox.codes.string;

import com.wmsbox.codes.Name;
import com.wmsbox.codes.Size;

public class SimpleStringCode extends StringCode<SimpleStringCode> {

	private static final long serialVersionUID = 3194500462299404465L;

	private final String field1;
	private final Integer field2;
	private final String field3;

	protected SimpleStringCode(String string,
			@Name('A') @Size(3) String field1,
			@Name('B') @Size(2) Integer field2,
			@Name('C') @Size(2) String field3) {
		super(string);
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}

	public String getField1() {
		return this.field1;
	}

	public Integer getField2() {
		return this.field2;
	}

	public String getField3() {
		return this.field3;
	}
}
