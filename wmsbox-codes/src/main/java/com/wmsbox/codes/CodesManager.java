package com.wmsbox.codes;

public interface CodesManager {

	<C extends Code, F extends CodeFormat<C>> F find(Class<F> formatType);
	
	<C extends Code, F extends CodeFormat<C>> void add(F format);

	Code parse(String code);
}
