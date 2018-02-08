/**
 * 
 */
package tools.vitruv.extensions.changevisualization.utils;

import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.palladiosimulator.pcm.core.entity.NamedElement;

import tools.vitruv.framework.change.description.VitruviusChange;

/**
 * This class is only used by the table visualization, potentially unneeded at the end.
 * Many of the information is gathered incorrectly. It was the first approach without
 * a deeper knowledge.
 * 
 * Do not consider for code review.
 * 
 * @author Andreas Löffler
 *
 */
public final class ModelHelper {

	private ModelHelper() {
		//Should not be called
	}
	
	public static String getOldValue(EObject eObject) {		
		return "old";
	}

	public static String getNewValue(EObject eObject) {
		return "new";
	}
	
	public static String getName(EObject eObject) {
		if(eObject instanceof NamedElement) {
			return ((NamedElement)eObject).getEntityName();
		}else if(eObject instanceof org.emftext.language.java.commons.NamedElement) {
			return ((org.emftext.language.java.commons.NamedElement)eObject).getName();
		}else{
			if(!inspectedClasses.contains(eObject.getClass())) {
				//inspectClass(eObject.getClass());
			}
			return "name";	
		}
	}
	
	public static void printHierarchy(Class clstype, int classdepth ){
		// Print the class name passed from main
		System.out.println( "Class depth is: " + classdepth + " Class Name is: " + clstype.getName() );
		
		printInterfaces(clstype);
		
		// Recurse to get super class details
		if ( clstype.getSuperclass() != null ){
			// Get all the details 
			printHierarchy( clstype.getSuperclass(), classdepth + 1 );			
		}		
	}	

	public static void printInterfaces(Class clstype) {
		Class[] interfaces = clstype.getInterfaces();
		if(interfaces!=null) {
			for(Class iface:interfaces) {
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
	
	private static HashSet<Class> inspectedClasses=new HashSet<Class>();
	private static HashSet<Class> inspectedEChangeClasses=new HashSet<Class>();
	
	private static void inspectClass(Class cl) {
		if(inspectedClasses.contains(cl)) {
			return;//Inspect only once
		}
		printHierarchy(cl,0);		
	}
	
	public static void inspectEChangeClass(Class cl) {
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
