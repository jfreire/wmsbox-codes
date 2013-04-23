package com.wmsbox.codes.helpers;

import com.wmsbox.codes.Code;
import com.wmsbox.codes.CodeFormat;
import com.wmsbox.codes.metainfo.FieldInfo;

public abstract class AbstractFormat<C extends Code> implements CodeFormat<C> {

	private final String name;
	protected final FieldInfo[] fields;
	protected final int fieldSizes;
	protected final CodePattern pattern;

	public AbstractFormat(String name, FieldInfo[] fields) {
		this.name = name;
		this.fields = fields;
		this.fieldSizes = fields.length;

		int toStringLength = 0;

		for (int i = 0; i < this.fieldSizes; i++) {
			toStringLength += this.fields[i].getSize();
		}

		char[] pattern = new char[toStringLength];
		int[] fieldEndIndexes = new int[this.fieldSizes];
		int toStringIndex = 0;

		for (int i = 0; i < this.fieldSizes; i++) {
			final FieldInfo field = this.fields[i];
			final int size = field.getSize();

			for (int k = 0; k < size; k++) {
				pattern[toStringIndex++] = ' ';
			}

			fieldEndIndexes[i] = toStringIndex - 1;
		}

		this.pattern = new CodePattern(pattern, fieldEndIndexes);
	}

	public String getName() {
		return this.name;
	}

	public C parse(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Empty code");
		}

		C code = parseWithOutException(text);

		if (code == null) {
			throw new IllegalArgumentException("Invalid code " + text);
		}

		return code;
	}

	protected C parseWithOutException(String text) {
		return parse(this.pattern, text, true);
	}
	
	public int[] getAcceptedLengths() {
		return new int[] { this.pattern.length };
	}

	protected abstract C parse(CodePattern pattern, String text, boolean calculateText);
}
