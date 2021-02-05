package tools.vitruv.dsls.commonalities.ui.setup

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.core.resources.IncrementalProjectBuilder
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.jdt.core.JavaCore
import org.eclipse.xtext.ui.XtextProjectHelper
import tools.vitruv.dsls.commonalities.util.CommonalitiesLanguageConstants
import java.io.InputStream
import java.io.ByteArrayInputStream
import org.eclipse.xtext.ui.util.JREContainerProvider
import org.eclipse.pde.internal.core.PDECore
import java.util.Hashtable
import org.eclipse.pde.core.project.IBundleProjectDescription
import org.eclipse.pde.core.project.IRequiredBundleDescription
import org.eclipse.ui.PlatformUI
import org.eclipse.swt.widgets.Display
import org.eclipse.core.resources.IContainer
import org.eclipse.core.runtime.Path
import edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IResourceUtil.*
import org.eclipse.jdt.core.IClasspathEntry

@Utility
class CommonalitiesProjectSetup {
	static val String COMPLIANCE_LEVEL = '11';

	def static IProject setupAsCommonalitiesProject(IProject project) {
		project.open(null)
		project.setDescription(project.description => [
			natureIds = #[JavaCore.NATURE_ID, XtextProjectHelper.NATURE_ID, IBundleProjectDescription.PLUGIN_NATURE]
		], null)
		project.createManifestMf()
		val sourcesFolder = project.getFolder(IProjectUtil.JAVA_SOURCE_FOLDER.toString).createIfNotExists()
		val generatedSourcesFolder = project.getFolder(IProjectUtil.SOURCE_GEN_FOLDER.toString).createIfNotExists()
		val javaProjectBinFolder = project.getFolder(IProjectUtil.JAVA_BIN_FOLDER.toString)
		val IClasspathEntry[] projectClasspath = #[
			JREContainerProvider.defaultJREContainerEntry,
			JavaCore.newContainerEntry(PDECore.REQUIRED_PLUGINS_CONTAINER_PATH),
			JavaCore.newSourceEntry(sourcesFolder.fullPath),
			JavaCore.newSourceEntry(generatedSourcesFolder.fullPath)
		]
		JavaCore.create(project) => [
			options = new Hashtable => [
				put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, COMPLIANCE_LEVEL)
				put(JavaCore.COMPILER_COMPLIANCE, COMPLIANCE_LEVEL)
				put(JavaCore.COMPILER_SOURCE, COMPLIANCE_LEVEL)
			]
			setRawClasspath(projectClasspath, javaProjectBinFolder.fullPath, true, null)
			save(null, true)
		]
		return project
	}

	def private static createManifestMf(IProject project) {
		val mf = '''
			Manifest-Version: 1.0
			Bundle-ManifestVersion: 2
			Bundle-Name: Commonalities Language Test Project
			Bundle-Vendor: vitruv.tools
			Bundle-Version: 2.1.0.qualifier
			Bundle-SymbolicName: «project.name.replace(' ', '-')»; singleton:=true
			Bundle-ActivationPolicy: lazy
			Require-Bundle: «CommonalitiesLanguageConstants.RUNTIME_BUNDLE»
			Bundle-RequiredExecutionEnvironment: JavaSE-«COMPLIANCE_LEVEL»
		'''
		project.getFolder('META-INF').createIfNotExists().createFile('MANIFEST.MF', mf)
	}

	def static removeRequiredBundle(IBundleProjectDescription pluginProject, String requiredBundleName) {
		val currentlyRequiredBundles = pluginProject.requiredBundles
		if (currentlyRequiredBundles === null) return;
		if (currentlyRequiredBundles.length === 1) {
			pluginProject.requiredBundles = null
		} else {
			var removeIndex = currentlyRequiredBundles.indexed.findFirst[value.name == requiredBundleName]?.key
			if (removeIndex === null) return;
			val newRequiredBundles = <IRequiredBundleDescription>newArrayOfSize(currentlyRequiredBundles.length - 1)
			if (removeIndex > 0) {
				System.arraycopy(currentlyRequiredBundles, 0, newRequiredBundles, 0, removeIndex)
			}
			if (removeIndex < newRequiredBundles.length) {
				System.arraycopy(currentlyRequiredBundles, removeIndex + 1, newRequiredBundles, removeIndex,
					newRequiredBundles.length - removeIndex)
			}
			pluginProject.requiredBundles = newRequiredBundles
		}
	}

	def static closeWelcomePage() {
		Display.^default.asyncExec [
			val introManager = PlatformUI.workbench.introManager
			val intro = introManager.intro
			if (intro !== null) {
				introManager.closeIntro(intro);
			}
		]
	}

	def static build(IProject project, String configName) {
		project.build(IncrementalProjectBuilder.FULL_BUILD, configName, null, new NullProgressMonitor)
	}

	def static refresh(IProject project) {
		project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor)
	}

	def static getSourceFolder(IProject project) {
		project.getFolder(IProjectUtil.SOURCE_GEN_FOLDER.toString)
	}

	def static getBinFolder(IProject project) {
		project.getFolder(IProjectUtil.JAVA_BIN_FOLDER.toString)
	}

	def static createFile(IContainer container, String fileName, InputStream content) {
		val file = container.getFile(new Path(fileName))
		file.create(content, true, null)
		return file
	}

	def static createFile(IContainer container, String fileName, String content) {
		container.createFile(fileName, new ByteArrayInputStream(content.bytes))
	}

	def static getPath(IResource eclipseResource) {
		eclipseResource.rawLocation.toFile.toPath
	}
}
