package com.wmsbox.codes;

public interface CodesManager {

	<C extends Code, F extends CodeFormat<C>> F find(Class<F> formatType);

	Code parse(String code);
}
