package net.microgoose.mocknet.factory;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;
import java.util.Arrays;

public class TestDataFactoryExtension implements BeforeEachCallback {
    
    @Override
    public void beforeEach(ExtensionContext context) {
        Object testInstance = context.getRequiredTestInstance();
        
        Arrays.stream(testInstance.getClass().getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(TestFactory.class))
            .forEach(field -> injectFactory(testInstance, field));
    }
    
    private void injectFactory(Object testInstance, Field field) {
        try {
            field.setAccessible(true);
            Object factoryInstance = field.getType().getDeclaredConstructor().newInstance();
            field.set(testInstance, factoryInstance);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject factory: " + field.getName(), e);
        }
    }
}