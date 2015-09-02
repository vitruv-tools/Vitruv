package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.HierarchicalTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;

public class DefaultTUIDCalculatorAndResolver extends HierarchicalTUIDCalculatorAndResolver<EObject> {
    private String nameOfIdFeature;
    private String nameOfNameFeauture;

    public DefaultTUIDCalculatorAndResolver(final String tuidPrefix) {
        this(tuidPrefix, VitruviusConstants.getDefaultNameOfIdentifierFeature(),
                VitruviusConstants.getDefaultNameOfNameFeature());
    }

    public DefaultTUIDCalculatorAndResolver(final String tuidPrefix, final String nameOfIDFeature) {
        this(tuidPrefix, nameOfIDFeature, VitruviusConstants.getDefaultNameOfNameFeature());
    }

    public DefaultTUIDCalculatorAndResolver(final String tuidPrefix, final String nameOfIDFeature,
            final String nameOfNameFeautre, final String... fileExtensions) {
        super(tuidPrefix);
        this.nameOfIdFeature = nameOfIDFeature;
        this.nameOfNameFeauture = nameOfNameFeautre;
    }

    /**
     * The default TUID calculator can be used for any EObject as long as it either has an ID or a
     * parent object with an ID
     */
    @Override
    protected Class<EObject> getRootObjectClass() {
        return EObject.class;
    }

    @Override
    protected boolean hasId(final EObject obj, final String indidivualId) throws IllegalArgumentException {
        return indidivualId.equals(calculateIndividualTUIDDelegator(obj));
    }

    @Override
    protected String calculateIndividualTUIDDelegator(final EObject obj) throws IllegalArgumentException {
        String id = getValueOfIdAttribute(obj);
        if (null == id) {
            id = getValueOfNameAttribute(obj);
        }
        if (null == id) {
            throw new RuntimeException("The DefaultTUIDCalculator cannot be used for the eObject '" + obj + "'");
        }
        return id;
    }

    private String getValueOfIdAttribute(final EObject eObject) {
        return getValueOfAttribute(eObject, this.nameOfIdFeature);
    }

    private String getValueOfNameAttribute(final EObject obj) {
        return getValueOfAttribute(obj, this.nameOfNameFeauture);
    }

    private String getValueOfAttribute(final EObject eObject, final String featureName) {
        EStructuralFeature idFeature = eObject.eClass().getEStructuralFeature(featureName);
        if (idFeature != null && idFeature instanceof EAttribute) {
            EAttribute idAttribute = (EAttribute) idFeature;
            EDataType eAttributeType = idAttribute.getEAttributeType();
            EDataType eString = EcorePackage.eINSTANCE.getEString();
            if (eString != null && eString.equals(eAttributeType)) {
                return (String) eObject.eGet(idFeature);
            }
            EDataType eInt = EcorePackage.eINSTANCE.getEInt();
            if (eInt != null && eString.equals(eAttributeType)) {
                return String.valueOf(eObject.eGet(idFeature));
            }
        }
        return null;
    }

}
