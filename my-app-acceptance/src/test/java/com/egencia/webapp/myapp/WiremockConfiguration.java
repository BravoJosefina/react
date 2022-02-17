package com.egencia.webapp.myapp;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


/**
 * Setup wiremock before spring context loads and add port as system property
 *
 * Uses @Configuration to load class as workaround until https://github.com/cucumber/cucumber-jvm/issues/515
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WiremockConfiguration {

    public static WireMockServer wireMockServer;

    static {
        startWireMock();
    }

    private static void startWireMock() {
        wireMockServer = new WireMockServer(com.github.tomakehurst.wiremock.core.WireMockConfiguration.options()
            .usingFilesUnderClasspath("wiremock")
            .port(8000));
        wireMockServer.start();

        System.setProperty("acceptance-test.wiremock.port", String.valueOf(wireMockServer.port()));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> wireMockServer.stop()));
    }

}
