package edu.kit.ipd.sdq.vitruvius.framework.mir.generator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator;

import com.google.inject.Inject;

/**
 * A generator that executes multiple IGenerators after each other
 * to support the multiple stages of the compilation process for MIR.
 * @author Dominik Werle
 *
 */
public class MIRGenerator implements IGenerator {

	List<IGenerator> generators;
	
	@Inject
	public MIRGenerator(
			JvmModelGenerator jvmModelGenerator,
			MIRIntermediateLanguageGenerator intermediateLanguageGenerator,
			MIRCodeGenerator codeGenerator) {
		
		generators = new ArrayList<IGenerator>();
		
		generators.add(jvmModelGenerator);
		generators.add(intermediateLanguageGenerator);
		generators.add(codeGenerator);
	}
	
	@Override
	public void doGenerate(Resource input, IFileSystemAccess fsa) {
		for (IGenerator generator : generators) {
			generator.doGenerate(input, fsa);
		}
	}

}
