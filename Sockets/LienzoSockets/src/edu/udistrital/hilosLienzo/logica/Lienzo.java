package edu.udistrital.hilosLienzo.logica;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;



public class Lienzo extends Canvas{
	
	private Image imgBuffered;
	private Graphics2D g;
	private Controlador cnt;
	
	public Lienzo(int x,int y,int width,int height) {
		cnt = new Controlador(this);
		this.setBounds(x, y, width, height);
		imgBuffered = new BufferedImage(width,height,BufferedImage.TRANSLUCENT);
		this.setBackground(Color.white); //Color de fondo del lienzo
		this.g = (Graphics2D)imgBuffered.getGraphics();
		setFocusable(true);
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				drawPoint(e.getX(), e.getY(),Color.blue);
				Thread hilo = new Thread(new Runnable() {
					
					@Override
					public void run() {
						cnt.enviar(e.getX(), e.getY());

					}
				});
			hilo.start();
				
			}
		});
		
	}
	
	public void paint(Graphics g) {
		g.drawImage(imgBuffered, 0, 0, this);
	}
	
	public void drawPoint(int x,int y,Color color) {
		this.g.setColor(color); //Color con el que se pintará sobre el lienzo
		this.g.drawLine(x, y, x, y);
		repaint();
	}
	
}
