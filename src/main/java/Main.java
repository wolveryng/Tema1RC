import java.util.Random;
import java.util.Scanner;

public class Main {
    private static String ReadMatrix(Scanner scanner) {
        String input;
        do {
            System.out.print("Introduceți un șir binar (multiplu de 7 caractere): ");
            input = scanner.next();
        } while (input.length() % 7 != 0 || !input.matches("[01]+"));
        return input;
    }

    private static void FillMatrix(int[][] matrix, String input , int row, int col) {
        int index=0;
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++)
                matrix[i][j]=Character.getNumericValue(input.charAt(index++));
    }
    private static void Paritate(int[][] matrix, int row, int col) {
        for(int i=0;i<row;i++)
        {
            int paritateRand=0;
            for(int j=0;j<col;j++)
            {
                paritateRand=matrix[i][j]+paritateRand;
            }
            paritateRand=paritateRand%2;
            matrix[i][col]=paritateRand;
        }

        for(int j=0;j<=col;j++)
        {
            int paritateColoana=0;
            for(int i=0;i<row;i++)
            {
                paritateColoana=matrix[i][j]+paritateColoana;
            }
            paritateColoana=paritateColoana%2;
            matrix[row][j]=paritateColoana;
        }
    }
    private static void SimulateError(int[][] matrix, int row, int col, Random random) {
        int errorRow= random.nextInt(row);
        int errorCol= random.nextInt(col);

        if(matrix[errorRow][errorCol]==1)
            matrix[errorRow][errorCol]=0;
        else
            matrix[errorRow][errorCol]=1;
    }

    public static void DetectError(int[][] matrix, int row, int col) {
        int errorRow=-1;
        int errorCol=-1;

        for(int i=0;i<row;i++)
        {
            int rowParity=0;
            for(int j=0;j<col;j++)
            {
                rowParity=matrix[i][j]+rowParity;
            }
            rowParity=rowParity%2;
            if(rowParity !=matrix[i][col]) {
                errorRow = i;
                break;
            }
        }

        for(int j=0;j<=col;j++)
        {
            int colParity=0;
            for(int i=0;i<row;i++)
            {
                colParity=matrix[i][j]+colParity;
            }
            colParity=colParity%2;
            if(colParity !=matrix[row][j]) {
                errorCol = j;
                break;
            }

        }

        if(errorRow!=-1 && errorCol!=-1) {
            System.out.println("Eroare detectata la pozitita (" +errorRow+","+errorCol+")");

        } else {
            System.out.println("Nu s-a detectat nicio eroare");
        }

    }

    private static void PrintMatrix(int[][] matrix , int row, int col) {
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<col;j++)
            {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }

    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input=ReadMatrix(scanner);
        Random random = new Random();
        int row=input.length()/7;
        int col=7;
        int [][]matrix=new int[row+1][col+1];
        System.out.println("Matricea rezultata in urma sirului introdus : ");

        FillMatrix(matrix,input,row,col);
        PrintMatrix(matrix,row,col);

        System.out.println("Matricea rezultata dupa calcularea bitului ");

        Paritate(matrix,row,col);
        PrintMatrix(matrix,row+1,col+1);

        System.out.println("Simulam coruperea unui bit");
        SimulateError(matrix,row,col,random);
        PrintMatrix(matrix,row+1,col+1);

        DetectError(matrix,row,col);

    }
}
