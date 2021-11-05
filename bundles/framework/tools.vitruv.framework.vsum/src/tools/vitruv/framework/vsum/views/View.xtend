package tools.vitruv.framework.vsum.views

import java.util.Collection
import org.eclipse.emf.ecore.EObject

interface View {

    def Collection<EObject> rootObjects()

    def <T> Collection<T> rootObjects(Class<T> clazz) {
        rootObjects.filter[clazz.isInstance(it)].map[clazz.cast(it)].toList
    }

    def boolean isModified()

    def boolean hasVSUMChanged()

    def void update()

    def boolean commitChanges()
}
