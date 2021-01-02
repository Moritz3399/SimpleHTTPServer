import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class IndexHTML {
    // generate the html code for the index page

    public static String get() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("<head>\n");
        sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        sb.append("<meta charset=\"UTF-8\">\n");
        // https://css-tricks.com/emojis-as-favicons/
        sb.append("<link rel=\"icon\" href=\"favicon.svg\"/>\n");
        sb.append("<link rel=\"apple-touch-icon\" href=\"favicon.svg\"/>\n");
        sb.append(String.format("<title>%s</title>\n", Config.title));
        sb.append("</head>\n");
        sb.append("<body style=\"margin-left:20px; margin-top:20px\">\n");
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
