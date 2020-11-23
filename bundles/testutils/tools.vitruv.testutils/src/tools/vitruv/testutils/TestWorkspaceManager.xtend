package tools.vitruv.testutils

import java.io.IOException
import java.lang.reflect.Parameter
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

/**
 * Extension managing a test workspace for Eclipse tests. Test classes using this extension can have a workspace
 * injected by annotating a parameter with {@link TestWorkspace}.
 */
class TestWorkspaceManager implements ParameterResolver {
	static val VM_ARGUMENT_TEST_WORKSPACE_PATH = "testWorkspacePath"
	static val log = Logger.getLogger(TestWorkspaceManager) => [level = INFO]
	static val namespace = ExtensionContext.Namespace.create(TestWorkspaceManager)
	static val projectNamespace = ExtensionContext.Namespace.create(TestWorkspaceManager, "projects")
	static val invalidFileCharacters = Pattern.compile("[/\\\\<>:\"|?*\u0000]")
	/**
	 * Eclipse uses multiple JUnit Jupiter runs. The only option to preserve the workspace across the runs is
	 * to make is static.
	 */
	static Path workspaceCache

	def private Path setupWorkspace() {
		if (TestWorkspaceManager.workspaceCache !== null)
			return TestWorkspaceManager.workspaceCache
		var testWorkspacePath = System.getProperty(VM_ARGUMENT_TEST_WORKSPACE_PATH)
		workspaceCache = if (testWorkspacePath === null) {
			// TODO what about surefire?
			val base = ResourcesPlugin.workspace.root.location.toFile.toPath
			createUniqueDirectory(base.resolve('''Vitruv-test-projects'''))
		} else {
			createDirectories(Path.of(testWorkspacePath))
		}
		log.info('''Running in the test workspace at «TestWorkspaceManager.workspaceCache»''')
		workspaceCache
	}

	def private Path getWorkspace(ExtensionContext context) {
		context.root.getStore(namespace).getOrComputeIfAbsent('''workspace''', [
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
		Path.of(
			context.testClass.map[simpleName.removeInvalidCharacters()].orElse(""),
			context.displayName.removeInvalidCharacters() +
				if (variant != "") '''[«variant.removeInvalidCharacters()»]''' else ""
		)

	}

	override resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
		val projectAnnotation = parameterContext.parameter.getAnnotation(TestProject)
		if (projectAnnotation !== null) {
			getProject(projectAnnotation.variant, extensionContext)
		} else {
			getWorkspace(extensionContext)
		}
	}

	override supportsParameter(ParameterContext parameterContext,
		ExtensionContext extensionContext) throws ParameterResolutionException {
		val isWorkspace = parameterContext.parameter.getAnnotation(TestWorkspace) !== null
		val isProject = parameterContext.parameter.getAnnotation(TestProject) !== null
		if (isWorkspace && isProject) {
			throw new ParameterResolutionException('''A parameter must not be annotated with @«TestWorkspace.simpleName» and «TestProject.simpleName» at the same time!''')
		}
		if (isWorkspace) {
			parameterContext.parameter.checkIsPath(TestWorkspace)
		}
		if (isProject) {
			parameterContext.parameter.checkIsPath(TestProject)
		}
		return isWorkspace || isProject
	}

	def private checkIsPath(Parameter parameter, Class<?> annotationClass) {
		if (parameter.type != Path) {
			throw new ParameterResolutionException('''The parameter «parameter.name» is annotated with @«TestWorkspace», but its type is not «Path.simpleName»!''')
		}
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
			checkState(counter < 1000, "Failed to create a unique version of %s with 1000 tries")
		}
		return uniqueProject
	}

	private static def removeInvalidCharacters(String fileName) {
		invalidFileCharacters.matcher(fileName).replaceAll("-")
	}

	@FinalFieldsConstructor
	private static class WorkspaceGuard implements ExtensionContext.Store.CloseableResource {
		val Path workspace

		override close() throws Throwable {
			val deleteWorkspace = workspace.toString != System.getProperty(VM_ARGUMENT_TEST_WORKSPACE_PATH)
			walkFileTree(workspace, new SimpleFileVisitor<Path>() {
				override postVisitDirectory(Path dir, IOException error) {
					super.postVisitDirectory(dir, error) => [
						if (deleteWorkspace || dir != workspace) {
							try {
								delete(workspace)
							} catch (DirectoryNotEmptyException e) {
								// still some files left
							}
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
			// clear if the test succeeded, retain if the test failed
			if (context.executionException.isEmpty) {
				walk(projectDir).sorted(reverseOrder).forEach[delete(it)]
			}
		}
	}
}
