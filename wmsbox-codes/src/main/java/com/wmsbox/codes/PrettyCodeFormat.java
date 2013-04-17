package com.wmsbox.codes;

import java.io.Serializable;


public interface PrettyCodeFormat<C extends Code, V extends Serializable> extends CodeFormat<C, V> {

	String prettyPrint(C code);
}
