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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.geom.Point2D;

public class PhasorPanel extends JPanel {
	private SidePanel sidePanel;
	private GridBagLayout gridBagLayout1 = new GridBagLayout();

	private DefaultListModel voltageList = new DefaultListModel();
	private DefaultListModel currentList = new DefaultListModel();

	private PhasorDialog phasorDialog;

	private JLabel vListLabel = new JLabel();
	private JComboBox vUnitsBox = new JComboBox();
	private JScrollPane vScrollPane = new JScrollPane();
	private JList vList = new JList();
	private JButton vAddButton = new JButton();
	private JButton vRemButton = new JButton();
	private JButton vEditButton = new JButton();
	private JCheckBox vActiveCheck = new JCheckBox();

	private JLabel iListLabel = new JLabel();
	private JComboBox iUnitsBox = new JComboBox();
	private JScrollPane iScrollPane = new JScrollPane();
	private JList iList = new JList();
	private JButton iAddButton = new JButton();
	private JButton iRemButton = new JButton();
	private JButton iEditButton = new JButton();
	private JCheckBox iActiveCheck = new JCheckBox();

	private JLabel propsLabel = new JLabel();

	private JLabel maxVLabel = new JLabel();
	private JTextField maxV = new JTextField();
	private JComboBox vMaxUnitsBox = new JComboBox();
	private JLabel maxILabel = new JLabel();
	private JTextField maxI = new JTextField();
	private JComboBox iMaxUnitsBox = new JComboBox();
	private JLabel magDivsLabel = new JLabel();
	private JTextField magDivs = new JTextField();
	private JLabel phaseDivsLabel = new JLabel();
	private JTextField phaseDivs = new JTextField();

	private InputVerifier nv = new NumberVerifier();
	public PhasorPanel() {
		try {
			jbInit();
			phasorDialog = new PhasorDialog();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void jbInit() throws Exception {
		this.setLayout(gridBagLayout1);

		vListLabel.setHorizontalAlignment(SwingConstants.LEFT);
		vListLabel.setText("Voltages");
		vListLabel.setVerticalAlignment(SwingConstants.BOTTOM);

		vUnitsBox.addItem("mV");
		vUnitsBox.addItem("V");
		vUnitsBox.addItem("kV");

		vScrollPane.getViewport().add(vList, null);
		vList.setModel(voltageList);
		vList.setCellRenderer(new Phasor());
		vList.setFixedCellHeight(15);
		vList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		vList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (vList.getSelectedIndex() != -1) {
					vRemButton.setEnabled(true);
					vEditButton.setEnabled(true);
					vActiveCheck.setEnabled(true);
					vActiveCheck.setSelected(((Phasor)(voltageList.getElementAt(vList.getSelectedIndex()))).isActive());
				} else {
					vAddButton.requestFocus();
					vRemButton.setEnabled(false);
					vEditButton.setEnabled(false);
					vActiveCheck.setEnabled(false);
					vActiveCheck.setSelected(false);
				}
			}
		});;

		vAddButton.setText("Add...");
		vAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPhasor("Voltage",voltageList);
			}
		});
		vRemButton.setText("Remove");
		vRemButton.setEnabled(false);
		vRemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePhasor(vList.getSelectedIndex(),voltageList);
			}
		});
		vEditButton.setText("Edit...");
		vEditButton.setEnabled(false);
		vEditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editPhasor("Voltage",vList.getSelectedIndex(),voltageList);
			}
		});
		vActiveCheck.setText("Active");
		vActiveCheck.setEnabled(false);
		vActiveCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeActive(vList.getSelectedIndex(),voltageList,vActiveCheck.isSelected());
			}
		});

		iListLabel.setHorizontalAlignment(SwingConstants.LEFT);
		iListLabel.setText("Currents");
		iListLabel.setVerticalAlignment(SwingConstants.BOTTOM);

		iUnitsBox.addItem("mA");
		iUnitsBox.addItem("A");
		iUnitsBox.addItem("kA");

		iScrollPane.getViewport().add(iList, null);
		iList.setModel(currentList);
		iList.setCellRenderer(new Phasor());
		iList.setFixedCellHeight(15);
		iList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		iList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (iList.getSelectedIndex() != -1) {
					iRemButton.setEnabled(true);
					iEditButton.setEnabled(true);
					iActiveCheck.setEnabled(true);
					iActiveCheck.setSelected(((Phasor)(currentList.getElementAt(iList.getSelectedIndex()))).isActive());
				} else {
					iAddButton.requestFocus();
					iRemButton.setEnabled(false);
					iEditButton.setEnabled(false);
					iActiveCheck.setEnabled(false);
					iActiveCheck.setSelected(false);
				}
			}
		});;

		iAddButton.setText("Add...");
		iAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPhasor("Current",currentList);
			}
		});
		iRemButton.setText("Remove");
		iRemButton.setEnabled(false);
		vRemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePhasor(iList.getSelectedIndex(),currentList);
			}
		});
		iEditButton.setText("Edit...");
		iEditButton.setEnabled(false);
		iEditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editPhasor("Current",iList.getSelectedIndex(),currentList);
			}
		});
		iActiveCheck.setText("Active");
		iActiveCheck.setEnabled(false);
		iActiveCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeActive(iList.getSelectedIndex(),currentList,iActiveCheck.isSelected());
			}
		});

		propsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		propsLabel.setText("Graph Properties");
		propsLabel.setVerticalAlignment(SwingConstants.BOTTOM);

		maxVLabel.setText("Max V");
		maxVLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//		maxV.setNextFocusableComponent(maxI);
		maxV.setInputVerifier(nv);
		maxV.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				maxV.selectAll();
			}
			public void focusLost(FocusEvent e) {
				sidePanel.redraw();
			}
		});
		vMaxUnitsBox.addItem("mV");
		vMaxUnitsBox.addItem("V");
		vMaxUnitsBox.addItem("kV");

		maxILabel.setText("Max I");
		maxILabel.setHorizontalAlignment(SwingConstants.RIGHT);
