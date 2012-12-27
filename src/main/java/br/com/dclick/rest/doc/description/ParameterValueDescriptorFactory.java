package br.com.dclick.rest.doc.description;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParameterValueDescriptorFactory {

	public static List<String> getParameterValue(Class<?> type) {
		if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
			return asList("FALSE", "TRUE");
		}

		if (type.isEnum()) {
			List<String> values = new ArrayList<String>();
			for (Object o : type.getEnumConstants()) {
				Enum<?> e = (Enum<?>) o;
				values.add(e.name());
			}
			Collections.sort(values);
			return values;
		}
		return new ArrayList<String>();
	}
}
