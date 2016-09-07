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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.language.java.JavaClasspath;
import org.emftext.language.java.JavaUniquePathConstructor;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.resource.ClassFileModelLoader;
import org.emftext.language.java.resource.JavaSourceOrClassFileResource;
import org.emftext.language.java.resource.java.IJavaInputStreamProcessorProvider;
import org.emftext.language.java.resource.java.IJavaOptions;
import org.emftext.language.java.resource.java.IJavaReferenceResolverSwitch;
import org.emftext.language.java.resource.java.IJavaTextPrinter;
import org.emftext.language.java.resource.java.mopp.JavaInputStreamProcessor;
import org.emftext.language.java.resource.java.util.JavaUnicodeConverter;
import org.emftext.language.java.util.JavaModelCompletion;

/**
 * This is a revised version of the JaMoPP {@link JavaSourceOrClassFileResource}
 * , which avoids model modifications during save that are caused by updating
 * layout information in the original version. Furthermore the load procedure is
 * optimized, so the compilation unit name is set before inserting the
 * compilation unit into the resource to avoid exceptions due to resource
 * modifications.
 * 
 * @author Heiko Klare
 *
 */
public class JavaSourceFileWithoutConcurrentWriteResource extends JavaSourceOrClassFileResource {
	private Object loadingLock = new Object();
	private org.emftext.language.java.resource.java.IJavaTextParser parser;
	// -- BEGIN MODIFICATION --
	// Use the revise JavaLayoutUtil that avoids modifications during saving the
	// model
	private org.emftext.language.java.resource.java.util.JavaLayoutUtil layoutUtil = new JavaLayoutUtilWithoutModelModification();
	// -- END MODIFiCATION

	public JavaSourceFileWithoutConcurrentWriteResource(URI uri) {
		super(uri);
	}

	/*
	 * This method is copied from the JavaSourceOrClassFileResource, removing
	 * code that modifies the model and using the overwritten JavaLayoutUtil
	 */
	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		if (isClassFile()) {
			// save not supported
			return;
		}

		ResourceSet resourceSetForSave = getResourceSet();
		if (resourceSetForSave == null) {
			resourceSetForSave = new ResourceSetImpl();
		}

