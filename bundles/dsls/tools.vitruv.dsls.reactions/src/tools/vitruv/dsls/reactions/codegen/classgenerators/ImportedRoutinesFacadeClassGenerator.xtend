package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.dsls.common.helper.ClassNameGenerator
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsImport
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*

class ImportedRoutinesFacadeClassGenerator extends RoutineFacadeClassGenerator {
	val ReactionsSegment reactionsSegment;
	val ReactionsImport reactionsImport;
	val String importedReactionsSegmentName;
	val ClassNameGenerator routinesFacadeNameGenerator;
	var JvmGenericType generatedClass;

	new(ReactionsSegment reactionsSegment, ReactionsImport reactionsImport, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(reactionsSegment, typesBuilderExtensionProvider);
		this.reactionsSegment = reactionsSegment;
		this.reactionsImport = reactionsImport;
		// not resolving cross-references here to get the imported reactions segment name:
		this.importedReactionsSegmentName = reactionsImport.parsedImportedReactionsSegmentName;
		this.routinesFacadeNameGenerator = reactionsSegment.getImportedRoutinesFacadeClassNameGenerator(importedReactionsSegmentName);
	}

	public override generateEmptyClass() {
		generatedClass = reactionsSegment.toClass(routinesFacadeNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
		]
	}

	override generateBody() {
		generatedClass => [
			val importedReactionsSegment = reactionsImport.importedReactionsSegment;
			val importedReactionsSegmentRoot = reactionsSegment.getImportedReactionsSegmentRoot(importedReactionsSegment, false);
			if (importedReactionsSegmentRoot === importedReactionsSegment) {
				// extend original routines facade of imported reactions segment:
				superTypes += typeRef(importedReactionsSegment.routinesFacadeClassNameGenerator.qualifiedName);
			} else {
				// extend imported routines facade of the imported reactions segment root:
				superTypes += typeRef(importedReactionsSegmentRoot.getImportedRoutinesFacadeClassNameGenerator(importedReactionsSegmentName).qualifiedName);
			}
			members += generateBaseConstructor();
			// override routines:
			reactionsSegment.overrideRoutines.filter [
				importedReactionsSegmentName.equals(it.overriddenReactionsSegment.name);
			].forEach [
				generatedClass.members += it.generateCallMethod;
			];
		]
	}
}
