package test_params;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Locale;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public final class InputBodyParam {

    private static String[] station = {"Черкизовская", "Сокольники", "Красносельская", "Комсомольская", "Фрунзенская"};


    public static Stream<Arguments> impotantQuestion() {
        return Stream.of(
                arguments(0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                arguments(1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете " +
                        "просто сделать несколько заказов — один за другим."),
                arguments(2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт " +
                        "времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат " +
                        "8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."),
                arguments(3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."),
                arguments(4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому " +
                        "номеру 1010."),
                arguments(5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если " +
                        "будете кататься без передышек и во сне. Зарядка не понадобится."),
                arguments(6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. " +
                        "Все же свои."),
                arguments(7, "Да, обязательно. Всем самокатов! И Москве, и Московской области.")
        );
    }

    public static Stream<Arguments> orderScooter() {
        //первый параметр отвечает за кнопку заказа. Затем Имя, Фамилия, Адрес, Станция, Номер телефона

        Faker faker = new Faker(new Locale("ru_Ru","RU"));

        return Stream.of(
                arguments(
                        0,
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.address().streetAddress(),
                        station[new Random().nextInt(station.length)],
                        RandomStringUtils.randomNumeric(11)),
                arguments(
                        1,
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.address().streetAddress(),
                        station[new Random().nextInt(station.length)],
                        RandomStringUtils.randomNumeric(11))
                );
    }
}
