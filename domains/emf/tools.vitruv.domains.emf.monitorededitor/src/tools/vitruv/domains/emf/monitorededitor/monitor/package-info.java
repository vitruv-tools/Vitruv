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
 * This package contains a listener for changes in EMF editors getting fired when the user saves an edited model.
 * The changes are made accessible via the {@link tools.vitruv.framework.change.description.VitruviusChange}
 * model such that the user can apply them to a non-synchronized instance of the edited model. 
 */
package tools.vitruv.domains.emf.monitorededitor.monitor;

