package com.wmsbox.codes.speed;

import com.wmsbox.codes.Size;
import com.wmsbox.codes.helpers.FieldsExtractor;
import com.wmsbox.codes.numeric.LongCode;
import com.wmsbox.codes.numeric.LongFormat;

public class ToTestCode extends LongCode {

	private static final long serialVersionUID = -4211060913181189437L;

	public static LongFormat<ToTestCode> FORMAT = new LongFormat<ToTestCode>("TestFormat",
			FieldsExtractor.extract(ToTestCode.class)) {

		@Override
		protected ToTestCode create(long value, String toString,
				int[] values) {
			return new ToTestCode(value, toString, values[0], values[1], values[2],
					 values[3], values[4], values[5]);
		}
	};

	public static ToTestCode create(int field1, int field2, int field3, int field4, int field5,
			int field6) {
		return FORMAT.create(new int[] { field1, field2, field3, field4, field5, field5} );
	}

	private final int field1;
	private final int field2;
	private final int field3;
	private final int field4;
	private final int field5;
	private final int field6;

	protected ToTestCode(long value, String string,
			@Size(5) int field1, @Size(4) int field2, @Size(3) int field3,
			@Size(3) int field4, @Size(2) int field5, @Size(2) int field6) {
		super(value, string);
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
		this.field5 = field5;
		this.field6 = field6;
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

    public int getField4() {
		return field4;
	}

	public int getField5() {
		return field5;
	}

	public int getField6() {
		return field6;
	}
}
