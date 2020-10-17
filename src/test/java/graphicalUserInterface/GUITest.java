package graphicalUserInterface;

import database.Database;
import main.Account;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.util.ArrayList;


public class GUITest {
    private static FrameFixture window;

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeAll
    public static void setUp() {
        // Create a fake GUI here.
        Account acc = new Account(new ArrayList<>());
        Database db = new Database("assets/testFile.txt");
        Frame frame = GuiActionRunner.execute(() -> new GUI(acc, db));

        window = new FrameFixture(frame);
        //window.show();
    }


    @Test
    @DisplayName("Window is open in its original size")
    public void windowOpenOriginalSize() {
        window.isEnabled();
        window.requireSize(new Dimension(800, 600));
    }

    @AfterAll
    public static void tearDown() {
        window.cleanUp();
    }
}
