package com.wmsbox.codes.numeric;

import com.wmsbox.codes.Code;
import com.wmsbox.codes.helpers.AbstractFormat;
import com.wmsbox.codes.helpers.NumericCodePattern;
import com.wmsbox.codes.metainfo.FieldInfo;

public abstract class NumericFormat<C extends Code, V extends Number> extends AbstractFormat<C> {

	protected final int[] masks;
	protected final int digits;

	public NumericFormat(String name, FieldInfo[] fields) {
		this(name, fields, new NumericCodePattern(buildDefaultPattern(fields)));
	}

	public NumericFormat(String name, FieldInfo[] fields, NumericCodePattern pattern) {
		super(name, fields, pattern);

		this.masks = new int[this.fieldSizes];
		int digits = 0;

		for (int i = 0; i < this.fieldSizes; i++) {
			FieldInfo field = this.fields[i];
			this.masks[i] = (int) Math.pow(10, field.getSize());
			digits += field.getSize();
		}

		this.digits = digits;
	}

	public abstract C create(V value);
}
