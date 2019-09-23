package server;

import com.sun.net.ssl.internal.ssl.Provider;
import logging.LogSeverity;
import logging.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.Security;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Représente le serveur. C'est essentiellement un thread.
 */
public class Server extends Thread {
    private SSLSocket listener;
    private ExecutorService pool;

    private SSLServerSocketFactory sslServerSocketFactory;
    private SSLServerSocket sslServerSocket;

    public Server(int port) throws IOException {
        Security.addProvider(new Provider());
        System.setProperty("javax.net.ssl.keyStore", "myKeyStore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "Soleil1234");
        System.setProperty("javax.net.debug", "all");

        this.sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        this.sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);


        this.pool = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.listener = (SSLSocket) this.sslServerSocket.accept();
                this.pool.execute(new ClientHandler(this.listener));
            } catch (IOException e) {
                Logger.getInstance().Log(LogSeverity.ERROR, this.getClass(), "Cannot confirm the connection\n" + e.getMessage());
            }
        }
    }
}
