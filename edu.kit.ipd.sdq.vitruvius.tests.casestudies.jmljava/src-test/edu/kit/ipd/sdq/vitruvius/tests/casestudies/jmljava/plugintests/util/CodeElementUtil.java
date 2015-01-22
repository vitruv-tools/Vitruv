package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

public class CodeElementUtil {

	private final IProject project;
	
	public CodeElementUtil(String projectName) {
		this(ResourcesPlugin.getWorkspace().getRoot().getProject(projectName));
	}
	
	public CodeElementUtil(IProject project) {
		this.project = project;
	}
	
	public Iterable<ICompilationUnit> getCompilationUnits() throws JavaModelException {
		final List<ICompilationUnit> cus = new ArrayList<ICompilationUnit>();
        IJavaProject javaProject = JavaCore.create(project);
        for (IPackageFragment pkg : javaProject.getPackageFragments()) {
        	cus.addAll(Arrays.asList(pkg.getCompilationUnits()));
        }
        return cus;
	}
	
    public ICompilationUnit getCompilationUnit(String compilationUnitName) throws JavaModelException {
        IJavaProject javaProject = JavaCore.create(project);
        for (IPackageFragment pkg : javaProject.getPackageFragments()) {
            for (ICompilationUnit unit : pkg.getCompilationUnits()) {
                if (unit.getElementName().equals(compilationUnitName)) {
                    return unit;
                }
            }
        }
        return null;
    }

    public IType getType(String compilationUnitName, String typeName) throws JavaModelException {
        ICompilationUnit unit = getCompilationUnit(compilationUnitName);
        for (IType iType : unit.getAllTypes()) {
            if (iType.getElementName().equals(typeName)) {
                return iType;
            }
        }
        return null;
    }

    public IMethod getMethod(String compilationUnitName, String typeName, String methodName)
            throws JavaModelException {
    	return getMethod(compilationUnitName, typeName, methodName, null);
    }
    
    public IMethod getMethod(String compilationUnitName, String typeName, String methodName, String parameterTypes)
            throws JavaModelException {
        IType iType = getType(compilationUnitName, typeName);
        if (iType.getElementName().equals(typeName)) {
        	List<IMethod> matchedMethods = new ArrayList<IMethod>();
            for (IJavaElement child : iType.getChildren()) {
                if ((child instanceof IMethod) && child.getElementName().equals(methodName))
                	matchedMethods.add((IMethod)child);
            }
            if (matchedMethods.size() == 1) {
            	return matchedMethods.get(0);
            } else if (matchedMethods.size() > 1 && parameterTypes != null) {
            	for (IMethod method : matchedMethods) {
            		StringBuilder parameterTypesStr = new StringBuilder();
					for (String parameterType : method.getParameterTypes()) {
						parameterTypesStr.append(Signature.toString(parameterType));
					}
					if (parameterTypes.equals(parameterTypesStr.toString())) {
						return method;
					}
            	}
            }
            
        }
        return null;
    }
    
    
    public IField getField(String compilationUnitName, String typeName, String fieldName) throws JavaModelException {
        IType iType = getType(compilationUnitName, typeName);
        if (iType.getElementName().equals(typeName)) {
            for (IJavaElement child : iType.getChildren()) {
                if ((child instanceof IField) && child.getElementName().equals(fieldName))
                    return (IField) child;
            }
        }
        return null;
    }
    
    public ILocalVariable getParameter(String compilationUnitName, String typeName, String methodName, String parameterName) throws JavaModelException {
    	return getParameter(compilationUnitName, typeName, methodName, null, parameterName);
    }
    
    public ILocalVariable getParameter(String compilationUnitName, String typeName, String methodName, String parameterTypes, String parameterName) throws JavaModelException {
    	IMethod method = getMethod(compilationUnitName, typeName, methodName, parameterTypes);
    	if (method == null) {
    		return null;
    	}
    	for (ILocalVariable param : method.getParameters()) {
    		if (param.getElementName().equals(parameterName)) {
    			return param;
    		}
    	}
    	return null;
    }

}
