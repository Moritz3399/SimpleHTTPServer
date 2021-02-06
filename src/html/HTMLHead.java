package html;

import main.Config;

public class HTMLHead {
    public static String get(){
        StringBuilder sb = new StringBuilder();

        sb.append("<head>\n");
        sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        sb.append("<meta charset=\"UTF-8\">\n");
        // https://css-tricks.com/emojis-as-favicons/
        sb.append("<link rel=\"icon\" href=\"favicon.svg\"/>\n");
        sb.append("<link rel=\"apple-touch-icon\" href=\"favicon.svg\"/>\n");
        sb.append("<style>");
        sb.append(CSS.get());
        sb.append("</style>");
        sb.append(String.format("<title>%s</title>\n", Config.title));
        sb.append("</head>\n");

        return sb.toString();
    }
}
