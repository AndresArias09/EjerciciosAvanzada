package edu.udistrital.hilosLienzo.presentacion;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.udistrital.hilosLienzo.logica.Lienzo;
import java.awt.Color;

public class Principal extends JFrame {

	private JPanel contentPane;
	private Lienzo lienzo;

	public static void main(String[] args) {
		Principal frame = new Principal();
	}


	public Principal() {
		setResizable(false);
		createFrame();
		lienzo = new Lienzo(0, 0, this.getWidth(), this.getHeight());
		contentPane.add(lienzo);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void createFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 575, 559);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
