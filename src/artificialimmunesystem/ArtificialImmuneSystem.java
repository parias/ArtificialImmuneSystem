/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artificialimmunesystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    private final List<FeatureVector> all = new ArrayList<>();
    private final List<FeatureVector> self = new ArrayList<>();
    private final List<FeatureVector> nonSelf = new ArrayList<>();
    private final List<FeatureVector> maskedSelf = new ArrayList<>();
    private final List<FeatureVector> maskedNonSelf = new ArrayList<>();
    private List<Detector> mature = new ArrayList<>();
    private List<Detector> matureContiguous = new ArrayList<>();
    private List<Detector> matureMasked = new ArrayList<>();
    private List<FeatureVector> matureApp = new ArrayList<>();
    private List<FeatureVector> matureAppContiguous = new ArrayList<>();
    private List<FeatureVector> matureAppMasked = new ArrayList<>();
    private List<Detector> detectors = new ArrayList<>();
    private List<Detector> detectorsContiguous = new ArrayList<>();
    private List<Detector> detectorsMasked = new ArrayList<>();
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
        //ais.queryDetectors();

        for (int i = 0; i < 524; i++) {
            double immatureDetectors = 0.0, matureDetector = 0.0, nonSelfApp = 0.0;
            double results = 0;
            for (int j = 0; j <30; j++) {
                ais.detectors.clear();
                ais.matureApp.clear();
                ais.mature.clear();
                ais.populateDetectors(1000, randomMinRangeValue, randomMaxRangeValue, 524);
                ais.matchRAny(i);
                results += (ais.matureApp.size() / 30.0) * 100;
                //System.out.println(ais.matureApp.size());
                immatureDetectors += ais.detectors.size();
                matureDetector += ais.mature.size();
                nonSelfApp += ais.matureApp.size();
                System.out.println("masked app: " + ais.matureApp.size() + "\tImmature detectors:  " + ais.detectors.size() + "\tMature detectors:  " + ais.mature.size());
            }
            System.out.println(results / 30.0);
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("results.txt", true)))) {
                out.print("R = " + i);
                out.print("\tAverage Accuracy:  " + (results / 30.0));
                out.print("\tNonSelf App Detected:  " + nonSelfApp / 30.0);
                out.print("\tImmature detectors:  " + immatureDetectors / 30.0);
                out.print("\tMature detectors:  " + matureDetector / 30.0);
                out.println();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }

        }
         
        //ais.setDetectorsRContiguous(ais.getDetectors());
        //ais.queryR();
        //ais.matchRAny(ais.getR());
        //System.out.println("R-Any AIS");
        //System.out.println(ais.mature.size());
        //System.out.println(ais.matureApp.size());
        //ais.matchRContiguous(ais.getR());
        //System.out.println("\nR-Contiguous AIS");
        //System.out.println(ais.matureContiguous.size());
        //System.out.println(ais.matureAppContiguous.size());
        /* masked process
         ais.populateDetectors(1000, randomMinRangeValue, randomMaxRangeValue, 524);
         reduceFeatures(ais.all);
         ais.setDetectorsMasked(1000, randomMinRangeValue, randomMaxRangeValue, ais.all.get(0).getNumberFeatures());
         for (int i = 0; i < 60; i++) {
         if (i < 30) {
         ais.maskedSelf.add(ais.all.get(i));
         } else {
         ais.maskedNonSelf.add(ais.all.get(i));
         }
         }
         ais.matchRAnyMasked(5);
         System.out.println("R-Any Masked AIS");
         System.out.println(ais.matureMasked.size());
         System.out.println(ais.matureAppMasked.size());
         System.out.println("Progress so Far");
         */
        
        /*
        reduceFeatures(ais.all);
        for (int k = 0; k < 60; k++) {
            if (k < 30) {
                ais.maskedSelf.add(ais.all.get(k));
            } else {
                ais.maskedNonSelf.add(ais.all.get(k));
            }
        }
        for (int i = 0; i < 211; i++) {
            double results = 0;
            double immatureDetectors = 0.0, matureDetector = 0.0, nonSelfApp = 0.0;
            for (int j = 0; j < 30; j++) {

                ais.detectorsMasked.clear();
                ais.matureAppMasked.clear();
                ais.matureMasked.clear();
                ais.detectors.clear();
                ais.populateDetectors(1000, randomMinRangeValue, randomMaxRangeValue, 524);
                ais.setDetectorsMasked(1000, randomMinRangeValue, randomMaxRangeValue, ais.all.get(0).getNumberFeatures());

                ais.matchRAnyMasked(i);
                results += (ais.matureAppMasked.size() / 30.0) * 100;
                immatureDetectors += ais.detectorsMasked.size();
                matureDetector += ais.matureMasked.size();
                nonSelfApp += ais.matureAppMasked.size();
                System.out.println("masked app: " + ais.matureAppMasked.size() + "\tImmature detectors:  " + ais.detectorsMasked.size() + "\tMature detectors:  " + ais.matureMasked.size());
            }
            System.out.println(results / 30.0);
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("resultsMasked.txt", true)))) {
                out.print("R = " + i);
                out.print("\tAverage Accuracy:  " + (results / 30.0));
                out.print("\tNonSelf App Detected:  " + nonSelfApp / 30.0);
                out.print("\tImmature detectors:  " + immatureDetectors / 30.0);
                out.print("\tMature detectors:  " + matureDetector / 30.0);
                out.println();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }

        }
        */
    }
    /*
     * Populates self/non-self Feature Vectors with Features
     * Input features from file
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
            all.add(vector);
        } else {
            nonSelf.add(vector);
            all.add(vector);
        }
    }

    /*
     * Populates Detector list with random ranges
     * Number of ranges equals number of features in Feature Vector
     */
    public void populateDetectors(int numDetectors, int randomMinRangeValue, int randomMaxRangeValue, int numberFeatures) {
        Detector detector;
        for (int i = 0; i < numDetectors; i++) {
            detector = new Detector(numberFeatures, randomMinRangeValue, randomMaxRangeValue);
            detector.populateRanges(randomMinRangeValue, randomMaxRangeValue);
            getDetectors().add(detector);
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
     * Matches detectors to Self
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
     * Matches remaining detectors to Non-Self
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
                    if (!mature.contains(detector)) {
                        mature.add(detector);
                    }
                    detected = true;
                }
            }
            if (detected == true) {
                if (!matureApp.contains(vector)) {
                    matureApp.add(vector);
                }
            }
        }
    }

    /*
     * Matches detectors to Self
     */
    public void matchSelfMasked(int r) {

        for (int i = 0; i < maskedSelf.size(); i++) {
            FeatureVector vector = maskedSelf.get(i);
            for (int k = 0; k < detectorsMasked.size(); k++) {
                int fired = 0;
                Detector detector = detectorsMasked.get(k);
                for (int j = 0; j < maskedSelf.get(0).getNumberFeatures(); j++) {
                    if (detector.inRange(detector.getRanges().get(j), vector.getFeatures().get(j))) {
                        fired++;
                    }
                }
                if (fired >= r) {
                    detectorsMasked.remove(k);
                    k--;
                }
            }
        }
    }

    /*
     * Matches remaining detectors to Non-Self
     */
    public void matchNonSelfMasked(int r) {

        int i;
        for (i = 0; i < maskedNonSelf.size(); i++) {
            boolean detected = false;
            FeatureVector vector = maskedNonSelf.get(i);
            for (int k = 0; k < detectorsMasked.size(); k++) {
                int fired = 0;
                Detector detector = detectorsMasked.get(k);
                for (int j = 0; j < maskedNonSelf.get(0).getNumberFeatures(); j++) {
                    if (detector.inRange(detector.getRanges().get(j), vector.getFeatures().get(j))) {
                        fired++;
                    }
                }
                if (fired >= r) {
                    if (!matureMasked.contains(detector)) {
                        matureMasked.add(detector);
                    }
                    detected = true;
                }
            }
            if (detected == true) {
                if (!matureAppMasked.contains(vector)) {
                    matureAppMasked.add(vector);
                }
            }
        }
    }
    /*
     * Matches detectors to Self, R-Contiguous AIS
     */

    public void matchSelfContiguous(int r) {

        for (int i = 0; i < self.size(); i++) {
            FeatureVector vector = self.get(i);
            for (int k = 0; k < getDetectorsContiguous().size(); k++) {
                Detector detector = detectorsContiguous.get(k);
                if (matchContiguous(vector, detector, r)) {
                    getDetectorsContiguous().remove(k);
                    k--;
                }
            }
        }
    }

    /*
     * Matches remaining detectors to Non-Self, R-Contiguous AIS
     */
    public void matchNonSelfContiguous(int r) {

        for (int i = 0; i < self.size(); i++) {
            FeatureVector vector = nonSelf.get(i);
            boolean detected = false;
            for (int k = 0; k < getDetectorsContiguous().size(); k++) {
                int fired = 0;
                Detector detector = detectorsContiguous.get(k);
                if (matchContiguous(vector, detector, r)) {
                    matureContiguous.add(detector);
                    detected = true;
                }
            }
            if (detected == true) {
                matureAppContiguous.add(vector);
            }
        }
    }

    /*
     * Determines if vector and detector match
     * Used in matchSelfContiguous & matchNonSelfContiguous
     */
    public boolean matchContiguous(FeatureVector vector, Detector detector, int r) {

        //create sub array of both detector and vector
        List<Double> features = vector.getFeatures();
        List<String> ranges = detector.getRanges();

        boolean matched = false;
        //match self/non-self
        for (int i = 0; i < features.size() - r; i++) {
            int fired = 0;
            List<Double> featuresTemp = features.subList(i, i + r);
            List<String> detectorTemp = ranges.subList(i, i + r);
            for (int j = 0; j < featuresTemp.size(); j++) {
                if (detector.inRange(detectorTemp.get(j), featuresTemp.get(j))) {
                    fired++;
                }
            }
            if (fired > r) {
                matched = true;
            }
        }
        return matched;

    }

    /*
     * R-Any AIS experiment procedure
     */
    public void matchRAny(int r) {
        matchSelf(r);
        matchNonSelf(r);
    }

    /*
     * R-Contiguous AIS experiment procedure
     */
    public void matchRContiguous(int r) {
        matchSelfContiguous(r);
        matchNonSelfContiguous(r);
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

    /**
     * @return the detectors
     */
    public List<Detector> getDetectors() {
        return detectors;
    }

    /**
     * @param detectors the detectors to set
     */
    public void setDetectors(List<Detector> detectors) {
        this.detectors = detectors;
    }

    /**
     * @return the detectorsRContiguous
     */
    public List<Detector> getDetectorsContiguous() {
        return detectorsContiguous;
    }

    /**
     * @param detectorsRContiguous, detectorsRContiguous detectors to set
     */
    public void setDetectorsRContiguous(List<Detector> detectors) {
        //detectorsContiguous
        for (Detector p : detectors) {
            try {
                detectorsContiguous.add((Detector) p.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ArtificialImmuneSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setDetectorsMasked(int numDetectors, int randomMinRangeValue, int randomMaxRangeValue, int numberFeatures) {
        Detector detector;
        for (int i = 0; i < numDetectors; i++) {
            detector = new Detector(numberFeatures, randomMinRangeValue, randomMaxRangeValue);
            detector.populateRanges(randomMinRangeValue, randomMaxRangeValue);
            detectorsMasked.add(detector);
        }
    }

    public void setMask(List<FeatureVector> featureVector, List<FeatureVector> masked) {
        //detectorsContiguous
        for (FeatureVector p : featureVector) {
            try {
                masked.add((FeatureVector) p.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ArtificialImmuneSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void reduceFeatures(List<FeatureVector> maskedFV) {

        for (int i = 0; i < maskedFV.get(0).getNumberFeatures(); i++) { //524 features
            double mask = 0.0;
            for (int j = 0; j < maskedFV.size(); j++) { //30 apps
                mask += (double) maskedFV.get(j).getFeatures().get(i);
            }
            if (mask <= 30.) {
                for (int k = 0; k < maskedFV.size(); k++) {
                    maskedFV.get(k).getFeatures().remove(i);
                }
                i--;
            }
        }
    }

    private void matchRAnyMasked(int r) {
        matchSelfMasked(r);
        matchNonSelfMasked(r);
    }
}
