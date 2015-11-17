package edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers

import org.eclipse.xtext.generator.IFileSystemAccess
import java.util.Collection
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants

/**
 * Helper class containing methods for setting up the plugin project to generate
 * for a MIR-File.
 * 
 * @author Dominik Werle
 */
class MappingPluginProjectHelper {
	private final static String[] requiredBundles = #[
		"org.eclipse.emf.ecore",
		"edu.kit.ipd.sdq.vitruvius.framework.util",
		"edu.kit.ipd.sdq.vitruvius.framework.contracts",
		"edu.kit.ipd.sdq.vitruvius.framework.mir.executor",
		"edu.kit.ipd.sdq.vitruvius.framework.meta.change",
		"edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence",
		"edu.kit.ipd.sdq.vitruvius.framework.mir.testframework",
		"com.google.guava",
		"org.eclipse.core.resources",
		"org.apache.log4j",
		"org.eclipse.xtend.lib",
 		"org.eclipse.xtend.lib.macro"
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
		         point="«Change2CommandTransforming.ID»">
		      <provides
		            «VitruviusConstants.getExtensionPropertyName()»="«classFQN»">
		      </provides>
		   </extension>
		</plugin>
		'''
		)
	}
	
	/**
	 * Creates the META-INF/MANIFEST.MF file for the plugin project.
	 */
	public static def createManifest(IFileSystemAccess fsa, String projectName,
		Collection<String> additionalRequiredBundles,
		Collection<String> exportedPackages
	) {
		fsa.generateFile("META-INF/MANIFEST.MF",
		'''
		Manifest-Version: 1.0
		Bundle-ManifestVersion: 2
		Bundle-Name: «projectName»
		Bundle-SymbolicName: «projectName»;singleton:=true
		Bundle-Version: 1.0.0.qualifier
		Bundle-RequiredExecutionEnvironment: JavaSE-1.8
		Require-Bundle: «(requiredBundles + additionalRequiredBundles).join(",\n ")»
		Export-Package: «(exportedPackages.join(",\n "))»
		'''
		)
	}
}