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
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class PhasorView extends JPanel implements Pageable, Printable{

	private SidePanel sourcePanel;
	private PageFormat pageFormat;

	public PhasorView(Dimension size) {
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setBackground(Color.white);
		this.setDoubleBuffered(false);
		pageFormat = new PageFormat();

	}

	public Point2D.Double toCartesian(double mag, double angle) {
		double x = mag*Math.cos(angle);
		double y = mag*Math.sin(angle);
		return new Point2D.Double(x,y);
	}

	public Point2D.Double toPolar(double x, double y) {
		double mag = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
		double angle = Math.atan2(y,x);
		return new Point2D.Double(mag,angle);
	}

	//implement Pageable
	public int getNumberOfPages() {
		return 1;
	}

	public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
		if (pageIndex == 0) {
			return pageFormat;
		} else {
			throw new IndexOutOfBoundsException("Page "+pageIndex+" does not exist.");
		}
	}

	public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
		if (pageIndex == 0) {
			return this;
		} else {
			throw new IndexOutOfBoundsException("Page "+pageIndex+" does not exist.");
		}
	}

	//implement Printable
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException{
		if (pageIndex >=1) {
			return NO_SUCH_PAGE;
		}

		Rectangle r = g.getClipBounds();
		Point tl = new Point(r.getLocation());
		Dimension b = new Dimension(Math.min(r.width,r.height),Math.min(r.width,r.height));
		g.setClip(new Rectangle(tl,b));
		sourcePanel.paintView(g,g.getClipBounds());
		return PAGE_EXISTS;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		sourcePanel.paintView(g,this.getBounds());
	}
	public void paintNow(Graphics g,Rectangle r) {
		sourcePanel.paintView(g,r);
	}

	public void setSource(SidePanel sp) {
		sourcePanel = sp;
	}

}
