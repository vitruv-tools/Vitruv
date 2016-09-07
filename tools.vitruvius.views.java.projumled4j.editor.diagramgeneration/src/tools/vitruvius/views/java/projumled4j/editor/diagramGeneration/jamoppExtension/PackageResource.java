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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.FileInfoMatcherDescription;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceFilterDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;

import tools.vitruvius.framework.util.bridges.EMFBridge;

/**
 * This resource represents a java package as a JaMoPP model. It expects the
 * package location with a "package" extension in the {@link URI}. Java files in
 * the specified package folder are inserted into the package resource. Because
 * this is an in-memory resource saving this resource will not do anything. The
 * java resources inside this package can be saved.
 * 
 * @author Heiko Klare
 *
 */
public class PackageResource extends ResourceImpl {
	private static final Logger logger = LogManager.getLogger(PackageResource.class);

	public PackageResource(URI uri) {
		super(uri);
		isLoading = false;
		// logger.setLevel(Level.INFO);
	}

	@Override
	protected void doLoad(InputStream stream, Map<?, ?> options) throws IOException {
		load();
	}

	@Override
	public void load(Map<?, ?> options) throws IOException {
		load();
	}

	private void load() {
		logger.info("Loading package: " + uri);
		if (isLoading) {
			return;
		}
		isLoading = true;

		if (!this.getContents().isEmpty()) {
			this.getContents().clear();
		}

		// TODO Determine the correct package structure instead of assuming that
		// we have a pure java project with source files only in "src/"
		String pathWithoutDummyExtension = uri.path().substring(0, uri.path().lastIndexOf("."));
		Package javaPackage = ContainersFactory.eINSTANCE.createPackage();
		javaPackage.setName(pathWithoutDummyExtension.substring(pathWithoutDummyExtension.indexOf("src/") + 4).replace("/", "."));

		IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(pathWithoutDummyExtension.substring(pathWithoutDummyExtension.indexOf("/", 1))));
		addJavaFilesToPackage(folder, javaPackage);

		if (this.getContents().isEmpty() || !(this.getContents().get(0) instanceof Package)) {
			this.getContents().add(javaPackage);
		}

		setLoaded(true);
		isLoading = false;
	}

	private void addJavaFilesToPackage(IFolder folder, Package javaPackage) {
		logger.info("Loading files in folder: " + folder.getFullPath());
		try {
			folder.createFilter(IResourceFilterDescription.INCLUDE_ONLY, new FileInfoMatcherDescription("java", "java"), IResource.BACKGROUND_REFRESH, new NullProgressMonitor());
			IResource[] members = folder.members();
			for (IResource potentialSourceFile : members) {
				addJavaFileToPackage(potentialSourceFile, javaPackage);
			}
		} catch (CoreException e) {
			logger.error("Could not load source files in folder: " + folder);
			throw new IllegalStateException("Could not load source files.");
		}

	}

	private void addJavaFileToPackage(IResource javaFileResource, Package javaPackage) {
		logger.info("Loading file: " + javaFileResource.getFullPath());
		if (javaFileResource.getType() == IResource.FILE && javaFileResource.getFileExtension().equals("java")) {
			Resource sourceRes = resourceSet.getResource(EMFBridge.getEMFPlatformUriForIResource(javaFileResource), true);
			if (!sourceRes.getContents().isEmpty() && sourceRes.getContents().get(0) instanceof CompilationUnit) {
				javaPackage.getCompilationUnits().add((CompilationUnit) sourceRes.getContents().get(0));
			}
		} else if (javaFileResource.getType() == IResource.FOLDER) {
			// TODO add subpackages
		}
	}

	@Override
	public void save(Map<?, ?> optioos) {
		// Do nothing, because this is an in-memory-only resource. The inner
		// java resources can be saved.
	}

	@Override
	public void doSave(OutputStream stream, Map<?, ?> options) throws IOException {
		// Do nothing, because this is an in-memory-only resource. The inner
		// java resources can be saved.
	}
}
