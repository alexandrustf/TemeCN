import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RarMatrix {
    public static double eps =  Math.pow(10, -7);;
    private final Map<Integer, Map<Integer, Double>> matrix;
    private int n;

    public RarMatrix() {
        this.matrix = new TreeMap<>();
    }

    public RarMatrix(Map<Integer, Map<Integer, Double>> matrix) {
        this.matrix = matrix;
        this.n = matrix.size();
    }

    public void buildMatrix(String path, int limit) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));
            this.n = Integer.parseInt(br.readLine());

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.compareTo("") == 0) {
                    break;
                }

                String[] data = sCurrentLine.split(", ");

                double value = Double.parseDouble(data[0]);
                int ln = Integer.parseInt(data[1]);
                int cl = Integer.parseInt(data[2]);

                this.matrix.computeIfAbsent(ln, k -> new TreeMap<>());

                if (this.matrix.get(ln).size() == limit) {
                    throw new IllegalArgumentException("Dimensiune depasita la linia " + ln);
                }
                if (this.matrix.get(ln).size() > 0) {
                    if (this.matrix.get(ln).get(cl) != null) {
                        double aux = this.matrix.get(ln).get(cl) + value;
                        this.matrix.get(ln).put(cl, aux);
                    } else {
                        this.matrix.get(ln).put(cl, value);
                    }
                } else {
                    this.matrix.get(ln).put(cl, value);
                }
            }


        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
    }

    private static Map<Integer, Map<Integer, Double>> buildEmptyMatrix(int n) {
        Map<Integer, Map<Integer, Double>> newMatrix = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            newMatrix.computeIfAbsent(i, k -> new TreeMap<>());
        }
        return newMatrix;
    }

    private boolean checkCondition(double value) {
        return Math.abs(value) < eps;
    }

    public RarMatrix multiply(RarMatrix B) {
        Map<Integer, Map<Integer, Double>> newMatrix = buildEmptyMatrix(n);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                double total = 0.0;
                for (int columnOfA : this.matrix.get(i).keySet()) {
                    if (B.getLine(columnOfA).get(j) != null) {
                        total += this.matrix.get(i).get(columnOfA) * B.getLine(columnOfA).get(j);
                    }
                }
                if (!checkCondition(total)) {
                    newMatrix.get(i).put(j, total);
                }
            }
        }

        return new RarMatrix(newMatrix);
    }

    public RarMatrix plus(RarMatrix B) {
        Map<Integer, Map<Integer, Double>> newMatrix = buildEmptyMatrix(n);
        for (int i = 0; i < this.n; i++) {
            Map<Integer, Double> aux = new HashMap<>(B.getLine(i));
            for (int index : this.matrix.get(i).keySet()) {
                if (B.getLine(i).containsKey(index)) {
                    double sum = this.matrix.get(i).get(index) + B.getLine(i).get(index);
                    newMatrix.get(i).put(index, sum);
                    aux.remove(index);
                } else {
                    newMatrix.get(i).put(index, this.matrix.get(i).get(index));
                }
            }
            for (int index : aux.keySet()) {
                newMatrix.get(i).put(index, aux.get(index));
            }
        }
        return new RarMatrix(newMatrix);
    }

    public boolean areEqual(RarMatrix B) {
        for (int i = 0; i < this.n; i++) {
            for (int auxBIndex : B.getLine(i).keySet()) {
                if (!this.matrix.get(i).containsKey(auxBIndex))
                    return false;
                if (!checkCondition(this.matrix.get(i).get(auxBIndex) - B.getLine(i).get(auxBIndex)))
                    return false;
            }
        }
        return true;
    }

    public Map<Integer, Map<Integer, Double>> getMatrix() {
        return matrix;
    }

    public Map<Integer, Double> getLine(int i) {
        return this.matrix.get(i);
    }

    public List<Double> ALG(List<Double> b){
        List<Double> X = new ArrayList<>();

        List<Map<Integer,Double>> A = new ArrayList<Map<Integer,Double>>(this.matrix.values());
        double delta;
        int k=0;
        for(int i=0;i<b.size();i++){
            X.add(0.0);
        }
        double mathPow = Math.pow(10,8);
        do {
            delta = SimpleNorma(Formula3(A, b, X));
            System.out.print("Pentru K=" + k + " valoarea deltei este: "+ delta);
            System.out.println();
            k++;
        }while(delta >= eps && k <= 10000 && delta <= mathPow);
        if(delta< eps)
            return X;
        else
            return null;
    }

    public static double SimpleNorma(List<Double> Z) {
        double norm = 0.0;
        for (double val : Z) {
            norm += Math.abs(val);
        }
        return norm;
    }

    private static List<Double> Formula3(List<Map<Integer,Double>> A, List<Double> b,List<Double>X){
        double Xp = X.get(0);
        int k=1;
        List<Double> dif = new ArrayList<>();
        for(int i=0;i<b.size();i++){
            if(i==k){
                k++;
                Xp=X.get(i);
            }
            double valueDiagonal = A.get(i).get(i);
            double bVal = b.get(i);
            double lowerTriangle = LowerTriangle(A,X,i);
            double upperTriangle = UpperTriangle(A,X,i);
            double value =  (bVal - lowerTriangle - upperTriangle)/valueDiagonal;
            X.set(i,value);
            dif.add(X.get(i)- Xp);
        }
        return dif;
    }

    private static double LowerTriangle(List<Map<Integer,Double>> A, List<Double> X,int i){
        double total =0.0;
        for(int val:A.get(i).keySet()) {
            if (val < i) {
                total += A.get(i).get(val) * X.get(val) ;
            }
            else
                break;
        }
        return total;
    }

    private static double UpperTriangle(List<Map<Integer,Double>> A, List<Double> X,int i){
        double total =0.0;
        for(int val:A.get(i).keySet()){
            if(val>=i+1){
                total += A.get(i).get(val) * X.get(val) ;
            }
        }
        return total;
    }

}

