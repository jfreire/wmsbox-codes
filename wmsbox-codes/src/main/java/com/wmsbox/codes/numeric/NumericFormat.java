package com.wmsbox.codes.numeric;

import com.wmsbox.codes.Code;
import com.wmsbox.codes.helpers.AbstractFormat;
import com.wmsbox.codes.metainfo.FieldInfo;

public abstract class NumericFormat<C extends Code, V extends Number> extends AbstractFormat<C> {

	protected final int[] masks;

	public NumericFormat(String name, FieldInfo[] fields) {
		super(name, fields);

		this.masks = new int[this.fieldSizes];

		for (int i = 0; i < this.fieldSizes; i++) {
			FieldInfo field = this.fields[i];
			this.masks[i] = (int) Math.pow(10, field.getSize());
		}
	}

	public abstract C create(V value);

	protected int convertToValue(int fieldIndex, String fieldCode) {
		throw new UnsupportedOperationException();
	}

	protected String convertToPrint(int fieldIndex, int fieldValue) {
		throw new UnsupportedOperationException();
	}
}
