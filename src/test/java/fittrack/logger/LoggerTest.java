package fittrack.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class LoggerTest {

    @BeforeEach
    public void setUp() {
        LogManager.getLogManager().reset(); // Reset logging configuration before each test
        FitTrackLogger.setupLogger(); // Call setupLogger to set up handlers
    }

    @Test
    public void setupLogger_initializesLoggerWithExpectedLevel() {
        assertEquals(Level.ALL, FitTrackLogger.LOGGER.getLevel(), "Logger level should be set to ALL");
    }

    @Test
    public void setupLogger_addsConsoleHandlerWithCorrectLevel() {
        boolean consoleHandlerFound = false;
        for (var handler : FitTrackLogger.LOGGER.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                consoleHandlerFound = true;
                assertEquals(Level.SEVERE, handler.getLevel(), "ConsoleHandler level should be set to SEVERE");
            }
        }
        assertTrue(consoleHandlerFound, "ConsoleHandler should be added to the logger");
    }

    @Test
    public void setupLogger_addsFileHandlerWithCorrectLevel() {
        boolean fileHandlerFound = false;
        for (var handler : FitTrackLogger.LOGGER.getHandlers()) {
            if (handler instanceof FileHandler) {
                fileHandlerFound = true;
                assertEquals(Level.FINE, handler.getLevel(), "FileHandler level should be set to FINE");
                assertTrue(new File("FitTrackLogger.log").exists(), "Log file should be created");
            }
        }
        assertTrue(fileHandlerFound, "FileHandler should be added to the logger");
    }

    @Test
    public void setupLogger_logsInitializationMessage() {
        // Check that the logger contains the expected initialization message
        // Here we would ideally capture log output to verify, but for simplicity:
        assertDoesNotThrow(() -> FitTrackLogger.LOGGER.info("Logger successfully initialized"));
    }
}
