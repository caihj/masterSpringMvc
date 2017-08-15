package masterSpringMvc.date;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ChinaDateFormatter implements Formatter<LocalDate> {

    public static final String  CHINA_PATTERN = "yyyy/M/d";

    public static final String NORMAL_PATTERN ="d/M/yyyy";

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text,DateTimeFormatter.ofPattern(getPattern(locale)));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return  DateTimeFormatter.ofPattern(getPattern(locale)).format(object);
    }

    public static String getPattern(Locale locale){
        boolean isChina = "CN".equals(locale.getCountry());
        System.out.println("country is "+locale.getCountry());
        System.out.println("locale is "+locale.toString());
        System.out.println("is china:"+isChina);
        return isChina ? CHINA_PATTERN : NORMAL_PATTERN;
    }
}
