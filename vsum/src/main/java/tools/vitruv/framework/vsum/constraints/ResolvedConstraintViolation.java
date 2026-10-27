package tools.vitruv.framework.vsum.constraints;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.uuid.Uuid;

/**
 * A constraint violation with the identity of the violating element in the virtual model. The
 * {@code uuid} and {@code resourceUri} are captured at validation time and should be used to
 * identify the element; the {@code element} itself is the live model element, which may change or
 * be removed by later change propagations and must not be accessed concurrently with them.
 *
 * @param element the violating element
 * @param uuid the UUID of the element in the virtual model, {@code null} if it has none
 * @param resourceUri the URI of the resource containing the element, {@code null} if it is not
 *     contained in a resource
 * @param severity the severity of the violation
 * @param message a human-readable explanation, may be {@code null}
 */
public record ResolvedConstraintViolation(
    EObject element, Uuid uuid, URI resourceUri, ConstraintSeverity severity, String message) {}
