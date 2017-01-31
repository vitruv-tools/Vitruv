package tools.vitruv.dsls.reactions.codegen.typesbuilder

import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1
import org.eclipse.xtext.common.types.TypesFactory
import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmField
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociator
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.common.types.JvmGenericType

class JvmTypesBuilderWithoutAssociations extends JvmTypesBuilder {
	@Inject
	private TypesFactory typesFactory;
		
	@Inject
	private IJvmModelAssociator associator;
	
	
	/**
	 * Creates a public method with the given name and the given return type and associates it with the given
	 * sourceElement.
	 * 
	 * @param sourceElement 
	 * 		the sourceElement the method should be associated with.
	 * @param name
	 * 		the simple name of the method to be created.
	 * @param returnType
	 * 		the return type of the created method.
	 * @param initializer
	 *            the initializer to apply on the created method. If <code>null</code>, the method won't be initialized.
	 * 
	 * @return a result representing a Java method with the given name, <code>null</code> if sourceElement or name are <code>null</code>.
	 */
	/* @Nullable */
	public def JvmOperation generateUnassociatedMethod(/* @Nullable */ String name, /* @Nullable */ JvmTypeReference returnType,
			/* @Nullable */ Procedure1<? super JvmOperation> initializer) {
		if(name == null) 
			return null;
		val result = typesFactory.createJvmOperation();
		result.setSimpleName(name);
		result.setVisibility(JvmVisibility.PUBLIC);
		result.setReturnType(cloneWithProxies(returnType));
		return initializeSafely(result, initializer);
	}
	
	
	
	/**
	 * Creates a private field with the given name and the given type associated to the given sourceElement.
	 * 
	 * @param sourceElement the sourceElement the resulting element is associated with.
	 * @param name the simple name of the resulting field.
	 * @param typeRef the type of the field
	 * 
	 * @return a {@link JvmField} representing a Java field with the given simple name and type.
	 */
	/* @Nullable */
	public def JvmField generateUnassociatedField(/* @Nullable */ String name, /* @Nullable */ JvmTypeReference typeRef) {
		return generateUnassociatedField(name, typeRef, null);
	}
	
	/**
	 * Same as {@link #toField(EObject, String, JvmTypeReference)} but with an initializer passed as the last argument.
	 */
	/* @Nullable */	
	public def JvmField generateUnassociatedField(/* @Nullable */ String name, /* @Nullable */ JvmTypeReference typeRef, 
			/* @Nullable */ Procedure1<? super JvmField> initializer) {
		if(name == null) 
			return null;
		val result = typesFactory.createJvmField();
		result.setSimpleName(name);
		result.setVisibility(JvmVisibility.PRIVATE);
		result.setType(cloneWithProxies(typeRef));
		return initializeSafely(result, initializer);
	}
	
	/**
	 * Associates a source element with a target element. This association is used for tracing. Navigation, for
	 * instance, uses this information to find the real declaration of a Jvm element.
	 * 
	 * @see IJvmModelAssociator
	 * @see IJvmModelAssociations
	 * 
	 * @return the target for convenience.
	 */
	/* @Nullable */
	public override <T extends EObject> T associate(/* @Nullable */ EObject sourceElement, /* @Nullable */ T target) {
		if(sourceElement != null && target != null && sourceElement.eResource != null && isValidSource(sourceElement))
			associator.associate(sourceElement, target);
		return target;
	}
	
	public def JvmFormalParameter createParameter(String name, JvmTypeReference typeRef) {
		val result = typesFactory.createJvmFormalParameter();
		result.setName(name);
		result.setParameterType(cloneWithProxies(typeRef));
		return result;
	}
	
	public def JvmGenericType generateUnassociatedClass(/* @Nullable */ String name, /* @Nullable */ Procedure1<? super JvmGenericType> initializer) {
		val result = createJvmGenericType(name);
		if (result == null)
			return null;
		return initializeSafely(result, initializer);
	}
	
	protected def JvmGenericType createJvmGenericType(/* @Nullable */ String name) {
		if (name == null)
			return null;
		val fullName = splitQualifiedName(name);
		val result = typesFactory.createJvmGenericType();
		result.setSimpleName(fullName.getSecond());
		if (fullName.getFirst() != null)
			result.setPackageName(fullName.getFirst());
		result.setVisibility(JvmVisibility.PUBLIC);
		return result;
	}
}