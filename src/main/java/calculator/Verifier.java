package calculator;

import calculator.common.enums.CustomDelimiterSyntax;
import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * 입력값 검증 클래스 커스텀 구분자 영역과 숫자 및 구분자 영역의 형식을 검증하는 메서드를 제공한다.
 */
public class Verifier {
    private static final String PREFIX = CustomDelimiterSyntax.PREFIX.getValue();
    private static final String SUFFIX = CustomDelimiterSyntax.SUFFIX.getValue();
    private static final int PREFIX_LENGTH = PREFIX.length();
    private static final int SUFFIX_LENGTH = SUFFIX.length();

    private static final Pattern NOT_ALLOWED_CUSTOM_DELIMITER = Pattern.compile(
            "\\d"+"|"+          // 숫자
            PREFIX+"|"+ // PREFIX
            SUFFIX     // SUFFIX
    );

    /**
     * 커스텀 구분자 영역 검증 메서드
     *
     * @param customDelimiterDomain 커스텀 구분자 영역 문자열
     * @return boolean 올바른 형식인지 여부
     */
    public boolean verifyCustomDelimiterDomain(String customDelimiterDomain) {
        if (customDelimiterDomain.isEmpty()) {
            return true;
        }

        while(customDelimiterDomain.startsWith(PREFIX)){
            int nextSuffixIndex = customDelimiterDomain.indexOf(SUFFIX);

            // SUFFIX가 존재하지 않으면 올바르지 않은 형식
            if(nextSuffixIndex == -1){
                return false;
            }

            // 구분자에 숫자, PREFIX 또는 SUFFIX가 포함되어 있으면 올바르지 않은 형식
            String newDelimiter = customDelimiterDomain.substring(PREFIX_LENGTH, nextSuffixIndex);
            if (NOT_ALLOWED_CUSTOM_DELIMITER.matcher(newDelimiter).find()) {
                return false;
            }

            // 다음 SUFFIX가 마지막 SUFFIX라면 검증 종료(올바른 형식)
            if(nextSuffixIndex + SUFFIX_LENGTH >= customDelimiterDomain.length()){
                return true;
            }

            // 다음 SUFFIX 이후로 계속 검사
            customDelimiterDomain = customDelimiterDomain.substring(nextSuffixIndex + SUFFIX_LENGTH);
        }

        return false;
    }

    /**
     * 숫자 및 구분자 영역 검증 메서드
     *
     * @param input      숫자 및 구분자 영역 문자열
     * @param delimiters 추출된 구분자 집합
     * @return boolean 올바른 형식인지 여부
     */
    public boolean verifyNumberAndDelimiterDomain(String input, HashSet<String> delimiters) {
        if (input.isEmpty()) {
            return true;
        }

        // 구분자가 맨 앞이나 맨 뒤에 오는지 확인
        if (!Character.isDigit(input.charAt(0)) || !Character.isDigit(input.charAt(input.length() - 1))) {
            return false;
        }

        // 구분자가 연속으로 오는지 확인
        for (int currentIndex = 0; currentIndex < input.length(); currentIndex++) {
            // 숫자가 오면 다음 인덱스로
            if (Character.isDigit(input.charAt(currentIndex))) {
                continue;
            }

            String currentDelimiter = null;
            // 숫자가 아닌 문자가 오면 다음 숫자가 나오는 위치를 찾는다
            for (int nextDigitIndex = currentIndex; nextDigitIndex < input.length(); nextDigitIndex++) {
                if (Character.isDigit(input.charAt(nextDigitIndex))) {
                    // 다음 숫자까지가 구분자 영역
                    currentDelimiter = input.substring(currentIndex, nextDigitIndex);
                    currentIndex = nextDigitIndex;
                    break;
                }
                if (nextDigitIndex == input.length() - 1) {
                    return false;
                }
            }

            // 찾은 구분자가 등록된 구분자인지 확인
            if (currentDelimiter == null || !delimiters.contains(currentDelimiter)) {
                return false;
            }
        }
        return true;
    }
}
