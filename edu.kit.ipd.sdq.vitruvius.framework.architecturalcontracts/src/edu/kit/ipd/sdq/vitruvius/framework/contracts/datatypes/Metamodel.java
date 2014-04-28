package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.DefaultTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TUIDCalculatorAndResolver;

public class Metamodel extends AbstractURIHaving {

    private String[] fileExtensions;
    private TUIDCalculatorAndResolver tuidCalculatorAndResolver;
    private String nsURI;

    public Metamodel(final String nsURI, final VURI uri, final String... fileExtensions) {
        this(nsURI, uri, new DefaultTUIDCalculatorAndResolver(), fileExtensions);
    }

    public Metamodel(final String nsURI, final VURI uri, final TUIDCalculatorAndResolver tuidCalculatorAndResolver,
            final String... fileExtensions) {
        super(uri);
        this.fileExtensions = fileExtensions;
        this.tuidCalculatorAndResolver = tuidCalculatorAndResolver;
        this.nsURI = nsURI;
    }

    public String[] getFileExtensions() {
        return this.fileExtensions;
    }

    public String getTUID(final EObject eObject) {
        return this.tuidCalculatorAndResolver.getTUID(eObject);
    }

    public boolean hasMetaclassInstance(final EObject eObject) {
        if (null == eObject || null == eObject.eClass() || null == eObject.eClass().getEPackage()
                || null == eObject.eClass().getEPackage().getNsURI()) {
            return false;
        }
        return eObject.eClass().getEPackage().getNsURI().startsWith(this.nsURI);
    }
}
