package tools.vitruvius.applications.jmljava.helper.java.shadowcopy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.statements.Statement;

import tools.vitruvius.domains.jml.language.ConcreteSyntaxHelper;
import tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.ModelURIProvider;
import tools.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyJMLToJavaHelper;
import tools.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyJavaToJMLHelper;
import tools.vitruvius.domains.jml.language.jML.ClassBodyDeclaration;
import tools.vitruvius.domains.jml.language.jML.JMLMultilineSpec;
import tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement;
import tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier;
import tools.vitruvius.domains.jml.language.jML.MemberDeclaration;
import tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration;
import tools.vitruvius.domains.jml.language.jML.Type;
import tools.vitruvius.framework.correspondence.CorrespondenceModel;
import tools.vitruvius.framework.metamodel.ModelInstance;
import tools.vitruvius.framework.util.datatypes.VURI;

public class ShadowCopyImpl implements ShadowCopy {

	private static final Logger LOGGER = Logger.getLogger(ShadowCopyImpl.class);
	private static final ShadowCopyJMLToJavaHelper JML2JAVA_CONVERTER = new ShadowCopyJMLToJavaHelper();
	private static final ShadowCopyJavaToJMLHelper JAVA2JML_CONVERTER = new ShadowCopyJavaToJMLHelper();
	private final Collection<CompilationUnit> javaCUs;
	private final ShadowCopyCorrespondencesWritable dummyCorrespondences;

	public ShadowCopyImpl(CorrespondenceModel ci,
			ModelURIProvider javaModelUriProvider) {
		this(ci, javaModelUriProvider, false);
	}

	public ShadowCopyImpl(CorrespondenceModel ci,
			ModelURIProvider javaModelUriProvider, boolean useJMLCopies) {
		CachedModelInstanceLoader temporaryLoader = new CachedModelInstanceLoader();
		this.javaCUs = Collections.unmodifiableCollection(collectJavaCUs(
				temporaryLoader, ci, javaModelUriProvider));
		this.dummyCorrespondences = new ShadowCopyCorrespondencesImpl(ci,
				javaCUs, useJMLCopies);
	}

	/**
	 * Finds all Java compilation units. The models to look at are determined by
	 * the <code>JavaModelURIProvider</code>.
	 * 
	 * @param modelInstanceHelper
	 *            The model loader to use.
	 * @param ci
	 *            The correspondence instance, which contains the relationship
	 *            between Java and JML.
	 * @param javaModelUriProvider
	 *            The model URI provider to use to find Java models.
	 * @return A set of specified compilation units.
	 */
	private static List<CompilationUnit> collectJavaCUs(
			final CachedModelInstanceLoader modelInstanceHelper,
			CorrespondenceModel ci, ModelURIProvider javaModelUriProvider) {
		LOGGER.debug("Looking for monitored Java models.");
		final List<CompilationUnit> matches = new ArrayList<CompilationUnit>();
		for (final VURI uri : javaModelUriProvider.getAllRelevantURIs()) {
			ModelInstance originalJavaModelInstance = modelInstanceHelper
					.loadModelInstance(uri.getEMFUri());
			CompilationUnit javaRoot = originalJavaModelInstance
					.getUniqueTypedRootEObject(CompilationUnit.class);
			matches.add(javaRoot);
		}
		return matches;
	}

	@Override
	public void setupShadowCopyWithJMLSpecifications(
			boolean resolveAllReferences) {
		LOGGER.debug("Start: Initializing shadow copy with JML specifications. Reference resolution: "
				+ resolveAllReferences);
		long timestamp1 = System.currentTimeMillis();
		for (CompilationUnit obj : javaCUs) {
			for (ConcreteClassifier javaClassifier : obj.getClassifiers()) {
				if (javaClassifier instanceof Interface) {
					// TODO interface should be handled as well
					continue;
				}
				addJMLStatementsToShadowCopyFor(javaClassifier);
			}
		}
		long timestamp3 = System.currentTimeMillis();
		LOGGER.debug("End: Initializing shadow copy with JML specifications. Duration: "
				+ ((timestamp3 - timestamp1) / 1000) + " s.");
		
//		try {
//			dummyCorrespondences.saveAll();			
//		} catch (Exception e) {
//			System.err.println(e);
//		}
		
		if (resolveAllReferences) {
			dummyCorrespondences.getShadowModelInstanceLoader().resolveAll();
		}
		long timestamp2 = System.currentTimeMillis();
		LOGGER.debug("Resolved all references. Duration: "
				+ ((timestamp2 - timestamp3) / 1000) + " s.");
	}

