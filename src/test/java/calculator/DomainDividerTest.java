package calculator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DomainDividerTest {
    private DomainDivider domainDivider;

    @BeforeEach
    void setUp() {
        domainDivider = new DomainDivider();
    }

    @Test
    @DisplayName("구분자가 포함된 경우 마지막 구분자를 기준으로 앞/뒤 문자열로 나눈다")
    void divideWhenSeparatorExists() {
        // given
        String input = "//;\\n1;2;3";

        // when
        String[] result = domainDivider.divideDomain(input);

        // then
        String[] expected = new String[]{"//;\\n", "1;2;3"};
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("구분자가 여러 개 포함된 경우에도 마지막 구분자를 기준으로 나눈다")
    void divideWithMultipleSeparators() {
        // given
        String input = "//;\\n1;2\\n3;4";

        // when
        String[] result = domainDivider.divideDomain(input);

        // then
        String[] expected = new String[]{"//;\\n1;2\\n", "3;4"};
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("구분자가 포함되지 않은 경우 앞부분은 빈 문자열, 뒷부분은 원본 문자열을 반환한다")
    void divideWhenSeparatorNotExists() {
        // given
        String input = "1,2,3";

        // when
        String[] result = domainDivider.divideDomain(input);

        // then
        String[] expected = new String[]{"", "1,2,3"};
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("입력값이 빈 문자열인 경우 두 부분 모두 빈 문자열을 반환한다")
    void divideWithEmptyInput() {
        // given
        String input = "";

        // when
        String[] result = domainDivider.divideDomain(input);

        // then
        String[] expected = new String[]{"", ""};
        assertArrayEquals(expected, result);
    }
}
