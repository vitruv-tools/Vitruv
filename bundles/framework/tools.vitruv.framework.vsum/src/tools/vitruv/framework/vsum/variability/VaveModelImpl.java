package tools.vitruv.framework.vsum.variability;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import com.google.common.base.Preconditions;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.framework.vsum.variability.InternalVaveModel;
import vavemodel.VavemodelFactory;

public class VaveModelImpl implements InternalVaveModel {
	private static final Map<String, String> saveOptions = Map.<String, String>of("PROCESS_DANGLING_HREF", "DISCARD");
	private final vavemodel.System system;
	private final Resource vaveResource;

	public VaveModelImpl(final URI resourceUri) {
		this.system = VavemodelFactory.eINSTANCE.createSystem();
		// vaveResource.getContents().add(system);
		Resource _xifexpression = null;
		if ((resourceUri != null)) {
			Resource _createResource = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl())
					.createResource(resourceUri);
			final Procedure1<Resource> _function = (Resource it) -> {
				EList<EObject> _contents = it.getContents();
				_contents.add(this.system);
			};
			_xifexpression = ObjectExtensions.<Resource>operator_doubleArrow(_createResource, _function);
		}
		this.vaveResource = _xifexpression;
	}

	public void loadSerializedVave() {
		Preconditions.checkState((this.vaveResource != null),
				"Vave resource must be specified to load existing vave model");
	    final Resource loadedResource = ResourceSetUtil.loadOrCreateResource(ResourceSetUtil.withGlobalFactories(new ResourceSetImpl()), this.vaveResource.getURI());
		boolean _isEmpty = loadedResource.getContents().isEmpty();
		boolean _not = (!_isEmpty);
		if (_not) {
			EObject _get = loadedResource.getContents().get(0);
	//		final Correspondences loadedCorrespondences = ((Correspondences) _get);
		}
	}
	
	  @Override
	  public void save() {
	    try {
	      Resource _vaveResource = this.vaveResource;
	      if (_vaveResource!=null) {
	        _vaveResource.save(VaveModelImpl.saveOptions);
	      }
	    } catch (Throwable _e) {
	      throw Exceptions.sneakyThrow(_e);
	    }
	  }

//	public VaveModelImpl(Resource vaveResource, String systemName) {
//		loadVave(vaveResource, systemName);
//	}
//
//	private void loadVave(Resource vaveResource, String systemName) {
//		try {
//			vaveResource.load(null);
//		} catch (IOException e) {
//		}
//		vavemodel.System system = VavemodelFactory.eINSTANCE.createSystem();
//		vaveResource.getContents().add(system);
//		system.setName(systemName);
//	}
}
