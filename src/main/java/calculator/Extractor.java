package calculator;

import calculator.common.enums.CustomDelimiterSyntax;
import calculator.common.enums.DefaultDelimiter;
import java.util.HashSet;

/**
 * 커스텀 구분자 및 숫자 추출 클래스
 *
 * Verifier를 통해 검증이 완료된 입력값을 받아 커스텀 구분자와 숫자를 추출한다.
 * */
public class Extractor {
    private static final String PREFIX = CustomDelimiterSyntax.PREFIX.getValue();
    private static final String SUFFIX = CustomDelimiterSyntax.SUFFIX.getValue();
    private static final int PREFIX_LENGTH = PREFIX.length();
    private static final int SUFFIX_LENGTH = SUFFIX.length();


    /**
     * 커스텀 구분자 추출 메서드
     *
     * 기본 구분자인 , : 에 커스텀 구분자를 더하여 HashSet으로 반환한다.
     * @param input 커스텀 구분자 영역 문자열
     * @return HashSet<String> 추출된 구분자 집합
     * */
    public HashSet<String> extractCustomDelimiter(String input) {
        HashSet<String> delimiters = DefaultDelimiter.getDefaultDelimiters();

        int currentIndex = 0;
        while (currentIndex < input.length()) {
            int nextOpenIndex = input.indexOf(PREFIX, currentIndex);
            int nextCloseIndex = input.indexOf(SUFFIX, nextOpenIndex);
            String newDelimiter = input.substring(nextOpenIndex + PREFIX_LENGTH, nextCloseIndex);
            delimiters.add(newDelimiter);
            currentIndex = nextCloseIndex + SUFFIX_LENGTH;
        }
        return delimiters;
    }

    /**
     * 숫자 추출 메서드
     *
     * 입력 문자열에서 숫자만 추출하여 Integer 배열로 반환한다.
     * @param input 숫자 및 구분자 영역 문자열
     * @return Integer[] 추출된 숫자 배열
     * */
    public Integer[] extractNumbers(String input) {
        if (input.isEmpty()) {
            return new Integer[]{};
        }
        String[] numberStrings = input.split("\\D+");
        Integer[] numbers = new Integer[numberStrings.length];
        for (int i = 0; i < numberStrings.length; i++) {
            numbers[i] = Integer.parseInt(numberStrings[i]);
        }
        return numbers;
    }
}
