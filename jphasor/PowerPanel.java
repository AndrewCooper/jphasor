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
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;

public class PowerPanel extends JPanel {
	private SidePanel sidePanel;

	private GridBagLayout gridBagLayout1 = new GridBagLayout();

	private JLabel powerDataLabel = new JLabel();
	private JLabel sLabel = new JLabel();
	private JLabel pLabel = new JLabel();
	private JLabel qLabel = new JLabel();
	private JLabel pfLabel = new JLabel();
	private JLabel angleLabel = new JLabel();
	private JTextField pField = new JTextField();
	private JTextField sField = new JTextField();
	private JTextField pfField = new JTextField();
	private JTextField qField = new JTextField();
	private JTextField angleField = new JTextField();
	private JPanel leadLagPanel = new JPanel();
	private JRadioButton leadRadio = new JRadioButton();
	private JRadioButton lagRadio = new JRadioButton();
	private ButtonGroup leadLagGroup = new ButtonGroup();
	private JLabel graphDataLabel = new JLabel();
	private JLabel jLabel5 = new JLabel();
	private JLabel jLabel6 = new JLabel();
	private JLabel maxXYLabel = new JLabel();
	private JTextField maxXYField = new JTextField();
	private JLabel realDivLabel = new JLabel();
	private JTextField realDivField = new JTextField();
	private JLabel jLabel12 = new JLabel();
	private JLabel imagDivLabel = new JLabel();
	private JTextField imagDivField = new JTextField();
	private JLabel jLabel14 = new JLabel();

	private JComboBox sUnits = new JComboBox();
	private JComboBox pUnits = new JComboBox();
	private JComboBox qUnits = new JComboBox();
	private JComboBox aUnits = new JComboBox();
	private JComboBox maxXYUnits = new JComboBox();

	private InputVerifier nv = new NumberVerifier();
	private DecimalFormat fmt = new DecimalFormat("0.####");

	public PowerPanel() {
		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void jbInit() throws Exception {
		this.setLayout(gridBagLayout1);

		powerDataLabel.setHorizontalAlignment(SwingConstants.LEFT);
		powerDataLabel.setText("Power Data");
		powerDataLabel.setVerticalAlignment(SwingConstants.BOTTOM);

		sLabel.setText("S");
		sLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		sField.setNextFocusableComponent(pField);
		sField.setText("Apparent");
		sField.setInputVerifier(nv);
		sField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				sField.selectAll();
			}
			public void focusLost(FocusEvent e) {
				redraw();
			}
		});
		sUnits.addItem("mVA");
		sUnits.addItem("VA");
		sUnits.addItem("kVA");

		pLabel.setText("P");
		pLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		sField.setNextFocusableComponent(sUnits);
		pField.setText("Real");
//		pField.setNextFocusableComponent(pUnits);
		pField.setInputVerifier(nv);
		pField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				pField.selectAll();
			}
			public void focusLost(FocusEvent e) {
				redraw();
			}
		});
		pUnits.addItem("mW");
		pUnits.addItem("W");
		pUnits.addItem("kW");

		qLabel.setText("Q");
		qLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		qField.setNextFocusableComponent(qUnits);
		qField.setText("Imaginary");
		qField.setInputVerifier(nv);
		qField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				qField.selectAll();
			}
			public void focusLost(FocusEvent e) {
				redraw();
			}
		});
		qUnits.addItem("mVAR");
		qUnits.addItem("VAR");
		qUnits.addItem("kVAR");

		pfLabel.setText("pf");
		pfLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		pfField.setNextFocusableComponent(leadRadio);
		pfField.setInputVerifier(nv);
		pfField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				pfField.selectAll();
			}
			public void focusLost(FocusEvent e) {
				redraw();
			}
		});
		pfField.setText("pf");

		leadLagPanel.setLayout(new BoxLayout(leadLagPanel,BoxLayout.Y_AXIS));
//		leadRadio.setNextFocusableComponent(lagRadio);
		leadRadio.setSelected(true);
		leadRadio.setText("Leading");
//		lagRadio.setNextFocusableComponent(angleField);
		lagRadio.setText("Lagging");
		graphDataLabel.setHorizontalAlignment(SwingConstants.LEFT);
		graphDataLabel.setVerticalAlignment(SwingConstants.BOTTOM);
//		maxXYField.setNextFocusableComponent(maxXYUnits);
//		sUnits.setNextFocusableComponent(pField);
//		pUnits.setNextFocusableComponent(qField);
//		qUnits.setNextFocusableComponent(pfField);
//		aUnits.setNextFocusableComponent(maxXYField);
//		maxXYUnits.setNextFocusableComponent(realDivField);
		leadLagGroup.add(leadRadio);
		leadLagGroup.add(lagRadio);

		angleLabel.setText("Angle");
		angleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		angleField.setText("Theta");
		angleField.setInputVerifier(nv);
