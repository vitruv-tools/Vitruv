package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.commons.Commentable;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.Parametrizable;
import org.emftext.language.java.references.Argumentable;
import org.emftext.language.java.resource.java.mopp.JavaPrinter2;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypedElement;

/**
 * utility methods when working from element from the java emf model.
 * 
 * @author Jonas Kunz
 *
 */
public final class JamoppModelUtils {
	
	private JamoppModelUtils() {}
	
	public static boolean isJamoppObject(EObject element) {
		return element instanceof Commentable;
	}
	
	/**
	 * @param elem
	 * 		the element ot get the code of
	 * @return
	 * 		a String containing the javacode represented by the given element.
	 */
	public static String getJavaCodeOfElement(EObject elem) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JavaPrinter2 printer = new JavaPrinter2(baos, null);
		try {
			printer.print(elem);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return baos.toString();
	}
	
	
	/**
	 * Returns the fully qualified name for the given element.
	 * If the element is a method or constructor, an simplified parameterlist (e.g. packages are removed from parameter types) is added.
	 * 
	 * @param element
	 * 		the element whose name to get
	 * @return
	 * 		the name
	 */
	public static String getFullElementNameWithSignature(NamedElement element){
		String name = getFullElementName(element);
		if (element instanceof TypedElement) {
			Type type = ((TypedElement) element).getTypeReference().getTarget();
			if (type != null) {
				name = getUserFriendlyTypeName(type) +" " +name;
			}
		}
		if (element instanceof Parametrizable) {
			Parametrizable method = (Parametrizable)element;
			name += "(";
			List<Parameter> parameters = method.getParameters();
			for (int i = 0; i < parameters.size(); i++) {
				Parameter param = parameters.get(i);
				if (i != 0) {
					name += ", ";
				}
				Type type = param.getTypeReference().getTarget();
				name += getUserFriendlyTypeName(type) + " " + param.getName();
				
			}
			name += ")";
		}
		return name;
	}
	
	/**
	 * Returns an easily readable name for the type.
	 * The name returned is for example the name of the class of the type without package or int, boolean..)
	 * @param type
	 * @return
	 */
	public static String getUserFriendlyTypeName(Type type) {
		if (type instanceof NamedElement) {
			return ((NamedElement)type).getName();
		} else if (type instanceof org.emftext.language.java.types.Boolean) {
			return "boolean";
		} else if (type instanceof org.emftext.language.java.types.Byte) {
			return "byte";
		} else if (type instanceof org.emftext.language.java.types.Char) {
			return "char";
		} else if (type instanceof org.emftext.language.java.types.Double) {
			return "double";
		} else if (type instanceof org.emftext.language.java.types.Float) {
			return "float";
		} else if (type instanceof org.emftext.language.java.types.Int) {
			return "int";
		} else if (type instanceof org.emftext.language.java.types.Long) {
			return "long";
		} else if (type instanceof org.emftext.language.java.types.Short) {
			return "short";
		} else if (type instanceof org.emftext.language.java.types.Void) {
			return "void";
		}
		return type.toString();
	}
	
	/**
	 * Returns the fully qualified name for the given element.
	 * 
	 * @param element
	 * 		the element whose name to get
	 * @return
	 * 		the name
	 */
	public static String getFullElementName(NamedElement element) {
		if (element instanceof CompilationUnit) {
			return getCompilationUnitPackage((CompilationUnit)element);
		} else {
			NamedElement parent = element.getParentByType(NamedElement.class);			
			String parentName = "";
			if(parent != null) {
				parentName = getFullElementName(parent);
			}
			if(parentName.length() > 0) {
				return parentName+"."+element.getName();
			} else {
				return element.getName();				
			}
		}
	}
	
