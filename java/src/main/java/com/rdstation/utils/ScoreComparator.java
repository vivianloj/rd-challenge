package com.rdstation.utils;

import com.rdstation.adapter.IScoreAdapter;

import java.util.Comparator;

public class ScoreComparator implements Comparator<IScoreAdapter> {
    @Override
    public int compare(IScoreAdapter o1, IScoreAdapter o2) {
        return Integer.compare(o1.getScore(),o2.getScore());
    }
}
