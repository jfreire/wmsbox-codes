package com.wmsbox.codes.metainfo;

public class FieldInfo {

	private final int size;
	private final Class<?> type;
	private final boolean convert;

	public FieldInfo(int size, Class<?> type, boolean convert) {
		this.size = size;
		this.type = type;
		this.convert = convert;
	}

	public int getSize() {
		return this.size;
	}

	public Class<?> getType() {
		return this.type;
	}

	public boolean isConvert() {
		return this.convert;
	}
}
