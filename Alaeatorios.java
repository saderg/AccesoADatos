import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;


/**
 * Ejemplo de programa de escritura y lectura aleatoria de ficheros binarios - 
 * Clase RandomAccesFile: constructor RandomAccesFile(ruta /File, tipo de acceso)
 * acceso de lectura r, escritura lectura rw)
 * r - si intentamos escribir --> IOException
 * RW -si no existe el fichero se crea
 * 
 * Escribir o leer con las clases writeXXX/readXXX de DataOutputStream/DataInputStream
 * La clase RandomAccessFile maneja un puntero a la posici�n. Puntero =0 cuando el fichero se crea
 * Metodos importantes:
 * long getFilePointer() --> devuelve la posici�n actual del fichero 
 * void seek(long posicion) --> Coloca el puntero del fichero en una posicion determinada desde el comienzo del mismo
 * long lenght() -->devuelve el tama�o del fichero en bytes. la posicion length marca el final del fichero
 * int skiBytes(int desplazamiento) --> desplaza el puntero el numero de bytes indicados en desplazamiento>
 * @author Laura
 *
 */
public class Alaeatorios {

	public static void main(String[] args) throws IOException {
		 
		
		escribirAleatorio();
		
		accederUnEmpleado();
		introducirEmpleado();
		System.out.println("---------------------------------------------------------------------");
		leerAleatorio();
		
		
		//para a�adir regsitros a partir del ultimo insertado hemps de poscionar el puntero 
		//del fichero al final del mismo
//		long posicion = raf.length();
//		raf.seek(posicion);

	}

	//Consultar un empleado no es necesario recorre el fichero completo
	private static void accederUnEmpleado() throws IOException {
		int id, dep,posicion;
		Double salario;
		char apellido[] = new char[10], aux;
		System.out.println("Introduce el id del empleado");
		Scanner teclado = new Scanner(System.in);
		posicion = teclado.nextInt();
		
		int posicionFichero = (posicion-1) * 36;//numero de bytes x empleado/registro
		
		File fichero = new File("Empleados.dat");
		//Fichero de acceso aleatorio
		RandomAccessFile raf = new RandomAccessFile(fichero, "r");
		if (posicionFichero>= raf.length()) {
			System.out.println("El empleado con ID "+ posicion + " no existe");
		}else {
			raf.seek(posicionFichero);//nos  posicionamos
			
			 id = raf.readInt(); //leemos el id del empleado
			
			//recorro uno a uno los caracteres del apellido
			for (int i=0; i<apellido.length;i++) {
				aux = raf.readChar();
				apellido[i]=aux;
			}
			
			//convierto a string el array
			String apellidos = new String(apellido);
			dep = raf.readInt();
			salario = raf.readDouble();
			
			
			if(id>0) {
				System.out.println("id " + id + " apellidos " + apellidos + "departamento " + dep + " salario " + salario);
			}
		}
		
	}

	private static void escribirAleatorio() throws IOException {
		File fichero = new File("Empleados.dat");
		//Fichero de acceso aleatorio
		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
			//arrays con datos
			String[] apellido = {"FERNANDEZ","GIL","LOPEZ"};
			int[] dep = {10,20,10};
			double[] salario = {1000.45,2400.60,3000.0};
			
			StringBuffer buffer = null; //buffer para almacenar el apellido
			int n = apellido.length; //numedo de elementos del array
			for (int i=0;i<n;i++) {
				raf.writeInt(i+1);//identificador del empleado
				
				//asi nos aseguramos la longitud igual para todos los registros
				buffer = new StringBuffer(apellido[i]);
				buffer.setLength(10);//para que todos los apellidos tengan la misma longitud
				raf.writeChars(buffer.toString());//inserta apellido
			//	raf.writeUTF(apellido[i]);
				raf.writeInt(dep[i]); // inserta departamento
				raf.writeDouble(salario[i]);// inserta salario
				
			}
			
			raf.close();
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} 
		
	}
	/**
	 * Lee el fichero anterior y visualiza todos los registros. el posicionamiento para empezar
	 * a recorrer los registros empieza en 0, para recuperar los siguientes registros hay que
	 * sumar 36 (tama�o de cada regsitro) a la variable utilizada para el posicionamiento
	 * @throws IOException
	 */
	private static void leerAleatorio() throws IOException {
		File fichero = new File("Empleados.dat");
		//Fichero de acceso aleatorio
		RandomAccessFile raf = new RandomAccessFile(fichero, "r");
		int id, dep,posicion;
		Double salario;
		char apellido[] = new char[10], aux;
		
		posicion=0; //para situarnos al principio
		
		while (true) {//recorro el fichero
			raf.seek(posicion); //nos pos*icionamos en la posicion
			
			id = raf.readInt(); //leemos el id del empleado
			
			//recorro uno a uno los caracteres del apellido
			for (int i=0; i<apellido.length;i++) {
				aux = raf.readChar();
				apellido[i]=aux;
			}
			
			//convierto a string el array
			String apellidos = new String(apellido);
			dep = raf.readInt();
			salario = raf.readDouble();
			
			
			if(id>0) {
				System.out.println("id " + id + " apellidos " + apellidos + "departamento " + dep + " salario " + salario);
			}
			//me posiciono para el siguiente empleado, cada empleado ocupa 36 bytes
			posicion=posicion+36;
			
			//si he recorrido todos los bytes salgo del for
			if(raf.getFilePointer() == raf.length()) {
				break;
			}
		}
	}
	
	public static void introducirEmpleado() {
		
		
		Scanner teclado = new Scanner(System.in);
		System.out.println(" ");
		System.out.println("INTRODUCIR EMPLEADO");
		System.out.println("Introduce el ID del empleado");
		int idEmpleado = teclado.nextInt();
		
		int pos = (idEmpleado-1)*36;
		
		
		try {
		File fichero = new File("Empleados.dat");
		//Fichero de acceso aleatorio
		RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
		
		
		
		if (pos > raf.length()) {
			
			System.out.println("Introduce el apellido del empleado");
			String apellidos = teclado.next();
			
			System.out.println("Introduce el número del departamento");
			int departamento = teclado.nextInt();
			
			System.out.println("Introduce el salario del empleado");
			double salarioEmpleado = teclado.nextDouble();
			
			StringBuffer buffer = null;
			buffer = new StringBuffer(apellidos);
			buffer.setLength(10);//para que todos los apellidos tengan la misma longitud
			raf.writeChars(buffer.toString());//inserta apellido
			raf.writeUTF(apellidos);
			raf.writeInt(departamento); // inserta departamento
			raf.writeDouble(salarioEmpleado);
			}
		
		else {
			
			int id, dep,posicion;
			Double salario;
			char apellido[] = new char[10], aux;
		
			raf.seek(pos);
			
			 id = raf.readInt();
			
			for (int i=0; i<apellido.length;i++) {
				aux = raf.readChar();
				apellido[i]=aux;
			}
			
			//convierto a string el array
			String apellidos = new String(apellido);
			dep = raf.readInt();
			salario = raf.readDouble();
			
			
			if(id>0) {
				System.out.println("id " + id + " apellidos " + apellidos + "departamento " + dep + " salario " + salario);
			
			
			}
			}
		}catch(IOException e){
			e.printStackTrace();
			e.getMessage();
			
		}
		
		
	}
	
	
//CREAR UN EMPLEADO NUEVO PIDIENDO TODOS LOS DATOS Y SI EXISTE EL ID ENTONCES MENSAJE SI NO AÑADIR
}