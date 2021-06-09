package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements IClientStrategy {

    @Override
    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) { }

    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.clientStrategy = strategy;
    }

    public void communicateWithServer(){
        try(Socket serverSocket = new Socket(serverIP, serverPort)){
          //  System.out.println("connected to server - IP = " + serverIP + ", Port = " + serverPort);
            clientStrategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
