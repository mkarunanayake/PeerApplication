package com.peerapplication.model;

import com.peerapplication.repository.AnswerRepository;

import java.io.Serializable;
import java.util.ArrayList;

public class Answer implements Serializable {

    private String answerID;
    private String threadID;
    private String description;
    private ArrayList<Vote> votes;
    private long timestamp;
    private int postedUserID;

    public Answer() {
    }


    public Answer(String answerID) {
        getAnswer(answerID);
    }

    public Answer(String answerID, String threadID, String description, ArrayList<Vote> votes, long timestamp) {
        this.answerID = answerID;
        this.threadID = threadID;
        this.description = description;
        this.votes = votes;
        this.timestamp = timestamp;
    }

    public static ArrayList<Answer> getAnswers(String threadID) {                                                       //get answers of a given thread
        AnswerRepository answerRepository = AnswerRepository.getAnswerRepository();
        return answerRepository.getAnswers(threadID);
    }

    public static ArrayList<Answer> getLatestAnswers(long timestamp) {                                                  // get answers after a given date
        AnswerRepository answerRepository = AnswerRepository.getAnswerRepository();
        return answerRepository.getLatestAnswers(timestamp);
    }

    public static void saveAnswers(ArrayList<Answer> answers) {                                                         //save answer
        if (!answers.isEmpty()) {
            for (Answer answer : answers) {
                answer.saveAnswer();
            }
        }
    }

    public String getAnswerID() {
        return answerID;
    }

    public void setAnswerID(String answerID) {
        this.answerID = answerID;
    }

    public String getThreadID() {
        return threadID;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Vote> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Vote> votes) {
        this.votes = votes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getPostedUserID() {
        return postedUserID;
    }

    public void setPostedUserID(int postedUserID) {
        this.postedUserID = postedUserID;
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public void getAnswer(String answerID) {                                                                            //get answer of a given ID
        AnswerRepository answerRepo = AnswerRepository.getAnswerRepository();
        answerRepo.getAnswer(answerID, this);
        if (answerID.equals(this.answerID)) {
            this.setVotes(Vote.getVotes(this.answerID));
        }
    }

    public void saveAnswer() {                                                                                          //save answer
        if (answerID != null) {
            AnswerRepository answerRepo = AnswerRepository.getAnswerRepository();
            answerRepo.saveAnswer(this);
        }
    }
}
