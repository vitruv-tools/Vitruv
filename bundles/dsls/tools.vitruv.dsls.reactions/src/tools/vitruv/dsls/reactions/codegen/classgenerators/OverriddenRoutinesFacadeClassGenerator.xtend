package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtend2.lib.StringConcatenationClient
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.dsls.common.helper.ClassNameGenerator
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath

import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsImportsHelper.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*
import static extension tools.vitruv.dsls.reactions.util.ReactionsLanguageUtil.*

class OverriddenRoutinesFacadeClassGenerator extends RoutineFacadeClassGenerator {

	// the reactions segment containing the routine override(s):
	val ReactionsSegment reactionsSegment;
	// the import path to the overridden reactions segment, relative to the overriding reactions segment:
	val ReactionsImportPath relativeImportPath;
	val ClassNameGenerator routinesFacadeNameGenerator;
	var JvmGenericType generatedClass;

	new(ReactionsSegment reactionsSegment, ReactionsImportPath relativeImportPath, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(reactionsSegment, typesBuilderExtensionProvider);
		if (!reactionsSegment.isComplete) {
			throw new IllegalArgumentException("incomplete");
		}
		this.reactionsSegment = reactionsSegment;
		this.relativeImportPath = relativeImportPath;
		this.routinesFacadeNameGenerator = reactionsSegment.getOverriddenRoutinesFacadeClassNameGenerator(relativeImportPath);
	}

	override generateEmptyClass() {
		generatedClass = reactionsSegment.toClass(routinesFacadeNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
		]
	}

	override generateBody() {
		val overrideRootResult = reactionsSegment.getRoutinesOverrideRoot(relativeImportPath, false);
		if (overrideRootResult === null) {
			// invalid import path, skipping class-body generation:
			return generatedClass;
		}
		val overrideRootSegment = overrideRootResult.value;

		generatedClass => [
			if (overrideRootSegment.name.equals(relativeImportPath.lastSegment)) {
				// the override root is the overridden reactions segment itself: extend the original routines facade:
				superTypes += typeRef(overrideRootSegment.routinesFacadeClassNameGenerator.qualifiedName);
			} else {
				// extend the overridden routines facade of the override root:
				val relativeImportPathFromOverrideRoot = relativeImportPath.relativeTo(overrideRootSegment.name);
				superTypes += typeRef(overrideRootSegment.getOverriddenRoutinesFacadeClassNameGenerator(relativeImportPathFromOverrideRoot).qualifiedName);
			}
			members += generateConstructor();

			// override routines:
			reactionsSegment.overrideRoutines.filter[it.isComplete].filter [
				it.overrideImportPath.toReactionsImportPath.equals(relativeImportPath)
			].forEach [
				generatedClass.members += it.generateCallMethod;
			];
		]
	}

	protected override StringConcatenationClient getExtendedConstructorBody() '''
	'''

	protected override StringConcatenationClient generateGetOwnRoutinesFacade() '''
		this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().subPathTo("«reactionsSegment.name»"))'''
}
