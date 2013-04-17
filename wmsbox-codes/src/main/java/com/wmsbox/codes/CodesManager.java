package com.wmsbox.codes;

import java.io.Serializable;


public interface CodesManager {

	<C extends Code, V extends Serializable, F extends CodeFormat<C, V>>
			F find(Class<F> formatType);

	Code parse(String code);
}
