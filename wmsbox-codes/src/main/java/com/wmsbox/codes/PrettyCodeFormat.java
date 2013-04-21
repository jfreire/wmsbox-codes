package com.wmsbox.codes;



public interface PrettyCodeFormat<C extends Code> extends CodeFormat<C> {

	String prettyPrint(C code);
}
