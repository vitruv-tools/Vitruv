package edu.kit.ipd.sdq.vitruvius.framework.mir.generator;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * Holds status used for the communication between the different stages of the transformation
 * process.
 * 
 * @author Dominik Werle
 */
public interface IGeneratorStatus {
	public String getJvmName(EObject obj);
	public void putJvmName(EObject obj, String fqn);
	
	public void put(MIRFile file, MIR mir);
	public MIR getIntermediateForMIR(MIRFile file);
	
	public void reset();
}
