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

    public String ReadAndUploadTerraformConfig(String filePath, String archiveURL) {

        // byte[] file = ReadConfigFile(filePath);
        String res = Try(archiveURL, filePath);
        return res;

    }

    /*
     * public byte[] ReadConfigFile(String filePath) {
     * try {
     * byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
     * return fileBytes;
     * } catch (IOException e) {
     * throw new RuntimeException("Error: could not read file", e);
     * }
     * }
     * 
     * public boolean UploadConfigFile(byte[] fileBytes) {
     * HttpRequest request = HttpRequest.newBuilder().PUT(null)
     * .uri(archiveURL)
     * .timeout(Duration.ofMinutes(2))
     * .header("Content-Type", "application/octet-stream")
     * .PUT(BodyPublishers.ofFile(Paths.get("file.json")))
     * .build();
     * client.sendAsync(request, BodyHandlers.ofString())
     * .thenApply(HttpResponse.body)
     * .thenAccept(System.out.println);
     * return true;
     * }
     */

    public String Try(String archiveURL, String filePath) {

        try {
            URI uri = new URI(archiveURL);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/octet-stream");

            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            FileInputStream fileInputStream = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytesRead);
            }

            dataOutputStream.flush();
            dataOutputStream.close();
            fileInputStream.close();

            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Success");
            }
            return "";
        }

        catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

}