package tools.vitruv.framework.variability.vave.impl;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.xbase.lib.Exceptions;

import vavemodel.VavemodelFactory;
import tools.vitruv.framework.util.VitruviusConstants;
import tools.vitruv.framework.util.datatypes.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.uuid.UuidResolver;
import tools.vitruv.framework.variability.vave.VaveModel;

public class VaveModelImpl extends ModelInstance implements VaveModel {
	final UuidResolver uuidResolver;
	private final VaveModel vaveModel;

	public VaveModelImpl(final UuidResolver uuidResolver, final VURI vaveVURI, final Resource vaveResource) {
		super(vaveResource);
		this.vaveModel = this.loadAndRegisterVaveModel(vaveResource);
		this.uuidResolver = uuidResolver;
	}

	private Map<String, String> getSaveAndLoadOptions() {
		return Map.<String, String>of(VitruviusConstants.getOptionProcessDanglingHref(),
				VitruviusConstants.getOptionProcessDanglingHrefDiscard());
	}
	
	  private VaveModel loadAndRegisterVaveModel(final Resource vaveResource) {
		    try {
		      vaveResource.load(this.getSaveAndLoadOptions());
		    } catch (final Throwable _t) {
		      if (_t instanceof IOException) {
		        final IOException e = (IOException)_t;
		        Throwable _cause = e.getCause();
		      } else {
		        throw Exceptions.sneakyThrow(_t);
		      }
		    }
		    vavemodel.System system = VavemodelFactory.eINSTANCE.createSystem();
			vaveResource.getContents().add(system);
		    return vaveModel;
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
