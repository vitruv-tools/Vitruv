package tools.vitruv.dsls.commonalities.testutils;

import com.google.inject.Injector;
import org.eclipse.xtext.testing.IInjectorProvider;
import tools.vitruv.dsls.commonalities.ui.internal.CommonalitiesActivator;

/**
 * Copied from generated code in commonalities.ui.tests
 */
class CommonalitiesLanguageUiInjectorProvider implements IInjectorProvider {

	@Override
	public Injector getInjector() {
		return CommonalitiesActivator.getInstance().getInjector(CommonalitiesActivator.TOOLS_VITRUV_DSLS_COMMONALITIES_COMMONALITIESLANGUAGE);
	}
}
