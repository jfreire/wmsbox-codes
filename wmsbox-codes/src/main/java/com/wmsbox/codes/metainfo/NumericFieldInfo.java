package com.wmsbox.codes.metainfo;

public class NumericFieldInfo extends FieldInfo {

	private final int maxValue;
	private final int minValue;

	public NumericFieldInfo(int size, int minValue, int maxValue) {
		super(size);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public int getMaxValue() {
		return this.maxValue;
	}

	public int getMinValue() {
		return this.minValue;
	}
}
