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
                    readFromFile(FILENAME);
                    break;
                    
                case 3:  
                    System.out.print("Enter the name to search: ");
                    name = input.nextLine();
                    searchFromFile(FILENAME, name);
                    break;
                    
                case 4:
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
        System.out.println("4. | Quit");
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
    
    private static void readFromFile(String file) {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            
            String name;
            
            while ((name = br.readLine()) != null) {
                System.out.println(name);
            }            
        }
        catch (IOException error) {
            System.out.println("Error while reading names from " + file + ". ERR: " + error);
        }
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            }
            catch(IOException error) {
                System.out.println("Error in closing " + file + ". ERR: " + error);
            }
            try {
                if (fr != null) {
                    fr.close();
                }
            }
            catch(IOException error) {
                System.out.println("Error in closing " + file + ". ERR: " + error);
            }
        }
    }
    
    private static void searchFromFile(String file, String query) {
        FileReader fr = null;
        BufferedReader br = null;
        
        String name;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            
            while ((name = br.readLine()) != null) {
                if (name.toLowerCase().equals(query.toLowerCase())) {
                    System.out.println(name);
                    return;
                }
                String nameSplit[] = name.toLowerCase().split(" ");
                for (int i = 0; i < nameSplit.length; i++) {
                    if (nameSplit[i].equals(query.toLowerCase())) {
                        System.out.println(name);
                        return;
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
    }
}
