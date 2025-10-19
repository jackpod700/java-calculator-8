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

    private static final Pattern NOT_ALLOWED_PATTERN = Pattern.compile(
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

        if (customDelimiterDomain.startsWith(PREFIX)) {// PREFIX로 시작하는지 확인
            boolean isOpen = true;
            int currentIndex = PREFIX_LENGTH; // PREFIX 다음 인덱스부터 시작
            while (currentIndex < customDelimiterDomain.length() - 1) {
                if (isOpen) {
                    int nextPrefixIndex = customDelimiterDomain.indexOf(PREFIX, currentIndex);
                    int nextSuffixIndex = customDelimiterDomain.indexOf(SUFFIX, currentIndex);

                    // currentIndex 이후로 PREFIX가 존재할 때 SUFFIX보다 앞에 있으면 오류
                    if (nextPrefixIndex < nextSuffixIndex && nextPrefixIndex != -1) {
                        return false;
                    }

                    // currentIndex부터 nextSuffixIndex까지가 새로운 구분자
                    String newDelimiter = customDelimiterDomain.substring(currentIndex, nextSuffixIndex);

                    // 구분자에 숫자 혹은 PREFIX 또는 SUFFIX가 포함되어 있으면 오류
                    if (NOT_ALLOWED_PATTERN.matcher(newDelimiter).find()) {
                        return false;
                    }

                    currentIndex = nextSuffixIndex + SUFFIX_LENGTH;
                    isOpen = false;
                } else {
                    int nextPrefixIndex = customDelimiterDomain.indexOf(PREFIX, currentIndex);

                    // 구분자 영역이 닫힌 상태에서 currentIndex가 PREFIX가 아닌 다른 문자로 시작하면 오류
                    if (nextPrefixIndex != currentIndex) {
                        return false;
                    }
                    currentIndex = nextPrefixIndex + PREFIX_LENGTH;
                    isOpen = true;
                }
            }
            return true;
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
