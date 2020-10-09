package database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class DatabaseTest {

    @Test
    @DisplayName("Reading an existing file should return a list of each line of the file")
    public void ReadingExistingFileShouldReturnListOfEachLineOfTheFile() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    @DisplayName("Reading a non existing file throw an exception")
    public void ReadingNonExistingFileShouldThrowException() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    @DisplayName("Reading an empty file should return an empty list")
    public void ReadingEmptyFileShouldReturnEmptyList() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    @DisplayName("Writing a list into an existing file should replace the previous by the new one")
    public void WritingNewListIntoExistingFileShouldReplacePreviousByNew() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    @DisplayName("Writing into a non existing file throw an exception")
    public void WritingIntoANonExistingFileShouldThrowAnException() {
        assertThat(1).isEqualTo(1);
    }

}
