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
package tools.vitruv.framework.util.bridges;

import java.io.File;
import org.eclipse.emf.common.util.URI;

import tools.vitruv.framework.util.VitruviusConstants;

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
	 * Creates and returns and EMF file URI for the given File.
	 * 
	 * @param file
	 *            - the file to get the EMF URI for
	 * @return the EMF file URI for the given file
	 */
	public static URI getEmfFileUriForFile(final File file) {
		return URI.createFileURI(file.getAbsolutePath());
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
			if (!uriString.startsWith(VitruviusConstants.getPlatformResourcePrefix())
					&& !uriString.startsWith(VitruviusConstants.getPathmapPrefix())) {
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

}
