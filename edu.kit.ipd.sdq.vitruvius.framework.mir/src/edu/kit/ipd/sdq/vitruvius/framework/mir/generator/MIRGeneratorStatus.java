package edu.kit.ipd.sdq.vitruvius.framework.mir.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;

public class MIRGeneratorStatus implements IGeneratorStatus {
	
	private Map<EObject, String> objectToName;

	public MIRGeneratorStatus() {
		this.objectToName = new HashMap<EObject, String>();
	}

	@Override
	public String GetJvmName(EObject obj) {
		return objectToName.get(obj);
	}

	@Override
	public void PutJvmName(EObject obj, String fqn) {
		objectToName.put(obj, fqn);
	}

	@Override
	public void reset() {
		objectToName.clear();		
	}
	
	public void output() {
		for (Entry<EObject, String> e : objectToName.entrySet()) {
			System.out.println(e.getKey().toString() + " -> " + e.getValue());
		}
	}

}
