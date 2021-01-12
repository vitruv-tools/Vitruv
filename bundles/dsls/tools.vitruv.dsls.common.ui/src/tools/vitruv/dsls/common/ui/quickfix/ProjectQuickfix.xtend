package tools.vitruv.dsls.common.ui.quickfix

import org.eclipse.xtext.validation.Issue
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import static com.google.common.base.Preconditions.checkArgument
import static extension tools.vitruv.dsls.common.ui.ProjectAccess.*
import static extension tools.vitruv.dsls.common.ui.PluginProjectExtensions.*
import org.eclipse.core.runtime.NullProgressMonitor
import edu.kit.ipd.sdq.activextendannotations.Utility
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil.*
import org.eclipse.pde.internal.ui.wizards.tools.ConvertProjectToPluginOperation

@Utility
class ProjectQuickfix {
	/**
	 * Adds a missing bundle to the project classpath. Applicable for {@link ProjectValidation.ErrorCodes#BUNDLE_MISSING_ON_CLASSPATH}.
	 */
	def static addBundleToProject(Issue issue, IssueResolutionAcceptor acceptor) {
		issue.checkCode(ProjectValidation.ErrorCodes.BUNDLE_MISSING_ON_CLASSPATH)
		val requiredBundle = issue.data.get(0)

		acceptor.accept(issue, '''Add dependency on ‹«requiredBundle»›''', null, null) [ context |
			val pluginProject = context.eclipseProject.pluginProject
			pluginProject.addRequiredBundle(requiredBundle)
			pluginProject.apply(new NullProgressMonitor)
		]
	}

	/**
	 * Configures the project to be a plugin project. Applicable for {@link ProjectValidation.ErrorCodes#NOT_A_PLUGIN_PROJECT}
	 */
	def static convertToPluginProject(Issue issue, IssueResolutionAcceptor acceptor) {
		issue.checkCode(ProjectValidation.ErrorCodes.NOT_A_PLUGIN_PROJECT)

		acceptor.accept(issue, 'Convert the project to a plugin project', null, null) [ context |
			new ConvertProjectToPluginOperation(#[context.eclipseProject], false).run(new NullProgressMonitor)
		]
	}

	/**
	 * Configures the project to be a Java project. Applicable for {@link ProjectValidation.ErrorCodes#NOT_A_JAVA_PROJECT}
	 */
	def static convertToJavaProject(Issue issue, IssueResolutionAcceptor acceptor) {
		issue.checkCode(ProjectValidation.ErrorCodes.NOT_A_JAVA_PROJECT)

		acceptor.accept(issue, 'Convert the project to a Java project', null, null) [ context |
			context.eclipseProject.configureAsJavaProject().configureSrcGenFolder()
		]
	}

	def private static checkCode(Issue issue, String expectedCode) {
		checkArgument(
			issue.code == expectedCode,
			'''This quickfix is only applicable for issues with code «expectedCode»!'''
		)
	}
}
