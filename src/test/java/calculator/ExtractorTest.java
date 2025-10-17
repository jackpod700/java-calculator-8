package calculator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Extractor 클래스의 단위 테스트를 담당하는 클래스
 * 다양한 입력 시나리오에 대해 extractCustomDelimiter() 및 extractNumbers() 메서드를 실행하고, 예상되는 출력 결과를 검증한다.
 * Verifier 거치고 사용되기 때문에 검증이 완료된 입력값만을 사용한다.
 */
public class ExtractorTest {

    private Extractor extractor;

    @BeforeEach
    void setUp() {
        extractor = new Extractor();
    }

    //커스텀 구분자 추출 테스트
    @Test
    @DisplayName("커스텀 구분자 추출 - 빈 문자열")
    void extractCustomDelimiter() {
        // given
        String input = "";

        // when
        HashSet<String> result = extractor.extractCustomDelimiter(input);

        // then
        HashSet<String> expected = new HashSet<>();
        expected.add(",");
        expected.add(";");
        assert (result.equals(expected));
    }

    @Test
    @DisplayName("커스텀 구분자 추출 - 단일 구분자")
    void extractSingleCustomDelimiter() {
        // given
        String input = "//@\\n";

        // when
        HashSet<String> result = extractor.extractCustomDelimiter(input);

        // then
        HashSet<String> expected = new HashSet<>();
        expected.add(";");
        expected.add(",");
        expected.add("@");

        assert (result.equals(expected));
    }

    @Test
    @DisplayName("커스텀 구분자 추출 - 다중 구분자")
    void extractMultipleCustomDelimiters() {
        // given
        String input = "//add\\n//@\\n";

        // when
        HashSet<String> result = extractor.extractCustomDelimiter(input);

        // then
        HashSet<String> expected = new HashSet<>();
        expected.add(";");
        expected.add(",");
        expected.add("add");
        expected.add("@");

        assert (result.equals(expected));
    }

    //숫자 추출 테스트
    @Test
    @DisplayName("숫자 추출 - 빈 문자열")
    void extractNumbersEmptyString() {
        // given
        String input = "";

        // when
        Integer[] result = extractor.extractNumbers(input);

        // then
        Integer[] expected = new Integer[]{};
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("숫자 추출 - 단일 숫자")
    void extractNumbersSingleNumber() {
        // given
        String input = "123";

        // when
        Integer[] result = extractor.extractNumbers(input);

        // then
        Integer[] expected = new Integer[]{123};
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("숫자 추출 - 다중 숫자")
    void extractNumbersMultipleNumbers() {
        // given
        String[] input = new String[]{"1,2;3,4", "1,2;3add4"};

        // when & then
        Integer[] expected = new Integer[]{1, 2, 3, 4};
        for (String in : input) {
            Integer[] result = extractor.extractNumbers(in);
            assert Arrays.equals(result, expected);
        }
    }
}
