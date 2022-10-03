package com.ucmate.socialmediasaver.FacebookVideo;

import android.util.Log;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.ExecutionException;
import com.ucmate.socialmediasaver.GetHtmlResponse;

public class facebook {
    public static String getvideo(String url) {
        try {
            String html_resp;
            html_resp = new GetHtmlResponse.get_response().execute(url).get();
            return String.valueOf(new JSONObject(get_url(html_resp.replace("\\", "").replace("  ",""))));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
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
    public static HashMap < String, String > get_url(String html_resp) {
        List < String > containedUrls = new ArrayList < String > ();
        HashMap < String, String > linkshash = new HashMap < String, String > ();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(html_resp);
        while (urlMatcher.find()) {
            containedUrls.add(html_resp.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));

        }
        for (int i = 0; i < containedUrls.size(); i++) {
            String urls = containedUrls.get(i);
            if (urls.contains("fbcdn.net/v/") && urls.contains(".mp4")) {
                if (urls.contains("&amp;")) {
                    linkshash.put("SD320", urls.replace("&amp;", "&"));
                }

                if (urls.contains("_nc_vts_internal")) {
                    linkshash.put("HD720", urls.replace("&amp;", "&"));

                }

                if (urls.contains("fbcdn.net/v/t66")) {
                    linkshash.put("SD480", urls.replace("&amp;", "&"));
                }

                if (urls.contains("fbcdn.net/v/t42") && urls.contains("_nc_cat=1&")) {
                    linkshash.put("MP3", urls.replace("&amp;", "&"));
                }

            }

        }
        if (linkshash.size()>0){
            linkshash.put("success", "true");
            linkshash.put("TITLE", get_title(html_resp));
        }
        else {
            linkshash.put("success", "false");
        }
        return linkshash;
    }


}