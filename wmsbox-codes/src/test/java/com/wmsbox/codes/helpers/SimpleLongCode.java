package com.wmsbox.codes.helpers;

import com.wmsbox.codes.Size;
import com.wmsbox.codes.numeric.LongCode;

public class SimpleLongCode extends LongCode {

	private static final long serialVersionUID = -4211060913181189437L;

	private final int field1;
	private final int field2;
	private final int field3;

	protected SimpleLongCode(long value, String string,
			@Size(3) int field1, @Size(1) int field2, @Size(2) int field3) {
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
