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

    private static final String UNKNOWN_SYMBOL = "*";


    public static void main(String[] args) {
        while (true) {
            System.out.println();
            System.out.println("1 - начать игру");
            System.out.println("0 - выйти из игры");
            String choosing = SCANNER.next();

            switch (choosing) {
                case "1" -> startGame();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Введите 1 или 0");
            }
        }

    }

    public static String readRandomWord(int randomNumber) {
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
        String secretWord = guessRandomWord();
        char[] maskedWord = getUnknownSymbol(secretWord.length()).toCharArray();
        Set<Character> repeatChars = new HashSet<>();
        int mistakesCount = 0;
        int hiddenLetters = secretWord.length();
        boolean isWin = false;

        System.out.println(maskedWord);
        while (mistakesCount < MAX_MISTAKES && !isWin) {
            char letter = inputCharFromPlayer(repeatChars);

            int opened = getGuessedLettersCount(letter, secretWord, maskedWord);
            if (opened == 0) {
                mistakesCount++;
            }
            hiddenLetters -= opened;
            isWin = hiddenLetters == 0;

            printGameState(mistakesCount, maskedWord, isWin, secretWord);
        }
    }

    public static String guessRandomWord() {
        int randomNumber = RANDOM.nextInt(MAX_COUNT_WORDS) + 1;
        return readRandomWord(randomNumber);

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

    public static String getUnknownSymbol(int length) {
        StringBuilder unknowSymbols = new StringBuilder();
        for (int i = 0; i < length; i++) {
            unknowSymbols.append(UNKNOWN_SYMBOL);
        }

        return unknowSymbols.toString();
    }

    public static int getGuessedLettersCount(char letter, String guessWord, char[] maskedWord) {
        int openned = 0;
        for (int i = 0; i < guessWord.length(); i++) {
            if (letter == guessWord.charAt(i)) {
                maskedWord[i] = guessWord.charAt(i);
                openned++;
            }
        }
        return openned;
    }

    public static char inputCharFromPlayer(Set<Character> repeatChars) {
        System.out.println("введите ОДНУ букву:");
        do {
            String playerInput = SCANNER.next().toLowerCase();
            if (isValidLetter(repeatChars, playerInput)) {
                continue;
            }
            char letter = playerInput.charAt(0);
            repeatChars.add(letter);
            return letter;
        } while (true);
    }

    private static boolean isValidLetter(Set<Character> repeatChars, String playerInput) {
        if (playerInput.length() > 1) {
            System.out.println("Введите только одну букву");
            return false;
        }
        if (!playerInput.matches("[а-яё]")) {
            System.out.println("Это не русская буква");
            return false;
        }

        char letter = playerInput.charAt(0);
        if (repeatChars.contains(letter)) {
            System.out.println("Вы уже вводили эту букву");
            return false;
        }
        return true;
    }

}
