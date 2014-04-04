package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.DefaultTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TUIDCalculatorAndResolver;

public class Metamodel extends AbstractURIHaving {

    private String[] fileExtensions;
    private TUIDCalculatorAndResolver tuidCalculatorAndResolver;

    public Metamodel(final VURI uri, final String... fileExtensions) {
        this(uri, new DefaultTUIDCalculatorAndResolver(), fileExtensions);
    }

    public Metamodel(final VURI uri, final TUIDCalculatorAndResolver tuidCalculatorAndResolver, final String... fileExtensions) {
        super(uri);
        this.fileExtensions = fileExtensions;
        this.tuidCalculatorAndResolver = tuidCalculatorAndResolver;
    }

    public String[] getFileExtensions() {
        return this.fileExtensions;
    }

    public String getTUID(final EObject eObject) {
        return this.tuidCalculatorAndResolver.getTUID(eObject);
    }
}
