package tools.vitruv.framework.vsum.variability;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

public class VaveModelHelper {

	public static EStructuralFeature getStructuralFeatureValue(EObject object, String attrKey, String attrValue) {
		EList<EStructuralFeature> allFeatures = object.eClass().getEAllStructuralFeatures();
		EStructuralFeature feat = null;
		for (EStructuralFeature feature : allFeatures) {
			if (getStructuralFeature(feature, attrKey) == attrValue)
				feat = feature;
		}
		return feat;
	}

	private static Object getStructuralFeature(EObject object, String attrName) {
		Object attr = null;
		try {
			attr = object.eGet(object.eClass().getEStructuralFeature(attrName));
		} catch (NullPointerException e) {
			java.lang.System.out.println(e.getMessage());
		}
		return attr;
	}

	public static EObject findFeature(Resource vaveRes, String featureName) {
		TreeIterator<EObject> iterator = vaveRes.getAllContents();
		EObject childElementIn = null;
		while (iterator.hasNext()) {
			childElementIn = iterator.next();
			if (childElementIn != null && childElementIn.eClass().getName().equals("Variant")) {
				if (getFeatureValue(childElementIn, "name").toString().equals(featureName))
					break;
			}
		}
		return childElementIn;
	}

	private static Object getFeatureValue(EObject object, String attrName) {
		Object attr = object.eGet(object.eClass().getEStructuralFeature(attrName));
		return attr;
	}
}
