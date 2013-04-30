package com.wmsbox.codes.numeric;

import com.wmsbox.codes.Code;

public interface LongCode<C extends LongCode<C>> extends Code<C> {

	long longValue();
}
