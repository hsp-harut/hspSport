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

import am.chronograph.domain.user.Permission;
import am.chronograph.service.user.PermissionService;

/**
 * Faces converter which used to convert the {@link Permission} into web strings
 * and back.
 * 
 * @author tigranbabloyan
 *
 */
@FacesConverter("permissionConverter")
public class PermissionConverter implements Converter {

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
				PermissionService service = context.getBean(PermissionService.class);
				return service.getById(Long.parseLong(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid permission."));
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
			return String.valueOf(((Permission) object).getId());
		} else {
			return null;
		}
	}
}