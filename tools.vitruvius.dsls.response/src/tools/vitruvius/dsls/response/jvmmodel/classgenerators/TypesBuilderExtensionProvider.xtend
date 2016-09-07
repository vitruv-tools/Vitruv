package tools.vitruvius.dsls.response.jvmmodel.classgenerators

import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import org.eclipse.xtext.xbase.jvmmodel.JvmAnnotationReferenceBuilder

class TypesBuilderExtensionProvider {
	protected extension tools.vitruvius.dsls.response.jvmmodel.JvmTypesBuilderWithoutAssociations _typesBuilder;
	protected extension JvmTypeReferenceBuilder _typeReferenceBuilder;
	protected extension JvmAnnotationReferenceBuilder _annotationTypesBuilder; 
	protected extension ResponseLanguageParameterGenerator _parameterGenerator;
	
	public def void setBuilders(tools.vitruvius.dsls.response.jvmmodel.JvmTypesBuilderWithoutAssociations typesBuilder, JvmTypeReferenceBuilder typeReferenceBuilder, JvmAnnotationReferenceBuilder annotationReferenceBuilder) {
		this._typesBuilder = typesBuilder;
		this._typeReferenceBuilder = typeReferenceBuilder;
		this._annotationTypesBuilder = annotationReferenceBuilder;
		this._parameterGenerator = new ResponseLanguageParameterGenerator(typeReferenceBuilder, typesBuilder);
	}
	
	public def copyBuildersTo(TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		typesBuilderExtensionProvider._typesBuilder = _typesBuilder;
		typesBuilderExtensionProvider._typeReferenceBuilder = _typeReferenceBuilder;
		typesBuilderExtensionProvider._annotationTypesBuilder = _annotationTypesBuilder;
		typesBuilderExtensionProvider._parameterGenerator = _parameterGenerator;
	}
}
	