/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artificialimmunesystem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pablo A. Arias <parias@aggies.ncat.edu>
 */
public class FeatureVector {

    private List<Double> features = new ArrayList<>();
    private int numberFeatures;

    /**
     * @return the numberFeatures
     */
    public int getNumberFeatures() {
        return numberFeatures;
    }

    /**
     * @param numberFeatures the numberFeatures to set
     */
    public void setNumberFeatures(int numberFeatures) {
        this.numberFeatures = numberFeatures;
    }

    public void populateFeatures(String allFeatures) {
        String[] allFeaturesSplit = allFeatures.split(",");
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

}
