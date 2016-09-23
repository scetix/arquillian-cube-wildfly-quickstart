package com.quickstart;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class TestHello {
    @Deployment(testable = false)
    public static WebArchive create() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(Hello.class)
                .addClass(JAXRSConfiguration.class);
    }

    @Test
    @RunAsClient
    public void test(@ArquillianResource URL url) throws Exception {
        String hello = ClientBuilder.newClient()
                .target("http://localhost:58080" + url.getFile() + "resources/hello")
                .request(MediaType.TEXT_PLAIN)
                .get()
                .readEntity(String.class);

        assertEquals("Hello", hello);
    }
}
