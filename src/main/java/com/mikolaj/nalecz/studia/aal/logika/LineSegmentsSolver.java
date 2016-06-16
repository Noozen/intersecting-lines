package com.mikolaj.nalecz.studia.aal.logika;

import java.awt.geom.Line2D;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineSegmentsSolver {

	public List<Collection<Line2D>> grupyOdcinkow = new ArrayList<Collection<Line2D>>();
	private boolean treeSet = false;
	private boolean pomijaj = false;

	public void dodajNowyOdcinek(Line2D linia) {
		Collection<Line2D> grupaDoKtorejDolaczylOdcinek = null;

		Iterator<Collection<Line2D>> i = grupyOdcinkow.iterator();
		while (i.hasNext()) {
			Collection<Line2D> grupaOdcinkow = i.next();
			for (Line2D odcinek : grupaOdcinkow) {
				if (pomijaj) {
					if(treeSet) {
						if (Math.max(linia.getX1(), linia.getX2()) < Math.min(odcinek.getX1(), odcinek.getX2()))
							break;
					} else {
						if (Math.max(linia.getX1(), linia.getX2()) < Math.min(odcinek.getX1(), odcinek.getX2()))
							continue;
					}

					if (Math.min(linia.getX1(), linia.getX2()) > Math.max(odcinek.getX1(), odcinek.getX2()))
						continue;
				}

				if (linia.intersectsLine(odcinek)) {
					if (grupaDoKtorejDolaczylOdcinek == null) {
						grupaDoKtorejDolaczylOdcinek = grupaOdcinkow;
						grupaOdcinkow.add(linia);
						break;
					}

					grupaDoKtorejDolaczylOdcinek.addAll(grupaOdcinkow);
					i.remove();
					break;
				}
			}
		}

		if (grupaDoKtorejDolaczylOdcinek == null) {

			Collection<Line2D> nowaLista;
			if (treeSet)
				nowaLista = new TreeSet<Line2D>(
						(o1, o2) -> Double.compare(Math.min(o1.getX1(), o1.getX2()), Math.min(o2.getX1(), o2.getX2())));
			else
				nowaLista = new LinkedList<Line2D>();
			nowaLista.add(linia);
			grupyOdcinkow.add(nowaLista);
		}
	}

	public void wczytajWszystkieOdcinkiZPliku(String plik)
			throws NumberFormatException, IOException, URISyntaxException {
		URL resource = this.getClass().getResource(plik);

		Pattern p = Pattern.compile("(\\-?\\d+\\.\\d+)");

		Files.lines(Paths.get(resource.toURI())).forEach(s -> {
			Matcher m = p.matcher(s);

			m.find();
			double x1 = Double.parseDouble(m.group());
			m.find();
			double y1 = Double.parseDouble(m.group());
			m.find();
			double x2 = Double.parseDouble(m.group());
			m.find();
			double y2 = Double.parseDouble(m.group());

			Line2D line2D = new Line2D.Double();
			line2D.setLine(x1, y1, x2, y2);

			dodajNowyOdcinek(line2D);
		});
	}

	public void clear() {
		grupyOdcinkow.clear();
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		for (Collection<Line2D> grupaOdcinkow : grupyOdcinkow) {
			for (Line2D odcinek : grupaOdcinkow) {
				stringBuilder.append(new DecimalFormat("#.##").format(odcinek.getX1()) + ";"
						+ new DecimalFormat("#.##").format(odcinek.getY1()) + ";"
						+ new DecimalFormat("#.##").format(odcinek.getX2()) + ";"
						+ new DecimalFormat("#.##").format(odcinek.getY2()) + "\n");
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	public void dodajLosowe(int ilosc, int wielkosc) {
		Random rand = new Random();
		for (int i = 0; i < ilosc; i++) {
			dodajNowyOdcinek(new Line2D.Double(rand.nextDouble() * wielkosc, rand.nextDouble() * wielkosc,
					rand.nextDouble() * wielkosc, rand.nextDouble() * wielkosc));
		}
	}

	public void dodajLosowe2(int ilosc, int wielkosc) {
		Random rand = new Random();
		for (int i = 0; i < ilosc; i++) {
			double x1 = 10 + rand.nextDouble() * (wielkosc - 20);
			double y1 = 10 + rand.nextDouble() * (wielkosc - 20);
			double x2;
			double y2;
			if (rand.nextBoolean() == true)
				x2 = x1 + rand.nextDouble() * 10;
			else
				x2 = x1 - rand.nextDouble() * 10;
			if (rand.nextBoolean() == true)
				y2 = y1 + rand.nextDouble() * 10;
			else
				y2 = y1 - rand.nextDouble() * 10;
			dodajNowyOdcinek(new Line2D.Double(x1, y1, x2, y2));
		}
	}

	public void dodajLosowe3(int ilosc, int wielkosc) {
		Random rand = new Random();
		for (int i = 0; i < ilosc; i++) {
			double x1 = rand.nextDouble() * (wielkosc - 25);
			double y1 = rand.nextDouble() * (wielkosc - 25);
			double a = rand.nextDouble() * 25;
			dodajNowyOdcinek(new Line2D.Double(x1, y1, x1 + a, y1 + a));
		}
	}

	public boolean isTreeSet() {
		return treeSet;
	}

	public void setTreeSet(boolean treeSet) {
		this.treeSet = treeSet;
	}

	public boolean isPomijaj() {
		return pomijaj;
	}

	public void setPomijaj(boolean pomijaj) {
		this.pomijaj = pomijaj;
	}

}
