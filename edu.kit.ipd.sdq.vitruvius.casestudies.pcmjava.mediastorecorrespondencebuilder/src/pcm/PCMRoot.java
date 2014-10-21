package pcm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import utils.EMFUtils;
import utils.PCMUtils;
import de.uka.ipd.sdq.pcm.core.entity.Entity;
import de.uka.ipd.sdq.pcm.core.entity.InterfaceProvidingEntity;
import de.uka.ipd.sdq.pcm.core.entity.InterfaceRequiringEntity;
import de.uka.ipd.sdq.pcm.repository.Interface;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationProvidedRole;
import de.uka.ipd.sdq.pcm.repository.OperationRequiredRole;
import de.uka.ipd.sdq.pcm.repository.ProvidedRole;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RequiredRole;

/**
 * @author Jonas Kunz
 * 
 */
public class PCMRoot {

    private static final Logger LOGGER = Logger.getLogger(PCMRoot.class);

    private final Repository repo;

    public PCMRoot(Repository repo) {
        this.repo = repo;
    }

    public Repository getRepository() {
        return this.repo;
    }

    public Entity getElementByFullName(String name) {
        return PCMUtils.findElementByName(this.repo, name);
    }

    public List<Entity> getAllElementsByFullName(String name) {
        return PCMUtils.findAllElementsByName(this.repo, name);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> Set<T> getAllReferencingElementsOfType(Entity target, Class<T> type) {

        Set<T> referencingElements = new HashSet<T>();
        EMFUtils.putElementOrParentOfTypeInCollection(target, referencingElements, type);

        if (target instanceof OperationInterface) {
            List<ProvidedRole> provRoles = EMFUtils.listDirectAndIndirectContents(this.repo, ProvidedRole.class);
            for (ProvidedRole role : provRoles) {
                if (role instanceof OperationProvidedRole) {
                    OperationInterface targetInterface = ((OperationProvidedRole) role)
                            .getProvidedInterface__OperationProvidedRole();
                    if (targetInterface == target) {
                        EMFUtils.putElementOrParentOfTypeInCollection(role, referencingElements, type);
                    }
                } else {
                    LOGGER.warn("unknown provided roel type : " + role);
                }
            }
            List<RequiredRole> reqRoles = EMFUtils.listDirectAndIndirectContents(this.repo, RequiredRole.class);
            for (RequiredRole role : reqRoles) {
                if (role instanceof OperationRequiredRole) {
                    OperationInterface targetInterface = ((OperationRequiredRole) role)
                            .getRequiredInterface__OperationRequiredRole();
                    if (targetInterface == target) {
                        EMFUtils.putElementOrParentOfTypeInCollection(role, referencingElements, type);
                    }
                } else {
                    LOGGER.warn("unknown provided roel type : " + role);
                }
            }
        }
        if (target instanceof Interface && EMFUtils.isClassSubclass(Interface.class, type)) {
            List<Interface> interfaces = EMFUtils.listDirectAndIndirectContents(this.repo, Interface.class);
            for (Interface interface1 : interfaces) {
                if (type.isInstance(interface1)) {
                    List<Interface> parents = interface1.getParentInterfaces__Interface();
                    if (parents.contains(target)) {
                        referencingElements.add((T) interface1);
                    }
                }
            }
        }
        return referencingElements;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> Set<T> getAllReferencedElementsOfType(Entity source, Class<T> type) {

        Set<T> referencedElements = new HashSet<T>();
        referencedElements.addAll(EMFUtils.listDirectContents(source, type));

        if (source instanceof InterfaceProvidingEntity) {
            List<ProvidedRole> roles = ((InterfaceProvidingEntity) source).getProvidedRoles_InterfaceProvidingEntity();
            for (ProvidedRole role : roles) {
                if (role instanceof OperationProvidedRole) {
                    Interface targetInterface = ((OperationProvidedRole) role)
                            .getProvidedInterface__OperationProvidedRole();
                    if (type.isInstance(targetInterface)) {
                        referencedElements.add((T) targetInterface);
                    }
                } else {
                    LOGGER.warn("unknown provided roel type : " + role);
                }
            }

        }
        if (source instanceof InterfaceRequiringEntity) {
            List<RequiredRole> roles = ((InterfaceRequiringEntity) source).getRequiredRoles_InterfaceRequiringEntity();
            for (RequiredRole role : roles) {
                if (role instanceof OperationRequiredRole) {
                    Interface targetInterface = ((OperationRequiredRole) role)
                            .getRequiredInterface__OperationRequiredRole();
                    if (type.isInstance(targetInterface)) {
                        referencedElements.add((T) targetInterface);
                    }
                } else {
                    LOGGER.warn("unknown required role type : " + role);
                }
            }
        }
        if (source instanceof Interface) {
            List<Interface> parents = ((Interface) source).getParentInterfaces__Interface();
            for (Interface parent : parents) {
                if (type.isInstance(parent)) {
                    referencedElements.add((T) parent);

                }
            }
        }
        return referencedElements;
    }

}
