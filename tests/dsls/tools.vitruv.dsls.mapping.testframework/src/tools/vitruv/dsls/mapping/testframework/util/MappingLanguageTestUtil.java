package tools.vitruv.dsls.mapping.testframework.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.function.Consumer;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import tools.vitruv.framework.change.processing.ChangePropagationSpecification;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;
import tools.vitruv.framework.tuid.TuidCalculatorAndResolver;
import tools.vitruv.framework.util.bridges.JavaHelper;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.VirtualModel;

/**
 * Utility class for the testing framework for the mapping language.
 * @author Dominik Werle
 */
public final class MappingLanguageTestUtil {
	public static final String[] DEFAULT_ATTRIBUTE_NAMES = new String[] {
		"id", "id2", "name", "entityName"
	};
	
	/**
	 * Constructs an empty VSUM that contains the meta models for the given
	 * URIs.
	 * 
	 * @param mmURIs the URIs and extensions of the meta models to include in the VSUM.
	 * @return the created VSUM
	 */
	public static VirtualModel createEmptyVSUM(Collection<Metamodel> metamodels, Collection<ChangePropagationSpecification> transformer) {
		return TestUtil.createVirtualModel("vitruvius.meta", metamodels, transformer);
	}

	public static Metamodel createMetamodel(String nsURI, TuidCalculatorAndResolver tuidCalculatorAndResolver, String... extensions) {
		return new Metamodel(VURI.getInstance(nsURI), nsURI, tuidCalculatorAndResolver, extensions);
	}
	
	public static Metamodel createAttributeTuidMetamodel(String nsURI, String... extensions) {
		return new Metamodel(VURI.getInstance(nsURI), nsURI, new AttributeTuidCalculatorAndResolver(nsURI, DEFAULT_ATTRIBUTE_NAMES), extensions);
	}
	
	public static Injector injector(Consumer<Binder> configure) {
		return Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				configure.accept(binder);
			}
		});
	}
	
	/**
	 * @see JavaHelper#requireType(Object, Class)
	 * @see JavaHelper#requireType(Object, Class, String)
	 */
	public static void assertType(String message, Object object, Class<?> type) {
		assertNotNull(object);
		assertTrue(message, type.isInstance(object));
	}
}
