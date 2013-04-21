package com.wmsbox.codes.metainfo;

public class FieldInfo {

	private final int size;
	private final boolean convert;

	public FieldInfo(int size, boolean convert) {
		this.size = size;
		this.convert = convert;
	}

	public int getSize() {
		return this.size;
	}

	public boolean isConvert() {
		return this.convert;
	}
}
