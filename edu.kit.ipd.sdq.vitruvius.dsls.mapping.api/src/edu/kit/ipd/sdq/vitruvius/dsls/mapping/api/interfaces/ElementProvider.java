package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

@FunctionalInterface
public interface ElementProvider {
	public List<EObject> getElements();
}
