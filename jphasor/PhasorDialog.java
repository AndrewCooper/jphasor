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
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;

public class PhasorDialog extends JDialog {
	private Phasor phasor;
	private ListModel others;

	private JLabel nameLabel = new JLabel();
	private JLabel magLabel = new JLabel();
	private JLabel phaseLabel = new JLabel();
	private JLabel addLabel = new JLabel();
	private JTextField nameField = new JTextField();
	private JTextField magField = new JTextField();
	private JTextField phaseField = new JTextField();
	private JComboBox addBox = new JComboBox();
	private JButton okButton = new JButton();
	private JButton cancelButton = new JButton();
	private JPanel panel = new JPanel();
	private GridBagLayout gridBagLayout1 = new GridBagLayout();

	private InputVerifier nv = new NumberVerifier();
	private JColorChooser colorChooser = new JColorChooser();
	private PrevPanel previewPanel = new PrevPanel();
	private JLabel emptyLabel = new JLabel();
	private JLabel emptyLabel2 = new JLabel();

	public PhasorDialog() {
		try {
			jbInit();
			this.getRootPane().setDefaultButton(okButton);
			this.setModal(true);
			this.pack();

			//Center the window
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = this.getSize();
			if (frameSize.height > screenSize.height) {
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			}
			this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 3);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void initData(Phasor ph, boolean newph, ListModel lm) {
		setPhasor(ph,newph);
		addListModel(lm);
	}
	public void setPhasor(Phasor ph, boolean newph) {
		phasor = ph;
		nameField.setText(ph.getName());
		magField.setText(""+ph.getMag());
		phaseField.setText(""+ph.getPhase());
		colorChooser.setColor(ph.getColor());
		if (!newph) {
			okButton.setText("Change");
		}
	}
	public void addListModel(ListModel lm) {
		others = lm;
		String newname;
		addBox.removeAllItems();
		addBox.addItem("None");
		for(int i = 0; i < lm.getSize(); i++) {
			newname = ((Phasor)lm.getElementAt(i)).getName();
			if ( newname.compareTo(nameField.getText()) != 0) {
				addBox.addItem(newname);
			}
			if (phasor.getTail() != null && phasor.getTail().getName().equals(newname)) {
				addBox.setSelectedItem(newname);
			}
		}
	}
	private void jbInit() throws Exception {
		panel.setLayout(gridBagLayout1);

		nameLabel.setText("Name");
		magLabel.setText("Magnitude");
		phaseLabel.setText("Phase");
		addLabel.setText("Add To");
		okButton.setText("OK");
		cancelButton.setText("Cancel");
		emptyLabel.setText("");
		emptyLabel2.setText("");

		addBox.addItem("None");

		nameField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				nameField.selectAll();
			}
		});

		magField.setInputVerifier(nv);
		magField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				magField.selectAll();
			}
		});

		phaseField.setInputVerifier(nv);
		phaseField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				phaseField.selectAll();
			}
		});

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePhasor();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		colorChooser.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(153, 153, 153),2),"Color"));
		colorChooser.setPreviewPanel(new JPanel());

		panel.add(nameLabel,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		panel.add(nameField,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 2), 0, 15));

		panel.add(magLabel,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		panel.add(magField,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 2), 0, 15));

		panel.add(phaseLabel,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		panel.add(phaseField,  new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 2), 0, 15));

		panel.add(addLabel,  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		panel.add(addBox,  new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 2), 0, 15));

		panel.add(emptyLabel, new GridBagConstraints(0, 4, 2, 1, 1.0, 1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		panel.add(okButton,   new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		panel.add(cancelButton,    new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		panel.add(colorChooser, new GridBagConstraints(2, 0, 1, 6, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		panel.add(colorChooser, new GridBagConstraints(2, 0, 1, 5, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		panel.setFocusTraversalPolicy(new ContainerOrderFocusTraversalPolicy());
		this.getContentPane().add(panel,null);

	}

	public void changePhasor() {
		phasor.setName(nameField.getText());
		phasor.setMag(Double.parseDouble(magField.getText()));
		phasor.setPhase(Double.parseDouble(phaseField.getText()));
		phasor.setColor(colorChooser.getColor());
		phasor.setActive(true);

		if (addBox.getSelectedIndex() != 0) {
			String addn = (String)addBox.getSelectedItem();
			Phasor ph;
			for (int i = 0; i < others.getSize(); i++) {
				ph = (Phasor)others.getElementAt(i);
				if (addn.compareTo(ph.getName()) == 0) {
					phasor.setTail(ph);
				}
			}
		} else {
			phasor.setTail(null);
		}
		hide();
	}
	public void reset() {
		nameField.setText("0");
		magField.setText("0");
		phaseField.setText("0");
		addBox.setSelectedIndex(0);
	}
	public void show() {
		super.show();
		nameField.requestFocus();
		nameField.selectAll();
	}

	class PrevPanel extends JPanel implements ChangeListener {
		Color c;

		public PrevPanel() {
			setMinimumSize(new Dimension(300,30));
			setPreferredSize(new Dimension(300,30));
			setMaximumSize(new Dimension(300,30));
			c = Color.black;
		}
		public void stateChanged(ChangeEvent e) {
			c = colorChooser.getColor();
			repaint();
		}
		public Color getColor() {
			return c;
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Rectangle r = g.getClipBounds();
			g.setColor(Color.white);
			g.fillRect(r.x,r.y,r.width,r.height);
			g.setColor(Color.gray);
			for (int x = r.x; x <= r.x+r.width; x+=50) {
				g.drawLine(x,r.y,x,r.y+r.height);
			}
			for (int y = r.y; y <= r.y+r.height; y+=10) {
				g.drawLine(r.x,y,r.x+r.width,y);
			}
			Phasor.drawVector(g,c,r.x+r.width/5,r.y+r.height/2,3*r.width/5,0,"Color");
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