package messenger;

import com.peerapplication.handler.BSHandler;
import message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender implements Runnable{
    private Message message;
    private Socket senderSocket;
    private Peer peer;

    public Sender(Message message, Peer peer){
        this.message = message;
        this.peer = peer;
    }



    @Override
    public void run() {
        synchronized (peer) {
            try {
                senderSocket = new Socket(message.getReceiverAddress(), message.getReceiverPort());
                ObjectOutputStream os = new ObjectOutputStream(senderSocket.getOutputStream());
                if (message instanceof BSMessage) {
                    os.writeObject(message);
                    ObjectInputStream is = new ObjectInputStream(senderSocket.getInputStream());
                    RequestStatusMessage response = (RequestStatusMessage) is.readObject();
                    is.close();
                    if (!response.getTitle().equals("LogoutSuccess")) {
                        BSHandler.handleRequest(response);
                    }
                } else if (message instanceof ForumUpdateMessage) {

                }
                os.close();
                senderSocket.close();
            } catch (IOException e) {
                if (peer.getUserID()==000000){
                    System.out.println("No Network Connection!");
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
}