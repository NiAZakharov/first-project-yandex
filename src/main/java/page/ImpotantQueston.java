package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import helper.ByConverter;
import helper.PropertyLoaderStatic;
import org.openqa.selenium.By;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;
import ru.yandex.qatools.properties.annotations.Use;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$;

@Resource.Classpath("mainPage/impotantQuestion.properties")
public class ImpotantQueston {
    @Property("impotant_ask_question_list")
    @Use(ByConverter.class)
    private static By IMPOTANT_ASK_QUESTION_LIST;

    @Property("impotant_ask_answer_list")
    @Use(ByConverter.class)
    private static By IMPOTANT_ASK_ANSWER_LIST;


    static {
        PropertyLoaderStatic.populate(ImpotantQueston.class);
    }

    public void scrollToQuestions(int index) {
        $$(IMPOTANT_ASK_QUESTION_LIST).get(index).scrollIntoView(true);
    }

    public String getOpenQuestionAndGetAnswer(int index) {
        $$(IMPOTANT_ASK_QUESTION_LIST)
                .get(index)
                .click();
        return $$(IMPOTANT_ASK_ANSWER_LIST)
                .get(index)
                .shouldBe(Condition.visible, Duration.ofSeconds(30))
                .getText();

    }

}