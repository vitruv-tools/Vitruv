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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;

/**
 * A utility class hiding details of the Eclipse Modeling Framework API for
 * recurring tasks that are not project-specific. Methods for Ecore metamodels,
 * Ecore metamodel creation, Ecore metamodel variants and Ecore resources should
 * not be put in this utility but in the corresponding separate utilities!<br/>
 * <br/>
 * (Note that it is disputable whether this class conforms to the bridge pattern
 * as we are currently only providing one implementation and the "abstractions"
 * can be regarded as low-level.)
 *
 * @author Max E. Kramer
 *
 * @see EcoreBridge
 * @see EcoreFactoryBridge
 * @see EcorePkgVariantsBridge
 * @see EcoreResourceBridge
 */
public final class EMFBridge {
	/** Utility classes should not have a public or default constructor. */
	private EMFBridge() {
	}

	/**
	 * Creates and returns an EMF platform resource URI for the given Eclipse
	 * resource.
	 *
	 * @param iResource
	 *            an Eclipse resource
	 * @return a platform resource URI for the resource
	 */
	public static URI getEMFPlatformUriForIResource(final IResource iResource) {
		return URI.createPlatformResourceURI(iResource.getFullPath().toString(), true);
	}

	/**
	 * Creates and returns a new Eclipse path for the given EMF URI.
	 *
	 * @param uri
	 *            an EMF URI
	 * @return a new Eclipse path for the given URI
	 */
	public static IPath getIPathForEMFUri(final URI uri) {
		return new Path(uri.toPlatformString(true));
	}

	/**
	 * Returns an Eclipse file for the given EMF URI.
	 *
	 * @param uri
	 *            an EMF URI
	 * @return an Eclipse file for the given URI
	 */
	public static IFile getIFileForEMFUri(final URI uri) {
		IPath path = getIPathForEMFUri(uri);
		return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
	}

	/**
	 * Creates a new URI from the given URI by appending the given string to the
	 * file name of the URI (i.e. before the dot "." and the file extension)
	 *
	 * @param uri
	 *            an URI
	 * @param toAppend
	 *            the string to be appended to the file name of the URI
	 * @return the new URI
	 */
	public static URI newURIWithStringAppendedToFilename(final URI uri, final String toAppend) {
		String fileExt = uri.fileExtension();
		if (fileExt != null) {
			URI uriWithoutFileExt = uri.trimFileExtension();
			String resultFileName = uriWithoutFileExt.lastSegment() + toAppend;
			return uriWithoutFileExt.trimSegments(1).appendSegment(resultFileName).appendFileExtension(fileExt);
		} else {
			throw new RuntimeException("The uri '" + uri + "' has no file extension so '" + toAppend
					+ "' cannot be appended before the file extension!");
		}

	}

	public static URI createPlatformResourceURI(final String pathAfterPlatformResource) {
		return URI.createPlatformResourceURI(pathAfterPlatformResource, true);
	}

	/**
	 * Creates a URI from the passed uriString by adding the platform resource
	 * prefix if no absolute web prefix (http://) and no absolute file prefix (/
	 * or X:\) is specified.
	 *
	 * @param uriString
	 * @return the new URI
	 */
	public static URI createURI(String uriString) {
		if (uriString != null) {
			if (!uriString.startsWith(VitruviusConstants.getPlatformResourcePrefix())) {
				if (startsWithWindowsDriveLetterColonBackslash(uriString) || uriString.startsWith("/")) {
					return URI.createFileURI(uriString);
				} else if (!uriString.startsWith("http://")) {
					uriString = VitruviusConstants.getPlatformResourcePrefix() + uriString;
				}
			}
		}
		return URI.createURI(uriString);
	}

	private static boolean startsWithWindowsDriveLetterColonBackslash(final String uriString) {
		char firstChar = uriString.charAt(0);
		return Character.isLetter(firstChar) && uriString.regionMatches(1, ":\\", 0, 2);
	}

	public static IFolder createFolderInProjectIfNecessary(IProject project, String folderName) {
		IFolder folder = project.getFolder(folderName);
		if (!folder.exists()) {
			try {
				folder.create(true, true, new NullProgressMonitor());
			} catch (CoreException e) {
				// soften
				throw new RuntimeException(e);
			}
		}
		return folder;
	}
}
