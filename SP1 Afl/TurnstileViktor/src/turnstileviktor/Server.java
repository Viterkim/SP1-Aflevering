
package turnstileviktor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;



public class Server 
{
    private final String host;
    private final int port;
    private AtomicInteger totalCount;
    private ArrayList<Turnstile> turnstiles;
    

    public Server(String host, int port) 
    {
        this.host = host;
        this.port = port;
        totalCount = new AtomicInteger(0);
        turnstiles = new ArrayList<>();
    }
    
    public void startServer() throws IOException 
    {
        // Create a new unbound socket
        ServerSocket socket = new ServerSocket();
        // Bind to a port number
        socket.bind(new InetSocketAddress(host, port));

        System.out.println("TurnstileServer listening on port " + port);

        // Wait for a connection
        Socket connection;
        while ((connection = socket.accept()) != null) 
        {
            // Handle the connection in the #handleConnection method below
            handleConnection(connection);
            // Now the connection has been handled and we've sent our reply
            // -- So now the connection can be closed so we can open more
            //    sockets in the future
            connection.close();
        }
    }
    
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
        if (split[0].equalsIgnoreCase("TURN")) 
        {
            if(split[1].equalsIgnoreCase("CONNECT"))
            {
//                turnstiles.add();
            }
            else if(split[1].equalsIgnoreCase("ADD1"))
            {
                int c = totalCount.incrementAndGet();
                System.out.println("Total is: " + c);
            }
            connection.close();
        } 
        else if (split[0].equalsIgnoreCase("MONITOR")) 
        {
            if(split[1].equalsIgnoreCase("GET"))
            {
                writer.print("Total Count: " + totalCount.get());
            }
            connection.close();
        } 
    }
    
    public synchronized int getAmountTurnstiles()
    {
        return turnstiles.size();
    }
    
    public static void main(String[] args) throws IOException 
    {
        Server server = new Server("localhost", 8080);

        server.startServer();
    }
    
}
