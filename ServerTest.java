import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServerTest {

    private ServerSocket serverSocket;
    private Server server;

    @BeforeEach
    public void setUp() throws IOException {
        // Mock the ServerSocket
        serverSocket = mock(ServerSocket.class);
        server = new Server(serverSocket);
    }

    @Test
    public void testStartServer() throws IOException {
        // Mock the Socket
        Socket socket = mock(Socket.class);

        // Simulate a client connection
        when(serverSocket.accept()).thenReturn(socket);

        // Run the server in a separate thread to avoid blocking
        Thread serverThread = new Thread(() -> {
            try {
                server.startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        // Give some time for the server to run and accept the connection
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shut down the server to stop the thread
        server.shutDownServer();

        // Verify that the socket accept method was called
        verify(serverSocket, atLeastOnce()).accept();
    }

    @Test
    public void testShutDownServer() throws IOException {
        server.shutDownServer();

        // Verify that the server socket is closed
        verify(serverSocket, times(1)).close();
    }

    }

