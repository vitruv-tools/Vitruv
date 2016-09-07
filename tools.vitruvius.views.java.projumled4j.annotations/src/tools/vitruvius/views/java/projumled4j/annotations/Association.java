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

package tools.vitruvius.views.java.projumled4j.annotations;

public @interface Association {
	int sourceLowerMultiplicity() default 1;
	int sourceUpperMultiplicity() default 1;
	int targetLowerMultiplicity() default 1;
	int targetUpperMultiplicity() default 1;
	Type type() default Type.Association;
	
	enum Type {
		Association, Aggregation, Composition
	}
}
