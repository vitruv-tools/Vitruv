/*******************************************************************************
 * Copyright (c) 2015 Heiko Klare (Karlsruhe Institute of Technology)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Heiko Klare - initial API and implementation and/or initial documentation
 *******************************************************************************/ 

package tools.vitruvius.views.java.projumled4j.editor.diagramGeneration.jamoppExtension;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.common.tools.api.resource.ResourceSetFactory;

/**
 * This factory extends the standard {@link ResourceSetFactory} by registering
 * factories for java and package resources. It is intended to be used by Sirius
 * for loading semantic resources with the correct resource factories.
 * 
 * @author Heiko Klare
 *
 */
public class SiriusResourceSetFactory extends ResourceSetFactory {
	@Override
	public ResourceSet createResourceSet(URI uri) {
		ResourceSet resSet = super.createResourceSet(uri);
		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("java", new JavaSourceFileWithoutValidationResourceFactory());
		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("package", new PackageResourceFactory());
		return resSet;
	}
}
