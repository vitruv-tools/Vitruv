package tools.vitruv.framework.vsum.views;

import org.eclipse.emf.ecore.ENamedElement
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
package class SelectableElement {
    val ENamedElement element
    boolean selected

    new(ENamedElement element) {
        this.element = element
        selected = false
    }
}
