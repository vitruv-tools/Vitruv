/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree.decoder.echange;

/**
 * Decoder for deleteEObject changes
 * 
 * @author Andreas Loeffler
 */
public class DeleteEObjectDecoder extends EObjectNameDecoder {

	public DeleteEObjectDecoder() {
		super("DeleteEObject","affectedEObject");
	}

}
