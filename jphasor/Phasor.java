/*
	jPhasor - A Program to draw voltage and current phasors on a polar plot.
		Also draws power triangle diagrams
	Copyright (C) 2003  Andrew Cooper, acooper at hkcreations dot org

	This program is free software; you can redistribute it and/or
	modify it under the terms of the GNU General Public License
	as published by the Free Software Foundation; either version 2
	of the License, or (at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program; if not, write to the Free Software
	Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
package jphasor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

public class Phasor extends JLabel implements ListCellRenderer{
	private String name;
	private double mag;
	private double phase; //degrees
	private Color color;
	private Phasor tail;
	boolean active;

	private DecimalFormat fmt = new DecimalFormat("0.####");

	public Phasor() {
		this("",0,0,null,true);
	}

	public Phasor(String n, double m, double p, Phasor t, boolean a) {
		name = n;
		mag = m;
		phase = p;
		color = Color.black;
		tail = t;
		active = a;
		this.setOpaque(true);
	}

	/*
	 * This method finds the image and text corresponding
	 * to the selected value and returns the label, set up
	 * to display the text and image.
	 */
	public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		//Get the selected index. (The index param isn't
		//always valid, so just use the value.)
		//int selectedIndex = ((Integer)value).intValue();
		Phasor ph_value = (Phasor)value;

		name = ph_value.getName();
		mag = ph_value.getMag();
		phase = ph_value.getPhase();
		color = ph_value.getColor();
		tail = ph_value.getTail();
		active = ph_value.isActive();

		if (isSelected) {
			this.setBackground(list.getSelectionBackground());
		} else {
			this.setBackground(list.getBackground());
		}
		if (active) {
			this.setForeground(color);
		} else {
			this.setForeground(new Color(color.getRed(),color.getGreen(),color.getBlue(),127));
		}
		Point2D.Double tailpt = getTailPt();
		this.setText(name +":\t"+fmt.format(mag)+" \u2220 "+fmt.format(phase)+"¡ @ ("+fmt.format(tailpt.x)+","+fmt.format(tailpt.y)+")");
		return this;
	}
	public Point2D.Double getTailPt() {
		Point2D.Double headpt;
		if (tail != null) {
			headpt = tail.getTailPt();
			headpt.setLocation(headpt.x+tail.getMag()*Math.cos(Math.toRadians(tail.getPhase())),headpt.y+tail.getMag()*Math.sin(Math.toRadians(tail.getPhase())));
		} else {
			headpt = new Point2D.Double(0,0);
		}
		return headpt;
	}
	public String getName() {
		return name;
	}
	public double getMag() {
		return mag;
	}
	public double getPhase() {
		return phase;
	}
	public Color getColor() {
		return color;
	}
	public Phasor getTail() {
		return tail;
	}
	public boolean isActive() {
		return active;
	}
	public void setName(String n) {
		name = n;
	}
	public void setMag(double m) {
		mag = m;
	}
	public void setPhase(double p) {
		phase = p;
	}
	public void setColor(Color c) {
		color = c;
	}
	public void setTail(Phasor t) {
		tail = t;
	}
	public void setActive(boolean a) {
		active = a;
	}
	private static Polygon arrowhead;
	private static double headLength = 10.0;
	private static double headAngle = Math.toRadians(20);
	public static void drawVector(Graphics g, Color c, int sx, int sy, double mag, double phase,String name) {
		int dx = (int)(sx+mag*Math.cos(phase));
		int dy = (int)(sy-mag*Math.sin(phase));
		double backangle = phase + Math.PI;
		int[] xpts = {dx, dx+(int)(headLength*Math.cos(backangle+headAngle)), dx+(int)(headLength*Math.cos(backangle-headAngle)),dx};
		int[] ypts = {dy, dy-(int)(headLength*Math.sin(backangle+headAngle)), dy-(int)(headLength*Math.sin(backangle-headAngle)),dy};
		arrowhead = new Polygon(xpts,ypts,4);

		g.setColor(c);
		g.drawLine(sx,sy,dx,dy);
		g.fillPolygon(arrowhead);
		int ycorr = 0;
		if (phase%(2*Math.PI) >= 0 && phase%(2*Math.PI) <= Math.PI) {
			ycorr = -5;
		} else {
			ycorr = 10;
		}
		if (name != null) {
			g.drawString(name,dx,dy+ycorr);
		}
	}
}