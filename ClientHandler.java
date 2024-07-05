import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {//implements the runnable interface

    //static array list of every instance of the class(ClientHandler)
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();//keeps track of all clients and loops through the array of clients whenever a message is sent.
    private Socket socket; //establishes a connection between the client and server
    BufferedReader bufferedReader; //reads messages sent from clients
    BufferedWriter bufferedWriter; //sends messages to clients which is sent from another client. The message will then be broadcasted using the array list in line 9
    private String clientUserName;

    //Constructors
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUserName = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER : " + clientUserName + " has entered the chat! ");

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    //runs a separate thread which listens for messages different from the thread running program
    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();//The program holds until we receive a message from the client
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;//breaks the while loop once the client disconnects
            }
        }
        //method to broadcast message to every client connected to the server
    }
    public void broadcastMessage(String messageToSend){
        for (ClientHandler clientHandler : clientHandlers){
            try {
                if (!clientHandler.clientUserName.equals(clientUserName)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();//flushes the buffer if the message sent doesn't fill it
                }
            }catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }
    //method to remove client handler
    public void removeClientHandler(){
        clientHandlers.remove(this); //removes current client handler
        broadcastMessage("SERVER: " + clientUserName + " has left the chat! ");
    }
    //method to close down connection once client handler is removed
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



