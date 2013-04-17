package com.wmsbox.codes.helpers;

import java.io.Serializable;

import com.wmsbox.codes.Code;
import com.wmsbox.codes.CodeFormat;
import com.wmsbox.codes.metainfo.FieldInfo;

public abstract class AbstractFormat<C extends Code, V extends Serializable>
		implements CodeFormat<C, V> {

	private final String name;
	protected final FieldInfo[] fields;
	protected final int fieldsSize;
	protected final int toStringLength;
	protected final char[] toStringPattern;

	public AbstractFormat(String name, FieldInfo[] fields) {
		this.name = name;
		this.fields = fields;
		this.fieldsSize = fields.length;

		int toStringLength = 0;

		for (int i = 0; i < this.fieldsSize; i++) {
			toStringLength += this.fields[i].getSize();
		}

		this.toStringLength = toStringLength;
		this.toStringPattern = new char[toStringLength];
		int toStringIndex = 0;

		for (int i = 0; i < this.fieldsSize; i++) {
			final FieldInfo field = this.fields[i];
			final int size = field.getSize();
			char ch = Number.class.isAssignableFrom(field.getType()) ? '0' : ' ';

			for (int k = 0; k < size; k++) {
				this.toStringPattern[toStringIndex++] = ch;
			}
		}
	}

	public String getName() {
		return this.name;
	}
}
