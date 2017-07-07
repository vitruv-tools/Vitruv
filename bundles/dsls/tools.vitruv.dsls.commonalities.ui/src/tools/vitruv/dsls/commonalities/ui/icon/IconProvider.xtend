package tools.vitruv.dsls.commonalities.ui.icon

import com.google.inject.Singleton
import org.eclipse.core.runtime.Platform
import org.eclipse.core.runtime.FileLocator
import java.util.Collections
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.swt.graphics.Image
import org.eclipse.core.runtime.Path
import edu.kit.ipd.sdq.activextendannotations.Lazy

@Singleton
final class IconProvider {
	
	@Lazy Image emfPackageIcon = iconFromBundle("org.eclipse.emf.ecore.edit", "icons/full/obj16/EPackage.gif")
	
	def private static iconFromBundle(String bundleName, String iconPath) {
		val emfEditorBundle = Platform.getBundle(bundleName)
		val ePackageIconUrl = FileLocator.find(emfEditorBundle, new Path(iconPath), Collections.emptyMap)
		if (ePackageIconUrl !== null) {
			return ImageDescriptor.createFromURL(ePackageIconUrl).createImage
		}
		return null
	}
}	
