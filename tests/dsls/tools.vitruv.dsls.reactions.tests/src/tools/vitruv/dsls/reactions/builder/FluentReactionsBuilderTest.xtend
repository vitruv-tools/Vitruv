package tools.vitruv.dsls.reactions.builder

import allElementTypes.AllElementTypesPackage
import allElementTypes2.AllElementTypes2Package
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.xtext.testing.InjectWith
import tools.vitruv.dsls.reactions.tests.ReactionsLanguageInjectorProvider
import javax.inject.Inject
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension

@ExtendWith(InjectionExtension)
@InjectWith(ReactionsLanguageInjectorProvider)
abstract class FluentReactionsBuilderTest {
	protected static val Root = AllElementTypesPackage.eINSTANCE.root
	protected static val NonRoot = AllElementTypesPackage.eINSTANCE.nonRoot
	protected static val Root2 = AllElementTypes2Package.eINSTANCE.root2
	protected static val EObject = EcorePackage.eINSTANCE.EObject

	@Inject protected GeneratedReactionsMatcherBuilder matcher
	@Inject protected FluentReactionsLanguageBuilder create

	def protected builds(String reactionsTest) {
		matcher.builds(reactionsTest)
	}
}
