package tools.vitruv.framework.remote.client;

import tools.vitruv.framework.remote.client.impl.VitruvRemoteConnection;

import static tools.vitruv.framework.remote.server.VitruvServer.STD_PORT;

import java.nio.file.Path;

public class VitruvClientFactory {

	 /**
     * Creates a new {@link VitruvClient} using the given url and the standard port of 8080.
     *
     * @param url of the vitruv server
     * @return a {@link VitruvClient}
     */
    public static VitruvClient create(String url, Path temp) {
        return create(url, STD_PORT, temp);
    }

    /**
     * Creates a new {@link VitruvClient} using the given url and port.
     *
     * @param url  of the vitruv server
     * @param port of the vitruv server
     * @return a {@link VitruvClient}
     */
    public static VitruvClient create(String url, int port, Path temp) {
        return new VitruvRemoteConnection(url, port, temp);
    }

}
