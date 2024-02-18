package com.mycompany.file.handling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandling {
    final static String FILENAME = "names.txt";
    public static void main(String[] args) {
        int choice;
        String name;
        while (true) {
            menue();
            Scanner input = new Scanner(System.in);
            choice = input.nextInt();
            input.nextLine();
            switch(choice) {
                case 1:
                    System.out.print("Enter the name: ");
                    name = input.nextLine();
                    writeInFile(FILENAME, name);
                    break;
                
                case 2:
                    readFromFile(FILENAME, true);
                    break;
                    
                case 3:  
                    System.out.print("Enter the name to search: ");
                    name = input.nextLine();
                    searchFromFile(FILENAME, name, true);
                    break;
                
                case 4:  
                    String newName;
                    System.out.print("Enter the name to edit: ");
                    name = input.nextLine();
                    System.out.print("Enter the new name: ");
                    newName = input.nextLine();
                    editInFIle(FILENAME, name, newName);
                    break;
                    
                case 5:
                    System.out.print("Enter the name to delete: ");
                    name = input.nextLine();
                    deleteFromFIle(FILENAME, name);
                    break;
                    
                case 6:
                    System.exit(0);
                    
                default:
                    System.out.println("Invalid input!!!");
            }
        }
    }
    
    private static void menue() {
        System.out.println("1. | Write name in file");
        System.out.println("2. | Read names from file");        
        System.out.println("3. | Search name from file");
        System.out.println("4. | Edit name");        
        System.out.println("5. | Delete name");
        System.out.println("6. | Quit");
        System.out.print("Enter your choice: ");        
    }
    
    private static void writeInFile(String file, String name) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            
            bw.write(name);
            bw.newLine();
        }
        catch (IOException error) {
            System.out.println("Error while writing name in " + file + ". ERR: " + error);
        }
        finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            }
            catch(IOException error) {
                System.out.println("Error in closing " + file + ". ERR: " + error);
            }
            try {
                if (fw != null) {
                    fw.close();
                }
            }
            catch(IOException error) {
                System.out.println("Error in closing " + file + ". ERR: " + error);
            }
        }
    }
    
    private static String[] readFromFile(String file, boolean shouldPrint) {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            int numLines = 0;
            while (br.readLine() != null) {
                numLines++;
            }

            String[] allNames = new String[numLines];

            int itr = 0;
            String name;

            br.close();

            fr = new FileReader(file);
            br = new BufferedReader(fr);

            while ((name = br.readLine()) != null) {
                if (shouldPrint) System.out.println(name);
                allNames[itr++] = name;
            }

            return allNames;
        } catch (IOException error) {
            System.out.println("Error while reading names from " + file + ". ERR: " + error);
            return null;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException error) {
                System.out.println("Error in closing " + file + ". ERR: " + error);
            }
        }
    }
    
    private static String searchFromFile(String file, String query, boolean shouldPrint) {
        FileReader fr = null;
        BufferedReader br = null;
        
        String name;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            
            while ((name = br.readLine()) != null) {
                if (name.toLowerCase().equals(query.toLowerCase())) {
                    if (shouldPrint) System.out.println(name);
                    return name;
                }
                String nameSplit[] = name.toLowerCase().split(" ");
                for (int i = 0; i < nameSplit.length; i++) {
                    if (nameSplit[i].equals(query.toLowerCase())) {
                        if (shouldPrint) System.out.println(name);
                        return name;
                    }
                }
            }
            System.out.println("No record found.");
        } catch(IOException error) {
            System.out.println("Error while searching name from " + file + ". ERR: " + error);
        }
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch(IOException error) {
                System.out.println("Error while closing file" + file + ". ERR: " + error);
            }
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch(IOException error) {
                System.out.println("Error while closing file" + file + ". ERR: " + error);
            }
        }
        return "";
    }
    
    private static void editInFIle(String file, String with, String from) {
        boolean isWordFound = false;
        String data[] = readFromFile(file, false);
        String targetName = searchFromFile(file, with, false);

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals(targetName)) {
                isWordFound = true;
                data[i] = from;
            }
        }
        
        if (!isWordFound) {
            System.out.println("No record found.");
            return;
        }
        
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            
            for (int i = 0; i < data.length; i++) {
                bw.write(data[i]);
                bw.newLine();
            }
        }
        catch (IOException error) {
            System.out.println("Error while writing name in " + file + ". ERR: " + error);
        }
        finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            }
            catch(IOException error) {
                System.out.println("Error in closing " + file + ". ERR: " + error);
            }
            try {
                if (fw != null) {
                    fw.close();
                }
            }
            catch(IOException error) {
                System.out.println("Error in closing " + file + ". ERR: " + error);
            }
        }
        System.out.println("Name updated successfully!");
    }
    
    private static void deleteFromFIle(String file, String name) {
        boolean isWordFound = false;
        String data[] = readFromFile(file, false);
        String targetName = searchFromFile(file, name, false);

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals(targetName)) {
                isWordFound = true;
                data[i] = "";
            }
        }
        
        if (!isWordFound) {
            System.out.println("No record found.");
            return;
        }
        
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            
            for (int i = 0; i < data.length; i++) {
                if (!data[i].equals("")) {
                    bw.write(data[i]);
                    bw.newLine();
                }
            }
        }
        catch (IOException error) {
            System.out.println("Error while writing name in " + file + ". ERR: " + error);
        }
        finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            }
            catch(IOException error) {
                System.out.println("Error in closing " + file + ". ERR: " + error);
            }
            try {
                if (fw != null) {
                    fw.close();
                }
            }
            catch(IOException error) {
                System.out.println("Error in closing " + file + ". ERR: " + error);
            }
        }
        System.out.println("Name deleted successfully!");
    }
}
