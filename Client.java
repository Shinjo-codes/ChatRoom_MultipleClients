import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    String username;

    //main method
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the chat");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 13579);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }
    //constructors
    public Client (Socket socket, String username){
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }
    //method to send message from client to clientHandler
    public void sendMessage(){
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in); //gets input from a console
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }
    //method for listening for messages from the server (i.e. messages broadcast from other users)
    //line 42 to 58 waits for messages broadcast from line 43 to 55 of client handler
    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromChat;
                while (socket.isConnected()){
                    try {
                        msgFromChat = bufferedReader.readLine();
                        System.out.println(msgFromChat);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedWriter, bufferedReader);
                    }
                }
            }
        }).start();
    }
    //method to close everything
    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader){
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
