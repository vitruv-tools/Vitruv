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

package tools.vitruvius.views.java.projumled4j.editor.viewpoint.javaActions;

import org.emftext.language.java.containers.Package;

/**
 * This abstract action has to be called in the context of a JaMoPP package. In that case the user is
 * asked to specify a name for a new Java interface that will be created in the Java package the
 * JaMoPP package is representing.
 * 
 * @author Heiko Klare
 */
public class CreateJavaInterfaceAction extends CreateTemplateAction {
	@Override
	protected void createTemplateFile(Package pckg) {
		createTemplateFile(pckg, "interface");
	}

}
