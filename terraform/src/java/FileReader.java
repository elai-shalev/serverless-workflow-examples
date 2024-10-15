package io.janus.workflow.Terraform;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;

@ApplicationScoped
public class FileReader {
    public byte[] ReadFile(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath)); 
        } catch (IOException e) { 
            throw new RuntimeException("Error: could not read file", e); 
        }
    }
}