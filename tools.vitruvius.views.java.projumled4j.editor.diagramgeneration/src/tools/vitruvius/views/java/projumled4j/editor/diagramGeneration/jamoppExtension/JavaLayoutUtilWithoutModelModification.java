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

package tools.vitruvius.views.java.projumled4j.editor.diagramGeneration.jamoppExtension;

import org.emftext.language.java.resource.java.util.JavaLayoutUtil;

/**
 * This class overrides the {@link JavaLayoutUtil} used by JaMoPP avoiding model
 * modifications during a write action of the model.
 * 
 * @author Heiko Klare
 *
 */
public class JavaLayoutUtilWithoutModelModification extends JavaLayoutUtil {
	/*
	 * This method is copied from the JavaLayoutUtil, removing code that
	 * modifies the model.
	 */
	@Override
	public void transferLayoutInformationFromModel(org.eclipse.emf.ecore.EObject element) {
		org.eclipse.emf.ecore.EReference layoutReference = findLayoutReference(element.eClass());
		if (layoutReference != null) {
			org.emftext.language.java.resource.java.mopp.JavaLayoutInformationAdapter layoutInformationAdapter = getLayoutInformationAdapter(element);
			@SuppressWarnings("unchecked")
			java.util.List<org.eclipse.emf.ecore.EObject> list = (java.util.List<org.eclipse.emf.ecore.EObject>) element.eGet(layoutReference);
			for (java.util.Iterator<org.eclipse.emf.ecore.EObject> i = list.iterator(); i.hasNext();) {
				org.eclipse.emf.ecore.EObject layoutModelElement = i.next();
				org.emftext.language.java.resource.java.mopp.JavaLayoutInformation layoutInformation = createLayoutInformation(layoutModelElement);
				if (layoutInformation != null) {
					layoutInformationAdapter.getLayoutInformations().add(layoutInformation);
					// -- BEGIN MODIFICATION --
					// i.remove();
					// -- END MODIFICATION --
				}
			}
		}
	}

}
