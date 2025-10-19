package calculator.common.enums;

public enum CustomDelimiterSyntax {
    // "접두사"
    PREFIX("//"),
    // "접미사"
    SUFFIX("\\n");

    private final String value;
    CustomDelimiterSyntax(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
