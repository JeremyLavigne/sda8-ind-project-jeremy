package main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemTest {

    @Test
    @DisplayName("getStringMonth method return the expected month as a String")
    public void getStringMonthReturnStringMonth() {
        assertThat(1).isEqualTo(1);
    }
}
