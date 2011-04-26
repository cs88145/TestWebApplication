package example.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import java.util.List;

import static ch.lambdaj.collection.LambdaCollections.with;
import static org.hamcrest.Matchers.endsWith;

public class WebServer {

    private Server server;

    public WebServer(int port) {
        server = new Server(port);
    }

    public WebServer start() throws Exception {
        WebAppContext context = new WebAppContext("src/webapp", "/example");
        context.setConfigurationClasses(removeTagLibConfiguration(context));
        server.addHandler(context);
        server.start();
        return this;
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new WebServer(8080).start();
    }

    private String[] removeTagLibConfiguration(WebAppContext context) {
        List<String> configuration = with(context.getConfigurationClasses()).remove(endsWith("TagLibConfiguration"));
        return configuration.toArray(new String[configuration.size()]);
    }
}
