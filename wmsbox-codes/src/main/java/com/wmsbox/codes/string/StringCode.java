package com.wmsbox.codes.string;

import com.wmsbox.codes.Code;

public class StringCode implements Code {

	private static final long serialVersionUID = 2599444396227804816L;

	private final String value;

	protected StringCode(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof StringCode && ((StringCode) obj).value.equals(this.value);
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public String toString() {
		return this.value;
	}
}
