package tools.vitruv.dsls.reactions.builder

import tools.vitruv.testutils.domains.AllElementTypesDomainProvider
import tools.vitruv.testutils.domains.AllElementTypes2DomainProvider
import allElementTypes.AllElementTypesPackage
import allElementTypes2.AllElementTypes2Package
import org.eclipse.emf.ecore.EcorePackage
import org.junit.rules.ExpectedException
import org.junit.Rule
import org.junit.runner.RunWith
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import tools.vitruv.dsls.reactions.tests.ReactionsLanguageInjectorProvider
import javax.inject.Inject

@RunWith(XtextRunner)
@InjectWith(ReactionsLanguageInjectorProvider)
abstract class FluentReactionsBuilderTest {
	protected static val AllElementTypes = new AllElementTypesDomainProvider().domain
	protected static val AllElementTypes2 = new AllElementTypes2DomainProvider().domain
	protected static val Root = AllElementTypesPackage.eINSTANCE.root
	protected static val NonRoot = AllElementTypesPackage.eINSTANCE.nonRoot
	protected static val Root2 = AllElementTypes2Package.eINSTANCE.root2
	protected static val EObject = EcorePackage.eINSTANCE.EObject

	@Rule
	public val ExpectedException thrown = ExpectedException.none()

	@Inject protected GeneratedReactionsMatcherBuilder matcher
	@Inject protected FluentReactionsLanguageBuilder create

	def protected builds(String reactionsTest) {
		matcher.builds(reactionsTest)
	}
}