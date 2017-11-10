package tools.vitruv.dsls.commonalities.names

import com.google.inject.Inject
import java.util.function.Function
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.xbase.lib.Functions.Function1

interface IEObjectDescriptionProvider extends Function<EObject, IEObjectDescription>, Function1<EObject, IEObjectDescription> {
	def IEObjectDescription describe(EObject object);
		
	override apply(EObject object) {
		describe(object)
	}
}

class QualifiedNameProviderDescriptionProvider implements IEObjectDescriptionProvider {
	
	@Inject IQualifiedNameProvider qualifiedNameProvider
	
	override describe(EObject object) {
		EObjectDescription.create(qualifiedNameProvider.getFullyQualifiedName(object), object);
	}
	
}