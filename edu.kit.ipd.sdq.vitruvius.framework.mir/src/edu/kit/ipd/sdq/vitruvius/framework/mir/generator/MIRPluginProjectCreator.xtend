package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting
import org.eclipse.xtext.generator.IFileSystemAccess

/**
 * Helper class containing methods for setting up the plugin project to generate
 * for a MIR-File.
 * 
 * @author Dominik Werle
 */
class MIRPluginProjectCreator{
	private final static String[] requiredBundles = #[
		"org.eclipse.emf.ecore",
		"edu.kit.ipd.sdq.vitruvius.framework.util",
		"edu.kit.ipd.sdq.vitruvius.framework.contracts",
		"edu.kit.ipd.sdq.vitruvius.framework.mir.executor",
		"edu.kit.ipd.sdq.vitruvius.framework.meta.change",
		"com.google.guava"
	]
	
	/**
	 * Creates the plugin.xml for extending the correct extension point
	 * for edu.kit.ipd.sdq.vitruvius.framework.contracts.transformationexecuting 
	 */
	public static def createPluginXML(IFileSystemAccess fsa, String classFQN) {
		fsa.generateFile("plugin.xml",
		'''
		<?xml version="1.0" encoding="UTF-8"?>
		<?eclipse version="3.4"?>
		<plugin>
		   <extension
		         point="«EMFModelTransformationExecuting.ID»">
		      <provides
		            provider="«classFQN»">
		      </provides>
		   </extension>
		</plugin>
		'''
		)
	}
	
	/**
	 * Creates the META-INF/MANIFEST.MF file for the plugin project.
	 */
	public static def createManifest(IFileSystemAccess fsa, String projectName, String ... additionalRequiredBundles) {
		fsa.generateFile("META-INF/MANIFEST.MF",
		'''
		Manifest-Version: 1.0
		Bundle-ManifestVersion: 2
		Bundle-Name: «projectName»
		Bundle-SymbolicName: «projectName»;singleton:=true
		Bundle-Version: 1.0.0.qualifier
		Bundle-RequiredExecutionEnvironment: JavaSE-1.7
		Require-Bundle: «(requiredBundles + additionalRequiredBundles).join(",\n ")»
		
		'''
		)
	}
}