package tools.vitruv.extensions.changevisualization.tree.decoder;

import java.awt.Component;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.extensions.changevisualization.ui.LabelValuePanel;
import tools.vitruv.extensions.changevisualization.utils.ModelHelper;

/**
 * Feature decoder suitable for EObjects. It creates a detailedUI that shows all existent
 * structural features and the name of the eClass as simpleText. This text is append by
 * the name or entityName of the given eObject in brackets, if existent
 * 
 * @author Andreas Loeffler
 */
public class EObjectFeatureDecoder implements FeatureDecoder {

	/**
	 * The first structural feature name to look for when extracting the eObject name
	 */
	private static final String FIRST_NAME_SF="entityName";

	/**
	 * The second structural feature name to look for when extracting the eObject name.
	 * This one is only considered if the first on does not exist
	 */
	private static final String SECOND_NAME_SF="name";

	@Override 	
	public Class<?> getDecodedClass(){
		return EObject.class;
	}

	@Override
	public String decodeSimple(Object obj) {
		org.eclipse.emf.ecore.EObject eObj=(org.eclipse.emf.ecore.EObject)obj;
		String name=getName(eObj);
		if(name==null) {
			return eObj.eClass().getName();
		}else {
			return eObj.eClass().getName()+" ["+name+"]";
		}		
	}		

	/**
	 * Extracts the name of the eObject, if existent. It looks for FIRST_NAME_SF and SECOND_NAME_SF
	 * to find it.
	 * 
	 * @param eObj The eObject to get the name of
	 * @return The name, if existent
	 */
	private String getName(EObject eObj) {
		String firstName=null;
		String secondName=null;

		for (EStructuralFeature feature:eObj.eClass().getEAllStructuralFeatures()) {
			if(feature==null) {
				continue;
			}
			if(feature.getName().equals(FIRST_NAME_SF)){
				Object fObj=eObj.eGet(feature);
				firstName=String.valueOf(fObj);
			}
			if(feature.getName().equals(SECOND_NAME_SF)){
				Object fObj=eObj.eGet(feature);
				secondName=String.valueOf(fObj);
			}
		}

		if(firstName!=null) {
			return firstName;
		}else{
			//If secondName!=null, it is returned. Otherwise null is returned as expected
			return secondName;
		}

	}

	@Override
	public String decodeDetailed(Object obj) {
		return null;
	}

	@Override
	public Component decodeDetailedUI(Object obj) {
		return null;
	}

	@Override
	public String[][] decodeDetailedArray(Object obf) {
		return ModelHelper.extractStructuralFeatureArray((EObject)obf);
	}		

}
