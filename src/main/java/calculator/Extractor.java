package calculator;

import java.util.HashSet;

//커스텀 구분자 및 숫자 추출 클래스
public class Extractor {
    public HashSet<String> extractCustomDelimiter(String input) {
        HashSet<String> delimiters = new HashSet<>();
        delimiters.add(","); // 기본 구분자 추가
        delimiters.add(";"); // 기본 구분자 추가

        int currentIndex = 0;
        while(currentIndex < input.length()){
            int nextOpenIndex = input.indexOf("//", currentIndex);
            int nextCloseIndex = input.indexOf("\\n", nextOpenIndex);
            String newDelimiter = input.substring(nextOpenIndex + 2, nextCloseIndex);
            delimiters.add(newDelimiter);
            currentIndex = nextCloseIndex + 2;
        }
        return delimiters;
    }

    public Integer[] extractNumbers(String input) {
        if(input.isEmpty()){
            return new Integer[]{};
        }
        String[] numberStrings = input.split("\\D+");
        Integer[] numbers = new Integer[numberStrings.length];
        for(int i=0;i<numberStrings.length;i++){
            numbers[i] = Integer.parseInt(numberStrings[i]);
        }
        return numbers;
    }
}
