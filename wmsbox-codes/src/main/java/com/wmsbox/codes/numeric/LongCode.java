package com.wmsbox.codes.numeric;

import com.wmsbox.codes.Code;

public abstract class LongCode<C extends LongCode<C>> implements Code<C> {

	private static final long serialVersionUID = 2599444396227804816L;

	private final long value;
	private final String string;

	protected LongCode(long value, String string) {
		this.value = value;
		this.string = string;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		return obj instanceof LongCode && ((LongCode<C>) obj).value == this.value;
	}

	@Override
	public int hashCode() {
		return (int) (this.value ^ (this.value >>> 32));
	}

	@Override
	public String toString() {
		return this.string;
	}

	/**
	 * Devuelve el código como numero
	 * @return
	 */
	public long longValue() {
		return this.value;
	}

	public int compareTo(C other) {
		return (this.value < other.value) ? -1 : ((this.value == other.value) ? 0 : 1);
	}
}
