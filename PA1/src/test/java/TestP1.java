
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;


public class TestP1 {
P1 object = new P1();

// Tests if file exists
@Test
    void testFileNotFound() {
        File nonExistentFile = new File("nonexistent.txt");
        assertThrows(FileNotFoundException.class, () -> P1.main(new String[]{nonExistentFile.getPath()}));
    }

// Tests if file is empty
@Test
    void testEmptyFile() throws FileNotFoundException {
        String testInput = ""; // empty file
        System.setIn(new ByteArrayInputStream(testInput.getBytes()));

        File testFile = new File("testFile.txt");
        assertThrows(RuntimeException.class, () -> P1.main(new String[]{testFile.getPath()}));
    }

// Tests if 
@Test
    void testNonNumericData() throws FileNotFoundException {
        String testInput = "2.5\nabc\n1.8\n"; // file with non-numeric data
        System.setIn(new ByteArrayInputStream(testInput.getBytes()));

        File testFile = new File("testFile.txt");
        assertThrows(NumberFormatException.class, () -> P1.main(new String[]{testFile.getPath()}));
    }
}
