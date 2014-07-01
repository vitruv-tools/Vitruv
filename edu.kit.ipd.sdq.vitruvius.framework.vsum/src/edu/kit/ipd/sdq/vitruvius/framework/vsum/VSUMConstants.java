package edu.kit.ipd.sdq.vitruvius.framework.vsum;

public class VSUMConstants {
    /** Utility classes should not have a public or default constructor. */
    private VSUMConstants() {
    }

    public static String VSUM_PROJECT_NAME = "vitruvius.meta";

    public static final String CORRESPONDENCE_FOLDER_NAME = "correspondence";
    public static final String VSUM_FOLDER_NAME = "vsum";
    public static final String VSUM_INSTANCES_FILE_NAME = "instances.ser";

    /**
     * Fields are needed as options to save correspondence instance (we need to ignore dangling
     * HREFs) Copied from XMLResource.java
     */
    public static String OPTION_PROCESS_DANGLING_HREF = "PROCESS_DANGLING_HREF";
    public static String OPTION_PROCESS_DANGLING_HREF_DISCARD = "DISCARD";
}