		if (containsMultipleCompilationUnits()) {
			for (EObject eObject : new BasicEList<EObject>(getContentsInternal())) {
				if (eObject instanceof CompilationUnit) {
					CompilationUnit cu = (CompilationUnit) eObject;
					if (cu.getClassifiers().isEmpty()) {
						continue;
					}

					String[] folder = cu.getNamespaces().toArray(new String[cu.getNamespaces().size()]);
					String file = cu.getClassifiers().get(0).getName();

					URI normalizedURI = resourceSetForSave.getURIConverter().normalize(getURI());

					URI subResourcURI = normalizedURI.trimFileExtension().trimFileExtension();

					if (normalizedURI.segmentCount() >= folder.length + 1
							&& normalizedURI.segmentsList().subList(normalizedURI.segmentCount() - 1 - folder.length, normalizedURI.segmentCount() - 1).equals(Arrays.asList(folder))) {
						subResourcURI = subResourcURI.trimSegments(1);
					} else {
						subResourcURI = subResourcURI.appendSegments(folder);
					}
					subResourcURI = subResourcURI.appendSegment(file);
					subResourcURI = subResourcURI.appendFileExtension("java");

					Resource subResource = resourceSetForSave.createResource(subResourcURI);

					addPackageDeclaration(cu);

					subResource.getContents().add(cu);
					subResource.save(options);
				} else {
					// nothing
				}
			}
		} else {
			if (!getContentsInternal().isEmpty()) {
				if (getContentsInternal().get(0) instanceof CompilationUnit) {
					CompilationUnit cu = (CompilationUnit) getContentsInternal().get(0);
					addPackageDeclaration(cu);
				}
				// super.doSave(outputStream, options);
				IJavaTextPrinter printer = getMetaInformation().createPrinter(outputStream, JavaSourceFileWithoutConcurrentWriteResource.this);
				IJavaReferenceResolverSwitch referenceResolverSwitch = getReferenceResolverSwitch();
				printer.setEncoding(getEncoding(options));
				referenceResolverSwitch.setOptions(options);
				EObject root = getContentsInternal().get(0); // only print the
																// single CU or
																// Package
				if (isLayoutInformationRecordingEnabled()) {
					layoutUtil.transferAllLayoutInformationFromModel(root);
				}
				printer.print(root);

				// -- BEGIN MODIFICATION --
				/*
				 * if (isLayoutInformationRecordingEnabled()) { new
				 * JavaLayoutUtilWithoutWrite()
				 * .transferAllLayoutInformationToModel(root); }
				 */
				// -- END MODIFICATION --
			}
		}
	}

	/*
	 * This method is copied from the JavaSourceOrClassFileResource, avoiding
	 * setting the compilation unit name again if it is already correct.
	 */
	@Override
	protected void setCompilationUnitName(CompilationUnit cu) {
		String packageName = "";
		if (!hasJavaClassifierURI()) {
			// physical URIs do not include the package name
			// so we construct it from the cu's namespaces
			packageName = JavaUniquePathConstructor.packageName(cu);
		}
		String fileName = getURI().lastSegment();
		if (!"".equals(packageName)) {
			if (!(packageName + "." + fileName).equals(cu.getName())) {
				cu.setName(packageName + "." + fileName);
			}
		} else if (!fileName.equals(cu.getName())) {
			cu.setName(fileName);
		}
	}

	/*
	 * This method is copied from the JavaSourceOrClassFileResource. It calls a
	 * new implementation of the doLoad method other than the super method of
	 * JavaResource.
	 */
	@Override
	protected void doLoad(java.io.InputStream inputStream, java.util.Map<?, ?> options) throws java.io.IOException {
		if (getURI().toString().startsWith(JavaUniquePathConstructor.JAVA_CLASSIFIER_PATHMAP)) {
			super.doLoad(inputStream, options);
			return;
		}

		if (isClassFile()) {
			JavaClasspath javaClasspath = JavaClasspath.get(this);
			ClassFileModelLoader classFileParser = new ClassFileModelLoader(javaClasspath);
			CompilationUnit cu = classFileParser.parse(inputStream, getURI().lastSegment());
			getContentsInternal().add(cu);
			JavaModelCompletion.complete(this);
		} else {
			Map<Object, Object> optionsWithUnicodeConverter = new LinkedHashMap<Object, Object>();
			if (options != null) {
				optionsWithUnicodeConverter.putAll(options);
			}
			if (!optionsWithUnicodeConverter.containsKey(IJavaOptions.INPUT_STREAM_PREPROCESSOR_PROVIDER)) {
				optionsWithUnicodeConverter.put(IJavaOptions.INPUT_STREAM_PREPROCESSOR_PROVIDER, new IJavaInputStreamProcessorProvider() {

					public JavaInputStreamProcessor getInputStreamProcessor(InputStream inputStream) {
						return new JavaUnicodeConverter(inputStream);
					}
				});
			}
			// -- BEGIN MODIFICATION --
			doLoadJavaFile(inputStream, optionsWithUnicodeConverter);
			// replaced: super.doLoad(...);
			// -- END MODIFICATION --
			if (getContentsInternal().isEmpty() && getErrors().isEmpty()) {
				contents.add(ContainersFactory.eINSTANCE.createEmptyModel());
			}
		}
	}

	/*
	 * This method is copied from JavaResource. It sets the compilation unit
	 * name right after parsing instead of setting it after inserting the
	 * complete model into the resource.
	 */
	private void doLoadJavaFile(java.io.InputStream inputStream, java.util.Map<?, ?> options) throws java.io.IOException {
		synchronized (loadingLock) {
			if (processTerminationRequested()) {
				return;
			}
			resetLocationMap();
			String encoding = getEncoding(options);
			java.io.InputStream actualInputStream = inputStream;
			Object inputStreamPreProcessorProvider = null;
			if (options != null) {
				inputStreamPreProcessorProvider = options.get(org.emftext.language.java.resource.java.IJavaOptions.INPUT_STREAM_PREPROCESSOR_PROVIDER);
			}
			if (inputStreamPreProcessorProvider != null) {
				if (inputStreamPreProcessorProvider instanceof org.emftext.language.java.resource.java.IJavaInputStreamProcessorProvider) {
					org.emftext.language.java.resource.java.IJavaInputStreamProcessorProvider provider = (org.emftext.language.java.resource.java.IJavaInputStreamProcessorProvider) inputStreamPreProcessorProvider;
					org.emftext.language.java.resource.java.mopp.JavaInputStreamProcessor processor = provider.getInputStreamProcessor(inputStream);
					actualInputStream = processor;
				}
			}

			parser = getMetaInformation().createParser(actualInputStream, encoding);
			parser.setOptions(options);
			org.emftext.language.java.resource.java.IJavaReferenceResolverSwitch referenceResolverSwitch = getReferenceResolverSwitch();
			referenceResolverSwitch.setOptions(options);
			org.emftext.language.java.resource.java.IJavaParseResult result = parser.parse();
			// dispose parser, we don't need it anymore
			parser = null;

			if (processTerminationRequested()) {
				// do nothing if reload was already restarted
				return;
			}

			clearState();
			getContentsInternal().clear();
			org.eclipse.emf.ecore.EObject root = null;
			if (result != null) {
				root = result.getRoot();
				if (root != null) {
					// -- BEGIN MODIFICATION --
					if (root instanceof CompilationUnit) {
						setCompilationUnitName((CompilationUnit) root);
					}
					// -- END MODIFICATION --
					if (isLayoutInformationRecordingEnabled()) {
						layoutUtil.transferAllLayoutInformationToModel(root);
					}
					if (processTerminationRequested()) {
						// the next reload will add new content
						return;
					}
					getContentsInternal().add(root);
				}
				java.util.Collection<org.emftext.language.java.resource.java.IJavaCommand<org.emftext.language.java.resource.java.IJavaTextResource>> commands = result.getPostParseCommands();
				if (commands != null) {
					for (org.emftext.language.java.resource.java.IJavaCommand<org.emftext.language.java.resource.java.IJavaTextResource> command : commands) {
						command.execute(this);
					}
				}
			}
			getReferenceResolverSwitch().setOptions(options);
			if (getErrors().isEmpty()) {
				if (!runPostProcessors(options)) {
					return;
				}
				if (root != null) {
					runValidators(root);
				}
			}
			notifyDelayed();
		}
	}

}
