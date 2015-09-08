package edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.pcmjamoppenforcer.withocl;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;

import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.PCMRepositorytoJaMoPPInvariantEnforcer;

 
/**
 * Example for an invariante enforcer that uses ocl queries to identify conflicting model elements.
 *
 * @author Johannes Hoor
 *
 */
public class PJIE_BeginCharOCL extends PCMRepositorytoJaMoPPInvariantEnforcer {

    private int renameCtr = 0;

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.FixedInvariantEnforcer#enforceInvariant
     * ()
     */
    @Override
    public void enforceInvariant() {
        try {
            for (final EObject e : this.executeOCLQuery()) {
                if (e instanceof Entity) {
                    ((Entity) e).setEntityName(this.renameElement(((Entity) e).getEntityName()));
                } else if (e instanceof Parameter) {
                    // case for old pcm definition of parameters
                    ((Parameter) e).setParameterName(this.renameElement(((Parameter) e).getParameterName()));
                }
            }
        } catch (final ParserException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rename element.
     *
     * @param element
     *            the element
     * @return the string
     */
    protected String renameElement(final String element) {
        final String ret = "RN" + this.renameCtr + "_" + element.substring(1);
        this.renameCtr++;
        return ret;
    }

    /**
     * Execute ocl query.
     *
     * @return the sets the
     * @throws ParserException
     *             the parser exception
     */
    @SuppressWarnings("unchecked")
    private Set<EObject> executeOCLQuery() throws ParserException {
        // preparation stage
        final OCL ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
        final OCLHelper<EClassifier, ?, ?, Constraint> helper = ocl.createOCLHelper();
        helper.setContext(RepositoryPackage.Literals.REPOSITORY);
        OCLExpression<EClassifier> query = null;

        // searchs for components that do not start with azAZ_
        String queryExpression = "self.components__Repository->select(c : RepositoryComponent | c.entityName.matches('[^a-zA-Z_$].*'))";

        // create Query
        query = helper.createQuery(queryExpression);

        // the result set
        Set<EObject> result;

        // execute query
        Query<EClassifier, EClass, EObject> eval = ocl.createQuery(query);
        result = (Set<EObject>) eval.evaluate(this.root);

        // now interfaces...
        queryExpression = "self.interfaces__Repository->select(c : Interface | c.entityName.matches('[^a-zA-Z_$].*'))";
        eval = ocl.createQuery(helper.createQuery(queryExpression));
        result.addAll((Collection<? extends EObject>) eval.evaluate(this.root));

        // methods
        queryExpression = "self.interfaces__Repository->select(true).oclAsType(OperationInterface).signatures__OperationInterface->select( m : OperationSignature | m.entityName.matches('[^a-zA-Z_$].*'))";
        eval = ocl.createQuery(helper.createQuery(queryExpression));
        result.addAll((Collection<? extends EObject>) eval.evaluate(this.root));

        // parameters
        queryExpression = "self.interfaces__Repository->select(true).oclAsType(OperationInterface).signatures__OperationInterface->select( true).parameters__OperationSignature->select( o : Parameter | o.entityName.matches('[^a-zA-Z_$].*'))";
        eval = ocl.createQuery(helper.createQuery(queryExpression));
        result.addAll((Collection<? extends EObject>) eval.evaluate(this.root));

        return result;

    }

}
