package tools.vitruv.variability.vave.tests.comparator;

/*******************************************************************************
 * Copyright (c) 2014
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Benjamin Klatt - initial API and implementation and/or initial documentation
 *******************************************************************************/

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ComposedSwitch;
import org.eclipse.emf.ecore.util.Switch;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.classifiers.util.ClassifiersSwitch;
import org.emftext.language.java.commons.Commentable;
import org.emftext.language.java.commons.CommonsPackage;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.JavaRoot;
import org.emftext.language.java.containers.util.ContainersSwitch;
import org.emftext.language.java.expressions.Expression;
//import org.emftext.language.java.extensions.members.ConstructorExtension;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.instantiations.NewConstructorCall;
import org.emftext.language.java.members.AdditionalField;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.members.util.MembersSwitch;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.references.MethodCall;
import org.emftext.language.java.statements.Block;
import org.emftext.language.java.statements.ExpressionStatement;
import org.emftext.language.java.statements.LocalVariableStatement;
import org.emftext.language.java.statements.Return;
import org.emftext.language.java.statements.Statement;
import org.emftext.language.java.statements.StatementListContainer;
import org.emftext.language.java.statements.TryBlock;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.variables.AdditionalLocalVariable;
import org.emftext.language.java.variables.LocalVariable;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Utility class to handle JaMoPP elements.
 */
public final class JaMoPPElementUtil {

	/** Disable constructor for utility class. */
	private JaMoPPElementUtil() {
	}

	/**
	 * Get the first container that is not of any given type.
	 * 
	 * @param element               The element to get an appropriate container for.
	 * @param ignoredContainerTypes A set of types that should be skipped.
	 * @return The first matching container, which can be null as well.
	 */
	public static EObject getFirstContainerNotOfGivenType(final Commentable element, final Class<?>... ignoredContainerTypes) {
		Predicate<EObject> matchingPredicate = new Predicate<EObject>() {
			@Override
			public boolean apply(EObject container) {
				for (Class<?> clazz : ignoredContainerTypes) {
					if (clazz.isAssignableFrom(container.getClass())) {
						return false;
					}
				}
				return true;
			}
		};
		return getTransitiveContainerForPredicate(element, matchingPredicate);
	}

	/**
	 * Get the first container of an element which matches the given predicate.
	 * 
	 * @param element   The element to get an appropriate container for.
	 * @param predicate The predicate that matches a valid container.
	 * @return The first matching container, which can be null as well.
	 */
	private static EObject getTransitiveContainerForPredicate(Commentable element, Predicate<EObject> predicate) {
		EObject container = element.eContainer();
		while (!predicate.apply(container)) {
			container = container.eContainer();
		}
		return container;
	}

	/**
	 * Check if a candidate is in the container hierarchy of the child.
	 * 
	 * @param parentCandidate The candidate to check.
	 * @param child           The child to prove the hierarchy of.
	 * @return True/false depending on parent relationship exists or not.
	 */
	public static boolean isParentOf(Commentable parentCandidate, Commentable child) {

		EObject container = child;
		while (container != null) {

			if (container == parentCandidate) {
				return true;
			}

			container = container.eContainer();
		}

		return false;
	}

	/**
	 * Get the name for a JaMoPP element.<br>
	 * This is the name in case of {@link NamedElement}s, otherwise null will be returned.<br>
	 * 
	 * If you want to always get a string representation, check out {@link JaMoPPElementUtil#getLabel(Commentable)}.
	 * 
	 * @param element The JaMoPP element to get the name for.
	 * @return The name attribute of an element.
	 */
	public static String getName(Commentable element) {
		if (element == null) {
			return null;
		}
		if (element instanceof NamedElement) {
			return ((NamedElement) element).getName();
		} else {
			return null;
		}
	}

