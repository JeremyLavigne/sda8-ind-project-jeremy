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
    private static GUI testableGUI;

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public static void setUp() {
        // Create a fake GUI here.
        Account acc = new Account(new ArrayList<>());
        Database db = new Database("assets/testFile.txt");
        testableGUI = GuiActionRunner.execute(() -> new GUI(acc, db));

        window = new FrameFixture(testableGUI);
        //window.show();
    }


    @Test
    @DisplayName("Window is open in its original size")
    public void windowOpenOriginalSize() {
        testableGUI.start();

        window.isEnabled();
        window.requireSize(new Dimension(800, 600));
    }

    @AfterEach
    public static void tearDown() {
        window.cleanUp();
    }
}
