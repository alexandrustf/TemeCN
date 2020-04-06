import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.lang.Math;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        RarMatrix m1 = new RarMatrix();
        RarMatrix m2 = new RarMatrix();
        RarMatrix m3 = new RarMatrix();
        RarMatrix m4 = new RarMatrix();
        RarMatrix m5 = new RarMatrix();
        m1.buildMatrix("C:\\Users\\Alex Stefan\\Desktop\\MICROSERVICII\\A7IP2019-Microservices\\Tema4CalculNumeric\\src\\a_1.txt", 100);
        m2.buildMatrix("C:\\Users\\Alex Stefan\\Desktop\\MICROSERVICII\\A7IP2019-Microservices\\Tema4CalculNumeric\\src\\a_2.txt", 100);
        m3.buildMatrix("C:\\Users\\Alex Stefan\\Desktop\\MICROSERVICII\\A7IP2019-Microservices\\Tema4CalculNumeric\\src\\a_3.txt", 100);
        m4.buildMatrix("C:\\Users\\Alex Stefan\\Desktop\\MICROSERVICII\\A7IP2019-Microservices\\Tema4CalculNumeric\\src\\a_4.txt", 100);
        m5.buildMatrix("C:\\Users\\Alex Stefan\\Desktop\\MICROSERVICII\\A7IP2019-Microservices\\Tema4CalculNumeric\\src\\a_5.txt", 100);
        double[] b1 = ReadBFromFile("C:\\Users\\Alex Stefan\\Desktop\\MICROSERVICII\\A7IP2019-Microservices\\Tema4CalculNumeric\\src\\b_1.txt");
        double[] b2 = ReadBFromFile("C:\\Users\\Alex Stefan\\Desktop\\MICROSERVICII\\A7IP2019-Microservices\\Tema4CalculNumeric\\src\\b_2.txt");
        double[] b3 = ReadBFromFile("C:\\Users\\Alex Stefan\\Desktop\\MICROSERVICII\\A7IP2019-Microservices\\Tema4CalculNumeric\\src\\b_3.txt");
        double[] b4 = ReadBFromFile("C:\\Users\\Alex Stefan\\Desktop\\MICROSERVICII\\A7IP2019-Microservices\\Tema4CalculNumeric\\src\\b_4.txt");
        double[] b5 = ReadBFromFile("C:\\Users\\Alex Stefan\\Desktop\\MICROSERVICII\\A7IP2019-Microservices\\Tema4CalculNumeric\\src\\b_5.txt");

        List<Double> B1 = Arrays.stream(b1).boxed().collect(Collectors.toList());
        List<Double> B2 = Arrays.stream(b2).boxed().collect(Collectors.toList());
        List<Double> B3 = Arrays.stream(b3).boxed().collect(Collectors.toList());
        List<Double> B4 = Arrays.stream(b4).boxed().collect(Collectors.toList());
        List<Double> B5 = Arrays.stream(b5).boxed().collect(Collectors.toList());

        System.out.println("Rezultatul pentru a1 si b1");
        System.out.println(m1.ALG(B1));
        System.out.println("Rezultatul pentru a2 si b2");
        System.out.println(m2.ALG(B2));
        System.out.println("Rezultatul pentru a3 si b3");
        System.out.println(m3.ALG(B3));
        System.out.println("Rezultatul pentru a4 si b4");
        System.out.println(m4.ALG(B4));
        System.out.println("Rezultatul pentru a5 si b5");
        System.out.println(m5.ALG(B5));

    }

    public static double[] ReadBFromFile(String path){
        BufferedReader br = null;
        try {
        br = new BufferedReader(new FileReader(path));
        int n = Integer.parseInt(br.readLine());
        double[] result = new double[n];
        int index = 0;
        String sCurrentLine;

        while ((sCurrentLine = br.readLine()) != null) {
            if (sCurrentLine.compareTo("") == 0) {
                break;
            }

            double value = Double.parseDouble(sCurrentLine.trim());
            result[index] = value;
            index++;
        }
        return result;
    }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new double[]{0};
    }



}