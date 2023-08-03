package tools.vitruv.framework.remote.common.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emfcloud.jackson.databind.deser.ReferenceEntry;
import org.eclipse.emfcloud.jackson.handlers.URIHandler;
import org.eclipse.emfcloud.jackson.utils.EObjects;

import com.fasterxml.jackson.databind.DatabindContext;

import tools.vitruv.change.atomic.hid.HierarchicalId;

public class HidReferenceEntry implements ReferenceEntry {
	
	private final EObject owner;
    private final EReference reference;
	private final String hid;
	
	public HidReferenceEntry(EObject owner, EReference reference, String hid) {
		this.owner = owner;
		this.reference = reference;
		this.hid = hid;
	}

	@Override
	public void resolve(DatabindContext context, URIHandler handler) {
		EObjects.setOrAdd(owner, reference, new HierarchicalId(hid));
	}

}
