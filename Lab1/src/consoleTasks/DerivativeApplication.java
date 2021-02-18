package consoleTasks;

import java.io.*;

public class DerivativeApplication {

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {

        // TODO Auto-generated method stub
        Evaluatable functs[] = new Evaluatable[3];
        functs[0] = new FFunction(0.5);
        functs[1] = new ListInterpolation();
        functs[2] = new FileListInterpolation();

        ((SolveEqFunction)functs[1]).setRootApprox(0.7);

        try {
            ((FileListInterpolation)functs[2]).readFromFile("TblFunc.dat");
        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        double root = 0.6;
        for (double a = 1.0; a <= 7.0; a+= 0.1) {
            root = NumMethods.findRoot(root, 1.0e-6, new LeftHand(a));
            ((ListInterpolation)functs[1]).addPoint(new Point2D(a, root));
        }


        String fileName = "";
        for (Evaluatable f: functs) {
            System.out.println("Функция: " + f.getClass().getSimpleName());
            fileName = f.getClass().getSimpleName() + ".dat";
            PrintWriter out = new PrintWriter(new FileWriter(fileName));
            for (double x = 1.5; x <= 6.5; x += 0.05) {
                System.out.println("x: " + x + "\tf: " + f.evalf(x) + "\tf': " + NumMethods.der(x, 1.0e-4, f));
                out.printf("%16.6e%16.6e%16.6e\n", x, f.evalf(x), NumMethods.der(x, 1.0e-4, f));
            }
            System.out.println("\n");
            out.close();
        }
    }


}
