package question;

import helper.BaseScenario;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import page.ImpotantQueston;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AskAnswerTest extends BaseScenario {

    private final ImpotantQueston impotantQueston = new ImpotantQueston();
    @Disabled
    @ParameterizedTest(name = "Проверка вопроса {0}")
    @MethodSource("test_params.InputBodyParam#impotantQuestion")
    public void checkThatQuestionsHaveAnswers(Integer key, String value) {
        impotantQueston.scrollToQuestions(key);
        assertEquals(value, impotantQueston.getOpenQuestionAndGetAnswer(key), "Ответ на вопрос не соответствует ожидаемому");
    }

}
