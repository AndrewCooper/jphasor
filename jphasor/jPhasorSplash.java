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
import javax.swing.border.Border;
import java.awt.*;
import java.net.URL;

public class jPhasorSplash extends JWindow {
	JLabel message;

	public jPhasorSplash() {
		super();
		JLayeredPane c = new JLayeredPane();
		Border b1 = BorderFactory.createRaisedBevelBorder();
		Border b2 = BorderFactory.createLoweredBevelBorder();
		c.setBorder(BorderFactory.createCompoundBorder(b1,b2));
		Insets bi = c.getInsets();
		JLabel pic = new SplashLabel(getImage(this,"images/jPhasorLogo.png"),"Loading");
		message = new MessageLabel("Loading... Splash Screen");
		c.setBackground(Color.white);
		message.setHorizontalTextPosition(JLabel.LEFT);
		message.setBackground(new Color(1f,1f,1f));
		message.setForeground(new Color(0f,0f,0f,.5f));
		c.setLayout(null);
		Dimension d = pic.getPreferredSize();
		Dimension di = new Dimension(d.width+bi.left+bi.right,d.height+bi.top+bi.bottom);
		pic.setBounds(bi.left,bi.top,d.width,d.height);
		message.setBounds(20,160,160,20);
		c.add(pic,new Integer(0));
		c.add(message, new Integer(1));
		this.setContentPane(c);
		this.setSize(di);

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
		this.setVisible(true);
	}
	public static Image getImage(Component o, String path) {
		MediaTracker tracker = new MediaTracker(o);
		URL imageURL = jphasor.DiagramWindow.class.getResource(path);
		Image img = Toolkit.getDefaultToolkit().getImage(imageURL);
		tracker.addImage(img,0);
		try {
			tracker.waitForID(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}
	public JLabel getMessage() {
		return message;
	}
	public void setMessage(String m) {
		message.setText(m);
	}
}

class MessageLabel extends JLabel {
	public MessageLabel(String s) {
		super(s);
		this.setOpaque(true);
		this.setDoubleBuffered(true);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
	}
}

class SplashLabel extends JLabel {
	Image img;
	String message;
	public SplashLabel (Image i, String m) {
		super();
		img = i;
		message = m;
		this.setOpaque(true);
		Dimension d = new Dimension(img.getWidth(this),img.getHeight(this));
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle r = g.getClipBounds();
		g.drawImage(img,r.x,r.y,r.x+r.width,r.y+r.height,r.x,r.y,r.x+r.width,r.y+r.height,this);
	}
}





