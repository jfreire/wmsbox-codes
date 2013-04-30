package com.wmsbox.codes.numeric;


public abstract class BaseIntegerCode<C extends BaseIntegerCode<C>> implements IntegerCode<C> {

	private static final long serialVersionUID = 2599444396227804816L;

	private final int value;
	private final String string;

	protected BaseIntegerCode(int value, String string) {
		this.value = value;
		this.string = string;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		return obj.getClass() == getClass() && ((BaseIntegerCode<C>) obj).value == this.value;
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

	public int compareTo(C other) {
		return (this.value < other.value) ? -1 : ((this.value == other.value) ? 0 : 1);
	}
}
