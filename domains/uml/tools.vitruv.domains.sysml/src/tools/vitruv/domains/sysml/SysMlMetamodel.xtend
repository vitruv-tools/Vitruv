package tools.vitruv.domains.sysml

import tools.vitruv.framework.metamodel.Metamodel
import tools.vitruv.framework.tuid.TUIDCalculatorAndResolver
import tools.vitruv.framework.util.datatypes.VURI
import com.google.common.collect.Sets
import tools.vitruv.domains.uml.UmlMetamodel
import org.eclipse.papyrus.sysml14.sysmlPackage

class SysMlMetamodel extends Metamodel {
	public static val NAMESPACE_URIS = sysmlPackage.eINSTANCE.nsURIsRecursive.toList;
	public static final String FILE_EXTENSION = UmlMetamodel.FILE_EXTENSION;
	private static SysMlMetamodel instance;

	private new() {
		super(Sets.newHashSet(UmlMetamodel.NAMESPACE_URIS + NAMESPACE_URIS), VURI::getInstance(NAMESPACE_URIS.get(0)), FILE_EXTENSION);
	}

	override protected TUIDCalculatorAndResolver generateTuidCalculator(String nsPrefix) {
		return new SysMlTuidCalculatorAndResolver();
	}

	def public static synchronized SysMlMetamodel getInstance() {
		if (instance === null) {
			instance = new SysMlMetamodel()
		}
		return instance;
	}
	
}
