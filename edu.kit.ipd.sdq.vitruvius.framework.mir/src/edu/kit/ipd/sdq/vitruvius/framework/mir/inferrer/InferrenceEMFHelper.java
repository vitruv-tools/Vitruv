package edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer;

import org.eclipse.emf.ecore.EPackage;

public class InferrenceEMFHelper {
	/**
	 * Needed because Xtend does not allow this easily
	 * @return
	 */
	public static EPackage.Registry getPackageRegistry() {
		return EPackage.Registry.INSTANCE;
	}
}
