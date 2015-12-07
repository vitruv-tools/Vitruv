package edu.kit.ipd.sdq.vitruvius.framework.util.bridges;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

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
	
	public static final <T> Collection<T> claimNotEmpty(Collection<T> c) {
	    if (c.size() == 0) {
	        throw new RuntimeException("It was claimed that the collection '" + c + "' is not empty!");
	    }
	    return c;
	}
	
	public static final <T> T claimOne(Iterable<T> c) {
		Iterator<T> iterator = c.iterator();
		if (iterator.hasNext()) {
			T one = iterator.next();
	        if (!iterator.hasNext()) {
	        	return one;
	        }
		}
		throw new RuntimeException("It was claimed that the collection '" + c + "' contains exactly one element!");
	}
	
	public static final <T> T claimNotMany(Collection<T> c) {
	    int size = c.size();
		if (size > 1) {
	        throw new RuntimeException("It was claimed that the collection '" + c + "' contains exactly one element!");
	    } else if (size == 1) {
	    	return c.iterator().next();
	    } else {
	    	return null;
	    }
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
	
	public static final <T, R> List<R> mapFixed(Iterable<T> original, Function1<? super T, ? extends R> transformation) {
		List<R> list = new ArrayList<>();
		for (T o : original) {
			list.add(transformation.apply(o));
		}
		return list;
	}
	
    
	public static final <T> String toStringWithSeparator(Iterable<T> objects, String separator) {
		Function1<T, String> toString = new Function1<T, String>() {
			public String apply(T o) { return o.toString(); };
		};
		return toStringWithSeparator(objects, separator, toString);
	}
	
	public static final <T> String toStringWithSeparator(Iterable<T> objects, String separator, Function1<? super T, String> transformation) {
		String s = "";
		boolean firstObject = true;
		for (T o : objects) {
			if (firstObject) {
				firstObject = false;
			} else {
				s += separator;
			}
			s += transformation.apply(o);
		}
		return s;
	}
}
