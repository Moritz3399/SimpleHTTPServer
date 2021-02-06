package html;

import main.NavItems;

public class NavBarHTML {


    public static String get(NavItems selected){
        StringBuilder sb = new StringBuilder();
        for(NavItems i : NavItems.values()){
            sb.append("<a href=\"");
            sb.append(getTarget(i));
            if(i.equals(selected)) sb.append("\" class=\"button active\">"); else sb.append("\" class=\"button\">");
            sb.append(i);
            sb.append("</a>\n");
        }
        return sb.toString();
    }

    private static String getTarget(NavItems item){
        switch (item){
            case Print: return "/print";
            case Upload: return "/upload";
            default: return "/";
        }
    }


    public static void main(String[] args) {
        System.out.println(get(NavItems.Files));
    }

}
