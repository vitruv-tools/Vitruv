package edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.statements.Statement;

import com.google.common.collect.BiMap;

import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLExpressionHaving;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecifiedElement;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.metamodels.JMLTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.metamodels.JaMoPPTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.tuid.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.tuid.TUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;

public class ShadowCopyCorrespondencesImpl implements ShadowCopyCorrespondencesWritable {

    private static final Logger LOGGER = Logger.getLogger(ShadowCopyCorrespondencesImpl.class);
    private static final TUIDCalculatorAndResolver JAMOPP_TUID_RESOLVER = new JaMoPPTUIDCalculatorAndResolver();
    private static final JMLTUIDCalculatorAndResolver JML_TUID_RESOLVER = new JMLTUIDCalculatorAndResolver();
    private final CachedModelInstanceLoader modelInstanceHelperJMLCopies = new CachedModelInstanceLoader();
    private final CachedModelInstanceLoader modelInstanceHelperShadow = new CachedModelInstanceLoader();
    private final BiMap<JMLSpecifiedElement, Member> jmlElementToShadowMember = IdentityBiMap.create();
    private final BiMap<NamedElement, NamedElement> javaToShadowElement = IdentityBiMap.create();
    private final BiMap<JMLExpressionHaving, Statement> jmlExpression2ShadowStatement = IdentityBiMap.create();
    private final BiMap<Member, ClassMethod> jmlShadowMemberToSpecContainingMethod = IdentityBiMap.create();
    private final CorrespondenceModel ci;
    private final Collection<CompilationUnit> javaCUs;
    private final boolean useJMLCopies;

    public ShadowCopyCorrespondencesImpl(final CorrespondenceModel ci, final Collection<CompilationUnit> javaCUs,
            final boolean useJMLCopies) {
        this.ci = ci;
        this.javaCUs = javaCUs;
        this.useJMLCopies = useJMLCopies;
    }

    @Override
    public void addCorrespondence(final JMLSpecifiedElement jml, final Member java) {
        this.jmlElementToShadowMember.put(jml, java);
    }

    @Override
    public void addCorrespondence(final JMLExpressionHaving jml, final Statement java) {
        this.jmlExpression2ShadowStatement.put(jml, java);
    }

    @Override
    public Statement get(final JMLExpressionHaving jml) {
        return this.jmlExpression2ShadowStatement.get(jml);
    }

    @Override
    public JMLExpressionHaving get(final Statement stmt) {
        return this.jmlExpression2ShadowStatement.inverse().get(stmt);
    }

    @Override
    public JMLSpecifiedElement get(final Member javaMember) {
        return this.jmlElementToShadowMember.inverse().get(javaMember);
    }

