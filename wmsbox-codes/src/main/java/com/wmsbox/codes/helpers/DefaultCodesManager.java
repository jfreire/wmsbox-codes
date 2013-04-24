package com.wmsbox.codes.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wmsbox.codes.Code;
import com.wmsbox.codes.CodeFormat;
import com.wmsbox.codes.CodesManager;

public class DefaultCodesManager implements CodesManager {

	private final Map<Class<? extends CodeFormat<?>>, CodeFormat<?>> formatsByType;
	private final Map<Integer, List<CodeFormat<?>>> formatsByCodeLength;

	public DefaultCodesManager() {
		this.formatsByType = new HashMap<Class<? extends CodeFormat<?>>, CodeFormat<?>>();
		this.formatsByCodeLength = new HashMap<Integer, List<CodeFormat<?>>>();
	}

	@SuppressWarnings("unchecked")
	public <C extends Code, F extends CodeFormat<C>> F find(Class<F> formatType) {
		return (F) this.formatsByType.get(formatType);
	}

	public Code parse(String code) {
		List<CodeFormat<?>> formats = this.formatsByCodeLength.get(code.length());

		if (formats != null) {
			for (CodeFormat<?> format : formats) {
				try {
					return format.parse(code);
				} catch (IllegalArgumentException e) {
					return null;
				}
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public <C extends Code, F extends CodeFormat<C>> void add(F format) {
		Class<? extends CodeFormat<?>> type = (Class<? extends CodeFormat<?>>) format.getClass();

		if (this.formatsByType.containsKey(format)) {
			//TODO excepción.
		}

		this.formatsByType.put(type, format);

		for (int codeLength : format.getAcceptedLengths()) {
			List<CodeFormat<?>> formats = this.formatsByCodeLength.get(codeLength);

			if (formats == null) {
				formats = new ArrayList<CodeFormat<?>>();
				this.formatsByCodeLength.put(codeLength, formats);
			}

			formats.add(format);
		}
	}

	public <C extends Code, F extends CodeFormat<C>> F findByCodeType(Class<C> codeType) {
		// TODO Auto-generated method stub
		return null;
	}
}
