package calculator.common.enums;

import java.util.HashSet;

public enum DefaultDelimiter {
    COMMA(","),
    COLON(":");

    private final String value;

    DefaultDelimiter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static HashSet<String> getDefaultDelimiters() {
        HashSet<String> delimiters = new HashSet<>();
        for(DefaultDelimiter delimiter : DefaultDelimiter.values()) {
            delimiters.add(delimiter.getValue());
        }
        return delimiters;
    }
}
