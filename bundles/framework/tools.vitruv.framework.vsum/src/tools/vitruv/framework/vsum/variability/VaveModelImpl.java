package tools.vitruv.framework.vsum.variability;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.framework.correspondence.Correspondence;
import tools.vitruv.framework.correspondence.CorrespondenceFactory;
import tools.vitruv.framework.correspondence.Correspondences;
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel;
import tools.vitruv.framework.correspondence.impl.InternalCorrespondenceModelImpl;
import tools.vitruv.framework.util.VitruviusConstants;
import tools.vitruv.framework.util.datatypes.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.uuid.UuidResolver;
import tools.vitruv.framework.vsum.variability.InternalVaveModel;
import vavemodel.VavemodelFactory;

@SuppressWarnings("all")
public class VaveModelImpl implements InternalVaveModel {
	private static final Map<String, String> saveAndLoadOptions = Map.<String, String>of(
			VitruviusConstants.getOptionProcessDanglingHref(),
			VitruviusConstants.getOptionProcessDanglingHrefDiscard());
	private final vavemodel.System system;
	private final UuidResolver uuidResolver;
	private final Resource vaveResource;

	public VaveModelImpl(final UuidResolver uuidResolver, final URI resourceUri) {
		this.uuidResolver = uuidResolver;
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
				"Correspondences resource must be specified to load existing correspondences");
		ResourceSet _withGlobalFactories = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final Procedure1<ResourceSet> _function = (ResourceSet it) -> {
			Map<Object, Object> _loadOptions = it.getLoadOptions();
			_loadOptions.putAll(VaveModelImpl.saveAndLoadOptions);
		};
		final Resource loadedResource = ResourceSetUtil.loadOrCreateResource(
				ObjectExtensions.<ResourceSet>operator_doubleArrow(_withGlobalFactories, _function),
				this.vaveResource.getURI());
		boolean _isEmpty = loadedResource.getContents().isEmpty();
		boolean _not = (!_isEmpty);
		if (_not) {
			EObject _get = loadedResource.getContents().get(0);
			final Correspondences loadedCorrespondences = ((Correspondences) _get);
		}
	}

	@Override
	public void save() {
		try {
			Resource _vaveResource = this.vaveResource;
			if (_vaveResource != null) {
				_vaveResource.save(VaveModelImpl.saveAndLoadOptions);
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
