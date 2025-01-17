package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PrizeTest {

    @DisplayName("1글자 미만, 5글지 초과면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "abcdef"})
    void createFail(String input) {
        assertThatCode(() -> new Prize(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("1글자 이상, 5글자 이하면 예외를 발생하지않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"꽝", "50000"})
    void createSuccess(String input) {
        assertThatCode(() -> new Prize(input))
                .doesNotThrowAnyException();
    }
}
