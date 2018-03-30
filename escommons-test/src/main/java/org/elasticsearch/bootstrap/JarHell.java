package org.elasticsearch.bootstrap;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 1:16 PM
 */
public class JarHell {
    //region Constructors
    private JarHell() {
    }
    //endregion

    //region Public methods
    public static void checkJarHell() throws Exception {
    }

    public static void checkJarHell(final Set<?> aSet) throws Exception {
    }

    public static void checkJarHell(URL urls[]) throws Exception {
    }

    public static void checkVersionFormat(String targetVersion) {
    }

    public static void checkJavaVersion(String resource, String targetVersion) {
    }

    public static Set<URL> parseClassPath() {
        return new LinkedHashSet<>();
    }
    //endregion
}