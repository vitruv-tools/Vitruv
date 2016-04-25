package edu.kit.ipd.sdq.vitruvius.framework.util.changes;

import edu.kit.ipd.sdq.vitruvius.framework.util.changes.ForwardChangeDescription;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;

@SuppressWarnings("all")
public class ForwardChangeRecorder {
  private ChangeRecorder cr;
  
  private Map<EObject, URI> eObjectToProxyURIMap;
  
  private final Collection<Notifier> elementsToObserve;
  
  private boolean copyDefault = false;
  
  public ForwardChangeRecorder(final Collection<Notifier> elementsToObserve) {
    ChangeRecorder _changeRecorder = new ChangeRecorder();
    this.cr = _changeRecorder;
    this.elementsToObserve = elementsToObserve;
    this.cr.setRecordingTransientFeatures(false);
    this.cr.setResolveProxies(true);
    HashMap<EObject, URI> _hashMap = new HashMap<EObject, URI>();
    this.cr.setEObjectToProxyURIMap(this.eObjectToProxyURIMap = _hashMap);
  }
  
  public void beginRec() {
    this.cr.beginRecording(this.elementsToObserve);
  }
  
  public ForwardChangeDescription endRec() {
    return this.endRec(this.copyDefault);
  }
  
  public ForwardChangeDescription endRec(final boolean copy) {
    final ChangeDescription cd = this.cr.endRecording();
    if (copy) {
      return new ForwardChangeDescription(cd, this.eObjectToProxyURIMap);
    } else {
      return new ForwardChangeDescription(cd);
    }
  }
  
  public ForwardChangeDescription restartRec() {
    return this.restartRec(this.copyDefault);
  }
  
  public ForwardChangeDescription restartRec(final boolean copy) {
    final ForwardChangeDescription cd = this.endRec(copy);
    this.beginRec();
    return cd;
  }
  
  public void startObserving(final Notifier elementToObserve) {
    this.elementsToObserve.add(elementToObserve);
  }
  
  public boolean isRecording() {
    return this.cr.isRecording();
  }
  
  public void dispose() {
    this.cr.dispose();
  }
}
