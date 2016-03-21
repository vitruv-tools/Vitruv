package edu.kit.ipd.sdq.vitruvius.dsls.response.generator

import org.eclipse.emf.ecore.resource.Resource

class GeneratorUtils {
	private static String BASIC_PACKAGE = "mir";
	
	protected static def String getBasicPackageQualifiedName() '''
		«BASIC_PACKAGE»'''
		
	protected static def String getPackageNameForResource(Resource resource) {
		return resource.URI.lastSegment.split("\\.").get(0);
	}

}