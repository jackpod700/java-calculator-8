package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VerifierTest {
    private Verifier verifier;

    @BeforeEach
    void setUp() {
        verifier = new Verifier();
    }

    //커스텀 구분자 영역 검증 테스트
    @Test
    @DisplayName("커스텀 구분자 영역 입력 빈 문자열")
    void customVerifyEmptyString() {
        // given
        String input = "";

        // when
        boolean result = verifier.verifyCustomDelimiterDomain(input);

        // then
        assert(result);
    }

    @Test
    @DisplayName("커스텀 구분자 영역 올바른 형식")
    void customVerifyCorrectFormat() {
        // given
        String[] inputs = new String[]{"//#\\n","//#\\n//@\\n","///\\n"};
        // when
        boolean result = true;
        for(String in : inputs){
            result &= verifier.verifyCustomDelimiterDomain(in);
        }

        // then
        assert(result);
    }

    @Test
    @DisplayName("커스텀 구분자 영역 잘못된 형식 - //로 시작하지 않음")
    void customVerifyIncorrectFormatWrongStart() {
        // given
        String input = "#\\n";

        // when
        boolean result = verifier.verifyCustomDelimiterDomain(input);

        // then
        assert(!result);
    }

    @Test
    @DisplayName("커스텀 구분자 영역 잘못된 형식 - 구분자 영역 외 문자 존재")
    void customVerifyIncorrectFormatCharsOutOfDelimiterArea() {
        // given
        String input = "//#\\na//%\\n";

        // when
        boolean result = verifier.verifyCustomDelimiterDomain(input);

        // then
        assert(!result);
    }

    @Test
    @DisplayName("커스텀 구분자 영역 잘못된 형식 - 허용되지 않는 구분자 등록(숫자)")
    void customVerifyIncorrectFormatNumberDelimiter() {
        // given
        String[] inputs = new String[]{"//12\\n","//;\\n1;2\\n"};

        // when
        boolean result = false;
        for(String in : inputs){
            result |= verifier.verifyCustomDelimiterDomain(in);
        }

        // then
        assert(!result);
    }

    @Test
    @DisplayName("커스텀 구분자 영역 잘못된 형식 - 허용되지 않는 구분자 등록(//)")
    void customVerifyIncorrectFormatForbiddenDelimiter1() {
        // given
        String input = "////\\n";

        // when
        boolean result = verifier.verifyCustomDelimiterDomain(input);

        // then
        assert(!result);
    }

    @Test
    @DisplayName("커스텀 구분자 영역 잘못된 형식 - 허용되지 않는 구분자 등록(\\n)")
    void customVerifyIncorrectFormatForbiddenDelimiter2() {
        // given
        String input = "//\\n\\n";

        // when
        boolean result = verifier.verifyCustomDelimiterDomain(input);

        // then
        assert(!result);
    }
}
