/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Maxi
 */
public class FechaUtils {

    public static String fechaMaskFormat(Calendar fecha, String patron) {
        if (fecha == null || patron == null) {
            return null;
        }
        SimpleDateFormat date_format = new SimpleDateFormat(patron);
        return date_format.format(fecha.getTime());
    }

    public static String fechaConSeparador(Calendar fecha) {
        if (fecha == null) {
            return null;
        }
        SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
        return date_format.format(fecha.getTime());
    }
    
     public static Calendar convertDateToCalendar(Date d1){
        Calendar c = Calendar.getInstance();
        c.setTime(d1);
        
        return c;
    }
}
