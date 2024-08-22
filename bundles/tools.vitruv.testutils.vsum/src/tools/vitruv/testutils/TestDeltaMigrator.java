package tools.vitruv.testutils;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.change.changederivation.DeltaBasedResourceFactory;

public class TestDeltaMigrator {

	private TestDeltaMigrator() {};
	
	public static void makeVsumModelsDeltaBased(Path modelsNamesFilesPath) throws IllegalStateException, IOException {
		ResourceSet stateBasedResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		List<URI> modelUris;
		try {
			modelUris = Files.readAllLines(modelsNamesFilesPath).stream().map(URI::createURI).toList();
		} catch (NoSuchFileException e) {
			// There are no existing models, so don't do anything
			return;
		}
		modelUris.forEach(uri -> createOrLoadResourceStateBased(stateBasedResourceSet, uri));
		modelUris.forEach(uri -> {
			try {
				saveResourceDeltaBased(stateBasedResourceSet, uri);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	private static void createOrLoadResourceStateBased(ResourceSet stateBasedResourceSet, URI resourceUri) {
		if (resourceUri.isFile() || resourceUri.isPlatform()) {
			getOrCreateResource(stateBasedResourceSet, resourceUri);
		} else {
			loadOrCreateResource(stateBasedResourceSet, resourceUri);
		}
	}

	private static void saveResourceDeltaBased(ResourceSet stateBasedResourceSet, URI uri) throws IOException {
		Resource deltaBasedResource = new DeltaBasedResourceFactory().createResource(uri);
		Resource stateBasedResource = stateBasedResourceSet.getResource(uri, false);
		deltaBasedResource.getContents().addAll(EcoreUtil.copyAll(stateBasedResource.getContents()));
		deltaBasedResource.save(null);
	}
}
