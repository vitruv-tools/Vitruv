package tools.vitruv.extensions.dslruntime.commonalities.operators

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.lang.annotation.Annotation
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.RegisterGlobalsParticipant
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.TransformationParticipant
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.CodeGenerationParticipant
import org.eclipse.xtend.lib.macro.ValidationParticipant
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.Type
import static org.eclipse.xtend.lib.macro.declaration.Visibility.*
import org.eclipse.xtend.lib.macro.declaration.ResolvedMethod
import edu.kit.ipd.sdq.activextendannotations.TypeCopier
import org.eclipse.xtend.lib.macro.declaration.TypeReference
import org.eclipse.xtend.lib.macro.declaration.InterfaceDeclaration
import java.util.List
import javax.lang.model.SourceVersion

interface ClassProcessor extends
	RegisterGlobalsParticipant<ClassDeclaration>,
	TransformationParticipant<MutableClassDeclaration>,
	CodeGenerationParticipant<ClassDeclaration>,
	ValidationParticipant<ClassDeclaration> 
{}

@FinalFieldsConstructor
final class OperatorNameProcessor extends AbstractClassProcessor implements ClassProcessor  {
	val Class<? extends Annotation> annotationType
	
	override doRegisterGlobals(ClassDeclaration annotatedClass, extension RegisterGlobalsContext context) {
		val annotationTypeReference = findUpstreamType(annotationType)
		val operatorLanguageName = annotatedClass.getOperatorName(annotationTypeReference)
		if (operatorLanguageName.nameProblems.isEmpty) {
			registerClass(annotatedClass.getTargetQualifedName(annotationTypeReference))
		}
	}
	
	override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
		val annotationTypeReference = findTypeGlobally(annotationType)
		val operatorLanguageName = annotatedClass.getOperatorName(annotationTypeReference)
		val nameProblems = operatorLanguageName.nameProblems
		if (!nameProblems.isEmpty) {
			nameProblems.forEach [problem | annotatedClass.addError(problem)]
			return
		}
		
		findClass(annotatedClass.getTargetQualifedName(annotationTypeReference)) => [
			extension val classTypeCopier = new TypeCopier(context)
			annotatedClass.allImplementedInterfaces.forEach [ parentInterface |
				copyTypeParametersFrom(parentInterface)
			]
			implementedInterfaces = annotatedClass.allImplementedInterfaces.map[replaceTypeParameters]
			annotatedClass.annotations.forEach [sourceAnnotation | addAnnotation(sourceAnnotation.newAnnotationReference())]
			primarySourceElement = annotatedClass
			
			addField("delegate") [
				type = annotatedClass.newTypeReference(annotatedClass.typeParameters.map[newTypeReference().replaceTypeParameters()])
				final = true
				visibility = PRIVATE
			]
			
			annotatedClass.declaredConstructors.filter [visibility == PUBLIC].forEach [ sourceConstructor |
				addConstructor [
					sourceConstructor.parameters.forEach [source | addParameter(source.simpleName, source.type)]
					body = '''
						this.delegate = new «annotatedClass»(«FOR p : sourceConstructor.parameters SEPARATOR ", "»«p.simpleName»«ENDFOR»);
					'''
				]
			]
			
			annotatedClass.allImplementedInterfaces.flatMap [allResolvedMethods as Iterable<ResolvedMethod>]
				.filter [declaration.declaringType instanceof InterfaceDeclaration]
				.uniqueBy[identifier]
				.forEach [ interfaceMethod |
					extension val methodTypeCopier = new TypeCopier(classTypeCopier, context)
					addMethod(interfaceMethod.declaration.simpleName) [
						copyTypeParametersFrom(interfaceMethod)
						returnType = interfaceMethod.declaration.returnType.replaceTypeParameters()
						interfaceMethod.declaration.parameters.forEach [source | 
							addParameter(source.simpleName, source.type.replaceTypeParameters())
						]
						visibility = PUBLIC
						body = '''
							«IF (!interfaceMethod.declaration.returnType.isVoid)»return «ENDIF» this.delegate.«interfaceMethod.declaration.simpleName»(«FOR p : interfaceMethod.declaration.parameters SEPARATOR ", "»«p.simpleName»«ENDFOR»);
						'''
					]
				]
		]
	}
	
	def private getNameProblems(String name) {
		val convertedName = CommonalitiesOperatorConventions.toOperatorTypeName(name)
		check(!name.endsWith('_'), "Operator names must not end with underscores!")
			+ check(SourceVersion.isName(convertedName), '''‹«convertedName»› is not a legal Java type name!''')
	}
	
	def private String getTargetQualifedName(ClassDeclaration annotatedClass, Type annotationType) {
		val lastDot = annotatedClass.qualifiedName.lastIndexOf('.')
		CommonalitiesOperatorConventions.toOperatorTypeQualifiedName(
			annotatedClass.qualifiedName.substring(0, lastDot),
			annotatedClass.getOperatorName(annotationType)
		)
	}
	
	def private getOperatorName(ClassDeclaration annotatedClass, Type annotationType) {
		annotatedClass.findAnnotation(annotationType)?.getStringValue("name")
			?: annotatedClass.simpleName.toFirstLower
	}

	def private Iterable<TypeReference> getAllImplementedInterfaces(MutableClassDeclaration annotatedClass) {
		annotatedClass.implementedInterfaces + annotatedClass.extendedClass.allImplementedInterfaces
	}	
	
	def private Iterable<TypeReference> getAllImplementedInterfaces(TypeReference reference) {
		reference.declaredSuperTypes.filter [type instanceof InterfaceDeclaration] 
			+ reference.declaredSuperTypes.filter [type instanceof ClassDeclaration].flatMap[allImplementedInterfaces]
	}
	
	def private getIdentifier(ResolvedMethod method) {
		method.declaration.simpleName + "," + method.declaration.parameters.map[type.name].join(",")
	}
	
	def private <T> Iterable<T> uniqueBy(Iterable<T> elements, (T)=> Object identifier) {
		elements.groupBy(identifier).values.map[get(0)]
	}
	
	def private static check(boolean condition, String message) {
		if (!condition) List.of(message) else emptyList()
	}
}