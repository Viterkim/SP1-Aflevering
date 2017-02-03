import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A server which simply just echoes whatever it receives
 */
public class EchoServer {

    private final String host;
    private final int port;

    public EchoServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Starts running the server.
     *
     * @throws IOException If network or I/O or something goes wrong.
     */
    public void startServer() throws IOException {
        // Create a new unbound socket
        ServerSocket socket = new ServerSocket();
        // Bind to a port number
        socket.bind(new InetSocketAddress(host, port));

        System.out.println("Server listening on port " + port);

        // Wait for a connection
        Socket connection;
        while ((connection = socket.accept()) != null) {
            // Handle the connection in the #handleConnection method below
            handleConnection(connection);
            // Now the connection has been handled and we've sent our reply
            // -- So now the connection can be closed so we can open more
            //    sockets in the future
            connection.close();
        }
    }

    /**
     * Handles a connection from a client by simply echoing back the same thing the client sent.
     *
     * @param connection The Socket connection which is connected to the client.
     * @throws IOException If network or I/O or something goes wrong.
     */
    private void handleConnection(Socket connection) throws IOException {
        OutputStream output = connection.getOutputStream();
        InputStream input = connection.getInputStream();

        // Read whatever comes in
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = reader.readLine();

        // Print the same line we read to the client
        PrintStream writer = new PrintStream(output);
         if (!line.contains("#")) 
         {
            writer.println("Error: Missing # command!");
            connection.close();
        }
 
        String[] split = line.split("#");
        if (split[0].equalsIgnoreCase("UPPER")) 
        {
            writer.print(split[1].toUpperCase());
            connection.close();
        } 
        else if (split[0].equalsIgnoreCase("LOWER")) 
        {
            writer.print(split[1].toLowerCase());
            connection.close();
        } 
        else if (split[0].equalsIgnoreCase("REVERSE")) 
        {
            String reversedLine = "";
            for (int i = line.length(); i > 0; i--) 
            {
                reversedLine += split[1].substring(i);
            }
            connection.close();
        } 
        else if (split[0].equalsIgnoreCase("TRANSLATE")) 
        {
            writer.print("Tro på at vi gider at lave en translater ;)");
            connection.close();
        }
    }

    public static void main(String[] args) throws IOException 
    {
        EchoServer server = new EchoServer("localhost", 8080);

        // This method will block, forever!
        server.startServer();
    }


}
