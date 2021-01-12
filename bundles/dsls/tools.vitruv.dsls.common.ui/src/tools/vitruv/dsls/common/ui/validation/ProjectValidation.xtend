package tools.vitruv.dsls.common.ui.validation

import static extension tools.vitruv.dsls.common.ValidationMessageAcceptorExtensions.*
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.validation.ValidationMessageAcceptor
import static extension tools.vitruv.dsls.common.ui.ProjectAccess.*
import org.eclipse.core.runtime.CoreException
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry
import org.osgi.framework.FrameworkUtil
import org.eclipse.xtext.common.types.util.TypeReferences

@Utility
class ProjectValidation {
	static class ErrorCodes {
		public static val PREFIX = 'tools.vitruv.dsls.common.ui.validation.ProjectValidation.'
		// a type is missing on the classpath and adding a specific bundle will most likely fix it
		public static val BUNDLE_MISSING_ON_CLASSPATH = PREFIX + 'BUNDLE_MISSING_ON_CLASSPATH'
		// a type is missing on the classpath, but we do not know how to fix it
		public static val CANNOT_ACCESS_TYPE = PREFIX + 'CANNOT_ACCESS_TYPE'
		public static val NOT_A_JAVA_PROJECT = PREFIX + 'NOT_A_JAVA_PROJECT'
		public static val NOT_A_PLUGIN_PROJECT = PREFIX + 'NOT_A_PLUGIN_PROJECT'
	}

	def static checkIsJavaPluginProject(ValidationMessageAcceptor acceptor, EObject referenceObject) {
		checkIsJavaPluginProject(acceptor, referenceObject, null)
	}

	def static checkIsJavaPluginProject(ValidationMessageAcceptor acceptor, EObject referenceObject,
		EStructuralFeature messageTargetFeature) {
		val project = referenceObject.eclipseProject
		if (!project.isJavaProject) {
			acceptor.warning("The project is not a Java project", referenceObject, messageTargetFeature,
				ErrorCodes.NOT_A_JAVA_PROJECT)
		}
		if (!project.isPluginProject) {
			acceptor.warning("The project is not an Eclipse plugin project", referenceObject, messageTargetFeature,
				ErrorCodes.NOT_A_PLUGIN_PROJECT)
		}
	}

	def static checkRuntimeProjectIsOnClasspath(ValidationMessageAcceptor acceptor, TypeReferences typeReferences,
		Class<?> markerType, EObject referenceObject) {
		checkRuntimeProjectIsOnClasspath(acceptor, typeReferences, markerType, referenceObject, null)
	}

	def static checkRuntimeProjectIsOnClasspath(ValidationMessageAcceptor acceptor, TypeReferences typeReferences,
		Class<?> markerType, EObject referenceObject, EStructuralFeature messageTargetFeature) {
		checkOnClasspath(acceptor, typeReferences, markerType, referenceObject, messageTargetFeature,
			"The runtime bundle is not on the classpath")
	}

	def static checkDomainProjectIsOnClasspath(ValidationMessageAcceptor acceptor, TypeReferences typeReferences,
		String requiredDomainName, EObject referenceObject) {
		checkDomainProjectIsOnClasspath(acceptor, typeReferences, requiredDomainName, referenceObject, null)
	}

	def static checkDomainProjectIsOnClasspath(ValidationMessageAcceptor acceptor, TypeReferences typeReferences,
		String requiredDomainName, EObject referenceObject, EStructuralFeature messageTargetFeature) {
		val domainProviderClass = VitruvDomainProviderRegistry.getDomainProvider(requiredDomainName).class
		checkOnClasspath(acceptor, typeReferences, domainProviderClass, referenceObject,
			messageTargetFeature, '''«domainProviderClass.simpleName» is not on the classpath''')
	}

	def private static checkOnClasspath(ValidationMessageAcceptor acceptor, TypeReferences typeReferences,
		Class<?> searchedClass, EObject referenceObject, EStructuralFeature messageTargetFeature, String message) {
		val project = referenceObject.eclipseProject
		if(!project.isJavaProject) return;

		try {
			if (typeReferences.findDeclaredType(searchedClass, referenceObject) === null) {
				if (!project.isPluginProject) {
					acceptor.error(message, referenceObject, messageTargetFeature, ErrorCodes.CANNOT_ACCESS_TYPE)
				} else {
					val providingBundle = FrameworkUtil.getBundle(searchedClass).symbolicName
					val currentlyRequiredBundles = project.pluginProject.requiredBundles?.toList ?: emptyList
					if (currentlyRequiredBundles.exists[name == providingBundle]) {
						acceptor.error(message, referenceObject, messageTargetFeature, ErrorCodes.CANNOT_ACCESS_TYPE)
					} else {
						acceptor.error(message, referenceObject, messageTargetFeature,
							ErrorCodes.BUNDLE_MISSING_ON_CLASSPATH, #[providingBundle])
					}
				}
			}
		} catch (CoreException e) {
			acceptor.
				error('''Failed to search this project’s classpath. Please check that your project is set up correctly: «e.message»''',
					referenceObject, messageTargetFeature)
		}
	}
}
