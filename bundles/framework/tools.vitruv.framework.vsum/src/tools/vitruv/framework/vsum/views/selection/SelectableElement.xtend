package tools.vitruv.framework.vsum.views.selection

import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
package class SelectableElement<T> {
    val T element
    boolean selected

    new(T element) {
        this.element = element
        selected = false
    }
}
