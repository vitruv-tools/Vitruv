package edu.kit.ipd.sdq.vitruvius.framework.mir.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.lib.ListExtensions;

import com.google.common.collect.Lists;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR;
import edu.kit.ipd.sdq.vitruvius.framework.mir.jvmmodel.MIRJvmModelInferrer;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile;

/**
 * Wraps some state that is passed between the different stages of the
 * generation (i.e. {@link MIRJvmModelInferrer}, {@link MIRJvmModelGenerator}
 * and the various inferrers}.
 * <p>
 * @author Dominik Werle
 */
public class MIRGeneratorStatus implements IGeneratorStatus {
	
	private Map<Object, String> objectToName;
	private Map<MIRFile, MIR> mirToIL;
	
	private List<XExpression> whenWheresToInfer;
	private List<XExpression> withBlocksToInfer;
	private List<XExpression> invariantsToInfer;
	
	public MIRGeneratorStatus() {
		this.objectToName = new HashMap<Object, String>();
		this.mirToIL = new HashMap<MIRFile, MIR>();
		
		this.whenWheresToInfer = new ArrayList<XExpression>();
		this.withBlocksToInfer = new ArrayList<XExpression>();
		this.invariantsToInfer = new ArrayList<XExpression>();
	}

	@Override
	public String getJvmName(Object obj) {
		return objectToName.get(obj);
	}

	@Override
	public void putJvmName(Object obj, String fqn) {
		objectToName.put(obj, fqn);
	}

	@Override
	public void reset() {
		objectToName.clear();
		mirToIL.clear();
		
		whenWheresToInfer.clear();
		withBlocksToInfer.clear();
	}
	
	public void output() {
		for (Entry<Object, String> e : objectToName.entrySet()) {
			System.out.println(e.getKey().toString() + " -> " + e.getValue());
		}
	}

	@Override
	public void put(MIRFile file, MIR mir) {
		mirToIL.put(file, mir);
	}

	@Override
	public MIR getIntermediateForMIR(MIRFile file) {
		return mirToIL.get(file);
	}

	
	@Override
	public void addWhenWhereToInfer(XExpression whenWhere) {
		whenWheresToInfer.add(whenWhere);		
	}

	@Override
	public List<XExpression> getWhenWheresToInfer() {
		return Collections.unmodifiableList(whenWheresToInfer);
	}

	@Override
	public void addWithBlockToInfer(XExpression withBlock) {
		withBlocksToInfer.add(withBlock);
		
	}

	@Override
	public List<XExpression> getWithBlocksToInfer() {
		return Collections.unmodifiableList(withBlocksToInfer);
	}

	@Override
	public void addInvariantToInfer(XExpression invariant) {
		invariantsToInfer.add(invariant);
	}

	@Override
	public List<XExpression> getInvariantsToInfer() {
		return Collections.unmodifiableList(invariantsToInfer);
	}

}
