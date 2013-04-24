package com.wmsbox.codes.helpers;

import com.wmsbox.codes.Code;
import com.wmsbox.codes.CodeFormat;
import com.wmsbox.codes.metainfo.FieldInfo;

public abstract class AbstractFormat<C extends Code> implements CodeFormat<C> {

	protected static final String buildDefaultPattern(FieldInfo[] fields) {
		final StringBuilder sb = new StringBuilder();

		for (final FieldInfo field : fields) {
			sb.append("{").append(field.getSize()).append("}");
		}

		return sb.toString();
	}

	private final String name;
	protected final FieldInfo[] fields;
	protected final int fieldSizes;
	protected final CodePattern pattern;

	public AbstractFormat(String name, FieldInfo[] fields) {
		this(name, fields, new CodePattern(buildDefaultPattern(fields)));
	}

	public AbstractFormat(String name, FieldInfo[] fields, CodePattern pattern) {
		this.name = name;
		this.fields = fields;
		this.fieldSizes = fields.length;
		this.pattern = pattern;
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
