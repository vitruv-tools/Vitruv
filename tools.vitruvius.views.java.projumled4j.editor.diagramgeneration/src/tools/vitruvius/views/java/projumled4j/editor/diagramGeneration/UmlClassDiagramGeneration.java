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

package tools.vitruvius.views.java.projumled4j.editor.diagramGeneration;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.dialect.command.CreateRepresentationCommand;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.tools.api.command.semantic.AddSemanticResourceCommand;
import org.eclipse.sirius.ui.business.api.dialect.DialectUIManager;
import org.eclipse.sirius.ui.business.api.viewpoint.ViewpointSelection;
import org.eclipse.sirius.ui.business.api.viewpoint.ViewpointSelectionCallbackWithConfimation;
import org.eclipse.sirius.ui.business.internal.commands.ChangeViewpointSelectionCommand;
import org.eclipse.sirius.ui.tools.api.project.ModelingProjectManager;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.emftext.language.java.resource.IExtendedJavaOptions;

import tools.vitruvius.framework.util.bridges.EMFBridge;
import tools.vitruvius.views.java.projumled4j.annotations.ClasspathInjection;

/**
 * This class provides functionality for opening a UML class diagram for a given
 * java package. Therefore it creates or opens a Sirius session, adds the needed
 * semantic resources to it, activates the UML class diagram viewpoint and
 * creates a Sirius representations.
 * 
 * @author Heiko Klare
 *
 */
@SuppressWarnings("restriction")
public class UmlClassDiagramGeneration {
	private static final String REPRESENTATION_DESCRIPTION_NAME = "tools.vitruvius.views.java.projumled4j.editor.viewpoint.structure.classdiagram";
	private static final String VIEWPOINT_NAME = "tools.vitruvius.views.java.projumled4j.editor.viewpoint.structure";

	private static final int WORK_LOAD_SESSION = 5;
	private static final int WORK_LOAD_PER_RESOURCE = 8;
	private static final int WORK_ACTIVATE_VIEWPOINT = 3;
	private static final int WORK_CREATE_REPRESENTATION_PER_RESOURCE = 1;
	private static final int WORK_OPEN_DIAGRAM_PER_RESOURCE = 1;

	private IPackageFragment javaPackage;
	private IProgressMonitor progressMonitor;
	private IProgressMonitor dummyProgressMonitor;

	private static final Logger logger = LogManager.getLogger(UmlClassDiagramGeneration.class);

	/**
	 * Instantiates a new {@link UmlClassDiagramGeneration} for the given java
	 * package.
	 * 
	 * @param progressMonitor
	 *            the {@link IProgressMonitor} to present the progress on
	 * @param javaPackage
	 *            the {@link IPackageFragment} to create the diagram for
	 */
	public UmlClassDiagramGeneration(IProgressMonitor progressMonitor, IPackageFragment javaPackage) {
		this.javaPackage = javaPackage;
		this.progressMonitor = progressMonitor;
		this.dummyProgressMonitor = new NullProgressMonitor();
		logger.setLevel(Level.DEBUG);
	}

	private String getPackageName() {
		IPackageFragment currentPackage = javaPackage;
		StringBuilder builder = new StringBuilder();
		while (currentPackage.getParent().exists() && currentPackage.getParent() instanceof IPackageFragment) {
			builder.insert(0, currentPackage.getParent().getElementName() + ".");
		}
		builder.append(javaPackage.getElementName());
		return builder.toString();
	}

	/**
	 * Creates the UML class diagram for the package specified in the
	 * constructor. The diagram is opened in an editor afterwards.
	 * 
	 * @throws CoreException
	 *             is thrown if something goes wrong accessing the eclipse
	 *             workspace
	 * @throws IOException 
	 */
	public void createAndOpenDiagram() throws CoreException, IOException {
		String packageName = getPackageName();
		progressMonitor.beginTask("Generate UML diagram for package: " + packageName, WORK_LOAD_SESSION + WORK_ACTIVATE_VIEWPOINT
				+ (WORK_LOAD_PER_RESOURCE + WORK_CREATE_REPRESENTATION_PER_RESOURCE + WORK_OPEN_DIAGRAM_PER_RESOURCE) * (javaPackage.getCompilationUnits().length + 1));

		ClasspathInjection.addAnnotationsToClasspath(javaPackage.getJavaProject());
		Session session = loadSiriusSession();

		DRepresentation loadedRepresentation = findRepresentation(session, packageName);
		if (loadedRepresentation == null) {
			logger.info("Create representation because it does not exist");
			loadedRepresentation = loadResourcesAndCreateRepresentation(session);
		}

		progressMonitor.subTask("Open editor for the generated diagram");
		DialectUIManager.INSTANCE.openEditor(session, loadedRepresentation, dummyProgressMonitor);

		progressMonitor.worked(WORK_OPEN_DIAGRAM_PER_RESOURCE * (javaPackage.getCompilationUnits().length + 1));
		progressMonitor.done();
		session.save(progressMonitor);
	}

