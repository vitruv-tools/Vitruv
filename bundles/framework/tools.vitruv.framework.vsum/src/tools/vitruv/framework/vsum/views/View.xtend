package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.resource.Resource

interface View {

    def Resource getResource()

    def boolean isModified()

    def boolean hasVSUMChanged()

    def void update()

    def boolean commit()
}
