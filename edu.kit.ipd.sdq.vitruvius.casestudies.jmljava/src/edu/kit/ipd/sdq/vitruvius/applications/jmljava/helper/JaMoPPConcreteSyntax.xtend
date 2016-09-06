package edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper

import java.io.ByteArrayOutputStream
import java.io.File
import java.util.HashMap
import java.util.Map
import org.apache.commons.io.IOUtils
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.emftext.language.java.resource.java.IJavaOptions

/**
 * Utility class for working with the concrete syntax of JaMoPP.
 * This includes converting models to concrete syntax and vice versa.
 */
class JaMoPPConcreteSyntax {

	private static val Map<Object, Object> JAMOPP_LAYOUT_DISABLED_OPTIONS = getJaMoPPLayoutDisabledOptions();

	public static def getJaMoPPLayoutDisabledOptions() {
		if (JAMOPP_LAYOUT_DISABLED_OPTIONS == null) {
			val options = new HashMap<Object, Object>();
			options.put(IJavaOptions.DISABLE_LAYOUT_INFORMATION_RECORDING, Boolean.TRUE);
			options.put(IJavaOptions.DISABLE_LOCATION_MAP, Boolean.TRUE);
			//options.put(IJavaOptions.DISABLE_EMF_VALIDATION, Boolean.TRUE);
			options.put(IJavaOptions.DISABLE_CREATING_MARKERS_FOR_PROBLEMS, Boolean.TRUE);
			return options;
		}
		return JAMOPP_LAYOUT_DISABLED_OPTIONS
	}

	/**
	 * Converts a given JaMoPP object to concrete Java syntax.
	 */
	public static def String convertToConcreteSyntax(EObject javaObject) {
		val rs = new ResourceSetImpl()
		val r = rs.createResource(getRandomURI(".java"), "java")
		r.contents.add(javaObject)
		val loadOptions = new HashMap<Object, Object>()
		loadOptions.put(IJavaOptions.RESOURCE_CONTENT_TYPE, getJaMoPPLoadOptionsMap(javaObject.eClass))
		loadOptions.putAll(jaMoPPLayoutDisabledOptions);
		val baos = new ByteArrayOutputStream()
		r.save(baos, loadOptions)
		return baos.toString
	}

	/**
	 * Converts a concrete Java syntax to a JaMoPP object.
	 * @param javaConcreteSyntax The concrete syntax of the Java object
	 * @param startSymbol The EClass of the represented JaMoPP object
	 * @param eobjectType The class object of the represented JaMoPP object
	 * @param uri The uri, which shall be used for the resource
	 * @param <T> The class of the represented JaMoPP object
	 */
	public static def <T extends EObject> T convertFromConcreteSyntax(CharSequence javaConcreteSyntax,
		EClass startSymbol, Class<T> eobjectType, URI uri) {
		val inputStream = IOUtils.toInputStream(javaConcreteSyntax)
		val rs = new ResourceSetImpl();
		var resourceUri = uri;
		val r = rs.createResource(resourceUri, "java");
		r.load(inputStream, getJaMoPPLoadOptionsMap(startSymbol));
		return r.contents.get(0) as T
	}
	
	private static def getJaMoPPLoadOptionsMap(EClass startSymbol) {
		val loadOptions = new HashMap<Object, Object>()
		loadOptions.put(IJavaOptions.RESOURCE_CONTENT_TYPE, startSymbol)
		loadOptions.putAll(jaMoPPLayoutDisabledOptions);
		return loadOptions
	}

	private static def getRandomURI(String ext) {
		val f = File.createTempFile(Utilities.getRandomString(), ext)
		f.delete
		f.deleteOnExit
		return URI.createPlatformResourceURI(f.absolutePath)
	}

}
