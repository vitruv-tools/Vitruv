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

	override getRelevantAtomicChangeTypeRepresentation() {
		return usageChange;
	}

	public override StringConcatenationClient getRelevantChangeAssignmentCode(String untypedChangeVariableName, 
		String typedChangeVariableName) {
		val typedRelevantChangeString = relevantAtomicChangeTypeRepresentation.typedChangeTypeRepresentation;
		return '''
			«typedRelevantChangeString» «typedChangeVariableName» = «
					»((«typedChangeTypeRepresentation»)«untypedChangeVariableName»).«
					»«IF CreateAndInsertEObject.isAssignableFrom(changeType)»getInsertChange()«
					ELSEIF RemoveAndDeleteEObject.isAssignableFrom(changeType)»getRemoveChange()«
					ELSEIF CreateAndReplaceAndDeleteNonRoot.isAssignableFrom(changeType)»getReplaceChange()«ENDIF»;
		'''
	}
}
