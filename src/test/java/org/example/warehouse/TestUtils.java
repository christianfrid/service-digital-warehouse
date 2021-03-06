package org.example.warehouse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public class TestUtils {

    private static final String TEST_PRODUCTS_FILENAME = "products.json";
    private static final String TEST_INVENTORY_FILENAME = "inventory.json";

    public static MultipartFile createProductsTestFile() throws IOException {
        File file = new File("src/test/java/resources/" + TEST_PRODUCTS_FILENAME);
        FileInputStream input = new FileInputStream(file);
        return new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

    }

    public static MultipartFile createInventoryTestFile() throws IOException {
        File file = new File("src/test/java/resources/" + TEST_INVENTORY_FILENAME);
        FileInputStream input = new FileInputStream(file);
        return new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

    }
}
