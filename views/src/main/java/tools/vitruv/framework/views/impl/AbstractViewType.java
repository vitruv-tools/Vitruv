package tools.vitruv.framework.views.impl;

import org.eclipse.emf.ecore.EPackage;

import static com.google.common.base.Preconditions.checkArgument;

import tools.vitruv.framework.views.ViewSelector;

public abstract class AbstractViewType<S extends ViewSelector, Id> implements ViewCreatingViewType<S, Id> {
    private final String name;
    private final EPackage metamodel;

    public AbstractViewType(String name) {
        checkArgument(name != null, "view type name must not be null");
        this.name = name;
        this.metamodel = null;
    }

    public AbstractViewType(String name, EPackage metamodel) {
        checkArgument(name != null, "view type name must not be null");
        checkArgument(metamodel != null, "view type metamodel must not be null");
        this.name = name;
        this.metamodel = metamodel;
    }

    public String getName() {
        return this.name;
    }

    public EPackage getMetamodel() {
        return this.metamodel;
    }
}