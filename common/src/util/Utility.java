package util

import java.util.List;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BytesCombiner {
    public static byte[] concatContentParts(List<byte[]> parts) {
        int totalLength = 0;
        for (byte[] array : parts) {
            totalLength += array.length;
        }
        byte[] result = new byte[totalLength];
        int offset = 0;
        for (byte[] array : parts) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}

public class HttpPost {
    public static String formUploadRetString(String urlStr, Map<String, String> textMap,
                                             Map<String, byte[]> fileMap) {
        byte[] ret = formUpload(urlStr, textMap, fileMap);
        return new String(ret);
    }

    public static byte[] formUploadRetBytes(String urlStr, Map<String, String> textMap,
                                            Map<String, byte[]> fileMap) {
        return formUpload(urlStr, textMap, fileMap);
    }

    @SuppressWarnings("rawtypes")
    private static byte[] formUpload(String urlStr, Map<String, String> textMap,
                                    Map<String, byte[]> fileMap) {
        String res = "";
        HttpURLConnection conn = null;
        // boundary就是request头和上传文件内容的分隔符
        String BOUNDARY = "---------------------------123821742118716";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuilder strBuf = new StringBuilder();
                for (Object o : textMap.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"").append(inputName).append("\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }
            // file
            if (fileMap != null) {
                for (Object o : fileMap.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    String inputName = (String) entry.getKey();
                    byte[] inputValue = (byte[]) entry.getValue();

                    String contentType = "application/octet-stream";
                    String strBuf = "\r\n" + "--" + BOUNDARY +
                            "\r\n" +
                            "Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + inputName
                            + "\"\r\n" +
                            "Content-Type:" + contentType + "\r\n\r\n";
                    out.write(strBuf.getBytes());
                    out.write(inputValue);
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            // 读取返回数据
            InputStream inputStream = conn.getInputStream();
            int contentLength = conn.getContentLength();
            byte[] content = new byte[contentLength];
            byte[] buffer = new byte[1024 * 1024];
            int readLen = 0;
            int destPos = 0;
            while ((readLen = inputStream.read(buffer)) > 0) {
                System.arraycopy(buffer, 0, content, destPos, readLen);
                destPos += readLen;
            }

            return content;

        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
            return new byte[0];
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
    }
}

public class Str2MD5 {
    public static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            ret.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return ret.toString();
    }
}