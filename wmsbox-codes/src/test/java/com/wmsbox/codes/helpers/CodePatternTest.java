package com.wmsbox.codes.helpers;

import org.junit.Assert;
import org.junit.Test;

import com.wmsbox.codes.metainfo.FieldInfo;

public class CodePatternTest {

	@Test
	public void basic() {
		FieldInfo[] fields = { new FieldInfo('A', 2), new FieldInfo('B', 4) };
		CodePattern pattern = CodePattern.build("AAA'B'BB", fields);
		Assert.assertEquals("AAABBB", new String(pattern.chars()));


	}
}
