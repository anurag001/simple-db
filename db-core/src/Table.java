package src;

import java.io.*;

public class Table {
    private String tableName;
    private BPlusTree index;
    private RandomAccessFile dataFile;

    public Table(String tableName) throws IOException {
        this.tableName = tableName;
        this.index = new BPlusTree(2); // Order 2 (Min 1, Max 3 keys per node)
        this.dataFile = new RandomAccessFile("data/" + tableName + ".data", "rw");
    }

    public void insert(int id, String name, int age) throws IOException {
        long offset = dataFile.length();
        dataFile.seek(offset);
        dataFile.writeInt(id);
        dataFile.writeUTF(name);
        dataFile.writeInt(age);
        index.insert(id); //Inserting in BTree+
    }

    public String search(int id) throws IOException {
        if (index.search(id)) {
            dataFile.seek(0);
            while (dataFile.getFilePointer() < dataFile.length()) {
                int currentId = dataFile.readInt();
                String name = dataFile.readUTF();
                int age = dataFile.readInt();
                if (currentId == id) {
                    return "ID: " + currentId + ", Name: " + name + ", Age: " + age;
                }
            }
        }
        return "Record not found.";
    }

    public void close() throws IOException {
        dataFile.close();
    }
}
