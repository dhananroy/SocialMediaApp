package com.ucmate.socialmediasaver.InstagramVideo;

import com.ucmate.socialmediasaver.GetHtmlResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class instagram {
    public static String getvideo(String url) {
        String html_resp;
        try {
            html_resp = new GetHtmlResponse.get_response().execute(base_url() + url).get();
            return html_resp;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }
    public static String base_url() {
        byte[] bytes = hexStringToByteArray("68747470733a2f2f75636d6174652e696e666f2f696e73746170703f75726c3d");
        return new String(bytes, StandardCharsets.UTF_8);
    }
    public static byte[] hexStringToByteArray(String hex) {
        int l = hex.length();
        byte[] data = new byte[l / 2];
        for (int i = 0; i < l; i += 2) {
            data[i / 2] = (byte)((Character.digit(hex.charAt(i), 16) << 4) +
                    Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
    public static String get_url(String html_resp) {
        List < String > containedUrls = new ArrayList < String > ();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(html_resp);
        while (urlMatcher.find()) {
            if (html_resp.substring(urlMatcher.start(0),
                    urlMatcher.end(0)).contains(".mp4")) {
                containedUrls.add(html_resp.substring(urlMatcher.start(0),
                        urlMatcher.end(0)));
            }
        }
        if (containedUrls.size() > 0) {
            try {
                byte ptext[] = containedUrls.get(0).replace("\\u0026", "&").getBytes("ISO-8859-1");
                return new String(ptext, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static String get_title(String html_resp) {
        String video_ttl = null;
        Pattern patterns = Pattern.compile("<title.*?>(.+?)</title>", Pattern.CASE_INSENSITIVE);
        Matcher urlMatcherq = patterns.matcher(html_resp);

        while (urlMatcherq.find()) {
            video_ttl = html_resp.substring(urlMatcherq.start(0),
                    urlMatcherq.end(0));
            video_ttl = video_ttl.replace("<title>", "").replace("</title>", "");
        }
        return video_ttl;
    }

}