/*
 * generated by Xtext 2.9.0
 */
package tools.vitruv.dsls.reactions.ui.outline

import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode
import tools.vitruv.dsls.reactions.language.toplevelelements.Trigger
import tools.vitruv.dsls.reactions.language.toplevelelements.TopLevelElementsPackage
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode
import tools.vitruv.dsls.common.elements.MetamodelImport
import tools.vitruv.dsls.common.elements.ElementsPackage
import tools.vitruv.dsls.reactions.language.toplevelelements.Routine
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsFile
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsSegment
import tools.vitruv.dsls.reactions.language.toplevelelements.Reaction
import tools.vitruv.dsls.reactions.language.toplevelelements.Action
import static extension tools.vitruv.dsls.reactions.util.ReactionsLanguageUtil.*
import tools.vitruv.dsls.reactions.language.toplevelelements.Matcher
import tools.vitruv.dsls.reactions.language.toplevelelements.RoutineInput
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsImport
import tools.vitruv.dsls.reactions.language.toplevelelements.RoutineCallBlock
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode
import tools.vitruv.dsls.reactions.language.ModelElementChange
import tools.vitruv.dsls.reactions.language.ModelAttributeChange
import tools.vitruv.dsls.reactions.language.ArbitraryModelChange

/**
 * Outline structure definition for a reactions file.
 *
 * @author Heiko Klare
 */
class ReactionsLanguageOutlineTreeProvider extends DefaultOutlineTreeProvider {
	protected def void _createChildren(DocumentRootNode root, ReactionsFile reactionsFile) {
		val importsNode = createEStructuralFeatureNode(root, reactionsFile, 
			TopLevelElementsPackage.Literals.REACTIONS_FILE__METAMODEL_IMPORTS,
			imageDispatcher.invoke(reactionsFile), "imports", false);
		for (imp : reactionsFile.metamodelImports) {
			createChildren(importsNode, imp);
		}
		for (reactionsSegment : reactionsFile.reactionsSegments) {
			createChildren(root, reactionsSegment);
		}
	}
	
	protected def void _createChildren(DocumentRootNode parentNode, ReactionsSegment reactionsSegment) {
		val segmentNode = createEObjectNode(parentNode, reactionsSegment);
		val reactionsImportsNode = createEStructuralFeatureNode(segmentNode, reactionsSegment,
			TopLevelElementsPackage.Literals.REACTIONS_SEGMENT__REACTIONS_IMPORTS, imageDispatcher.invoke(reactionsSegment),
			"reactionsImports", false);
		for (reactionsImport : reactionsSegment.reactionsImports) {
			createChildren(reactionsImportsNode, reactionsSegment);
		}
		val reactionsNode = createEStructuralFeatureNode(segmentNode, reactionsSegment, 
			TopLevelElementsPackage.Literals.REACTIONS_SEGMENT__REACTIONS, imageDispatcher.invoke(reactionsSegment), "reactions", false)
		for (reaction : reactionsSegment.reactions) {
			createChildren(reactionsNode, reactionsSegment);	
		}
		val routinesNode = createEStructuralFeatureNode(segmentNode, reactionsSegment, TopLevelElementsPackage.Literals.REACTIONS_SEGMENT__ROUTINES, imageDispatcher.invoke(reactionsSegment), "routines", false)
		for (routine : reactionsSegment.routines) {
			createChildren(routinesNode, reactionsSegment);	
		}
	}
	
	protected def void _createChildren(EStructuralFeatureNode parentNode, MetamodelImport imp) {
		val importNode = createEObjectNode(parentNode, imp);
		createEStructuralFeatureNode(importNode,
			imp, ElementsPackage.Literals.METAMODEL_IMPORT__PACKAGE,
			imageDispatcher.invoke(imp?.package),
			imp?.package?.name, true);
	}
	
	protected def void _createChildren(EStructuralFeatureNode parentNode, ReactionsImport reactionsImport) {
		val importNode = createEObjectNode(parentNode, reactionsImport);
		createEStructuralFeatureNode(importNode, reactionsImport,
			TopLevelElementsPackage.Literals.REACTIONS_IMPORT__IMPORTED_REACTIONS_SEGMENT,
			imageDispatcher.invoke(reactionsImport.importedReactionsSegment),
			reactionsImport.importedReactionsSegment?.name, true);
	}
	
	protected def void _createChildren(EStructuralFeatureNode parentNode, Reaction reaction) {
		val reactionNode = createEObjectNode(parentNode, reaction);
		if (reaction.documentation !== null) {
			createEStructuralFeatureNode(reactionNode, reaction,
				TopLevelElementsPackage.Literals.REACTION__DOCUMENTATION,
				imageDispatcher.invoke(reaction.documentation),
				"documentation", true);
		}
		if (reaction.trigger !== null) {
			createChildren(reactionNode, reaction.trigger);
		}
	}
	
	protected def void _createChildren(EObjectNode parentNode, Trigger trigger) {
		createEObjectNode(parentNode, trigger);
	}
	
	protected def Object _text(MetamodelImport imp) {
		return imp?.name;
	}
	
	protected def Object _text(ReactionsImport reactionsImport) {
		return reactionsImport.importedReactionsSegment?.name;
	}
	
	protected def Object _text(Reaction reaction) {
		return "reaction: " + reaction.displayName;
	}
	
	protected def Object _text(Routine routine) {
		return "routine: " + routine.displayName;
	}
	
	protected def Object _text(RoutineInput routineInput) {
		return "parameters";
	}
	
	protected def Object _text(Matcher matcher) {
		return "matcher";
	}
	
	protected def Object _text(Action action) {
		return "action";
	}
	
	protected def Object _text(ReactionsSegment reactionsSegment) {
		return "segment: " + reactionsSegment.name;
	}
	
	protected def Object _text(Trigger trigger) {
		return "There is no outline for this trigger";
	}
	
	protected def Object _text(ModelElementChange event) {
		return "element change"; 
		//''«FOR change : event.extractChangeSequenceRepresentation.atomicChanges SEPARATOR ", "»«change.name»«ENDFOR»''';
	}
	
	protected def Object _text(ModelAttributeChange event) {
		return "attribute change"; 
		//''«FOR change : event.extractChangeSequenceRepresentation.atomicChanges SEPARATOR ", "»«change.name»«ENDFOR»''';
	}
	
	protected def Object _text(ArbitraryModelChange event) {
		return "any change";
	}
	
	protected def boolean _isLeaf(Trigger element) {
		return true;
	}
	
	protected def boolean _isLeaf(Matcher element) {
		return true;
	}
	
	protected def boolean _isLeaf(Action element) {
		return true;
	}
	
	protected def boolean _isLeaf(RoutineCallBlock element) {
		return true;
	}
	
}
