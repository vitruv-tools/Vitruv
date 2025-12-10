package tools.vitruv.framework.views.impl;

import org.eclipse.emf.ecore.EPackage;

import static com.google.common.base.Preconditions.checkArgument;

import tools.vitruv.framework.views.ViewSelector;

public abstract class AbstractViewType<S extends ViewSelector, Id> implements ViewCreatingViewType<S, Id> {
    private final String name;
    private final EPackage metamodel;

    /**
     * constructor for view types without metamodel
     * 
     * @param name
     */
    public AbstractViewType(String name) {
        checkArgument(name != null, "view type name must not be null");
        this.name = name;
        this.metamodel = null;
    }

    /**
     * constructor for view types with metamodel
     * 
     * @param name
     * @param metamodel
     */
    public AbstractViewType(String name, EPackage metamodel) {
        checkArgument(name != null, "view type name must not be null");
        checkArgument(metamodel != null, "view type metamodel must not be null");
        this.name = name;
        this.metamodel = metamodel;
    }

    /**
     * Returns the name of this view type.
     * 
     * @return the name of this view type
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the metamodel of this view type.
     * 
     * @return the metamodel of this view type
     */
    public EPackage getMetamodel() {
        return this.metamodel;
    }
}