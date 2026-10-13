import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final Random RANDOM = new Random();

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final int MAX_MISTAKES = 6;

    private static final int MAX_COUNT_WORDS = 50;

    private static final String WORDS_FILE_PATH = "words.txt";


    public static void main(String[] args) {
        while (true) {
            System.out.println();
            System.out.println("1 - начать игру");
            System.out.println("0 - выйти из игры");
            String choosing = SCANNER.next();

            if (choosing.equals("1")) {
                startGame();
            } else if (choosing.equals("0")) {
                break;
            } else {
                System.out.println("Введите 1 или 0");
            }
        }

    }

    public static String readRandomFile(int randomNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(WORDS_FILE_PATH))) {
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
        String guessWord = guessRandomWord();
        char[] maskedWord = printUnknowSymbols(guessWord.length()).toCharArray();
        List<Character> repeatChars = new ArrayList<>();
        int mistakes = 0;
        int hiddenLetters = guessWord.length();
        boolean isWin = false;

        System.out.println(maskedWord);
        while (mistakes < MAX_MISTAKES && !isWin) {
            char letter = inputCharFromPlayer(repeatChars);

            int opened = checkCharInWord(letter, guessWord, maskedWord);
            if (opened == 0) {
                mistakes++;
            }
            hiddenLetters -= opened;
            isWin = hiddenLetters == 0;

            printGameState(mistakes, maskedWord, isWin, guessWord);
        }
    }

    public static String guessRandomWord() {
        int randomNumber = RANDOM.nextInt(MAX_COUNT_WORDS) + 1;
        return readRandomFile(randomNumber);

    }

    public static void printGameState(int mistakes, char[] maskedWord, boolean isWin, String guessWord) {
        switch (mistakes) {
            case 0:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                break;
            case 1:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                System.out.print("  O  \n");
                break;
            case 2:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                System.out.print("  O  \n");
                System.out.print("  |  \n");
                System.out.print("  |  \n");
                break;
            case 3:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                System.out.print("  O  \n");
                System.out.print(" /|  \n");
                System.out.print("  |  \n");
                break;
            case 4:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                System.out.print("  O  \n");
                System.out.print(" /|\\  \n");
                System.out.print("  |  \n");
                break;
            case 5:
                System.out.print("_____\n");
                System.out.print("  |  \n");
                System.out.print("  O  \n");
                System.out.print(" /|\\  \n");
                System.out.print("  |  \n");
                System.out.print(" /   \n");
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

        System.out.println(maskedWord);

        if (isWin) {
            System.out.println("Вы победили!");
        } else if (mistakes >= MAX_MISTAKES) {
            System.out.println("Вы проиграли! Загаданное слово: " + guessWord);
        } else {
            System.out.println("Осталось ошибок: " + (MAX_MISTAKES - mistakes));
        }
    }

    public static String printUnknowSymbols(int length) {
        StringBuilder unknowSymbols = new StringBuilder();
        for (int i = 0; i < length; i++) {
            unknowSymbols.append("*");
        }

        return unknowSymbols.toString();
    }

    public static int checkCharInWord(char letter, String guessWord, char[] maskedWord) {
        int openned = 0;
        for (int i = 0; i < guessWord.length(); i++) {
            if (letter == guessWord.charAt(i)) {
                maskedWord[i] = guessWord.charAt(i);
                openned++;
            }
        }
        return openned;
    }

    public static char inputCharFromPlayer(List<Character> repeatChars) {
        System.out.println("введите ОДНУ букву:");
        do {
            String playerInput = SCANNER.next().toLowerCase();

            if (playerInput.length() > 1) {
                System.out.println("Введите только одну букву");
                continue;
            }
            if (!playerInput.matches("[а-яё]")) {
                System.out.println("Это не русская буква");
                continue;
            }

            char letter = playerInput.charAt(0);
            if (repeatChars.contains(letter)) {
                System.out.println("Вы уже вводили эту букву");
                continue;
            }

            repeatChars.add(letter);
            return letter;
        } while (true);
    }

}
