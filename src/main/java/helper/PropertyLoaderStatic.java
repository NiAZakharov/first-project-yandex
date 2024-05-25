package helper;

import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.decorators.DefaultFieldDecorator;
import ru.yandex.qatools.properties.decorators.FieldDecorator;
import ru.yandex.qatools.properties.exeptions.PropertyLoaderException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * Вспомогательный класс - забирает селекторы страниц из properties
 */
public class PropertyLoaderStatic {

    public static <T> void populate(Class<?> clazz) {
        populate(clazz, new Properties());
    }

    public static <T> void populate(Class<?> clazz, Properties properties) {
        IPropertyProviderStatic provider = instantiatePropertyProvider(clazz);
        Properties completed = provider.provide(clazz, properties);
        populate(clazz, new DefaultFieldDecorator(completed));
    }

    public static <T> void populate(Class<?> clazz, FieldDecorator decorator) {
        initFields(decorator, clazz);
    }

    private static <T> void initFields(FieldDecorator decorator, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Property.class) == null) {
                continue;
            }
            if ((field.getModifiers() & Modifier.STATIC) != Modifier.STATIC) {
                throw new PropertyLoaderException(
                        String.format("Field <%s> of <%s> class is not static", field, clazz.getName()),
                        new IllegalAccessException("Field is not static"));
            }
            Object value = decorator.decorate(field);
            if (value != null) {
                try {
                    field.setAccessible(true);

                    field.set(null, value);
                } catch (IllegalAccessException e) {
                    throw new PropertyLoaderException(
                            String.format("Can not set <%s> static field of <%s> class", field, clazz.getName()),
                            e);
                }
            }
        }
    }

    private static <T> IPropertyProviderStatic instantiatePropertyProvider(Class<?> clazz) {

        if (clazz.isAnnotationPresent(WithStatic.class)) {
            try {
                return clazz.getAnnotation(WithStatic.class).value().newInstance();
            } catch (InstantiationException e) {
                throw new PropertyLoaderException("Can't create instance property provider in class "
                        + clazz.getName(), e);
            } catch (IllegalAccessException e) {
                throw new PropertyLoaderException("Can't load property provider in class "
                        + clazz.getName(), e);
            }
        }
        return new DefaultPropertyProviderStatic();
    }
}
