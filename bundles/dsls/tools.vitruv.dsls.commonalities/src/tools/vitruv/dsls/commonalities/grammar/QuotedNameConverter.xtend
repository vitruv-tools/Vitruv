package tools.vitruv.dsls.commonalities.grammar

import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter
import org.eclipse.xtext.nodemodel.INode

class QuotedNameConverter extends AbstractLexerBasedConverter<String> {

	static val QUOTE_PAIRS = newHashMap(
		#['"' -> '"', "'" -> "'", '„' -> '“', '“' -> '”', '»' -> '«', '«' -> '»']
			.map[key.charAt(0) -> value.charAt(0)]
	)

	override toValue(String string, INode node) throws ValueConverterException {
		if (string === null) return null;
		if (string.length < 1) {
			throw new IllegalStateException("Empty Quoted Name passed to value converter!")
		}

		val endQuote = QUOTE_PAIRS.get(string.charAt(0))
		if (endQuote === null) {
			throw new IllegalStateException("Quoted Name that does not start with a quote passed to value converter!")
		}

		if (string.length == 1 || string.charAt(string.length - 1) != endQuote) {
			throw new IllegalStateException("Quoted Name with no ore wrong end quote passed to value converter!")
		}

		return string.substring(1, string.length - 1)
	}
}
