import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Creates a UDP server to receive an object from a Client and use the objects methods
 * @version 10-7-2021
 */
public class Server {
    /**
     * Creates the Server for communicating with a Client
     * @param args no commandline arguments expected
     */
    public static void main(String[] args) {
        int port = 1234; // Port to start the Server on
        int bufferSize = 256; // Size of the buffers for receiving data from the Client

        try {
            DatagramSocket datagramSocket = new DatagramSocket(port);

            // Keeps Server running till manually closed
            while (!datagramSocket.isClosed()) {
                byte[] bufferIn = new byte[bufferSize];
                DatagramPacket datagramPacketIn = new DatagramPacket(bufferIn, bufferSize);
                datagramSocket.receive(datagramPacketIn);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bufferIn);

                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

                // Receiving the Apple object from the Client, needs to be type cast
                Apple apple = (Apple) objectInputStream.readObject();

                // Using the apple's toString method for printing
                System.out.println(apple.toString());
            }

            datagramSocket.close();
        } catch (IOException ioException) {
            System.out.println(ioException);
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println(classNotFoundException);
        }
    }
}
