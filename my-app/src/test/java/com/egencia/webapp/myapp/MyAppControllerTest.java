package com.egencia.webapp.myapp;

import com.egencia.webapp.myapp.controller.MyAppController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class MyAppControllerTest {
    private MyAppController myAppController;

    @BeforeEach
    public void setup() {
        this.myAppController = new MyAppController();
    }

    @Test
    public void setsPageTitle() {
        final ModelMap model = new ModelMap();
        myAppController.index(model);
        assertTrue(model.containsAttribute("pageTitle"));
        assertEquals("My app", model.get("pageTitle"));
    }

    @Test
    public void returnsIndexRoute() {
        assertEquals(Routes.MY_APP + "/index", myAppController.index(new ModelMap()));
    }
}
