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

package tools.vitruvius.views.java.projumled4j.annotations;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class ClasspathInjection {
	private static final String PLUGIN_ID = "tools.vitruvius.views.java.projumled4j.annotations";
	private static final String CLASS_FOLDER_PATH = "/bin";
	private static final Logger logger = LogManager.getLogger(ClasspathInjection.class);
	
	private static URL getClassFolderURL() {
		return FileLocator.find(Platform.getBundle(PLUGIN_ID), new Path(CLASS_FOLDER_PATH), null); 
	}
	
	public static void addAnnotationsToClasspath(IJavaProject project) throws JavaModelException {
		String annotationsPath = null;
		try {
			annotationsPath = new File(FileLocator.resolve(getClassFolderURL()).getFile()).toString();
		} catch (IOException e) {
		}
		
		if (annotationsPath == null) {
			logger.error("Something went wrong generation the path for the annotation classes.");
			throw new IllegalStateException("Path to annotations folder could not be generated.");
		}
		
		IClasspathEntry[] currentClasspath = project.getRawClasspath();
		IClasspathEntry[] newClasspath = Arrays.copyOf(currentClasspath, currentClasspath.length + 1);
		IClasspathEntry newClasspathEntry = JavaCore.newLibraryEntry(new Path(annotationsPath), null, null, false);
		newClasspath[newClasspath.length - 1] = newClasspathEntry;
		
		// Check if the classpath entry already exists, then we do not have to add it again
		boolean alreadyExisting = false;
		for (IClasspathEntry entry : currentClasspath) {
			if (entry.equals(newClasspathEntry)) {
				alreadyExisting = true;
			}
		}
		
		if (!alreadyExisting) {
			logger.info("Add annotations to classpath: " + annotationsPath);
			project.getJavaProject().setRawClasspath(newClasspath, new NullProgressMonitor());
			/*try {
				javaPackage.getJavaProject().getProject().refreshLocal(IResource.DEPTH_INFINITE, progressMonitor);
			} catch (CoreException e) {
				logger.error("Project could not be refreshed.");
			}*/
		}
	}
}
