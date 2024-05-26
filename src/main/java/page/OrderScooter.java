package page;


import com.codeborne.selenide.Condition;
import helper.ByConverter;
import helper.PropertyLoaderStatic;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;
import ru.yandex.qatools.properties.annotations.Use;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Resource.Classpath("mainPage/scooterOrder.properties")
public class OrderScooter {

    @Property("orderBtn")
    @Use(ByConverter.class)
    private static By ORDERBTN;

    @Property("inputName")
    @Use(ByConverter.class)
    private static By INPUTNAME;

    @Property("inputSurname")
    @Use(ByConverter.class)
    private static By INPUTSURNAME;

    @Property("inputAdress")
    @Use(ByConverter.class)
    private static By INPUTADRESS;

    @Property("inputStantionInpt")
    @Use(ByConverter.class)
    private static By INPUTSTANTIONINPT;

    @Property("inputStantionList")
    @Use(ByConverter.class)
    private static By INPUTSTANTIONLIST;

    @Property("inputTel")
    @Use(ByConverter.class)
    private static By INPUTTEL;

    @Property("nextBtn")
    @Use(ByConverter.class)
    private static By NEXTBTN;

    @Property("datePickerInput")
    @Use(ByConverter.class)
    private static By DATEPICKERINPUT;

    @Property("datePickerToday")
    @Use(ByConverter.class)
    private static By DATEPICKERTODAY;

    @Property("pentalPeriodInput")
    @Use(ByConverter.class)
    private static By PENTALPERIODINPUT;

    @Property("pentalPeriodOptions")
    @Use(ByConverter.class)
    private static By PENTALPERIODOPTIONS;

    @Property("colorScooterOptios")
    @Use(ByConverter.class)
    private static By COLORSCOOTEROPTIOS;

    @Property("inputComment")
    @Use(ByConverter.class)
    private static By INPUTCOMMENT;

    @Property("finalOrderScooter")
    @Use(ByConverter.class)
    private static By FINALORDERSCOOTER;

    @Property("acceptOrderScooter")
    @Use(ByConverter.class)
    private static By ACCEPTORDERSCOOTER;

    @Property("successLabel")
    @Use(ByConverter.class)
    private static By SUCCESSLABEL;

    static {
        PropertyLoaderStatic.populate(OrderScooter.class);
    }

    public void openOrderForm(int index) {
        $$(ORDERBTN).get(index).scrollIntoView(true).click();
    }

    public String fillOrderParams(String[] params) {
        $(INPUTNAME).setValue(params[0]);

        $(INPUTSURNAME).setValue(params[1]);
        $(INPUTADRESS).setValue(params[2]);

        //Выбираем станцию метро
        $(INPUTSTANTIONINPT).click();
        $$(INPUTSTANTIONLIST).find(Condition.text(params[3])).scrollIntoView(true).click();

        $(INPUTTEL).setValue(params[4]);
        $(NEXTBTN).click();

        //Отрывается календарь и всегда выбирается текущая дата
        $(DATEPICKERINPUT).click();
        $(DATEPICKERTODAY).click();

        $(PENTALPERIODINPUT).click();
        //сделано так для случайного выбора из списка
        int option = new Random().nextInt($$(PENTALPERIODOPTIONS).size());
        $$(PENTALPERIODOPTIONS).get(option).click();

        //тоже самое для выбора цвета
        option = new Random().nextInt($$(COLORSCOOTEROPTIOS).size());
        $$(COLORSCOOTEROPTIOS).get(option).click();

        //Оставляем рандомный комментарий для заказа
        $(INPUTCOMMENT).sendKeys(RandomStringUtils.randomAlphanumeric(5));

        $(FINALORDERSCOOTER).click();
        $(ACCEPTORDERSCOOTER).click();

        //возвращает текст с финального попапа с номером заказа
        return $(SUCCESSLABEL)
                .shouldBe(Condition.visible, Duration.ofSeconds(30))
                //эта строчка тут из-за бага на
                .shouldBe(Condition.not(Condition.text("Хотите оформить заказ?")))
                .getText();
    }

}
