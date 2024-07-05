//This class is responsible for listening to clients who wish to connect and when they do, it will spot a new
//thread and then handles them appropriately.

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket; //creates a new socket object by listening for incoming connection request
    //from clients

    //main method to run server
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(13579); //Connects client making request to the port number '13579'
        Server server = new Server(serverSocket); //server object takes server number into its constructor
        server.startServer(); //keeps server running
    }
    public Server(ServerSocket serverSocket) { //constructor to set up server socket
        this.serverSocket = serverSocket;
    }

    //method to keep server running
    public void startServer() {
        try {
            while (!serverSocket.isClosed()) { //while the server socket is open, we wait for a client to connect.
                Socket socket = serverSocket.accept(); //A blocking method i.e. program is halted until client connects and returns a socket object when client connects which is used to communicate with the client.
                System.out.println("A new client has connected!"); //Prints line 19 when a new client connects
                ClientHandler clientHandler = new ClientHandler(socket); //Each object of this class is responsible for communicating with the client. It also implements the inner class runnable

                Thread thread = new Thread(clientHandler); //creates a new thread
                thread.start();//runs the clientHandler class
            }
        } catch (IOException e) { //handles I-O exceptions that comes accompanies working with sockets

        }
    }
    //method to shut down server if an error occurs
    public void shutDownServer(){
        try {
            if (serverSocket != null){ //Ensures server socket is not null to avoid no pointer error if closed.
                serverSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
