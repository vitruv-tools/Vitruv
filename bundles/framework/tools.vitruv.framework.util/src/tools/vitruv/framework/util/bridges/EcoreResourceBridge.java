/*******************************************************************************
 * Copyright (c) 2012 University of Luxembourg and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Max E. Kramer - initial API and implementation
 ******************************************************************************/
package tools.vitruv.framework.util.bridges;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;

/**
 * A utility class hiding details of the resources part of the Eclipse Modeling
 * Framework API related for recurring tasks that are not project-specific.<br/>
 * <br/>
 * (Note that it is disputable whether this class conforms to the bridge pattern
 * as we are currently only providing one implementation and the "abstractions"
 * can be regarded as low-level.)
 *
 * @author Max E. Kramer
 */
public final class EcoreResourceBridge {
	/** Utility classes should not have a public or default constructor. */
	private EcoreResourceBridge() {
	}

	/**
	 * Returns a {@link Resource} that is either already loaded into and retrieved from
	 * the given {@link ResourceSet}, or creates a new {@link Resource} if it does 
	 * not exist yet.
	 *
	 * @param resourceSet the {@link ResourceSet} to load the {@link Resource} into
	 * @param uri         the {@link URI} of the {@link Resource} to get or create
	 * @return a {@link Resource} created for or retrieved from the given {@link URI}
	 */
	public static Resource getOrCreateResource(ResourceSet resourceSet, URI uri) {
		var resource = resourceSet.getResource(uri, false);
		if (resource == null) {
			resource = resourceSet.createResource(uri);
		}
		return resource;
	}
	
	/**
	 * Returns a {@link Resource} that is either loaded from the given {@link URI}
	 * if some model is persisted at that {@link URI}, or creates a new
	 * {@link Resource} if it does not exist yet.
	 *
	 * @param resourceSet the {@link ResourceSet} to load the {@link Resource} into
	 * @param uri         the {@link URI} of the {@link Resource} to load
	 * @return a {@link Resource} created for or loaded from the given {@link URI}
	 * @throws RuntimeException if some exception occurred during loading the file
	 */
	public static Resource loadOrCreateResource(ResourceSet resourceSet, URI uri) throws RuntimeException {
		try {
			return resourceSet.getResource(uri, true);
		} catch (RuntimeException e) {
			// EMF failed during demand creation, usually because the file did not exist. If
			// it has created an empty resource, retrieve it.
			// If the file does not exist, depending on the URI type not only a
			// FileNotFoundException, but at least also a ResourceException can occur. Since
			// it that exception is internal, we match the message ("not exist") instead.
			if (e.getCause() instanceof FileNotFoundException || e.getMessage().contains("not exist")) {
				return resourceSet.getResource(uri, false);
			} else {
				throw e;
			}
		}
	}

