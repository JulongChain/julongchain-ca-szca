package org.bcia.javachain.ca.szca.common.bcca.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseCodeUtils implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private static List<BaseCodeData> all;
	private static Map<String, List<BaseCodeData>> bySection;
	private static Map<String, BaseCodeData> byKey;

	public List<BaseCodeData> getAll() {
		return all;
	}

	public List<BaseCodeData> getBySection(String section) {
		if (bySection != null && bySection.containsKey(section))
			return bySection.get(section);
		List<BaseCodeData> list = new ArrayList<BaseCodeData>();
		for (BaseCodeData code : all) {
			if (section.equals(code.getSection()))
				list.add(code);
		}
		bySection.put(section, list);
		return list;
	}

	public BaseCodeData getByKey(String section, String key) {
		String keyName = section + "-" + key;
		if (byKey != null && byKey.containsKey(keyName))
			return byKey.get(keyName);
		BaseCodeData retCode = null;
		for (BaseCodeData code : all) {
			if (section.equals(code.getSection()) && key.equals(code.getKeyname())) {
				byKey.put(keyName, code);
				retCode = code;
				break;
			}

		}
		return retCode;
	}

}