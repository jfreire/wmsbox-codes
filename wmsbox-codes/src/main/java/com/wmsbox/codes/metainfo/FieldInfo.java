package com.wmsbox.codes.metainfo;

public class FieldInfo {

	private Character name;
	private Integer size;

	public FieldInfo() {
		// Nada
	}

	public FieldInfo(Character name, Integer size) {
		this.name = name;
		this.size = size;
	}

	public Character getName() {
		return this.name;
	}

	public Integer getSize() {
		return this.size;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{").append(this.name).append(":").append(this.size).append('}');

		return sb.toString();
	}
}
