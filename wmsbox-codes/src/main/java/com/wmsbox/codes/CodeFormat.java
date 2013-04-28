package com.wmsbox.codes;

public interface CodeFormat<C extends Code<C>> {

	String getName();

	C parse(String text);

	int[] getAcceptedLengths();
}
