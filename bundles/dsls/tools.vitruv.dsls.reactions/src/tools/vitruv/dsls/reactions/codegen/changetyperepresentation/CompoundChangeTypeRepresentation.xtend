package tools.vitruv.dsls.reactions.codegen.changetyperepresentation

import org.eclipse.xtend2.lib.StringConcatenationClient
import tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot

public class CompoundChangeTypeRepresentation extends ChangeTypeRepresentation {
	private Class<?> changeType;
	private AtomicChangeTypeRepresentation existenceChange;
	private AtomicChangeTypeRepresentation usageChange;

	protected new(Class<?> changeType, AtomicChangeTypeRepresentation existenceChange,
		AtomicChangeTypeRepresentation usageChange) {
		this.changeType = changeType;
		this.existenceChange = existenceChange;
		this.usageChange = usageChange;
	}

	override getChangeType() {
		return changeType;
	}

	override getGenericTypeParameters() {
		return #[usageChange.affectedElementClass, usageChange.affectedValueClass].filterNull.toList;
	}

	def getExistenceChange() {
		return existenceChange;
	}

	def getUsageChange() {
		return usageChange;
	}

	override getRelevantChangeTypeRepresentation() {
		return usageChange;
	}

	public override StringConcatenationClient getRelevantChangeAssignmentCode(String originalChangeVariableName,
		boolean isOriginalVariableUntyped, String typedChangeVariableName) {
		val typedRelevantChangeString = relevantChangeTypeRepresentation.
			typedChangeTypeRepresentation;
		return '''
			«typedRelevantChangeString» «typedChangeVariableName» = «
					»(«IF isOriginalVariableUntyped»(«typedChangeTypeRepresentation»)«ENDIF»«originalChangeVariableName»).«
					»«IF CreateAndInsertEObject.isAssignableFrom(changeType)»getInsertChange()«
					ELSEIF RemoveAndDeleteEObject.isAssignableFrom(changeType)»getRemoveChange()«
					ELSEIF CreateAndReplaceAndDeleteNonRoot.isAssignableFrom(changeType)»getReplaceChange()«ENDIF»;
		'''
	}
}
