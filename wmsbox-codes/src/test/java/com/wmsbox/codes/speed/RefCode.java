package com.wmsbox.codes.speed;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RefCode {

	private static final Pattern PATTERN = Pattern.compile("(\\d{5})(\\d{4})(\\d{3})(\\d{3})(\\d{2})(\\d{2})");

	public static RefCode parse(String text) {
		Matcher matcher = PATTERN.matcher(text);

		if (matcher.matches()) {
			return new RefCode(Integer.parseInt(matcher.group(1)),
					Integer.parseInt(matcher.group(2)),
					Integer.parseInt(matcher.group(3)),
					Integer.parseInt(matcher.group(4)),
					Integer.parseInt(matcher.group(5)),
					Integer.parseInt(matcher.group(6)));
		}

		return null;
	}

	private final int field1;
	private final int field2;
	private final int field3;
	private final int field4;
	private final int field5;
	private final int field6;

	protected RefCode(int field1, int field2, int field3, int field4, int field5, int field6) {
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
		this.field5 = field5;
		this.field6 = field6;
	}

	public int getField1() {
		return this.field1;
	}

	public int getField2() {
		return this.field2;
	}

	public int getField3() {
		return this.field3;
	}

    public int getField4() {
		return field4;
	}

	public int getField5() {
		return field5;
	}

	public int getField6() {
		return field6;
	}

	@Override
	public String toString() {
		return String.format("%05d%04d%03d%03d%02d%02d", this.field1, this.field2, this.field3,
				this.field4, this.field5, this.field6);
	}

	public static void main(String[] args) {
		System.out.println(parse(new RefCode(1, 2, 3, 4, 5, 6).toString()));
	}
}