//		maxI.setNextFocusableComponent(magDivs);
		maxI.setInputVerifier(nv);
		maxI.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				maxI.selectAll();
			}
			public void focusLost(FocusEvent e) {
				sidePanel.redraw();
			}
		});
		iMaxUnitsBox.addItem("mA");
		iMaxUnitsBox.addItem("A");
		iMaxUnitsBox.addItem("kA");

		magDivsLabel.setText("Mag Divs");
		magDivsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//		magDivs.setNextFocusableComponent(phaseDivs);
		magDivs.setInputVerifier(nv);
		magDivs.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				magDivs.selectAll();
			}
			public void focusLost(FocusEvent e) {
				sidePanel.redraw();
			}
		});

		phaseDivsLabel.setText("Phase Divs");
		phaseDivsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//		phaseDivs.setNextFocusableComponent(maxV);
		phaseDivs.setInputVerifier(nv);
		phaseDivs.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				phaseDivs.selectAll();
			}
			public void focusLost(FocusEvent e) {
				sidePanel.redraw();
			}
		});

		this.add(vListLabel, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.BOTH, new Insets(0, 5, 5, 0), 100, 15));
		this.add(vUnitsBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(vScrollPane, new GridBagConstraints(0, 1, 4, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 150, 50));
		this.add(vAddButton,   new GridBagConstraints(0, 2, 1, 1, 0.2, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(vRemButton,  new GridBagConstraints(1, 2, 1, 1, 0.7, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(vEditButton, new GridBagConstraints(2, 2, 1, 1, 0.2, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(vActiveCheck, new GridBagConstraints(3, 2, 1, 1, 0.2, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 1, 0), 0, 0));

		this.add(iListLabel, new GridBagConstraints(0, 3, 4, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.BOTH, new Insets(0, 5, 5, 0), 100, 15));
		this.add(iUnitsBox, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(iScrollPane, new GridBagConstraints(0, 4, 4, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 150, 50));
		this.add(iAddButton,  new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(iRemButton,  new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(iEditButton, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(iActiveCheck, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		this.add(propsLabel, new GridBagConstraints(0, 6, 3, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.BOTH, new Insets(0, 5, 5, 0), 100, 15));

		this.add(maxVLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
		this.add(maxV, new GridBagConstraints(1, 7, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 12));
		this.add(vMaxUnitsBox, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		this.add(maxILabel, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
		this.add(maxI, new GridBagConstraints(1, 8, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 12));
		this.add(iMaxUnitsBox, new GridBagConstraints(3, 8, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		this.add(magDivsLabel, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
		this.add(magDivs, new GridBagConstraints(1, 9, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 12));

		this.add(phaseDivsLabel, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
		this.add(phaseDivs, new GridBagConstraints(1, 10, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 12));
	}

	public void clear() {
		vUnitsBox.setSelectedIndex(1);
		maxV.setText("10");
		vMaxUnitsBox.setSelectedIndex(1);

		iUnitsBox.setSelectedIndex(1);
		maxI.setText("10");
		iMaxUnitsBox.setSelectedIndex(1);

		magDivs.setText("5");
		phaseDivs.setText("12");
	}

	public double getVMax() {
		return Double.parseDouble(maxV.getText());
	}
	public int getVMaxUnits() {
		return vMaxUnitsBox.getSelectedIndex();
	}
	public double getIMax() {
		return Double.parseDouble(maxI.getText());
	}
	public int getIMaxUnits() {
		return iMaxUnitsBox.getSelectedIndex();
	}
	public int getMagDivs() {
		return Integer.parseInt(magDivs.getText());
	}
	public int getPhaseDivs() {
		return Integer.parseInt(phaseDivs.getText());
	}

	public void setParent(SidePanel sp) {
		sidePanel = sp;
	}

	public void addPhasor(String kind, DefaultListModel lm) {
		Phasor ph = new Phasor();
		phasorDialog.initData(ph,true,lm);
		phasorDialog.setTitle("New "+kind+" Phasor");
		phasorDialog.show();
		if (!ph.getName().equalsIgnoreCase("")) {
			lm.addElement(ph);
		}
		sidePanel.redraw();
	}
	public void editPhasor(String kind, int i, DefaultListModel lm) {
		if (i != -1) {
			Phasor ph = (Phasor)lm.elementAt(i);
			phasorDialog.initData(ph,false,lm);
			phasorDialog.setTitle("Edit "+kind+" Phasor");
			phasorDialog.show();
			lm.set(i,ph);
			sidePanel.redraw();
		}
	}
	public void removePhasor(int i, DefaultListModel lm) {
		if (i != -1) {
			String m = "Remove this Phasor?";
			int r = JOptionPane.showConfirmDialog(this,m,"Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null);
			if (r == JOptionPane.YES_OPTION) {
				lm.remove(i);
			}
			sidePanel.redraw();
		}
	}
	public void changeActive(int i, DefaultListModel lm, boolean selected) {
		Phasor ph = (Phasor)lm.elementAt(i);
		ph.setActive(selected);
		lm.set(i,ph);
		sidePanel.redraw();
	}

	public void paintView(Graphics g,Rectangle r) {
		Point tl = new Point(r.getLocation());
		Dimension b = new Dimension(Math.min(r.width,r.height),Math.min(r.width,r.height));
		Point c = new Point(tl.x+b.width/2,tl.y+b.height/2);

		g.setColor(Color.lightGray);
		//draw outline
		g.drawRect(tl.x, tl.y, b.width-1, b.height-1);

		//draw axes
		g.drawLine(tl.x, tl.y + b.height/2, tl.x + b.width, tl.y + b.height/2);
		g.drawLine(tl.x + b.width/2, tl.y, tl.x + b.width/2, tl.y + b.height);

		//pull data
		double MaxVMag = getVMax();
		double MaxIMag = getIMax();
		int MagDivs = getMagDivs();
		int PhaseDivs = getPhaseDivs();
		if (MagDivs < 1) MagDivs = 1;
		if (PhaseDivs < 1) PhaseDivs = 1;

		double VMagScaling = (b.width/2.0)/(MaxVMag);
		double IMagScaling = (b.width/2.0)/(MaxIMag);

		Object[] voltages = voltageList.toArray();
		Object[] currents = currentList.toArray();


		//draw mag circles
		double spacing = (b.width/(2.0*MagDivs));
		for(int i = 0; i < MagDivs; i++) {
			g.drawOval(tl.x+(int)(i*spacing),tl.y+(int)(i*spacing),(int)(2*(MagDivs-i)*spacing),(int)(2*(MagDivs-i)*spacing));
		}

		//draw phase lines
		double dtheta = 360.0/PhaseDivs;
		dtheta = Math.toRadians(dtheta);
		double cornerMag = .5*Math.sqrt(Math.pow(b.width,2) + Math.pow(b.height,2));
		for(int i = 0; i < PhaseDivs; i++) {
			g.drawLine((c.x),(c.y),(int)(c.x+cornerMag*Math.cos(i*dtheta)),(int)(c.y-cornerMag*Math.sin(i*dtheta)));
		}

		//draw vectors
		Phasor ph;
		Point2D.Double tailpt;
		for(int i=0; i < voltages.length; i++) {
			ph = (Phasor)(voltages[i]);
			if (ph.isActive()) {
				tailpt = ph.getTailPt();
				Phasor.drawVector(g,ph.getColor(),c.x+(int)(tailpt.x*VMagScaling),c.y-(int)(tailpt.y*VMagScaling),ph.getMag()*VMagScaling,Math.toRadians(ph.getPhase()),ph.getName());
			}
		}
		for(int i=0; i < currents.length; i++) {
			ph = (Phasor)(currents[i]);
			if (ph.isActive()) {
				tailpt = ph.getTailPt();
				Phasor.drawVector(g,ph.getColor(),c.x+(int)(tailpt.x*IMagScaling),c.y+(int)(tailpt.y*IMagScaling),ph.getMag()*IMagScaling,Math.toRadians(ph.getPhase()),ph.getName());
			}
		}
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
