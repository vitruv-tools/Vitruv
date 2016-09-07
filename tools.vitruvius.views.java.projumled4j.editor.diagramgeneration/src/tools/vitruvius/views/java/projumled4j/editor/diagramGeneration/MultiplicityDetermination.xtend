/*******************************************************************************
 * Copyright (c) 2015 Heiko Klare (Karlsruhe Institute of Technology)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Heiko Klare - initial API and implementation and/or initial documentation
 *******************************************************************************/ 

package tools.vitruvius.views.java.projumled4j.editor.diagramGeneration

import org.emftext.language.java.members.Field
import tools.vitruvius.views.java.projumled4j.util.JamoppUtils;

/** 
 * This class provides functions for determining the multiplicities of associations.
 */
class MultiplicityDetermination {
	public def int determineTargetLowerMultiplicity(Field field) {
		if (JamoppUtils.isFieldFinal(field) && !JamoppUtils.isFieldMultiValued(field)) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public def int determineTargetUpperMultiplicity(Field field) {
		if (JamoppUtils.isFieldMultiValued(field)) {
			return -1;
		} else {
			return 1;
		}
	}
	
}