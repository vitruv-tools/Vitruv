package tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;

/**
 * Traverses most PCM Repository elements. Order is not that important.
 *
 * @author Johannes Hoor
 *
 */
public class PCMRepositoryElementSelector extends PCMElementSelector<Repository> {

    protected int renameCtr = 0;

    /**
     * Std.
     */

    public PCMRepositoryElementSelector() {
        super();
        this.extractor = new PCMRepositoryExtractor();

    }

    /**
     * Identify and solve naming conflicts: Finds Components, Interfaces and Methods (in Interfaces
     * and SEFFs).
     */
    @Override
    public void traverseModelAndSolveConflics() {

        // inspect the repository element(s), they have names too
        this.inspectRepositoryElements();
        this.inspectDataTypes();
        // TODO: make access to model features consistent if possible (e.g.
        // structural feature get vs. getX methods)
        for (final Interface s : this.root.getInterfaces__Repository()) {
            final EStructuralFeature nameFeature = s.eClass().getEAllStructuralFeatures().get(1);
            final String iname = (String) s.eGet(nameFeature);
            if (this.checkForNameConflict(iname)) {
                // TODO find better place for this
                // this.logger.info("-identified@interface " + s.getEntityName());

                s.setEntityName(this.renameElement(iname));
            }

            // method names and parameter names
            final EStructuralFeature sig = s.eClass().getEAllStructuralFeatures().get(6);
            final EObjectContainmentWithInverseEList<?> list = (EObjectContainmentWithInverseEList<?>) s.eGet(sig);
            for (int i = 0; i < list.size(); i++) {
                final OperationSignature opsig = (OperationSignature) list.get(i);
                final String mname = opsig.getEntityName();

                if (this.checkForNameConflict(mname)) {
                    // TODO find better place for this
                    // this.logger.info("-identified@signature " + opsig.getEntityName());
                    opsig.setEntityName(this.renameElement(mname));
                }
                this.inspectParameterNames(opsig);
            }
        }

        for (final RepositoryComponent s : this.root.getComponents__Repository()) {
            final EStructuralFeature nameFeature = s.eClass().getEAllStructuralFeatures().get(1);
            final String cname = (String) s.eGet(nameFeature);

            if (this.checkForNameConflict(cname)) {
                // TODO find better place for this
                // this.logger.info("-identified@component " + s.getEntityName());

                s.setEntityName(this.renameElement(cname));
            }

            this.inspectRoleNames(s);

            if (s instanceof CompositeComponent) {
                inspectAssemblyContext((CompositeComponent) s);
                inspectConnectors((CompositeComponent) s);
            }

        }

    }

    /**
     * Checks also Connectors (Composite Component only!)
     * 
     * @param the
     *            composite component
     */
    private void inspectConnectors(CompositeComponent cc) {
        for (Connector c : cc.getConnectors__ComposedStructure()) {
            if (checkForNameConflict(c.getEntityName())) {
                // this.logger.info("-identified@Compcomponent/Connector " + c.getEntityName());
                c.setEntityName(renameElement(c.getEntityName()));
            }
        }
    }

    /**
     * Checks also Assembly Context (Composite Component only!)
     * 
     * @param the
     *            composite component
     */
    private void inspectAssemblyContext(CompositeComponent cc) {
        for (AssemblyContext ac : cc.getAssemblyContexts__ComposedStructure()) {
            if (checkForNameConflict(ac.getEntityName())) {
                // this.logger.info("-identified@Compcomponent/AssemblyContext " +
                // ac.getEntityName());
                ac.setEntityName(renameElement(ac.getEntityName()));
            }
        }
    }

    /**
     * Inspects the repository element(s) in the model file.
     */
    protected void inspectRepositoryElements() {
        // TODO: logger support and testcases
        if (this.checkForNameConflict(this.root.getEntityName())) {
            this.root.setEntityName(this.renameElement(this.root.getEntityName()));
        }
    }

    /**
     * (Optional) Addition that also checks parameter names for certain naming conflicts. TODO: uses
     * old PCM: setParameterName for parameters. change to setEntityName in newer PCM versions
     *
     * @param operation
     *            the operation
     */
    protected void inspectParameterNames(final OperationSignature operation) {
        final List<Parameter> list = operation.getParameters__OperationSignature();
        for (final Parameter p : list) {
            if (this.checkForNameConflict(p.getParameterName())) {
                // TODO find better place for this
                // this.logger.info("-identified@parameter " + p.getParameterName());
                p.setParameterName(this.renameElement(p.getParameterName()));
            }
        }
    }

