package com.wmsbox.codes;

import java.io.Serializable;

public interface Code<C extends Code<C>> extends Serializable, Comparable<C> {

}
