package webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.net.Socket;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HelloWorldPageTest {

    @Mock
    Socket clientSocket;

    @Test
    public void testServerOutputsHelloWorldMessage() throws IOException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        when(clientSocket.getOutputStream()).thenReturn(outContent);
        HelloWorldPage helloWorldPage = new HelloWorldPage(clientSocket);

        helloWorldPage.run();

        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<h1>Hello World!</h1>\n",
                outContent.toString());
    }
}