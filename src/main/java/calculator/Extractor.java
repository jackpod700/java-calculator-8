package calculator;

import java.util.HashSet;

/**
 * 커스텀 구분자 및 숫자 추출 클래스
 *
 * Verifier를 통해 검증이 완료된 입력값을 받아 커스텀 구분자와 숫자를 추출한다.
 * */
public class Extractor {
    /**
     * 커스텀 구분자 추출 메서드
     *
     * 기본 구분자인 , : 에 커스텀 구분자를 더하여 HashSet으로 반환한다.
     * @param input 커스텀 구분자 영역 문자열
     * @return HashSet<String> 추출된 구분자 집합
     * */
    public HashSet<String> extractCustomDelimiter(String input) {
        HashSet<String> delimiters = new HashSet<>();
        delimiters.add(","); // 기본 구분자 추가
        delimiters.add(":"); // 기본 구분자 추가

        int currentIndex = 0;
        while (currentIndex < input.length()) {
            int nextOpenIndex = input.indexOf("//", currentIndex);
            int nextCloseIndex = input.indexOf("\\n", nextOpenIndex);
            String newDelimiter = input.substring(nextOpenIndex + 2, nextCloseIndex);
            delimiters.add(newDelimiter);
            currentIndex = nextCloseIndex + 2;
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
