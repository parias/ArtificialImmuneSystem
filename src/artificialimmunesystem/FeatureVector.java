/* 
 * File:   FeatureVector.java
 * Author: @Pablo A. Arias
 * Email: parias@aggies.ncat.edu
 * Course: Comp 740 - Advanced Artificial Intelligence - Dr. G.V. Dozier
 * Objective: Implementation of Aritificial Immune System 
 *      through Negative Selection
 * Objective: FeatureVector Class for creation of Feature Vectors
 *      Feature Vectors were populated from file input, gathered from 
 *      FlowDroid Android Mobile Analysis tool. Populated Feature Vectors
 *      based on Sink/Flow connections to determine malicious vs. benign
 */
package artificialimmunesystem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pablo A. Arias <parias@aggies.ncat.edu>
 */
public class FeatureVector implements Cloneable {

    private List<Double> features = new ArrayList<>();
    private int numberFeatures;

    /**
     * @return the numberFeatures
     */
    public int getNumberFeatures() {
        return features.size();
    }

    /**
     * @param numberFeatures the numberFeatures to set
     */
    public void setNumberFeatures(int numberFeatures) {
        this.numberFeatures = numberFeatures;
    }

    /*
     * Polulates feature vector with Doubles from input file
     */
    public void populateFeatures(String allFeatures) {
        String[] allFeaturesSplit = allFeatures.split(",");

        //allfeatures[0] = appName, starts at one
        for (int i = 1; i < allFeaturesSplit.length - 1; i++) {
            features.add(Double.parseDouble(allFeaturesSplit[i]));
        }
    }

    /**
     * @return the features
     */
    public List<Double> getFeatures() {
        return features;
    }

    /**
     * @param features the features to set
     */
    public void setFeatures(List<Double> features) {
        this.features = features;
    }

    /*
     * Used for cloning the FeatureVectors Array. 
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
