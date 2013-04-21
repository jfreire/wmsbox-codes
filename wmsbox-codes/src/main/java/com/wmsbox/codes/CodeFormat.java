package com.wmsbox.codes;

public interface CodeFormat<C extends Code> {

	String getName();

	C parse(String text);
}
