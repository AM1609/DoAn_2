package com.example.doan.Model;
import com.google.firebase.firestore.DocumentId;
public class ChartsModel {
    private String currentUserId;

    private long correct, notAnswered, wrong, time;

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public long getCorrect() {
        return correct;
    }

    public void setCorrect(long correct) {
        this.correct = correct;
    }

    public long getNotAnswered() {
        return notAnswered;
    }

    public void setNotAnswered(long notAnswered) {
        this.notAnswered = notAnswered;
    }

    public long getWrong() {
        return wrong;
    }

    public void setWrong(long wrong) {
        this.wrong = wrong;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ChartsModel(String currentUserId, long correct, long time) {
        this.currentUserId = currentUserId;
        this.correct = correct;
        this.time = time;
    }
}
