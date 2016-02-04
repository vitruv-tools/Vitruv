package edu.kit.ipd.sdq.vitruvius.framework.util.bridges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Triple;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.*;

/**
 * Helper class for casting, assertions, etc...
 * 
 * TODO: DW merge with JavaBridge!
 * 
 * @author Dominik Werle
 *
 */
public final class JavaHelper {
	public static <Sub extends Sup, Sup> Sub requireType(Sup object, Class<Sub> type) {
		Objects.requireNonNull(object);
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
	 * If all elements in the iterator are (equal-)identical, return the element
	 * (the first). Else return <code>null</code>.
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

	public static <T> void with(T target, Consumer<T> consumer) {
		consumer.accept(target);
	}
	
	public static <T, R> R apply(T target, Function<T, R> consumer) {
		return consumer.apply(target);
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

	/**
	 * Returns elements as long as any of the two iterators returns something
	 * (hasNext == true). Will return null for the other iterator.
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static <T, U> Iterable<Pair<T, U>> zipAny(final Iterable<T> first, final Iterable<U> second) {
		return new Iterable<Pair<T, U>>() {
			@Override
			public Iterator<Pair<T, U>> iterator() {
				return new Iterator<Pair<T, U>>() {
					private final Iterator<T> first_iter = first.iterator();
					private final Iterator<U> second_iter = second.iterator();

					@Override
					public boolean hasNext() {
						return (first_iter.hasNext() || second_iter.hasNext());
					}

					@Override
					public Pair<T, U> next() {
						return new Pair<T, U>((first_iter.hasNext() ? first_iter.next() : null),
								(second_iter.hasNext() ? second_iter.next() : null));
					}
				};
			}
		};
	}

	public static <T, U, V> Iterable<Triple<T, U, V>> zip(final Iterable<T> first, final Iterable<U> second,
			final Iterable<V> third) {
		return map(zip(zip(first, second), third),
				it -> new Triple<>(it.getFirst().getFirst(), it.getFirst().getSecond(), it.getSecond()));
	}

	public static <T, U, V> Iterable<Triple<T, U, V>> zipAny(final Iterable<T> first, final Iterable<U> second,
			final Iterable<V> third) {
		return map(zipAny(zipAny(first, second), third),
				it -> new Triple<>(it.getFirst().getFirst(), it.getFirst().getSecond(), it.getSecond()));
	}

	public static <T> Iterable<T> andThen(Iterable<T> first, Iterable<T> second) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					private Iterator<T> first_iter = first.iterator();
					private Iterator<T> second_iter = second.iterator();

					@Override
					public boolean hasNext() {
						return (first_iter.hasNext() || second_iter.hasNext());
					}

					@Override
					public T next() {
						if (first_iter.hasNext())
							return first_iter.next();
						else
							return second_iter.next();
					}

				};
			}
		};
	}

	public static <T> Iterable<T> iterate(T t) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					@Override
					public boolean hasNext() {
						return true;
					}

					@Override
					public T next() {
						return t;
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
	
	/**
	 * Gets the given key from the given map, if present, or puts a value obtained from
	 * <code>valueSupplier</code> in the map under the given key and returns it.
	 */
	public static <K,V> V getOrPut(Map<K,V> map, K key, Supplier<V> valueSupplier) {
		if (!map.containsKey(key)) {
			map.put(key, valueSupplier.get());
		}
		return map.get(key);
	}
	
	/**
	 * Gets the given key from the given map, if present, or puts <code>defaultValue</code>
	 * in the map under the given key and returns it.
	 */
	public static <K,V> V getOrPut(Map<K,V> map, K key, V defaultValue) {
		if (!map.containsKey(key)) {
			map.put(key, defaultValue);
		}
		return map.get(key);
	}
	
	public static <T, S extends T> Integer indexOf(Iterable<T> objs, S obj) {
		for (Pair<Integer, T> it : withIndex(objs)) {
			if (it.getSecond() == obj)
				return it.getFirst();
		}
		
		return null;
	}
}