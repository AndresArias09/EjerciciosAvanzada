package com.sockets.chat.logica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import javax.swing.text.JTextComponent;


public class Controlador implements Runnable{
	
	private Socket socket;
	private ServerSocket serverSocket;
	private String remoteHost = "192.168.0.104";
	private int remotePort = 5046;
	private int localPort = 5041;
	private DataInputStream flujo_entrada;
	private DataOutputStream flujo_salida;
	private JTextComponent target;
	private Thread hiloServer;

	
	public Controlador(JTextComponent target) {
		this.target = target;
		//Parte servidor
		habilitarServer();
		hiloServer = new Thread(this);
		hiloServer.start();
		//Parte cliente
		conectar();
	}
	
	public void cerrar() {
	}
	
	private void escuchar() {
		try {
			while(true) {
				Socket connectionSocket = this.serverSocket.accept();
				this.flujo_entrada = new DataInputStream(connectionSocket.getInputStream());
				String msj = this.flujo_entrada.readUTF();
				this.target.setText(this.target.getText()+"\n"+msj);
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
	
	private void conectar() {
		try {
			System.out.println("Intentando establecer conexion");
			this.socket = new Socket(this.remoteHost, this.remotePort);
			this.socket.setSoTimeout(10000);
			System.out.println("Conexion establecida");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al intentar conectarse al host remoto: "+e.getMessage());
			conectar();
			//System.exit(0);
		}
	}
	
	public void enviar(String msj) {
		try {
			this.flujo_salida = new DataOutputStream(this.socket.getOutputStream());
			this.flujo_salida.writeUTF(msj);		
			this.flujo_salida.close();
		} catch (IOException e) {
			System.out.println("Error al enviar mensaje: "+e.getMessage());
		}
	}

	@Override
	public void run() {
		escuchar();
	}

}
