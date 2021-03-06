package com.peerapplication.handler;

import com.peerapplication.model.Answer;
import com.peerapplication.notifcation.NotificationHandler;
import com.peerapplication.util.UIUpdateHandler;
import message.AnswerMessage;
import message.Message;
import messenger.Handler;
import messenger.Peer;
import messenger.PeerHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AnswerHandler extends Handler {

    private static AnswerHandler answerHandler;
    private static ReadWriteLock answerHandleLock = new ReentrantReadWriteLock();

    private AnswerHandler() {

    }

    public static AnswerHandler getAnswerHandler() {                                                                    //singleton pattern for handler
        if (answerHandler == null) {
            synchronized (AnswerHandler.class) {
                answerHandler = new AnswerHandler();
            }
        }
        return answerHandler;
    }

    public static void postAnswer(Answer answer) {                                                                      // post an answer
        System.out.println("Posting Answer");
        AnswerMessage answerMessage = new AnswerMessage();
        answerMessage.setAnswer(answer);
        PeerHandler.knownPeersReadLock();
        PeerHandler.getSenderController().sendToAll(answerMessage, new ArrayList<>(PeerHandler.getKnownPeers().values()));
        PeerHandler.knownPeersReadUnlock();
        System.out.println("Answer Posted");
    }

    private static void handleAnswer(AnswerMessage answerMessage) {                                                     // handle received answer
        System.out.println("Handling answer");
        answerHandleLock.writeLock().lock();
        Answer answer = new Answer(answerMessage.getAnswer().getAnswerID());
        if (answer.getAnswerID() == null) {                                                                             // check answer is in database
            System.out.println("New Answer");
            answerMessage.getAnswer().saveAnswer();
            NotificationHandler.getNotificationHandler().handleAnswer(answerMessage.getAnswer());
            UIUpdateHandler.informAnswerUpdater(answerMessage);
            ArrayList<Peer> receivers = new ArrayList<>();
            PeerHandler.knownPeersReadLock();
            for (Map.Entry peer : PeerHandler.getKnownPeers().entrySet()) {                                             // create senders list
                if (peer.getKey().equals(Integer.valueOf(answerMessage.getAnswer().getPostedUserID()))
                        || peer.getKey().equals(Integer.valueOf(answerMessage.getSenderID()))) {
                    continue;
                } else {
                    receivers.add((Peer) peer.getValue());
                }
            }
            PeerHandler.knownPeersReadUnlock();
            PeerHandler.getSenderController().sendToAll(answerMessage, receivers);
            System.out.println("Answer detail Sent to known peers");
        }
        answerHandleLock.writeLock().unlock();
    }

    @Override
    public void handle(Message message) {
        if (message instanceof AnswerMessage) {
            handleAnswer((AnswerMessage) message);
        }
    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {

    }
}
