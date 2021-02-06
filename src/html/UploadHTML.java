package html;

import main.Config;
import main.NavItems;

public class UploadHTML {

    public static String get() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append(HTMLHead.get());
        sb.append("<body>\n");
        sb.append(NavBarHTML.get(NavItems.Upload));
        sb.append(String.format("<p>Upload files to <b>%s</b></p>\n", Config.pathToFiles));
        sb.append("<form action=\"/upload\" method=\"post\"enctype=\"multipart/form-data\">\n" +
                "  <label for=\"myfile\">Select a file:</label>\n" +
                "  <input type=\"file\" id=\"myfile\" name=\"myfile\"><br><br>\n" +
                "  <input type=\"submit\" value=\"Upload\">\n" +
                "</form>");
        sb.append("</body></html>");
        return sb.toString();
    }

}
