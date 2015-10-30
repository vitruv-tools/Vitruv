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
package edu.kit.ipd.sdq.vitruvius.framework.util.bridges;

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
	 * Returns the root element of the content of the given IResource if it is
	 * unique (exactly one root element) and {@code null} otherwise.
	 *
	 * @see getResourceContentRootIfUnique
	 * @param iResource
	 *            a resource
	 * @return the unique root element (if existing) otherwise {@code null}
	 */
	public static EObject getResourceContentRootFromVURIIfUnique(final URI uri, final ResourceSet resourceSet) {
		final Resource emfResource = loadResourceAtURI(uri, resourceSet);
		return getResourceContentRootIfUnique(emfResource);
	}

	/**
	 * Returns the root element of the content of the given resource if it is
	 * unique (exactly one root element) and {@code null} otherwise.
	 *
	 * @param resource
	 *            a resource
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
	 * Returns the root element of the content of the given resource if it is
	 * unique (exactly one root element) and throws a
	 * {@link java.lang.RuntimeException RuntimeException} otherwise.
	 *
	 * @param resource
	 *            a resource
	 * @param modelName
	 *            the name of the model represented by this resource (for
	 *            logging and error output)
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
	 * Returns the root element of the content of the model at the given URI if
	 * it is unique (exactly one root element) and has the type of the given
	 * class and throws a {@link java.lang.RuntimeException RuntimeException}
	 * otherwise
	 *
	 * @param resource
	 *            a resource
	 * @param modelName
	 *            the name of the model represented by this resource (for
	 *            logging and error output)
	 * @param rootElementClass
	 *            the class of which the root element has to be an instance of
	 * @param <T>
	 *            the type of the root element
	 * @return the root element
	 */
	public static <T extends EObject> T getUniqueContentRootIfCorrectlyTyped(final Resource resource,
			final String modelName, final Class<T> rootElementClass) {
		final EObject rootElement = getUniqueContentRoot(resource, modelName);
		return JavaBridge.dynamicCast(rootElement, rootElementClass,
				"root element '" + rootElement + "' of the " + modelName + " '" + resource + "'");
	}

	/**
	 * Returns the root element of the model instance if it is the only one with
	 * a compatible type. It is NOT necessary to have exactly one root element
	 * as long as only one of these element matches the given type. If there is
	 * not exactly one such element a {@link java.lang.RuntimeException
	 * RuntimeException} is thrown.
	 *
	 * @param resource
	 *            a resource
	 * @param modelName
	 *            the name of the model represented by this resource (for
	 *            logging and error output)
	 * @param rootElementClass
	 *            the class of which the root element has to be an instance of
	 * @param <T>
	 *            the type of the root element
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
	 * Returns the root element of the model instance, which is the first one.
	 * It is NOT necessary to have exactly one root element. If there is not at
	 * least one root element a {@link java.lang.RuntimeException
	 * RuntimeException} is thrown.
	 *
	 * @param resource
	 *            a resource
	 * @param modelName
	 *            the name of the model represented by this resource (for
	 *            logging and error output)
	 * @return the root element
	 */
	public static EObject getFirstRootEObject(final Resource resource, final String modelName) {
		if (resource.getContents().size() < 1) {
			throw new RuntimeException("The resource " + modelName + " does not contain a root element.");
		}
		return resource.getContents().get(0);
	}

	/**
	 * Returns a set containing all contents of the given resource.
	 *
	 * @param resource
	 *            a resource
	 * @return a set containing all resource contents
	 */
	public static Set<EObject> getAllContentsSet(final Resource resource) {
		return EcoreBridge.getAllContentsSet(resource.getAllContents());
	}

	/**
	 * Saves the given eObject as the only content of the model at the given
	 * URI.<br/>
	 * <br/>
	 * <b>Attention</b>: If a resource already exists at the given URI it will
	 * be overwritten!
	 *
	 * @param eObject
	 *            the new root element
	 * @param resource
	 *            the resource of which the content should be replaced or
	 *            created
	 * @throws IOException
	 *             if an error occurred during saving
	 */
	public static void saveEObjectAsOnlyContent(final EObject eObject, final Resource resource) throws IOException {
		resource.getContents().clear();
		resource.getContents().add(eObject);
		saveResource(resource);
	}

	/**
	 * Saves the given resource.
	 *
	 * @param resource
	 *            the resource to be saved
	 * @throws IOException
	 *             if an error occurred during saving
	 */
	public static void saveResource(final Resource resource) throws IOException {
		saveResource(resource, Collections.emptyMap());
	}

	/**
	 * Saves the given resource with the given options.
	 *
	 * @param resource
	 *            the resource to be saved
	 * @param saveOptions
	 *            the options for saving the resource
	 * @throws IOException
	 *             if an error occurred during saving
	 */
	public static void saveResource(final Resource resource, final Map<?, ?> saveOptions) throws IOException {
		resource.save(saveOptions);
		resource.setModified(true);
	}

	public static Resource loadResourceAtURI(final URI resourceURI, final ResourceSet resourceSet) {
		return loadResourceAtURI(resourceURI, resourceSet, Collections.emptyMap());
	}

	public static Resource loadResourceAtURI(final URI resourceURI, final ResourceSet resourceSet,
			final Map<Object, Object> loadOptions) {
		Resource resource = null;
		try {
			try {
				resource = resourceSet.getResource(resourceURI, true);
			} catch (org.eclipse.emf.common.util.WrappedException e) {
				// swallow silently
				e = null; // otherwise checkstyle complains: "Must have at least
							// one statement."
			}
			if (resource == null) {
				resource = resourceSet.createResource(resourceURI);
			} else {
				resource.load(loadOptions);
			}
			// fixes issue caused by JaMoPP: If a model is transitively loaded
			// (e.g. because of an
			// import)
			// the URI starts with pathmap instead of the usual URI. If you try
			// to load this model
			// again
			// the URI remains wrong.
			resource.setURI(resourceURI);
		} catch (final IOException e) {
			// soften
			throw new RuntimeException(e);
		}
		return resource;
	}
	
	public static void registerMetamodelPackages(ResourceSet rs, Object factory, String... nsURIs) {
		for (String nsURI : nsURIs) {
			rs.getPackageRegistry().put(nsURI,factory);
//			registerMetamodelPackages(nsURI,factory);
		}
	}

	// TODO MK rename to registerMetamodelPackage
	public static void registerMetamodelPackages(String nsURI, Object factory) {
		EPackage.Registry.INSTANCE.put(nsURI, factory);
	}
	
	public static void registerExtensionFactories(ResourceSet rs, Object resourceFactory, String... fileExtensions) {
		for (String fileExtension : fileExtensions) {
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension, resourceFactory);
//			registerExtensionFactories(fileExtension, resourceFactory);
		}
	}

	// TODO MK rename to registerExtensionFactory
	public static void registerExtensionFactories(String fileExtension, Object resourceFactory) {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(fileExtension, resourceFactory);
	}
	
	public static void registerDefaultXMIExtensionFactory(String fileExtension) {
		registerExtensionFactories(fileExtension, new XMIResourceFactoryImpl());
	}

}
