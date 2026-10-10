import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final Random random = new Random();

    private static final Scanner scanner = new Scanner(System.in);

    private static final char[] arrCharGuessWord = putWordInArray();

    private static final char[] maskedWord = printUnknowSymbols(arrCharGuessWord.length).toCharArray();

    private static int mistakes = 0;

    private static final char[] arrRepeatChar = new char[33];

    private static int indexArrRepeatChar = 0;



    public static void main(String[] args) {
        while (true){
            System.out.println();
            System.out.println("1 - начать игру ");
            System.out.println("0 - выйти из игры");
            int choosing = scanner.nextInt();
            System.out.println(new String(arrCharGuessWord));
            if (choosing == 1){
                startGame();
            }
            else{
                break;
            }
        }

    }

    public static String readFile(int randomNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader("words.txt"))) {
            String line = "";
            for (int i = 0; i < randomNumber; i++) {
                line = reader.readLine();
            }
            return line.toLowerCase();
        } catch (IOException e) {
            System.out.println(e);
            return "";
        }
    }

    public static void startGame() {
        System.out.println(maskedWord);
        while (mistakes < 6) {
            char leter = inputCharFromPlayer();
            boolean check = checkCharInWord(leter);
            if (!check) {
                System.out.println(maskedWord);
                mistakes += 1;
                printHangman();
            } else {
                printHangman();
                System.out.println(maskedWord);
            }
            if (!new String(maskedWord).contains("*")) {
                System.out.println("Вы победлили!");
                System.out.println(maskedWord);
                break;
            }
        }
        if (mistakes > 6) {
            System.out.println("Вы проиграли! Загаданное слово:" + new String(arrCharGuessWord));
        }
    }

    public static String guessRandomWord() {
        int randomNumber = random.nextInt(50) + 1;
        return readFile(randomNumber);

    }

    public static void printHangman() {
        switch (mistakes) {
            case 0:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                System.out.println("осталось 6 ошибок\n");
                break;
            case 1:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                System.out.print("  O  \n");
                System.out.println("осталось 5 ошибок\n");
                break;
            case 2:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                System.out.print("  O  \n");
                System.out.print("  |  \n");
                System.out.print("  |  \n");
                System.out.println("осталось 4 ошибки\n");
                break;
            case 3:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                System.out.print("  O  \n");
                System.out.print(" /|  \n");
                System.out.print("  |  \n");
                System.out.println("осталось 3 ошибки\n");
                break;
            case 4:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                System.out.print("  O  \n");
                System.out.print(" /|\\  \n");
                System.out.print("  |  \n");
                System.out.println("осталось 2 ошибки\n");
                break;
            case 5:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                System.out.print("  O  \n");
                System.out.print(" /|\\  \n");
                System.out.print("  |  \n");
                System.out.print(" /   \n");
                System.out.println("осталось 1 ошибкa\n");
                break;
            case 6:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                System.out.print("  O  \n");
                System.out.print(" /|\\  \n");
                System.out.print("  |  \n");
                System.out.print(" / \\  \n");
                break;

        }
    }

    public static char[] putWordInArray() {
        return guessRandomWord().toCharArray();
    }

    public static String printUnknowSymbols(int len) {
        StringBuilder unknowSymbols = new StringBuilder();
        for (int i = 0; i < len; i++) {
            unknowSymbols.append("*");
        }

        return unknowSymbols.toString();
    }

    public static boolean checkCharInWord(char leters) {
        boolean found = false;
        for (int i = 0; i < arrCharGuessWord.length; i++) {
            if (leters == arrCharGuessWord[i]) {
                maskedWord[i] = arrCharGuessWord[i];
                found = true;
            }
        }
        return found;
    }

    public static char inputCharFromPlayer() {
        String stringPlayer;
        System.out.println("введите ОДНУ букву:");
        do {
            stringPlayer = scanner.next();
            stringPlayer = stringPlayer.toLowerCase();
            if (stringPlayer.isEmpty()) {
                System.out.println("Вы ничего не ввели");
            } else if (stringPlayer.length() > 1) {
                System.out.println("Введите только одну букву");
            } else if (!stringPlayer.matches("[А-Яа-яЁё]")) {
                System.out.println("Это не русская буква");
            } else if (checkRepeatChar(stringPlayer.charAt(0))) {
                System.out.println("Вы уже вводили эту букву");
            }
        }
        while (stringPlayer.length() > 1 || !stringPlayer.matches("[А-Яа-яЁё]") || checkRepeatChar(stringPlayer.charAt(0)));
        arrRepeatChar[indexArrRepeatChar] = stringPlayer.charAt(0);
        indexArrRepeatChar += 1;
        return stringPlayer.charAt(0);
    }

    public static boolean checkRepeatChar(char stringPlayer) {
        for (char c : arrRepeatChar) {
            if (c == stringPlayer) {
                return true;
            }
        }
        return false;
    }


}
