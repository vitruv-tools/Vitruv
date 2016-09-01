package edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.mapping.interfaces;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

@FunctionalInterface
public interface ElementProvider {
	public List<EObject> getElements();
}
