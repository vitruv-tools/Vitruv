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

package edu.kit.ipd.sdq.vitruvius.casestudies.projumled4j.editor.diagramGeneration;

import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.language.java.annotations.AnnotationInstance;
import org.emftext.language.java.annotations.AnnotationsFactory;
import org.emftext.language.java.classifiers.Annotation;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.imports.ImportsFactory;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;

/**
 * This class provides functionality for generating {@link edu.kit.ipd.sdq.vitruvius.javauml.annotations.Assocation} annotations
 * based on the JaMoPP model of the compilation unit. If the import for the association does not already exist for the affected
 * compilation unit it is added as well.
 *  
 * @author Heiko Klare
 */
public class AssociationGenerator {
	private ResourceSet resourceSet;
	private Logger logger = LogManager.getLogger(AssociationGenerator.class);
	private static final String ANNOTATIONS_PACKAGE = "edu.kit.ipd.sdq.vitruvius.casestudies.projumled4j.annotations";
	private static final String ASSOCIATION_CLASS_FILE = "Association.java";
	private static final String JAMOPP_CLASSPATH_PREFIX = "pathmap:/javaclass/";
	
	public static final URI getAssociationAnnotationUri() {
		return URI.createURI(JAMOPP_CLASSPATH_PREFIX + ANNOTATIONS_PACKAGE + "." + ASSOCIATION_CLASS_FILE, true);
	}
	
	AssociationGenerator() {
		this.resourceSet = new ResourceSetImpl();
		logger.setLevel(Level.INFO);
	}
	
	private CompilationUnit getCompilationUnit(URI compilationUnitUri) {
		Resource potentialClassResource = resourceSet.getResource(compilationUnitUri, true);
		if (potentialClassResource.getContents().get(0) instanceof CompilationUnit) {
			return (CompilationUnit) potentialClassResource.getContents().get(0);
		}
		return null;
	}
	
	private Class getClassInCompilationUnit(URI compilationUnitUri) {
		CompilationUnit compilationUnit = getCompilationUnit(compilationUnitUri);
		if (compilationUnit != null && compilationUnit.getClassifiers().get(0) instanceof Class) {
			return (Class) compilationUnit.getClassifiers().get(0);
		}
		return null;
	}
	
	public void createAssociationsInsidePackageInCompilationUnit(URI compilationUnitUri) throws IOException {
		Class containedClass = getClassInCompilationUnit(compilationUnitUri);
		if (containedClass == null) {
			return;
		}
		
		boolean createdNewAssociation = false;
		for (Field field : containedClass.getFields()) {
			createdNewAssociation |= createAssociationInsidePackage(field);
		}
		
		if (createdNewAssociation) {
			addAssociationImport(compilationUnitUri);
			resourceSet.getResource(compilationUnitUri, true).save(null);
		}
	}
	
	private void addAssociationImport(URI compilationUnitUri) {
		CompilationUnit compilationUnit = getCompilationUnit(compilationUnitUri);
		if (compilationUnit != null && !hasCompilationUnitAssociationImport(compilationUnit)) {
			Annotation associationAnnotation = getAssociationAnnotation();
			ClassifierImport associationAnnotationImport = ImportsFactory.eINSTANCE.createClassifierImport();
			associationAnnotationImport.setClassifier(associationAnnotation);
			associationAnnotationImport.getNamespaces().addAll(Arrays.asList(ANNOTATIONS_PACKAGE.split("\\.")));
			compilationUnit.getImports().add(associationAnnotationImport);
		}
	}

	private boolean hasCompilationUnitAssociationImport(CompilationUnit compilationUnit) {
		Annotation associationAnnotation = getAssociationAnnotation();
		for (Import imp: compilationUnit.getImports()) {
			if (imp instanceof ClassifierImport && ((ClassifierImport) imp).getClassifier().equals(associationAnnotation)) {
				return true;
			}
		}
		return false;
	}

	private boolean createAssociationInsidePackage(Field field) {
		if (isFieldAssociation(field)) {
			return false;
		}
		
		Classifier referencedClassifier = getReferencedClassifier(field.getTypeReference());
		if (referencedClassifier == null) {
			return false;
		}
		
		logger.info("Field " + field.getName() + " references class " + referencedClassifier.getName() 
			+ " (package " + referencedClassifier.getContainingPackageName().get(0) + ")");
		Annotation associationAnnotation = getAssociationAnnotation();
		AnnotationInstance annotationInstance = AnnotationsFactory.eINSTANCE.createAnnotationInstance();
		annotationInstance.setAnnotation(associationAnnotation);
		field.getAnnotationsAndModifiers().add(0, annotationInstance);
		return true;
	}

	private Classifier getReferencedClassifier(TypeReference typeReference) {
		if (typeReference instanceof NamespaceClassifierReference) {
			NamespaceClassifierReference reference = (NamespaceClassifierReference) typeReference; 
			Type referenceType = reference.getTarget();
			if (referenceType instanceof Classifier) {
				return (Classifier) referenceType;
			}
		}
		return null;
	}
	
	private Annotation getAssociationAnnotation() {
		Resource associationResource = resourceSet.getResource(getAssociationAnnotationUri(), true);
		if (associationResource.getContents().get(0) instanceof CompilationUnit) {
			CompilationUnit associationCompilationUnit = (CompilationUnit) associationResource.getContents().get(0);
			if (associationCompilationUnit.getClassifiers().get(0) instanceof Annotation) {
				return (Annotation) associationCompilationUnit.getClassifiers().get(0);
			}
		}
		throw new IllegalStateException("Association annotation does not exist.");
	}
	
	private boolean isFieldAssociation(Field field) {
		for (AnnotationInstanceOrModifier potentialAnnotation : field.getAnnotationsAndModifiers()) {
			if (potentialAnnotation instanceof AnnotationInstance) {
				AnnotationInstance annotationInstance = (AnnotationInstance) potentialAnnotation;
				if (annotationInstance.getAnnotation().equals(getAssociationAnnotation())) {
					return true;
				}
			}
		}
		return false;
	}
	
	
}
