package com.wmsbox.codes.speed;


//5000000 en oficina
//Ref  98234ms 
//Ref2 20562ms 
//Codes 9906ms

public class SpeedTest {

	public static void main(String[] args) {
		long time = System.currentTimeMillis();

		for (int i = 0; i < 5000000; i++) {
//			RefCode refCode = new RefCode(i % 1000, 1, 1, 1, 1, 1);
//			RefCode.parse(refCode.toString());
//			RefCode2 refCode2 = new RefCode2(i % 1000, 1, 1, 1, 1, 1);
//			RefCode2.parse(refCode2.toString());
			ToTestCode code = ToTestCode.create(i % 1000, 1, 1, 1, 1, 1);
			ToTestCode.FORMAT.parse(code.toString());
		}

		System.out.println("------> " + (System.currentTimeMillis() - time) + "ms ");
	}
}
