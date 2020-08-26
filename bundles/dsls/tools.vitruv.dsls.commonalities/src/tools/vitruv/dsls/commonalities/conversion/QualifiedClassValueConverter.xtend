package tools.vitruv.dsls.commonalities.conversion

import com.google.inject.Inject
import com.google.inject.Provider
import com.google.inject.Singleton
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.conversion.IValueConverterService
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.conversion.impl.AbstractValueConverter
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.INode
import tools.vitruv.dsls.commonalities.names.QualifiedNameHelper

@Singleton
class QualifiedClassValueConverter extends AbstractValueConverter<String> {

	static val DELIMITER = QualifiedNameHelper.DOMAIN_METACLASS_SEPARATOR
	static val DOMAIN_NAME_RULE = 'DomainName'
	static val UNQUALIFIED_CLASS_RULE = 'UnqualifiedClass'

	@Inject Provider<IValueConverterService> valueConverterService

	override toString(String value) throws ValueConverterException {
		if (value === null) {
			throw new ValueConverterException('''Invalid QualifiedClass value: '«value»'.''', null, null)
		}

		val segments = value.split(DELIMITER, 2)
		if (segments.length != 2) {
			throw new ValueConverterException('''Invalid QualifiedClass value: '«value»'.''', null, null)
		}
		val domainName = valueConverterService.get.toString(segments.get(0), DOMAIN_NAME_RULE)
		val className = valueConverterService.get.toString(segments.get(1), UNQUALIFIED_CLASS_RULE)
		return domainName + DELIMITER + className
	}

	override toValue(String string, INode node) throws ValueConverterException {
		if (node !== null) {
			var INode domainNameNode = null
			var INode classNameNode = null
			for(INode leafNode : node.asTreeIterable) {
				val grammarElement = leafNode.getGrammarElement()
				if (grammarElement instanceof RuleCall) {
					val ruleName = grammarElement.rule.name
					if (ruleName == DOMAIN_NAME_RULE) {
						domainNameNode = leafNode
					} else if (ruleName == UNQUALIFIED_CLASS_RULE) {
						classNameNode = leafNode
					}
				}
			}
			if (domainNameNode === null || classNameNode === null) {
				throw new ValueConverterException('''Could not find DomainName and/or UnqualifiedClassName node(s): '«
					node.text»'.''', null, null)
			}
			val domainName = valueConverterService.get.toValue(domainNameNode.visibleText, DOMAIN_NAME_RULE, domainNameNode) as String
			val className = valueConverterService.get.toValue(classNameNode.visibleText, UNQUALIFIED_CLASS_RULE, classNameNode) as String
			return domainName + DELIMITER + className
		} else {
			val segments = string.split(DELIMITER, 2)
			if (segments.length != 2) {
				throw new ValueConverterException('''Invalid QualifiedClass string: '«string»'.''', null, null)
			}
			val domainName = valueConverterService.get.toValue(segments.get(0), DOMAIN_NAME_RULE, null)
			val className = valueConverterService.get.toValue(segments.get(1), UNQUALIFIED_CLASS_RULE, null)
			return domainName + DELIMITER + className
		}
	}

	private static def String getVisibleText(INode node) {
		val text = new StringBuilder()
		for (ILeafNode leafNode : node.leafNodes) {
			if (!leafNode.isHidden) {
				text.append(leafNode.text)
			}
		}
		return text.toString
	}
}
