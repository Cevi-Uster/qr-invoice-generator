package ch.cevi_uster.qrinvoice.generator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import ch.cevi_uster.qrinvoice.generator.configuration.Texts;
import ch.cevi_uster.qrinvoice.generator.gui.action.AboutAction;
import ch.cevi_uster.qrinvoice.generator.gui.action.CloseApplicationAction;
import ch.cevi_uster.qrinvoice.generator.gui.mainmenu.MainMenuBar;
import ch.cevi_uster.qrinvoice.generator.gui.statusbar.StatusBar;


public class MainWindow extends JFrame {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class.getName());

	private static final long serialVersionUID = 1109381891234L;

	private static final String WINDOW_WIDTH_PREF_KEY = "MainWindow.width";

	private static final String WINDOW_HEIGHT_PREF_KEY = "MainWindow.height";

	private static final String WINDOW_XPOS_PREF_KEY = "MainWindow.xPos";

	private static final String WINDOW_YPOS_PREF_KEY = "MainWindow.yPos";

	private static final String WINDOW_EXTENDED_STATE_KEY = "MainWindow.extendedState";

	private Preferences prefs;



	private MainMenuBar mainMenuBar;

	
	
	private StatusBar statusBar;

	private JScrollPane treeScrollPane;

	

	public MainWindow() throws HeadlessException {
		super(Texts.getTranslatedText(Texts.MAIN_WINDOW_TITLE));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		this.prefs = Preferences.userRoot().node(this.getClass().getName());

		registerWindowListeners();
		initMainMenu();
		initMainView();
		initLogAndStatusPane();
	}

	public void start() {
		LOGGER.info("Start MainWindow with locale " + Locale.getDefault());
		restoreSizePositionAndExtendedState();
		setVisible(true);	
	}

	private void registerWindowListeners() {

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				saveSizePositionAndExtendedState();
			}

		});
	}

	private void initMainMenu() {
		this.mainMenuBar = new MainMenuBar();
		this.mainMenuBar.setExitAction(new CloseApplicationAction(this));
		this.mainMenuBar.setAboutAction(new AboutAction(this));
		this.setJMenuBar(mainMenuBar);
	}

	private void initMainView() {
		this.getContentPane().setLayout(new BorderLayout());
	}

	private void initLogAndStatusPane() {
		statusBar = new StatusBar();
		FormLayout layout = new FormLayout("fill:0px:grow", "fill:pref:grow, top:pref:none");
		JPanel southPanel = new JPanel(layout);
		CellConstraints cc = new CellConstraints();
		southPanel.add(statusBar, cc.xy(1, 2));
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	private void restoreSizePositionAndExtendedState() {
		LOGGER.debug("Restoring position and size of main window");
		int width = prefs.getInt(WINDOW_WIDTH_PREF_KEY, 800);
		int height = prefs.getInt(WINDOW_HEIGHT_PREF_KEY, 600);
		int xPos = prefs.getInt(WINDOW_XPOS_PREF_KEY, -1000);
		int yPos = prefs.getInt(WINDOW_YPOS_PREF_KEY, -1000);
		int state = prefs.getInt(WINDOW_EXTENDED_STATE_KEY, MAXIMIZED_BOTH);

		setSize(width, height);
		this.setExtendedState(state);
		if (xPos == -1000 && yPos == -1000) {
			setLocationRelativeTo(null);
		} else {
			setLocation(xPos, yPos);
		}
	}

	private void saveSizePositionAndExtendedState() {
		prefs.putInt(WINDOW_WIDTH_PREF_KEY, this.getWidth());
		prefs.putInt(WINDOW_HEIGHT_PREF_KEY, this.getHeight());
		prefs.putInt(WINDOW_XPOS_PREF_KEY, this.getLocation().x);
		prefs.putInt(WINDOW_YPOS_PREF_KEY, this.getLocation().y);
		prefs.putInt(WINDOW_EXTENDED_STATE_KEY, this.getExtendedState());
	}
}
