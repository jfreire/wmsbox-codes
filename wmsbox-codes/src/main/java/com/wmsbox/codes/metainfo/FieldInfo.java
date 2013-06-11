package com.wmsbox.codes.metainfo;

public class FieldInfo {

	private final Character name;
	private final Integer size;
	private final Class<?> type;
	private final boolean numeric;

	public FieldInfo(Character name, Integer size, Class<?> type) {
		this.name = name;
		this.size = size;
		this.type = type;
		this.numeric = Number.class.isAssignableFrom(type) || type == Integer.TYPE
				|| type == Short.TYPE || type == Byte.TYPE || type == Long.TYPE;
	}

	public Character getName() {
		return this.name;
	}

	public Integer getSize() {
		return this.size;
	}

	public Class<?> getType() {
		return this.type;
	}

	public boolean isNumeric() {
		return this.numeric;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{").append(this.name).append(":").append(this.size).append('}');

		return sb.toString();
	}
}
