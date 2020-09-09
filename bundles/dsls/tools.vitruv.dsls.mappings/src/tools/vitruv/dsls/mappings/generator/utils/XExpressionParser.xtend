package tools.vitruv.dsls.mappings.generator.utils

import java.io.ByteArrayInputStream

import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource

class XExpressionParser {

	val static String XBASE_LANGAUGE_EXTENSION='___xbase';
	var static XtextResourceSet resourceSet
	val static URI DUMMY_RESOURCE_URI = URI.createURI("dummy:/example."+XBASE_LANGAUGE_EXTENSION)
	var static Resource dummyResource
	
	def static initParser(XtextResourceSet resourceSet) {
		XExpressionParser.resourceSet = resourceSet
		dummyResource = resourceSet.createResource(DUMMY_RESOURCE_URI)
	}

	def static XExpression parseExpression(String expression) {
		if(dummyResource.loaded){
			dummyResource.unload
		}
		val in = new ByteArrayInputStream(expression.getBytes())
		dummyResource.load(in, resourceSet.getLoadOptions())
		dummyResource.getContents().get(0) as XExpression
	}
}
