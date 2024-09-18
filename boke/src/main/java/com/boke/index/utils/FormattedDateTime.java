package com.boke.index.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormattedDateTime {
    public static String getNowDateTime(){
        LocalDateTime now = LocalDateTime.now();

        // 创建一个DateTimeFormatter对象，指定所需的格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 使用formatter格式化LocalDateTime对象
        String formattedDateTime = now.format(formatter);

        return formattedDateTime;
    }
}
