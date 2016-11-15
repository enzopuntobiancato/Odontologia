package com.utn.tesis.util;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 14/09/16
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class DateParameter implements Serializable {
    private Calendar date;

    //GETTERS AND SETTERS
    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
    //MAIN
    public static DateParameter valueOf(String dateString){
        try{
            String trim = dateString.substring(0,10);
            DateParameter parameter = new DateParameter();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(formatter.parse(trim));
            parameter.setDate(cal);
            return parameter;
        }catch (Exception e){
           log.error("No se pudo convertir el parámetro en una fecha. " + e.getMessage());
            return null;
        }
    }

}
