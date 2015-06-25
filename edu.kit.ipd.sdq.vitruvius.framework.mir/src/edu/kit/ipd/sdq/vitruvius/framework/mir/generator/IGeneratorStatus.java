package edu.kit.ipd.sdq.vitruvius.framework.mir.generator;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.xbase.XExpression;

/**
 * Holds status used for the communication between the different stages of the transformation
 * process.
 * 
 * @author Dominik Werle
 */
public interface IGeneratorStatus {
	public String getJvmName(Object obj);
	public void putJvmName(Object obj, String fqn);
	
	public void put(MIRFile file, MIR mir);
	public MIR getIntermediateForMIR(MIRFile file);
	
	// Used for communication between JvmModelInferrer and JvmModelGenerator
	public void addWhenWhereToInfer(XExpression whenWhere);
	public List<XExpression> getWhenWheresToInfer();
	
	public void addWithBlockToInfer(XExpression withBlock);
	public List<XExpression> getWithBlocksToInfer();
	
	public void addInvariantToInfer(XExpression invariant);
	public List<XExpression> getInvariantsToInfer();
	
	public void reset();
}
