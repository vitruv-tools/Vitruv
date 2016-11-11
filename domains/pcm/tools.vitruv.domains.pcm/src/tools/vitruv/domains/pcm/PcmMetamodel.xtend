package tools.vitruv.domains.pcm

import tools.vitruv.framework.metamodel.Metamodel
import org.palladiosimulator.pcm.PcmPackage
import com.google.common.collect.Sets
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.tuid.AttributeTUIDCalculatorAndResolver
import org.palladiosimulator.pcm.core.entity.EntityPackage
import de.uka.ipd.sdq.identifier.IdentifierPackage

class PcmMetamodel extends Metamodel {
	public static val MAIN_NAMESPACE_URI = PcmPackage.eNS_URI;
	public static val NAMESPACE_URIS = PcmPackage.eINSTANCE.nsURIsRecursive.toList;
	public static final String REPOSITORY_FILE_EXTENSION = "repository";
	public static final String SYSTEM_FILE_EXTENSION = "system";
	
	package new() {
		super(Sets.newHashSet(NAMESPACE_URIS), VURI.getInstance(MAIN_NAMESPACE_URI), #[REPOSITORY_FILE_EXTENSION, SYSTEM_FILE_EXTENSION]);
	}
	
	override protected generateTuidCalculator(String nsPrefix) {
		return new AttributeTUIDCalculatorAndResolver(MAIN_NAMESPACE_URI, 
			#[IdentifierPackage.Literals.IDENTIFIER__ID.name, EntityPackage.Literals.NAMED_ELEMENT__ENTITY_NAME.name]);
	}
	
}