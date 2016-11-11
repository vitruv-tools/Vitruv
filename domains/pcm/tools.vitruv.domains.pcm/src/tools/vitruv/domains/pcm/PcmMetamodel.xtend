package tools.vitruv.domains.pcm

import tools.vitruv.framework.metamodel.Metamodel
import com.google.common.collect.Sets
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.tuid.AttributeTUIDCalculatorAndResolver
import org.palladiosimulator.pcm.core.entity.EntityPackage
import de.uka.ipd.sdq.identifier.IdentifierPackage
import static extension tools.vitruv.domains.pcm.PcmNamespace.*;

class PcmMetamodel extends Metamodel {
	package new() {
		super(Sets.newHashSet(ROOT_PACKAGE.nsURIsRecursive.toList), VURI.getInstance(METAMODEL_NAMESPACE), 
			#[REPOSITORY_FILE_EXTENSION, SYSTEM_FILE_EXTENSION]
		);
	}
	
	override protected generateTuidCalculator(String nsPrefix) {
		return new AttributeTUIDCalculatorAndResolver(METAMODEL_NAMESPACE, 
			#[IdentifierPackage.Literals.IDENTIFIER__ID.name, EntityPackage.Literals.NAMED_ELEMENT__ENTITY_NAME.name]);
	}
	
}