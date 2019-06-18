package com.sockets.chat.presentacion;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sockets.chat.logica.Controlador;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Principal extends JFrame{
	
	private JTextField txtEscribir;
	private JTextArea txtRecibido;
	private Controlador cnt;
	
	public Principal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				cnt.cerrar();
			}
		});
		
		setBounds(0, 0, 500,500);
		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBounds(0, 0, 500,500);
		setContentPane(contentPane);
		
		txtEscribir = new JTextField();
		txtEscribir.setBounds(101, 13, 280, 34);
		getContentPane().add(txtEscribir);
		txtEscribir.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cnt.enviar(txtEscribir.getText());
			}
		});
		btnEnviar.setBounds(192, 68, 97, 25);
		contentPane.add(btnEnviar);
		
		txtRecibido = new JTextArea();
		txtRecibido.setBounds(54, 136, 374, 264);
		contentPane.add(txtRecibido);
		
		setLocationRelativeTo(null);
		cnt = new Controlador(this.txtRecibido);
	}
}
