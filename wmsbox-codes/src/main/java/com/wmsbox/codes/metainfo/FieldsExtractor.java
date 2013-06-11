package com.wmsbox.codes.metainfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.wmsbox.codes.Code;
import com.wmsbox.codes.Name;
import com.wmsbox.codes.Size;

public class FieldsExtractor {

	public static <C extends Code<C>> FieldInfo[] extract(Class<C> codeType) {
		final Constructor<?>[] constructors = codeType.getDeclaredConstructors();

		if (constructors.length != 1) {
			throw new IllegalArgumentException(codeType + " must have only one constructor "
					+ constructors.length);
		}

		final Constructor<?> constructor = constructors[0];
		final Class<?>[] types = constructor.getParameterTypes();
		final Annotation[][] annotations = constructor.getParameterAnnotations();
		final List<FieldInfo> infos = new ArrayList<FieldInfo>();

		for (int i = 0; i < types.length; i++) {
			Integer size = null;
			Character name = null;

			for (final Annotation annotation : annotations[i]) {
				if (annotation instanceof Size) {
					size = ((Size) annotation).value();
				} else if (annotation instanceof Name) {
					name = ((Name) annotation).value();
				}
			}

			if (size != null || name != null) {
				if (size == null || size <= 0) {
					throw new IllegalArgumentException(codeType + " argument " + (i + 1)
							+ " have an invalid size " + size);
				}

				if (name == null) {
					throw new IllegalArgumentException(codeType + " argument " + (i + 1)
							+ " have an invalid name " + name);
				}

				infos.add(new FieldInfo(name, size, types[i]));
			}
		}

		return infos.toArray(new FieldInfo[infos.size()]);
	}
}
