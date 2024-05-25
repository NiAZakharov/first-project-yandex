package helper;

import org.apache.commons.beanutils.Converter;
import org.openqa.selenium.By;

/**
 * Конвертирует селектор в нужный тип.
 * служит для более простой записи обращению к слекторам
 * SelenideElement element(css = #selector) -> $(element)
 */
public class ByConverter implements Converter {

    @Override
    public Object convert(Class aClass, Object o) {
        String str = (String) o;
        By selfBy = null;

        if (str.substring(0, str.indexOf('=')).toLowerCase().contains("id")) {
            selfBy = By.id(str.substring(str.indexOf('=') + 1).trim());
        }

        if (str.substring(0, str.indexOf('=')).toLowerCase().contains("class")) {
            selfBy = By.className(str.substring(str.indexOf('=') + 1).trim());
        }

        if (str.substring(0, str.indexOf('=')).toLowerCase().contains("name")) {
            selfBy = By.name(str.substring(str.indexOf('=') + 1).trim());
        }

        if (str.substring(0, str.indexOf('=')).toLowerCase().contains("tag")) {
            selfBy = By.tagName(str.substring(str.indexOf('=') + 1).trim());
        }

        if (str.substring(0, str.indexOf('=')).toLowerCase().contains("xpath")) {
            selfBy = By.xpath(str.substring(str.indexOf('=') + 1).trim());
        }

        if (str.substring(0, str.indexOf('=')).toLowerCase().contains("css")) {
            selfBy = By.cssSelector(str.substring(str.indexOf('=') + 1).trim());
        }

        return selfBy;
    }
}
