package tools.vitruv.domains.java.monitorededitor.astchangelistener.classification.postchange;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IPackageFragment;

import tools.vitruv.domains.java.monitorededitor.astchangelistener.PreviousASTState;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.monitorededitor.changeclassification.events.DeletePackageEvent;

public class DeletePackageClassifier extends PackageClassifier {

    @Override
    protected List<? extends ChangeClassifyingEvent> classifyPackageChange(IJavaElementDelta delta,
            PreviousASTState previousState) {
        IJavaElement element = delta.getElement();
        if (element.getElementType() == IJavaElement.PACKAGE_FRAGMENT && delta.getKind() == IJavaElementDelta.REMOVED
                && (delta.getFlags() & IJavaElementDelta.F_MOVED_TO) == 0) {
            final IPackageFragment pkg = (IPackageFragment) element;
            if (pkg != null) {
                return new ArrayList<DeletePackageEvent>() {
                    private static final long serialVersionUID = 1L;

                    {
                        add(new DeletePackageEvent(pkg.getElementName(), pkg.getResource()));
                    }
                };
            }
        }
        return EMPTY_LIST;
    }
}
