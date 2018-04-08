package com.pc.util;

import java.lang.reflect.Field;

public class DataGridUtil {
    public static String getFields(Class t) {
        Field[] fields = t.getDeclaredFields();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i < fields.length) {
                builder.append(fields[i].getName() + ",");
            } else {
                builder.append(fields[i].getName());
            }
        }
        return builder.toString();
    }
}
