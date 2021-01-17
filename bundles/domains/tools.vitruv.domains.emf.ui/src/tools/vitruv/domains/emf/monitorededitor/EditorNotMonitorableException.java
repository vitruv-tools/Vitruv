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

package tools.vitruv.domains.emf.monitorededitor;

/**
 * A {@link RuntimeException} derivate indicating that the monitor registration of a monitor has
 * failed because the respective editor cannot be monitored.
 */
@SuppressWarnings("serial")
public class EditorNotMonitorableException extends RuntimeException {
}