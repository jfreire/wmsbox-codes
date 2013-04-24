package com.wmsbox.codes.numeric;

import com.wmsbox.codes.helpers.FieldsExtractor;
import com.wmsbox.codes.helpers.NumericCodePattern;

public class SimpleIntegerFormat extends IntegerFormat<SimpleIntegerCode> {

	public SimpleIntegerFormat() {
		super("SimpleInteger", FieldsExtractor.extract(SimpleLongCode.class),
				 new NumericCodePattern("CO{3}{1}/{2}>"));
	}

	@Override
	protected SimpleIntegerCode create(int value, String toString, int[] values) {
		return new SimpleIntegerCode(value, toString, values[0], values[1], values[2]);
	}

	public SimpleIntegerCode create(int field1, int field2, int field3) {
		return create(new int[] { field1, field2, field3} );
	}
}
