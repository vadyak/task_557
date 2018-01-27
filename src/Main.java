import java.io.*;
import java.util.Arrays;

public class Main {
    private int dimension;
    private int arrAmount;
    private int elemColumnNum;
    private int elemLineNum;
    private int moduleNum;
    private int[][][] allArrInFile;
    private int requiredNumber;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run(){
        readFile();
        searchNumber();
        writeFile();
    }

    private void readFile(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"))) {
            String[] tmpArr;
            tmpArr = bufferedReader.readLine().split(" ");
            arrAmount = Integer.parseInt(tmpArr[0]);
            dimension = Integer.parseInt(tmpArr[1]);

            tmpArr = bufferedReader.readLine().split(" ");
            elemColumnNum = Integer.parseInt(tmpArr[0]);
            elemLineNum = Integer.parseInt(tmpArr[1]);

            moduleNum = Integer.parseInt(bufferedReader.readLine());
            allArrInFile = new int[arrAmount][dimension][];
            bufferedReader.readLine();

            for (int i = 0; i < arrAmount; i++) {
                String[] currentStringLine;
                int[] currentIntLine = new int[dimension];

                for (int j = 0; j < dimension; j++) {
                    currentStringLine = bufferedReader.readLine().split(" ");

                    for (int k = 0; k < dimension; k++) {
                        currentIntLine[k] = Integer.parseInt(currentStringLine[k]);
                    }

                    allArrInFile[i][j] = currentIntLine.clone();
                }
                bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("file or line not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchNumber(){
        int[] tmpArr = new int[dimension];
        int[] tmpArr2 = new int[dimension];

        for (int i = 0; i < dimension; i++) { //только необходимая строка из первой матрицы
            tmpArr[i] = allArrInFile[0][elemColumnNum-1][i];
        }
        System.out.println(Arrays.toString(tmpArr));

        for (int k = 0; k < arrAmount-1; k++) { //кол-во перемножений матриц

            for (int i = 0; i < dimension; i++) { //перемножение строки на кол-во столбцов
                int tmpNum2 = 0;

                for (int j = 0; j < dimension; j++) { //каждый элемент будущей строки
                    tmpNum2 += tmpArr[j]*allArrInFile[k+1][j][i];
                }
                
                if (tmpNum2 >= moduleNum) tmpArr2[i] = tmpNum2%moduleNum;
                else tmpArr2[i] = tmpNum2;
            }
            for (int i = 0; i < dimension; i++) { //временное копирование строки
                tmpArr[i] = tmpArr2[i];
            }
        }

        requiredNumber = tmpArr[elemLineNum-1]; //искомое число
    }

    private void writeFile(){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"))){
            bufferedWriter.write(Integer.toString(requiredNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