	/**
	 * Get the name of a JaMoPP element.<br>
	 * This will always return a representative String for the element.<br>
	 * The main purpose of the method is to get a human readable representation.
	 * 
	 * @param element The JaMoPP element to get the label for.
	 * @return The String representation.
	 */
	public static String getLabel(Commentable element) {
		if (element == null) {
			return "NULL";

		} else if (element instanceof CompilationUnit) {
			CompilationUnit cu = (CompilationUnit) element;
			String name = cu.getName();
			if (name != null) {
				return name.substring(cu.getNamespacesAsString().length());
			} else {
				return "CompilationUnit (no name)";
			}

		} else if (element instanceof LocalVariableStatement) {
			LocalVariableStatement statement = (LocalVariableStatement) element;
			LocalVariable variable = statement.getVariable();

			StringBuilder labelBuilder = new StringBuilder("Variable Declaration (");
			labelBuilder.append(variable.getName());
			for (AdditionalLocalVariable addVar : variable.getAdditionalLocalVariables()) {
				labelBuilder.append(", ");
				labelBuilder.append(addVar.getName());
			}
			labelBuilder.append(") :");
			return labelBuilder.toString();

		} else if (element instanceof ExpressionStatement) {
			ExpressionStatement statement = (ExpressionStatement) element;
			Expression expression = statement.getExpression();
			if (expression.getType() != null) {
				String expressionType = statement.getExpression().getType().getClass().getSimpleName();
				if (expressionType.endsWith("Impl")) {
					expressionType = expressionType.substring(0, (expressionType.length() - 4));
				}
				return String.format("Expression Statement (%s)", expressionType);
			} else {
				return "Expression Statement (void)";
			}

		} else if (element instanceof Return) {
			return "Return Statement";

		} else if (element instanceof ClassifierImport) {
			ClassifierImport importDecl = (ClassifierImport) element;
			return "Import: " + importDecl.getClassifier().getName();

		} else if (element instanceof Import) {
			return "Import";

		} else if (element instanceof MethodCall) {
			MethodCall invocation = (MethodCall) element;
			return "Method Invocation: " + invocation.getTarget().getName() + "()";

		} else if (element instanceof Method) {
			Method method = (Method) element;
			return method.getName() + "()";

		} else if (element instanceof Constructor) {
			Constructor method = (Constructor) element;
			return method.getName() + "()";

		} else if (element instanceof Block && getContainingMethod(element) != null) {
			Method method = getContainingMethod(element);
			return method.getName() + "()";

		} else if (element instanceof TryBlock) {
			String containerName = null;
			if (element.eContainer() instanceof Commentable) {
				containerName = getLabel((Commentable) element.eContainer());
			} else {
				containerName = "" + element.eContainer();
			}
			return "try-Block in " + containerName;

		} else if (element instanceof NamedElement) {
			return ((NamedElement) element).getName();
		}

		return "JaMoPP Element " + element.getClass().getSimpleName();
	}

	/**
	 * Determines a qualified name for the given element. The name is not fully qualified but gives some hints about the location of the element. The compilation unit and the containing classifier and method are mentioned.
	 * 
	 * @param commentable The Commentable for which the name shall be determined.
	 * @return The qualified name of the Commentable.
	 */
	public static String getQualifiedName(Commentable commentable) {
		String result = new QualifiedNameSwitch().doSwitch(commentable);
		return result;
	}

	/**
	 * Determines the qualified name of a given EObject. If the object is no Commentable, null will be returned.
	 */
	private static class QualifiedNameSwitch extends ComposedSwitch<String> {

		public QualifiedNameSwitch() {
			addSwitch(new MembersQualifiedNameSwitch());
			addSwitch(new ClassifiersQualifiedNameSwitch());
			addSwitch(new ContainersQualifiedNameSwitch());
		}

		@Override
		public String doSwitch(EObject eObject) {
			Optional<NamedElement> containerOptional = findRelevantContainer(eObject);
			if (!containerOptional.isPresent()) {
				return null;
			}

			String containerName = doSuperSwitch(containerOptional.get());

			NamedElement container = containerOptional.get();
			if (container != eObject) {
				String elementName = eObject.getClass().getSimpleName();
				if (eObject instanceof NamedElement) {
					elementName = ((NamedElement) eObject).getName();
				}
				return String.format("%s -> %s", containerName, elementName);
			}

			return containerName;
		}

		@Override
		protected String doSwitch(EClass theEClass, EObject theEObject) {
			List<EClass> typesToCheck = Lists.newLinkedList(theEClass.getEAllSuperTypes());
			typesToCheck.add(0, theEClass);
			typesToCheck.remove(CommonsPackage.eINSTANCE.getCommentable());
			for (EClass eclass : typesToCheck) {
				Switch<String> delegate = findDelegate(eclass.getEPackage());
				if (delegate != null) {
					String result = delegatedDoSwitch(delegate, eclass, theEObject);
					if (result != null) {
						return result;
					}
				}
			}
			return defaultCase(theEObject);
		}

		private String doSuperSwitch(EObject obj) {
			return super.doSwitch(obj);
		}

		private static Optional<NamedElement> findRelevantContainer(EObject obj) {
			EObject relevantContainer = obj;
			while (relevantContainer != null && !isRelevantContainerObject(relevantContainer)) {
				relevantContainer = relevantContainer.eContainer();
			}
			if (relevantContainer != null && relevantContainer instanceof NamedElement) {
				return Optional.of((NamedElement) relevantContainer);
			}
			return Optional.absent();
		}

		@SuppressWarnings("unchecked")
		private static boolean isRelevantContainerObject(final EObject obj) {
			return Iterables.any(Lists.newArrayList(JavaRoot.class, Classifier.class, Method.class, Constructor.class, Field.class), new Predicate<Class<?>>() {
				@Override
				public boolean apply(Class<?> input) {
					return input.isAssignableFrom(obj.getClass());
				}
			});
		}

