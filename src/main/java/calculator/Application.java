package calculator;
import camp.nextstep.edu.missionutils.Console;
import java.util.HashSet;

public class Application {

    public static void main(String[] args) {
        DomainDivider domainDivider = new DomainDivider();
        Verifier verifier = new Verifier();
        Extractor extractor = new Extractor();

        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String userInput = Console.readLine();

        // 1. 도메인 분리
        String[] domains = domainDivider.divideDomain(userInput);

        // 2. 커스텀 구분자 영역 검증
        if(!verifier.verifyCustomDelimiterDomain(domains[0])){
            throw new IllegalArgumentException();
        }

        // 3. 커스텀 구분자 추출
        HashSet<String> delimiters = extractor.extractCustomDelimiter(domains[0]);

        // 4. 숫자 및 구분자 영역 검증
        if(!verifier.verifyNumberAndDelimiterDomain(domains[1], delimiters)){
            throw new IllegalArgumentException();
        }

        // 5. 숫자 추출
        Integer[] numbers = extractor.extractNumbers(domains[1]);

        // 6. 덧셈 계산 및 출력
        int sum = 0;
        for(int number : numbers){
            sum += number;
        }
        System.out.println("결과 : " + sum);
    }
}
