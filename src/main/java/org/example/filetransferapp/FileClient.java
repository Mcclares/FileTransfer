package org.example.filetransferapp;
import java.io.*;
import java.net.Socket;
import javax.net.ssl.*;

public class FileClient {
    private String serverAddress;
    private int serverPort;

    public FileClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void sendFile(String filePath) {
        try {

             SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
             SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(serverAddress,serverPort);


             FileInputStream fileIn = new FileInputStream(filePath);
             BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
             OutputStream out = socket.getOutputStream();

             PrintWriter writer = new PrintWriter(out,true);
             File file = new File(filePath);
             writer.println(STR."UPLOAD \{file.getName()}");

             byte[] buffer = new byte[4096];
             int bytesRead;
             while((bytesRead = bufferedIn.read(buffer)) != -1) {
                 out.write(buffer,0,bytesRead);
             }
             out.flush();
             System.out.println(STR."File sent \{filePath}");

             bufferedIn.close();
             socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}