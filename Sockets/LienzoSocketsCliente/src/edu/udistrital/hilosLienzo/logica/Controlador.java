package edu.udistrital.hilosLienzo.logica;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Controlador implements Runnable{
	
	private Socket socket; //parte cliente
	private ServerSocket servidor; //parte servidor
	private DataOutputStream flujo_salida; //Flujo de datos de salida
	private DataInputStream flujo_entrada; //Flujo de datos de entrada
	
	private int puertoLocal;
	private int puertoRemoto;
	private InetAddress ipLocal;
	private InetAddress ipRemota;
	
	private Lienzo lienzo;
	
	private static Controlador instance;
	
	public static Controlador getInstance() throws IOException {
		if(instance==null) {
			instance = new Controlador();
		}
		return instance;
	}
	
	private Controlador() throws IOException{
		String direccionLocal = JOptionPane.showInputDialog("Digite la dirección IP local: ");
		this.puertoLocal = Integer.parseInt(JOptionPane.showInputDialog("Digite el número del puerto local: "));
		String direccionRemota = JOptionPane.showInputDialog("Digite la dirección IP remota: ");
		this.puertoRemoto = Integer.parseInt(JOptionPane.showInputDialog("Digite el numero del puerto remoto: "));
		this.ipLocal = InetAddress.getByName(direccionLocal);
		this.ipRemota = InetAddress.getByName(direccionRemota);
		
		//parte cliente
		Thread hiloCliente = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("Intentando establecer conexion...");
					socket = new Socket(ipRemota, puertoRemoto, ipLocal, puertoLocal);
					System.out.println("Conexion establecida");
					//salida
					crearFlujoSalida(socket.getOutputStream());
				} catch (IOException e) {
					System.out.println("Error de red: "+e.getMessage());
					run();
					System.out.println("Intentando de nuevo...");
				}
				
			}
		});
		hiloCliente.start();
		
		//Parte servidor
		//El controlador escuchará por el puerto en un proceso que se ejecutará en segundo plano
		Thread hiloServidor = new Thread(this);
		hiloServidor.start();
		
	}
	
	public void setLienzo(Lienzo lienzo) {
		this.lienzo = lienzo;
	}
	
	public boolean enviar(int x, int y) {
		try {
			this.flujo_salida.writeUTF(x+" "+y);
			this.flujo_salida.close();
		} catch (IOException e) {
			System.out.println("Error en el envío de datos: "+e.getMessage());
			return false;
		}
		return true;
	}
	
	private void crearFlujoSalida(OutputStream os) throws IOException {
		flujo_salida = new DataOutputStream(os); //El flujo de datos de salida se hará mediante el socket
	}
	
	private void crearFlujoEntrada(InputStream is) throws IOException {
		flujo_entrada = new DataInputStream(is); //El flujo de datos de entrada se hará mediante el socket
	}
	
	private void escuchar() throws IOException {
		//Parte servidor
		System.out.println("Iniciando escucha en puerto "+this.puertoLocal+"...");
		this.servidor = new ServerSocket(this.puertoLocal); //Se abre el puerto y se pone a escuchar en dicho puerto
		while(true) {
			Socket socket = this.servidor.accept(); //Se aceptan las conecciones que vengan del exterior en el puerto dado 
			//entrada
			crearFlujoEntrada(socket.getInputStream());
			
			String entrada = flujo_entrada.readUTF(); //Leemos lo que entre por el flujo de entrada
			String[] cadena = entrada.split(" ");
			int x = Integer.parseInt(cadena[0]);
			int y = Integer.parseInt(cadena[1]);
			lienzo.drawPoint(x, y, Color.RED); //se recibe y se pinta sobre el lienzo
			
			socket.close();
		}
	}

	@Override
	public void run() {
		try {
			escuchar();
		} catch (IOException e) {
			System.out.println("Error al intentar escuchar: "+e.getMessage());
		}
	}
}
