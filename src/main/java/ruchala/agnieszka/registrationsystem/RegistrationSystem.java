package ruchala.agnieszka.registrationsystem;

import java.io.*;

public class RegistrationSystem {

    public static void main(String[] args) {
        try {
            RegistrationSystem registrationSystem = new RegistrationSystem("data.txt");
            registrationSystem.addUser("Marcin", 30);
            registrationSystem.addUser("Tomek", 20);
            registrationSystem.deleteUser("Marcin");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private final File file;
    private final File tmpFile;

    public RegistrationSystem(String fileName) throws IOException {
        file = new File(fileName);
        tmpFile = new File("tmp_" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    void addUser(String name, int age) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            if (name.contains("\t") || name.contains("\n")) {
                throw new IllegalArgumentException("Name contains illegal characters");
            }
            writer.write(name + "\t" + Integer.toString(age) + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void deleteUser(String name) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter tmpWriter = new BufferedWriter(new FileWriter(tmpFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitted = line.split("\t");
                if (!splitted[0].equals(name)) {
                    tmpWriter.write(line + "\n");
                }
            }
            reader.close();
            tmpWriter.close();
            file.delete();
            tmpFile.renameTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
