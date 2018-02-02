package tools.vitruv.dsls.reactions.codegen.classgenerators

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

	val ReactionsSegment reactionsSegment;
	val ReactionsImportPath relativeImportPath; // relative to reactions segment
	val ReactionsImportPath absoluteImportPath;
	val ClassNameGenerator routinesFacadeNameGenerator;
	var JvmGenericType generatedClass;

	new(ReactionsSegment reactionsSegment, ReactionsImportPath relativeImportPath, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(reactionsSegment, typesBuilderExtensionProvider);
		if (!reactionsSegment.isComplete) {
			throw new IllegalArgumentException("incomplete");
		}
		this.reactionsSegment = reactionsSegment;
		this.relativeImportPath = relativeImportPath;
		this.absoluteImportPath = ReactionsImportPath.create(#[reactionsSegment.name], relativeImportPath.segments);
		this.routinesFacadeNameGenerator = reactionsSegment.getOverriddenRoutinesFacadeClassNameGenerator(relativeImportPath);
	}

	public override generateEmptyClass() {
		generatedClass = reactionsSegment.toClass(routinesFacadeNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
		]
	}

	override generateBody() {
		val overriddenReactionsSegment = reactionsSegment.getReactionsSegment(relativeImportPath);
		val routinesOverrideRoot = reactionsSegment.getRoutinesOverrideRoot(relativeImportPath, false);
		if (overriddenReactionsSegment === null || routinesOverrideRoot === null) {
			// invalid import path, skipping class-body generation:
			return generatedClass;
		}

		generatedClass => [
			if (routinesOverrideRoot.name.equals(overriddenReactionsSegment.name)) {
				// extend the original routines facade of the overridden reactions segment:
				superTypes += typeRef(overriddenReactionsSegment.routinesFacadeClassNameGenerator.qualifiedName);
			} else {
				// extend the overridden routines facade from the override root:
				val overrideRootRelativeImportPath = relativeImportPath.relativeTo(routinesOverrideRoot.name);
				superTypes += typeRef(routinesOverrideRoot.getOverriddenRoutinesFacadeClassNameGenerator(overrideRootRelativeImportPath).qualifiedName);
			}
			members += generateConstructor();

			// override routines:
			reactionsSegment.overrideRoutines.filter[it.isComplete].filter [
				it.overrideImportPath.toReactionsImportPath.equals(relativeImportPath)
			].forEach [
				generatedClass.members += it.generateCallMethod(absoluteImportPath);
			];
		]
	}

	protected override String getExtendedConstructorBody() {
		return "";
	}
}
