package com.wmsbox.codes.numeric;

import com.wmsbox.codes.Code;
import com.wmsbox.codes.helpers.AbstractFormat;
import com.wmsbox.codes.helpers.NumericCodePattern;
import com.wmsbox.codes.metainfo.FieldInfo;
import com.wmsbox.codes.metainfo.FieldsExtractor;

/**
 *
 * @author jfreire
 *
 * @param <C>
 * @param <V>
 */
public abstract class NumericFormat<C extends Code<C>, V extends Number> extends AbstractFormat<C> {

	private static final NumericCodePattern buildNumericPattern(FieldInfo[] fields) {
		StringBuilder builder = new StringBuilder();

		for (final FieldInfo field : fields) {
			char name = field.getName();

			for (int i = 0; i < field.getSize(); i++) {
				builder.append(name);
			}
		}

		return NumericCodePattern.build(builder.toString(), fields);
	}

	protected final int[] masks;
	protected final int digits;

	public NumericFormat(String name, Class<C> codeType) {
		this(name, FieldsExtractor.extract(codeType), null, null);
	}

	public NumericFormat(String name, Class<C> codeType, String pattern) {
		this(name, FieldsExtractor.extract(codeType), pattern, null);
	}

	public NumericFormat(String name, Class<C> codeType, String pattern,
			NumericConversor conversor) {
		this(name, FieldsExtractor.extract(codeType), pattern, conversor);
	}

	public NumericFormat(String name, FieldInfo[] fields, String pattern,
			NumericConversor conversor) {
		super(name, fields, pattern == null ? buildNumericPattern(fields)
				: NumericCodePattern.build(pattern, fields, conversor));

		this.masks = new int[this.fieldSizes];
		int digits = 0;

		for (int i = 0; i < this.fieldSizes; i++) {
			int size = fields[i].getSize();
			this.masks[i] = (int) Math.pow(10, size);
			digits += size;
		}

		this.digits = digits;
	}

	public abstract C create(V value);
}
