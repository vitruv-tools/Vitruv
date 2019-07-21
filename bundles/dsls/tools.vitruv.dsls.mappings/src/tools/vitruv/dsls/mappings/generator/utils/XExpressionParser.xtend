package tools.vitruv.dsls.mappings.generator.utils

import java.io.ByteArrayInputStream
import java.io.InputStream

import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource

class XExpressionParser {

	private final static String XBASE_LANGAUGE_EXTENSION='___xbase';
	private static XtextResourceSet resourceSet
	private final static URI DUMMY_RESOURCE_URI = URI.createURI("dummy:/example."+XBASE_LANGAUGE_EXTENSION)
	private static Resource dummyResource
	
	def public static initParser(XtextResourceSet resourceSet) {
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
