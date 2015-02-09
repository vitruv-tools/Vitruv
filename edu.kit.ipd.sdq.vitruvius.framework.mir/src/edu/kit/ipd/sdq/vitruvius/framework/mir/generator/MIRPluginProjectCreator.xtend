package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR

class MIRPluginProjectCreator{
	public static def transform(MIR file, URI resourcePath, IFileSystemAccess fsa) {
	}
	
	private static def createPluginXML(IFileSystemAccess fsa, String classFQN) {
		fsa.generateFile("plugin.xml",
		'''
		<?xml version="1.0" encoding="UTF-8"?>
		<?eclipse version="3.4"?>
		<plugin>
		   <extension
		         point="edu.kit.ipd.sdq.vitruvius.framework.contracts.transformationexecuting">
		      <provides
		            provider="«classFQN»">
		      </provides>
		   </extension>
		</plugin>
		'''
		)
	}
}