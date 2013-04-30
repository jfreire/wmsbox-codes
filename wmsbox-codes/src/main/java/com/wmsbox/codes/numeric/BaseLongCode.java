package com.wmsbox.codes.numeric;

public abstract class BaseLongCode<C extends LongCode<C>> implements LongCode<C> {

	private static final long serialVersionUID = 2599444396227804816L;

	private final long value;
	private final String string;

	protected BaseLongCode(long value, String string) {
		this.value = value;
		this.string = string;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		return obj.getClass() == getClass() && ((LongCode<C>) obj).longValue() == this.value;
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
		return (this.value < other.longValue()) ? -1 : ((this.value == other.longValue()) ? 0 : 1);
	}
}
