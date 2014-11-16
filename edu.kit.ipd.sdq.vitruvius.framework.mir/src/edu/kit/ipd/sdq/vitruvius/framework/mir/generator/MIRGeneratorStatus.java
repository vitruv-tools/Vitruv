package edu.kit.ipd.sdq.vitruvius.framework.mir.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile;

public class MIRGeneratorStatus implements IGeneratorStatus {
	
	private Map<EObject, String> objectToName;
	private Map<MIRFile, MIR> mirToIL;

	public MIRGeneratorStatus() {
		this.objectToName = new HashMap<EObject, String>();
		this.mirToIL = new HashMap<MIRFile, MIR>();
	}

	@Override
	public String getJvmName(EObject obj) {
		return objectToName.get(obj);
	}

	@Override
	public void putJvmName(EObject obj, String fqn) {
		objectToName.put(obj, fqn);
	}

	@Override
	public void reset() {
		objectToName.clear();
		mirToIL.clear();
	}
	
	public void output() {
		for (Entry<EObject, String> e : objectToName.entrySet()) {
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

}
