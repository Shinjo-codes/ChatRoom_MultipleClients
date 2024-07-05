import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientTest {

    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private Client client;

    @BeforeEach
    public void setUp() throws IOException {
        // Mock the Socket, BufferedReader, and BufferedWriter
        socket = mock(Socket.class);
        bufferedReader = mock(BufferedReader.class);
        bufferedWriter = mock(BufferedWriter.class);

        // Simulate input and output streams
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("Test message\n".getBytes()));
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        // Initialize Client with the mocked socket
        client = new Client(socket, "TestUser");

        // Replace actual bufferedReader and bufferedWriter with mocks
        client.bufferedReader = bufferedReader;
        client.bufferedWriter = bufferedWriter;
    }

    @Test
    public void testClientInitialization() throws IOException {
        assertNotNull(client);
        assertEquals("TestUser", client.username);
    }

//    @Test
//    public void testSendMessage() throws IOException {
//        // Mock the Scanner to simulate user input
//        Scanner scanner = mock(Scanner.class);
//        when(scanner.nextLine()).thenReturn("Hello");
//
//        // Send a test message
//        client.sendMessage();
//
//        // Verify that the message is sent
//        verify(bufferedWriter, times(1)).write("TestUser");
//        verify(bufferedWriter, times(1)).newLine();
//        verify(bufferedWriter, times(1)).flush();
//        verify(bufferedWriter, times(1)).write("TestUser: Hello");
//        verify(bufferedWriter, times(2)).newLine();
//        verify(bufferedWriter, times(2)).flush();
//    }

//    @Test
//    public void testListenForMessage() throws IOException {
//        // Simulate a message from the server
//        when(bufferedReader.readLine()).thenReturn("Server message");
//
//        // Start listening for messages in a separate thread
//        Thread listenThread = new Thread(() -> client.listenForMessage());
//        listenThread.start();
//
//        // Give some time for the listener to run
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Verify that the message is read and printed
//        verify(bufferedReader, atLeastOnce()).readLine();
//    }

    @Test
    public void testCloseEverything() throws IOException {
        client.closeEverything(socket, bufferedWriter, bufferedReader);

        // Verify that the resources are closed
        verify(bufferedReader, times(1)).close();
        verify(bufferedWriter, times(1)).close();
        verify(socket, times(1)).close();
    }
}
