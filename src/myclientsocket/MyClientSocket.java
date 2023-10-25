/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package myclientsocket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyClientSocket {

    public static void main(String[] args) {
        Socket socket = null;
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        String ip = "127.0.0.1";
        int port = 9999;
        Scanner inputScanner = new Scanner(System.in);

        boolean continuar = true;
        try {
            // Abre un archivo de salida para guardar el chat
            FileWriter fileWriter = new FileWriter("chat_log.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            BufferedReader reader = new BufferedReader(new FileReader("chat_log.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Imprime cada línea en la consola
            }

            while (continuar) {
                System.out.println("Por favor, introduce algo: ");
                String msgWrite = inputScanner.nextLine();

                String msgRead = null;
                socket = new Socket(ip, port);
                printWriter = new PrintWriter(socket.getOutputStream(), true);
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printWriter.println(msgWrite);
                msgRead = bufferedReader.readLine();

                Logger.getLogger(MyClientSocket.class.getName()).log(Level.INFO, "--------------------");
                Logger.getLogger(MyClientSocket.class.getName()).log(Level.INFO, msgRead);
                Logger.getLogger(MyClientSocket.class.getName()).log(Level.INFO, "--------------------");

                // Escribe los mensajes en el archivo de salida
                bufferedWriter.write("Tú: " + msgWrite + "\n");
                bufferedWriter.write("Servidor: " + msgRead + "\n");

                System.out.print("¿Deseas enviar otro mensaje? (S/N): ");
                String continuarRespuesta = inputScanner.nextLine();

                continuar = continuarRespuesta.equalsIgnoreCase("S");
            }
            reader.close();
            // Cierra el archivo de salida al final del programa
            bufferedWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(MyClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
