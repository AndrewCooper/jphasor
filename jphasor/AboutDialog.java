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
import java.io.*;

/**
 * Displays a dialog box containing information about this program, as well as
 * displaying the GPL for users to view.
 */
public class AboutDialog extends JDialog {
	private JPanel viewpane = new JPanel();
	private GridBagLayout gridBagLayout1 = new GridBagLayout();
	private JLabel logoLabel = new JLabel();
	private JScrollPane jScrollPane1 = new JScrollPane();
	private JTextArea licenseTextArea = new JTextArea();
	private JLabel creatorLabel = new JLabel();
	private JLabel copyrightLabel = new JLabel();

	/**
	 * Default constructor, does not depend on anything else to display this dialog box.
	 */
	public AboutDialog() {
		try {
			jbInit();
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
	private void jbInit() throws Exception {
		viewpane.setLayout(gridBagLayout1);
		this.setModal(true);
		this.setResizable(false);
		try {
			InputStream licenseStream = this.getClass().getResourceAsStream("/LICENSE");
			licenseTextArea.read(new InputStreamReader(licenseStream),null);
		} catch (Exception e) {
			System.err.println("No LICENSE file found");
			licenseTextArea.setText("No LICENSE file found");
		}
		licenseTextArea.setToolTipText("GNU General Public License");
		licenseTextArea.setEditable(false);
		licenseTextArea.setRows(15);
		licenseTextArea.setTabSize(4);

		creatorLabel.setText("Created By: Andrew Cooper, acooper at hkcreations dot org");
		copyrightLabel.setText("Copyright © 2003 Andrew Cooper, HK Creations under the GNU GPL (see below)");
		jScrollPane1.getViewport().add(licenseTextArea, null);

		logoLabel.setIcon(new ImageIcon(Utils.getImage(this,"/images/jPhasorLogo.png")));
		viewpane.add(logoLabel, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
		viewpane.add(jScrollPane1, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		viewpane.add(creatorLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
		viewpane.add(copyrightLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
		this.getContentPane().add(viewpane, null);
	}
}