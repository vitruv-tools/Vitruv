package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.util;

import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;

public final class ModifierUtil {

    private ModifierUtil() {

    }

    public static String toModifiersString(int flags) {
        StringBuilder result = new StringBuilder(30);

        if (Modifier.isPrivate(flags))
            result.append(ModifierKeyword.PRIVATE_KEYWORD + " ");
        if (Modifier.isProtected(flags))
            result.append(ModifierKeyword.PROTECTED_KEYWORD + "");
        if (Modifier.isPublic(flags))
            result.append(ModifierKeyword.PUBLIC_KEYWORD + " ");
        if (Modifier.isStatic(flags))
            result.append(ModifierKeyword.STATIC_KEYWORD + " ");
        if (Modifier.isAbstract(flags))
            result.append(ModifierKeyword.ABSTRACT_KEYWORD + " ");
        if (Modifier.isNative(flags))
            result.append(ModifierKeyword.NATIVE_KEYWORD + " ");
        if (Modifier.isTransient(flags))
            result.append(ModifierKeyword.TRANSIENT_KEYWORD + " ");
        if (Modifier.isVolatile(flags))
            result.append(ModifierKeyword.VOLATILE_KEYWORD + " ");
        if (Modifier.isStrictfp(flags))
            result.append(ModifierKeyword.STRICTFP_KEYWORD + " ");
        if (Modifier.isFinal(flags))
            result.append(ModifierKeyword.FINAL_KEYWORD + " ");
        if (Modifier.isSynchronized(flags))
            result.append(ModifierKeyword.SYNCHRONIZED_KEYWORD + " ");

        return result.toString();
    }

}