	/**
	 * Returns the root element of the content of the given resource if it is unique
	 * (exactly one root element) and {@code null} otherwise.
	 *
	 * @param resource a resource
	 * @return the unique root element (if existing) otherwise {@code null}
	 */
	public static EObject getResourceContentRootIfUnique(final Resource resource) {
		final List<EObject> resourceContents = resource.getContents();
		if (resourceContents.size() == 1) {
			return resourceContents.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Returns the root element of the content of the given resource if it is unique
	 * (exactly one root element) and throws a {@link java.lang.RuntimeException
	 * RuntimeException} otherwise.
	 *
	 * @param resource  a resource
	 * @param modelName the name of the model represented by this resource (for
	 *                  logging and error output)
	 * @return the root content element of the given resource
	 */
	public static EObject getUniqueContentRoot(final Resource resource, final String modelName) {
		final EObject uniqueResourceContentRootElement = getResourceContentRootIfUnique(resource);
		if (uniqueResourceContentRootElement != null) {
			return uniqueResourceContentRootElement;
		} else {
			throw new RuntimeException(
					"The " + modelName + " '" + resource + "' has to contain exactly one root element!");
		}
	}

	/**
	 * Returns the root element of the content of the model at the given URI if it
	 * is unique (exactly one root element) and has the type of the given class and
	 * throws a {@link java.lang.RuntimeException RuntimeException} otherwise
	 *
	 * @param resource         a resource
	 * @param modelName        the name of the model represented by this resource
	 *                         (for logging and error output)
	 * @param rootElementClass the class of which the root element has to be an
	 *                         instance of
	 * @param <T>              the type of the root element
	 * @return the root element
	 */
	public static <T extends EObject> T getUniqueContentRootIfCorrectlyTyped(final Resource resource,
			final String modelName, final Class<T> rootElementClass) {
		final EObject rootElement = getUniqueContentRoot(resource, modelName);
		return JavaBridge.dynamicCast(rootElement, rootElementClass,
				"root element '" + rootElement + "' of the " + modelName + " '" + resource + "'");
	}

	/**
	 * Returns the root element of the model instance if it is the only one with a
	 * compatible type. It is NOT necessary to have exactly one root element as long
	 * as only one of these element matches the given type. If there is not exactly
	 * one such element a {@link java.lang.RuntimeException RuntimeException} is
	 * thrown.
	 *
	 * @param resource         a resource
	 * @param modelName        the name of the model represented by this resource
	 *                         (for logging and error output)
	 * @param rootElementClass the class of which the root element has to be an
	 *                         instance of
	 * @param <T>              the type of the root element
	 * @return the root element
	 */
	@SuppressWarnings("unchecked")
	public static <T extends EObject> T getUniqueTypedRootEObject(final Resource resource, final String modelName,
			final Class<T> rootElementClass) {
		T typedRootObject = null;
		for (final EObject rootObject : resource.getContents()) {
			if (rootElementClass.isInstance(rootObject)) {
				if (typedRootObject != null) {
					throw new RuntimeException(
							"There are more than one root objects in " + modelName + ", which match the given type.");
				}
				typedRootObject = (T) rootObject;
			}
		}
		if (typedRootObject == null) {
			throw new RuntimeException(
					"The resource " + modelName + " does not contain a correctly typed root element.");
		}
		return typedRootObject;
	}

	/**
	 * Returns the root element of the model instance, which is the first one. It is
	 * NOT necessary to have exactly one root element. If there is not at least one
	 * root element a {@link java.lang.IllegalStateException IllegalStateException} is thrown.
	 *
	 * @param resource  a resource
	 * @param modelName the name of the model represented by this resource (for
	 *                  logging and error output)
	 * @return the root element
	 */
	public static EObject getFirstRootEObject(final Resource resource, final String modelName) {
		if (resource.getContents().size() < 1) {
			throw new IllegalStateException("The resource " + modelName + " does not contain a root element.");
		}
		return resource.getContents().get(0);
	}
	
	/**
	 * Returns the root element of the model instance, which is the first one. It is
	 * NOT necessary to have exactly one root element. If there is not at least one
	 * root element a {@link java.lang.IllegalStateException IllegalStateException} is thrown.
	 *
	 * @param resource  a resource
	 * @return the root element
	 */
	public static EObject getFirstRootEObject(final Resource resource) {
		return getFirstRootEObject(resource, resource.getURI().toString());
	}	

	/**
	 * Returns a set containing all contents of the given resource.
	 *
	 * @param resource a resource
	 * @return a set containing all resource contents
	 */
	public static Set<EObject> getAllContentsSet(final Resource resource) {
		return EcoreBridge.getAllContentsSet(resource.getAllContents());
	}

	/**
	 * Saves the given eObject as the only content of the model at the given
	 * URI.<br/>
	 * <br/>
	 * <b>Attention</b>: If a resource already exists at the given URI it will be
	 * overwritten!
	 *
	 * @param eObject     the new root element
	 * @param resourceURI the URI of the resource for which the content should be
	 *                    replaced or created
	 * @throws IOException if an error occurred during saving
	 */
	public static void saveEObjectAsOnlyContent(final EObject eObject, final URI resourceURI,
			final ResourceSet resourceSet) throws IOException {
		Resource resource = URIUtil.loadResourceAtURI(resourceURI, resourceSet);
		saveEObjectAsOnlyContent(eObject, resource);
	}

	/**
	 * Saves the given eObject as the only content of the given resource.
	 *
	 * @param eObject  the new root element
	 * @param resource the resource for which the content should be replaced or
	 *                 created
	 * @throws IOException if an error occurred during saving
	 */
	public static void saveEObjectAsOnlyContent(final EObject eObject, final Resource resource) throws IOException {
		resource.getContents().clear();
		resource.getContents().add(eObject);
		saveResource(resource);
	}

	/**
	 * Saves the given resource.
	 *
	 * @param resource the resource to be saved
	 * @throws IOException if an error occurred during saving
	 */
	public static void saveResource(final Resource resource) throws IOException {
		saveResource(resource, Collections.emptyMap());
	}

	/**
	 * Saves the given resource with the given options. Requires that the resource
	 * URI is either a file URI or a platform URI. Resources with other URI types
	 * (e.g. with a "pathmap" prefix) will not be saved
	 *
	 * @param resource    the resource to be saved
	 * @param saveOptions the options for saving the resource
	 * @throws IOException if an error occurred during saving
	 */
	public static void saveResource(final Resource resource, final Map<?, ?> saveOptions) throws IOException {
		if (resource.getURI().isPlatform() || resource.getURI().isFile()) {
			resource.save(saveOptions);
		}
	}

	public static void registerMetamodelPackageOn(ResourceSet rs, Object pckg, String... nsURIs) {
		for (String nsURI : nsURIs) {
			rs.getPackageRegistry().put(nsURI, pckg);
		}
	}

	public static void registerGlobalMetamodelPackage(String nsURI, Object pckg) {
		EPackage.Registry.INSTANCE.put(nsURI, pckg);
	}

	public static void registerExtensionFactoryOn(ResourceSet rs, Object resourceFactory, String... fileExtensions) {
		for (String fileExtension : fileExtensions) {
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension, resourceFactory);
		}
	}

	public static void registerGlobalExtensionFactory(String fileExtension, Object resourceFactory) {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(fileExtension, resourceFactory);
	}

	public static void registerDefaultXMIExtensionFactory(String fileExtension) {
		registerGlobalExtensionFactory(fileExtension, new XMIResourceFactoryImpl());
	}

}
