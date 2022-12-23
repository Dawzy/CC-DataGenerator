package DataGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DataGenerator extends JPanel {
	final static int WINDOW_WIDTH = 800;
	final static int WINDOW_HEIGHT = 600;

	static JFrame frame = new JFrame("Data Generator");
	static File dataFileName = new File("data.txt");

	static Color bgColor;
	static Color fgColor;

	public DataGenerator() {
		// Add buttons
		Button notClearButton = new Button("Barely Readable");
		Button somewhatButton = new Button("Somewhat Readable");
		Button clearButton = new Button("Very Readable");

		// Set buttons' bounds
		notClearButton.setBounds(0, WINDOW_HEIGHT - 100, WINDOW_WIDTH / 3, 100);
		somewhatButton.setBounds(WINDOW_WIDTH / 3, WINDOW_HEIGHT - 100, WINDOW_WIDTH / 3, 100);
		clearButton.setBounds(2 * WINDOW_WIDTH / 3, WINDOW_HEIGHT - 100, WINDOW_WIDTH / 3, 100);

		// Add buttons to frame
		frame.add(notClearButton);
		frame.add(somewhatButton);
		frame.add(clearButton);

		// Add action listeners
		notClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorsRated(0);
			}
		});

		somewhatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorsRated(1);
			}
		});

		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorsRated(2);
			}
		});

		repaint();
	}

	/*
	 * Called everytime repaint() or paintImmediately is called.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		g.setColor(bgColor);
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		g.setColor(fgColor);
		g.drawString("How clear is this text?", WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT / 2);
	}

	public static void main(String[] args) {
		// Init some colors
		bgColor = generateColor();
		fgColor = generateColor();

		// Boilerplate for having a window up
		frame.getContentPane().add(new DataGenerator(), BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		
		// Makes sure that the actual canvas is exactly the specified window size
		// Calculates the size of the title bar and adds its dimensions to the total width and height
		Insets insets = frame.getInsets();
		int addedW = insets.left + insets.right;
		int addedH = insets.top + insets.bottom;
		frame.setSize(WINDOW_WIDTH + addedW, WINDOW_HEIGHT + addedH);
	}

	// Methods
	void colorsRated(int rating) {
		// Truncate bg and fg colors, and rating to data file
		// Open file
		try (
			FileWriter writer = new FileWriter(dataFileName, true);
			BufferedWriter bw = new BufferedWriter(writer);
			PrintWriter out = new PrintWriter(bw)) {
			

			writer.flush();

			String line = "";

			// Bg color
			line += Float.toString((float)bgColor.getRed() / 255.0f);
			line += ",";
			line += Float.toString((float)bgColor.getGreen() / 255.0f);
			line += ",";
			line += Float.toString((float)bgColor.getBlue() / 255.0f);
			line += ",";
			
			// Fg color
			line += Float.toString((float)fgColor.getRed() / 255.0f);
			line += ",";
			line += Float.toString((float)fgColor.getGreen() / 255.0f);
			line += ",";
			line += Float.toString((float)fgColor.getBlue() / 255.0f);
			line += ",";

			// Rating
			line += Integer.toString(rating);
			line += "\n";

			// Write to file
			out.write(line);

			// Close writers
			out.close();
			bw.close();
			writer.close();

		} catch (IOException e) {
			System.out.println("Error occurred opening file.");
		}

		// Generate new colors
		bgColor = generateColor();
		fgColor = generateColor();

		// Paint
		repaint();
	}


	// Utils
	static Color generateColor() {
		return new Color(getRandInt(0, 255), getRandInt(0, 255), getRandInt(0, 255));
	}

	static int getRandInt(int min, int max) {
		return (int)(min + Math.random() * (max - min + 1));
	}
}