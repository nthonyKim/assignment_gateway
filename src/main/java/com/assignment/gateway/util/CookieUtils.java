package com.assignment.gateway.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class CookieUtils {
    public static Map<String, String> parserCookie(Collection<String> cookie){
        return cookie.stream()
                .flatMap(c -> Arrays.stream(c.split(";\\s*")))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }
}