		/**
		 * Determines the qualified name for a container element.
		 */
		private class ContainersQualifiedNameSwitch extends ContainersSwitch<String> {

			@Override
			public String caseJavaRoot(JavaRoot object) {
				String namespaces = object.getNamespacesAsString();
				Matcher m = Pattern.compile(Pattern.quote(namespaces) + "(.*?)(\\.java)?").matcher(object.getName());
				if (m.matches()) {
					return m.group(1);
				}
				return object.getName();
			}

		}

		/**
		 * Determines the qualified name for a classifier element.
		 */
		private class ClassifiersQualifiedNameSwitch extends ClassifiersSwitch<String> {

			@Override
			public String caseClassifier(Classifier object) {
				return String.format("%s::%s", doSuperSwitch(object.getContainingCompilationUnit()), object.getName());
			}

		}

		/**
		 * Determines the qualified name for a member element.
		 */
		private class MembersQualifiedNameSwitch extends MembersSwitch<String> {

			@Override
			public String caseField(Field object) {
				return String.format("%s::%s::%s", doSuperSwitch(object.getContainingCompilationUnit()), object.getContainingConcreteClassifier().getName(), object.getName());
			}

			@Override
			public String caseMethod(Method object) {
				return String.format("%s::%s::%s()", doSuperSwitch(object.getContainingCompilationUnit()), object.getContainingConcreteClassifier().getName(), object.getName());
			}

		}

	}

	private static Method getContainingMethod(Commentable element) {
		EObject container = element.eContainer();
		while (container != null) {
			if (container instanceof Method) {
				return (Method) container;
			} else {
				container = container.eContainer();
			}
		}
		return null;
	}

	/**
	 * Get the position of a statement in its container. If the container is not a {@link StatementListContainer} the method will always return -1.
	 * 
	 * @param statement The statement to check the position of.
	 * @return The position in the container's statement list.
	 */
	public static int getPositionInContainer(Statement statement) {

		if (statement.eContainer() instanceof StatementListContainer) {
			StatementListContainer container = (StatementListContainer) statement.eContainer();
			return container.getStatements().indexOf(statement);
		}

		return -1;
	}

	/**
	 * Get the import declaration for a type in the compilation unit of a JaMoPP element.
	 * 
	 * @param referenceElement The element to inspect the container of.
	 * @param type             The type to search the import for.
	 * @return The Import declaration or null if none found.
	 */
	public static Import checkForImport(Commentable referenceElement, Type type) {
		CompilationUnit cu = referenceElement.getContainingCompilationUnit();
		EList<ClassifierImport> imports = cu.getChildrenByType(ClassifierImport.class);
		for (ClassifierImport importDecl : imports) {
			if (importDecl.getClassifier().equals(type)) {
				return importDecl;
			}
		}
		return null;
	}

	/**
	 * Get a human readable label for the type of an element.
	 * 
	 * @param element The element to get the type label for.
	 * @return The type identifier.
	 */
	public static String getTypeLabel(Commentable element) {
		if (element instanceof Enumeration) {
			return "Enumeration";
		} else if (element instanceof org.emftext.language.java.classifiers.Class) {
			return "Class";
		} else if (element instanceof Interface) {
			return "Interface";
		} else if (element instanceof Field || element instanceof AdditionalField) {
			return "Field";
		} else if (element instanceof Method || element instanceof Constructor) {
			return "Method";
		} else if (element instanceof Statement) {
			return "Statement";
		} else if (element instanceof LocalVariable || element instanceof AdditionalLocalVariable) {
			return "Variable";
		} else if (element instanceof Parameter) {
			return "Parameter";

		} else if (element instanceof ClassifierImport) {
			return getTypeLabel(((ClassifierImport) element).getClassifier());

		} else {
			int implLength = 4;
			String className = element.getClass().getSimpleName();
			return className.substring(0, className.length() - implLength);
		}
	}

	/**
	 * Get the constructor matching a given constructor call.
	 * 
	 * The call itself does not have an explicit reference, thus the members of the referenced type are scanned for a constructor with arguments matching the given call.
	 * 
	 * In case of an implicit constructor call, null will be returned. Only explicit constructors are returned.
	 * 
	 * @param call The call to search the constructor for.
	 * @return The constructor or null if none found.
	 */
	public static Constructor getConstructor(NewConstructorCall call) {

		Type type = call.getReferencedType();
		if (type instanceof ConcreteClassifier) {
			for (Constructor constructor : ((ConcreteClassifier) type).getConstructors()) {

				// TODO: Allow for none perfect matches such as varying parameter lengths
				boolean constructorForCall = ConstructorExtension.isConstructorForCall(constructor, call, true);
				if (constructorForCall) {
					return constructor;
				}
			}
		}
		return null;
	}

}
