package edu.udistrital.hilosLienzo.logica;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;


public class Lienzo extends Canvas{
	
	Image imgBuffered;
	Graphics2D g;
	
	public Lienzo(int x,int y,int width,int height) {
		this.setBounds(x, y, width, height);
		imgBuffered = new BufferedImage(width,height,BufferedImage.TRANSLUCENT);
		this.setBackground(Color.white); //Color de fondo del lienzo
		this.g = (Graphics2D)imgBuffered.getGraphics();
		setFocusable(true);
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				drawPoint(e.getX(), e.getY());
			}
		});
	}
	
	public void paint(Graphics g) {
		g.drawImage(imgBuffered, 0, 0, this);
	}
	
	public void drawShape(Shape shape,Color color) {
		this.g.setColor(color); //Color con el que se pintará sobre el lienzo
		this.g.fill(shape);
		repaint();
	}
	private void drawPoint(int x,int y) {
		this.g.setColor(Color.BLACK); //Color con el que se pintará sobre el lienzo
		this.g.drawLine(x, y,x, y);
		repaint();
	}
	
	public void drawTraingle(int x,int y,int width,int height) {
		Image triangulo = new ImageIcon("triangulo.png").getImage();
		this.g.drawImage(triangulo, x, y, width, height, null);
		repaint();
	}
	
}
