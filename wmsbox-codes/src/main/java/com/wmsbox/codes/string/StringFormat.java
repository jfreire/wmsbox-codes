package com.wmsbox.codes.string;

import com.wmsbox.codes.helpers.AbstractFormat;
import com.wmsbox.codes.metainfo.FieldInfo;

public class StringFormat<S extends StringCode> extends AbstractFormat<S, String> {

	private final int[] valueIndexes;

	public StringFormat(String name, FieldInfo[] fields) {
		super(name, fields);
		this.valueIndexes = new int[this.fieldsSize];
		int currentIndex = 0;

		for (int i = 0; i < this.fieldsSize; i++) {
			FieldInfo field = this.fields[i];
			final int size = field.getSize();
			this.valueIndexes[i] = currentIndex;
			currentIndex += size;
		}
	}

	public S parse(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Empty code");
		}

		final int length = text.length();

		if (length == this.toStringPattern.length) {
			return null; //build(Long.parseLong(text), text);
		}

		throw new IllegalArgumentException("Invalid code " + text);
	}

	public S create(String value) {
		// TODO Auto-generated method stub
		return null;
	}
}