package am.chronograph.web.security;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagHandler;

/**
 * Taglib to combine the Spring-Security Project with Facelets <br />
 * <p>
 * This is the Class responsible for making the <br />
 * <code><br />
 * &lt;sec:isAuthenticated;&gt;<br />
 * The components you want to show only when the user is authenticated<br />
 * lt;/sec:isAuthenticated&gt;<br />
 * </code> work.
 *
 * @author tigranbabloyan
 */
public class IsAuthenticatedTag extends TagHandler {

	public void apply(FaceletContext faceletContext, UIComponent uiComponent)
			throws IOException, FacesException, ELException {

		if (SpringSecurityELLibrary.isAuthenticated()) {
			this.nextHandler.apply(faceletContext, uiComponent);
		}
	}

	public IsAuthenticatedTag(ComponentConfig componentConfig) {
		super(componentConfig);
	}

}