	/**
	 * Adds the JML specifications to the Java shadow copy for a classifier. It
	 * processed each method of the classifier and adds dummy methods required
	 * for the modified method statements.
	 * 
	 * @param javaClassifier
	 *            The classifier, which shall be modified.
	 */
	private void addJMLStatementsToShadowCopyFor(
			ConcreteClassifier javaClassifier) {
		Map<String, Type> methodTypes = new HashMap<String, Type>();

		// find corresponding JML class
		ConcreteClassifier javaClassifierShadow = dummyCorrespondences
				.getShadow(javaClassifier);
		Set<NormalClassDeclaration> jmlClasses;
		try {
			jmlClasses = dummyCorrespondences.getCorrespondingEObjectsByType(
					javaClassifier, NormalClassDeclaration.class);
		} catch (RuntimeException e) {
			jmlClasses = null;
		}
		if (jmlClasses == null || jmlClasses.size() != 1) {
			return;
		}
		NormalClassDeclaration jmlClass = jmlClasses.iterator().next();

		List<JMLTypeExpressionWithModifier> typeSpecs = new ArrayList<JMLTypeExpressionWithModifier>();

		// process every statement in the class body
		for (ClassBodyDeclaration bodyDeclaration : jmlClass
				.getBodyDeclarations()) {
			if (bodyDeclaration instanceof JMLSpecifiedElement) {
				JMLSpecifiedElement specElement = (JMLSpecifiedElement) bodyDeclaration;
				if (specElement.getJmlTypeSpecifications().size() > 0) {
					// type specifications have to be treated in a different way
					typeSpecs.addAll(specElement.getJmlTypeSpecifications());
					continue;
				}

				Map<String, Type> elementTypes = addJMLStatementsToShadowCopyFor(
						specElement, javaClassifierShadow);
				methodTypes.putAll(elementTypes);
			}
		}

		// add dummy methods
		javaClassifierShadow.getMembers().add(
				0,
				JML2JAVA_CONVERTER.getOldReplacementMethod(javaClassifierShadow
						.eResource().getURI()));
		javaClassifierShadow.getMembers().add(
				0,
				JML2JAVA_CONVERTER
						.getFreshReplacementMethod(javaClassifierShadow
								.eResource().getURI()));
		for (Type resultType : methodTypes.values()) {
			javaClassifierShadow.getMembers().add(
					0,
					JML2JAVA_CONVERTER.getResultReplacementMethodFor(
							resultType, javaClassifierShadow.eResource()
									.getURI()));
		}

		javaClassifierShadow.getMembers().add(
				0,
				JML2JAVA_CONVERTER.getTypeSpecificationsMethod(typeSpecs,
						javaClassifierShadow.eResource().getURI(),
						dummyCorrespondences));
	}

	/**
	 * Adds the JML specifications to the Java shadow copy for a specified
	 * element. A specified element can contain an unbound number of model
	 * elements and exactly one regular element.
	 * 
	 * @param jmlSpecifiedElement
	 *            The specified element, which shall be added to the shadow
	 *            copy.
	 * @param javaClassifierShadow
	 *            The Java classifier from the shadow copy where the specified
	 *            element shall be added.
	 * @return All types of the specified elements (for methods this is the
	 *         return type, for fields their types).
	 */
	private Map<String, Type> addJMLStatementsToShadowCopyFor(
			JMLSpecifiedElement jmlSpecifiedElement,
			ConcreteClassifier javaClassifierShadow) {

		Member shadowMember = null;
		if (jmlSpecifiedElement.getElement() != null) {
			// regular element
			Set<Member> javaMembers = dummyCorrespondences
					.getCorrespondingEObjectsByType(
							jmlSpecifiedElement.getElement(), Member.class);
			if (javaMembers.size() == 1) {
				shadowMember = dummyCorrespondences.getShadow(javaMembers
						.iterator().next());
				if (javaMembers.iterator().next() instanceof InterfaceMethod) {
					ClassMethod shadowMemberSpecContaining = JML2JAVA_CONVERTER
							.createSpecContainingMethod(
									((JMLSpecifiedElement) jmlSpecifiedElement)
											.getElement(), javaClassifierShadow
											.eResource().getURI());
					javaClassifierShadow.getMembers().add(
							shadowMemberSpecContaining);
					dummyCorrespondences.addCorrespondence(shadowMember,
							shadowMemberSpecContaining);
					shadowMember = shadowMemberSpecContaining;
				}
			} else {
				// error
			}
		} else if (jmlSpecifiedElement instanceof JMLMultilineSpec
				&& ((JMLMultilineSpec) jmlSpecifiedElement).getModelElement() != null) {
			// model element
			shadowMember = JML2JAVA_CONVERTER.createJavaMember(
					((JMLMultilineSpec) jmlSpecifiedElement).getModelElement()
							.getElement().getElement(), javaClassifierShadow
							.eResource().getURI());
			javaClassifierShadow.getMembers().add(shadowMember);
		} else {
			// error
		}

		dummyCorrespondences.addCorrespondence(jmlSpecifiedElement,
				shadowMember);
		JML2JAVA_CONVERTER.addJMLStatementsToMember(shadowMember,
				jmlSpecifiedElement.getJmlSpecifications(),
				dummyCorrespondences);

		Map<String, Type> methodTypes = new HashMap<String, Type>();
		Type t = getType(jmlSpecifiedElement);
		if (t != null) { // we do not need dummy methods for void types
			methodTypes.put(ConcreteSyntaxHelper.convertToConcreteSyntax(t), t);
		}
		return methodTypes;
	}

