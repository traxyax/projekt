package sn.ashia.projekt.patcher;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class Patcher<T> {
    public void patch(T existing, T incomplete) throws IllegalAccessException {
        if (existing == null || incomplete == null) {
            throw new IllegalArgumentException("Both existing and incomplete objects must be non-null");
        }

        Class<?> clazz = existing.getClass();  // Use the runtime class of 'existing'
        while (clazz != null) {  // Traverse class hierarchy
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                boolean wasAccessible = field.isAccessible();
                field.setAccessible(true);
                Object value = field.get(incomplete);
                if (value != null) {
                    field.set(existing, value);
                }
                field.setAccessible(wasAccessible);  // Restore original accessibility
            }
            clazz = clazz.getSuperclass();  // Move to superclass
        }
    }
}
