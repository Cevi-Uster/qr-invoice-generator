/**
 * 
 */
package ch.cevi_uster.qrinvoice.generator;

import javax.swing.SwingUtilities;

import ch.cevi_uster.qrinvoice.generator.gui.MainWindow;

/**
 * @author mbaumgar
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainWindow().start());
	}

}
