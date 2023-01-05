package com.example.shoppingcart.util;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtil {

   static LocalDateTime now= LocalDateTime.now();
   static DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

   public static String getCurrentDateTime(){
       return now.format(format);
   }
}
