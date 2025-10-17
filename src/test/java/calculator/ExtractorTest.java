package calculator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//이미 검증이 완료된 입력값만 받으므로 잘못된 형식에 대한 테스트는 필요 없음
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
        assert(result.equals(expected));
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

        assert(result.equals(expected));
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

        assert(result.equals(expected));
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
        String[] input = new String[]{"1,2;3,4","1,2;3add4"};

        // when & then
        Integer[] expected = new Integer[]{1,2,3,4};
        for(String in : input){
            Integer[] result = extractor.extractNumbers(in);
            assert Arrays.equals(result, expected);
        }
    }
}
