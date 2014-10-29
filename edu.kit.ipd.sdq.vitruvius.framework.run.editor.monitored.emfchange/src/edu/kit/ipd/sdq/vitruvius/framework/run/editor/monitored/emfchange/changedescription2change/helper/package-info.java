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
 * This package contains a system of helper classes used to convert
 * {@link org.eclipse.emf.ecore.change.FeatureChange}s produced by EMF
 * {@link org.eclipse.emf.ecore.change.util.ChangeRecorder}s to their
 * corresponding {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change}
 * representation.
 * 
 * The appropriate helper for a given {@link org.eclipse.emf.ecore.change.FeatureChange}
 * can be obtained via the {@link edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper.FeatureChangeKind}
 * enumeration.
 */
package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

