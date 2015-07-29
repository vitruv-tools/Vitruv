/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.provider;


import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PartialEReferenceCorrespondenceItemProvider extends PartialEFeatureCorrespondenceItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartialEReferenceCorrespondenceItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addElementATUIDPropertyDescriptor(object);
			addElementBTUIDPropertyDescriptor(object);
			addTypePropertyDescriptor(object);
			addFeatureAPropertyDescriptor(object);
			addFeatureBPropertyDescriptor(object);
			addValueATUIDPropertyDescriptor(object);
			addValueBTUIDPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Element ATUID feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addElementATUIDPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SameTypeCorrespondence_elementATUID_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SameTypeCorrespondence_elementATUID_feature", "_UI_SameTypeCorrespondence_type"),
				 CorrespondencePackage.Literals.SAME_TYPE_CORRESPONDENCE__ELEMENT_ATUID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Element BTUID feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addElementBTUIDPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SameTypeCorrespondence_elementBTUID_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SameTypeCorrespondence_elementBTUID_feature", "_UI_SameTypeCorrespondence_type"),
				 CorrespondencePackage.Literals.SAME_TYPE_CORRESPONDENCE__ELEMENT_BTUID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_EFeatureCorrespondence_type_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_EFeatureCorrespondence_type_feature", "_UI_EFeatureCorrespondence_type"),
				 CorrespondencePackage.Literals.EFEATURE_CORRESPONDENCE__TYPE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Feature A feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFeatureAPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_EFeatureCorrespondence_featureA_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_EFeatureCorrespondence_featureA_feature", "_UI_EFeatureCorrespondence_type"),
				 CorrespondencePackage.Literals.EFEATURE_CORRESPONDENCE__FEATURE_A,
				 false,
				 false,
				 false,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Feature B feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFeatureBPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_EFeatureCorrespondence_featureB_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_EFeatureCorrespondence_featureB_feature", "_UI_EFeatureCorrespondence_type"),
				 CorrespondencePackage.Literals.EFEATURE_CORRESPONDENCE__FEATURE_B,
				 false,
				 false,
				 false,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Value ATUID feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addValueATUIDPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PartialEReferenceCorrespondence_valueATUID_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_PartialEReferenceCorrespondence_valueATUID_feature", "_UI_PartialEReferenceCorrespondence_type"),
				 CorrespondencePackage.Literals.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_ATUID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Value BTUID feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addValueBTUIDPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PartialEReferenceCorrespondence_valueBTUID_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_PartialEReferenceCorrespondence_valueBTUID_feature", "_UI_PartialEReferenceCorrespondence_type"),
				 CorrespondencePackage.Literals.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_BTUID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns PartialEReferenceCorrespondence.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/PartialEReferenceCorrespondence"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		TUID labelValue = ((PartialEReferenceCorrespondence<?>)object).getElementATUID();
		String label = labelValue == null ? null : labelValue.toString();
		return label == null || label.length() == 0 ?
			getString("_UI_PartialEReferenceCorrespondence_type") :
			getString("_UI_PartialEReferenceCorrespondence_type") + " " + label;
	}
	

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(PartialEReferenceCorrespondence.class)) {
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_ATUID:
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_BTUID:
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__TYPE:
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_ATUID:
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_BTUID:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
