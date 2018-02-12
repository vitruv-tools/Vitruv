package tools.vitruv.extensions.changevisualization.utils;

import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import tools.vitruv.framework.change.description.VitruviusChange;

/**
 * Utility class that does some useful general tasks regarding eCore Models
 * 
 * @author Andreas Loeffler
 *
 */
public final class ModelHelper {

	private ModelHelper() {
		//Should not be called
	}

	/**
	 * Returns if the given structural feature of the given eObject exists
	 * 
	 * @param eObject The eObject, not null
	 * @param sfName The name of the structural feature, not null
	 * @return true if structural feature exists, false otherwise
	 */
	public static boolean hasStructuralFeature(EObject eObject, String sfName) {
		if(eObject==null||sfName==null) return false;
		EList<EStructuralFeature> features = eObject.eClass().getEAllStructuralFeatures();
		for(EStructuralFeature feature:features) {
			if(feature==null) continue;
			if(sfName.equals(feature.getName())){
				return true;				
			}
		}
		return false;
	}

	/**
	 * Returns the value of the given structural feature of the given eObject.
	 * Feature not existent returns null, also the feature value==null will return null. 
	 * If it is necessary to distinguish between the two, use hasStructuralFeature
	 * 
	 * @param eObject The eObject, nut null
	 * @param sfName The name of the structural feature, not null
	 * @return The associated value, might be null
	 */
	public static Object getStructuralFeature(EObject eObject, String sfName) {
		if(eObject==null||sfName==null) return null;
		EList<EStructuralFeature> features = eObject.eClass().getEAllStructuralFeatures();
		for(EStructuralFeature feature:features) {
			if(feature==null) continue;
			if(sfName.equals(feature.getName())){
				return eObject.eGet(feature);				
			}
		}
		return null;
	}

	/* Code below is either unneeded or should at least be revised
	 * Most of this class is only used by the table visualization, potentially unneeded at the end.
	 * Many of the information is gathered incorrectly. It was the first approach without
	 * a deeper knowledge.
	 * 
	 * Do not consider for code review.
	 */

	public static String getOldValue(EObject eObject) {		
		return "old";
	}

	public static String getNewValue(EObject eObject) {
		return "new";
	}

	public static String getName(EObject eObject) {
		return "name";			
	}


	public static void printHierarchy(Class<?> clstype, int classdepth ){
		// Print the class name passed from main
		System.out.println( "Class depth is: " + classdepth + " Class Name is: " + clstype.getName() );

		printInterfaces(clstype);

		// Recurse to get super class details
		if ( clstype.getSuperclass() != null ){
			// Get all the details 
			printHierarchy( clstype.getSuperclass(), classdepth + 1 );			
		}		
	}	

	public static void printInterfaces(Class<?> clstype) {
		Class<?>[] interfaces = clstype.getInterfaces();
		if(interfaces!=null) {
			for(Class<?> iface:interfaces) {
				System.out.println( "   - Interface " + iface.getName() );
			}
		}
	}

	public static EObject getRootObject(VitruviusChange vChange){
		Iterable<EObject> eObjects = vChange.getAffectedEObjects();
		Iterator<EObject> iterator = eObjects.iterator();
		if(iterator.hasNext()) {
			EObject eObject = iterator.next();
			return EcoreUtil.getRootContainer(eObject);
		}else {
			return null;
		}
	}

	public static String getRootObjectName(VitruviusChange vChange){
		EObject root = getRootObject(vChange);
		if(root==null) {
			return "none";
		}else {
			return getName(root);
		}
	}

	public static String getRootObjectID(VitruviusChange vChange){
		EObject root = getRootObject(vChange);
		if(root==null) {
			return "none";
		}else {
			return getID(root);
		}
	}

	public static String getID(EObject eObject) {
		if(eObject.getClass().getName().startsWith("org.palladiosimulator.pcm")) {
			return eObject.getClass().getSimpleName()+"_"+getName(eObject);
		}else if(eObject.getClass().getName().startsWith("org.emftext.language.java")) {
			return eObject.getClass().getSimpleName()+"_"+getName(eObject);
		}else {
			return "HC"+eObject.hashCode();
		}
	}

	//private static HashSet<Class<?>> inspectedClasses=new HashSet<Class<?>>();
	private static HashSet<Class<?>> inspectedEChangeClasses=new HashSet<Class<?>>();

//	private static void inspectClass(Class<?> cl) {
//		if(inspectedClasses.contains(cl)) {
//			return;//Inspect only once
//		}
//		printHierarchy(cl,0);		
//	}

	public static void inspectEChangeClass(Class<?> cl) {
		if(inspectedEChangeClasses.contains(cl)) {
			return;//Inspect only once
		}
		inspectedEChangeClasses.add(cl);	
	}

	public static String getModel(EObject eObject) {

		if(eObject.getClass().getName().startsWith("org.palladiosimulator.pcm")) {
			return "PCM";
		}else if(eObject.getClass().getName().startsWith("org.emftext.language.java")) {
			return "Java";
		}else if(eObject.getClass().getName().startsWith("tools.vitruv.dsls.reactions")) {
			return "Reactions";
		}else if(eObject.getClass().getName().startsWith("tools.vitruv.framework.correspondence")) {
			return "Correspondence";
		}else {		
			return eObject.getClass().getName();
		}
	}	

}
