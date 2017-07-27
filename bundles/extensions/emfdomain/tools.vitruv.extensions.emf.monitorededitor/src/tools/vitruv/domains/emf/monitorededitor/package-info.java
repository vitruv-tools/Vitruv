/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

/**
 
This package contains a listener for EMF editors recording model changes occurring
in EMF resources. The listener can be attached to EMF tree editors as well as to
GMF diagram editors and works for arbitrary EMF models. It is fired whenever the user
saves the changes in the editor.

The "main" listener class is SynchronizingMonitoredEmfEditorImpl, implementing
the {@link ISynchronizingMonitoredEmfEditor} interface. It monitors Eclipse
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

Within the context of Vitruvius, the class VitruviusModelEditorSyncMgr is of highest
interest to users of this listener. When initialize() is called, the manager sets
up the backing ISynchronizingMonitoredEmfEditor. When a model is added to the
Vitruvius framework, it can be registered with the VitruviusModelMgr so that
already-opened editors get monitored, too. Likewise functionality is provided for
remove operations.

This front-end for the Vitruvius system buffers the changes collected when the user
saves models and synchronizes the changes when the user calls 
{@link tools.vitruv.domains.emf.monitorededitor.IVitruviusEMFEditorMonitor#triggerSynchronisation(tools.vitruv.framework.util.datatypes.VURI)}.

Known issues:
 - Feature maps are not supported yet. Should this need arise, the packages
   changedescription2change and changeapplication have to be changed accordingly.

 */

package tools.vitruv.domains.emf.monitorededitor;

