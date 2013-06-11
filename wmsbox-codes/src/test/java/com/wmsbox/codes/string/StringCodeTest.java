package com.wmsbox.codes.string;

import junit.framework.Assert;

import org.junit.Test;

public class StringCodeTest {

	private final static SimpleStringCodeFormat FORMAT = new SimpleStringCodeFormat();

	@Test
	public void createSplit() {
		SimpleStringCode code = FORMAT.create("AB", 2, "XY");
		Assert.assertEquals("AB", code.getField1());
		Assert.assertEquals(2, code.getField2());
		Assert.assertEquals("XY", code.getField3());
		Assert.assertEquals("AB 02XY", code.toString());
		Assert.assertEquals("AB -02.XY", FORMAT.prettyPrint(code));
	}

	@Test
	public void parse() {
		SimpleStringCode code = FORMAT.parse("AB 02XY");
		Assert.assertEquals("AB", code.getField1());
		Assert.assertEquals(2, code.getField2());
		Assert.assertEquals("XY", code.getField3());
		Assert.assertEquals("AB 02XY", code.toString());
		Assert.assertEquals("AB -02.XY", FORMAT.prettyPrint(code));
	}

	@Test
	public void pretty() {
		SimpleStringCode code = FORMAT.parse("AB -02.XY");
		Assert.assertEquals("AB", code.getField1());
		Assert.assertEquals(2, code.getField2());
		Assert.assertEquals("XY", code.getField3());
		Assert.assertEquals("AB 02XY", code.toString());
		Assert.assertEquals("AB -02.XY", FORMAT.prettyPrint(code));
	}

	@Test
	public void invalidParse() {
		invalid(null);
		invalid("");
		invalid("AB 0ZXY");
	}

	@Test
	public void invalidParsePretty() {
		invalid("AB -0Z.XY");
		invalid("AB +0Z.XY");
		invalid("AB -0ZXY");
	}

	private void invalid(String text) {
		try {
			FORMAT.parse(text);
			Assert.fail(text + " is not valid");
		} catch (IllegalArgumentException e) {
			//OK
		}
	}
}
