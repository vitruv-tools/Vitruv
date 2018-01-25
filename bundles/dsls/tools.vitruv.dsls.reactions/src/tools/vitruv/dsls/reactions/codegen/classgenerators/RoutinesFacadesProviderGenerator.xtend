package tools.vitruv.dsls.reactions.codegen.classgenerators

import java.util.LinkedHashMap
import java.util.Map
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.dsls.common.helper.ClassNameGenerator
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRoutinesFacadesProvider
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath

import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsImportsHelper.*

class RoutinesFacadesProviderGenerator extends ClassGenerator {

	val ReactionsSegment reactionsSegment;
	var JvmGenericType generatedClass;

	new(ReactionsSegment reactionsSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider)
		this.reactionsSegment = reactionsSegment;
	}

	override generateEmptyClass() {
		generatedClass = reactionsSegment.toClass(reactionsSegment.routinesFacadesProviderClassNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
		];
	}

	override generateBody() {
		generatedClass => [
			superTypes += typeRef(AbstractRoutinesFacadesProvider);
			members += reactionsSegment.toConstructor()[];

			// create routines facades for the whole reactions import hierarchy:
			members += reactionsSegment.toMethod("createRoutinesFacade", typeRef(AbstractRepairRoutinesFacade)) [
				visibility = JvmVisibility.PUBLIC;
				val reactionsImportPathParameter = generateParameter("reactionsImportPath", typeRef(ReactionsImportPath));
				val sharedExecutionStateParameter = generateParameter("sharedExecutionState", typeRef(RoutinesFacadeExecutionState));
				parameters += reactionsImportPathParameter;
				parameters += sharedExecutionStateParameter;
				body = '''
					switch(«reactionsImportPathParameter.name».getPathString()) {
					«FOR importHierarchyEntry : reactionsSegment.importHierarchyRoutinesFacades.entrySet»
						«val importPath = importHierarchyEntry.key»
						«val routinesFacadeClassNameGenerator = importHierarchyEntry.value»
							case "«importPath.pathString»": {
								return new «routinesFacadeClassNameGenerator.qualifiedName»(this, «reactionsImportPathParameter.name», «sharedExecutionStateParameter.name»);
							}
					«ENDFOR»
						default: {
							return null;
						}
					}
				'''
			]
		]
	}

	// includes the routine facade for the root reactions segment:
	private def Map<ReactionsImportPath, ClassNameGenerator> getImportHierarchyRoutinesFacades(ReactionsSegment rootReactionsSegment) {
		val importHierarchyRoutinesFacades = new LinkedHashMap<ReactionsImportPath, ClassNameGenerator>();

		// add routines facade for the root reactions segment:
		importHierarchyRoutinesFacades.put(ReactionsImportPath.create(rootReactionsSegment.name), rootReactionsSegment.routinesFacadeClassNameGenerator);

		// add routines facades for all other reactions segments in the import hierarchy:
		for (importHierarchyEntry : rootReactionsSegment.routinesImportHierarchy.entrySet) {
			// for each reactions segment in the import hierarchy determine the routines facade class of the top-most reactions segment
			// in the import hierarchy overriding routines of it:
			val importPath = importHierarchyEntry.key;
			val importedReactionsSegment = importHierarchyEntry.value;
			val routinesOverrideRoot = reactionsSegment.getRoutinesOverrideRoot(importPath, true);
			var ClassNameGenerator routinesFacadeClassNameGenerator;
			if (routinesOverrideRoot.name.equals(importedReactionsSegment.name)) {
				// no other reactions segment is overriding routines of this reactions segment -> using the original routines facade:
				routinesFacadeClassNameGenerator = importedReactionsSegment.routinesFacadeClassNameGenerator;
			} else {
				// get the overridden routines facade from the override root:
				val relativeImportPath = importPath.relativeTo(routinesOverrideRoot.name);
				routinesFacadeClassNameGenerator = routinesOverrideRoot.getOverriddenRoutinesFacadeClassNameGenerator(relativeImportPath);
			}
			importHierarchyRoutinesFacades.put(importPath, routinesFacadeClassNameGenerator);
		}
		return importHierarchyRoutinesFacades;
	}
}
