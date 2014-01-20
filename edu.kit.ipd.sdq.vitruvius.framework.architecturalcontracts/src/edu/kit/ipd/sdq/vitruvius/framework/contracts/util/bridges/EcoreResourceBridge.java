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
package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

/**
 * A utility class hiding details of the resources part of the Eclipse Modeling Framework API related for recurring tasks that are
 * not project-specific.<br/>
 * <br/>
 * (Note that it is disputable whether this class conforms to the bridge pattern as we are currently only providing one
 * implementation and the "abstractions" can be regarded as low-level.)
 *
 * @author Max E. Kramer
 */
public final class EcoreResourceBridge {
   /** Utility classes should not have a public or default constructor. */
   private EcoreResourceBridge() {
   }

   /**
    * Returns the root element of the content of the given IResource if it is unique (exactly one root element) and {@code null}
    * otherwise.
    * @see getResourceContentRootIfUnique
    * @param iResource
    *           a resource
    * @return the unique root element (if existing) otherwise {@code null}
    */
   public static EObject getResourceContentRootFromVURIIfUnique(final VURI vuri) {
      ResourceSet resSet = new ResourceSetImpl();
      Resource emfResource = resSet.getResource(vuri.getEMFUri(), true);
      return getResourceContentRootIfUnique(emfResource);
   }
   
   /**
    * Returns the root element of the content of the given resource if it is unique (exactly one root element) and {@code null}
    * otherwise.
    *
    * @param resource
    *           a resource
    * @return the unique root element (if existing) otherwise {@code null}
    */
   public static EObject getResourceContentRootIfUnique(final Resource resource) {
      List<EObject> resourceContents = resource.getContents();
      if (resourceContents.size() == 1) {
         return resourceContents.get(0);
      } else {
         return null;
      }
   }

   /**
    * Returns the root element of the content of the given resource if it is unique (exactly one root element) and throws a
    * {@link java.lang.RuntimeException RuntimeException} otherwise.
    *
    * @param resource
    *           a resource
    * @param modelName
    *           the name of the model represented by this resource (for logging and error output)
    * @return the root content element of the given resource
    */
   public static EObject getUniqueContentRoot(final Resource resource, final String modelName) {
      EObject uniqueResourceContentRootElement = getResourceContentRootIfUnique(resource);
      if (uniqueResourceContentRootElement != null) {
         return uniqueResourceContentRootElement;
      } else {
         throw new RuntimeException("The " + modelName + " '" + resource + "' has to contain exactly one root element!");
      }
   }

   /**
    * Returns the root element of the content of the model at the given URI if it is unique (exactly one root element) and has the
    * type of the given class.
    *
    * @param resource
    *           a resource
    * @param modelName
    *           the name of the model represented by this resource (for logging and error output)
    * @param rootElementClass
    *           the class of which the root element has to be an instance of
    * @param <T>
    *           the type of the root element
    * @return the root element
    */
   public static <T extends EObject> T getUniqueContentRootIfCorrectlyTyped(final Resource resource, final String modelName,
         final Class<T> rootElementClass) {
      EObject rootElement = getUniqueContentRoot(resource, modelName);
      return JavaBridge.dynamicCast(rootElement, rootElementClass, "root element '" + rootElement + "' of the " + modelName
            + " '" + resource + "'");
   }

   /**
    * Returns a set containing all contents of the given resource.
    *
    * @param resource
    *           a resource
    * @return a set containing all resource contents
    */
   public static Set<EObject> getAllContentsSet(final Resource resource) {
      return EcoreBridge.getAllContentsSet(resource.getAllContents());
   }

   /**
    * Saves the given eObject as the only content of the model at the given URI.<br/>
    * <br/>
    * <b>Attention</b>: If a resource already exists at the given URI it will be overwritten!
    *
    * @param eObject
    *           the new root element
    * @param resource
    *           the resource of which the content should be replaced or created
    * @throws IOException
    *            if an error occurred during saving
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
    *           the resource to be saved
    * @throws IOException
    *            if an error occurred during saving
    */
   public static void saveResource(final Resource resource) throws IOException {
      resource.save(Collections.EMPTY_MAP);
      resource.setModified(true);
   }
}