	/**
	 * Finds an element with the given name.
	 * The element itself and all its contents are considered, 
	 * if there are several elements with the same name, only the first one is returned.
	 * 
	 * For Example:
	 * 		my.test.package.ClassA.method1 will return the Method "method1" of ClassA in my.test.package
	 * 
	 * @param parentOrPossibleTarget
	 * 		the object to begin with
	 * @param name
	 * 		the fully qualified name of the element to find
	 * @return
	 * 		the found element or null if it was not found
	 */
	public static NamedElement findElementByName(EObject parentOrPossibleTarget, String name) {			
		if (parentOrPossibleTarget instanceof NamedElement) {
			String parentName = ((NamedElement) parentOrPossibleTarget).getName();
			if (name.equals(parentName)) {
				return (NamedElement) parentOrPossibleTarget;
			}
			//if the name doesnt start with our name the lement is not in this branch
			if (!name.startsWith(parentName + ".")) {
				return null;
			}
			//remove our name from the search pattern
			name = name.substring((parentName + ".").length());		
		}		
		List<EObject> children = EMFUtils.listDirectContents(parentOrPossibleTarget, EObject.class);
		for (EObject child : children) {		
			NamedElement foundElement = findElementByName(child, name);
			if (foundElement != null) {
				return foundElement;
			}
		}
		return null;
	}
	
	/**
	 * Finds all elements with the given name.
	 * The element itself and all its contents are considered, 
	 * 
	 * For Example:
	 * 		my.test.package.ClassA.method1 will return the Method "method1" of ClassA in my.test.package
	 * 
	 * @param name
	 * 		the fully qualified name of the elements to find
	 * @return
	 * 		a list of all elements found
	 */
	public static List<NamedElement> findAllElementsByName(EObject parentOrPossibleTarget, String name) {	
		List<NamedElement> foundElements = new ArrayList<NamedElement>();
		findAllElementsByName(parentOrPossibleTarget, name, foundElements);
		return foundElements;
	}

	/**
	 * Finds all elements with the given name.
	 * The element itself and all its contents are considered, 
	 * 
	 * For Example:
	 * 		my.test.package.ClassA.method1 will return the Method "method1" of ClassA in my.test.package
	 * 
	 * @param parentOrPossibleTarget
	 * 		the object to begin with
	 * @param name
	 * 		the fully qualified name of the elements to find
	 * @param foundElements
	 * 		the list where the found elements are appended
	 * @return
	 * 		a list of all elements found
	 */
	public static void findAllElementsByName(EObject parentOrPossibleTarget, String name, List<NamedElement> foundElements) {			
		if (parentOrPossibleTarget instanceof NamedElement) {
			String parentName = ((NamedElement) parentOrPossibleTarget).getName();
			if (name.equals(parentName)) {
				foundElements.add((NamedElement) parentOrPossibleTarget);
				return;
			}
			//if the name doesnt start with our name the lement is not in this branch
			if (!name.startsWith(parentName + ".")) {
				return;
			}
			//remove our name from the search pattern
			name = name.substring((parentName + ".").length());		
		}		
		List<EObject> children = EMFUtils.listDirectContents(parentOrPossibleTarget, EObject.class);
		for (EObject child : children) {		
			findAllElementsByName(child, name, foundElements);
		}
	}
	
	
	/**
	 * Returns the package of the given Compilation unit.
	 * the returned String does NOT end with a dot.
	 * @param unit
	 * 		the unit to get the package of
	 * @return
	 * 		the package
	 */
	public static String getCompilationUnitPackage(CompilationUnit unit) {
		String packageStr = unit.getNamespacesAsString();
		if (packageStr.length() > 0) {
			return packageStr.substring(0, packageStr.length()-1);				
		} else {
			return "";
		}
	}
	
	/**
	 * Helper method for checking whether call arguments match a given signature.
	 * @param signature
	 * 		the signature to check
	 * @param call
	 * 		the call to check
	 * @return
	 * 		true if the arguments match the signature, false otherwise
	 */
	public static boolean doesCallMatchSignature(Parametrizable signature, Argumentable call) {
		
		List<Parameter> constructorParameters = signature.getParameters();
		
		List<Type> callArgumentTypes = call.getArgumentTypes();
		if (constructorParameters.size() != callArgumentTypes.size()) {
			return false;
		}
		
		List<Type> constructorSignature = new ArrayList<Type>();
		for (Parameter parameter : constructorParameters) {
			Type paramType = parameter.getTypeReference().getTarget();
			constructorSignature.add(paramType);			
		}
		
		for (int i = 0; i < callArgumentTypes.size(); i++) {
			//TypeImpl i;
			Type paramType = constructorSignature.get(i);
			Type argType = callArgumentTypes.get(i);
			//TODO: fix the problem with implicit casts which are not allowed for method calls (e.g object to string)
			//		at the moment the implementation returns some false positives
			if (!argType.isSuperType(0, paramType, null) ) {
				return false;
			}
			
		}
		
		return true;
	}
}
