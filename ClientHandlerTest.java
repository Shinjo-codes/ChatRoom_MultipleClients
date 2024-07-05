import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientHandlerTest {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ClientHandler clientHandler;

    @BeforeEach
    public void setUp() throws IOException {
        // Mock the Socket, BufferedReader, and BufferedWriter
        socket = mock(Socket.class);
        bufferedReader = mock(BufferedReader.class);
        bufferedWriter = mock(BufferedWriter.class);

        // Simulate input and output streams
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("TestUser\n".getBytes()));
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        // Initialize ClientHandler with the mocked socket
        clientHandler = new ClientHandler(socket);

        // Replace actual bufferedReader and bufferedWriter with mocks
        clientHandler.bufferedReader = bufferedReader;
        clientHandler.bufferedWriter = bufferedWriter;
    }

    @AfterEach
    public void tearDown() {
        // Clean up after each test
        ClientHandler.clientHandlers.clear();
    }

    @Test
    public void testClientHandlerInitialization() throws IOException {
        assertNotNull(clientHandler);
        assertTrue(ClientHandler.clientHandlers.contains(clientHandler));
    }

//    @Test
//    public void testBroadcastMessage() throws IOException {
//        String testMessage = "Test message";
//
//        // Mock another ClientHandler to verify message broadcasting
//        Socket otherSocket = mock(Socket.class);
//        ClientHandler otherClientHandler = new ClientHandler(otherSocket);
//        otherClientHandler.bufferedWriter = mock(BufferedWriter.class);
//        ClientHandler.clientHandlers.add(otherClientHandler);

//        // Send a test message
//        clientHandler.broadcastMessage(testMessage);
//
//        // Verify that the message is sent to the other client
//        verify(otherClientHandler.bufferedWriter, times(1)).write(testMessage);
//        verify(otherClientHandler.bufferedWriter, times(1)).newLine();
//        verify(otherClientHandler.bufferedWriter, times(1)).flush();
//    }

    @Test
    public void testRemoveClientHandler() {
        clientHandler.removeClientHandler();
        assertFalse(ClientHandler.clientHandlers.contains(clientHandler));
    }

    @Test
    public void testCloseEverything() throws IOException {
        clientHandler.closeEverything(socket, bufferedReader, bufferedWriter);
        verify(bufferedReader, times(1)).close();
        verify(bufferedWriter, times(1)).close();
        verify(socket, times(1)).close();
    }
}
