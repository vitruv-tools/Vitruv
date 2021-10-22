package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.util.URI

interface View {

    def Resource getResource(URI uri)

    def boolean isModified()

    def boolean hasVSUMChanged()

    def void update()

    def boolean commit()
}
