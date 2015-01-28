package edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.util;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

/**
 * Utility class for tests for the MIR language.
 * @author Dominik Werle
 */
public class MIRTestUtil {
	/**
	 * Helper class for encapsuling an URI with an extension.
	 * @author Dominik Werle
	 */
	public static class URIwithExtension {
		private final String uri;
		private final String extension;
		
		public URIwithExtension(String uri, String extension) {
			this.uri = uri;
			this.extension = extension;
		}
		
		public String getUri() {
			return uri;
		}

		public String getExtension() {
			return extension;
		}
	}
	
	/**
	 * Constructs an empty VSUM that contains the meta models for the given
	 * URIs.
	 * 
	 * @param mmURIs the URIs and extensions of the meta models to include in the VSUM.
	 * @return the created VSUM
	 */
	public static VSUMImpl createEmptyVSUM(List<URIwithExtension> mmURIs) {
		MetaRepositoryImpl metarepository = createEmptyMetarepository(mmURIs);
		return new VSUMImpl(metarepository, metarepository, metarepository);
	}
	
	/**
	 * @see #createEmptyVSUM(List)
	 */
	public static VSUMImpl createEmptyVSUM(URIwithExtension... mmURIs) {
		return createEmptyVSUM(Arrays.asList(mmURIs));
	}
	
	/**
	 * Constructs an empty meta repository that contains the meta models for the given
	 * URIs.
	 * 
	 * @param mmURIs the URIs and extensions of the meta models to include in the meta repository.
	 * @return the created meta repository
	 */
	public static MetaRepositoryImpl createEmptyMetarepository(List<URIwithExtension> mmURIs) {
		final MetaRepositoryImpl metarepository = new MetaRepositoryImpl();
		for (URIwithExtension mmURI : mmURIs) {
			Metamodel metamodel = new Metamodel(mmURI.getUri(), VURI.getInstance(mmURI.getUri()), mmURI.getExtension());
			metarepository.addMetamodel(metamodel);
		}

		return metarepository;
	}

	/**
	 * @see #createEmptyMetarepository(List) 
	 */
	public static MetaRepositoryImpl createEmptyMetarepository(URIwithExtension... mmURIs) {
		return createEmptyMetarepository(Arrays.asList(mmURIs));
	}
}
