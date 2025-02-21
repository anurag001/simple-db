 package src;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class DatabaseServer {
    private static final int PORT = 3000;
    private static HashMap<String, Table> tables = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Database server started on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new ClientHandler(clientSocket).start();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String query;
                while ((query = in.readLine()) != null) {
                    System.out.println("Received: " + query);
                    String[] parts = query.split(" ");
                    String command = parts[0].toUpperCase();

                    switch (command) {
                        case "CREATE":
                            String tableName = parts[1];
                            tables.put(tableName, new Table(tableName));
                            out.println("Table " + tableName + " created.");
                            break;

                        case "INSERT":
                            tableName = parts[1];
                            int id = Integer.parseInt(parts[2]);
                            String name = parts[3];
                            int age = Integer.parseInt(parts[4]);
                            if (tables.containsKey(tableName)) {
                                tables.get(tableName).insert(id, name, age);
                                out.println("Inserted into " + tableName);
                            } else {
                                out.println("Table not found.");
                            }
                            break;

                        case "SELECT":
                            tableName = parts[1];
                            int searchId = Integer.parseInt(parts[2]);
                            if (tables.containsKey(tableName)) {
                                String result = tables.get(tableName).search(searchId);
                                out.println(result);
                            } else {
                                out.println("Table not found.");
                            }
                            break;

                        default:
                            out.println("Invalid command.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
