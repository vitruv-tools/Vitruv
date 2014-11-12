package edu.kit.ipd.sdq.vitruvius.framework.mir.generator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;

public class MIRCodeGenerator implements IGenerator {

	@Override
	public void doGenerate(Resource input, IFileSystemAccess fsa) {
		System.out.println("MIRCodeGenerator");
	}

}
