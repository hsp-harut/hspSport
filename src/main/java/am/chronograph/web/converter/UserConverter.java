package am.chronograph.web.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import am.chronograph.domain.user.User;
import am.chronograph.service.user.UserService;

/**
 * Faces converter which used to convert the {@link User} into web strings and
 * back.
 * 
 * @author tigranbabloyan
 *
 */
@FacesConverter("userConverter")
public class UserConverter implements Converter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.
	 * FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (StringUtils.isNotBlank(value)) {
			try {
				WebApplicationContext context = FacesContextUtils.getRequiredWebApplicationContext(fc);
				UserService service = context.getBean(UserService.class);
				return service.getUserById(Long.parseLong(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid user."));
			}
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.
	 * FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((User) object).getId());
		} else {
			return null;
		}
	}
}