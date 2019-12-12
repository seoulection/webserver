package webserver;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.socket.SocketIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;

public class HttpHandler implements Runnable {
    private Socket clientSocket;
    private Router router;

    public HttpHandler(Socket clientSocket, Router router) {
        this.clientSocket = clientSocket;
        this.router = router;
    }

    public void run() {
        try {
            PrintWriter output = SocketIO.createSocketWriter(clientSocket);
            BufferedReader input = SocketIO.createSocketReader(clientSocket);
            HttpRequest httpRequest = new HttpRequest(input);
            HttpResponse httpResponse = router.route(httpRequest);

            httpResponse.send(output);

            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}