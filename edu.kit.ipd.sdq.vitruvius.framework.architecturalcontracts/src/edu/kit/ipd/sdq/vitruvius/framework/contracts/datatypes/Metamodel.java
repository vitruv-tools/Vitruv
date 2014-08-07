package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.DefaultTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TUIDCalculatorAndResolver;

public class Metamodel extends AbstractURIHaving {

    private String[] fileExtensions;
    private TUIDCalculatorAndResolver tuidCalculatorAndResolver;
    private Set<String> nsURIs;

    public Metamodel(final String nsURI, final VURI uri, final String... fileExtensions) {
        this(new HashSet<String>(Arrays.asList(nsURI)), uri, new DefaultTUIDCalculatorAndResolver(), fileExtensions);
    }

    public Metamodel(final Set<String> nsURIs, final VURI uri, final String... fileExtensions) {
        this(nsURIs, uri, new DefaultTUIDCalculatorAndResolver(), fileExtensions);
    }

    public Metamodel(final String nsURI, final String nameOfIDFeature, final VURI uri, final String... fileExtensions) {
        this(new HashSet<String>(Arrays.asList(nsURI)), uri, new DefaultTUIDCalculatorAndResolver(nameOfIDFeature),
                fileExtensions);
    }

    public Metamodel(final Set<String> nsURIs, final String nameOfIDFeature, final VURI uri,
            final String... fileExtensions) {
        this(nsURIs, uri, new DefaultTUIDCalculatorAndResolver(nameOfIDFeature), fileExtensions);
    }

    public Metamodel(final String nsURI, final VURI uri, final TUIDCalculatorAndResolver tuidCalculatorAndResolver,
            final String... fileExtensions) {
        this(new HashSet<String>(Arrays.asList(nsURI)), uri, tuidCalculatorAndResolver, fileExtensions);
    }

    public Metamodel(final Set<String> nsURIs, final VURI uri,
            final TUIDCalculatorAndResolver tuidCalculatorAndResolver, final String... fileExtensions) {
        super(uri);
        this.fileExtensions = fileExtensions;
        this.tuidCalculatorAndResolver = tuidCalculatorAndResolver;
        this.nsURIs = nsURIs;
    }

    public String[] getFileExtensions() {
        return this.fileExtensions;
    }

    public String calculateTUIDFromEObject(final EObject eObject) {
        return this.tuidCalculatorAndResolver.calculateTUIDFromEObject(eObject);
    }

    public VURI getModelVURIContainingIdentifiedEObject(final String tuid) {
        return this.tuidCalculatorAndResolver.getModelVURIContainingIdentifiedEObject(tuid);
    }

    public EObject resolveEObjectFromRootAndFullTUID(final EObject root, final String tuid) {
        return this.tuidCalculatorAndResolver.resolveEObjectFromRootAndFullTUID(root, tuid);
    }

    public boolean hasMetaclassInstance(final EObject eObject) {
        if (null == eObject || null == eObject.eClass() || null == eObject.eClass().getEPackage()
                || null == eObject.eClass().getEPackage().getNsURI()) {
            return false;
        }
        return this.nsURIs.contains(eObject.eClass().getEPackage().getNsURI());
    }

    public boolean hasTUID(final String tuid) {
        return this.tuidCalculatorAndResolver.isValidTUID(tuid);
    }

    public Set<String> getNsURIs() {
        return this.nsURIs;
    }
}
