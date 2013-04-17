package com.wmsbox.codes;

import java.io.Serializable;

public interface CodeFormat<C extends Code, V extends Serializable> {

	String getName();

	C parse(String text);

	C create(V value);
}
