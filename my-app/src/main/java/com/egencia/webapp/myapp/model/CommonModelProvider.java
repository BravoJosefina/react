package com.egencia.webapp.myapp.model;

import com.egencia.library.uitoolkit.model.UitkCommonModelProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;
import java.util.HashMap;

/**
 * This class makes sure that the Model is always populated with some common data.
 *
 * @author aallegret
 */
@ControllerAdvice(basePackages = {"com.egencia"})
public class CommonModelProvider extends UitkCommonModelProvider<AppEG> {
    private static final Logger logger = LoggerFactory.getLogger(CommonModelProvider.class);

    public CommonModelProvider() {
        super(AppEG.class);
    }

    @ModelAttribute("team")
    public Map<String, String> getTeam() {
        final Map<String, String> team = new HashMap<>();
        team.put("name", "GUIE");
        team.put("email", "guie@expedia.com");
        return team;
    }

    // Put anything in the Model
    @ModelAttribute("user")
    public Map<String, String> getSomething() {
        final Map<String, String> user = new HashMap<>();
        user.put("first_name", "Testy");
        user.put("last_name", "McTestington");
        return user;
    }

    // Create your app's namespace and add it to 'EG'
    @Override
    public void extendEG(AppEG eg) {
        eg.setNamespace(new Namespace());
    }

    // Your applications pagename (reported to Omniture and GlassBox)
    @Override
    public String getPageName(String requestUri) {
        return "page.MyApp.Index";
    }
}