package de.dennisfabri.alphatimer.legacy;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.ArrayTypePermission;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import de.dennisfabri.alphatimer.legacy.model.Heat;
import de.dennisfabri.alphatimer.legacy.model.Lane;
import de.dennisfabri.alphatimer.legacy.model.LaneStatus;
import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
public class LegacyXStreamUtil {

    public static XStream getXStream() {
        XStream xstream = new XStream();
        addAliasesToStream(xstream);
        setupPermissions(xstream);
        return xstream;
    }

    private static void addAliasesToStream(XStream xstream) {
        xstream.aliasType("AlphaServer.Heat", Heat.class);
        xstream.aliasType("AlphaServer.Lane", Lane.class);
        xstream.aliasType("AlphaServer.LaneStatus", LaneStatus.class);

        xstream.useAttributeFor(Heat.class, "event");
        xstream.useAttributeFor(Heat.class, "heat");
        xstream.useAttributeFor(Heat.class, "id");

        xstream.useAttributeFor(Lane.class, "laneindex");
    }

    /*
     * Source: http://x-stream.github.io/security.html#example
     */
    private static void setupPermissions(XStream xstream) {
        // clear out existing permissions and start a whitelist
        xstream.addPermission(NoTypePermission.NONE);
        // allow some basics
        xstream.addPermission(NullPermission.NULL);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xstream.addPermission(ArrayTypePermission.ARRAYS);
        xstream.allowTypeHierarchy(Collection.class);
        // allow any type from the same package
        xstream.allowTypes(new Class[]{Heat.class, Lane.class, LaneStatus.class});
    }
}
