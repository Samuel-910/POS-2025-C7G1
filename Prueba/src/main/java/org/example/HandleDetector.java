package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandleDetector {
    public List<String> detectUserHandles(String text) {
        List<String> handles = new ArrayList<>();
        Pattern pattern = Pattern.compile("@[\\w+]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            handles.add(matcher.group());
        }
        return handles;
    }

    public List<String> detectHashtagHandles(String text) {
        List<String> handles = new ArrayList<>();
        Pattern pattern = Pattern.compile("#[\\w+]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            handles.add(matcher.group());
        }
        return handles;
    }

    public List<String> detectWebHandles(String text) {
        List<String> handles = new ArrayList<>();
        Pattern pattern = Pattern.compile("(https?://\\S+|www\\.\\S+)");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            handles.add(matcher.group());
        }
        return handles;
    }
}
