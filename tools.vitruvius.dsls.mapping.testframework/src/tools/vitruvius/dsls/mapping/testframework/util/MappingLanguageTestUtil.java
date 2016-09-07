package tools.vitruvius.dsls.mapping.testframework.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import tools.vitruvius.framework.metamodel.Metamodel;
import tools.vitruvius.framework.metamodel.Mapping;
import tools.vitruvius.framework.metarepository.MetaRepositoryImpl;
import tools.vitruvius.framework.tuid.AttributeTUIDCalculatorAndResolver;
import tools.vitruvius.framework.tuid.TUIDCalculatorAndResolver;
import tools.vitruvius.framework.util.bridges.JavaHelper;
import tools.vitruvius.framework.util.datatypes.VURI;
import tools.vitruvius.framework.vsum.VSUMImpl;

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
	public static VSUMImpl createEmptyVSUM(Collection<Metamodel> metamodels) {
		MetaRepositoryImpl metarepository = createEmptyMetaRepository(metamodels);
		return new VSUMImpl(metarepository, metarepository);
	}
	
	/**
	 * @see #createEmptyVSUM(List)
	 */
	public static VSUMImpl createEmptyVSUM(Metamodel... metamodels) {
		return createEmptyVSUM(Arrays.asList(metamodels));
	}
	
	/**
	 * Constructs an empty meta repository that contains the meta models and mappings
	 * between all meta models.
	 * 
	 * @param mmURIs the metamodels include in the meta repository.
	 * @return the created meta repository
	 * @see PCMJavaUtils#createPCMJavaMetarepository()
	 */
	public static MetaRepositoryImpl createEmptyMetaRepository(Collection<Metamodel> metamodels) {
		final MetaRepositoryImpl metarepository = new MetaRepositoryImpl();
		for (Metamodel metamodel : metamodels) {
			metarepository.addMetamodel(metamodel);
		}
		
		// create mappings for all pairs of metamodels
		Metamodel[] repositoryMetamodels = metarepository.getAllMetamodels();
		
		for (int i = 0; i < repositoryMetamodels.length; i++)
			for (int j = i + 1; j < repositoryMetamodels.length; j++)
					metarepository.addMapping(new Mapping(repositoryMetamodels[i], repositoryMetamodels[j]));

		return metarepository;
	}

	/**
	 * @see #createEmptyMetarepository(List) 
	 */
	public static MetaRepositoryImpl createEmptyMetaRepository(Metamodel... metamodels) {
		return createEmptyMetaRepository(Arrays.asList(metamodels));
	}

	public static VSUMImpl createEmptyVSUM(MetaRepositoryImpl metaRepository) {
		return new VSUMImpl(metaRepository, metaRepository);
	}
	
	public static Metamodel createMetamodel(String nsURI, String... extensions) {
		return new Metamodel(nsURI, VURI.getInstance(nsURI), extensions);
	}
	
	public static Metamodel createMetamodel(String nsURI, TUIDCalculatorAndResolver tuidCalculatorAndResolver, String... extensions) {
		return new Metamodel(nsURI, VURI.getInstance(nsURI), tuidCalculatorAndResolver, extensions);
	}
	
	public static Metamodel createAttributeTUIDMetamodel(String nsURI, String... extensions) {
		return new Metamodel(nsURI, VURI.getInstance(nsURI), new AttributeTUIDCalculatorAndResolver(nsURI, DEFAULT_ATTRIBUTE_NAMES), extensions);
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
