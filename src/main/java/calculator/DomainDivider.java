package calculator;

import calculator.common.enums.CustomDelimiterSyntax;

/**
 * 도메인 분리 클래스
 */
public class DomainDivider {
    private static final String SUFFIX = CustomDelimiterSyntax.SUFFIX.getValue();

    /**
     * 도메인 분리 메서드
     * 사용자의 입력에 대하여 SUFFIX가 존재할 시
     *  * 마지막 SUFFIX를 기준으로 커스텀 구분자 영역(전), 숫자 및 구분자 영역(후)로 나눈다.
     * SUFFIX가 존재하지 않을 시
     *  * 빈 문자열(전), 원본 문자열(후)을 반환한다.
     *
     * @param input 사용자의 입력 문자열
     * @return 커스텀 구분자 영역(전), 숫자 및 구분자 영역(후)
     */
    public String[] divideDomain(String input) {
        int index = input.lastIndexOf(SUFFIX);
        if (index == -1) {
            return new String[]{"", input};
        }
        return new String[]{input.substring(0, index + SUFFIX.length()),
                input.substring(index + SUFFIX.length())};
    }
}
