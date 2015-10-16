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

package edu.kit.ipd.sdq.vitruvius.javauml.editor.viewpoint.javaActions;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Map;

import javax.swing.JOptionPane;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.tools.api.command.semantic.AddSemanticResourceCommand;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;

/**
 * This action has to be called in the context of a JaMoPP package. In that case the user is
 * asked to specify a name for a new class that will be created in the Java package the
 * JaMoPP package is representing.
 * 
 * @author Heiko Klare
 */
public class CreateClassAction implements IExternalJavaAction {
	private static final Logger logger = LogManager.getLogger(CreateClassAction.class);
	
	@Override
	public boolean canExecute(Collection<? extends EObject> arg0) {
		return true;
	}

	@Override
	public void execute(Collection<? extends EObject> objects, Map<String, Object> parameters) {
		// Determine if there is a sirius node with a class as the target in the given collection
		if (objects.iterator().hasNext()) {
			EObject object = objects.iterator().next();
			if (object instanceof Package) {
				Package pckg = (Package) object;
				IFolder packageFolder = getPackageFolder(pckg);
				String newClassName = promptForClassName(packageFolder);
				IFile javaFile = createEmptyJavaClass(packageFolder, pckg.getName(), newClassName);
				if (javaFile == null) {
					return;
				}
				addJavaClassToPackage(pckg, javaFile);
				logger.info("Creating: " + javaFile);
			}
		}
	}

	private IFolder getPackageFolder(Package pckg) {
		URI packageURI = pckg.eResource().getURI();
		String pathWithoutDummyExtension = packageURI.path().substring(0, packageURI.path().lastIndexOf("."));
		IFolder packageFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(pathWithoutDummyExtension.substring(pathWithoutDummyExtension.indexOf("/", 1))));
		return packageFolder;
	}

	private void addJavaClassToPackage(Package pckg, IFile javaFile) {
		Session session = SessionManager.INSTANCE.getSession(pckg);
		URI classURI = URI.createPlatformResourceURI(javaFile.getFullPath().toString(), true);
		AddSemanticResourceCommand newClassSemanticResourceCmd = new AddSemanticResourceCommand(session, classURI , new NullProgressMonitor());
		session.getTransactionalEditingDomain().getCommandStack().execute(newClassSemanticResourceCmd);
		pckg.getCompilationUnits().add((CompilationUnit)session.getTransactionalEditingDomain().getResourceSet().getResource(classURI, true).getContents().get(0));
	}

	private IFile createEmptyJavaClass(IFolder packageFolder, String packageName, String newClassName) {
		String javaFileContent = createEmptyJavaFileContent(packageName, newClassName);
		IFile javaFile = null;
		try {
			javaFile = packageFolder.getFile(newClassName + ".java");
			javaFile.create(new ByteArrayInputStream(javaFileContent.getBytes()), true, new NullProgressMonitor());
		} catch (CoreException e) {
			return null;
		}
		return javaFile;
	}
		
	private String promptForClassName(IFolder packageFolder) {
		String promptString = "\nName for the new class:";
		String newClassName = null;
		do {
			newClassName = (String)JOptionPane.showInputDialog(null, promptString, "Create New Class", JOptionPane.PLAIN_MESSAGE, null, null, "NewClass");
			if (newClassName == null || newClassName.length() == 0) {
				return null;
			}
			promptString = "Class " + newClassName + " already exists.\nName for the new class:";
		} while (packageFolder.getFile(newClassName + ".java").exists());
		return newClassName;
	}
	
	private String createEmptyJavaFileContent(String packageName, String className) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("package " + packageName + ";\n");
		buffer.append("\n");
		buffer.append("public class " + className + " {\n");
		buffer.append("\n");
		buffer.append("}");
		
		return buffer.toString();
	}
}
