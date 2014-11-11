package edu.kit.ipd.sdq.vitruvius.framework.mir.generator;

import org.eclipse.emf.ecore.EObject;

/**
 * Holds status used for the communication between the different stages of the transformation
 * process.
 * 
 * @author Dominik Werle
 */
public interface IGeneratorStatus {
	public String GetJvmName(EObject obj);
	public void PutJvmName(EObject obj, String fqn);
	
	public void reset();
}
