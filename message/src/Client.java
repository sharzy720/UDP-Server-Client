import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Creates a UDP Client to communicate to a Server running on a given address and port
 * @version 10-7-2021
 */
public class Client {
    /**
     * Handles what the Client does
     * @param args (host address) (port number)
     */
    public static void main(String[] args) {
        int offset = 0; // Offset for creating a String from the Server's byte array
        int bufferSize = 256; // Size of the buffers to be used
        try {
            InetAddress host = InetAddress.getByName(args[0]); // Host address to connect to
            int port = Integer.parseInt(args[1]); // Port to use when connecting to the Host
            System.out.println("Attempting to connect to Address: " + host + " on port: " + port);

            Scanner scanner = new Scanner(System.in);
            // Getting a word to send to the server
            String word = scanner.nextLine();

            byte[] bufferOut = new byte[bufferSize];
            // Packing the entered word into a byte array to be sent to the server
            bufferOut = word.getBytes();

            DatagramSocket datagramSocket = new DatagramSocket();
            DatagramPacket datagramPacketOut = new DatagramPacket(bufferOut, bufferOut.length,
                    host, port);

            // Sending the word to the server
            datagramSocket.send(datagramPacketOut);

            byte[] bufferIn = new byte[bufferSize];

            DatagramPacket datagramPacketIn = new DatagramPacket(bufferIn, bufferIn.length);
            // Receiving a word from the server
            datagramSocket.receive(datagramPacketIn);

            // Parsing the received data from the server into a String
            String serverWord = new String(datagramPacketIn.getData(), offset,
                    datagramPacketIn.getLength());

            System.out.println("Word from server: " + serverWord);

            datagramSocket.close();
            scanner.close();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}
