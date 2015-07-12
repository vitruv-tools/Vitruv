package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers;

import java.util.Objects;

/**
 * Helper class for casting, assertions, etc...
 * @author Dominik Werle
 *
 */
public final class JavaHelper {
	public static <Sub extends Sup, Sup> Sub requireType(Sup object, Class<Sub> type) {
		return requireType(object, type, "Cannot cast " + object.toString() + " to " + type.toString());
	}
	
	@SuppressWarnings("unchecked")
	public static <Sub extends Sup, Sup> Sub requireType(Sup object, Class<Sub> type, String errorMessage) {
		Objects.requireNonNull(object);
		if (!(type.isInstance(object))) {
			throw new RuntimeException(errorMessage);
		} else {
			return (Sub) object;
		}
	}
}