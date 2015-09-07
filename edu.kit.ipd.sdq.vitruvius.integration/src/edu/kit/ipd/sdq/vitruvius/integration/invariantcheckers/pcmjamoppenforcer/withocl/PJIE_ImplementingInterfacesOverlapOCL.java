package edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.pcmjamoppenforcer.withocl;

//package edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.PCMJaMoPPEnforcer.WithOCL;
//
//import java.util.Set;
//
//import org.eclipse.emf.ecore.EClass;
//import org.eclipse.emf.ecore.EClassifier;
//import org.eclipse.emf.ecore.EObject;
//import org.eclipse.ocl.ParserException;
//import org.eclipse.ocl.Query;
//import org.eclipse.ocl.ecore.Constraint;
//import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
//import org.eclipse.ocl.ecore.OCL;
//import org.eclipse.ocl.expressions.OCLExpression;
//import org.eclipse.ocl.helper.OCLHelper;
//
//import de.uka.ipd.sdq.pcm.repository.RepositoryPackage;
//import edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.PCMJaMoPPEnforcer.PCMtoJaMoPPInvariantEnforcer;
///**
// * Not yet implemented
// * @author usr
// *
// */
//public class PJIE_ImplementingInterfacesOverlapOCL extends PCMtoJaMoPPInvariantEnforcer{
//
//	public PJIE_ImplementingInterfacesOverlapOCL(String id) {
//		super(id);
//	}
//
//	@Override
//	public void enforceInvariant() {
//		// TODO Auto-generated method stub
//
//	}
//
//	private Set<EObject> executeOCLQuery() throws ParserException {
//		// preparation stage
//			OCL ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
//			OCLHelper<EClassifier, ?, ?, Constraint> helper = ocl.createOCLHelper();
//			helper.setContext(RepositoryPackage.Literals.REPOSITORY);
//			OCLExpression<EClassifier> query = null;
//
//			// expression
//			String queryExpression = "";
//
//			// create Query
//			query = helper.createQuery(queryExpression);
//
//			// the result set
//			Set<EObject> result;
//
//			// execute query
//			Query<EClassifier, EClass, EObject> eval = ocl.createQuery(query);
//			result = (Set<EObject>) eval.evaluate(root);
//
//
//
//		return null;
//	}
//
// }
