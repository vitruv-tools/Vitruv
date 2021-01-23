package tools.vitruv.dsls.commonalities.conversion

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.xtext.conversion.IValueConverter
import org.eclipse.xtext.conversion.ValueConverter
import org.eclipse.xtext.conversion.impl.KeywordAlternativeConverter
import org.eclipse.xtext.xbase.conversion.XbaseValueConverterService

@Singleton
class CommonalitiesLanguageValueConverterService extends XbaseValueConverterService {

	@Inject KeywordAlternativeConverter validIDConverter
	@Inject QualifiedClassValueConverter qualifiedClassValueConverter
	@Inject OperatorNameConverter operatorNameConverter

	@ValueConverter(rule = "DomainName")
	def IValueConverter<String> getDomainNameConverter() {
		validIDConverter
	}

	@ValueConverter(rule = "UnqualifiedClass")
	def IValueConverter<String> getUnqualifiedClassConverter() {
		validIDConverter
	}

	@ValueConverter(rule = "QualifiedClass")
	def IValueConverter<String> getQualifiedClassConverter() {
		qualifiedClassValueConverter
	}

	@ValueConverter(rule = "UnqualifiedAttribute")
	def IValueConverter<String> getUnqualifiedAttributeConverter() {
		validIDConverter
	}
	
	@ValueConverter(rule = "OperatorName")
	def IValueConverter<String> getOperatorNameConverter() {
		operatorNameConverter
	}
	
	@ValueConverter(rule = "QualifiedOperatorName")
	def IValueConverter<String> getQualifierOperatorNameConverter() {
		operatorNameConverter
	}
	
	@ValueConverter(rule = "QualifiedOperatorWildCard")
	def IValueConverter<String> getQualifierOperatorWilCardConverter() {
		operatorNameConverter
	}
}
