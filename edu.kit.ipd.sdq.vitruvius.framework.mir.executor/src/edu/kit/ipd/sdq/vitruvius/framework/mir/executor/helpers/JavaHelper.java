package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * Helper class for casting, assertions, etc...
 * 
 * @author Dominik Werle
 *
 */
public final class JavaHelper {
	public static <Sub extends Sup, Sup> Sub requireType(Sup object, Class<Sub> type) {
		return requireType(object, type, "Cannot cast " + object.toString() + " to " + type.toString());
	}

	@SuppressWarnings("unchecked")
	public static <T> Collection<T> requireCollectionType(Object object, Class<T> type) {
		return Collections.checkedCollection(requireType(object, Collection.class), type);
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

	public static <Sub extends Sup, Sup> Sub requireTypeOrNull(Sup object, Class<Sub> type) {
		if (object == null) {
			return null;
		}

		return requireType(object, type, "Cannot cast " + object.toString() + " to " + type.toString());
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

	public static <T> T claimExactlyOne(Iterable<T> iterable) {
		return claimOneOrNone(iterable)
				.orElseThrow(() -> new RuntimeException("Iterable does not have an element, as claimed."));
	}

	public static <T> Optional<T> claimOneOrNone(Iterable<T> iterable) {
		final Iterator<T> iterator = iterable.iterator();
		if (!iterator.hasNext()) {
			return Optional.empty();
		}

		final T result = iterator.next();
		if (iterator.hasNext()) {
			throw new RuntimeException("Iterable does not have exactly one element as claimed, but more. Next element: "
					+ iterator.next());
		}

		return Optional.of(result);
	}

	/**
	 * @see JavaHelper#claimIdenticalElements(Iterator)
	 */
	public static <T> T claimIdenticalElements(Iterable<T> iterable) {
		return claimIdenticalElements(iterable.iterator());
	}

	/**
	 * Claims that all elements in the iterator are identical and returns one
	 * (the first).
	 */
	public static <T> T claimIdenticalElements(Iterator<T> iterator) {
		if (!iterator.hasNext()) {
			throw new RuntimeException("Iterator does not have an element, as claimed.");
		}

		final T result = iterator.next();

		while (iterator.hasNext()) {
			T next = iterator.next();
			if (!result.equals(next)) {
				throw new RuntimeException("Iterable does not have identical elements, found " + result.toString()
						+ " and " + next.toString());
			}
		}

		return result;
	}

	/**
	 * @see JavaHelper#getIdenticalElement(Iterator)
	 */
	public static <T> T getIdenticalElement(Iterable<T> iterable) {
		return getIdenticalElement(iterable.iterator());
	}
	
	/**
	 * If all elements in the iterator are (equal-)identical, return the element (the first). Else return <code>null</code>.
	 */
	public static <T> T getIdenticalElement(Iterator<T> iterator) {
		if (!iterator.hasNext()) {
			return null;
		}

		final T result = iterator.next();

		while (iterator.hasNext()) {
			T next = iterator.next();
			if (!result.equals(next)) {
				return null;
			}
		}

		return result;
	}
	
	public static <T> Collector<T, ?, Optional<T>> identicalElementCollector() {
		return Collectors.reducing((a, b) -> (a == null || b == null) ? null : ((a.equals(b)) ? a : null));
	}
	
	/**
	 * Method for assertions inside function chains, when used with Xtend.
	 */
	public static <T> T claim(T target, Predicate<T> claim) {
		if (!claim.test(target)) {
			throw new RuntimeException("Target " + target.toString() + " does not fulfill claim.");
		}

		return target;
	}

	/**
	 * Returns the target element predicate holds, else null.
	 * 
	 * Can be used with the Xtend <code>?.</code> operator, to only continue
	 * operation if a predicate is true, e.g.:
	 * <p>
	 * <code>aCollection.when[size > 2]?.map[...]</code>
	 */
	public static <T> T when(T target, Predicate<T> predicate) {
		return (predicate.test(target) ? target : null);
	}

	public static <T> Consumer<T> emptyConsumer() {
		return it -> {
		};
	}

	public static <T> Supplier<T> nullSupplier() {
		return () -> null;
	}

	public static final char FQN_SEPARATOR = '.';
	public static final char PATH_SEPERATOR = '/';

	public static String classNameToJavaPath(String name) {
		return (name.replace(FQN_SEPARATOR, PATH_SEPERATOR) + ".java");
	}

	public static String classNameToPackageName(String fqn) {
		int lastSeparatorPos = fqn.lastIndexOf(FQN_SEPARATOR);
		if (lastSeparatorPos == -1)
			return null;
		else
			return fqn.substring(0, lastSeparatorPos);
	}

	public static String toSimpleName(String fqn) {
		int lastSeparatorPos = fqn.lastIndexOf(FQN_SEPARATOR);
		if (lastSeparatorPos == -1)
			return fqn;
		else
			return fqn.substring(lastSeparatorPos + 1);
	}

	public static boolean isSimpleName(String fqn) {
		int lastSeparatorPos = fqn.lastIndexOf(FQN_SEPARATOR);
		return (lastSeparatorPos == -1);
	}

	// iterate with index
	public static <T> Iterable<Pair<Integer, T>> withIndex(final Iterable<T> iterable) {
		return zip(createIntegerIterator(0), iterable);
	}

	public static <T, U> Iterable<Pair<T, U>> zip(final Iterable<T> first, final Iterable<U> second) {
		return new Iterable<Pair<T, U>>() {
			@Override
			public Iterator<Pair<T, U>> iterator() {
				return new Iterator<Pair<T, U>>() {
					private final Iterator<T> first_iter = first.iterator();
					private final Iterator<U> second_iter = second.iterator();

					@Override
					public boolean hasNext() {
						return (first_iter.hasNext() && second_iter.hasNext());
					}

					@Override
					public Pair<T, U> next() {
						return new Pair<T, U>(first_iter.next(), second_iter.next());
					}
				};
			}
		};
	}

	public static Iterable<Integer> createIntegerIterator(int start) {
		return new Iterable<Integer>() {
			@Override
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>() {
					private int counter = start;

					@Override
					public boolean hasNext() {
						return counter < Integer.MAX_VALUE;
					}

					@Override
					public Integer next() {
						return counter++;
					}

				};
			}
		};
	}
}