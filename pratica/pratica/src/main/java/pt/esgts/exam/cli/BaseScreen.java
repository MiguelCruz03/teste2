package pt.esgts.exam.cli;

import pt.esgts.exam.cli.model.ScreenOption;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Abstract class that provides some common methods used on most screens
 *
 * @author Bruno Jesus
 * @version 1.0
 */
public abstract class BaseScreen implements Runnable {

    protected Scanner scanner;

    public BaseScreen() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Executes the screen
     * Makes the class a valid {@link Runnable}
     */
    public abstract void run();

    /**
     * Asks for user input
     *
     * @param question the question to ask
     * @param inline   if false will ask for the input in a new line
     * @return the text provided by the user
     */
    public String askText(String question, boolean inline) {
        System.out.printf("%s:%s", question, inline ? " " : "\n");
        return scanner.nextLine();
    }

    /**
     * Asks for numeric user input
     * Keeps asking until the user provides a valid number
     *
     * @param question the question to ask
     * @param inline   if false will ask for the input in a new line
     * @return the number provided by the user
     */
    public int askInteger(String question, boolean inline) {
        Integer result = null;
        while (result == null) {
            try {
                String textResult = askText(question, inline);
                result = Integer.parseInt(textResult);
            } catch (NumberFormatException e) {
                System.out.println();
            }
        }
        return result;
    }

    /**
     * Prints the list of options surrounded by an ASCII box
     *
     * @param options the options to print
     */
    public void printOptions(List<ScreenOption> options) {
        final int width = options.stream()
                .map(ScreenOption::toTextMenuOption)
                .mapToInt(String::length)
                .map(i -> i + 4)
                .max()
                .orElse(80);

        final String line = filler(width, '-');

        System.out.println(line);
        options.stream()
                .map(ScreenOption::toTextMenuOption)
                .map(s -> "| " + s + filler(width - s.length() - 4, ' ') + " |")
                .forEach(System.out::println);
        System.out.println(line);

        int chosenId = askInteger("Choose", true);

        options.stream().filter(option -> option.getId() == chosenId)
                .map(ScreenOption::getAction)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unknown option"))
                .run();

    }

    private String filler(int size, char character) {
        return IntStream.range(0, size)
                .mapToObj(i -> String.valueOf(character))
                .collect(Collectors.joining());
    }
}
