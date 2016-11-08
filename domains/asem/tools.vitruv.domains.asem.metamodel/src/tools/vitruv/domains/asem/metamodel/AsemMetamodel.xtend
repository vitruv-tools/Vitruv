package tools.vitruv.domains.asem.metamodel

import tools.vitruv.framework.metamodel.Metamodel
import tools.vitruv.framework.tuid.AttributeTUIDCalculatorAndResolver
import edu.kit.ipd.sdq.ASEM.ASEMPackage
import tools.vitruv.framework.tuid.TUIDCalculatorAndResolver
import edu.kit.ipd.sdq.ASEM.base.BasePackage
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EPackage

class AsemMetamodel extends Metamodel {
	public static final EPackage rootPackage = ASEMPackage.eINSTANCE;
	public static final String FILE_EXTENSION = "asem";
	private static AsemMetamodel instance;
	private static val nsURIs = rootPackage.nsURIsRecursive;
	
	private new() {
		super(newHashSet(nsURIs), VURI::getInstance(rootPackage.nsURI), FILE_EXTENSION);
	}

	override protected TUIDCalculatorAndResolver generateTuidCalculator(String nsPrefix) {
		return new AttributeTUIDCalculatorAndResolver(nsPrefix, 
			#[BasePackage.Literals.IDENTIFIABLE__ID.name, BasePackage.Literals.NAMED__NAME.name]
		);
	}

	def public static synchronized AsemMetamodel getInstance() {
		if (instance === null) {
			instance = new AsemMetamodel()
		}
		return instance;
	}
}