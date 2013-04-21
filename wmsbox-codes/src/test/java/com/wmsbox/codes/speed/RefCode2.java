package com.wmsbox.codes.speed;




public class RefCode2 {

	public static RefCode2 parse(String text) {
		return new RefCode2(Integer.parseInt(text.substring(0, 5)),
				Integer.parseInt(text.substring(5, 9)),
				Integer.parseInt(text.substring(9, 12)),
				Integer.parseInt(text.substring(12, 15)),
				Integer.parseInt(text.substring(15, 17)),
				Integer.parseInt(text.substring(17, 19)));
	}

	private final int field1;
	private final int field2;
	private final int field3;
	private final int field4;
	private final int field5;
	private final int field6;

	protected RefCode2(int field1, int field2, int field3, int field4, int field5, int field6) {
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

	public static String fillWithZeros(Object value, Integer length) {
        String str = value.toString();
        int tam = str.length();

        while (tam++ < length) {
            str = "0" + str;
        }

        return str;
    }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(fillWithZeros(this.field1, 5));
		sb.append(fillWithZeros(this.field2, 4));
		sb.append(fillWithZeros(this.field3, 3));
		sb.append(fillWithZeros(this.field4, 3));
		sb.append(fillWithZeros(this.field5, 2));
		sb.append(fillWithZeros(this.field6, 2));

		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(parse(new RefCode2(1, 3, 4, 4, 5, 4).toString()));
	}
}
