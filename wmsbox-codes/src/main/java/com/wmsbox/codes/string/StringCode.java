package com.wmsbox.codes.string;

import com.wmsbox.codes.Code;

public abstract class StringCode<C extends StringCode<C>> implements Code<C> {

	private static final long serialVersionUID = 2599444396227804816L;

	private final String value;

	protected StringCode(String value) {
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		return obj instanceof StringCode && ((StringCode<C>) obj).value.equals(this.value);
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public String toString() {
		return this.value;
	}

	public int compareTo(C other) {
		return this.value.compareTo(other.value);
	}
}
