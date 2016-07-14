package Mahout;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.apache.mahout.cf.taste.common.TasteException;

		 
		public class Test extends JPanel
		                             implements ActionListener {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			protected static final String textFieldString = "Username";
		    protected static final String passwordFieldString = "User-based";
		    protected static final String ftfString = "Item-Based";
		    protected static final String buttonString = "JButton";
		    protected static TasteException exc=null;
		 
		    protected JLabel actionLabel;
		    protected JTextArea textArea;
		    protected JTextArea statArea;
		    protected JTextArea tagArea;
		    protected static JRecom recommender;
		    public Test() {
		        setLayout(new BorderLayout());
		 
		        //Create a regular text field.
		        JTextField textField = new JTextField(10);
		        textField.setActionCommand(textFieldString);
		        textField.addActionListener(this);
		 
		        //Create a password field.
		        JTextField passwordField = new JPasswordField(10);
		        passwordField.setActionCommand(passwordFieldString);
		        passwordField.addActionListener(this);
		 
		        //Create a formatted text field.
		        JTextField ftf = new JTextField(10);
		        ftf.setActionCommand(textFieldString);
		        ftf.addActionListener(this);
		 
		        //Create some labels for the fields.
		        JLabel textFieldLabel = new JLabel(textFieldString + ": ");
		        textFieldLabel.setLabelFor(textField);
		        JLabel passwordFieldLabel = new JLabel(passwordFieldString + ": ");
		        passwordFieldLabel.setLabelFor(passwordField);
		        JLabel ftfLabel = new JLabel(ftfString + ": ");
		        ftfLabel.setLabelFor(ftf);
		 
		        //Create a label to put messages during an action event.
		        actionLabel = new JLabel("Digita il tuo username e premi ENTER.");
		        actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		 
		        //Lay out the text controls and the labels.
		        JPanel textControlsPane = new JPanel();
		        GridBagLayout gridbag = new GridBagLayout();
		        GridBagConstraints c = new GridBagConstraints();
		 
		        textControlsPane.setLayout(gridbag);
		 
		        JLabel[] labels = {textFieldLabel};
		        JTextField[] textFields = {textField};
		        addLabelTextRows(labels, textFields, gridbag, textControlsPane);
		 
		        c.gridwidth = GridBagConstraints.REMAINDER; //last
		        c.anchor = GridBagConstraints.WEST;
		        c.weightx = 1.0;
		        textControlsPane.add(actionLabel, c);
		        textControlsPane.setBorder(
		                BorderFactory.createCompoundBorder(
		                                BorderFactory.createTitledBorder("Utente"),
		                                BorderFactory.createEmptyBorder(5,5,5,5)));
		 
		        //Create a text area.
		         textArea = new JTextArea(
		                "Qui compariranno le raccomandazioni per utenti simili");
		         textArea.setEditable(false);
		        textArea.setFont(new Font("Serif", Font.ITALIC, 16));
		        textArea.setLineWrap(true);
		        textArea.setWrapStyleWord(true);
		        JScrollPane areaScrollPane = new JScrollPane(textArea);
		        areaScrollPane.setVerticalScrollBarPolicy(
		                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		        areaScrollPane.setPreferredSize(new Dimension(250, 250));
		        areaScrollPane.setBorder(
		            BorderFactory.createCompoundBorder(
		                BorderFactory.createCompoundBorder(
		                                BorderFactory.createTitledBorder("Collaborative Filtering"),
		                                BorderFactory.createEmptyBorder(5,5,5,5)),
		                areaScrollPane.getBorder()));
		 
		        //Create an editor pane.
		        statArea = createEditorPane();
		        JScrollPane editorScrollPane = new JScrollPane(statArea);
		        editorScrollPane.setVerticalScrollBarPolicy(
		                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		        editorScrollPane.setPreferredSize(new Dimension(250, 145));
		        editorScrollPane.setMinimumSize(new Dimension(10, 10));
		 
		        //Create a text pane.
		        tagArea = new JTextArea("Qui compariranno le tue tag ");
		        
		        JScrollPane paneScrollPane = new JScrollPane(tagArea);
		        paneScrollPane.setVerticalScrollBarPolicy(
		                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		        paneScrollPane.setPreferredSize(new Dimension(250, 155));
		        paneScrollPane.setMinimumSize(new Dimension(10, 10));
		 
		        //Put the editor pane and the text pane in a split pane.
		        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
		                                              editorScrollPane,
		                                              paneScrollPane);
		        splitPane.setOneTouchExpandable(true);
		        splitPane.setResizeWeight(0.5);
		        JPanel rightPane = new JPanel(new GridLayout(1,0));
		        rightPane.add(splitPane);
		        rightPane.setBorder(BorderFactory.createCompoundBorder(
		                        BorderFactory.createTitledBorder("Raccomandazioni user-based"),
		                        BorderFactory.createEmptyBorder(5,5,5,5)));
		 
		 
		        //Put everything together.
		        JPanel leftPane = new JPanel(new BorderLayout());
		        leftPane.add(textControlsPane, 
		                     BorderLayout.PAGE_START);
		        leftPane.add(areaScrollPane,
		                     BorderLayout.CENTER);
		 
		        add(leftPane, BorderLayout.LINE_START);
		        add(rightPane, BorderLayout.LINE_END);
		    }
		 
		    private void addLabelTextRows(JLabel[] labels,
		                                  JTextField[] textFields,
		                                  GridBagLayout gridbag,
		                                  Container container) {
		        GridBagConstraints c = new GridBagConstraints();
		        c.anchor = GridBagConstraints.EAST;
		        int numLabels = labels.length;
		 
		        for (int i = 0; i < numLabels; i++) {
		            c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
		            c.fill = GridBagConstraints.NONE;      //reset to default
		            c.weightx = 0.0;                       //reset to default
		            container.add(labels[i], c);
		 
		            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
		            c.fill = GridBagConstraints.HORIZONTAL;
		            c.weightx = 1.0;
		            container.add(textFields[i], c);
		        }
		    }
		 
		    public void actionPerformed(ActionEvent e) {
		    	float percentage;
		    	ArrayList<String> result=null;
		        /*
		        if (textFieldString.equals(e.getActionCommand())) {
		            JTextField source = (JTextField)e.getSource();
		            actionLabel.setText(prefix + source.getText() + "\"");
		        } else if (passwordFieldString.equals(e.getActionCommand())) {
		            JPasswordField source = (JPasswordField)e.getSource();
		            actionLabel.setText(prefix + new String(source.getPassword())
		                                + "\"");
		        } else if (buttonString.equals(e.getActionCommand())) {
		            Toolkit.getDefaultToolkit().beep();
		        }*/
	            JTextField source = (JTextField)e.getSource();
		        try {
		        	System.out.println(new String(source.getText()));
					result=recommender.recommendThings(new String(source.getText()));
					
				} catch (TasteException e1) {
					exc=e1;
					
					e1.printStackTrace();
				}
		        
		        
		        try {
					percentage=Filemanaging.elaboraTag(new String(source.getText()), result);
					String str= String.format("%2.02f", percentage);
					actionLabel.setText("Raccomandazioni simili al "+str+"%");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        if(exc!=null){textArea.setText("Il nome cercato non esiste nel db!"); exc=null;}
		        else{
                try
                {
                    FileReader reader = new FileReader( "UserRecommendations.txt" );
                    BufferedReader br = new BufferedReader(reader);
                    textArea.read( br, null );
                    br.close();
                    textArea.requestFocus();
                }
                catch(Exception e2) { System.out.println(e2); }
		        }
                try
                {
                    FileReader reader = new FileReader( "RecommendTags.txt" );
                    BufferedReader br = new BufferedReader(reader);
                    statArea.read( br, null );
                    br.close();
                    statArea.requestFocus();
                }
                catch(Exception e2) { System.out.println(e2); }
                try
                {
                    FileReader reader = new FileReader( "Tags.txt" );
                    BufferedReader br = new BufferedReader(reader);
                    tagArea.read( br, null );
                    br.close();
                    tagArea.requestFocus();
                }
                catch(Exception e2) { System.out.println(e2); }
                
		    }
		 
		    private JTextArea createEditorPane() {
		        JTextArea statArea = new JTextArea();
		        statArea.setEditable(false);
		        statArea.setText("Qui compariranno le tue raccomandazioni per tag");
		        return statArea;
		    }
		 
		    protected void addStylesToDocument(StyledDocument doc) {
		        //Initialize some styles.
		        Style def = StyleContext.getDefaultStyleContext().
		                        getStyle(StyleContext.DEFAULT_STYLE);
		 
		        Style regular = doc.addStyle("regular", def);
		        StyleConstants.setFontFamily(def, "SansSerif");
		 
		        Style s = doc.addStyle("italic", regular);
		        StyleConstants.setItalic(s, true);
		 
		        s = doc.addStyle("bold", regular);
		        StyleConstants.setBold(s, true);
		 
		        s = doc.addStyle("small", regular);
		        StyleConstants.setFontSize(s, 10);
		 
		        s = doc.addStyle("large", regular);
		        StyleConstants.setFontSize(s, 16);
		 
		        s = doc.addStyle("icon", regular);
		        StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
		        ImageIcon pigIcon = createImageIcon("images/Pig.gif",
		                                            "a cute pig");
		        if (pigIcon != null) {
		            StyleConstants.setIcon(s, pigIcon);
		        }
		 
		        s = doc.addStyle("button", regular);
		        StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
		        ImageIcon soundIcon = createImageIcon("images/sound.gif",
		                                              "sound icon");
		        JButton button = new JButton();
		        if (soundIcon != null) {
		            button.setIcon(soundIcon);
		        } else {
		            button.setText("BEEP");
		        }
		        button.setCursor(Cursor.getDefaultCursor());
		        button.setMargin(new Insets(0,0,0,0));
		        button.setActionCommand(buttonString);
		        button.addActionListener(this);
		        StyleConstants.setComponent(s, button);
		    }
		 
		    /** Returns an ImageIcon, or null if the path was invalid. */
		    protected static ImageIcon createImageIcon(String path,
		                                               String description) {
		        java.net.URL imgURL = Test.class.getResource(path);
		        if (imgURL != null) {
		            return new ImageIcon(imgURL, description);
		        } else {
		            System.err.println("Couldn't find file: " + path);
		            return null;
		        }
		    }
		 
		    private static void createAndShowGUI() {
		        //Create and set up the window.
		        JFrame frame = new JFrame("JRecom");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		        //Add content to the window.
		        frame.add(new Test());
		 
		        //Display the window.
		        frame.pack();
		        frame.setVisible(true);
		    }
		 
		    public static void main(String[] args) throws FileNotFoundException {
		    	recommender=new JRecom();
		    	recommender.initRecommender();
		        SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		        UIManager.put("swing.boldMetal", Boolean.FALSE);
		        createAndShowGUI();
		            }
		        });
		    }
 
	}
