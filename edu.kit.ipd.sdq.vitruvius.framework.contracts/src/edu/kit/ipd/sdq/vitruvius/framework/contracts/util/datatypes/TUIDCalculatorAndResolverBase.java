package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;

/**
 * Base class for TUID calculators and resolvers. It handles the default parts of the TUID like
 * generator identifier and URI inclusion for TUID calculation and resolution.
 *
 * @author Stephan Seifermann
 *
 */
public abstract class TUIDCalculatorAndResolverBase implements TUIDCalculatorAndResolver {

    private static final Logger LOGGER = Logger.getLogger(TUIDCalculatorAndResolverBase.class.getSimpleName());

    @Override
    public boolean isValidTUID(final String tuid) {
        return tuid.startsWith(getDefaultTUIDPrefix());
    }

    @Override
    public String calculateTUIDFromEObject(final EObject eObject) {
        // FIXME MAX (cache c1): HERE access to resource, called from various places so cannot be
        // moved
        // to callees
        if (eObject.eResource() == null) {
            // FIXME MAX (cache c2): get root and his resource, if no resource is there then cache
            // root
            // and keep on calculating
            // if there is a resource check if there is also a cache entry and if yes remove it
            LOGGER.warn("The given EObject " + eObject
                    + " has no resource attached, which is necessary to generate a TUID.");
            return getDefaultTUIDPrefix();
        }
        // return defaulttuidprefix + hashCode as prefix for cached objects
        String tuidPrefix = getDefaultTUIDPrefix() + VURI.getInstance(eObject.eResource());
        // FIXME MAX (cache c3): only JML tests call this with virtualRoot != null
        return calculateTUIDFromEObject(eObject, null, tuidPrefix);
    }

    // FIXME MK (cache): if cache does not get emptied provide a removeFromCache(rootThatIsNowSaved)
    // and call it during saving

    // FIXME MK (cache): during update of tuidsremove entry for old tuid from cache

    // TODO MK the only way to have something in the cache that should no longer be there is if you
    // create objects that are never stored
    /**
     * @return The default prefix for all TUIDs of this calculator.
     */
    public String getDefaultTUIDPrefix() {
        return getTUIDIdentifier() + VitruviusConstants.getTUIDSegmentSeperator();
    }

    @Override
    public VURI getModelVURIContainingIdentifiedEObject(final String extTuid) {
        String tuid = getTUIDWithoutDefaultPart(extTuid);
        String[] ids = tuid.split(VitruviusConstants.getTUIDSegmentSeperator());
        String vuriKey = ids[0];
        return VURI.getInstance(vuriKey);
        // FIXME MAX (cache r2): has to return null for cached tuids
    }

    @Override
    public EObject resolveEObjectFromRootAndFullTUID(final EObject root, final String extTuid) {
        // FIXME MAX (cache r5) if root is null try to get one from the cache, keep on going with it
        String identifier = getTUIDWithoutRootObjectPart(root, extTuid);
        if (identifier == null) {
            return null;
        }

        String[] ids = identifier.split(VitruviusConstants.getTUIDSegmentSeperator());
        if (identifier.length() == 0) {
            ids = new String[0];
        }

        EObject foundElement = getIdentifiedEObjectWithinRootEObjectInternal(root, ids);
        if (foundElement != null) {
            return foundElement;
        }

        LOGGER.warn("No EObject found for TUID: " + extTuid + " in root object: " + root);
        return null;
    }

    /**
     * Removes the root object prefix from the given TUID and returns the result.
     *
     * @param root
     *            The root object.
     * @param extTuid
     *            The TUID to process.
     * @return The TUID without the root object prefix.
     */
    private String getTUIDWithoutRootObjectPart(final EObject root, final String extTuid) {
        String rootTUID = calculateTUIDFromEObject(root);
        if (!extTuid.startsWith(rootTUID)) {
            LOGGER.warn("TUID " + extTuid + " is not in EObject " + root);
            return null;
        }

        String identifyingTUIDPart = extTuid.substring(rootTUID.length());
        if (identifyingTUIDPart.startsWith(VitruviusConstants.getTUIDSegmentSeperator())) {
            identifyingTUIDPart = identifyingTUIDPart.substring(VitruviusConstants.getTUIDSegmentSeperator().length());
        }
        return identifyingTUIDPart;
    }

    /**
     * Finds the object described by the given IDs in the given root object.
     *
     * @param root
     *            The root object.
     * @param ids
     *            The IDs of the object to find.
     * @return The found object or null if no such object exists.
     */
    protected abstract EObject getIdentifiedEObjectWithinRootEObjectInternal(final EObject root, final String[] ids);

    /**
     * @param tuid
     *            The TUID.
     * @return The given TUID without the default part.
     */
    private String getTUIDWithoutDefaultPart(final String tuid) {
        if (!isValidTUID(tuid)) {
            throw new IllegalArgumentException("TUID: " + tuid + " not generated by class " + getTUIDIdentifier());
        }
        return tuid.substring(getDefaultTUIDPrefix().length());
    }

    /**
     * @return The identifier for the TUID calculator and resolver (e.g. the name of the class).
     */
    protected abstract String getTUIDIdentifier();

}
