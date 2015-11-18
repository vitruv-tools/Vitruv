package edu.kit.ipd.sdq.vitruvius.framework.util.bridges;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
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

		public static <T> Collection<T> claimNotEmpty(Collection<T> c) {
	        if (c.size() == 0) {
	            throw new RuntimeException("It was claimed that the collection '" + c + "' is not empty!");
	        }
	        return c;
		}
		
		public static <T> T claimOne(Iterable<T> c) {
			Iterator<T> iterator = c.iterator();
			T one = iterator.next();
	        if (iterator.hasNext()) {
	            throw new RuntimeException("It was claimed that the collection '" + c + "' contains exactly one element!");
	        }
	        return one;
		}
		
		public static <T> T claimNotMany(Collection<T> c) {
	        int size = c.size();
			if (size > 1) {
	            throw new RuntimeException("It was claimed that the collection '" + c + "' contains exactly one element!");
	        } else if (size == 1) {
	        	return c.iterator().next();
	        } else {
	        	return null;
	        }
		}
		
		public static <T> List<T> toList(T o) {
			return Collections.singletonList(o);
		}
		
		public static <T> Set<T> toSet(T o) {
			return Collections.singleton(o);
		}
		
		public static <T> int replaceFirst(List<T> list, T oldElement, T newElement) {
			int indexForReplacement = list.indexOf(oldElement);
			if (indexForReplacement != -1) {
				list.set(indexForReplacement,newElement);
			}
			return indexForReplacement;
		}
		
		public static <T> T replaceFirstStringEqualElement(List<T> list, String oldElementToString, T newElement) {
			T elementToReplace = firstToStringEqualElement(list, oldElementToString);
			if (elementToReplace != null) {
				int indexOfReplacement = replaceFirst(list, elementToReplace, newElement);
				return list.get(indexOfReplacement);
			} else {
				return null;
			}
		}
		
		public static <T> boolean containsAToStringEqualElement(Iterable<T> iterable, String elementString) {
			return firstToStringEqualElement(iterable, elementString) != null;
		}
		
		public static <T> T firstToStringEqualElement(Iterable<T> iterable, String elementString) {
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