	private Session loadSiriusSession() throws CoreException {
		progressMonitor.subTask("Load Sirius session");
		IFile airdFile = javaPackage.getJavaProject().getProject().getFile("representations.aird");
		URI airdFileURI = EMFBridge.getEMFPlatformUriForIResource(airdFile);
		if (!airdFile.exists()) {
			logger.info("Representations file: " + airdFile.getFullPath());
			ModelingProjectManager.INSTANCE.createLocalRepresentationsFile(javaPackage.getJavaProject().getProject(), dummyProgressMonitor);
		}

		Session session = SessionManager.INSTANCE.getSession(airdFileURI, dummyProgressMonitor);
		progressMonitor.worked((int) Math.floor(WORK_LOAD_SESSION / 2.0));
		if (!session.isOpen()) {
			session.open(dummyProgressMonitor);
		}
		progressMonitor.worked((int) Math.ceil(WORK_LOAD_SESSION / 2.0));
		return session;
	}

	private DRepresentation loadResourcesAndCreateRepresentation(Session session) throws JavaModelException, IOException {
		final String packageName = getPackageName();

		logger.info("-- RESOURCE GENERATION --");
		URI packageURI = URI.createPlatformResourceURI(javaPackage.getPath().toString() + ".package", false);
		loadJavaAndPackageResources(session, packageURI, packageName);

		logger.info("-- VIEWPOINT SELECTION --");
		activateViewpoint(session, VIEWPOINT_NAME);

		logger.info("-- REPRESENTATION CREATION --");
		EObject rootObject = session.getTransactionalEditingDomain().getResourceSet().getResource(packageURI, true).getContents().get(0);
		RepresentationDescription description = findRepresentationDescription(session, REPRESENTATION_DESCRIPTION_NAME, rootObject);
		DRepresentation createdRepresentation = createNewRepresentation(session, packageName, rootObject, description);

		return createdRepresentation;
	}

	
	private void loadJavaAndPackageResources(Session session, final URI packageURI, final String packageName) throws JavaModelException, IOException {
		progressMonitor.subTask("Load resources");
		ResourceSet resourceSet = session.getTransactionalEditingDomain().getResourceSet();
		final Map<Object, Object> loadOptions = resourceSet.getLoadOptions();
		// Disable on demand proxy resolution to avoid loading proxy objects
		// instead of the real objects in case of in-package references.
		loadOptions.put(IExtendedJavaOptions.DISABLE_ON_DEMAND_PROXY_RESOLVING, Boolean.TRUE);
		
		AssociationGenerator associationGenerator = new AssociationGenerator();
		for (ICompilationUnit folderMember : javaPackage.getCompilationUnits()) {
			URI compilationUnitURI = EMFBridge.getEMFPlatformUriForIResource(folderMember.getResource());
			associationGenerator.createAssociationsInsidePackageInCompilationUnit(compilationUnitURI);
			addJavaResourceToSession(session, folderMember);
		}
		addPackageResourceToSession(session, packageURI, packageName);

		// Re-enable proxy resolution and manually resolve all reference in our
		// resource set.
		loadOptions.remove(IExtendedJavaOptions.DISABLE_ON_DEMAND_PROXY_RESOLVING);
		EcoreUtil.resolveAll(resourceSet);
		logger.info("Semantic resources in session: " + session.getSemanticResources());
	}

	private void addPackageResourceToSession(final Session session, final URI packageURI, String packageName) {
		progressMonitor.subTask("Load package resource: " + packageName);
		addResourceToSession(session, packageURI);
	}

	private void addJavaResourceToSession(Session session, ICompilationUnit compilationUnit) {
		progressMonitor.subTask("Load java resource: " + compilationUnit.getElementName());
		URI fileURI = EMFBridge.getEMFPlatformUriForIResource(compilationUnit.getResource());
		logger.info("Add semantic resource: " + fileURI);
		addResourceToSession(session, fileURI);
	}

