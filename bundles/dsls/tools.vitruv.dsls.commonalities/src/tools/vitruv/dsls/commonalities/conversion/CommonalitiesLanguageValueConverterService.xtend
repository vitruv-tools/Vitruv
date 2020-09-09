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

	@ValueConverter(rule = "DomainName")
	def IValueConverter<String> getDomainNameConverter() {
		return validIDConverter
	}

	@ValueConverter(rule = "UnqualifiedClass")
	def IValueConverter<String> getUnqualifiedClassConverter() {
		return validIDConverter
	}

	@ValueConverter(rule = "QualifiedClass")
	def IValueConverter<String> getQualifiedClassConverter() {
		return qualifiedClassValueConverter
	}

	@ValueConverter(rule = "UnqualifiedAttribute")
	def IValueConverter<String> getUnqualifiedAttributeConverter() {
		return validIDConverter
	}
}
