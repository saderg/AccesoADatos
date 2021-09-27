import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class GUI extends JFrame implements ActionListener {
    //Variables Absolutas para la clase.
    private JLabel texto;
    private JTextField caja;
    private JButton boton;
    private JButton boton2;
    private String mensaje;
    private String mensaje2;
    private JLabel texto2;
    private JLabel texto3;
    private JTextField caja2;
    private JComboBox eleccion;
    private Color color = new Color(86, 186, 145);
    private Color color2 = new Color(140, 213, 183);
    private Color color3 = new Color(0, 0, 0);
    
    public GUI() {
        super();
        configurarGUI();
        inicializarComponentes();
 
    }

    private void configurarGUI() {
      
        this.setTitle("Ventana de opciones");                   // colocamos titulo a la ventana
        this.setSize(500, 400);                                // colocamos tamanio a la ventana (ancho, alto)
        this.setLocationRelativeTo(null);                       // centramos la ventana en la pantalla
        this.setLayout(null);                                   // no usamos ningun layout, solo asi podremos dar posiciones a los componentes
        this.setResizable(true);                               // hacemos que la ventana no sea redimiensionable
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // hacemos que cuando se cierre la ventana termina todo proceso
        this.setVisible(true);
        this.getContentPane().setBackground(color2);    
    
    }

    private void inicializarComponentes() {
       

        texto = new JLabel();
        texto2 = new JLabel();
        texto3 = new JLabel();
        caja = new JTextField();
        caja2 = new JTextField();
        eleccion = new JComboBox();
        boton = new JButton();
        boton2 = new JButton();

        

        texto2.setText("Selecciona terminal");    // colocamos un texto a la etiqueta
        texto2.setBounds(50, 20, 200, 25);   // colocamos posicion y tamanio al texto (x, y, ancho, alto)

        eleccion.setBounds(220, 20, 200, 25);   // colocamos posicion y tamanio a la caja (x, y, ancho, alto);
        eleccion.addItem(" ");
        eleccion.addItem("cmd");
        eleccion.addItem("powershell");


        texto.setText("Inserte comando");    // colocamos un texto a la etiqueta
        texto.setBounds(50, 100, 100, 25);   // colocamos posicion y tamanio al texto (x, y, ancho, alto)
        caja.setBounds(220, 100, 200, 25);   // colocamos posicion y tamanio a la caja (x, y, ancho, alto)

        
        boton.setText("Mostrar ayuda");   // colocamos un texto al boton
        boton.setBounds(130, 140, 200, 30);  // colocamos posicion y tamanio al boton (x, y, ancho, alto)
        boton.addActionListener(this);      // hacemos que el boton tenga una accion y esa accion estara en esta clase
        


        texto3.setText("Introduce la dirección");
        texto3.setBounds(50, 200, 150, 25);
        caja2.setBounds(220, 200, 200, 25);

        boton2.setText("Hacer un ping");   // colocamos un texto al boton
        boton2.setBounds(130, 240, 200, 30);  // colocamos posicion y tamanio al boton (x, y, ancho, alto)
        boton2.addActionListener(this);      // hacemos que el boton tenga una accion y esa accion estara en esta clase


        eleccion.setBorder(BorderFactory.createLineBorder(color3));
        eleccion.setBackground(color);
        boton.setBackground(color);
        boton2.setBackground(color);
        caja.setBackground(color);
        caja2.setBackground(color);
        caja.setBorder(BorderFactory.createLineBorder(color3));
        caja2.setBorder(BorderFactory.createLineBorder(color3));
        boton.setBorder(BorderFactory.createLineBorder(color3));
        boton2.setBorder(BorderFactory.createLineBorder(color3));
        

        this.add(texto);
        this.add(caja);
        this.add(boton);
        this.add(boton2);
        this.add(texto2);
        this.add(eleccion);
        this.add(texto3);
        this.add(caja2);

    }

    @Override

    public void actionPerformed(ActionEvent e) {
       

        if (e.getSource() == boton){
            help();
            JOptionPane.showMessageDialog(null, mensaje, "Ventana de ayuda", EXIT_ON_CLOSE );
        } else if(e.getSource() == boton2){
            ping();
            JOptionPane.showMessageDialog(null, mensaje2, "Ping", EXIT_ON_CLOSE );
        }
     
    }
    
    public void help(){

        String consola = (String) eleccion.getSelectedItem();
        String comando = caja.getText();         
        ProcessBuilder processBuilder = new ProcessBuilder();
         
        processBuilder.command(consola, "/c", "help" , comando);
        
        
        try {
 
            Process process = processBuilder.start();
           
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream(), "CP437"));
 
            String line;
            while ((line = reader.readLine()) != null) {
                mensaje = mensaje + line + "\n";
            }
 
            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
 
        } catch (IOException d) {
            d.printStackTrace();
        } catch (InterruptedException d) {
            d.printStackTrace();
        }
    }
   
    public void ping(){
        ProcessBuilder processBuilder = new ProcessBuilder();
        
        String consola = (String) eleccion.getSelectedItem();
        String ping = caja2.getText();
    // Comando para arrancar en windows
    processBuilder.command(consola, "/c", "ping -n 3 ",  ping);

    try {
        //Ejecutamos el proceso con .start()
        Process process = processBuilder.start();

        // blocked :(
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream(), "CP437"));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            mensaje2 = mensaje + line + "\n";
        }

        int exitCode = process.waitFor();
        System.out.println("\nExited with error code : " + exitCode);

    } catch (IOException e) {
        e.printStackTrace();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    }
    public static void main(String[] args) {
        
        GUI usuario = new GUI(); 

    }

}

