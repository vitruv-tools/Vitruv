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

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;

/**
 * This class extends the revised JaMoPP
 * {@link JavaSourceFileWithoutConcurrentWriteResource} disabling the validators
 * to avoid a negative validation with proxy resolution turned off.
 * 
 * @author Heiko Klare
 * 
 */
public class JavaSourceFileWithoutValidationResource extends JavaSourceFileWithoutConcurrentWriteResource {
	private static final Logger logger = LogManager.getLogger(JavaSourceFileWithoutValidationResource.class);

	public JavaSourceFileWithoutValidationResource(URI uri) {
		super(uri);
		// logger.setLevel(Level.INFO);
	}

	@Override
	public void save(Map<?, ?> options) throws IOException {
		logger.info("Saving java file: " + uri);
		super.save(options);
	}

	@Override
	public void load(Map<?, ?> options) throws IOException {
		logger.info("Loading java file: " + uri);
		super.load(options);
	}

	protected void runValidators(org.eclipse.emf.ecore.EObject root) {
		// Overwrite this method with empty content to avoid validation, especially of dependant resources.
	}

}
