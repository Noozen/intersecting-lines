package com.mikolaj.nalecz.studia.aal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;

import javax.swing.JPanel;

import com.mikolaj.nalecz.studia.aal.logika.LineSegmentsSolver;

public class Lines extends JPanel {

	private static final long serialVersionUID = 1L;
	
	LineSegmentsSolver solver;
	public int wielkosc = 500;
	
	public Lines(LineSegmentsSolver solver) {
		this.solver = solver;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// solver.wczytajWszystkieOdcinkiZPliku("/input.txt");

		for (SortedSet<Line2D> grupa : solver.grupyOdcinkow) {
			g.setColor(new Color(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
			for (Line2D linia : grupa) {
				g.drawLine((int) linia.getX1(), (int) linia.getY1(), (int) linia.getX2(), (int) linia.getY2());
			}
		}
		solver.clear();
	}
	
	public int getWielkosc() {
		return wielkosc;
	}

	public void setWielkosc(int wielkosc) {
		this.wielkosc = wielkosc;
	}

}
