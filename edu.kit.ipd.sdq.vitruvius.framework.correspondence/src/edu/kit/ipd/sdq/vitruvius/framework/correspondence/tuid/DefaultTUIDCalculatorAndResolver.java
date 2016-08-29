package edu.kit.ipd.sdq.vitruvius.framework.correspondence.tuid;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;

public class DefaultTUIDCalculatorAndResolver extends AttributeTUIDCalculatorAndResolver {
    public DefaultTUIDCalculatorAndResolver(final String tuidPrefix) {
        super(tuidPrefix, VitruviusConstants.getDefaultNameOfIdentifierFeature(),
                VitruviusConstants.getDefaultNameOfNameFeature());
    }

    public DefaultTUIDCalculatorAndResolver(final String tuidPrefix, final String nameOfIDFeature) {
        super(tuidPrefix, nameOfIDFeature, VitruviusConstants.getDefaultNameOfNameFeature());
    }

    public DefaultTUIDCalculatorAndResolver(final String tuidPrefix, final String nameOfIDFeature,
            final String nameOfNameFeautre, final String... fileExtensions) {
        super(tuidPrefix);
    }

    /**
     * The default TUID calculator can be used for any EObject as long as it either has an ID or a
     * parent object with an ID
     */
    @Override
    protected Class<EObject> getRootObjectClass() {
        return EObject.class;
    }

}
