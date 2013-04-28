package com.wmsbox.codes.helpers;

import com.wmsbox.codes.Code;
import com.wmsbox.codes.CodeFormat;
import com.wmsbox.codes.metainfo.FieldInfo;

public abstract class AbstractFormat<C extends Code<C>> implements CodeFormat<C> {

	private static final CodePattern buildPattern(FieldInfo[] fields) {
		StringBuilder builder = new StringBuilder();

		for (final FieldInfo field : fields) {
			char name = field.getName();

			for (int i = 0; i < field.getSize(); i++) {
				builder.append(name);
			}
		}

		return CodePattern.build(builder.toString(), fields);
	}

	private final String name;
	protected final int fieldSizes;
	protected final CodePattern pattern;
	protected FieldInfo[] fields;

	public AbstractFormat(String name, FieldInfo[] fields, CodePattern pattern) {
		this.name = name;
		this.fieldSizes = fields.length;
		this.pattern = pattern == null ? buildPattern(fields) : pattern;
		this.fields = fields;
	}

	public AbstractFormat(String name, FieldInfo[] fields) {
		this(name, fields, null);
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