	private static Type getType(JMLSpecifiedElement element) {
		MemberDeclWithModifier memberDecl = element.getElement();
		if (memberDecl == null) {
			memberDecl = ((JMLMultilineSpec) element).getModelElement()
					.getElement().getElement();
		}
		if (memberDecl.getMemberdecl() instanceof MemberDeclaration) {
			return ((MemberDeclaration) memberDecl.getMemberdecl()).getType();
		}
		// TODO this should handle generic methods as well
		return null;
	}

	@Override
	public Collection<EObject> updateOriginalJMLSpecifications() {
		LOGGER.debug("Updating original JML models with specifications from the shadow copy.");
		Set<EObject> changedObjects = new HashSet<EObject>();
		for (CompilationUnit javaCu : javaCUs) {
			for (ConcreteClassifier javaClassifier : javaCu.getClassifiers()) {
				changedObjects
						.addAll(updateOriginalJMLSpecificationsFromShadowCopy(javaClassifier));
			}
		}
		return changedObjects;
	}

	/**
	 * Updates the original JML specifications of the given class with the data
	 * from the shadow copy.
	 * 
	 * @param javaClassifier
	 *            The class, whichs methods shall be updated.
	 * @return The set of changed methods.
	 */
	private Set<JMLSpecifiedElement> updateOriginalJMLSpecificationsFromShadowCopy(
			ConcreteClassifier javaClassifier) {
		Set<JMLSpecifiedElement> changedMethods = new HashSet<JMLSpecifiedElement>();

		// determine corresponding JML class
		Set<NormalClassDeclaration> jmlClasses = dummyCorrespondences
				.getCorrespondingEObjectsByType(javaClassifier,
						NormalClassDeclaration.class);
		if (jmlClasses == null || jmlClasses.size() != 1) {
			return changedMethods;
		}
		NormalClassDeclaration jmlClass = jmlClasses.iterator().next();

		List<JMLTypeExpressionWithModifier> jmlTypeSpecifications = new ArrayList<JMLTypeExpressionWithModifier>();

		// process every statement in JML class body
		for (ClassBodyDeclaration bodyDeclaration : jmlClass
				.getBodyDeclarations()) {
			if (bodyDeclaration instanceof JMLSpecifiedElement) {
				JMLSpecifiedElement jmlSpecifiedElement = (JMLSpecifiedElement) bodyDeclaration;
				if (jmlSpecifiedElement.getJmlTypeSpecifications().size() > 0) {
					jmlTypeSpecifications.addAll(jmlSpecifiedElement
							.getJmlTypeSpecifications());
					continue;
				}

				Member shadowMember = dummyCorrespondences
						.getMember(jmlSpecifiedElement);
				if (JAVA2JML_CONVERTER
						.updateByShadowObject(jmlSpecifiedElement,
								shadowMember, dummyCorrespondences)) {
					changedMethods.add(jmlSpecifiedElement);
				}

			}
		}

		if (jmlTypeSpecifications.size() > 0) {
			Statement stmt = dummyCorrespondences.get(jmlTypeSpecifications
					.get(0).getSpec());
			ClassMethod typeSpecMethod = (ClassMethod) stmt.eContainer();
			JAVA2JML_CONVERTER
					.updateTypeSpecsByShadowObject(jmlTypeSpecifications,
							typeSpecMethod, dummyCorrespondences);
			// TODO add to changed?
		}

		changedMethods.remove(null);
		return changedMethods;
	}

	@Override
	public ShadowCopyCorrespondences getShadowCopyCorrespondences() {
		return dummyCorrespondences;
	}

}