    @Override
    public Member getMember(final JMLSpecifiedElement jml) {
        return this.jmlElementToShadowMember.get(jml);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends NamedElement> T getShadow(final T original) {
        if (!this.javaToShadowElement.containsKey(original)) {
            final String originalTUID = this.ci.calculateTUIDFromEObject(original).toString();
            final ModelInstance shadowModelInstance = this.modelInstanceHelperShadow
                    .loadModelInstance(original.eResource().getURI());
            final EObject shadowModelRoot = shadowModelInstance.getUniqueTypedRootEObject(CompilationUnit.class);
            final EObject resolvedObject = JAMOPP_TUID_RESOLVER.resolveEObjectFromRootAndFullTUID(shadowModelRoot,
                    originalTUID);
            if (this.javaToShadowElement.containsValue(resolvedObject)) {
                return (T) resolvedObject;
            }
            this.javaToShadowElement.put(original, (T) resolvedObject);
        }
        return (T) this.javaToShadowElement.get(original);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends NamedElement> T getOriginal(final T shadow) {
        if (!this.javaToShadowElement.inverse().containsKey(shadow)) {
            final String shadowTUID = this.ci.calculateTUIDFromEObject(shadow).toString();
            CompilationUnit originalRoot = null;
            for (final CompilationUnit cu : this.javaCUs) {
                if (cu.eResource().equals(shadow.eResource())) {
                    originalRoot = cu;
                }
            }
            final EObject resolvedObject = JAMOPP_TUID_RESOLVER.resolveEObjectFromRootAndFullTUID(originalRoot,
                    shadowTUID);
            this.javaToShadowElement.inverse().put(shadow, (T) resolvedObject);
        }
        return (T) this.javaToShadowElement.inverse().get(shadow);

    }

    @Override
    public CachedModelInstanceLoader getShadowModelInstanceLoader() {
        return this.modelInstanceHelperShadow;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends EObject> Set<T> getCorrespondingEObjectsByType(final EObject eObject, final Class<T> type) {
        final Set<T> correspondingElements = CorrespondenceModelUtil.getCorrespondingEObjectsByType(this.ci, eObject,
                type);
        if (correspondingElements == null || !this.useJMLCopies) {
            return correspondingElements;
        }

        final Set<T> copies = new HashSet<T>();
        for (final T correspondingElement : correspondingElements) {
            final ModelInstance mi = this.modelInstanceHelperJMLCopies
                    .loadModelInstance(correspondingElement.eResource().getURI());
            final T correspondingElementCopy = (T) this.ci.resolveEObjectFromRootAndFullTUID(mi.getUniqueRootEObject(),
                    this.ci.calculateTUIDFromEObject(correspondingElement).toString());
            copies.add(correspondingElementCopy);
        }
        return copies;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends EObject> T getJMLElement(final T original) {
        final TUID originalTUID = this.ci.calculateTUIDFromEObject(original);
        if (!this.useJMLCopies) {
            return (T) this.ci.resolveEObjectFromTUID(originalTUID);
        }

        final ModelInstance mi = this.modelInstanceHelperJMLCopies.loadModelInstance(original.eResource().getURI());
        return (T) JML_TUID_RESOLVER.resolveEObjectFromRootAndFullTUID(mi.getUniqueRootEObject(),
                originalTUID.toString());
    }

    @Override
    public CachedModelInstanceLoader getShadowModelInstanceLoaderJML() {
        return this.modelInstanceHelperJMLCopies;
    }

    @Override
    public Collection<Setting> findReferencesToJavaObject(final EObject obj) {
        LOGGER.debug("Starting to find references to " + obj);
        final long timestamp1 = System.currentTimeMillis();
        final Collection<Setting> tmp = this.modelInstanceHelperShadow.findObjectReferences(obj);
        LOGGER.debug(
                "Finished finding references. Duration: " + (System.currentTimeMillis() - timestamp1) / 1000 + " s.");
        return tmp;
    }

    @Override
    public void addCorrespondence(final Member shadowMember, final ClassMethod specContainingMember) {
        this.jmlShadowMemberToSpecContainingMethod.put(shadowMember, specContainingMember);
        // TODO this feature should be documented as well as the limitations
        if (shadowMember instanceof Method) {
            final Method shadowMethod = (Method) shadowMember;
            for (int i = 0; i < shadowMethod.getParameters().size(); ++i) {
                shadowMethod.getParameters().get(i).eAdapters()
                        .add(new MethodParameterObserver(specContainingMember.getParameters().get(i)));
            }
        }
    }

    @Override
    public Set<Entry<Member, ClassMethod>> getSpecContainingMethods() {
        return this.jmlShadowMemberToSpecContainingMethod.entrySet();
    }

    @Override
    public ClassMethod getSpecContainingMethod(final Member shadowMember) {
        return this.jmlShadowMemberToSpecContainingMethod.get(shadowMember);
    }

    /**
     * Observer for changes on method parameters. It contains a corresponding parameter to which all
     * changes shall be applied. At the moment not all changes are implemented.
     *
     * @author Stephan Seifermann
     *
     */
    private static class MethodParameterObserver extends AdapterImpl {

        private static final Logger LOGGER = Logger.getLogger(MethodParameterObserver.class);
        private final Parameter correspondingParameter;
        private volatile boolean processingChange = false;

        /**
         * Constructor.
         *
         * @param correspondingParameter
         *            The parameter on which the changes shall be applied.
         */
        public MethodParameterObserver(final Parameter correspondingParameter) {
            this.correspondingParameter = correspondingParameter;
        }

        @Override
        public void notifyChanged(final Notification msg) {
            if (this.processingChange) {
                return;
            }
            this.processingChange = true;
            switch (msg.getEventType()) {
            case Notification.SET:
                this.applySet((EStructuralFeature) msg.getFeature(), msg.getOldValue(), msg.getNewValue());
                break;
            case Notification.UNSET:
                this.applyUnset((EStructuralFeature) msg.getFeature());
                break;
            default:
                LOGGER.trace("Ignoring change on parameter " + ((Parameter) this.getTarget()).getName() + ".");
                // TODO implement more
            }
            this.processingChange = false;
        }

        /**
         * Applies a set change
         *
         * @param feature
         *            The changed feature
         * @param oldValue
         *            The old value of the feature
         * @param newValue
         *            The new value of the feature
         */
        private void applySet(final EStructuralFeature feature, final Object oldValue, final Object newValue) {
            LOGGER.debug("Updating feature " + feature.getName() + " from " + oldValue + " to " + newValue + ".");
            this.correspondingParameter.eSet(feature, newValue);
        }

        /**
         * Applies an unset change
         *
         * @param feature
         *            The changed feature
         */
        private void applyUnset(final EStructuralFeature feature) {
            LOGGER.debug("Unsetting feature " + feature.getName() + ".");
            this.correspondingParameter.eUnset(feature);
        }

    }

    @Override
    public void saveAll() throws IOException {
        for (final ModelInstance mi : this.modelInstanceHelperShadow.getAllCachedModelInstances()) {
            EcoreResourceBridge.saveResource(mi.getResource());
        }
    }

}
