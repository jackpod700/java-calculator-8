package calculator;

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
}
