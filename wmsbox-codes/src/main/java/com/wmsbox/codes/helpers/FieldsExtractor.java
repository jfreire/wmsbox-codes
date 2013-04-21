package com.wmsbox.codes.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import com.wmsbox.codes.Code;
import com.wmsbox.codes.Size;
import com.wmsbox.codes.metainfo.FieldInfo;

public class FieldsExtractor {

	public static <C extends Code> FieldInfo[] extract(Class<C> codeType) {
		final Constructor<?>[] constructors = codeType.getDeclaredConstructors();

		if (constructors.length != 1) {
			throw new IllegalArgumentException(codeType + " must have only one constructor "
					+ constructors.length);
		}

		final Constructor<?> constructor = constructors[0];
		final Class<?>[] types = constructor.getParameterTypes();

		if (types.length < 3) {
			throw new IllegalArgumentException(codeType
					+ " constructor must have more than 3 params");
		}

		final Annotation[][] annotations = constructor.getParameterAnnotations();
		final int length = types.length - 2;
		final FieldInfo[] infos = new FieldInfo[length];

		for (int i = 0; i < length; i++) {
			int size = 0;

			for (final Annotation annotation : annotations[i + 2]) {
				if (annotation instanceof Size) {
					size = ((Size) annotation).value();
					break;
				}
			}

			if (size <= 0) {
				throw new IllegalArgumentException(codeType + " argument " + (i + 1)
						+ " not have size");
			}

			infos[i] = new FieldInfo(size, false);
		}

		return infos;
	}
}
