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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SidePanel extends JPanel{
	private PhasorView phasorView;

	private JTabbedPane modeTabPane = new JTabbedPane();
	private PhasorPanel phasorTab;
	private PowerPanel powerTab;
	private JButton clearButton = new JButton();

	public SidePanel(PhasorView pv) {
		try {
			phasorView = pv;
			jbInit();
			clear();
			phasorView.setSource(this);
			redraw();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void jbInit() throws Exception {
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		phasorTab = new PhasorPanel();
		phasorTab.setParent(this);

		powerTab = new PowerPanel();
		powerTab.setParent(this);

		clearButton.setHorizontalTextPosition(SwingConstants.CENTER);
		clearButton.setText("Clear");
		clearButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});


		modeTabPane.add(phasorTab, "Phasors");
		modeTabPane.add(powerTab, "Power");
		modeTabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				redraw();
			}
		});

		this.add(modeTabPane, null);
		this.add(clearButton, null);
	}

	public void clear() {
		phasorTab.clear();
		powerTab.clear();
	}

	public void redraw() {
		phasorView.repaint();
	}

	public void paintView(Graphics g,Rectangle r) {
		if (isPhasorMode()) {
			phasorTab.paintView(g,r);
		} else {
			powerTab.paintView(g,r);
		}
	}

	public boolean isPhasorMode() {
		if (modeTabPane.getSelectedIndex() == 0) {
			return true;
		} else {
			return false;
		}
	}

}