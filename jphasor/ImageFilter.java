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

import javax.swing.filechooser.FileFilter;
import java.io.File;

/* ImageFilter.java is a 1.4 example used by FileChooserDemo2.java. */
public class ImageFilter extends FileFilter {
    String type;

    public ImageFilter(String t) {
        super();
        type = t;
    }

    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
			return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(type)) {
				return true;
			} else {
				return false;
			}
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return type;
    }
}