    /**
     * inspect datatypes and inner declarations (composite datatypes)
     * 
     * TODO: untested as of 20.03.15
     */
    protected void inspectDataTypes() {
        EList<DataType> datatypes = root.getDataTypes__Repository();
        for (final DataType datatype : datatypes) {
            // only composite and collection datatypes have entitynames
            if (datatype instanceof CompositeDataType) {

                if (checkForNameConflict(((CompositeDataType) datatype).getEntityName())) {
                    ((CompositeDataType) datatype).setEntityName(this.renameElement(((CompositeDataType) datatype)
                            .getEntityName()));
                }

                // check their inner declarations
                EList<InnerDeclaration> innerDeclarations = ((CompositeDataType) datatype)
                        .getInnerDeclaration_CompositeDataType();

                for (InnerDeclaration declaration : innerDeclarations) {
                    if (checkForNameConflict(declaration.getEntityName())) {
                        declaration.setEntityName(this.renameElement(declaration.getEntityName()));
                    }
                }
            }

            if (datatype instanceof CollectionDataType) {
                if (checkForNameConflict(((Entity) datatype).getEntityName())) {
                    ((CollectionDataType) datatype).setEntityName(this.renameElement(((CollectionDataType) datatype)
                            .getEntityName()));
                }
            }

        }
    }

    /**
     * Also look for conflicts in Prov/Req Roles. These conflicts should be equal to those of the
     * corresponding interfaces and components. This method should therefor be invoked after
     * checking for interface and component conflicts. --- Consider similar methods for classes that
     * are not subclasses of this class. This method tries to rename the role names so that they are
     * valid java identifiers.
     *
     *
     * RoleNames always contain elements of the corresponding interface AND component. Both must be
     * changed to preserve consistency. Naming Consistency is not necessarily needed, but it is nice
     * to have.
     *
     * @param component
     *            the component
     */
    protected void inspectRoleNames(final RepositoryComponent component) {

        // TODO:logger support and test cases
        for (final ProvidedRole pr : component.getProvidedRoles_InterfaceProvidingEntity()) {
            // Interface inf = getInterfaceForProvidedRole(pr);
            //
            // PCMNameChangeTrace<Interface> infChange =
            // interfaceChanges.get(inf);
            // PCMNameChangeTrace<RepositoryComponent> compChange =
            // componentChanges.get(component);
            //
            //
            // String roleName = pr.getEntityName();
            // //assumes that inf-name != comp-name, --> no conflicts should be
            // on this level: sameidentifier invariant must be true
            // System.out.println(infChange.getPreviousName() +
            // "<--->"+infChange.getCurrentName());
            // roleName = roleName.replace(infChange.getPreviousName(),
            // infChange.getCurrentName());
            // roleName = roleName.replace(compChange.getPreviousName(),
            // compChange.getCurrentName());
            if (this.checkForNameConflict(pr.getEntityName())) {
                final String roleName = pr.getEntityName().substring(0, 8)
                        + this.renameElement(pr.getEntityName().substring(8));
                pr.setEntityName(roleName);
            }

        }

        for (final RequiredRole rr : component.getRequiredRoles_InterfaceRequiringEntity()) {
            // Interface inf = getInterfaceForRequiredRole(rr);
            // PCMNameChangeTrace<Interface> infChange =
            // interfaceChanges.get(inf);
            // PCMNameChangeTrace<RepositoryComponent> compChange =
            // componentChanges.get(component);
            //
            // String roleName = rr.getEntityName();
            // //assumes that inf-name != comp-name, --> no conflicts should be
            // on this level: sameidentifier invariant must be true
            // roleName = roleName.replace(infChange.getPreviousName(),
            // infChange.getCurrentName());
            // roleName = roleName.replace(compChange.getPreviousName(),
            // compChange.getCurrentName());
            if (this.checkForNameConflict(rr.getEntityName())) {
                final String roleName = rr.getEntityName().substring(0, 9)
                        + this.renameElement(rr.getEntityName().substring(9));
                rr.setEntityName(roleName);
            }

        }

    }

    /**
     * Returns the Interface for a given role.
     *
     * @param role
     *            (prov.)
     * @return the corresponding interface
     */
    protected Interface getInterfaceForProvidedRole(final ProvidedRole role) {
        return (Interface) role.eCrossReferences().get(0); // TODO uncertain is
        // one element is
        // always correct
    }

    /**
     * Returns the Interface for a given role.
     *
     * @param role
     *            (req.)
     * @return the corresponding interface
     */
    protected Interface getInterfaceForRequiredRole(final RequiredRole role) {

        return (Interface) role.eCrossReferences().get(0); // TODO uncertain is
        // one element is
        // always correct;
    }

    /*
     * (non-Javadoc)
     * 
     * @see tools.vitruv.integration.invariantChecker.PCMJaMoPPEnforcer.
     * PCMSimpleTraversalStrategy#loadModelRoot()
     */
    @Override
    public void loadModelRoot() {
        this.root = this.extractor.getImpl(this.parentEnforcer.getModel());

    }

}
