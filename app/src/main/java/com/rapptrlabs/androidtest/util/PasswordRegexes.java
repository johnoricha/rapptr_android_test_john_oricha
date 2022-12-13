package com.rapptrlabs.androidtest.util;

import java.util.regex.Pattern;

public class PasswordRegexes {
    private static final String SEVEN_CHARS = ".{7,}";
    public static final Pattern AT_LEAST_7_CHARACTERS = Pattern.compile(SEVEN_CHARS);
    private static final String ONE_SPECIAL = "^.*(?=.*[!@#$%^&*()-+=?<>]).*$";
    public static final Pattern AT_LEAST_ONE_SPECIAL = Pattern.compile(ONE_SPECIAL);
    private static final String ONE_NUMBER = ".*[0-9].*";
    public static final Pattern AT_LEAST_ONE_NUMBER = Pattern.compile(ONE_NUMBER);
    private static final String ONE_LOWER = ".*[a-z].*";
    public static final Pattern AT_LEAST_ONE_LOWER_CASE = Pattern.compile(ONE_LOWER);
    private static final String ONE_UPPER = ".*[A-Z].*";
    public static final Pattern AT_LEAST_ONE_UPPER_CASE = Pattern.compile(ONE_UPPER);

}
