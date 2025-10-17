package calculator;

import java.util.HashSet;
import java.util.regex.Pattern;

public class Verifier {

    private final Pattern NOT_ALLOWED_PATTERN = Pattern.compile("\\d|//");

    public boolean verifyCustomDelimiterDomain(String customDelimiterDomain){

        if(customDelimiterDomain.isEmpty()){
            return true;
        }

        if(customDelimiterDomain.startsWith("//")){// //로 시작하는지 확인
            boolean isOpen=true;
            int currentIndex=2; // // 다음 인덱스부터 시작
            while(currentIndex<customDelimiterDomain.length()-1){
                if(isOpen){
                    int nextOpenIndex = customDelimiterDomain.indexOf("//",currentIndex);
                    int nextCloseIndex = customDelimiterDomain.indexOf("\\n",currentIndex);

                    // currentIndex 이후로 //가 존재할 때 \n보다 앞에 있으면 오류
                    if(nextOpenIndex<nextCloseIndex && nextOpenIndex!=-1){
                        return false;
                    }

                    // currentIndex부터 nextCloseIndex까지가 새로운 구분자
                    String newDelimiter = customDelimiterDomain.substring(currentIndex,nextCloseIndex);

                    // 구분자에 숫자 혹은 //가 포함되어 있으면 오류(\n은 위의 코드상 존재 불가능)
                    if(NOT_ALLOWED_PATTERN.matcher(newDelimiter).find()){
                        return false;
                    }

                    currentIndex = nextCloseIndex+2;
                    isOpen=false;
                }
                else{
                    int nextOpenIndex = customDelimiterDomain.indexOf("//",currentIndex);

                    // 구분자 영역이 닫힌 상태에서 currentIndex가 //가 아닌 다른 문자로 시작하면 오류
                    if(nextOpenIndex!=currentIndex){
                        return false;
                    }
                    currentIndex = nextOpenIndex+2;
                    isOpen=true;
                }
            }
            return true;
        }
        return false;
    }

    public boolean verifyNumberAndDelimiterDomain(String input, HashSet<String> delimiters){
        if(input.isEmpty()){
            return true;
        }

        // 구분자가 맨 앞이나 맨 뒤에 오는지 확인
        if(!Character.isDigit(input.charAt(0)) || !Character.isDigit(input.charAt(input.length()-1))){
            return false;
        }

        // 구분자가 연속으로 오는지 확인

        for(int currentIndex=0;currentIndex<input.length();currentIndex++){
            // 숫자가 오면 다음 인덱스로
            if(Character.isDigit(input.charAt(currentIndex))){
                continue;
            }

            String currentDelimiter = null;
            // 숫자가 아닌 문자가 오면 다음 숫자가 나오는 위치를 찾는다
            for(int nextDigitIndex=currentIndex;nextDigitIndex<input.length();nextDigitIndex++){
                if(Character.isDigit(input.charAt(nextDigitIndex))){
                    // 다음 숫자까지가 구분자 영역
                    currentDelimiter = input.substring(currentIndex,nextDigitIndex);
                    currentIndex = nextDigitIndex;
                    break;
                }
                if(nextDigitIndex==input.length()-1){
                    return false;
                }
            }

            // 찾은 구분자가 등록된 구분자인지 확인
            if(currentDelimiter==null || !delimiters.contains(currentDelimiter)){
                return false;
            }
        }
        return true;
    }
}
