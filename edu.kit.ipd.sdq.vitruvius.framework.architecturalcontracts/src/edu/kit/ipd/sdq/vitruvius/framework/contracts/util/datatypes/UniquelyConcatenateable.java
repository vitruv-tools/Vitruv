package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

/**
 * Interface implemented by objects that can be concatenated with other objects of the same type <T> in a unique order.
 * @author kramerm
 *
 * @param <T>
 */
public interface UniquelyConcatenateable<T> {
	String concatenate(T other);
}
