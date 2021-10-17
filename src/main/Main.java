package main;

import prediction.Model;
import training.Train;
import utils.CsvDataReader;
import utils.ProcessResult;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        ProcessResult result = CsvDataReader.parse(250, 11);

        double[][] matrice = result.matrice;
        int[] classes = result.classes;

        System.out.println("Taille d'une line de matrice: "+matrice[0].length);
        System.out.println("Taille des classes: "+classes.length);


//        double[] initialParams = new double[11];
//        for(int i =0; i < initialParams.length; i++) {
//            initialParams[i] = Math.random();
//        }

//        double[] initialParams = new double[11];
//        Arrays.fill(initialParams, Math.random());

        double[] initialParams = {0.43166803435690015,0.5514613218300405,0.39827556196565594,0.27644001169500343,0.00808967746178546,0.7612215158321446,0.3102403771847696,0.004061699494182336,0.9916075511256646,0.10915875260645025,0.7562641781046145};

        Train train = new Train(matrice, classes, initialParams, 0.000000001, 0.002, 100000);
        try {
            Model model = train.start();

            if(model != null){
                System.out.println("\n*********************Training terminé**********************");
                System.out.println("Les paramètres...");
                train.showBestParms();
                System.out.println("\nMinimum error from training: "+model.errorRate);

                System.out.println("\n***********Prédiction************");

                model.predict(matrice[0]);
            }
            else System.out.println("*********************Le Training n'a pas aboutti***********************");
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
