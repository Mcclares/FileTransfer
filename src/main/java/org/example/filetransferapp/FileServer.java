package org.example.filetransferapp;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ssl.*;

public class FileServer {
    private int port;
    private String saveDirectory;
    private volatile boolean running = true;
    private SSLServerSocket serverSocket;

    public FileServer(int port, String saveDirectory) {
        this.port = port;
        this.saveDirectory = saveDirectory;
    }

    public void start() {
        try  {
            System.out.println(STR."Server is listening on port \{port}");

            System.setProperty("javax.net.ssl.keystore", "server.keystore");
            System.setProperty("javax.net.ssl.keyStorePassword", "password");

            SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);

            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(new FileReceiverHandler(socket, saveDirectory)).start();
                } catch (IOException e) {
                    if(running) {
                        System.out.println(STR."Error accepting connection \{e.getMessage()}");
                    }else {
                        System.out.println("Server stopped");
                    }
                }

            }
        } catch (IOException e) {
            System.out.println(STR."Server error: \{e.getMessage()}");
        }
    }

    public void stop() {
        running = false;
        if(serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();

            }catch (IOException e) {
                System.out.println(STR."Error closing server socket: \{e.getMessage()}");
            }
        }
    }

    private static class FileReceiverHandler implements Runnable {
        private final Socket socket;
        private String saveDirectory;

        public FileReceiverHandler(Socket socket, String saveDirectory) {
            this.socket = socket;
            this.saveDirectory = saveDirectory;
        }
        public void setSaveDirectory(String saveDir) {
            saveDirectory = saveDir;
        }

        @Override
        public void run() {
            try (InputStream in = socket.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {

                String command = reader.readLine();
                if (command.startsWith("UPLOAD")) {
                    String[] parts = command.split(" ");
                    String fileName = parts[1];

                    File file = new File(saveDirectory, fileName);
                    try{
                        BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(file));
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            fileOut.write(buffer, 0, bytesRead);
                        }

                    } catch (Exception e) {
                        throw new RuntimeException("Error in fileOut variable");
                    }

                    System.out.println(STR."File received and saved: \{file.getAbsolutePath()}");
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

//    public static void main(String[] args) {
//        FileServer server = new FileServer(12345, "C:/path/to/save/directory");
//        server.start();
//    }
}