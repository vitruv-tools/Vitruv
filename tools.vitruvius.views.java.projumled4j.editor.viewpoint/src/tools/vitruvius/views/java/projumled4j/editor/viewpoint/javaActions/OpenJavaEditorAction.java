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

package tools.vitruvius.views.java.projumled4j.editor.viewpoint.javaActions;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DNodeContainerSpec;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.emftext.language.java.classifiers.Class;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.ResourcesPlugin;

/**
 * This action has to be called in the context of JaMoPP class node. In that case a java editor
 * for the corresponding file is opened inside the eclipse workbench.
 * 
 * @author Heiko Klare
 */
@SuppressWarnings("restriction")
public class OpenJavaEditorAction implements IExternalJavaAction {
	private static final Logger logger = LogManager.getLogger(OpenJavaEditorAction.class);
	
	@Override
	public boolean canExecute(Collection<? extends EObject> arg0) {
		return true;
	}

	@Override
	public void execute(Collection<? extends EObject> objects, Map<String, Object> parameters) {
		// Determine if there is a sirius node with a class as the target in the given collection
		if (objects.iterator().hasNext()) {
			EObject object = objects.iterator().next();
			if (object instanceof DNodeContainerSpec && ((DNodeContainerSpec)object).getTarget() instanceof Class) {
				// The class is a platform resource, so combine its location with the workspace location
				Class classObject = (Class) ((DNodeContainerSpec)object).getTarget();
				URI classURI = classObject.eResource().getURI();
				String file = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + classURI.toPlatformString(true);
				logger.info("Opening: " + file);
				File fileToOpen = new File(file);
				openJavaEditor(fileToOpen);
			}
		}
	}
	
	private void openJavaEditor(File fileToOpen) {
		if (fileToOpen.exists() && fileToOpen.isFile()) {
		    IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
		    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		    try {
		        IDE.openEditorOnFileStore( page, fileStore );
		    } catch (PartInitException e) {
		    }
		}
	}

}
