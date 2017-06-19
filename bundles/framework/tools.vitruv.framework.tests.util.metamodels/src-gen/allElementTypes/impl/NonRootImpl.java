/**
 */
package allElementTypes.impl;

import allElementTypes.AllElementTypesPackage;
import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Non
 * Root</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public class NonRootImpl extends IdentifiedImpl implements NonRoot {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected NonRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AllElementTypesPackage.Literals.NON_ROOT;
	}

	@Override
	public boolean equals(Object o) {
		if (null != o && o instanceof NonRootImpl) {
			final NonRootImpl containerHelper = (NonRootImpl) o;
			// TODO PS HACK
			final int beginIndex = 7;
			final String thisId = this.id.substring(beginIndex);
			final String otherId = containerHelper.getId().substring(beginIndex);
			final boolean equal = thisId.equals(otherId);
			return equal;
		} else
			return false;
	}
} // NonRootImpl
