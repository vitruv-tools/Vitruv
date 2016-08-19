package edu.kit.ipd.sdq.vitruvius.integration.pcm.traversal.util;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.system.System;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootFactory;
import edu.kit.ipd.sdq.vitruvius.integration.traversal.util.ChangeBuildHelper;

/**
 * A helper class that provides methods for creating atomic change models from PCM elements
 *
 * @author Sven Leonhardt
 */

public class PCMChangeBuildHelper extends ChangeBuildHelper {

    /**
     * Creates a change model from a Repository element.
     *
     * @param source
     *            : Repository to be traversed
     * @return : CreateRootEObject Change
     */

    /**
     * Creates the change from a repository
     *
     * @param source
     *            the repository to add
     * @return the e change
     */
    public static EChange createChangeFromRepository(final Repository source) {
        return createChangeFromRootEntity(source);
    }

    /**
     * Creates the change from a system.
     *
     * @param source
     *            the source
     * @return the e change
     */
    public static EChange createChangeFromSystem(final System source) {
        return createChangeFromRootEntity(source);
    }

    /**
     * Creates the change from root entity.
     *
     * @param source
     *            the source
     * @return the e change
     */
    public static EChange createChangeFromRootEntity(final Entity source) {

        final InsertRootEObject<EObject> change = RootFactory.eINSTANCE.createInsertRootEObject();
        change.setNewValue(source);
        return change;
    }

    /**
     * Creates a change model from a BasicComponent element.
     *
     * @param source
     *            : BasicComponent to be traversed
     * @return : CreateNonRootEObject Change
     */
    public static EChange createChangeFromBasicComponent(final BasicComponent source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }

    /**
     * Creates the change from composite component.
     *
     * @param source
     *            the source
     * @return the e change
     */
    public static EChange createChangeFromCompositeComponent(final CompositeComponent source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }

    /**
     * Creates the change from interface.
     *
     * @param source
     *            the source
     * @return the e change
     */
    public static EChange createChangeFromInterface(final Interface source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }

    /**
     * Creates the change from role.
     *
     * @param source
     *            the source
     * @return the e change
     */
    public static EChange createChangeFromRole(final Role source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }

    /**
     * Creates the change from connector.
     *
     * @param source
     *            the source
     * @return the e change
     */
    public static EChange createChangeFromConnector(final Connector source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }

    /**
     * Creates the change from data type.
     *
     * @param source
     *            the source
     * @return the e change
     */
    public static EChange createChangeFromDataType(final DataType source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }

    /**
     * Creates the change from inner declaration.
     *
     * @param source
     *            the source
     * @return the e change
     */
    public static EChange createChangeFromInnerDeclaration(final InnerDeclaration source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }

    /**
     * Creates the change from assembly context.
     *
     * @param source
     *            the source
     * @return the e change
     */
    public static EChange createChangeFromAssemblyContext(final AssemblyContext source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }

    /**
     * Creates the change from provides component.
     *
     * @param component
     *            the component
     * @return the e list
     */
    public static EList<EChange> createChangeFromProvidesComponent(final ProvidesComponentType component) {
        final EList<EChange> compositeChanges = new BasicEList<EChange>();

        final InsertEReference<EObject,EObject> componentChange = ReferenceFactory.eINSTANCE
                .createInsertEReference();
        componentChange.setIsCreate(true);
        componentChange.setNewValue(component);

        compositeChanges.add(componentChange);

        for (final ProvidedRole role : component.getProvidedRoles_InterfaceProvidingEntity()) {
            compositeChanges.add(createChangeFromRole(role));
        }

        return compositeChanges;
    }

    /**
     * Creates the change from complete component.
     *
     * @param component
     *            the component
     * @return the e list
     */
    public static EList<EChange> createChangeFromCompleteComponent(final CompleteComponentType component) {
        final EList<EChange> compositeChanges = new BasicEList<EChange>();

        final InsertEReference<EObject,EObject> componentChange = ReferenceFactory.eINSTANCE
                .createInsertEReference();
        componentChange.setIsCreate(true);
        componentChange.setNewValue(component);

        compositeChanges.add(componentChange);

        for (final ProvidedRole role : component.getProvidedRoles_InterfaceProvidingEntity()) {
            compositeChanges.add(createChangeFromRole(role));
        }
        for (final RequiredRole role : component.getRequiredRoles_InterfaceRequiringEntity()) {
            compositeChanges.add(createChangeFromRole(role));
        }

        return compositeChanges;
    }

    /**
     * Creates the change from signature.
     *
     * @param source
     *            the source
     * @return the e change
     */
    public static EChange createChangeFromSignature(final OperationSignature source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }

    /**
     * Creates the change from parameter.
     *
     * @param source
     *            the source
     * @return the e change
     */
    public static EChange createChangeFromParameter(final Parameter source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }

    /**
     * Creates the changes from roles and delegations.
     *
     * @param innerComp
     *            the inner comp
     * @return the e list
     */
    public static EList<EChange> createChangesFromRolesAndDelegations(
            final ComposedProvidingRequiringEntity innerComp) {

        final EList<ProvidedRole> providedRoles = innerComp.getProvidedRoles_InterfaceProvidingEntity();
        final EList<RequiredRole> requiredRoles = innerComp.getRequiredRoles_InterfaceRequiringEntity();
        final EList<Connector> connectors = innerComp.getConnectors__ComposedStructure();

        final EList<EChange> changes = new BasicEList<EChange>();

        for (final ProvidedRole role : providedRoles) {
            changes.add(createChangeFromRole(role));

            for (final Connector connector : connectors) {
                if (connector instanceof ProvidedDelegationConnector && ((ProvidedDelegationConnector) connector)
                        .getOuterProvidedRole_ProvidedDelegationConnector() == role) {
                    changes.add(createChangeFromConnector(connector));
                }
            }
        }

        for (final RequiredRole role : requiredRoles) {
            changes.add(createChangeFromRole(role));

            for (final Connector connector : connectors) {
                if (connector instanceof RequiredDelegationConnector && ((RequiredDelegationConnector) connector)
                        .getOuterRequiredRole_RequiredDelegationConnector() == role) {
                    changes.add(createChangeFromConnector(connector));
                }
            }
        }

        return changes;

    }

}
