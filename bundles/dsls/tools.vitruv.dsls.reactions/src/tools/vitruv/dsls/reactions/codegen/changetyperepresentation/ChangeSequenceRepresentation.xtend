package tools.vitruv.dsls.reactions.codegen.changetyperepresentation

import org.eclipse.xtend2.lib.StringConcatenationClient
import java.util.List
import tools.vitruv.dsls.reactions.codegen.helper.AccessibleElement
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.root.RootEChange

public class ChangeSequenceRepresentation {
	@Accessors(PUBLIC_GETTER)
	private List<AtomicChangeTypeRepresentation> atomicChanges;

	protected new(Iterable<AtomicChangeTypeRepresentation> atomicChanges) {
		this.atomicChanges = newArrayList;
		this.atomicChanges += atomicChanges;
	}

	public def AtomicChangeTypeRepresentation getChange(int number) {
		if (number >= atomicChanges.size) {
			throw new IllegalArgumentException();
		}
		return atomicChanges.get(number);
	}
	
	public def int getNumberOfChanges() {
		return atomicChanges.size;
	}
	
	public def List<AccessibleElement> getFields() {
		return atomicChanges.map[accessibleElement];
	}
	
	public def StringConcatenationClient getResetFieldsCode() {
		'''
			«FOR atomicChange : atomicChanges»
				«atomicChange.resetFieldCode»
			«ENDFOR»
		'''
	}
	
	// FIXME HK This only works as long as we are restricted to the basic change sequence types
	private def AtomicChangeTypeRepresentation getRelevantAtomicChange() {
		val nonExistenceChange = atomicChanges.findFirst[FeatureEChange.isAssignableFrom(changeType) || RootEChange.isAssignableFrom(changeType)];
		if (nonExistenceChange !== null) {
			return nonExistenceChange;
		}
		return atomicChanges.last;
	}
	
	public def Iterable<AccessibleElement> generatePropertiesParameterList() {
		relevantAtomicChange.generatePropertiesParameterList();
	}
	
	public def StringConcatenationClient generatePropertiesAssignmentCode() {
		relevantAtomicChange.generatePropertiesAssignmentCode();
		
	}

}
