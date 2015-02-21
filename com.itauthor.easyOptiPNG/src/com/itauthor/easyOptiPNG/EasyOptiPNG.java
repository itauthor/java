package com.itauthor.easyOptiPNG;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class EasyOptiPNG {

	static String progName = "OptiPNG.exe";
	static String progCheckParam = "-v";

	private JFrame frmRunOptiPNG;
	private JTextField txtBox;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public static void main(String[] args) {
		// Display the GUI

		int exitCode = 1; // Assume there was a problem
		try {
			exitCode = OptiPngOperations.checkItRuns(progName, progCheckParam);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (exitCode != 0) {
			String path = System.getenv("Path");
			String errorText = "There was a problem with " + progName
					+ "\n\nCheck that " + progName + " is in your path:\n\n"
					+ path
					+ "\n\nIf that still doesn't work, let me know.  -Alistair";
			ErrorDialog.showError(errorText, "error");
			System.exit(0);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EasyOptiPNG window = new EasyOptiPNG();

					window.frmRunOptiPNG.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Get the input - i.e. path & ext

		// Send the path & ext to getFilePaths() in FileList.java

		// If all is well, and files were found,
		// Check number of files
		// If the original path selected was a directory rather than a file:
		// - if more than n files, tell user, warn it will take time, ask to
		// confirm
		// - show a dialog box listing the file(s) to be converted, ask user to
		// confirm

		// If user confirmed
		// Make a string out of the files paths, to pass to OptiPNG

		// run OptiPNG against the file paths

	}

	/**
	 * Create the application.
	 */
	public EasyOptiPNG() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRunOptiPNG = new JFrame();
		frmRunOptiPNG.getContentPane().setBackground(new Color(255, 250, 250));
		frmRunOptiPNG.setResizable(false);
		frmRunOptiPNG.setTitle("Image Optimizer");
		frmRunOptiPNG.setBounds(100, 100, 450, 273);
		frmRunOptiPNG.setLocationRelativeTo(null);
		frmRunOptiPNG.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRunOptiPNG.getContentPane().setLayout(null);

		Image im = null;
		try {
			im = ImageIO.read(ResourceLoader.load("/icons/png-icon.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		frmRunOptiPNG.setIconImage(im);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Recurse");
		chckbxNewCheckBox.setBackground(new Color(255, 250, 250));
		chckbxNewCheckBox.setMnemonic('r');
		chckbxNewCheckBox.setToolTipText("Process files in subdirectories");
		chckbxNewCheckBox.setEnabled(false);
		chckbxNewCheckBox.setIconTextGap(6);
		chckbxNewCheckBox.setBounds(341, 88, 81, 26);
		frmRunOptiPNG.getContentPane().add(chckbxNewCheckBox);

		String cwd = System.getProperty("user.dir");
		txtBox = new JTextField();
		txtBox.setMargin(new Insets(0, 7, 0, 5));
		txtBox.setBackground(Color.WHITE);
		txtBox.setText(cwd);
		txtBox.setBounds(10, 132, 424, 26);
		frmRunOptiPNG.getContentPane().add(txtBox);
		txtBox.setColumns(10);

		final JRadioButton rdbtnSingleFile = new JRadioButton("A single file");
		rdbtnSingleFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				promptForFile(false);
			}
		});
		rdbtnSingleFile.setBackground(new Color(255, 250, 250));
		rdbtnSingleFile.setMnemonic('s');
		rdbtnSingleFile.setIconTextGap(8);
		buttonGroup.add(rdbtnSingleFile);
		rdbtnSingleFile.setBounds(30, 40, 109, 23);
		frmRunOptiPNG.getContentPane().add(rdbtnSingleFile);

		final JRadioButton rdbtnChooseDirectory = new JRadioButton(
				"All PNGs and GIFs in a directory");
		rdbtnChooseDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				promptForFile(true);
			}
		});
		rdbtnChooseDirectory.setBackground(new Color(255, 250, 250));
		rdbtnChooseDirectory.setMnemonic('d');
		rdbtnChooseDirectory.setIconTextGap(8);
		buttonGroup.add(rdbtnChooseDirectory);
		rdbtnChooseDirectory.setBounds(30, 66, 308, 23);
		frmRunOptiPNG.getContentPane().add(rdbtnChooseDirectory);

		final JRadioButton rdbtnCurrentDirectory = new JRadioButton(
				"All PNGs and GIFs in the current directory");
		rdbtnCurrentDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBox.setText(System.getProperty("user.dir"));
			}
		});
		rdbtnCurrentDirectory.setBackground(new Color(255, 250, 250));
		rdbtnCurrentDirectory.setMnemonic('u');
		rdbtnCurrentDirectory.setIconTextGap(8);
		rdbtnCurrentDirectory.setSelected(true);
		buttonGroup.add(rdbtnCurrentDirectory);
		rdbtnCurrentDirectory.setBounds(30, 92, 300, 23);
		frmRunOptiPNG.getContentPane().add(rdbtnCurrentDirectory);

		JLabel lblRunOptipngOn = new JLabel("Optimize:");
		lblRunOptipngOn.setBounds(30, 19, 176, 14);
		frmRunOptiPNG.getContentPane().add(lblRunOptipngOn);

		/* ************************************************** */
		// **********RUN THE CONVERSION PROCESS **************
		JButton btnRun = new JButton("OK");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnSingleFile.isSelected()) {
					System.out.println("SELECTED: single file");
				} else if (rdbtnChooseDirectory.isSelected()) {
					System.out.println("SELECTED: choose a dir");
				} else {
					System.out.println("SELECTED: current dir");
				}

				String fName = txtBox.getText();
				String fType = "d"; // Types: d=directory (non-recursive),
									// f=single file, r=directory and
									// subdirectories (recursion)
				if (rdbtnSingleFile.isSelected()) {
					fType = "f";
				} else {
					// if recursion selected then fType = "r";
				}

				OutputAndResult outputAndExitcode = new OutputAndResult("", 1);

				// optipng -nc -nb -backup -o7
				// TODO: change the -o7 to user selection of 0, 1 or 7
				// need to add a 3rd argument to the getOutputAndResult method
				// for the compressionlevel

				try {
					outputAndExitcode = OptiPngOperations.run(progName, fType, fName);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				System.out.println(outputAndExitcode.getOutput()
						+ "\nExit code: " + outputAndExitcode.getResult());
				if (outputAndExitcode.getResult() != 0) {
					ErrorDialog.showError(outputAndExitcode.getOutput(),
							"error");
				} else {
					ErrorDialog.showError(outputAndExitcode.getOutput(), "info");
				}
			}
		});
		btnRun.setSelected(true);
		btnRun.setMnemonic('R');
		btnRun.setBounds(116, 192, 89, 23);
		frmRunOptiPNG.getContentPane().add(btnRun);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancel.setMnemonic('C');
		btnCancel.setBounds(238, 192, 89, 23);
		frmRunOptiPNG.getContentPane().add(btnCancel);
		frmRunOptiPNG.getContentPane().setFocusTraversalPolicy(
				new FocusTraversalOnArray(new Component[] { btnRun, btnCancel,
						rdbtnSingleFile, rdbtnChooseDirectory,
						rdbtnCurrentDirectory, txtBox }));

		InitialFocusSetter.setInitialFocus(frmRunOptiPNG, btnRun);

		JLabel lblNoteAllGifs = new JLabel(
				"Note: GIFs will be converted into PNGs, which will break hrefs.");
		lblNoteAllGifs.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblNoteAllGifs.setRequestFocusEnabled(false);
		lblNoteAllGifs.setEnabled(false);
		lblNoteAllGifs.setFocusable(false);
		lblNoteAllGifs.setFocusTraversalKeysEnabled(false);
		lblNoteAllGifs.setForeground(Color.GRAY);
		lblNoteAllGifs.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNoteAllGifs.setBounds(10, 163, 352, 14);
		frmRunOptiPNG.getContentPane().add(lblNoteAllGifs);

		JCheckBox chckbxLog = new JCheckBox("Log");
		chckbxLog.setToolTipText("Process files in subdirectories");
		chckbxLog.setMnemonic('L');
		chckbxLog.setIconTextGap(6);
		chckbxLog.setEnabled(false);
		chckbxLog.setBackground(new Color(255, 250, 250));
		chckbxLog.setBounds(341, 66, 81, 26);
		frmRunOptiPNG.getContentPane().add(chckbxLog);

	}

	private void promptForFile(boolean filesAndDirs) {
		// parent component of the dialog:
		JFrame parentFrame = new JFrame();

		setupUIManager(); // Change the ugly tooltip for the Cancel button

		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter pngFilter = new FileNameExtensionFilter(
				"PNG files only", "png");
		FileNameExtensionFilter pngGifFilter = new FileNameExtensionFilter(
				"PNGs and GIFs", "png", "gif");
		fileChooser.addChoosableFileFilter(pngFilter);
		fileChooser.addChoosableFileFilter(pngGifFilter);
		fileChooser.setFileFilter(pngFilter);

		fileChooser
				.setApproveButtonToolTipText("Choose what you want to process");
		// Note: fileChooser.setApproveButtonMnemonic('e'); has no effect

		fileChooser.setDialogTitle("Choose a file to process");
		if (filesAndDirs) {
			fileChooser
					.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fileChooser.setDialogTitle("Choose a directory to process");
		}
		int userSelection = fileChooser.showDialog(parentFrame, "Select");

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToProcess = fileChooser.getSelectedFile();
			String filePath = fileToProcess.getAbsolutePath();
			System.out.println("Chosen file or directory: " + filePath); // CONSOLE
																			// OUTPUT
			txtBox.setText(filePath);
		}
	}

	public static void setupUIManager() {
		// Change the otherwise inaccessible FileChooser strings
		// UIManager.put("FileChooser.cancelButtonText", "Cancel-test");
		UIManager.put("FileChooser.cancelButtonToolTipText",
				"Cancel without choosing anything");
	}
}

class InitialFocusSetter {
	public static void setInitialFocus(Window w, Component c) {
		w.addWindowListener(new FocusSetter(c));
	}
}

class FocusSetter extends WindowAdapter {
	Component initComp;

	FocusSetter(Component c) {
		initComp = c;
	}

	public void windowOpened(WindowEvent e) {
		initComp.requestFocus();
		e.getWindow().removeWindowListener(this);
	}
}
