package helper;

import ru.yandex.qatools.properties.annotations.Resource;

import java.lang.annotation.Annotation;
import java.util.Properties;

import static ru.yandex.qatools.properties.utils.PropertiesUtils.readProperties;

/**
 * Вспомогательный класс служит для вычитывания ресурсов
 */
public class DefaultPropertyProviderStatic implements IPropertyProviderStatic {
    @Override
    public <T> Properties provide(Class<?> clazz, Properties properties) {

        if (have(clazz, Resource.Classpath.class)) {
            String path = classpath(clazz, properties);
            properties.putAll(readProperties(getClassLoader().getResourceAsStream(path)));
        }

        if (have(clazz, Resource.File.class)) {
            String path = filepath(clazz, properties);
            properties.putAll(readProperties(new java.io.File(path)));
        }

        properties.putAll(System.getProperties());
        return properties;
    }


    protected boolean have(Class<?> clazz, Class<? extends Annotation> anno) {
        return clazz.isAnnotationPresent(anno);
    }

    protected String filepath(Class<?> clazz, Properties properties) {
        return clazz.getAnnotation(Resource.File.class).value();
    }

    protected String classpath(Class<?> clazz, Properties properties) {

        Resource.Classpath a = clazz.getAnnotation(Resource.Classpath.class);
        return clazz.getAnnotation(Resource.Classpath.class).value();
    }

    private ClassLoader getClassLoader() {
        ClassLoader classLoader = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (SecurityException e) {
            // do nothing
        } finally {
            if (classLoader == null) {
                return ClassLoader.getSystemClassLoader();
            }
            return classLoader;
        }
    }
}
