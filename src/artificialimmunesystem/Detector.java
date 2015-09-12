/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artificialimmunesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Pablo A. Arias <parias@aggies.ncat.edu>
 */
public class Detector {

    private List<String> ranges = new ArrayList<>();
    private int numberRanges;
    private int minRangesValue;
    private int maxRangesValue;

    public Detector(int numberRangess, int minRangesValue, int maxRangesValue) {
        this.numberRanges = numberRangess;
        this.minRangesValue = minRangesValue;
        this.maxRangesValue = maxRangesValue;

    }

    /*
     * Populates Detector with ranges
     */
    public void populateRangess(int minRangesValue, int maxRangesValue) {
        for (int i = 0; i < numberRanges; i++) {
            getRanges().add(randomRange(minRangesValue, maxRangesValue));
        }
    }

    /**
     * @return the numberRangess
     */
    public int getNumberRangess() {
        return numberRanges;
    }

    /**
     * @param numberRangess the numberRangess to set
     */
    public void setNumberRangess(int numberRangess) {
        this.numberRanges = numberRangess;
    }

    /**
     * @return the minRangesValue
     */
    public int getMinRangesValue() {
        return minRangesValue;
    }

    /**
     * @param minRangesValue the minRangesValue to set
     */
    public void setMinRangesValue(int minRangesValue) {
        this.minRangesValue = minRangesValue;
    }

    /**
     * @return the maxRangesValue
     */
    public int getMaxRangesValue() {
        return maxRangesValue;
    }

    /**
     * @param maxRangesValue the maxRangesValue to set
     */
    public void setMaxRangesValue(int maxRangesValue) {
        this.maxRangesValue = maxRangesValue;
    }

    /*
     * @return string with 2 random integers, creating a range
     */
    public String randomRange(int minValue, int maxValue) {
        Random rand = new Random();
        int small = minValue + (int) (Math.random() * ((maxValue - minValue) + 1));
        int large = minValue + (int) (Math.random() * ((maxValue - minValue) + 1));

        if (small < large) {
            return small + " " + large;
        } else {
            return large + " " + small;
        }
    }
    
    public boolean inRange(String range, double feature){ 
        String[] rangeSplit = range.split(" ");
        double min = Double.parseDouble(rangeSplit[0]);
        double max = Double.parseDouble(rangeSplit[1]);
        return (feature > min) && (feature < max);
        
    }

    /**
     * @return the ranges
     */
    public List<String> getRanges() {
        return ranges;
    }

    /**
     * @param ranges the ranges to set
     */
    public void setRanges(List<String> ranges) {
        this.ranges = ranges;
    }

}
