package prediction;

import java.util.Map;

public class Model {
    public double errorRate;
    private double[] params;
    public Map<Integer, String> classes;

    public Model(double errorRate, double[] params, Map<Integer, String> classes) {
        this.errorRate = errorRate;
        this.params = params;
        this.classes = classes;
    }

    public void predict(double[] values){
        Double result = Math.abs(getYp(values));
        System.out.println("Yprédit = "+result);
        int classe = result.intValue();
        System.out.println("Entier le plus proche = "+classe);
        if(classes.containsKey(classe)){
            System.out.println("\n***********************Résultat***********************");
            System.out.println("Classe prédite: "+classes.get(classe));
        }
        else System.out.println("*************************Aucun résultat trouvé*******************\nCette valeur ne correspond à aucune classe.");
    }

    private double getYp(double[] values){
        double result = params[0];
        for(int i = 1; i < params.length; i++){
            result += params[i]*values[i-1];
        }
        return result;
    }
}
