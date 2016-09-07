package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;

public abstract class PackageEvent extends ChangeClassifyingEvent {

    private static final String PACKAGE_INFO_FILE = "package-info.java";

    public final String packageName;
    public final IResource iResource;

    /**
     * Since JaMoPP can not deal with plain package folders we need to have the event on a file.
     * Hence, if the event occurs on a IFolder we automatically change it to the package-info file.
     *
     * @param packageName
     * @param iResource
     */
    public PackageEvent(final String packageName, final IResource iResource) {
        this.packageName = packageName;
        if (iResource instanceof IFolder) {
            final IFolder packageFolder = (IFolder) iResource;
            final IFile iFile = packageFolder.getFile(PACKAGE_INFO_FILE);
            this.iResource = iFile;
        } else {
            this.iResource = iResource;
        }
    }
}
