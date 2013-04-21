package com.wmsbox.codes.speed;


//Ref   ------> 9996ms 107675648 - 65689200
//Ref2  ------> 1802ms 402128896 - 351493616
//Codes ------> 1320ms 179896320 - 60184408

//------> 14440ms 68288512 - 62338128
//------> 15352ms 128647168 - 111513272
public class SpeedTest {

	public static void main(String[] args) {
		long time = System.currentTimeMillis();

		for (int i = 0; i < 10000000; i++) {
//			RefCode refCode = new RefCode(i % 1000, 1, 1, 1, 1, 1);
//			RefCode.parse(refCode.toString());
//			RefCode2 refCode2 = new RefCode2(i % 1000, 1, 1, 1, 1, 1);
//			RefCode2.parse(refCode2.toString());
			ToTestCode code = ToTestCode.create(i % 1000, 1, 1, 1, 1, 1);
			ToTestCode.FORMAT.parse(code.toString());
		}

		System.out.println("------> " + (System.currentTimeMillis() - time) + "ms "
				+ Runtime.getRuntime().totalMemory() + " - " + Runtime.getRuntime().freeMemory());
	}
}
