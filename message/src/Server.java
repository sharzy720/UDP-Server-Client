import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Creates a UDP Server for Clients to connect to using the Server's address and port
 * @version 10-7-2021
 */
public class Server {
    /**
     * Handles what the Sever does
     * @param args no commandline arguments expected
     */
    public static void main(String[] args) {
        int port = 1234; // Change to whatever port the Server will run on
        int offset = 0; // Change to what offset is needed when creating a String from the
                       // received datagramPacket's byte array
        int bufferSize = 256; // Size of the buffers
        try {
            DatagramSocket datagramSocket = new DatagramSocket(port);

            // Keeps the Server open till manually closed
            while (!datagramSocket.isClosed()){
                byte[] bufferIn = new byte[bufferSize];
                // Creating the packet to receive the Word from the Client
                DatagramPacket datagramPacketIn = new DatagramPacket(bufferIn, bufferIn.length);
                // Receiving the word from the client as a byte array
                datagramSocket.receive(datagramPacketIn);

                // Converting the Client's byte array to a String
                String clientWord = new String(datagramPacketIn.getData(), offset,
                        datagramPacketIn.getLength());

                String outputWord = clientWord + " changed by server";
                // Packing the Server's word into a byte array
                byte[] bufferOut = outputWord.getBytes();

                // Creating the out packet that will hold the Server's byte array
                DatagramPacket datagramPacketOut = new DatagramPacket(bufferOut, bufferOut.length
                        , datagramPacketIn.getAddress(), datagramPacketIn.getPort());

                // Sending the packet holding the Server's word as a byte array to the Client
                datagramSocket.send(datagramPacketOut);
            }

            datagramSocket.close();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}
