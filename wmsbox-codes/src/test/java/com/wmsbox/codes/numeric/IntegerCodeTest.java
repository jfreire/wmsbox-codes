package com.wmsbox.codes.numeric;

import junit.framework.Assert;

import org.junit.Test;

public class IntegerCodeTest {

	private final static SimpleIntegerFormat FORMAT = new SimpleIntegerFormat();

	@Test
	public void createSplit() {
		SimpleIntegerCode code = FORMAT.create(1, 2, 3);
		Assert.assertEquals(1, code.getField1());
		Assert.assertEquals(2, code.getField2());
		Assert.assertEquals(3, code.getField3());
		Assert.assertEquals(1203, code.intValue());
		Assert.assertEquals("CO0012/03>", code.toString());
	}

	@Test
	public void createValue() {
		SimpleIntegerCode code = FORMAT.create(1203);
		Assert.assertEquals(1, code.getField1());
		Assert.assertEquals(2, code.getField2());
		Assert.assertEquals(3, code.getField3());
		Assert.assertEquals(1203l, code.intValue());
		Assert.assertEquals("CO0012/03>", code.toString());
	}

	@Test
	public void parse() {
		SimpleIntegerCode code = FORMAT.parse("CO0012/03>");
		Assert.assertEquals(1, code.getField1());
		Assert.assertEquals(2, code.getField2());
		Assert.assertEquals(3, code.getField3());
		Assert.assertEquals(1203l, code.intValue());
		Assert.assertEquals("CO0012/03>", code.toString());
	}

	@Test
	public void invalidParse() {
		invalid(null);
		invalid("");
		invalid("00000");
		invalid("00000X");
		invalid("0000000");
		invalid(".00000");
	}

	@Test
	public void invalidParsePretty() {
		invalid("001+2.03");
		invalid("001-2+03+");
		invalid("+001-2.03");
		invalid("001-2.0A");
	}

	@Test
	public void invalidCreate() {
		invalid(-3);
		invalid(1234567);
	}

	private void invalid(String text) {
		try {
			FORMAT.parse(text);
			Assert.fail(text + " is not valid");
		} catch (IllegalArgumentException e) {
			//OK
		}
	}

	private void invalid(int value) {
		try {
			FORMAT.create(value);
			Assert.fail(value + " is not valid");
		} catch (IllegalArgumentException e) {
			//OK
		}
	}
}
