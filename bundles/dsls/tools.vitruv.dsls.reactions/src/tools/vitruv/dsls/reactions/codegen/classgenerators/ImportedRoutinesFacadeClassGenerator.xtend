package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.TypesFactory
import tools.vitruv.dsls.common.helper.ClassNameGenerator
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsImport
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*

class ImportedRoutinesFacadeClassGenerator extends RoutineFacadeClassGenerator {
	val ReactionsSegment reactionsSegment;
	val ReactionsSegment importedReactionsSegment;
	val ReactionsImport rootReactionsImport;
	val ClassNameGenerator routinesFacadeNameGenerator;
	var JvmGenericType generatedClass;

	new(ReactionsSegment reactionsSegment, ReactionsSegment importedReactionsSegment, ReactionsImport rootReactionsImport,
		TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(reactionsSegment, typesBuilderExtensionProvider);
		this.reactionsSegment = reactionsSegment;
		this.importedReactionsSegment = importedReactionsSegment;
		this.rootReactionsImport = rootReactionsImport;
		this.routinesFacadeNameGenerator = reactionsSegment.getImportedRoutinesFacadeClassNameGenerator(importedReactionsSegment);
	}

	public override generateEmptyClass() {
		generatedClass = reactionsSegment.toClass(routinesFacadeNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
		]
	}

	override generateBody() {
		generatedClass => [
			val rootImportReactionsSegment = rootReactionsImport.importedReactionsSegment;
			if (rootImportReactionsSegment.equals(importedReactionsSegment)) {
				// direct import:
				superTypes += typeRef(importedReactionsSegment.routinesFacadeClassNameGenerator.qualifiedName);
			} else {
				// transitive import, originating from importRootReactionsSegment:
				// TODO this is an ugly hack:
				// typeRef(..) doesn't find the super class for transitive routines facades,
				// because those weren't generated during the indexing phase,
				// because the cross-refs for imported segments cannot be resolved during indexing
				// because xtext filters project-local resources from the index during indexing phase (commit mentions a bug ticket, which cannot be found anymore though).
				val superClassNameGenerator = rootImportReactionsSegment.getImportedRoutinesFacadeClassNameGenerator(importedReactionsSegment);
				var superTypeRef = typeRef(superClassNameGenerator.qualifiedName);
				if (superTypeRef.type === null) {
					// creating virtual dummy class to use as reference, otherwise it would use an UnknownReference,
					// which gets later (during serialization) interpreted as a reference to an interface, resulting in 'implements' instead of 'extends':
					val superClass = TypesFactory.eINSTANCE.createJvmGenericType();
					superClass.simpleName = superClassNameGenerator.simpleName;
					superClass.packageName = superClassNameGenerator.packageName;
					superClass.visibility = JvmVisibility.PUBLIC;
					superTypeRef = typeRef(superClass);
				}
				superTypes += superTypeRef
				//superTypes += typeRef(rootImportReactionsSegment.getImportedRoutinesFacadeClassNameGenerator(importedReactionsSegment).qualifiedName);
			}
			members += generateBaseConstructor();
			// overridden routines:
			for (overriddenRoutine : reactionsSegment.overriddenRoutines) {
				if (overriddenRoutine.overriddenReactionsSegment.equals(importedReactionsSegment)) {
					members += overriddenRoutine.generateCallMethod;
				}
			}
		]
	}
}
