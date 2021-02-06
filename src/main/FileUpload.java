package main;

import java.io.*;
import java.util.Arrays;

public class FileUpload {


    public static String handleRequestBody(InputStream in, String boundary, int contentLength){
        byte[] requestBytes = getBytes(in, contentLength);
        int headerEnd = findStringInByteArray(requestBytes, new String(new byte[]{'\r', '\n', '\r', '\n'}), 0);
        int footerEnd = findStringInByteArray(requestBytes, boundary, headerEnd);
        // extract filename from header
        byte[] headerBytes = Arrays.copyOfRange(requestBytes, 0, headerEnd);
        String filename = new String(headerBytes);
        filename = filename.substring(filename.indexOf("filename=\""));
        filename = filename.substring(filename.indexOf("\"")+ 1);
        filename = filename.substring(0, filename.indexOf("\""));
        // validate filename not to overwrite an existing file
        // also adds absolute path
        filename = validateFilename(filename);
        // write file to disk
        byte[] newFileBytes = Arrays.copyOfRange(requestBytes, headerEnd, footerEnd - boundary.length());
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(new File(filename));
            fos.write(newFileBytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.error(String.format("Could not write file: %s", filename));
        }
        return filename;
    }

    private static byte[] getBytes(InputStream in, int contentLength){
        try {
            byte[] bytes = new byte[contentLength];
            int b;
            int i = 0;
            while((b = in.read()) != -1){
                bytes[i++] = (byte) b;
            }
            in.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }


//    Find a string in a byte array. Will return the last inclusive byte index of the array.
//    return -1  if not found, otherwise the last index of the sequence.
    private static int findStringInByteArray(byte[] bytes, String str, int startAt){
        byte[] strBytes = str.getBytes();
        outer:
        for(int i = startAt; i< bytes.length - strBytes.length + 1; i++){
            if(bytes[i] == strBytes[0]){
                for(int j = 1; j <strBytes.length; j++){
                    if(bytes[i+j] != strBytes[j]) continue outer;
                }
                return i + strBytes.length;
            }
        }
        Logger.debug(String.format("Could not find String %s in RequestBody", str ));
        return -1;
    }

    // validate filename not to overwrite an existing file
    // also adds absolute path
    private static String validateFilename(String filename){
        File file = new File(Config.pathToFiles + Config.fileSeparator + filename);
        int i = 1;
        while(file.exists()) {
            if (filename.contains(".")) {
                int lastDot = filename.lastIndexOf(".");
                file = new File(Config.pathToFiles + Config.fileSeparator + filename.substring(0, lastDot) + "("+ i++ + ")" + filename.substring(lastDot));

            } else {
                file = new File(Config.pathToFiles + Config.fileSeparator + filename + "("+ i++ + ")");
            }
        }
        return file.getAbsolutePath();
    }

}
