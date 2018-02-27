package com.peerapplication.messenger;

import com.peerapplication.util.Main;
import message.Message;

public class SenderController {
    public void send(Message message, Peer peer) {
        message.setSenderID(Main.getSystemUserID());
        message.setSenderAddress(PeerHandler.getUserAddress());
        message.setSenderPort(PeerHandler.getUserPort());
        message.setReceiverAddress(peer.getPeerAddress());
        message.setReceiverPort(peer.getPeerPort());
        Sender sender = new Sender(message, peer);
        Thread senderThread = new Thread(sender);
        senderThread.start();
    }

}
