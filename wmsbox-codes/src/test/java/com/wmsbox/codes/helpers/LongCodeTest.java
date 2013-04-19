package com.wmsbox.codes.helpers;

import junit.framework.Assert;

import org.junit.Test;

public class LongCodeTest {

	private final static SimpleLongCodeFormat FORMAT = new SimpleLongCodeFormat();

	@Test
	public void createSplit() {
		SimpleLongCode code = FORMAT.create(1, 2, 3);
		Assert.assertEquals(1, code.getField1());
		Assert.assertEquals(2, code.getField2());
		Assert.assertEquals(3, code.getField3());
		Assert.assertEquals(1203l, code.longValue());
		Assert.assertEquals("001203", code.toString());
	}

	@Test
	public void createValue() {
		SimpleLongCode code = FORMAT.create(1203l);
		Assert.assertEquals(1, code.getField1());
		Assert.assertEquals(2, code.getField2());
		Assert.assertEquals(3, code.getField3());
		Assert.assertEquals(1203l, code.longValue());
		Assert.assertEquals("001203", code.toString());
	}

	@Test
	public void parse() {
		SimpleLongCode code = FORMAT.parse("001203");
		Assert.assertEquals(1, code.getField1());
		Assert.assertEquals(2, code.getField2());
		Assert.assertEquals(3, code.getField3());
		Assert.assertEquals(1203l, code.longValue());
		Assert.assertEquals("001203", code.toString());
	}

	@Test
	public void pretty() {
		SimpleLongCode code = FORMAT.parse("001-2.03");
		Assert.assertEquals(1, code.getField1());
		Assert.assertEquals(2, code.getField2());
		Assert.assertEquals(3, code.getField3());
		Assert.assertEquals(1203l, code.longValue());
		Assert.assertEquals("001203", code.toString());
		Assert.assertEquals("001-2.03", FORMAT.prettyPrint(code));
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
		invalid(1234567l);
	}

	private void invalid(String text) {
		try {
			FORMAT.parse(text);
			Assert.fail(text + " is not valid");
		} catch (IllegalArgumentException e) {
			//OK
		}
	}

	private void invalid(long value) {
		try {
			FORMAT.create(value);
			Assert.fail(value + " is not valid");
		} catch (IllegalArgumentException e) {
			//OK
		}
	}
}
