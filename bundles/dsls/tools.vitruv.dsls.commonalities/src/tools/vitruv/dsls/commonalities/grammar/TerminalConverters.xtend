package tools.vitruv.dsls.commonalities.grammar

import com.google.inject.Inject
import org.eclipse.xtext.common.services.DefaultTerminalConverters
import org.eclipse.xtext.conversion.IValueConverter
import org.eclipse.xtext.conversion.ValueConverter

class TerminalConverters extends DefaultTerminalConverters {
	
	@Inject QuotedNameConverter quotedNameConverter
	
	@ValueConverter(rule = "QUOTED_NAME")
	def IValueConverter<String> convertQuotedName() {
		quotedNameConverter
	}
}