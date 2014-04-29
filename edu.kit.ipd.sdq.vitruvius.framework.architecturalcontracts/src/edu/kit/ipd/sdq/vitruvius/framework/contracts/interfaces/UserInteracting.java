package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;

public interface UserInteracting {
    void interruptAndInform(String message);

    int selectFromMessage(String message, String... selectionDescriptions);

    int selectFromModel(String message, ModelInstance... modelInstances);

    void addToInformationList(String message);

    void removeFromInformationList(String message);
}
