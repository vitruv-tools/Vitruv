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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.language.java.resource.JavaSourceOrClassFileResource;
import org.emftext.language.java.resource.JavaSourceOrClassFileResourceFactoryImpl;

/**
 * Factory for {@link JavaSourceFileWithoutValidationResource}, a revised
 * version of the JaMoPP {@link JavaSourceOrClassFileResource}.
 * 
 * @author Heiko Klare
 * 
 */
public class JavaSourceFileWithoutValidationResourceFactory extends JavaSourceOrClassFileResourceFactoryImpl {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(JavaSourceFileWithoutValidationResourceFactory.class);

	public Resource createResource(URI uri) {
		return new JavaSourceFileWithoutValidationResource(uri);
	}

}