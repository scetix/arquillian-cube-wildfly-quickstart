package com.quickstart;

import org.arquillian.cube.DockerUrl;
import org.arquillian.cube.HostPort;
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
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
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

    @ArquillianResource
    @DockerUrl(containerName = "arquillian-cube-wildfly-test", exposedPort = 8080)
    private URL url;

    @HostPort(containerName = "arquillian-cube-wildfly-test", value = 8080)
    private int port;

    @Test
    @RunAsClient
    public void test() throws Exception {
        URI uri = UriBuilder.fromUri(url.toURI())
                .port(port)
                .path("resources")
                .path("hello")
                .build();

        String hello = ClientBuilder.newClient()
                .target(uri)
                .request(MediaType.TEXT_PLAIN)
                .get()
                .readEntity(String.class);

        assertEquals("Hello", hello);
    }
}
