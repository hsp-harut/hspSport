package am.chronograph.web.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import am.chronograph.web.util.Constants;

/**
 * Faces converter for support of LocalDate
 * @author Tiko
 */
@FacesConverter(value="localDateTimeConverter")
public class LocalDateTimeConverter implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
          return LocalDate.parse(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
    	if(value == null){
    		return "";
    	}

        LocalDateTime dateValue = (LocalDateTime) value;
        
        return dateValue.format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_TIME_PATTERN));
    }
    
}
