package edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.util;

/**
 * Utility class which contains one element.
 * @author Dominik Werle
 */
public class ClaimableSingletonContainer<T> {
	private T element;
	private final boolean isFinal;
	
	public ClaimableSingletonContainer() {
		this(false);
	}
	
	public ClaimableSingletonContainer(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	public T claim() {
		if (element == null) {
			throw new RuntimeException("It was claimed that the container contains an element. There is no element in singleton container.");
		}
		
		return element;
	}
	
	public void put(T element) {
		if (element == null) {
			throw new RuntimeException("Cannot put null inside container.");
		}
		
		if (this.isFinal && (this.element != null)) {
			throw new RuntimeException("Container is final, but already contains an element.");
		}
		
		this.element = element;
	}
}
