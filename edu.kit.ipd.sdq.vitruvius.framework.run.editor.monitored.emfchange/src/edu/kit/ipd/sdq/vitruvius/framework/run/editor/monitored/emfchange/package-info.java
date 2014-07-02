/**
 
This package contains a listener for EMF editors recording model changes occurring
in EMF resources. The listener can be attached to EMF tree editors as well as to
GMF diagram editors and works for arbitrary EMF models. It is fired whenever the user
saves the changes in the editor.

The "main" listener class is SynchronizingMonitoredEmfEditorImpl, implementing
the ISynchronizingMonitoredEmfEditor interface. It monitors Elipse
editors and installs EMF model change listeners when appropriate. The editors
are accessed through adapter objects provided by a user-supplied adapter factory.

These editor adapters, created by a factory implementing the IEditorPartAdapterFactory
interface, provide the following editor-related functionality:
 * Provide access to the editor's EMF editing domain.
 * Provide access to the EMF resource which can be monitored.
 * Execute a command within the editor's context.
A default implementation, DefaultEditorPartAdapterFactoryImpl, provides a simple
factory implementation providing access to EMF and GMF editors, selecting the
monitored resource by its filename extension. An example implementation for
Ecore models is provided by EcoreEditorPartAdapterFactory.

The set of monitored editors is determined via an IMonitoringDecider object
passed to the SynchronizingMonitoredEmfEditor.

Within the context of Vitruvius, the class VitruviusModelEditorSyncMgr is of highest interest
to users of this listener. When initialize() is called, the manager sets
up the backing ISynchronizingMonitoredEmfEditor. When a model is added to the
Vitruvius framework, it can be registered with the VitruviusModelMgr so that
already-opened editors get monitored, too. Likewise functionality is provided for
remove operations.

Only the packages
edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf,
edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf.experimental and
edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf.monitor
are of external interest.

 */

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange;

