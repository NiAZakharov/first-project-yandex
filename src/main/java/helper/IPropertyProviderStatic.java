package helper;

import java.util.Properties;

public interface IPropertyProviderStatic {
    <T> Properties provide(Class<?> clazz, Properties properties);
}
