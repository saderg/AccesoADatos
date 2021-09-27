import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
 
public class cmd2 {
 
    public static void main(String[] args) {
        
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("Elige una opción: " + "\n1.Enviar un ping" + "\n2.Listar datos de un directorio" + "\n3.Mostrar ayuda de un comando");
        int opcion = teclado.nextInt();
            switch(opcion){
                case 1: 
                    pingGoogle();
                break;
                case 2: 
                    listar();
                break;
                case 3: 
                    mostrarComandos();
                break;
            }
    }

    public static void pingGoogle(){
        
    ProcessBuilder processBuilder = new ProcessBuilder();
        
    Scanner scan = new Scanner(System.in);
        System.out.println("Introduce a quien vas a enviar el ping");
        String ping = scan.nextLine();
    // Run this on Windows, cmd, /c = terminate after this run
    processBuilder.command("cmd.exe", "/c", "ping -n 3 ",  ping);

    try {

        Process process = processBuilder.start();

        // blocked :(
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream(), "CP437"));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("\nExited with error code : " + exitCode);

    } catch (IOException e) {
        e.printStackTrace();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

}

    public static void listar(){

        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce la ruta del directorio que vas a utilizar");
        String directory = scan.nextLine();
        ProcessBuilder processBuilder = new ProcessBuilder();
 
        processBuilder.command("POWERSHELL", "/c", "dir");
        processBuilder.directory(new File("C:\\" , directory));
 
        try {
 
            Process process = processBuilder.start();
 
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream(), "CP437"));
 
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
 
            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
     }
    public static void mostrarComandos(){

        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce el comando sobre el que quieres ver su ayuda");
        String comando = scan.nextLine();
        
        ProcessBuilder processBuilder = new ProcessBuilder();
        
        processBuilder.command("cmd.exe", "/c", "help", comando );
 
        try {
 
            Process process = processBuilder.start();
 
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream(), "CP437"));
 
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
 
            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
    }
}
