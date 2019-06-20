package edu.udistrital.hilosLienzo.logica;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Controlador implements Runnable{
	
	private ServerSocket serverSocket;
	private String remoteHost = "192.168.0.104";
	private int remotePort = 5046;
	private int localPort = 5041;
	private DataInputStream flujo_entrada;
	private Lienzo target;
	private Thread hiloServer;

	
	public Controlador(Lienzo target) {
		this.target = target;
		//Parte servidor
		habilitarServer();
		hiloServer = new Thread(this);
		hiloServer.start();
	}
	
	public void cerrar() {
	}
	
	private void escuchar() {
		try {
			while(true) {
				Socket connectionSocket = this.serverSocket.accept();
				this.flujo_entrada = new DataInputStream(connectionSocket.getInputStream());
				String msj = this.flujo_entrada.readUTF();
				String[] cadena = msj.split(" ");
				this.target.drawPoint(Integer.parseInt(cadena[0]),Integer.parseInt(cadena[1]), Color.red);
				System.out.println("Recibo");
			}
		} catch (IOException e) {
			System.out.println("Error al intentar escuchar en el puerto "+this.localPort+" :"+e.getMessage());
			System.exit(0);
		}
	}
	
	private void habilitarServer() {
		//se habilita el puerto especificado
		try {
			this.serverSocket = new ServerSocket(this.localPort);
			System.out.println("Puerto local abierto");
		} catch (IOException e) {
			System.out.println("Error al intentar abrir el puerto "+this.localPort+" :"+e.getMessage());
			System.exit(0);
		}
	}

	public void enviar(int x, int y) {
		try {
			Socket miSocket = new Socket(this.remoteHost,this.remotePort);
			DataOutputStream dos = new DataOutputStream(miSocket.getOutputStream());
			dos.writeUTF(String.valueOf(x)+" "+String.valueOf(y));
			miSocket.close();
		} catch (IOException e) {
			System.out.println("Error al enviar mensaje: "+e.getMessage());
		}
	}

	@Override
	public void run() {
		escuchar();
	}
}
