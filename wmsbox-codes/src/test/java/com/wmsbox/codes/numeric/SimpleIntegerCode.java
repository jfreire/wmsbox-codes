package com.wmsbox.codes.numeric;

import com.wmsbox.codes.Name;
import com.wmsbox.codes.Size;

public class SimpleIntegerCode extends BaseIntegerCode<SimpleIntegerCode> {

	private static final long serialVersionUID = -4211060913181189437L;

	private final int field1;
	private final int field2;
	private final int field3;

	protected SimpleIntegerCode(int value, String string,
			@Name('A') @Size(3) int field1,
			@Name('B') @Size(1) int field2,
			@Name('C') @Size(2) int field3) {
		super(value, string);
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}

	public int getField1() {
		return this.field1;
	}

	public int getField2() {
		return this.field2;
	}

	public int getField3() {
		return this.field3;
	}
}
