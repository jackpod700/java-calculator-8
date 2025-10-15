package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {

    //성공 테스트 케이스(커스텀 구분자 X)
    @Test
    void 빈_문자열(){
        assertSimpleTest(() -> {
            run("");
            assertThat(output()).contains("결과 : 0");
        });
    }

    @Test
    void 숫자_하나(){
        assertSimpleTest(() -> {
            run("1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 숫자_및_기본_구분자_사용(){
        assertSimpleTest(() -> {
            run("1,2;3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    //성공 테스트 케이스(커스텀 구분자 O)
    @Test
    void 커스텀_구분자_하나() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 커스텀_구분자_여러개() {
        assertSimpleTest(() -> {
            run("//|\\n//e\\n1e2|3,4;5");
            assertThat(output()).contains("결과 : 15");
        });
    }

    //실패 테스트 케이스(숫자 및 구분자 영역)
    @Test
    void 허용_구분자_외_문자사용() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("1,-2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 구분자_위치_오류() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("1,2;3,"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    //실패 테스트 케이스(커스텀 구분자 영역)
    @Test
    void 커스텀_구분자_등록_시작_오류(){
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("#//$\\n1,2;3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분자_등록_문자쌍_오류(){
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("//#\\n//1"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분자_숫자등록_시도(){
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("//1\\n//1"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분자_등록_불가값_시도1(){
        // "//"는 구분자로 등록 불가
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("////\\n1,2;3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분자_등록_불가값_시도2(){
        // "\n"는 구분자로 등록 불가
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//\\n\\n1,2;3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
