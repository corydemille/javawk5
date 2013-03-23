import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
	 * @author Cory DeMille
	 * PRG421 Instructor: Yen Lee
	 * Week 3 Assignment
	 * University of Phoenix
	 */

public class Wk5Cory extends javax.swing.JFrame implements ActionListener {
	
	// Declare Variables
	private double mortgage;
	private double years;
	private double monthlyPaymentAmt;
	private double rate;
	double[] termArray = {7,15,30};
	double[] rateArray = {5.35, 5.5, 5.75};
	double interestPaid = 0;
	double principal = 0;
	double loanBalance = 0;
	double interest = 0;
	public double totalInterest = 0;
	public double loanAmount = 0;
	public double monthlyPayment = 0;
	public int customLoan;
	
	// Format
	DecimalFormat dFormat = new DecimalFormat("$#,##0.00");

    /** Creates new form Mortgage Converter GUI */
    public Wk5cory() {
        initComponents();
    }

    @SuppressWarnings("unchecked")

    private void initComponents() {
    	
    	// GUI visuals
        mPaymentField = new javax.swing.JTextField(); // Loan amount input
        loanAmtLabel = new javax.swing.JLabel(); // Loan amount label
        calculate = new javax.swing.JButton();  // Calculate button
        loanOptionsArray = new javax.swing.JComboBox(); // Loan options array
        mPaymentReturn = new javax.swing.JTextField(); // Output field
        	mPaymentReturn.setEnabled(false); // Sets field to disabled
        loanOptionsLabel = new javax.swing.JLabel(); // Loan array label
        mPaymentLabel = new javax.swing.JLabel(); // Output field label
        titleLabel = new javax.swing.JLabel(); // Header
        amorizPane = new javax.swing.JScrollPane(); // Amortization Table
        amorizationTable = new javax.swing.JTextArea(); // Amortization Table
        rateInput = new javax.swing.JTextField(); // Rate input box
        	rateInput.setEnabled(false); // Default inactive
        termInput = new javax.swing.JTextField(); // Term input box
        	termInput.setEnabled(false); // Default inactive
        termLabel = new javax.swing.JLabel(); // Term label
        rateLabel = new javax.swing.JLabel(); // Rate label
        reset = new javax.swing.JButton(); // Reset Button
        
        // Close statement
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        // Labels
        loanAmtLabel.setText("Loan Amount");
        
        // Calculate Event Handler
        calculate.setText("Calculate"); 
        calculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateActionPerformed(evt);
            }
        });
        
        // Reset Event Handler
        reset.setText("Reset"); 
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        
        // Selection Event Handler
        loanOptionsArray.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "7 Year, 5.35%", "15 Year, 5.5%", "30 Year, 5.75%", "Custom" })); // Drop down choice
        loanOptionsArray.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanOptionsArrayActionPerformed(evt);
            }
        });
        
        // Labels
        loanOptionsLabel.setText("Loan Options");
        mPaymentLabel.setText("Monthly Payment");
        titleLabel.setText("Calculate Mortgage");
        amorizationTable.setColumns(20);
        amorizationTable.setRows(5);
        amorizPane.setViewportView(amorizationTable);
        termLabel.setText("Term");
        rateLabel.setText("Rate");

        // GUI Layout - Uses Group layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mPaymentReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mPaymentLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(calculate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reset))
                    .addComponent(amorizPane, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titleLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mPaymentField, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loanAmtLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(termInput, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rateInput)
                            .addComponent(loanOptionsArray, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loanOptionsLabel))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rateLabel)
                                    .addComponent(termLabel))
                                .addGap(49, 49, 49)))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {calculate, loanOptionsArray, mPaymentField, mPaymentReturn});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mPaymentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loanAmtLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loanOptionsArray, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loanOptionsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rateInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(termInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(termLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mPaymentReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mPaymentLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calculate)
                    .addComponent(reset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(amorizPane, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pack();
    }
    // End GUI Layout
    
    // Calculation class
    public void MonthlyPaymentCalculation()
    {
        monthlyPaymentAmt = ((rate / 12) * mortgage) / (1- (Math.pow (1+ (rate / 12), (years * -12))));
    }
    
    // Draws Graph Window
    public static void Drawing()
    {
    	JFrame graph = new JFrame(); // Creates new frame
    	
    	graph.setSize(300, 400); // Window size
    	graph.setLocation(465,0); // Location of window
    	graph.setTitle("Graph Display"); // Window title
    	graph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit statement
    	graph.add(new DisplayChart()); // Displays DisplayChart.java
    	
    	graph.setVisible(true); // Sets visibility
    }

	// Input "data.txt"
    public static void FileInput()
    {
    	File aFile = new File("data.txt"); // File name
                FileInputStream inFile = null;
                try { //File not found catch. File must exist.
                    inFile = new FileInputStream(aFile);
                } catch(FileNotFoundException e) {
                    e.printStackTrace(System.err);
                    System.exit(1);
                }
                FileChannel inChannel = inFile.getChannel();
                final int RATECOUNT = 6; //Number of elements to extract from the file
                ByteBuffer buf = ByteBuffer.allocate(8*RATECOUNT); // Sets buffer for double
                double[] rates = new double[RATECOUNT];
                try {
                    int ratesRead = 0;
                    while(inChannel.read(buf) != -1) {
                        DoubleBuffer doubleBuf = ((ByteBuffer)(buf.flip())).asDoubleBuffer(); // Buffer setup
                        ratesRead = doubleBuf.remaining();
                        doubleBuf.get(rates, 0, doubleBuf.remaining());
 
                        for(int i = 0; i < ratesRead; i++) { //gets the rates and years
 
                        }
                        aFile.deleteOnExit(); //Deletes the text file when the program exits
                        buf.clear(); // Clear buffer
                    }
                    inFile.close(); // Close
                     
                } catch(IOException e) { // Exception Statement
                    e.printStackTrace(System.err);
                    System.exit(1);
                }

    }

    // Event actions
    private void loanOptionsArrayActionPerformed(java.awt.event.ActionEvent evt) { // Menu Enable listener
        // Hide the rate and term input unless "Custom" is selected from the drop down menu.
    	customLoan = loanOptionsArray.getSelectedIndex();
    	
    	if (customLoan == 3)
    	{
    		// Enables text box for custom entry
    		rateInput.setEnabled(true);
    		termInput.setEnabled(true);
    	} else 
    	{
    		// Hides text box for defined entry
    		rateInput.setEnabled(false);
    		termInput.setEnabled(false);
    	}
    }
    private void calculateActionPerformed(java.awt.event.ActionEvent evt) { // Calculate Event Listener
    	try 
    	{
    		// File input statement
    		/*FileInput();*/  // Causes error
    		
    		// Gets users Mortgage value input
    		String userInput;
    		userInput = mPaymentField.getText(); 
    		mortgage = Integer.parseInt(userInput);
    		
    		// Allows custom input
    		customLoan = loanOptionsArray.getSelectedIndex();
    		if (customLoan == 3) // If selection == Custom
    		{
    			String userRate; // Sets userRate to string
				userRate = rateInput.getText(); // gets value from rateInput text box
				double userRateConv = Double.parseDouble(userRate); // user input change to double
				String userTerm; // Sets userTerm to string
				userTerm = termInput.getText(); // gets value from termInput text box
				double userTermConv = Integer.parseInt(userTerm); // user input change
				rate = userRateConv / 100; // sets value for calculation
				years = userTermConv; // sets value for calculation
    		} else // Gets array value if selecting defined variables
    		{
    			rate = rateArray[loanOptionsArray.getSelectedIndex()]/100 ; // Gets array input
    			years = termArray[loanOptionsArray.getSelectedIndex()]; // Gets array input
    		}
    		
    		MonthlyPaymentCalculation(); // Invokes calculation statement
    		mPaymentReturn.setText(String.valueOf(dFormat.format(monthlyPaymentAmt))); // Outputs the monthly payment
    		
    		// Calculations
    		monthlyPayment = monthlyPaymentAmt; // Sets monthly payment for calculation
    		loanAmount = monthlyPayment * (years * 12); // Loan Amount
    		interest = rate / 12; // Interest Rate
    		principal = mortgage; // Principal (Amount borrowed)
    		
    		amorizationTable.setText("Payment #\t Interest Paid\t\tLoan Balance\n"); // Sets column headers
    		Drawing();
    		repaint(); // Re-draws window if other selections are made
    		
    		for(int counter = 1; counter <= years*12 ; counter++) // Loop
			{
				// Loop calculations
				interestPaid = (principal * interest);
				principal = principal - (monthlyPayment - interestPaid);
				loanAmount = (loanAmount - monthlyPayment);

				// Display Amortization Table
				amorizationTable.setCaretPosition(0);
				amorizationTable.append("     " +(counter) + "\t  "+dFormat.format(interestPaid)+"\t\t"+ dFormat.format(loanAmount)+"\n");
			}
    	} catch (Exception ex) {
            titleLabel.setText("Loan Amount cannot be blank."); // Exception statement
        }
        
    }
    
    // Resets the fields
    private void resetActionPerformed(java.awt.event.ActionEvent evt) {                                  
    	rateInput.setText(null);
    	termInput.setText(null);
    	mPaymentField.setText(null);
    	mPaymentReturn.setText(null);
    	amorizationTable.setText(null);
    	loanOptionsArray.setSelectedIndex(0);    	
    }        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Wk5cory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Wk5cory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Wk5cory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Wk5cory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // End look and feel

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Wk5cory().setVisible(true);
            }
        });
    }
    // Variables declaration
    private javax.swing.JTextArea amorizationTable;
    private javax.swing.JButton calculate;
    private javax.swing.JLabel loanAmtLabel;
    private javax.swing.JComboBox loanOptionsArray;
    private javax.swing.JLabel loanOptionsLabel;
    private javax.swing.JTextField mPaymentField;
    private javax.swing.JLabel mPaymentLabel;
    private javax.swing.JTextField mPaymentReturn;
    private static javax.swing.JLabel titleLabel;
    private javax.swing.JScrollPane amorizPane;
    private javax.swing.JTextField rateInput;
    private javax.swing.JLabel rateLabel;
    private javax.swing.JTextField termInput;
    private javax.swing.JLabel termLabel;
    private javax.swing.JButton reset;
    // End of variables declaration

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}