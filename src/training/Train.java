package training;

import prediction.Model;
import utils.CsvClassReader;

public class Train {
    private double[][] matrice;
    private int[] classes;
    private double[] params;
    private double[] bestParams;
    private double n;
    private double error;
    private double errorMin;
    public boolean found = false;
    private int rowToEvalError = 5;
    private int maxIteration;
    public double miningMinError;

    public Train(double[][] matrice, int[] classes, double[] initialParams, double n, double errorMin, int maxIteration) {
        this.matrice = matrice;
        this.classes = classes;
        this.params = initialParams;
        this.n = n;
        this.errorMin = errorMin;
        this.maxIteration = maxIteration;
    }

    public Model start() throws Exception{
        if(matrice[0].length != params.length - 1) throw new Exception("La matrice de données est incompatible avec les paramètres soumis.");
        if(matrice.length != classes.length) throw new Exception("La matrice de données est incompatible avec les classes à l'entrée.");

        int rowCount = 0;
        double errorCount = 0;
        int iterations = 0;

        System.out.println("\n*********************Début*******************");

        miningMinError = Double.POSITIVE_INFINITY;

        while (!found && iterations <= maxIteration && !Double.isNaN(error) && !Double.isInfinite(error)){
            System.out.println("Iteration "+iterations);
            for(int i = 0; i < matrice.length; i++){
                error = Math.abs(getTheErrorRate());
                if(error < miningMinError) {
                    miningMinError = error;
                    bestParams = this.params.clone();
                }
                System.out.println("Nouvelle évaluation de l'erreur: " + error);
                if(Double.isNaN(error) || Double.isInfinite(error)) {
                    System.out.println("La fonction d'erreur à divergé.");
                    break;
                }
                if(error <= errorMin) {
                    found = true;
                    break;
                }
                params = nextParams(i);
                System.out.println("Nouveau params");
                showParms();
            }
            iterations++;
        }

        if(bestParams != null && bestParams.length > 0){
            return new Model(miningMinError, bestParams, CsvClassReader.parse());
        }
        return null;
    }

    private double getTheErrorRate(){
        double error = 0;
        for(int i = 0; i < matrice.length; i++){
            error += getError(getYp(i), i);
        }
        return error/matrice.length;
    }

    private double getYp(int rowToPredict){
        double result = 0;
        for(int i = 0; i < params.length; i++){
            if(i==0) result += params[i];
            else result += params[i]*matrice[rowToPredict][i-1];
        }
        return result;
    }

    public void showParms(){
        for(int i = 0; i < params.length; i++){
            System.out.print(params[i]+" ");
        }
        System.out.println();
    }

    public void showBestParms(){
        for(int i = 0; i < bestParams.length; i++){
            System.out.print(bestParams[i]+" ");
        }
        System.out.println();
    }

    private double nextParam(double oldParam, double xi){
        return oldParam - n * error * xi;
    }

    public double[] nextParams(int row){
        double[] newParams = new double[params.length];
        newParams[0] = nextParam(params[0], 1);
        for(int i = 1; i < params.length; i++){
            newParams[i] = nextParam(params[i], matrice[row][i-1]);
        }

        return newParams;
    }

    private double getError(double yp, int rowPredicted){
        return yp-classes[rowPredicted];
    }
}
