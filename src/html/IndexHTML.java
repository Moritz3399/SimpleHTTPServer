package html;

import main.Config;
import main.NavItems;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class IndexHTML {
    // generate the html code for the index page

    public static String get() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append(HTMLHead.get());
//        sb.append("<body style=\"margin-left:20px; margin-top:20px\">\n");
        sb.append("<body>\n");
        sb.append(NavBarHTML.get(NavItems.Files));
        sb.append(String.format("<p>Files in <b>%s</b></p>", Config.pathToFiles));
        for (String s : getFileHTMLElements()) sb.append(String.format("%s\n", s));
        sb.append("</body></html>");
        return sb.toString();
    }

    private static String[] getFileHTMLElements() {
        // initial kick off of the recursive directory scan/processing
        return getFileHTMLElements(Config.pathToFiles, "");
    }

    private static String[] getFileHTMLElements(String path, String suffix) {
        int indent = suffix.length() - suffix.replace(Config.fileSeparator, "").length();
        ArrayList<String> files = new ArrayList<>();
        File[] directoryContent = new File(path).listFiles();
        if (directoryContent == null) return new String[]{"<p>The directory is empty!</p>"};
        for (File f : directoryContent) {
            if (f.isFile()) {
                files.add(
                        String.format("<p style='margin-left:%sem'>" +
                                "&#128196; %s  " +
                                "<a href=\"%s%s\" style=\"text-decoration:none\">&#128229;" +
                                "</a></p>", indent, f.getName(), suffix, f.getName()));
            } else if (f.isDirectory()) {
                files.add(String.format("<p style='margin-left:%sem'>&#128193; %s</p>", indent, f.getName(), suffix));
                files.addAll(Arrays.asList(getFileHTMLElements(
                        String.format("%s%s%s", path, Config.fileSeparator, f.getName()),
                        suffix + f.getName() + Config.fileSeparator)));
            }
        }
        return files.toArray(new String[0]);
    }

}
