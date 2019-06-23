package shift.sextiarysector4.lib.util;

import javax.annotation.Nonnull;

public class NamespaceHelper {

    @Nonnull
    public static String getLocation(@Nonnull String modId, @Nonnull String name) {
        return modId + ":" + name;
    }

}
