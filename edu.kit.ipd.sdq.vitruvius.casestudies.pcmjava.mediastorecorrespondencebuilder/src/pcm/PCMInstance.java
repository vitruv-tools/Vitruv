package pcm;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.language.java.resource.java.IJavaOptions;

import de.uka.ipd.sdq.pcm.PcmPackage;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.util.PcmResourceFactoryImpl;

/**
 * @author Jonas Kunz
 *
 */
public class PCMInstance {

	private ResourceSet rs;
	private Repository repo;
	
	private PCMInstance() {
		rs = new ResourceSetImpl();
		setUp();
	}
	
	public static PCMInstance createFromRepositoryFile(File repo) {
		PCMInstance inst = new PCMInstance();
        URI uri;
		try {
			uri = URI.createFileURI(repo.getCanonicalPath());
		}  catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
        inst.repo = (Repository) inst.rs.getResource(uri, true).getContents().get(0);
        return inst;
	}
	

	private static boolean wasSetUp = false;
	private void setUp() {
		if(!wasSetUp) {
			wasSetUp = true;
			EPackage.Registry.INSTANCE.put(PcmPackage.eNS_URI, PcmPackage.eINSTANCE);
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("repository", new PcmResourceFactoryImpl());
		}
		rs.getLoadOptions().put(IJavaOptions.DISABLE_LOCATION_MAP, Boolean.TRUE);
	}
	
	public PCMRoot getModelRoot() {
		return new PCMRoot(repo);
	}
	
}
