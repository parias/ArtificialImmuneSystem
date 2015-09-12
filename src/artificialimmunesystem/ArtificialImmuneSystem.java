/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artificialimmunesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo A. Arias <parias@aggies.ncat.edu>
 */
public class ArtificialImmuneSystem {

    private final List<FeatureVector> self = new ArrayList<>();
    private final List<FeatureVector> nonSelf = new ArrayList<>();
    private List<Detector> mature = new ArrayList<>();
    private List<FeatureVector> matureApp = new ArrayList<>();
    private final List<Detector> detectors = new ArrayList<>();
    private static final int BENIGN = 30;
    private static final int MALICIOUS = 30;
    private int numberFeatures;
    private static int numDetectors;
    private int r;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int randomMinRangeValue = 0, randomMaxRangeValue = 150;
        ArtificialImmuneSystem ais = new ArtificialImmuneSystem();

        //Reading from File, populate self/nonself
        File file = new File("Flowdroid_FVs.txt");
        try {
            try (FileReader fileReader = new FileReader(file)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder stringBuffer = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    ais.populateFeatures(line);
                    stringBuffer.append(line);
                    stringBuffer.append("\n");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ArtificialImmuneSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

        ais.setNumberFeatures();

        ais.queryDetectors();

        ais.populateDetectors(getNumDetectors(), randomMinRangeValue, randomMaxRangeValue);

        ais.queryR();

        ais.matchRAny(ais.getR());

        System.out.println(ais.mature.size());
        System.out.println(ais.matureApp.size());

        System.out.println("Progress so Far");

    }

    /*
     * Populates self/non-self Feature Vectors with Features
     * Features input from file
     */
    public void populateFeatures(String allFeatures) {
        FeatureVector vector = new FeatureVector();
        List<Double> features = new ArrayList<>();
        String[] allFeaturesSplit = allFeatures.split(",");

        for (int i = 1; i < allFeaturesSplit.length - 1; i++) {
            features.add(Double.parseDouble(allFeaturesSplit[i]));
        }

        vector.setFeatures(features);
        if (self.size() < BENIGN) {
            self.add(vector);
        } else {
            nonSelf.add(vector);
        }

    }

    /*
     * Populates Detector list with random ranges
     * Number of ranges equals number of features in Feature Vector
     */
    public void populateDetectors(int numDetectors, int randomMinRangeValue, int randomMaxRangeValue) {
        Detector detector;
        for (int i = 0; i < numDetectors; i++) {
            detector = new Detector(524, randomMinRangeValue, randomMaxRangeValue);
            detector.populateRangess(randomMinRangeValue, randomMaxRangeValue);
            detectors.add(detector);
        }
    }

    /**
     * @return the numberFeatures
     */
    public int getNumberFeatures() {
        return numberFeatures;
    }

    /**
     * numberFeatures to set
     */
    public void setNumberFeatures() {
        this.numberFeatures = self.get(0).getFeatures().size();
    }

    public boolean inRange(int feature, String rangeString) {
        String rangeArray[] = rangeString.split(" ");
        int[] range = {Integer.parseInt(rangeArray[0]), Integer.parseInt(rangeArray[1])};

        return (feature > range[0]) && (feature < range[1]);
    }

    /**
     * @return the numDetectors
     */
    public static int getNumDetectors() {
        return numDetectors;
    }

    /**
     * @param aNumDetectors the numDetectors to set
     */
    public static void setNumDetectors(int aNumDetectors) {
        numDetectors = aNumDetectors;
    }

    /*
     * Queries user for number of detectors 
     */
    public void queryDetectors() {
        String numDetectorsString = JOptionPane.showInputDialog(null,
                "Number of Detectors?");
        setNumDetectors(Integer.parseInt(numDetectorsString));
    }

    /*
     * Queries user for number of detectors 
     */
    public void queryR() {
        String rAny = JOptionPane.showInputDialog(null,
                "Any R number?");
        setR(Integer.parseInt(rAny));
    }


    /*
     * 
     */
    public void matchSelf(int r) {

        for (int i = 0; i < self.size(); i++) {
            FeatureVector vector = self.get(i);
            for (int k = 0; k < detectors.size(); k++) {
                int fired = 0;
                Detector detector = detectors.get(k);
                for (int j = 0; j < numberFeatures; j++) {
                    if (detector.inRange(detector.getRanges().get(j), vector.getFeatures().get(j))) {
                        fired++;
                    }
                }
                if (fired >= r) {
                    detectors.remove(k);
                    k--;
                }
            }
        }
    }

    /*
     * 
     */
    public void matchNonSelf(int r) {

        int i;
        for (i = 0; i < nonSelf.size(); i++) {
            boolean detected = false;
            FeatureVector vector = nonSelf.get(i);
            for (int k = 0; k < detectors.size(); k++) {
                int fired = 0;
                Detector detector = detectors.get(k);
                for (int j = 0; j < numberFeatures; j++) {
                    if (detector.inRange(detector.getRanges().get(j), vector.getFeatures().get(j))) {
                        fired++;
                    }
                }
                if (fired >= r) {
                    mature.add(detector);
                    detected = true;
                }
            }
            if (detected == true) {
                matureApp.add(vector);
            }
        }
    }

    /*
     * 
     */
    public void matchRAny(int r) {
        matchSelf(r);
        matchNonSelf(r);
    }

    /*
     *
     */
    public void matchRContinous() {

    }

    /**
     * @return the r
     */
    public int getR() {
        return r;
    }

    /**
     * @param r the r to set
     */
    public void setR(int r) {
        this.r = r;
    }
}
