package com.rapptrlabs.androidtest.util;

import java.util.regex.Pattern;

public class PasswordRegexes {
    private static final String SEVEN_CHARS = ".{7,}";
    public static final Pattern AT_LEAST_7_CHARACTERS = Pattern.compile(SEVEN_CHARS);
    private static final String ONE_SPECIAL = "^.*(?=.*[!@#$%^&*()-+=?<>]).*$";
}
