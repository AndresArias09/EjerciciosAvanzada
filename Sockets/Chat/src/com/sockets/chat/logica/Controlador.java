package com.sockets.chat.logica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;


public class Controlador implements Runnable{
	
	private ServerSocket serverSocket;
	private String remoteHost = "192.168.0.104";
	private int remotePort = 5046;
	private int localPort = 5041;
	private DataInputStream flujo_entrada;
	private JTextComponent target;
	private Thread hiloServer;

	
	public Controlador(JTextComponent target) {
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

	public void enviar(String msj) {
		try {
			Socket miSocket = new Socket(this.remoteHost,this.remotePort);
			DataOutputStream dos = new DataOutputStream(miSocket.getOutputStream());
			dos.writeUTF(msj);
			miSocket.close();
		} catch (IOException e) {
			System.out.println("Error al enviar mensaje: "+e.getMessage());
			JOptionPane.showMessageDialog(null,"Host remoto no conectado");
		}
	}

	@Override
	public void run() {
		escuchar();
	}

}
