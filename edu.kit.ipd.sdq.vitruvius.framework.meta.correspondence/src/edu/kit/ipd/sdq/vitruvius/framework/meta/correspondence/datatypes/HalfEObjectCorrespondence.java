package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public interface HalfEObjectCorrespondence extends List<EObject> {
	<T extends EObject> List<T> getEObjectsByType(Class<T> type);
}
