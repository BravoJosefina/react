package com.egencia.webapp.myapp.controller;

import com.egencia.webapp.myapp.Routes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 	MyApp's Controller
 *  NOTE: By convention method names match the name of their View, e.g. HotelController.index() should return views/hotel/index
 */
@Controller
public class MyAppController {
    /**
     * Index
     * @param model {@link ModelMap} that contains the common model
     * @return {@link String} view in `assets/views` to render
     */
    @GetMapping("")
    public String index(ModelMap model) {
        // Put data on the model
        model.addAttribute("pageTitle", "My app");
        return Routes.MY_APP + "/index";
    }
}

