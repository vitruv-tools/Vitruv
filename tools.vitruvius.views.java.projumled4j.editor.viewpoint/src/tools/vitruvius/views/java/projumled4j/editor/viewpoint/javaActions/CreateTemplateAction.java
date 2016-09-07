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
 * This abstract action has to be called in the context of a JaMoPP package. In that case a
 * template file is created depending on the concrete implementation in a subclass.
 * 
 * @author Heiko Klare
 */
public abstract class CreateTemplateAction implements IExternalJavaAction {
	private static final Logger logger = LogManager.getLogger(CreateTemplateAction.class);
	
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
				createTemplateFile(pckg);
			}
		}
	}

	protected abstract void createTemplateFile(Package pckg);
	
	protected void createTemplateFile(Package pckg, String typeName) {
		IFolder packageFolder = getPackageFolder(pckg);
		String typeInstanceName = promptForJavaFileNameInPackage(packageFolder, typeName);
		IFile javaFile = createJavaFile(packageFolder, pckg.getName(), typeName, typeInstanceName);
		if (javaFile == null) {
			return;
		}
		addJavaClassToPackage(pckg, javaFile);
		logger.info("Creating: " + javaFile);
	}

	protected IFolder getPackageFolder(Package pckg) {
		URI packageURI = pckg.eResource().getURI();
		String pathWithoutDummyExtension = packageURI.path().substring(0, packageURI.path().lastIndexOf("."));
		IFolder packageFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(pathWithoutDummyExtension.substring(pathWithoutDummyExtension.indexOf("/", 1))));
		return packageFolder;
	}

	protected static void addJavaClassToPackage(Package pckg, IFile javaFile) {
		Session session = SessionManager.INSTANCE.getSession(pckg);
		URI classURI = URI.createPlatformResourceURI(javaFile.getFullPath().toString(), true);
		AddSemanticResourceCommand newClassSemanticResourceCmd = new AddSemanticResourceCommand(session, classURI , new NullProgressMonitor());
		session.getTransactionalEditingDomain().getCommandStack().execute(newClassSemanticResourceCmd);
		pckg.getCompilationUnits().add((CompilationUnit)session.getTransactionalEditingDomain().getResourceSet().getResource(classURI, true).getContents().get(0));
	}

	protected IFile createJavaFile(IFolder packageFolder, String packageName, String typeName, String typeInstanceName) {
		IFile javaFile = null;
		try {
			String emptyTypeContent = createEmptyJavaFileContent(packageName, typeName, typeInstanceName);
			javaFile = packageFolder.getFile(typeInstanceName + ".java");
			javaFile.create(new ByteArrayInputStream(emptyTypeContent.getBytes()), true, new NullProgressMonitor());
		} catch (CoreException e) {
			return null;
		}
		return javaFile;
	}
		
	protected static String promptForJavaFileNameInPackage(IFolder packageFolder, String typeName) {
		String camelCaseTypeName = Character.toUpperCase(typeName.charAt(0)) + typeName.substring(1);
		String promptString = "\nName for the new " + camelCaseTypeName + ":";
		String newName = null;
		do {
			newName = (String)JOptionPane.showInputDialog(null, promptString, "Create New " + camelCaseTypeName, JOptionPane.PLAIN_MESSAGE, null, null, "New" + camelCaseTypeName);
			if (newName == null || newName.length() == 0) {
				return null;
			}
			promptString = camelCaseTypeName + " " + newName + " already exists.\nName for the new " + camelCaseTypeName + ":";
		} while (packageFolder.getFile(newName + ".java").exists());
		return newName;
	}
	
	protected String createEmptyJavaFileContent(String packageName, String typeName, String typeInstanceName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("package " + packageName + ";\n");
		buffer.append("\n");
		buffer.append("public " + typeName + " " + typeInstanceName + " {\n");
		buffer.append("\n");
		buffer.append("}");
		
		return buffer.toString();
	}

}