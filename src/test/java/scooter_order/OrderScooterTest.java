package scooter_order;

import helper.BaseScenario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import page.OrderScooter;

public class OrderScooterTest extends BaseScenario {

    private final OrderScooter orderScooter = new OrderScooter();

    private static boolean isNumeric(String str) {
        boolean hasNumeric = false;
        for (int i = 0; i < str.length() && !hasNumeric; i++) {
            if (Character.isDigit(str.charAt(i))) {
                hasNumeric = true;
            }
        }
        return hasNumeric;
    }

    @ParameterizedTest(name = "Заказ для {1}")
    @MethodSource("test_params.InputBodyParam#orderScooter")
    public void OrderInHeaderBtnTest(Integer buttonId, String name, String surname, String address, String station,
                                     String tel) {

        orderScooter.openOrderForm(buttonId);

        String[] data = {name, surname, address, station, tel};
        //Первая проверка, что окошко появилось находится внутри метода по средствам самого Selenide
        String msg = orderScooter.fillOrderParams(data);

        Assertions.assertTrue(isNumeric(msg), "Итоговое сообщение не содержит номера заказа");

    }

}