	private void addResourceToSession(final Session session, final URI resourceURI) {
		AddSemanticResourceCommand packageSemanticResourceCmd = new AddSemanticResourceCommand(session, resourceURI, dummyProgressMonitor);
		session.getTransactionalEditingDomain().getCommandStack().execute(packageSemanticResourceCmd);
		progressMonitor.worked(WORK_LOAD_PER_RESOURCE);
	}

	private DRepresentation createNewRepresentation(Session session, final String representationName, EObject rootObject, RepresentationDescription representationDescription)
			throws JavaModelException {
		progressMonitor.subTask("Create UML diagram representation");

		Command createViewCommand = new CreateRepresentationCommand(session, representationDescription, rootObject, representationName, dummyProgressMonitor);
		session.getTransactionalEditingDomain().getCommandStack().execute(createViewCommand);
		SessionManager.INSTANCE.notifyRepresentationCreated(session);

		Collection<DRepresentation> representations = DialectManager.INSTANCE.getRepresentations(representationDescription, session);
		logger.info("Available representations: " + representations);
		DRepresentation createdRepresentation = null;
		for (DRepresentation representation : representations) {
			if (representation.getName().equals(representationName)) {
				createdRepresentation = representation;
			}
		}

		if (createdRepresentation == null) {
			logger.error("Representation was not correctly created");
			throw new IllegalStateException("Representation was not correctly created");
		}

		logger.info("Created representation: " + createdRepresentation);
		progressMonitor.worked(WORK_CREATE_REPRESENTATION_PER_RESOURCE * (javaPackage.getCompilationUnits().length + 1));
		return createdRepresentation;
	}

	private void activateViewpoint(Session session, String viewpointName) {
		progressMonitor.subTask("Activate the UML viewpoint");

		Viewpoint viewpointToActivate = findViewpointInRegistry(viewpointName);
		if (viewpointToActivate == null) {
			logger.error("Viewport " + viewpointName + " could not be found.");
			throw new IllegalStateException("Could not find viewport with name: " + viewpointName);
		}
		logger.info("Available viewpoint: " + viewpointToActivate.getName());
		ViewpointSelection.Callback callback = new ViewpointSelectionCallbackWithConfimation();
		RecordingCommand command = new ChangeViewpointSelectionCommand(session, callback, Collections.singleton(viewpointToActivate), new HashSet<Viewpoint>(), true, dummyProgressMonitor);
		session.getTransactionalEditingDomain().getCommandStack().execute(command);

		logger.info("Selected viewpoints: " + session.getSelectedViewpoints(true));
		progressMonitor.worked(WORK_ACTIVATE_VIEWPOINT);
	}

	private DRepresentation findRepresentation(Session session, String representationName) {
		for (DRepresentation representation : DialectManager.INSTANCE.getAllRepresentations(session)) {
			if (representation.getName().equals(representationName)) {
				return representation;
			}
		}
		return null;
	}

	/**
	 * Returns a representation description with the given name in the given
	 * session for the specified semantic root object.
	 * 
	 * @param representationName
	 *            the name of the representation to search for
	 * @param session
	 *            the Sirius session
	 * @param rootObject
	 *            the semantic root object the representation description is
	 *            designed for
	 * @return the representation description or null if none exists
	 */
	private RepresentationDescription findRepresentationDescription(Session session, String representationName, EObject rootObject) {
		Collection<RepresentationDescription> availableDescriptions = DialectManager.INSTANCE.getAvailableRepresentationDescriptions(session.getSelectedViewpoints(false), rootObject);

		RepresentationDescription description = null;
		for (RepresentationDescription potentialDescription : availableDescriptions) {
			if (potentialDescription.getName().equals(representationName)) {
				description = potentialDescription;
				break;
			}
		}

		if (description == null) {
			logger.error("Representation description with name " + representationName + " was not found for object " + rootObject);
			throw new IllegalStateException("Could not find representation description " + REPRESENTATION_DESCRIPTION_NAME + " for object " + rootObject);
		}
		logger.info("Selected representation description: " + description);

		return description;
	}

	/**
	 * Returns the first viewpoint in the registry with the given name.
	 * 
	 * @param viewpointName
	 *            the viewpoint name to search for
	 * @return the first viewpoint with the given name or null if none exists
	 */
	private Viewpoint findViewpointInRegistry(String viewpointName) {
		final Set<Viewpoint> registry = ViewpointRegistry.getInstance().getViewpoints();
		Viewpoint candidateViewpoint = null;
		for (Viewpoint registeredViewpoint : registry) {
			if (registeredViewpoint.getName().equals(viewpointName)) {
				candidateViewpoint = registeredViewpoint;
				break;
			}
		}
		return candidateViewpoint;
	}

}
