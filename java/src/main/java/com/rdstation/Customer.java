package com.rdstation;

import com.rdstation.adapter.IScoreAdapter;

public class Customer implements IScoreAdapter {

    private final int id;
    private final int score;

    public Customer(int id, int score) {
        this.id = id;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    @Override
    public int getScore() {
        return score;
    }
}
