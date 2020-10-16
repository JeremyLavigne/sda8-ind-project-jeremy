package userInterface;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class UserInterfaceTest {

    @Nested
    class GetIntegerTest {

        /**
         * This method is a copy of original method we are testing.
         * Why ? In order to add two parameters which mock input/output
         */
        public int getExpectedInteger(int min, int max, InputStream in, PrintStream out) {
            Scanner specialScan = new Scanner(in); // It changes here
            String input;
            int userChoice = -1;

            do {
                out.print("->");   // It changes here
                try {
                    input = specialScan.next();   // It changes here
                    userChoice = Integer.parseInt(input);
                } catch (Exception e) {
                    out.println("We are expecting an integer.");  // It changes here
                }
                if (userChoice > max || userChoice < min){
                    out.println("Please enter a number between " + min + " and " + max + ".");  // It changes here
                }
            } while (userChoice > max || userChoice < min);
            return userChoice;
        }

        @Test
        @DisplayName("Method should not accept a String as an input.")
        public void getIntegerNotAcceptString() {

            // Prepare our own input / output
            String data = "String 4"; // First input = "String" - second input = 4
            InputStream in = new ByteArrayInputStream(data.getBytes());

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(outContent);

            // Ask for an integer using our special method
            this.getExpectedInteger(3, 6, in, out);

            String expected = "We are expecting an integer.";

            assertThat(outContent.toString()).contains(expected);

        }

        @Test
        @DisplayName("Method should not accept an integer greater than the limit.")
        public void getIntegerGreaterThanLimit() {

            // Prepare our own input / output
            String data = "18 4"; // First input = 18 - second input = 4
            InputStream in = new ByteArrayInputStream(data.getBytes());

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(outContent);

            // Ask for an integer using our special method
            this.getExpectedInteger(3, 6, in, out);

            String expected = "Please enter a number between 3 and 6.";

            assertThat(outContent.toString()).contains(expected);
        }

        @Test
        @DisplayName("Method should accept an integer in the range.")
        public void getIntegerInRange() {

            // Prepare our own input / output
            String data = "4"; // First input = 4
            InputStream in = new ByteArrayInputStream(data.getBytes());

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(outContent);

            // Ask for an integer using our special method
            this.getExpectedInteger(3, 6, in, out);

            String expected = "Please enter a number between 3 and 6.";

            assertThat(outContent.toString()).doesNotContain(expected);

        }
    }

    @Nested
    class GetStringTest {

        /**
         * This method is a copy of original method we are testing.
         * Why ? In order to add two parameters which mock input/output
         */
        public String getExpectedString(int minLength, int maxLength, InputStream in, PrintStream out) {
            Scanner specialScan = new Scanner(in); // It changes here
            String input = "";

            while (input.length() > maxLength || input.length() < minLength){
                out.print("->"); // It changes here
                input = specialScan.nextLine(); // It changes here
                if (input.length() > maxLength || input.length() < minLength){
                    out.println("Please enter a string between " + minLength + " and " + maxLength + " characters.");
                }
            }
            return input;
        }

        @Test
        @DisplayName("Method should not accept a String length greater than the limit.")
        public void getStringLongerThanLimit() {

            // Prepare our own input / output
            String data = "A way to long and not acceptable Title\n An acceptable Title";
            InputStream in = new ByteArrayInputStream(data.getBytes());

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(outContent);

            // Ask for an integer using our special method
            this.getExpectedString(3, 20, in, out);

            String expected = "Please enter a string between 3 and 20 characters.";

            assertThat(outContent.toString()).contains(expected);
        }

        @Test
        @DisplayName("Method should accept an String which length is in the range.")
        public void getStringInRange() {

            // Prepare our own input / output
            String data = "An acceptable Title";
            InputStream in = new ByteArrayInputStream(data.getBytes());

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(outContent);

            // Ask for an integer using our special method
            this.getExpectedString(3, 20, in, out);

            String expected = "Please enter a String between 3 and 20 characters.";

            assertThat(outContent.toString()).doesNotContain(expected);
        }
    }
}
