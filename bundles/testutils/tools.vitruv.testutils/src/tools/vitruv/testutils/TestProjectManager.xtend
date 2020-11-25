package tools.vitruv.testutils

import java.io.IOException
import java.nio.file.DirectoryNotEmptyException
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import org.apache.log4j.Logger
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.junit.jupiter.api.^extension.ExtensionContext
import org.junit.jupiter.api.^extension.ParameterContext
import org.junit.jupiter.api.^extension.ParameterResolutionException
import org.junit.jupiter.api.^extension.ParameterResolver

import static com.google.common.base.Preconditions.checkState
import static java.nio.file.Files.*
import static java.nio.file.Files.createDirectories
import static java.nio.file.Files.createDirectory
import static java.nio.file.Files.walk
import static java.util.Comparator.reverseOrder
import static org.apache.log4j.Level.INFO
import static tools.vitruv.framework.util.VitruviusConstants.getTestProjectMarkerFileName
import java.util.regex.Pattern
import org.eclipse.core.resources.ResourcesPlugin
import static com.google.common.base.Preconditions.checkNotNull
import java.util.ArrayList
import org.junit.jupiter.api.^extension.AfterEachCallback
import static com.google.common.base.Preconditions.checkArgument

/**
 * Extension managing the test projects for Eclipse tests. Test classes using this extension can have test project 
 * folders injected by using the @{@link TestProject} annotation. Test projects will be cleaned if their corresponding
 * test succeed, but retained if their corresponding test failed.
 */
class TestProjectManager implements ParameterResolver, AfterEachCallback {
	// set this system property to “always”, “on_failure” or “never” to retain the test projects 
	// always, on failure, or never. The default is “on_failure”.
	static val RETAIN_TEST_PROJECTS_SYSTEM_PROPERTY = "vitruv.retainTestProjects"
	static val log = Logger.getLogger(TestProjectManager) => [level = INFO]
	static val namespace = ExtensionContext.Namespace.create(TestProjectManager)
	static val observedFailure = "observedFailure"
	static val projectNamespace = ExtensionContext.Namespace.create(TestProjectManager, "projects")
	static val invalidFileCharacters = Pattern.compile("[/\\\\<>:\"|?*\u0000]")

	// When running tests of a whole project, Eclipse sometimes uses multiple JUnit Jupiter runs to run all tests.
	// The only option to preserve the workspace across these runs is to make it static.
	static Path workspaceCache

	override afterEach(ExtensionContext context) throws Exception {
		if (context.executionException.isPresent) {
			context.observedFailure = true
		}
	}

	def private static getObservedFailure(ExtensionContext context) {
		context.getStore(namespace).getOrDefault(observedFailure, Boolean, false)
	}

	def private static setObservedFailure(ExtensionContext context, boolean value) {
		context.parentChain.forEach[getStore(namespace).put(observedFailure, true)]
	}

	def private static getParentChain(ExtensionContext context) {
		var result = new ArrayList<ExtensionContext>()
		for (var current = context; current !== null; current = current.parent.orElse(null)) {
			result += current
		}
		return result
	}

	def private Path setupWorkspace() {
		if (workspaceCache !== null) return workspaceCache

		val testWorkspace = ResourcesPlugin.workspace.root.location.toFile.toPath
		workspaceCache = createUniqueDirectory(testWorkspace.resolve('Vitruv'))
		log.info('''Running in the test workspace at «TestProjectManager.workspaceCache»''')
		workspaceCache
	}

	def private Path getWorkspace(ExtensionContext context) {
		context.root.getStore(namespace).getOrComputeIfAbsent('workspace', [
			new WorkspaceGuard(setupWorkspace())
		], WorkspaceGuard).workspace
	}

	def private Path getProject(String variant, ExtensionContext context) {
		val workspace = getWorkspace(context)

		context.getStore(projectNamespace).getOrComputeIfAbsent(variant, [
			val projectPath = getProjectRelativePath(variant, context)
			val projectDir = createUniqueDirectory(workspace.resolve(projectPath)) => [
				createFile(resolve(testProjectMarkerFileName))
			]
			new ProjectGuard(projectDir, context)
		], ProjectGuard).projectDir

	}

	def private Path getProjectRelativePath(String variant, ExtensionContext context) {
		val testDir = if (context.testMethod.isPresent) {
				Path.of(
					context.testClass.map[simpleName.removeInvalidCharacters()].orElse(""),
					context.displayName.removeInvalidCharacters()
				)
			} else {
				Path.of(context.displayName.removeInvalidCharacters())
			}
		return if (variant != "") testDir.resolve('''[«variant.removeInvalidCharacters()»]''') else testDir
	}

	override resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
		val projectAnnotation = checkNotNull(parameterContext.parameter.getAnnotation(TestProject))
		getProject(projectAnnotation.variant, extensionContext)
	}

	override supportsParameter(ParameterContext parameterContext,
		ExtensionContext extensionContext) throws ParameterResolutionException {
		if (parameterContext.parameter.getAnnotation(TestProject) !== null) {
			if (parameterContext.parameter.type != Path) {
				throw new ParameterResolutionException('''The parameter «parameterContext.parameter.name» is annotated with @«TestProject.simpleName», but its type is not «Path.simpleName»!''')
			}
			return true
		}
		return false
	}

	def private static Path createUniqueDirectory(Path projectPath) {
		var uniqueProject = projectPath
		createDirectories(projectPath.parent)
		for (var counter = 2, var created = false; !created; counter++) {
			try {
				createDirectory(uniqueProject)
				created = true
			} catch (FileAlreadyExistsException alreadyExists) {
				uniqueProject = projectPath.resolveSibling('''«projectPath.fileName» «counter»''')
			}
			checkState(counter < 1000, "Failed to create a unique version of %s with 1000 tries", projectPath)
		}
		return uniqueProject
	}

	private static def removeInvalidCharacters(String fileName) {
		invalidFileCharacters.matcher(fileName).replaceAll("-")
	}

	private static enum RetainMode {
		ALWAYS,
		ON_FAILURE,
		NEVER
	}

	def private static getRetainMode() {
		val propertyValue = System.getProperty(RETAIN_TEST_PROJECTS_SYSTEM_PROPERTY)?.toLowerCase
		if (propertyValue === null) return RetainMode.ON_FAILURE
		val mode = RetainMode.values.findFirst[name.toLowerCase == propertyValue]
		checkArgument(mode !== null, '''Unknown test project retain mode '«propertyValue»!''')
		return mode
	}

	@FinalFieldsConstructor
	private static class WorkspaceGuard implements ExtensionContext.Store.CloseableResource {
		val Path workspace

		override close() throws Throwable {
			walkFileTree(workspace, new SimpleFileVisitor<Path>() {
				override postVisitDirectory(Path dir, IOException error) {
					super.postVisitDirectory(dir, error) => [
						try {
							delete(dir)
						} catch (DirectoryNotEmptyException e) {
							// still some files left
						}
					]
				}
			})
		}
	}

	@FinalFieldsConstructor
	private static class ProjectGuard implements ExtensionContext.Store.CloseableResource {
		val Path projectDir
		val ExtensionContext context

		override close() throws Throwable {
			switch retain: retainMode {
				case NEVER,
				case retain == ON_FAILURE && !context.observedFailure:
					walk(projectDir).sorted(reverseOrder).forEach[delete(it)]
				default: { // retain
				}
			}
		}
	}
}
