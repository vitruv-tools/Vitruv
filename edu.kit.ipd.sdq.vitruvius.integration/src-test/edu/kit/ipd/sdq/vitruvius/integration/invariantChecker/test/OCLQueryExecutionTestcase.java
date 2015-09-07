package edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.test;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;

import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryPackage;
import edu.kit.ipd.sdq.vitruvius.integration.util.RepositoryModelLoader;

 

/**
 * The Class OCLQueryExecutionTestcase.
 */
public class OCLQueryExecutionTestcase {

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {

        final OCL ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
        final OCLHelper<EClassifier, ?, ?, Constraint> helper = ocl.createOCLHelper();

        helper.setContext(RepositoryPackage.Literals.REPOSITORY);

        OCLExpression<EClassifier> query = null;

        try {
            query = helper
                    .createQuery("self.components__Repository->select(c : RepositoryComponent | c.entityName.matches('^[a-zA-Z_$]*'))->asSet()");
        } catch (final ParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // String path = "Testmodels/InvalidComponentName.repository";
        final String path = "Testmodels/small_example.repository";
        final Resource r = RepositoryModelLoader.loadPCMResource(path);
        final Repository repo = (Repository) r.getContents().get(0);

        final Query<EClassifier, EClass, EObject> eval = ocl.createQuery(query);

        @SuppressWarnings("unchecked")
        final Set<EObject> result = (Set<EObject>) eval.evaluate(repo);
        for (final Object object : result) {
            System.out.println(object.toString());
        }

    }

}
