package webserver;

import webserver.router.RouteInitializer;
import webserver.router.Router;
import webserver.socket.SocketCreator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int port;
    private String directory;
    private boolean running;

    public Server(int port, String directory) throws IOException {
        this.port = port;
        this.directory = directory;
        this.serverSocket = SocketCreator.createServerSocket(port);
    }

    @Override
    public void run() {
        try {
            Router router = new Router();
            createRoutes(router);
            while (running) {
                clientSocket = SocketCreator.createClientSocket(serverSocket);
                HttpHandler httpHandler = new HttpHandler(clientSocket, router);
                new Thread(httpHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        this.running = true;
        System.out.println("Listening for connection on port " + this.port + "...");
        new Thread(this).start();
    }

    public void stop() throws IOException {
        this.running = false;
    }

    public boolean isRunning() {
        return this.running;
    }

    private void createRoutes(Router router) throws IOException {
        RouteInitializer.createServerRoutes(router);
        if (this.directory.equals(null)) {
            RouteInitializer.createTodoListRoutes(router);
        } else {
            RouteInitializer.createTodoListRoutes(router, this.directory);
        }
    }
}