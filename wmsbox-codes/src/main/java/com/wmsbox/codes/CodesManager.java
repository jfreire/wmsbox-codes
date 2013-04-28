package com.wmsbox.codes;

public interface CodesManager {

	<C extends Code<C>, F extends CodeFormat<C>> F find(Class<F> formatType);

	<C extends Code<C>, F extends CodeFormat<C>> void add(F format);

	<C extends Code<C>> C parse(String code);
}
