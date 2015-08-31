package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Helper class for casting, assertions, etc...
 * @author Dominik Werle
 *
 */
public final class JavaHelper {
	public static <Sub extends Sup, Sup> Sub requireType(Sup object, Class<Sub> type) {
		Objects.requireNonNull(object);
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
	
	@SuppressWarnings("unchecked")
	public static <Sub extends Sup, Sup> Stream<Sub> filterType(Stream<Sup> stream, Class<Sub> type) {
		return (Stream<Sub>) stream.filter(it -> type.isInstance(it));
	}
	
	public static <Sub extends Sup, Sup> Stream<Sub> filterType(Collection<Sup> collection, Class<Sub> type) {
		return filterType(collection.stream(), type);
	}
	
	public static <T> Optional<T> tryCast(Object object, Class<T> type) {
		if ((object != null) && type.isInstance(object)) {
			return Optional.of(type.cast(object));
		} else {
			return Optional.empty();
		}
	}
	
	public static <T> Consumer<T> emptyConsumer() {
		return it -> {};
	}
	
	public static <T> Supplier<T> nullSupplier() {
		return () -> null;
	}
}