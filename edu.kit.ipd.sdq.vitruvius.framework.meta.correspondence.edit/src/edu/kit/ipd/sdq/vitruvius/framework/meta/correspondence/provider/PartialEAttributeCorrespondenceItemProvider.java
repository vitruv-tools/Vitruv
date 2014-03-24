/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.provider;


import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PartialEAttributeCorrespondenceItemProvider
    extends PartialEFeatureCorrespondenceItemProvider
    implements
        IEditingDomainItemProvider,
        IStructuredItemContentProvider,
        ITreeItemContentProvider,
        IItemLabelProvider,
        IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PartialEAttributeCorrespondenceItemProvider(AdapterFactory adapterFactory) {
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

            addDependentCorrespondencesPropertyDescriptor(object);
            addParentPropertyDescriptor(object);
            addElementAPropertyDescriptor(object);
            addElementBPropertyDescriptor(object);
            addTypePropertyDescriptor(object);
            addFeatureAPropertyDescriptor(object);
            addFeatureBPropertyDescriptor(object);
            addValueAPropertyDescriptor(object);
            addValueBPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Dependent Correspondences feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addDependentCorrespondencesPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_Correspondence_dependentCorrespondences_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_Correspondence_dependentCorrespondences_feature", "_UI_Correspondence_type"),
                 CorrespondencePackage.Literals.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES,
                 true,
                 false,
                 true,
                 null,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Parent feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addParentPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_Correspondence_parent_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_Correspondence_parent_feature", "_UI_Correspondence_type"),
                 CorrespondencePackage.Literals.CORRESPONDENCE__PARENT,
                 true,
                 false,
                 true,
                 null,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Element A feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addElementAPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_SameTypeCorrespondence_elementA_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_SameTypeCorrespondence_elementA_feature", "_UI_SameTypeCorrespondence_type"),
                 CorrespondencePackage.Literals.SAME_TYPE_CORRESPONDENCE__ELEMENT_A,
                 true,
                 false,
                 true,
                 null,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Element B feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addElementBPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_SameTypeCorrespondence_elementB_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_SameTypeCorrespondence_elementB_feature", "_UI_SameTypeCorrespondence_type"),
                 CorrespondencePackage.Literals.SAME_TYPE_CORRESPONDENCE__ELEMENT_B,
                 true,
                 false,
                 true,
                 null,
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
     * This adds a property descriptor for the Value A feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addValueAPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_PartialEAttributeCorrespondence_valueA_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_PartialEAttributeCorrespondence_valueA_feature", "_UI_PartialEAttributeCorrespondence_type"),
                 CorrespondencePackage.Literals.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_A,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Value B feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addValueBPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_PartialEAttributeCorrespondence_valueB_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_PartialEAttributeCorrespondence_valueB_feature", "_UI_PartialEAttributeCorrespondence_type"),
                 CorrespondencePackage.Literals.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_B,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This returns PartialEAttributeCorrespondence.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/PartialEAttributeCorrespondence"));
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        CorrespondenceType labelValue = ((PartialEAttributeCorrespondence<?>)object).getType();
        String label = labelValue == null ? null : labelValue.toString();
        return label == null || label.length() == 0 ?
            getString("_UI_PartialEAttributeCorrespondence_type") :
            getString("_UI_PartialEAttributeCorrespondence_type") + " " + label;
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

        switch (notification.getFeatureID(PartialEAttributeCorrespondence.class)) {
            case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__TYPE:
            case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_A:
            case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_B:
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
