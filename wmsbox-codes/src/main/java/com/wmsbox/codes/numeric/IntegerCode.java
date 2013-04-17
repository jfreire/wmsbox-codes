package com.wmsbox.codes.numeric;

import com.wmsbox.codes.Code;

public abstract class IntegerCode implements Code {

	private static final long serialVersionUID = 2599444396227804816L;

	private final int value;
	private final String string;

	protected IntegerCode(int value, String string) {
		this.value = value;
		this.string = string;
	}

	@Override
	public boolean equals(Object obj) {
		return obj.getClass() == getClass() && ((IntegerCode) obj).value == this.value;
	}

	@Override
	public int hashCode() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.string;
	}

	/**
	 * Devuelve el código como numero
	 * @return
	 */
	public int intValue() {
		return this.value;
	}
}
