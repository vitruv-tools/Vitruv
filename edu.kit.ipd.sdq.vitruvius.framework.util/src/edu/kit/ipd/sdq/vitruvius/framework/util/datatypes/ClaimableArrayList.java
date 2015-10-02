package edu.kit.ipd.sdq.vitruvius.framework.util.datatypes;

import java.util.ArrayList;

public class ClaimableArrayList<E> extends ArrayList<E> implements ClaimableList<E>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public E claimValueForIndex(int index) {
		E element = get(index);
		if (element == null) {
			throw new RuntimeException(getIndexViolationMsg(index));
		}
		return element;
	}
	
	private String getIndexViolationMsg(int index) {
		String elementMsg = "an element";
		return getIndexViolationMsg(index,elementMsg);
	}
				
	private String getIndexViolationMsg(int index, String elementMsg) {
		return "It was claimed that " + elementMsg + " is at position '" + index + "' in the list '" + this + "' but was not found!";
	}

	@Override
	public E setClaimingNullOrSameListed(int index, E element) {
		// check whether the element to set will be next to the last index
		boolean isNextToLastIndex = index == size();
		E previouslyListed = null;
		if (isNextToLastIndex) {
			add(index, element);
		} else if (index < size()) {
			previouslyListed = set(index, element);
		} else {
			throw new RuntimeException("aaaaa");
		}
		
		boolean previouslyNull = previouslyListed == null;
		boolean nullOrSameListed = previouslyNull || previouslyListed.equals(element);
		if (!nullOrSameListed) {
			throw new RuntimeException(getIndexViolationMsg(index, "the element '" + element + "'"));
		}
		return previouslyListed;
	}

	@Override
	public boolean containsElementAtPosition(int index) {
		E element = get(index);
		return element != null;
	}
}
