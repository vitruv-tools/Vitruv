package tools.vitruv.framework.util.bridges;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
* A utility class providing additional methods for Java collections.<br/>
* <br/>
* (Note that it is disputable whether this class conforms to the bridge pattern as we are currently
* only providing one implementation and the "abstractions" can be regarded as low-level.)
* 
* @author Max E. Kramer
*/
public final class CollectionBridge {
	
    /** Utility classes should not have a public or default constructor. */
    private CollectionBridge() {
    }
	
	public static final <T> List<T> toList(T o) {
		return Collections.singletonList(o);
	}
	
	public static final <T> Set<T> toSet(T o) {
		return Collections.singleton(o);
	}
	
	public static final <T> int replaceFirst(List<T> list, T oldElement, T newElement) {
		int indexForReplacement = list.indexOf(oldElement);
		if (indexForReplacement != -1) {
			list.set(indexForReplacement,newElement);
		}
		return indexForReplacement;
	}
	
	public static final <T> T replaceFirstStringEqualElement(List<T> list, String oldElementToString, T newElement) {
		T elementToReplace = firstToStringEqualElement(list, oldElementToString);
		if (elementToReplace != null) {
			int indexOfReplacement = replaceFirst(list, elementToReplace, newElement);
			return list.get(indexOfReplacement);
		} else {
			return null;
		}
	}
	
	public static final <T> boolean containsAToStringEqualElement(Iterable<T> iterable, String elementString) {
		return firstToStringEqualElement(iterable, elementString) != null;
	}
	
	public static final <T> T firstToStringEqualElement(Iterable<T> iterable, String elementString) {
		if (elementString == null) {
			return null;
		} else {
			for (T element : iterable) {
				if (elementString.equals(element.toString())) {
					return element;
				}
			}
			return null;
		}
	}
	
}
