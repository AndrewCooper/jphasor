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

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.print.*;

/**
 * This is the main window of the program, where most of the action in the program happens.
 */
public class DiagramWindow extends JFrame {
	private JPanel contentPane;
	private PhasorView phasorView;
	private JDialog aboutDialog;
	private JLabel statusMessage;
	JTextField picSizeField;

	/**
	 * Constructs a new window, passed a JLabel representing the splash screen message.
	 * @param message The message displayed on the splash screen.
	 */
	public DiagramWindow(JLabel message) {
		statusMessage = message;
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
			pack();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	//Component initialization
	private void jbInit() throws Exception  {
		statusMessage.setText("Loading... about box");
		aboutDialog = new AboutDialog();
		statusMessage.setText("Loading... instance variables");
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		//this.setSize(new Dimension(600, 600));
		this.setTitle("Phasor Diagram");
		JPanel logoCont = new JPanel();
		JPanel buttonCont = new JPanel();
		JPanel viewCont = new JPanel();
		JPanel centerContY = new JPanel();
		logoCont.setLayout(new BoxLayout(logoCont,BoxLayout.X_AXIS));
		buttonCont.setLayout(new BoxLayout(buttonCont,BoxLayout.X_AXIS));
		viewCont.setLayout(new BoxLayout(viewCont,BoxLayout.X_AXIS));
		centerContY.setLayout(new BoxLayout(centerContY,BoxLayout.Y_AXIS));

		statusMessage.setText("Loading... phasor graph");
		JLabel logoLabel = new JLabel(new ImageIcon(jphasor.jPhasor.class.getResource("images/jPhasorLogo.png")));
		phasorView = new PhasorView(new Dimension(500,500));
		JButton aboutButton = new JButton("About jPhasor...");
		aboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutDialog.setVisible(true);
			}
		});
		JButton printButton = new JButton("Print...");
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JButton) {
					PrinterJob printJob = PrinterJob.getPrinterJob();
					//printJob.setPrintable(phasorView);
					printJob.setPageable(phasorView);
					if (printJob.printDialog()) {
						try {
							printJob.print();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});
		JButton copyButton = new JButton("Copy");
copyButton.setEnabled(false);
		copyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClipboard();
			}
		});
		JButton saveButton = new JButton("Save...");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveToFile();
			}
		});
		picSizeField = new JTextField("500",4);
		picSizeField.setPreferredSize(new Dimension(100,20));
		picSizeField.setMinimumSize(new Dimension(100,20));
		picSizeField.setMaximumSize(new Dimension(100,20));
		picSizeField.setInputVerifier(new NumberVerifier());
		JLabel picSizeUnits = new JLabel("px");
		logoCont.add(Box.createHorizontalGlue());
		logoCont.add(logoLabel);
		logoCont.add(Box.createHorizontalGlue());
		buttonCont.add(aboutButton);
		buttonCont.add(Box.createHorizontalGlue());
		buttonCont.add(printButton);
		buttonCont.add(Box.createHorizontalGlue());
		buttonCont.add(copyButton);
		buttonCont.add(Box.createHorizontalGlue());
		buttonCont.add(saveButton);
		buttonCont.add(Box.createHorizontalGlue());
		buttonCont.add(picSizeField);
		buttonCont.add(picSizeUnits);
		viewCont.add(phasorView);
		viewCont.add(Box.createHorizontalGlue());

		centerContY.add(logoCont);
		centerContY.add(buttonCont);
		centerContY.add(viewCont);
		centerContY.add(Box.createVerticalGlue());
		contentPane.add(centerContY,BorderLayout.CENTER);

		statusMessage.setText("Loading... control sidebar");
		JPanel eastCont = new JPanel();
		eastCont.setLayout(new BoxLayout(eastCont,BoxLayout.Y_AXIS));
		JPanel sidePanel = new SidePanel(phasorView);
		eastCont.add(sidePanel);
		eastCont.add(Box.createVerticalGlue());
		contentPane.add(eastCont,BorderLayout.EAST);
		statusMessage.setText("Activating window...");
	}

	/**
	 * Implements copying the current view to the clipboard/pasteboard.
	 */
	public void copyToClipboard() {

	}

	/**
	 * Implements saving the current view to a file.
	 */
	public void saveToFile() {
		try {
			JFileChooser jfc = new JFileChooser();
            jfc.setAcceptAllFileFilterUsed(false);
			jfc.addChoosableFileFilter(new ImageFilter(Utils.jpg));
			jfc.addChoosableFileFilter(new ImageFilter(Utils.png));
			int val = jfc.showSaveDialog(this);
			if (val == JFileChooser.APPROVE_OPTION) {
				File ofile = jfc.getSelectedFile();
				String type = jfc.getFileFilter().getDescription();
				String pname = ofile.getPath();
				String ext = Utils.getExtension(ofile);
				if (ext == null || !ext.equalsIgnoreCase(type)) {
					ofile = new File(pname+"."+type);
				}
				int s = Integer.parseInt(picSizeField.getText());
				BufferedImage ibuf = new BufferedImage(s,s,BufferedImage.TYPE_4BYTE_ABGR_PRE);
				phasorView.paintNow(ibuf.createGraphics(),new Rectangle(0,0,s,s));
				ImageIO.write(ibuf,type,ofile);
			}
		} catch (IOException e) {

		}
	}

	/**
	 * Overridden so that the program exits when the window closes.
	 * @param e Event to check for closing.
	 */
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}

	/**
	 * A NumberVerifier designed to ensure the values in a text box are numbers.
	 */
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