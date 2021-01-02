import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Server {

    // https://css-tricks.com/emojis-as-favicons/
    // TODO: Format smiley position and size
    private final byte[] faviconBytes = new String(
            ("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 100 100\">" +
                    "<text y=\"1em\" font-size=\"80\">" +
                    Config.faviconUnicodeEmoji +
                    "</text>" +
                    "</svg>").getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8
    ).getBytes();

    public Server() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(Config.PORT), 0);
        Config.server = server;
        server.createContext("/", httpExchange -> {
            String url = httpExchange.getRequestURI().toASCIIString();
            url = url.replace("%20", " ");
            Logger.debug(String.format("Requested: %s", url));
            OutputStream out = httpExchange.getResponseBody();
            if (url.equals("/")) { // index.html
                byte[] indexHtmlBytes = IndexHTML.get().getBytes();
                httpExchange.getResponseHeaders().add("Content-Type", "text/html");
                httpExchange.sendResponseHeaders(200, indexHtmlBytes.length);
                // when the index.html gets to big, only out.write does not work anymore!
                // so it needs to be split
                int i = 0;
                while (i * 1024 < indexHtmlBytes.length) {
                    out.write(indexHtmlBytes, i * 1024, i * 1024 + 1023 < indexHtmlBytes.length ? 1024 : indexHtmlBytes.length - i * 1024);
                    i++;
                }
            } else if (url.equals("/favicon.svg")) {
                // favicon
                httpExchange.getResponseHeaders().add("Content-Type", "image/svg+xml");
                httpExchange.sendResponseHeaders(200, faviconBytes.length);
                out.write(faviconBytes);
            } else if (new File(Config.pathToFiles + url.replace("/", Config.fileSeparator)).exists()) {
                // all other requests
                // https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types
                // mime type for downloads: application/octet-stream
                httpExchange.getResponseHeaders().add("Content-Type", "application/octet-stream");
                httpExchange.sendResponseHeaders(200, 0);
                InputStream in = new FileInputStream(new File(Config.pathToFiles + url.replace("/", Config.fileSeparator)));
                int b;
                byte[] buffer = new byte[8 * 1024]; // 8KB
                while ((b = in.read(buffer)) != -1) {
                    out.write(buffer, 0, b);
                }
                in.close();
            } else {
                // error 404
                httpExchange.getResponseHeaders().add("Content-Type", "text/plain");
                String errorMsg = "404 - Page not found!";
                httpExchange.sendResponseHeaders(404, errorMsg.length());
                out.write(errorMsg.getBytes());
            }
            out.close();
        });
        server.setExecutor(null);
        server.start();
        Logger.always("Server started!");
        Logger.always(String.format("Get to your files on %s:%s", Config.IP, Config.PORT));
    }

}