//		angleField.setNextFocusableComponent(aUnits);
		angleField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				angleField.selectAll();
			}
			public void focusLost(FocusEvent e) {
				redraw();
			}
		});
		aUnits.addItem("deg");
		aUnits.addItem("rad");

		graphDataLabel.setText("Graph Data");
		maxXYLabel.setText("Max XY");
		maxXYField.setText("Max XY");
		maxXYField.setInputVerifier(nv);
		maxXYField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				maxXYField.selectAll();
			}
			public void focusLost(FocusEvent e) {
				redraw();
			}
		});
		maxXYUnits.addItem("mW | mVAR");
		maxXYUnits.addItem("W | VAR");
		maxXYUnits.addItem("kW | kVAR");

		realDivLabel.setText("Real Divs");
		realDivField.setText("Real Divs");
		realDivField.setInputVerifier(nv);
//		realDivField.setNextFocusableComponent(imagDivField);
		realDivField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				realDivField.selectAll();
			}
			public void focusLost(FocusEvent e) {
				redraw();
			}
		});

		imagDivLabel.setText("Imag Divs");
		imagDivField.setText("Imag Divs");
		imagDivField.setInputVerifier(nv);
//		imagDivField.setNextFocusableComponent(sField);
		imagDivField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				imagDivField.selectAll();
			}
			public void focusLost(FocusEvent e) {
				redraw();
			}
		});
		leadLagPanel.add(Box.createVerticalGlue());
		leadLagPanel.add(leadRadio, null);
		leadLagPanel.add(lagRadio, null);
		leadLagPanel.add(Box.createVerticalGlue());

		this.add(powerDataLabel, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 100, 15));

		this.add(sLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 15));
		this.add(sField, new GridBagConstraints(1, 1, 1, 1, 0.2, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 0, 0, 0), 100, 0));
		this.add(sUnits, new GridBagConstraints(2, 1, 1, 1, 0.1, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));

		this.add(pLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 15));
		this.add(pField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 0, 0, 0), 0, 0));
		this.add(pUnits, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));

		this.add(qLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 15));
		this.add(qField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 0, 0, 0), 0, 0));
		this.add(qUnits, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));

		this.add(pfLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 15));
		this.add(pfField, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 0, 0, 0), 0, 0));
		this.add(leadLagPanel, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));

		this.add(angleLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 15));
		this.add(angleField, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 0, 0, 0), 0, 0));
		this.add(aUnits, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));

		this.add(graphDataLabel, new GridBagConstraints(0, 6, 3, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 0), 100, 15));

		this.add(maxXYLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 15));
		this.add(maxXYField, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 0, 0, 0), 0, 0));
		this.add(maxXYUnits, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));

		this.add(realDivLabel, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 15));
		this.add(realDivField, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 0, 0, 0), 0, 0));

		this.add(imagDivLabel, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 15));
		this.add(imagDivField, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 0, 0, 0), 0, 0));

	}

	public void clear() {
		sField.setText("0");
		pField.setText("0");
		qField.setText("0");
		pfField.setText("0");
		leadRadio.setSelected(true);
		lagRadio.setSelected(false);
		angleField.setText("0");
		maxXYField.setText("10");
		realDivField.setText("5");
		imagDivField.setText("5");

		sUnits.setSelectedIndex(1);
		pUnits.setSelectedIndex(1);
		qUnits.setSelectedIndex(1);
		aUnits.setSelectedIndex(0);
		maxXYUnits.setSelectedIndex(1);

	}
	public void redraw() {

	}
	public double getApparent() {
		return Double.parseDouble(sField.getText());
	}
	public int getSUnits() {
		return sUnits.getSelectedIndex();
	}
	public double getReal() {
		return Double.parseDouble(pField.getText());
	}
	public int getPUnits() {
		return pUnits.getSelectedIndex();
	}
	public double getImaginary() {
		return Double.parseDouble(qField.getText());
	}
	public int getQUnits() {
		return qUnits.getSelectedIndex();
	}
	public double getPF() {
		return Double.parseDouble(pfField.getText());
	}
	public boolean isLeading() {
		if (leadRadio.isSelected()) {
			return true;
		} else {
			return false;
		}
	}
	public double getAngle() {
		return Double.parseDouble(angleField.getText());
	}
	public int getAUnits() {
		return aUnits.getSelectedIndex();
	}
	public double getMaxReal() {
		return Double.parseDouble(maxXYField.getText());
	}
	public double getMaxImag() {
		return Double.parseDouble(maxXYField.getText());
	}
	public int getMaxXYUnits() {
		return maxXYUnits.getSelectedIndex();
	}
	public int getRealDivs() {
		return Integer.parseInt(realDivField.getText());
	}
	public int getImagDivs() {
		return Integer.parseInt(imagDivField.getText());
	}
	public void setParent(SidePanel sp) {
		sidePanel = sp;
	}

	public void paintView(Graphics g, Rectangle r) {
//		Rectangle r = g.getClipBounds();
		Point tl = new Point(r.getLocation());
		Dimension b = new Dimension(Math.min(r.width,r.height),Math.min(r.width,r.height));
//		g.setClip(new Rectangle(tl,b));
		Point c = new Point(tl.x+b.width/2,tl.y+b.height/2);

		g.setColor(Color.lightGray);
		//draw outline
		g.drawRect(tl.x, tl.y, b.width-1, b.height-1);

		//draw axes
		g.drawLine(tl.x, tl.y + b.height/2, tl.x + b.width, tl.y + b.height/2);
		g.drawLine(tl.x + b.width/2, tl.y, tl.x + b.width/2, tl.y + b.height);

		double S = getApparent();
		double P = getReal();
		double rA = 0;
		if (P < 0) {
			rA = Math.PI;
		}
		double Q = getImaginary();
		double pf = getPF();
		double angle = getAngle();
		int lf=0;
		if (isLeading()) {
			lf = 1;
		} else {
			lf = -1;
		}

		double maxReal = getMaxReal();
		double maxImag = getMaxImag();
		double maxApp = Math.sqrt(Math.pow(maxReal,2)+Math.pow(maxImag,2));
		int realDivs = getRealDivs();
		int imagDivs = getImagDivs();
		if (realDivs < 1) realDivs = 1;
		if (imagDivs < 1) imagDivs = 1;
		double realScaling = b.width/(2*maxReal);
		double imagScaling = b.height/(2*maxImag);
		double appScaling = Math.sqrt(Math.pow(b.width,2) + Math.pow(b.height,2))/(2*maxApp);

		double realSpacing = b.width/(2*realDivs);
		double imagSpacing = b.height/(2*imagDivs);

		//draw real ticks
		int x1=0;
		int x2=0;
		int y1=0;
		int y2=0;
		g.drawString("0",c.x,c.y);
		for(int i = 1; i <= realDivs; i++) {
			x1=c.x+(int)(i*realSpacing);
			x2=c.x-(int)(i*realSpacing);
			y1=c.y-3;
			y2=c.y+3;
			g.drawLine(x1,y1,x1,y2);
			g.drawLine(x2,y1,x2,y2);
			g.drawString(fmt.format(i*maxReal/realDivs),x1,y1);
			g.drawString(fmt.format(-1*i*maxReal/realDivs),x2,y1);
		}
		//draw imag ticks
		for(int i = 1; i <= imagDivs; i++) {
			x1=c.x-3;
			x2=c.x+3;
			y1=c.y-(int)(i*imagSpacing);
			y2=c.y+(int)(i*imagSpacing);
			g.drawLine(x1,y1,x2,y1);
			g.drawLine(x1,y2,x2,y2);
			g.drawString(fmt.format(i*maxImag/imagDivs),x2,y1);
			g.drawString(fmt.format(-1*i*maxImag/imagDivs),x2,y2);
		}

		//draw power triangle
		Color realC = Color.blue;
		Color imagC = Color.red;
		Color appC = Color.green;
		Phasor.drawVector(g,realC,c.x,c.y,Math.abs(P)*realScaling,rA,null);
		Phasor.drawVector(g,imagC,c.x+(int)(P*realScaling),c.y,Q*imagScaling,lf*Math.PI/2,null);
		Phasor.drawVector(g,appC,c.x,c.y,S*appScaling,lf*Math.toRadians(angle),null);

		Phasor.drawVector(g,appC, tl.x+10, tl.y+15, 30, 0, " Apparent (VA)");
		Phasor.drawVector(g,realC, tl.x+10, tl.y+35, 30, 0, " Real (W)");
		Phasor.drawVector(g,imagC, tl.x+10, tl.y+55, 30, 0, " Imag (VAR)");
	}

	class NumberVerifier extends InputVerifier {
		public boolean shouldYieldFocus(JComponent input) {
			return verify(input);
		}
		public boolean verify(JComponent input) {
			if (input instanceof JTextField) {
				JTextField jtf = (JTextField)input;
				try{
					Double.parseDouble(jtf.getText());
				} catch (NumberFormatException e) {
					jtf.setText("0");
				}
			}
			return true;
		}
	}
}


