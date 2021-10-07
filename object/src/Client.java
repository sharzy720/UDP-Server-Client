import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Creates a UDP client to send an object to a specified Server
 * @version 10-7-2021
 */
public class Client {
    /**
     * Creates the apple object for sending based off of the user's input
     * @return object for sending to a Server
     */
    public Apple createApple() {
        String appleColor, appleType;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the color of an apple: ");
        appleColor = scanner.nextLine();

        System.out.print("Enter the type of an apple: ");
        appleType = scanner.nextLine();

        Apple apple = new Apple(appleColor, appleType);
        scanner.close();
        return apple;
    }

    /**
     * Creates the Client for communicating with a Server
     * @param args (host address) (port number)
     */
    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        int byteSize = 256;

        System.out.println("Attempting to connect to: " + host + " on port: " + port);

        try {
            InetAddress hostAddress = InetAddress.getByName(host);
            Client client = new Client();
            Apple apple = client.createApple();

            ByteArrayOutputStream appleByte = new ByteArrayOutputStream(byteSize);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(appleByte);
            // Writing the Apple object to the byte array output stream
            objectOutputStream.writeObject(apple);

            // Getting the byte array of the appleByte
            byte[] bufferOut = appleByte.toByteArray();

            // Packing the byte array for sending to the Server
            DatagramPacket datagramPacketOut = new DatagramPacket(bufferOut, bufferOut.length,
                    hostAddress, port);
            DatagramSocket datagramSocket = new DatagramSocket();

            // Sending the Client's packet to the Server
            datagramSocket.send(datagramPacketOut);

            datagramSocket.close();
            objectOutputStream.close();
            appleByte.close();
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
    }
}
