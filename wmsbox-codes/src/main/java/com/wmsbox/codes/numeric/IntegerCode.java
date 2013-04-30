package com.wmsbox.codes.numeric;

import com.wmsbox.codes.Code;

public interface IntegerCode<C extends IntegerCode<C>> extends Code<C> {
	
	int intValue();
}
