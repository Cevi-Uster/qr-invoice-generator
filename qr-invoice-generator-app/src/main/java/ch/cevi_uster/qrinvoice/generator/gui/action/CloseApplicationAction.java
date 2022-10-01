package ch.cevi_uster.qrinvoice.generator.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;

import ch.cevi_uster.qrinvoice.generator.configuration.Texts;
import ch.cevi_uster.qrinvoice.generator.gui.MainWindow;

public class CloseApplicationAction extends AbstractAction {

	private static final long serialVersionUID = 7720672914222472305L;

	private MainWindow mainWindow;

	public CloseApplicationAction(MainWindow mainWindow) {
		super(Texts.getTranslatedText(Texts.MENU_FILE_MENU_EXIT_ITEM));
		this.mainWindow = mainWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING));
	}

}
