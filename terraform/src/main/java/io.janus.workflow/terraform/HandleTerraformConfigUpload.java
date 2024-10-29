package io.janus.workflow.terraform;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//import java.net.http.BodyPublishers;
//import java.net.http.BodyHandlers;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URI;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@ApplicationScoped
public class HandleTerraformConfigUpload {
    public int UploadTerraformConfig(String configFilePath, String archiveURL) {

        try {
            URI uri = new URI(archiveURL);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/octet-stream");

            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            FileInputStream fileInputStream = new FileInputStream(configFilePath);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytesRead);
            }

            dataOutputStream.flush();
            dataOutputStream.close();
            fileInputStream.close();

            return connection.getResponseCode();
        }

        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

}