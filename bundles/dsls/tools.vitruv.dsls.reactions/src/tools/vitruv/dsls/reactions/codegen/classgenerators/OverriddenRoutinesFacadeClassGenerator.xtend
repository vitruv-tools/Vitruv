package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.dsls.common.helper.ClassNameGenerator
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath

import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsImportsHelper.*
import static extension tools.vitruv.dsls.reactions.util.ReactionsLanguageUtil.*

class OverriddenRoutinesFacadeClassGenerator extends RoutineFacadeClassGenerator {
	val ReactionsSegment reactionsSegment;
	val ReactionsImportPath reactionsImportPath; // relative to reactions segment
	val ClassNameGenerator routinesFacadeNameGenerator;
	var JvmGenericType generatedClass;

	new(ReactionsSegment reactionsSegment, ReactionsImportPath reactionsImportPath, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(reactionsSegment, typesBuilderExtensionProvider);
		this.reactionsSegment = reactionsSegment;
		this.reactionsImportPath = reactionsImportPath;
		this.routinesFacadeNameGenerator = reactionsSegment.getOverriddenRoutinesFacadeClassNameGenerator(reactionsImportPath);
	}

	public override generateEmptyClass() {
		generatedClass = reactionsSegment.toClass(routinesFacadeNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
		]
	}

	override generateBody() {
		generatedClass => [
			val overriddenReactionsSegment = reactionsSegment.getReactionsSegment(reactionsImportPath);
			val routinesOverrideRoot = reactionsSegment.getRoutinesOverrideRoot(reactionsImportPath, false);
			if (routinesOverrideRoot === overriddenReactionsSegment) { // TODO compare only names here?
				// extend the original routines facade of the overridden reactions segment:
				superTypes += typeRef( overriddenReactionsSegment.routinesFacadeClassNameGenerator.qualifiedName);
			} else {
				// extend the overridden routines facade from the override root:
				val relativeImportPath = reactionsImportPath.relativeTo(routinesOverrideRoot.name);
				superTypes += typeRef(routinesOverrideRoot.getOverriddenRoutinesFacadeClassNameGenerator(relativeImportPath).qualifiedName);
			}
			members += generateConstructor();

			// override routines:
			val importPathString = reactionsImportPath.pathString;
			val absoluteImportPath = ReactionsImportPath.create(#[reactionsSegment.name], reactionsImportPath.segments);
			reactionsSegment.overrideRoutines.filter [
				importPathString.equals(it.overriddenReactionsSegmentImportPath)
			].forEach [
				generatedClass.members += it.generateCallMethod(absoluteImportPath);
			];
		]
	}

	override String getExtendedConstructorBody() {
		return "";
	}
}
