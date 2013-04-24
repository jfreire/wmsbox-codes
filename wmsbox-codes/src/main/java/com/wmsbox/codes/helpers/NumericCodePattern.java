package com.wmsbox.codes.helpers;

import com.wmsbox.codes.metainfo.NumericConversor;

public class NumericCodePattern extends CodePattern {

	public final NumericConversor[] conversors;

	public NumericCodePattern(String pattern) {
		this(pattern, null);
	}

	public NumericCodePattern(String pattern, NumericConversor[] conversors) {
		super(pattern);
		int fields = this.sizes.length;

		if (conversors == null) {
			this.conversors = new NumericConversor[fields];
		} else {
			if (this.conversors.length != fields) {
				throw new IllegalArgumentException("TODO Description");
			}

			this.conversors = conversors;
		}
	}

}
