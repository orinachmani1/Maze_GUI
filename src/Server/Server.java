package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server implements IServerStrategy {

    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
   //private final Logger LOG = LogManager.getLogger(); //
    private ExecutorService threadPool;


    public Server(int port, int listeningIntervalMS, IServerStrategy strategy){
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.stop = false;
        Configurations.getInstance();
        this.threadPool = Executors.newFixedThreadPool(Configurations.numberOfThreads());
       // System.out.println(Configurations.numberOfThreads());
}

    public void runServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    //This thread will handle the new Client
//                    threadPool.execute(() -> {
//                        handleClient(clientSocket);
//                    });
                    Thread t = new Thread(() -> {
                        handleClient(clientSocket);
                    });
                    threadPool.execute(t);

                } catch (SocketTimeoutException e){}
            }
            threadPool.shutdown();
            //serverSocket.close();
        } catch (IOException e) {
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e){ }
    }

    public void stop(){
        stop = true;
    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }
    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) { }
}
