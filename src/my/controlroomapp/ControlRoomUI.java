/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ControlRoomUI.java
 *
 * Created on May 24, 2010, 4:01:26 PM
 */
package my.controlroomapp;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.JTextComponent;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Riad
 */
public class ControlRoomUI extends javax.swing.JFrame {

    /*
     * Class ControlRoomUI Constuctor
     * @parameter adminlogin designates if application in ADMIN mode
     * ADMIN has the right to reset SIM REPLACEMENT eligibility
     */
    //public ControlRoomUI(INCommander INCmd, MSCCommander MSCCmd, String username, boolean adminlogin) {
    private java.util.Vector<String> requesters;
    private ListSelectionHandler rsHandler;

    public ControlRoomUI(INCommander INCmd, HuaweiHLR MSCCmd, String username, short role) {
        super("Control Room App");
        this.INCmd = INCmd;
        this.mscCmd = MSCCmd;
        this.userRole = role;
        this.userName = username;

        Thread appLogThread;

        try {
            appLog = new AppLogger(
                    INCmd.getDBConnector().getDBConnection(DBConnector.ControlRoomDBId),
                    INCmd.getDBConnector().getDBConnection(DBConnector.CSTMRDBId),
                    INCmd.getSessionID());
            appLogThread = new Thread(appLog, "AppLoggerThread: " + INCmd.getSessionID());
            appLogThread.start();
        } catch (java.io.IOException ioe) {
            javax.swing.JOptionPane.showMessageDialog(ControlRoomUI.this, "Could not load error log file.");
        } catch (java.sql.SQLException sqle) {
            javax.swing.JOptionPane.showMessageDialog(ControlRoomUI.this, "Could not connect to logger DB.");
            System.out.println(sqle.getMessage());
        }

        initComponents();

        this.postpaidRadioButton.setEnabled(this.userRole == 1);
        this.postpaidRadioButton.setVisible(this.userRole == 1);
        this.postpaidComboBox.setVisible(this.userRole == 1);
        this.minAmountField.setVisible(this.userRole == 1);
        this.minAmtLabel.setVisible(this.userRole == 1);
        hideMSCFields();
        this.replaceSimButton.setEnabled(false);
        this.oldIMSItextField.setEditable(false);
        this.newSIMTextField.setEditable(false);
        this.TokNumAdjButton.setVisible((this.userRole == 1) || (this.userRole == 4));
        this.TokNumAdjButton.setEnabled(false);
        this.payBackEVCButton.setEnabled(false);
        this.payBackDlrButton.setEnabled(false);
        this.normToEVCButton.setEnabled(false);
        this.retCrdtTransButton.setEnabled(false);

        this.AppPane.remove(this.credAddPanel);

        if (this.userRole == 3) {
            this.AppPane.remove(this.displayPanel);
            this.AppPane.remove(this.servicePanel);
            this.AppPane.remove(this.mscPanel);
            this.AppPane.remove(this.EVCPanel);
            this.AppPane.remove(this.SMSDeliveryPanel);
        } else if (this.userRole == 4) {
            this.AppPane.remove(this.mscPanel);
            this.AppPane.remove(this.simRepPanel);

            this.VASPanel.setEnabled(false);
            this.VASDeactPanel.setEnabled(false);
            this.CRBTSubPanel.setEnabled(false);
            this.GPRSSubPanel.setEnabled(false);
            this.roamRadioButton.setEnabled(false);
            this.ussdRadioButton.setEnabled(false);
            this.validRadioButton.setEnabled(false);
            this.langRadioButton.setEnabled(false);
            this.postpaidRadioButton.setEnabled(false);
            this.postpaidComboBox.setEnabled(false);
            this.applyChngButton.setEnabled(false);
            this.songCodeTextField.setEnabled(false);
            this.funRingSubButton.setEnabled(false);
            this.VASList.setEnabled(false);
            this.applyChngButton1.setEnabled(false);
            this.removeServiceButton.setEnabled(false);
            this.gprsSubButton.setEnabled(false);
        } else if (this.userRole == 1) {
            try {
                this.requesters = INCmd.getRequesters();
                this.requesterComboBox.setModel(new DefaultComboBoxModel(this.requesters));
                this.buttonGroupCredAdd.setSelected(this.realBalRadioButton.getModel(), true);
            } catch (SQLException sqle) {
                JOptionPane.showMessageDialog(this.AppPane, "Could Not Load Credit Requesters", "DB Error", 0);
                System.out.println(sqle.getMessage());
            }
        } else if (this.userRole == 5) {
            this.AppPane.remove(this.servicePanel);

            this.AppPane.remove(this.EVCPanel);
            this.AppPane.remove(this.SMSDeliveryPanel);
            this.AppPane.remove(this.displayPanel);
        } else {
            this.AppPane.remove(this.SMSDeliveryPanel);
            this.AppPane.remove(this.EVCPanel);
        }

        this.setTitle("Control Room App - " + userName);
    }

    public ControlRoomUI() {
        this(null, null, null, (short) 2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroupCredAdd = new javax.swing.ButtonGroup();
        buttonGroupEVC = new javax.swing.ButtonGroup();
        AppPane = new javax.swing.JTabbedPane();
        displayPanel = new javax.swing.JPanel();
        dispSubNumField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cosTextField = new javax.swing.JTextField();
        freeMonTextField = new javax.swing.JTextField();
        freeSMSTextField = new javax.swing.JTextField();
        balTextField = new javax.swing.JTextField();
        firstCallTextField = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        lastCallTextField = new javax.swing.JTextField();
        clearDispButton = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        accntRadioButton = new javax.swing.JRadioButton();
        callsRadioButton = new javax.swing.JRadioButton();
        viewHistButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        evoucherRadioButton = new javax.swing.JRadioButton();
        creditTransRadioButton = new javax.swing.JRadioButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        LoyaltyStatusField = new javax.swing.JTextField();
        KolarehCreditField = new javax.swing.JTextField();
        servicePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        subNumField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        serviceTextArea = new javax.swing.JTextArea();
        clrMSC1 = new javax.swing.JButton();
        VASPanel = new javax.swing.JPanel();
        roamComboBox = new javax.swing.JComboBox();
        roamRadioButton = new javax.swing.JRadioButton();
        ussdRadioButton = new javax.swing.JRadioButton();
        ussdComboBox = new javax.swing.JComboBox();
        validComboBox = new javax.swing.JComboBox();
        validRadioButton = new javax.swing.JRadioButton();
        langRadioButton = new javax.swing.JRadioButton();
        langComboBox = new javax.swing.JComboBox();
        postpaidComboBox = new javax.swing.JComboBox();
        postpaidRadioButton = new javax.swing.JRadioButton();
        applyChngButton = new javax.swing.JButton();
        TOKPanel = new javax.swing.JPanel();
        tokSubChkButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        TokNumField = new javax.swing.JTextField();
        setTokButton = new javax.swing.JButton();
        TokNumAdjButton = new javax.swing.JButton();
        VASDeactPanel = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        VASList = new javax.swing.JList();
        applyChngButton1 = new javax.swing.JButton();
        removeServiceButton = new javax.swing.JButton();
        CRBTSubPanel = new javax.swing.JPanel();
        songCodeLabel = new javax.swing.JLabel();
        funRingSubButton = new javax.swing.JButton();
        songCodeTextField = new javax.swing.JTextField();
        GPRSSubPanel = new javax.swing.JPanel();
        gprsSubButton = new javax.swing.JButton();
        mscPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        commandList = new javax.swing.JList();
        MSISDNtextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        IMSItextField = new javax.swing.JTextField();
        dataDispComboBox = new javax.swing.JComboBox();
        mscParTextField = new javax.swing.JTextField();
        mscParLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        prodList = new javax.swing.JList();
        prodComboBox = new javax.swing.JComboBox();
        jScrollPane6 = new javax.swing.JScrollPane();
        MSCTextArea = new javax.swing.JTextArea();
        submitMSCcmd = new javax.swing.JButton();
        clrMSC = new javax.swing.JButton();
        CallFwdDestTextField = new javax.swing.JTextField();
        ToLabel = new javax.swing.JLabel();
        simRepPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        subNumField1 = new javax.swing.JTextField();
        replaceSimButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        simRepTextArea = new javax.swing.JTextArea();
        refChkButton = new javax.swing.JButton();
        refNumField1 = new javax.swing.JTextField();
        refNumField2 = new javax.swing.JTextField();
        refNumField3 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        clearSimButton = new javax.swing.JButton();
        oldIMSItextField = new javax.swing.JTextField();
        oldIMSILabel = new javax.swing.JLabel();
        newSIMTextField = new javax.swing.JTextField();
        newSIMLabel = new javax.swing.JLabel();
        SMSDeliveryPanel = new javax.swing.JPanel();
        SMSOrigNumField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        SMSDestNumField = new javax.swing.JTextField();
        clearSMSDelivButton = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        SMSCheckDelivButton = new javax.swing.JButton();
        SMSStartDateChooser = new com.toedter.calendar.JDateChooser();
        SMSEndDateChooser = new com.toedter.calendar.JDateChooser();
        SMSDeliveryProgressBar = new javax.swing.JProgressBar();
        EVCPanel = new javax.swing.JPanel();
        EVCSenderField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        EVCRecipientField = new javax.swing.JTextField();
        clearEVCButton = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        searchEVCButton = new javax.swing.JButton();
        EVCTransactionDateChooser = new com.toedter.calendar.JDateChooser();
        EVCAmount1Field = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        payBackEVCButton = new javax.swing.JButton();
        normToEVCButton = new javax.swing.JButton();
        retCrdtTransButton = new javax.swing.JButton();
        dealerToSubRadioButton = new javax.swing.JRadioButton();
        dealerToDealerRadioButton = new javax.swing.JRadioButton();
        ordSubRadioButton = new javax.swing.JRadioButton();
        jLabel32 = new javax.swing.JLabel();
        EVCAmount2Field = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        EVCAmount3Field = new javax.swing.JTextField();
        minAmtLabel = new javax.swing.JLabel();
        minAmountField = new javax.swing.JTextField();
        payBackDlrButton = new javax.swing.JButton();
        credAddPanel = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        crAdReceipientNumField = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        requesterComboBox = new javax.swing.JComboBox();
        jLabel30 = new javax.swing.JLabel();
        crAdAmntField = new javax.swing.JTextField();
        clrCrdAddButton = new javax.swing.JButton();
        addCredButton = new javax.swing.JButton();
        realBalRadioButton = new javax.swing.JRadioButton();
        freeMonRadioButton = new javax.swing.JRadioButton();
        jLabel31 = new javax.swing.JLabel();
        credAddCmntTextField = new javax.swing.JTextField();
        exitButton = new javax.swing.JButton();

        jPopupMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPopupMenu1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPopupMenu1MouseReleased(evt);
            }
        });

        copyMenuItem.setText("Copy");
        copyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMenuItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(copyMenuItem);

        pasteMenuItem.setText("Paste");
        pasteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteMenuItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(pasteMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Control Room App");
        setBounds(new java.awt.Rectangle(150, 160, 0, 0));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        AppPane.setPreferredSize(new java.awt.Dimension(375, 261));

        dispSubNumField.setColumns(7);
        dispSubNumField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dispSubNumFieldMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dispSubNumFieldMouseReleased(evt);
            }
        });
        dispSubNumField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispSubNumFieldActionPerformed(evt);
            }
        });

        jLabel7.setText("Subscriber Number:");

        jLabel8.setText("Class of Service:");

        jLabel9.setText("Balance:");

        jLabel10.setText("Free Money:");

        jLabel11.setText("Free SMS:");

        jLabel15.setText("First Call Date:");

        cosTextField.setEditable(false);

        freeMonTextField.setEditable(false);

        freeSMSTextField.setEditable(false);

        balTextField.setEditable(false);
        balTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balTextFieldActionPerformed(evt);
            }
        });

        firstCallTextField.setEditable(false);

        jLabel20.setText("Last Call Date:");

        lastCallTextField.setEditable(false);
        lastCallTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastCallTextFieldActionPerformed(evt);
            }
        });

        clearDispButton.setText("Clear");
        clearDispButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearDispButtonActionPerformed(evt);
            }
        });

        jTable1.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setSelectionForeground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jScrollPane7.setViewportView(jTable1);

        buttonGroup2.add(accntRadioButton);
        accntRadioButton.setText("Account");
        accntRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accntRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup2.add(callsRadioButton);
        callsRadioButton.setText("Calls");
        callsRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                callsRadioButtonActionPerformed(evt);
            }
        });

        viewHistButton.setText("View History");
        viewHistButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewHistButtonActionPerformed(evt);
            }
        });

        jLabel12.setText("(Last 50 Records)");

        buttonGroup2.add(evoucherRadioButton);
        evoucherRadioButton.setText("E-Voucher");
        evoucherRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evoucherRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup2.add(creditTransRadioButton);
        creditTransRadioButton.setText("Credit Transfer");
        creditTransRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditTransRadioButtonActionPerformed(evt);
            }
        });

        jLabel27.setText("Kolareh Credit:");

        jLabel34.setText("Kolareh Status:");

        LoyaltyStatusField.setEditable(false);

        KolarehCreditField.setEditable(false);
        KolarehCreditField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KolarehCreditFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(displayPanelLayout.createSequentialGroup()
                        .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)
                            .addComponent(jLabel15)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(displayPanelLayout.createSequentialGroup()
                                .addComponent(dispSubNumField, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(clearDispButton))
                            .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(freeSMSTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                                .addComponent(freeMonTextField, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lastCallTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                                .addComponent(firstCallTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(balTextField, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(cosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(callsRadioButton)
                            .addGroup(displayPanelLayout.createSequentialGroup()
                                .addComponent(accntRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(viewHistButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12))
                            .addComponent(evoucherRadioButton)
                            .addComponent(creditTransRadioButton))
                        .addGap(212, 212, 212))
                    .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(displayPanelLayout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel27)
                                .addComponent(jLabel34))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(LoyaltyStatusField, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                .addComponent(KolarehCreditField))
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(displayPanelLayout.createSequentialGroup()
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 944, Short.MAX_VALUE)
                            .addContainerGap()))))
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(displayPanelLayout.createSequentialGroup()
                        .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dispSubNumField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearDispButton)
                            .addComponent(jLabel7)
                            .addComponent(viewHistButton)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(freeMonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(freeSMSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(balTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(firstCallTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(lastCallTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(displayPanelLayout.createSequentialGroup()
                        .addComponent(accntRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(callsRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(evoucherRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(creditTransRadioButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(LoyaltyStatusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(KolarehCreditField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        displayPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {balTextField, cosTextField, firstCallTextField, freeMonTextField, freeSMSTextField, lastCallTextField});

        AppPane.addTab("Display Sub", displayPanel);

        servicePanel.setEnabled(roamRadioButton.isSelected());
        servicePanel.setPreferredSize(new java.awt.Dimension(375, 233));

        jLabel2.setText("Subscriber Number:");

        subNumField2.setColumns(7);
        subNumField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                subNumField2MouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                subNumField2MousePressed(evt);
            }
        });
        subNumField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subNumField2ActionPerformed(evt);
            }
        });

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        serviceTextArea.setColumns(20);
        serviceTextArea.setEditable(false);
        serviceTextArea.setLineWrap(true);
        serviceTextArea.setRows(5);
        serviceTextArea.setWrapStyleWord(true);
        serviceTextArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        serviceTextArea.setDoubleBuffered(true);
        jScrollPane1.setViewportView(serviceTextArea);

        clrMSC1.setText("Clear");
        clrMSC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clrMSC1ActionPerformed(evt);
            }
        });

        VASPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Class of Service", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 1, 12))); // NOI18N

        roamComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enable", "Disable" }));
        roamComboBox.setDoubleBuffered(true);
        roamComboBox.setEnabled(roamRadioButton.isSelected());
        roamComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roamComboBoxActionPerformed(evt);
            }
        });

        buttonGroup1.add(roamRadioButton);
        roamRadioButton.setText("Roaming");
        roamRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roamRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(ussdRadioButton);
        ussdRadioButton.setText("USSD");
        ussdRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ussdRadioButtonActionPerformed(evt);
            }
        });

        ussdComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enable", "Disable" }));
        ussdComboBox.setDoubleBuffered(true);
        ussdComboBox.setEnabled(ussdRadioButton.isSelected());

        validComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "6 months", "1 year" }));
        validComboBox.setDoubleBuffered(true);
        validComboBox.setEnabled(validRadioButton.isSelected());

        buttonGroup1.add(validRadioButton);
        validRadioButton.setText("Africell Validitiy");
        validRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(langRadioButton);
        langRadioButton.setText("Language");
        langRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                langRadioButtonActionPerformed(evt);
            }
        });

        langComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"English", "French", "Wolof", "Mandinka"}));
        langComboBox.setDoubleBuffered(true);
        langComboBox.setEnabled(langRadioButton.isSelected());

        postpaidComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enable", "Disable" }));
        postpaidComboBox.setDoubleBuffered(true);
        postpaidComboBox.setEnabled(postpaidRadioButton.isSelected());

        buttonGroup1.add(postpaidRadioButton);
        postpaidRadioButton.setText("Postpaid");
        postpaidRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postpaidRadioButtonActionPerformed(evt);
            }
        });

        applyChngButton.setText("Apply Changes");
        applyChngButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyChngButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout VASPanelLayout = new javax.swing.GroupLayout(VASPanel);
        VASPanel.setLayout(VASPanelLayout);
        VASPanelLayout.setHorizontalGroup(
            VASPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, VASPanelLayout.createSequentialGroup()
                .addContainerGap(233, Short.MAX_VALUE)
                .addComponent(applyChngButton)
                .addContainerGap())
            .addGroup(VASPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(VASPanelLayout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addGroup(VASPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(postpaidRadioButton)
                        .addComponent(langRadioButton)
                        .addComponent(validRadioButton)
                        .addComponent(ussdRadioButton)
                        .addComponent(roamRadioButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(VASPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(postpaidComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(langComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(VASPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(validComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ussdComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roamComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(110, Short.MAX_VALUE)))
        );
        VASPanelLayout.setVerticalGroup(
            VASPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, VASPanelLayout.createSequentialGroup()
                .addContainerGap(179, Short.MAX_VALUE)
                .addComponent(applyChngButton)
                .addContainerGap())
            .addGroup(VASPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(VASPanelLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addGroup(VASPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(roamRadioButton)
                        .addComponent(roamComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(VASPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ussdRadioButton)
                        .addComponent(ussdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(7, 7, 7)
                    .addGroup(VASPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(validRadioButton)
                        .addComponent(validComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(VASPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(langRadioButton)
                        .addComponent(langComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(VASPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(postpaidRadioButton)
                        .addComponent(postpaidComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(50, Short.MAX_VALUE)))
        );

        TOKPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TOK", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 1, 12))); // NOI18N

        tokSubChkButton.setText("Check Subscription");
        tokSubChkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tokSubChkButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("TOK Number:");

        TokNumField.setColumns(7);
        TokNumField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TokNumFieldMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TokNumFieldMouseReleased(evt);
            }
        });
        TokNumField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TokNumFieldActionPerformed(evt);
            }
        });

        setTokButton.setText("Set TOK Num");
        setTokButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setTokButtonActionPerformed(evt);
            }
        });

        TokNumAdjButton.setText("Modify TOK Num");
        TokNumAdjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TokNumAdjButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TOKPanelLayout = new javax.swing.GroupLayout(TOKPanel);
        TOKPanel.setLayout(TOKPanelLayout);
        TOKPanelLayout.setHorizontalGroup(
            TOKPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TOKPanelLayout.createSequentialGroup()
                .addGroup(TOKPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TOKPanelLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(tokSubChkButton))
                    .addGroup(TOKPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(TOKPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(TOKPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(TokNumAdjButton)
                                .addGap(18, 18, 18)
                                .addComponent(setTokButton))
                            .addGroup(TOKPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TokNumField)))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        TOKPanelLayout.setVerticalGroup(
            TOKPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TOKPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tokSubChkButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(TOKPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TokNumField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(TOKPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TokNumAdjButton)
                    .addComponent(setTokButton))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        VASDeactPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VAS Deactivation", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 1, 12))); // NOI18N

        VASList.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        VASList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        VASList.setMaximumSize(new java.awt.Dimension(25, 0));
        VASList.setVisibleRowCount(5);
        VASList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                VASListValueChanged(evt);
            }
        });
        jScrollPane8.setViewportView(VASList);

        applyChngButton1.setText("View Subscribed Services");
        applyChngButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyChngButton1ActionPerformed(evt);
            }
        });

        removeServiceButton.setText("Remove");
        removeServiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeServiceButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout VASDeactPanelLayout = new javax.swing.GroupLayout(VASDeactPanel);
        VASDeactPanel.setLayout(VASDeactPanelLayout);
        VASDeactPanelLayout.setHorizontalGroup(
            VASDeactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, VASDeactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(VASDeactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeServiceButton)
                    .addComponent(applyChngButton1))
                .addGap(66, 66, 66))
        );
        VASDeactPanelLayout.setVerticalGroup(
            VASDeactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, VASDeactPanelLayout.createSequentialGroup()
                .addGroup(VASDeactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, VASDeactPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, VASDeactPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(applyChngButton1)
                        .addGap(18, 18, 18)
                        .addComponent(removeServiceButton)))
                .addContainerGap())
        );

        CRBTSubPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fun Ring Subscription", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 1, 12))); // NOI18N

        songCodeLabel.setText("Song Code:");

        funRingSubButton.setText("Subscribe");
        funRingSubButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funRingSubButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CRBTSubPanelLayout = new javax.swing.GroupLayout(CRBTSubPanel);
        CRBTSubPanel.setLayout(CRBTSubPanelLayout);
        CRBTSubPanelLayout.setHorizontalGroup(
            CRBTSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CRBTSubPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(songCodeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(songCodeTextField)
                .addGap(18, 18, 18)
                .addComponent(funRingSubButton)
                .addContainerGap())
        );
        CRBTSubPanelLayout.setVerticalGroup(
            CRBTSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CRBTSubPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CRBTSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(songCodeLabel)
                    .addComponent(funRingSubButton)
                    .addComponent(songCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        GPRSSubPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "3G", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 1, 12))); // NOI18N

        gprsSubButton.setText("Fix Data Service");
        gprsSubButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gprsSubButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout GPRSSubPanelLayout = new javax.swing.GroupLayout(GPRSSubPanel);
        GPRSSubPanel.setLayout(GPRSSubPanelLayout);
        GPRSSubPanelLayout.setHorizontalGroup(
            GPRSSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GPRSSubPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(gprsSubButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        GPRSSubPanelLayout.setVerticalGroup(
            GPRSSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GPRSSubPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gprsSubButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout servicePanelLayout = new javax.swing.GroupLayout(servicePanel);
        servicePanel.setLayout(servicePanelLayout);
        servicePanelLayout.setHorizontalGroup(
            servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(servicePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(servicePanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(subNumField2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clrMSC1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, servicePanelLayout.createSequentialGroup()
                        .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(VASPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CRBTSubPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(VASDeactPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(servicePanelLayout.createSequentialGroup()
                                .addComponent(TOKPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GPRSSubPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(155, 155, 155))))
        );
        servicePanelLayout.setVerticalGroup(
            servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subNumField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(clrMSC1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, servicePanelLayout.createSequentialGroup()
                        .addComponent(VASPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CRBTSubPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, servicePanelLayout.createSequentialGroup()
                        .addComponent(VASDeactPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GPRSSubPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TOKPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addContainerGap())
        );

        AppPane.addTab("Services", servicePanel);

        commandList.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        commandList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Display", "Display AC", "Create AC", "Cancel AC", "Create Sub", "Cancel Sub", "Product Modify", "Call Forward" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        commandList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        commandList.setMaximumSize(new java.awt.Dimension(25, 0));
        commandList.setVisibleRowCount(5);
        commandList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                commandListValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(commandList);

        MSISDNtextField.setColumns(7);
        MSISDNtextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MSISDNtextFieldMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                MSISDNtextFieldMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                MSISDNtextFieldMouseReleased(evt);
            }
        });
        MSISDNtextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MSISDNtextFieldActionPerformed(evt);
            }
        });

        jLabel4.setText("MSISDN");

        jLabel6.setText("MSIN");

        IMSItextField.setColumns(10);
        IMSItextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IMSItextFieldMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                IMSItextFieldMouseReleased(evt);
            }
        });
        IMSItextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IMSItextFieldActionPerformed(evt);
            }
        });

        dataDispComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALLDATA", "SSDATA" }));
        dataDispComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataDispComboBoxActionPerformed(evt);
            }
        });

        mscParTextField.setColumns(10);
        mscParTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mscParTextFieldActionPerformed(evt);
            }
        });

        mscParLabel.setText("A4KI");

        prodList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = new String[5];
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        prodList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        prodList.setMaximumSize(new java.awt.Dimension(25, 0));
        prodList.setVisibleRowCount(5);
        prodList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                prodListValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(prodList);

        prodComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Open","Block"}));
        prodComboBox.setDoubleBuffered(true);

        jScrollPane6.setAutoscrolls(true);
        jScrollPane6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane6.setDoubleBuffered(true);

        MSCTextArea.setColumns(20);
        MSCTextArea.setEditable(false);
        MSCTextArea.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
        MSCTextArea.setRows(10);
        MSCTextArea.setWrapStyleWord(true);
        MSCTextArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        MSCTextArea.setDoubleBuffered(true);
        MSCTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                MSCTextAreaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                MSCTextAreaMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(MSCTextArea);

        submitMSCcmd.setText("Submit");
        submitMSCcmd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitMSCcmdActionPerformed(evt);
            }
        });

        clrMSC.setText("Clear");
        clrMSC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clrMSCActionPerformed(evt);
            }
        });

        CallFwdDestTextField.setColumns(7);
        CallFwdDestTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CallFwdDestTextFieldMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CallFwdDestTextFieldMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CallFwdDestTextFieldMouseReleased(evt);
            }
        });
        CallFwdDestTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CallFwdDestTextFieldActionPerformed(evt);
            }
        });

        ToLabel.setText("TO");

        javax.swing.GroupLayout mscPanelLayout = new javax.swing.GroupLayout(mscPanel);
        mscPanel.setLayout(mscPanelLayout);
        mscPanelLayout.setHorizontalGroup(
            mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mscPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mscPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 944, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(mscPanelLayout.createSequentialGroup()
                        .addGroup(mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mscPanelLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(ToLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CallFwdDestTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)
                                .addComponent(prodComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 423, Short.MAX_VALUE)
                                .addComponent(submitMSCcmd)
                                .addGap(36, 36, 36))
                            .addGroup(mscPanelLayout.createSequentialGroup()
                                .addGroup(mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)
                                    .addComponent(mscParLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mscPanelLayout.createSequentialGroup()
                                        .addComponent(dataDispComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(mscParTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(IMSItextField, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(mscPanelLayout.createSequentialGroup()
                                        .addComponent(MSISDNtextField, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(36, 36, 36)
                                        .addComponent(clrMSC)))
                                .addContainerGap(428, Short.MAX_VALUE))))))
        );
        mscPanelLayout.setVerticalGroup(
            mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mscPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mscPanelLayout.createSequentialGroup()
                        .addGroup(mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mscPanelLayout.createSequentialGroup()
                                .addGroup(mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(MSISDNtextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(IMSItextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(clrMSC))
                        .addGap(18, 18, 18)
                        .addGroup(mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mscParLabel)
                            .addComponent(dataDispComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mscParTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mscPanelLayout.createSequentialGroup()
                        .addGroup(mscPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CallFwdDestTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ToLabel)
                            .addComponent(submitMSCcmd)
                            .addComponent(prodComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)))
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addContainerGap())
        );

        AppPane.addTab("MSC Commander", mscPanel);

        simRepPanel.setPreferredSize(new java.awt.Dimension(375, 233));

        jLabel1.setText("Subscriber Number:");

        subNumField1.setColumns(7);
        subNumField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                subNumField1MouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                subNumField1MousePressed(evt);
            }
        });
        subNumField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subNumField1ActionPerformed(evt);
            }
        });

        replaceSimButton.setText("Replace");
        replaceSimButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceSimButtonActionPerformed(evt);
            }
        });

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        simRepTextArea.setColumns(20);
        simRepTextArea.setEditable(false);
        simRepTextArea.setLineWrap(true);
        simRepTextArea.setRows(5);
        simRepTextArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(simRepTextArea);

        refChkButton.setText("Check Reference Numbers");
        refChkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refChkButtonActionPerformed(evt);
            }
        });

        refNumField1.setColumns(7);
        refNumField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                refNumField1MouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                refNumField1MousePressed(evt);
            }
        });
        refNumField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refNumField1ActionPerformed(evt);
            }
        });

        refNumField2.setColumns(7);
        refNumField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                refNumField2MouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                refNumField2MousePressed(evt);
            }
        });
        refNumField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refNumField2ActionPerformed(evt);
            }
        });

        refNumField3.setColumns(7);
        refNumField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                refNumField3MouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                refNumField3MousePressed(evt);
            }
        });
        refNumField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refNumField3ActionPerformed(evt);
            }
        });

        jLabel17.setText("Reference 1:");

        jLabel18.setText("Reference 2:");

        jLabel19.setText("Reference 3:");

        clearSimButton.setText("Clear");
        clearSimButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSimButtonActionPerformed(evt);
            }
        });

        oldIMSItextField.setEditable(false);
        oldIMSItextField.setColumns(10);
        oldIMSItextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                oldIMSItextFieldMouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                oldIMSItextFieldMousePressed(evt);
            }
        });
        oldIMSItextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oldIMSItextFieldActionPerformed(evt);
            }
        });

        oldIMSILabel.setText("Current MSIN");

        newSIMTextField.setColumns(10);
        newSIMTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                newSIMTextFieldMouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                newSIMTextFieldMousePressed(evt);
            }
        });
        newSIMTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newSIMTextFieldActionPerformed(evt);
            }
        });

        newSIMLabel.setText("New SIM");

        javax.swing.GroupLayout simRepPanelLayout = new javax.swing.GroupLayout(simRepPanel);
        simRepPanel.setLayout(simRepPanelLayout);
        simRepPanelLayout.setHorizontalGroup(
            simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simRepPanelLayout.createSequentialGroup()
                .addGroup(simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 954, Short.MAX_VALUE)
                    .addGroup(simRepPanelLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(oldIMSILabel)
                            .addComponent(newSIMLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(oldIMSItextField, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(simRepPanelLayout.createSequentialGroup()
                                .addComponent(newSIMTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77)
                                .addComponent(replaceSimButton))))
                    .addGroup(simRepPanelLayout.createSequentialGroup()
                        .addGroup(simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(simRepPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1))
                            .addGroup(simRepPanelLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refNumField1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(simRepPanelLayout.createSequentialGroup()
                                .addComponent(subNumField1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(clearSimButton))
                            .addComponent(refNumField2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refNumField3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refChkButton))))
                .addContainerGap())
        );
        simRepPanelLayout.setVerticalGroup(
            simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simRepPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(subNumField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearSimButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(refNumField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(refNumField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refNumField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refChkButton)
                .addGap(55, 55, 55)
                .addGroup(simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oldIMSItextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oldIMSILabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(simRepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newSIMTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newSIMLabel)
                    .addComponent(replaceSimButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        AppPane.addTab("SIM Replace", simRepPanel);

        SMSOrigNumField.setColumns(7);
        SMSOrigNumField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SMSOrigNumFieldMouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SMSOrigNumFieldMousePressed(evt);
            }
        });
        SMSOrigNumField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SMSOrigNumFieldActionPerformed(evt);
            }
        });

        jLabel14.setText("Originating Number:");

        jLabel16.setText("Destination Number:");

        jLabel22.setText("Start Date Time:");

        jLabel23.setText("End Date Time:");

        SMSDestNumField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SMSDestNumFieldMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SMSDestNumFieldMouseReleased(evt);
            }
        });
        SMSDestNumField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SMSDestNumFieldActionPerformed(evt);
            }
        });

        clearSMSDelivButton.setText("Clear");
        clearSMSDelivButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSMSDelivButtonActionPerformed(evt);
            }
        });

        jTable2.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setSelectionForeground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jScrollPane9.setViewportView(jTable2);

        SMSCheckDelivButton.setText("Check Delivery");
        SMSCheckDelivButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SMSCheckDelivButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SMSDeliveryPanelLayout = new javax.swing.GroupLayout(SMSDeliveryPanel);
        SMSDeliveryPanel.setLayout(SMSDeliveryPanelLayout);
        SMSDeliveryPanelLayout.setHorizontalGroup(
            SMSDeliveryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SMSDeliveryPanelLayout.createSequentialGroup()
                .addGroup(SMSDeliveryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 954, Short.MAX_VALUE)
                    .addGroup(SMSDeliveryPanelLayout.createSequentialGroup()
                        .addGroup(SMSDeliveryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SMSDeliveryPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(SMSDeliveryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel22))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SMSDeliveryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(SMSDeliveryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(SMSDeliveryPanelLayout.createSequentialGroup()
                                            .addComponent(SMSOrigNumField, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(clearSMSDelivButton))
                                        .addComponent(SMSDestNumField)
                                        .addComponent(SMSCheckDelivButton, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addGroup(SMSDeliveryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(SMSEndDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(SMSStartDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))))
                            .addGroup(SMSDeliveryPanelLayout.createSequentialGroup()
                                .addGap(342, 342, 342)
                                .addComponent(SMSDeliveryProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        SMSDeliveryPanelLayout.setVerticalGroup(
            SMSDeliveryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SMSDeliveryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SMSDeliveryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SMSOrigNumField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearSMSDelivButton)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SMSDeliveryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SMSDestNumField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SMSDeliveryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SMSStartDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SMSDeliveryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SMSEndDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(SMSCheckDelivButton)
                .addGap(37, 37, 37)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SMSDeliveryProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        AppPane.addTab("SMS Delivery", SMSDeliveryPanel);

        EVCSenderField.setColumns(7);
        EVCSenderField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                EVCSenderFieldMouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EVCSenderFieldMousePressed(evt);
            }
        });
        EVCSenderField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EVCSenderFieldActionPerformed(evt);
            }
        });

        jLabel21.setText("Dealer Number:");

        jLabel24.setText("Receipient Number:");

        jLabel25.setText("Transaction Date:");

        EVCRecipientField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                EVCRecipientFieldMouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EVCRecipientFieldMousePressed(evt);
            }
        });

        clearEVCButton.setText("Clear");
        clearEVCButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearEVCButtonActionPerformed(evt);
            }
        });

        jTable3.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable3.setSelectionForeground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jScrollPane10.setViewportView(jTable3);

        searchEVCButton.setText("Search Transaction");
        searchEVCButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchEVCButtonActionPerformed(evt);
            }
        });

        EVCAmount1Field.setColumns(7);
        EVCAmount1Field.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EVCAmount1FieldMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                EVCAmount1FieldMouseReleased(evt);
            }
        });
        EVCAmount1Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EVCAmount1FieldActionPerformed(evt);
            }
        });

        jLabel29.setText("Amount 1:");

        payBackEVCButton.setText("Reverse EVC");
        payBackEVCButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payBackEVCButtonActionPerformed(evt);
            }
        });

        normToEVCButton.setText("Normal to EVC");
        normToEVCButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normToEVCButtonActionPerformed(evt);
            }
        });

        retCrdtTransButton.setText("Reverse Credit Transfer");
        retCrdtTransButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retCrdtTransButtonActionPerformed(evt);
            }
        });

        buttonGroupEVC.add(dealerToSubRadioButton);
        dealerToSubRadioButton.setText("EVC Transfer");

        buttonGroupEVC.add(dealerToDealerRadioButton);
        dealerToDealerRadioButton.setText("Dealer Transfer");

        ordSubRadioButton.setText("Credit Transfer");

        jLabel32.setText("Amount 2:");

        EVCAmount2Field.setColumns(7);
        EVCAmount2Field.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EVCAmount2FieldMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                EVCAmount2FieldMouseReleased(evt);
            }
        });
        EVCAmount2Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EVCAmount2FieldActionPerformed(evt);
            }
        });

        jLabel33.setText("Amount 3:");

        EVCAmount3Field.setColumns(7);
        EVCAmount3Field.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EVCAmount3FieldMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                EVCAmount3FieldMouseReleased(evt);
            }
        });
        EVCAmount3Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EVCAmount3FieldActionPerformed(evt);
            }
        });

        minAmtLabel.setText("Miniumum Amount:");

        minAmountField.setColumns(7);
        minAmountField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                minAmountFieldMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                minAmountFieldMouseReleased(evt);
            }
        });
        minAmountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minAmountFieldActionPerformed(evt);
            }
        });

        payBackDlrButton.setText("Reverse Dealer Transfer");
        payBackDlrButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payBackDlrButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EVCPanelLayout = new javax.swing.GroupLayout(EVCPanel);
        EVCPanel.setLayout(EVCPanelLayout);
        EVCPanelLayout.setHorizontalGroup(
            EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EVCPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10)
                .addContainerGap())
            .addGroup(EVCPanelLayout.createSequentialGroup()
                .addGroup(EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EVCPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24)
                            .addComponent(jLabel21)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EVCPanelLayout.createSequentialGroup()
                                .addGroup(EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(EVCPanelLayout.createSequentialGroup()
                                        .addComponent(EVCSenderField, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(clearEVCButton))
                                    .addComponent(EVCRecipientField)
                                    .addGroup(EVCPanelLayout.createSequentialGroup()
                                        .addComponent(EVCAmount1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(EVCAmount2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(EVCPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel33)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(EVCAmount3Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(EVCPanelLayout.createSequentialGroup()
                                        .addComponent(minAmtLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(minAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(EVCTransactionDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(EVCPanelLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EVCPanelLayout.createSequentialGroup()
                                .addComponent(dealerToSubRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dealerToDealerRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ordSubRadioButton)
                                .addGap(18, 18, 18)
                                .addComponent(searchEVCButton))
                            .addGroup(EVCPanelLayout.createSequentialGroup()
                                .addComponent(payBackEVCButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                                .addComponent(normToEVCButton)
                                .addGap(141, 141, 141)
                                .addComponent(payBackDlrButton)
                                .addGap(75, 75, 75)
                                .addComponent(retCrdtTransButton)))))
                .addGap(37, 37, 37))
        );
        EVCPanelLayout.setVerticalGroup(
            EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EVCPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EVCSenderField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearEVCButton)
                    .addComponent(jLabel21)
                    .addComponent(minAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minAmtLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EVCRecipientField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EVCAmount1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(EVCAmount2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(EVCAmount3Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(EVCTransactionDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dealerToSubRadioButton)
                    .addComponent(dealerToDealerRadioButton)
                    .addComponent(ordSubRadioButton)
                    .addComponent(searchEVCButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(EVCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(payBackEVCButton)
                    .addComponent(normToEVCButton)
                    .addComponent(retCrdtTransButton)
                    .addComponent(payBackDlrButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        AppPane.addTab("EVC", EVCPanel);

        jLabel26.setText("Receipient Number:");

        crAdReceipientNumField.setColumns(7);
        crAdReceipientNumField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                crAdReceipientNumFieldMouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                crAdReceipientNumFieldMousePressed(evt);
            }
        });
        crAdReceipientNumField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crAdReceipientNumFieldActionPerformed(evt);
            }
        });

        jLabel28.setText("Requested By:");

        jLabel30.setText("Amount:");

        crAdAmntField.setColumns(7);
        crAdAmntField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                crAdAmntFieldMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                crAdAmntFieldMouseReleased(evt);
            }
        });
        crAdAmntField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crAdAmntFieldActionPerformed(evt);
            }
        });

        clrCrdAddButton.setText("Clear");
        clrCrdAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clrCrdAddButtonActionPerformed(evt);
            }
        });

        addCredButton.setText("Adjust");
        addCredButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCredButtonActionPerformed(evt);
            }
        });

        buttonGroupCredAdd.add(realBalRadioButton);
        realBalRadioButton.setText("Real Balance");

        buttonGroupCredAdd.add(freeMonRadioButton);
        freeMonRadioButton.setText("Free Money");

        jLabel31.setText("Comments:");

        javax.swing.GroupLayout credAddPanelLayout = new javax.swing.GroupLayout(credAddPanel);
        credAddPanel.setLayout(credAddPanelLayout);
        credAddPanelLayout.setHorizontalGroup(
            credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(credAddPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel28)
                    .addComponent(jLabel26)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(credAddPanelLayout.createSequentialGroup()
                        .addGroup(credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(crAdAmntField, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(crAdReceipientNumField, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(requesterComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(credAddPanelLayout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(clrCrdAddButton))
                            .addGroup(credAddPanelLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(freeMonRadioButton)
                                    .addGroup(credAddPanelLayout.createSequentialGroup()
                                        .addComponent(realBalRadioButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(addCredButton))))))
                    .addComponent(credAddCmntTextField))
                .addContainerGap(510, Short.MAX_VALUE))
        );
        credAddPanelLayout.setVerticalGroup(
            credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(credAddPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(credAddPanelLayout.createSequentialGroup()
                        .addGroup(credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(crAdReceipientNumField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addGap(18, 18, 18)
                        .addGroup(credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(requesterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(crAdAmntField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(addCredButton)
                                .addComponent(realBalRadioButton))
                            .addComponent(jLabel30)))
                    .addComponent(clrCrdAddButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(freeMonRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(credAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(credAddCmntTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(338, Short.MAX_VALUE))
        );

        AppPane.addTab("Credit Addition", credAddPanel);

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(AppPane, javax.swing.GroupLayout.DEFAULT_SIZE, 969, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(exitButton)
                        .addGap(42, 42, 42))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(AppPane, javax.swing.GroupLayout.PREFERRED_SIZE, 542, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hideMSCFields() {

        mscParLabel.setVisible(false);
        mscParTextField.setVisible(false);
        dataDispComboBox.setVisible(false);
        prodList.setVisible(false);
        prodComboBox.setVisible(false);
        ToLabel.setVisible(false);
        CallFwdDestTextField.setVisible(false);
        mscParTextField.setText("");
    }

    /*
     private void enableSIMReplaceFields(boolean status) {
    
     replaceSimButton.setEnabled(status);
     setTokButton.setEnabled(status);
     TokNumField.setEditable(status);
     }
     */
    private void toggleServComboBox() {
        roamComboBox.setEnabled(roamRadioButton.isSelected());
        ussdComboBox.setEnabled(ussdRadioButton.isSelected());
        validComboBox.setEnabled(validRadioButton.isSelected());
        langComboBox.setEnabled(langRadioButton.isSelected());
        postpaidComboBox.setEnabled(postpaidRadioButton.isSelected());
    }
    private void roamRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roamRadioButtonActionPerformed
        // TODO add your handling code here:
        toggleServComboBox();
}//GEN-LAST:event_roamRadioButtonActionPerformed
    /*
     * SIM Replacement Button Action Command
     * Gets the In
     */
    private void replaceSimButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceSimButtonActionPerformed
        // TODO add your handling code here:

        String newSIM = newSIMTextField.getText();
        if (newSIM.length() >= 8) {
            String nIMSI = INCmd.getMSIN(newSIM);
            refChkButton.setEnabled(false);
            clearSimButton.setEnabled(false);

            if (verifyIMSI(nIMSI)) {
                simRepTextArea.setText("Processing...\n");
                MSCSIMReplace SIMReplaceWorker = new MSCSIMReplace(subNumSimRep, nIMSI);
                SIMReplaceWorker.execute();
            } else {
                simRepTextArea.setText("MSIN Number does not exist for SIM");
            }
        } else {
            simRepTextArea.setText("Enter at least the last 8 digits of the SIM number.");
        }
}//GEN-LAST:event_replaceSimButtonActionPerformed

    private void subNumField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subNumField1ActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_subNumField1ActionPerformed

    private void applyChngButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyChngButtonActionPerformed
        // TODO add your handling code here:
        String subNum = subNumField2.getText();

        if (!verifyMSISDN(subNum)) {
            serviceTextArea.setText(wrgNumMsg);
        } else {
            int cosNum = (Integer)INCmd.getSubInfo(subNum, "cos_num");
            appLog.setMSISDN(subNum);
            int validPrd = 0;

            if (roamRadioButton.isSelected()) { //Roaming Facility Button Selected

                if (roamComboBox.getSelectedIndex() == 0) { //Enable
                    //serviceTextArea.setText("Activating roaming facility of "+subNum+" on the HLR...");
                    mscCmd.setServedMSISDN(subNum);
                    mscCmd.setBARRAOM("NOBAR");
                    mscCmd.executeCommand(HuaweiHLR.modifyBARROAMISDN);

                    StringTokenizer strToken = new StringTokenizer(mscCmd.getCmdOutput(), "\n");
                    String s;
                    while (strToken.hasMoreTokens()) {
                        s = strToken.nextToken();

                        if (s.contains("ERR".subSequence(0, 2))) {
                            serviceTextArea.append("\nHLR command failed. Cause:\n"+mscCmd.getCmdOutput());
                            return;
                        }
                    }
                    serviceTextArea.append(new StringBuilder().append("done!\nRoaming class of service has been ").append("activated").toString());
                    cosNum = INCommander.COS_ROAM_EN;
                    appLog.setLogActParam("Open");
                    appLog.setLogEventID((short) 18);
                    appLog.setLogActParam("Enable");
                    appLog.setLogEventID((short) 5);

                } else if (roamComboBox.getSelectedIndex() == 1) { //disable
                    //serviceTextArea.setText("Deactivating roaming facility of "+subNum+" on the HLR...");
                    mscCmd.setServedMSISDN(subNum);
                    mscCmd.setBARRAOM("BROHPLMN");
                    mscCmd.executeCommand(HuaweiHLR.modifyBARROAMISDN);

                    StringTokenizer strToken = new StringTokenizer(mscCmd.getCmdOutput(), "\n");
                    String s;
                    while (strToken.hasMoreTokens()) {
                        s = strToken.nextToken();

                        if (s.contains("ERR".subSequence(0, 2))) {
                            serviceTextArea.append("\nHLR command failed. Cause:\n"+mscCmd.getCmdOutput());
                            return;
                        }
                    }
                    serviceTextArea.append(new StringBuilder().append("done!\nDefault class of service has been ").append("activated").toString());
                    cosNum = INCommander.COS_AFRICELL_DEF;
                    appLog.setLogActParam("Block");
                    appLog.setLogEventID((short) 18);
                    appLog.setLogActParam("Disable");
                    appLog.setLogEventID((short) 5);
                } else {
                    JOptionPane.showMessageDialog(VASPanel, "Select Enable/Disable", "Input Error", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (langRadioButton.isSelected()) {

                switch (langComboBox.getSelectedIndex()) {
                    case 0: //English
                        cosNum = INCommander.COS_AFRICELL_DEF;
                        appLog.setLogActParam("English");
                        break;
                    case 1: //French
                        cosNum = INCommander.COS_FRENCH;
                        appLog.setLogActParam("French");
                        break;
                    case 2: //Wolof
                        cosNum = INCommander.COS_WOLOF;
                        appLog.setLogActParam("French");
                        break;
                    case 3: //Mandinka
                        cosNum = INCommander.COS_MANDINKA;
                        appLog.setLogActParam("Mandinka");
                        break;
                    default:
                        return;
                }
                appLog.setLogEventID(AppLogger.Language);

            } else if (ussdRadioButton.isSelected()) {

                if (ussdComboBox.getSelectedIndex() == 0) {
                    cosNum = INCommander.COS_USSD_DIS;
                    appLog.setLogActParam("Enable");
                } else {
                    cosNum = INCommander.COS_AFRICELL_DEF;
                    appLog.setLogActParam("Disable");
                }
                appLog.setLogEventID(AppLogger.USSD);

            } else if (validRadioButton.isSelected()) {
                if (validComboBox.getSelectedIndex() == 0) {
                    cosNum = INCommander.COS_Valid;
                    appLog.setLogActParam("6 months");
                    validPrd = 6;
                } else {
                    cosNum = INCommander.COS_Valid;
                    appLog.setLogActParam("1 year");
                    validPrd = 12;
                }
                appLog.setLogEventID(AppLogger.Validity);

            } else if (postpaidRadioButton.isSelected()) {
                if (postpaidComboBox.getSelectedIndex() == 0) {
                    cosNum = INCommander.COS_Post;
                    appLog.setLogActParam("Enable");
                } else {
                    cosNum = INCommander.COS_AFRICELL_DEF;
                    appLog.setLogActParam("Disable");
                }
                appLog.setLogEventID(AppLogger.PostPaid);
            } else {
                serviceTextArea.setText("Select action to apply");
                return;
            }

            String msg;

            switch (cosNum) {
                case INCommander.COS_FRENCH:
                case INCommander.COS_FRENCH_TOK:
                    msg = "Customer Language Settings Changed to French";
                    break;

                case INCommander.COS_WOLOF:
                case INCommander.COS_WOLOF_TOK:
                    msg = "Customer Language Settings Changed to Wolof";
                    break;

                case INCommander.COS_MANDINKA:
                case INCommander.COS_MANDINKA_TOK:
                    msg = "Customer Language Settings Changed to Mandinka";
                    break;

                case INCommander.COS_ENGLISH_TOK:
                    msg = "Customer Language Settings Changed to English";
                    break;

                case INCommander.COS_USSD_DIS:
                    msg = "Customer USSD Notification Enabled";
                    break;

                case INCommander.COS_ROAM_EN:
                    msg = "Roaming Class of Service Enabled for Customer";
                    break;

                case INCommander.COS_Valid:
                    java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat("dd/MM/yyyy");
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.add(java.util.Calendar.MONTH, validPrd);
                    msg = "Account Validity Updated, expires at " + dateformat.format(cal.getTime());
                    break;

                case INCommander.COS_Post:
                    msg = "Subscriber COS changed to Post-paid.";
                    break;

                default:
                    msg = "Customer Settings Remains Unchanged";
                    break;
            }
            try {
                INCmd.changeService(subNum, cosNum, validPrd);
            } catch (SQLException sqle) {
                //LogClass.errLog(sqle.getMessage());
            }
            serviceTextArea.setText(msg);
        }
    }//GEN-LAST:event_applyChngButtonActionPerformed

    private void roamComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roamComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roamComboBoxActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        try {
            INCmd.logOut();
            INCmd.getDBConnector().disconnect();
        } catch (SQLException sqlE) {
            javax.swing.JOptionPane.showMessageDialog(this, "Log-out Time could not be registered");
            System.out.println(sqlE.getMessage());
        }
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void tokSubChkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tokSubChkButtonActionPerformed
        // TODO add your handling code here:
        TokNumField.setText("");
        String subNum = subNumField2.getText();

        if (!verifyMSISDN(subNum)) {
            serviceTextArea.setText(wrgNumMsg);
        } else {
            try {
                boolean TOKsubFlg = INCmd.tokSubCheck(subNum); //Check subscription in Friends'n Family Table
                String chkRes = subNum.toString(); //copy the subscriber number to another String object
                String tokNum = null;

                if (TOKsubFlg) {
                    subNumTOK = subNum;
                    tokNum = INCmd.getTOKNumber(subNum); //Get the subscriber's TOK number subscribed to
                    TokNumField.setText(tokNum != null ? tokNum : ""); //Set TOK number field to the result in the database; if null set to empty string
                    chkRes += ": Valid";
                    if (tokNum == null) {
                        setTokButton.setEnabled(true);
                        TokNumField.setEditable(true);
                    } else if (TokNumAdjButton.isVisible()) {//User Role has privilege to change TOK Number
                        TokNumAdjButton.setEnabled(true);
                        TokNumField.setEditable(true);
                    }
                } else {
                    subNumTOK = null;
                    chkRes += ": Invalid";
                }

                chkRes += " TOK Subscriber";

                appLog.setMSISDN(subNum);
                appLog.setLogEventID(AppLogger.TOKSub);

                serviceTextArea.setText(chkRes);

            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
            }
        }
    }//GEN-LAST:event_tokSubChkButtonActionPerformed

    private void TokNumFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TokNumFieldActionPerformed
        // TODO add your handling code here:
        String tokNum = TokNumField.getText();

        if (!verifyMSISDN(tokNum)) {
            serviceTextArea.setText(tokNum + ": " + wrgNumMsg);
            TokNumField.setText("");
        } else if (subNumTOK.equals(tokNum)) {
            serviceTextArea.setText("TOK Number cannot be "
                    + "the same as subscriber number");
            TokNumField.setText("");
        } else {
            try {
                final int DUPERR = 2;
                final int MAXNUMEXERR = 3;
                final int NUMBARERR = 4;
                final int INSERTERR = 0;
                final int SUCCESS = 1;
                String s;
                switch (INCmd.setTOKNum(subNumTOK, tokNum)) {
                    case DUPERR:
                        s = "Subscriber already in F&F table";
                        break;
                    case MAXNUMEXERR:
                        s = "Subscriber having another class of Service";
                        break;
                    case NUMBARERR:
                        s = "Subscriber Number in Barred Range";
                        break;
                    case INSERTERR:
                        s = "Error duing insert into Friends and Family Table";
                        break;
                    case SUCCESS:
                        s = "Successful!\nTOK Number set to " + tokNum + " for subscriber " + subNumTOK;
                        appLog.setMSISDN(subNumTOK);
                        appLog.setLogActParam(tokNum);
                        appLog.setLogEventID(AppLogger.TOKNumSet);
                        break;
                    default:
                        s = "Some Error Occured. Contact Administrator";
                        break;
                }
                setTokButton.setEnabled(false);
                serviceTextArea.setText(s);
                subNumTOK = null;
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }//GEN-LAST:event_TokNumFieldActionPerformed

    private void setTokButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setTokButtonActionPerformed

        String tokNum = TokNumField.getText();

        if (!verifyMSISDN(tokNum)) {
            serviceTextArea.setText(tokNum + ": " + wrgNumMsg);
            TokNumField.setText("");
        } else if (subNumTOK.equals(tokNum)) {
            serviceTextArea.setText("TOK Number cannot be "
                    + "the same as subscriber number");
            TokNumField.setText("");
        } else {
            try {
                final int DUPERR = 2;
                final int MAXNUMEXERR = 3;
                final int NUMBARERR = 4;
                final int INSERTERR = 0;
                final int SUCCESS = 1;
                String s;
                switch (INCmd.setTOKNum(subNumTOK, tokNum)) {
                    case DUPERR:
                        s = "Subscriber already in F&F table";
                        break;
                    case MAXNUMEXERR:
                        s = "Subscriber having another class of Service";
                        break;
                    case NUMBARERR:
                        s = "Subscriber Number in Barred Range";
                        break;
                    case INSERTERR:
                        s = "Error duing insert into Friends and Family Table";
                        break;
                    case SUCCESS:
                        s = "Successful!\nTOK Number set to " + tokNum + " for subscriber " + subNumTOK;
                        appLog.setMSISDN(subNumTOK);
                        appLog.setLogActParam(tokNum);
                        appLog.setLogEventID(AppLogger.TOKNumSet);
                        break;
                    default:
                        s = "Some Error Occured. Contact Administrator";
                        break;
                }
                setTokButton.setEnabled(false);
                TokNumField.setEditable(false);
                serviceTextArea.setText(s);
                if (TokNumAdjButton.isVisible()) {
                    TokNumAdjButton.setEnabled(false);
                }
                subNumTOK = null;
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }//GEN-LAST:event_setTokButtonActionPerformed

    private void dispSubNumFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dispSubNumFieldActionPerformed

        String subNum = dispSubNumField.getText();
        if (verifyPrepaidMSISDN(subNum)) {

            String[] subInfoStr;
            try {
                subInfoStr = INCmd.getSubInfo(subNum);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                return;
            }
            if (subInfoStr.length > 1) {
                balTextField.setText(subInfoStr[0]);
                freeMonTextField.setText(subInfoStr[1]);
                freeSMSTextField.setText(subInfoStr[2]);
                cosTextField.setText(subInfoStr[3]);
                firstCallTextField.setText(subInfoStr[4]);
                lastCallTextField.setText(subInfoStr[5]);
                LoyaltyStatusField.setText(subInfoStr[6]);
                KolarehCreditField.setText(subInfoStr[7]);
                appLog.setMSISDN(subNum);
                appLog.setLogEventID(AppLogger.DispAccount);
            } else if (subInfoStr.length == 1) {
                cosTextField.setText(subInfoStr[0]);
                balTextField.setText("");
                freeMonTextField.setText("");
                freeSMSTextField.setText("");
                firstCallTextField.setText("");
                lastCallTextField.setText("");
            } else {
                cosTextField.setText("DB Error");
                balTextField.setText("");
                freeMonTextField.setText("");
                freeSMSTextField.setText("");
                firstCallTextField.setText("");
                lastCallTextField.setText("");
            }
        } else {
            cosTextField.setText("Invalid Number");
            balTextField.setText("");
            freeMonTextField.setText("");
            freeSMSTextField.setText("");
            firstCallTextField.setText("");
            lastCallTextField.setText("");
        }
        jTable1.setModel(new DefaultTableModel());
    }//GEN-LAST:event_dispSubNumFieldActionPerformed

    private void balTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_balTextFieldActionPerformed

    private void refChkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refChkButtonActionPerformed
        // TODO add your handling code here:
        replaceSimButton.setEnabled(false);
        String subNum = subNumField1.getText();
        String refNum1 = refNumField1.getText();
        String refNum2 = refNumField2.getText();
        String refNum3 = refNumField3.getText();

        if (!verifyMSISDN(subNum) || (refNum1.length() < 7)
                || (refNum2.length() < 7) || (refNum3.length() < 7)
                || refNum1.equals(refNum2) || refNum1.equals(refNum3)
                || refNum2.equals(refNum3)) {
            simRepTextArea.setText("Incorrect MSISDN/References");
            refNumField1.setText("");
            refNumField1.setBackground(Color.WHITE);
            refNumField2.setText("");
            refNumField2.setBackground(Color.WHITE);
            refNumField3.setText("");
            refNumField3.setBackground(Color.WHITE);
        } else {
            try {
                Object[] refRes = INCmd.checkRefForSimReplace(subNum, refNum1, refNum2, refNum3);
                if (refRes != null) {
                    short[] rs = (short[]) refRes[0];
                    appLog.setMSISDN(subNum);
                    appLog.setRefCheckParams(refNum1, refNum2, refNum3, rs);
                    appLog.setLogEventID(AppLogger.RefCheck);

                    int correctCount = rs[0] + rs[1] + rs[2];

                    java.awt.Color refNumFieldColor1 = rs[0] == 1 ? Color.CYAN : Color.PINK;
                    java.awt.Color refNumFieldColor2 = rs[1] == 1 ? Color.CYAN : Color.PINK;
                    java.awt.Color refNumFieldColor3 = rs[2] == 1 ? Color.CYAN : Color.PINK;

                    refNumField1.setBackground(refNumFieldColor1);
                    refNumField2.setBackground(refNumFieldColor2);
                    refNumField3.setBackground(refNumFieldColor3);
                    simRepTextArea.setText("Reference Results: " + correctCount + " correct.");

                    if (userRole != (short) 5) {
                        if (correctCount > 1) {

                            subNumSimRep = subNum;
                            subNumField1.setEditable(false);
                            replaceSimButton.setEnabled(true);
                            newSIMTextField.setEditable(true);
                            /*
                             if (userRole != (short)3) {
                            
                             replaceSimButton.setEnabled(true);
                             newSIMTextField.setEditable(true);
                            
                             } else {
                             registerButton.setEnabled(true);
                             }
                             * 
                             */
                        } else {
                            this.simRepTextArea.setText("Not Enough Correct Numbers.");
                        }
                    } else {
                        subNumSimRep = subNum;
                        subNumField1.setEditable(false);
                        replaceSimButton.setEnabled(true);
                        newSIMTextField.setEditable(true);
                    }
                } else {
                    simRepTextArea.setText("Invalid Pre-paid Subscriber");
                    refNumField1.setText("");
                    refNumField1.setBackground(Color.WHITE);
                    refNumField2.setText("");
                    refNumField2.setBackground(Color.WHITE);
                    refNumField3.setText("");
                    refNumField3.setBackground(Color.WHITE);
                    //simRepBalTextField.setText("");
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }//GEN-LAST:event_refChkButtonActionPerformed

    private void refNumField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refNumField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refNumField1ActionPerformed

    private void refNumField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refNumField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refNumField2ActionPerformed

    private void refNumField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refNumField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refNumField3ActionPerformed

    private void clearSimButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSimButtonActionPerformed
        // TODO add your handling code here:
        subNumField1.setText("");
        refNumField1.setText("");
        refNumField1.setBackground(Color.WHITE);
        refNumField2.setText("");
        refNumField2.setBackground(Color.WHITE);
        refNumField3.setText("");
        refNumField3.setBackground(Color.WHITE);
        simRepTextArea.setText("");
        replaceSimButton.setEnabled(false);
        oldIMSItextField.setText("");
        newSIMTextField.setText("");
        oldIMSItextField.setEditable(false);
        newSIMTextField.setEditable(false);
        subNumField1.setEnabled(true);
        subNumField1.setEditable(true);
        refChkButton.setEnabled(true);
    }//GEN-LAST:event_clearSimButtonActionPerformed

    private void lastCallTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastCallTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastCallTextFieldActionPerformed

    private void clearDispButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearDispButtonActionPerformed
        // TODO add your handling code here:
        dispSubNumField.setText("");
        cosTextField.setText("");
        balTextField.setText("");
        freeMonTextField.setText("");
        freeSMSTextField.setText("");
        firstCallTextField.setText("");
        lastCallTextField.setText("");
        jTable1.setModel(new DefaultTableModel());
    }//GEN-LAST:event_clearDispButtonActionPerformed

    private void validRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validRadioButtonActionPerformed
        // TODO add your handling code here:
        toggleServComboBox();
    }//GEN-LAST:event_validRadioButtonActionPerformed

    private void subNumField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subNumField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_subNumField2ActionPerformed

    private void ussdRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ussdRadioButtonActionPerformed
        // TODO add your handling code here:
        toggleServComboBox();
    }//GEN-LAST:event_ussdRadioButtonActionPerformed

    private void langRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langRadioButtonActionPerformed
        // TODO add your handling code here:
        toggleServComboBox();
    }//GEN-LAST:event_langRadioButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        try {
            INCmd.logOut();
            INCmd.getDBConnector().disconnect();
        } catch (SQLException sqlE) {
            javax.swing.JOptionPane.showMessageDialog(this, "Log-out Time could not be registered");
            System.out.println(sqlE.getMessage());
        }
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void commandListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_commandListValueChanged
        // TODO add your handling code here:
        if (!evt.getValueIsAdjusting()) {

            hideMSCFields();
            switch (commandList.getSelectedIndex()) {
                case 0:  //Display
                case 4:  //Create Sub
                case 5:  //Cancel Sub
                    IMSItextField.setEditable(true);
                    MSISDNtextField.setEditable(true);
                    break;
                case 1:  //Display AC
                case 3:  //Create AC
                    MSISDNtextField.setEditable(false);
                    IMSItextField.setEditable(true);
                    break;
                case 2: //Create AC
                    mscParLabel.setText("KI");
                    mscParLabel.setVisible(true);
                    mscParTextField.setVisible(true);
                    mscParTextField.setEditable(false);
                    MSISDNtextField.setEditable(false);
                    IMSItextField.setEditable(true);
                    break;
                case 6: //Product Modify
                    prodList.setListData(prodMod);
                    prodList.setVisible(true);
                    prodList.clearSelection();
                    prodComboBox.setVisible(true);
                    IMSItextField.setEditable(true);
                    MSISDNtextField.setEditable(true);
                    break;
                case 7: //Call Fwd
                    prodList.setListData(HuaweiHLR.callFwdOpts);
                    IMSItextField.setEditable(true);
                    prodList.setVisible(true);
                    prodList.clearSelection();
                    MSISDNtextField.setEditable(true);
                    prodComboBox.setVisible(true);
                    ToLabel.setVisible(true);
                    CallFwdDestTextField.setVisible(true);
                    break;
                default:
                    break;
            }
        }
    }//GEN-LAST:event_commandListValueChanged

    private void MSISDNtextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MSISDNtextFieldActionPerformed
        // TODO add your handling code here:       
        String msisdn = MSISDNtextField.getText();
        String imsi = IMSItextField.getText();
        if (verifyMSISDN(msisdn)) {
            int choice = commandList.getSelectedIndex();
            switch (choice) {
                case 0: //Display
                case 5: //Cancel Sub
                case 6: //Product Modify
                case 7: //Call Fwd
                    MSCTextArea.setText("Processing...");
                    MSCCommandSender ms = new MSCCommandSender(choice, msisdn, null);
                    ms.execute();
                    break;
                case 4: //Create sub
                    if (verifyIMSI(imsi)) {
                        if (userRole != (short) 1) {
                            if (!verifyPrepaidMSISDN(msisdn)) {
                                MSCTextArea.setText("MSISDN in Post-paid Range, cannot be created");
                                return;
                            }
                        }
                        MSCTextArea.setText("Processing...");
                        ms = new MSCCommandSender(choice, msisdn, imsi);
                        ms.execute();
                    } else {
                        MSCTextArea.setText("Invalid MSIN Number");
                    }
                    break;
                default:
                    MSCTextArea.setText("Select action to do");
                    break;
            }
        } else {
            MSCTextArea.setText("Invalid MSISDN");
        }
    }//GEN-LAST:event_MSISDNtextFieldActionPerformed

    private void IMSItextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IMSItextFieldActionPerformed
        // TODO add your handling code here:       
        String msisdn = MSISDNtextField.getText();
        String imsi = IMSItextField.getText();
        int choice = commandList.getSelectedIndex();
        if (verifyIMSI(imsi)) {
            switch (choice) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                case 7:
                    MSCTextArea.setText("Processing...");
                    MSCCommandSender ms = new MSCCommandSender(commandList.getSelectedIndex(), null, imsi);
                    ms.execute();
                    break;
                case 4:
                    if (verifyMSISDN(msisdn)) {
                        if (userRole != (short) 1) {
                            if (!verifyPrepaidMSISDN(msisdn)) {
                                MSCTextArea.setText("MSISDN in Post-paid Range, cannot be created");
                                return;
                            }
                        }
                        MSCTextArea.setText("Processing...");
                        ms = new MSCCommandSender(commandList.getSelectedIndex(), msisdn, imsi);
                        ms.execute();
                        break;
                    } else {
                        MSCTextArea.setText("Invalid MSISDN");
                    }
                default:
                    MSCTextArea.setText("Select action to do");
                    break;
            }
        } else {
            MSCTextArea.setText("Invalid MSIN Number");
        }
    }//GEN-LAST:event_IMSItextFieldActionPerformed

    private void mscParTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mscParTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mscParTextFieldActionPerformed

    private void prodListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_prodListValueChanged
        // TODO add your handling code here:
        /*
         switch(prodList.getSelectedIndex()) {
         case 0:
         case 1:
         case 2:
         dataServiceComboBox.setVisible(false);
         break;
         }
         * 
         */
    }//GEN-LAST:event_prodListValueChanged

    private void submitMSCcmdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitMSCcmdActionPerformed
        // TODO add your handling code here:
        String msisdn = MSISDNtextField.getText();
        String imsi = IMSItextField.getText();
        int choice = commandList.getSelectedIndex();
        if (verifyIMSI(imsi)) {
            switch (choice) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                case 7:
                    MSCTextArea.setText("Processing...");
                    MSCCommandSender ms = new MSCCommandSender(commandList.getSelectedIndex(), null, imsi);
                    ms.execute();
                    break;
                case 4:
                    if (verifyMSISDN(msisdn)) {
                        if (userRole != (short) 1) {
                            if (!verifyPrepaidMSISDN(msisdn)) {
                                MSCTextArea.setText("MSISDN in Post-paid Range, cannot be created");
                                return;
                            }
                        }
                        MSCTextArea.setText("Processing...");
                        ms = new MSCCommandSender(commandList.getSelectedIndex(), msisdn, imsi);
                        ms.execute();
                        break;
                    } else {
                        MSCTextArea.setText("Invalid MSISDN");
                    }
                default:
                    MSCTextArea.setText("Select action to do");
                    break;
            }
        } else if (verifyMSISDN(msisdn)) {
            switch (choice) {
                case 0:
                case 5:
                case 6:
                case 7:
                    MSCTextArea.setText("Processing...");
                    MSCCommandSender ms = new MSCCommandSender(commandList.getSelectedIndex(), msisdn, null);
                    ms.execute();
                    break;
                default:
                    MSCTextArea.setText("Select action to do");
                    break;
            }
        } else {
            MSCTextArea.setText("Invalid MSISDN and/or MSIN Number");
        }

    }//GEN-LAST:event_submitMSCcmdActionPerformed

    private void oldIMSItextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oldIMSItextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_oldIMSItextFieldActionPerformed

    private void newSIMTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newSIMTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newSIMTextFieldActionPerformed

    private void MSISDNtextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MSISDNtextFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_MSISDNtextFieldMouseClicked

    private void jPopupMenu1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPopupMenu1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPopupMenu1MousePressed

private void MSISDNtextFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MSISDNtextFieldMousePressed
    // TODO add your handling code here:
    mousePressComp = evt.getComponent();

    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_MSISDNtextFieldMousePressed

private void jPopupMenu1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPopupMenu1MouseReleased
    // TODO add your handling code here:
}//GEN-LAST:event_jPopupMenu1MouseReleased

private void MSISDNtextFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MSISDNtextFieldMouseReleased
    // TODO add your handling code here:
    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}//GEN-LAST:event_MSISDNtextFieldMouseReleased

private void IMSItextFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IMSItextFieldMousePressed
    // TODO add your handling code here:
    mousePressComp = evt.getComponent();

    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_IMSItextFieldMousePressed

private void IMSItextFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IMSItextFieldMouseReleased
    // TODO add your handling code here:
    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}//GEN-LAST:event_IMSItextFieldMouseReleased

private void copyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMenuItemActionPerformed
    // TODO add your handling code here:

    if (mousePressComp != null) {
        ((JTextComponent) mousePressComp).copy();
    }
}//GEN-LAST:event_copyMenuItemActionPerformed

private void pasteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteMenuItemActionPerformed
    // TODO add your handling code here:
    if (mousePressComp != null) {
        ((JTextComponent) mousePressComp).paste();
    }
}//GEN-LAST:event_pasteMenuItemActionPerformed

private void MSCTextAreaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MSCTextAreaMousePressed
    // TODO add your handling code here:
    mousePressComp = evt.getComponent();

    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_MSCTextAreaMousePressed

private void MSCTextAreaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MSCTextAreaMouseReleased
    // TODO add your handling code here:
    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}//GEN-LAST:event_MSCTextAreaMouseReleased

private void clrMSCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clrMSCActionPerformed
    // TODO add your handling code here:
    MSISDNtextField.setText("");
    IMSItextField.setText("");
    MSCTextArea.setText("");
    if (commandList.getSelectedIndex() == 2) {
        mscParTextField.setEditable(false);
    }
    if (commandList.getSelectedIndex() == 7) {
        CallFwdDestTextField.setText("");
    }
}//GEN-LAST:event_clrMSCActionPerformed

private void subNumField2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subNumField2MousePressed
    // TODO add your handling code here:
    mousePressComp = evt.getComponent();

    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_subNumField2MousePressed

private void subNumField2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subNumField2MouseReleased
    // TODO add your handling code here:
    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}//GEN-LAST:event_subNumField2MouseReleased

private void TokNumFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TokNumFieldMousePressed
    // TODO add your handling code here:
    mousePressComp = evt.getComponent();

    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_TokNumFieldMousePressed

private void TokNumFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TokNumFieldMouseReleased
    // TODO add your handling code here:
    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}//GEN-LAST:event_TokNumFieldMouseReleased

private void subNumField1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subNumField1MousePressed
    // TODO add your handling code here:
    mousePressComp = evt.getComponent();

    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_subNumField1MousePressed

private void subNumField1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subNumField1MouseReleased
    // TODO add your handling code here:
    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}//GEN-LAST:event_subNumField1MouseReleased

private void refNumField1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refNumField1MousePressed
    // TODO add your handling code here:
    mousePressComp = evt.getComponent();

    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_refNumField1MousePressed

private void refNumField1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refNumField1MouseReleased
    // TODO add your handling code here:
    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}//GEN-LAST:event_refNumField1MouseReleased

private void refNumField2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refNumField2MouseReleased
    // TODO add your handling code here:
    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}//GEN-LAST:event_refNumField2MouseReleased

private void refNumField2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refNumField2MousePressed
    // TODO add your handling code here:
    mousePressComp = evt.getComponent();

    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_refNumField2MousePressed

private void refNumField3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refNumField3MousePressed
    // TODO add your handling code here:
    mousePressComp = evt.getComponent();

    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_refNumField3MousePressed

private void refNumField3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refNumField3MouseReleased
    // TODO add your handling code here:
    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}//GEN-LAST:event_refNumField3MouseReleased

private void oldIMSItextFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_oldIMSItextFieldMousePressed
    // TODO add your handling code here:
    mousePressComp = evt.getComponent();

    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_oldIMSItextFieldMousePressed

private void oldIMSItextFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_oldIMSItextFieldMouseReleased
    // TODO add your handling code here:
    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}//GEN-LAST:event_oldIMSItextFieldMouseReleased

private void newSIMTextFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newSIMTextFieldMousePressed
    // TODO add your handling code here:
    mousePressComp = evt.getComponent();

    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_newSIMTextFieldMousePressed

private void newSIMTextFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newSIMTextFieldMouseReleased
    // TODO add your handling code here:
    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}//GEN-LAST:event_newSIMTextFieldMouseReleased

private void dispSubNumFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dispSubNumFieldMousePressed
    // TODO add your handling code here:
    mousePressComp = evt.getComponent();

    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_dispSubNumFieldMousePressed

private void dispSubNumFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dispSubNumFieldMouseReleased
    // TODO add your handling code here:
    if (evt.isPopupTrigger()) {
        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
}//GEN-LAST:event_dispSubNumFieldMouseReleased

private void CallFwdDestTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallFwdDestTextFieldMouseClicked
    // TODO add your handling code here:
}//GEN-LAST:event_CallFwdDestTextFieldMouseClicked

private void CallFwdDestTextFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallFwdDestTextFieldMousePressed
    // TODO add your handling code here:
}//GEN-LAST:event_CallFwdDestTextFieldMousePressed

private void CallFwdDestTextFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallFwdDestTextFieldMouseReleased
    // TODO add your handling code here:
}//GEN-LAST:event_CallFwdDestTextFieldMouseReleased

private void CallFwdDestTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CallFwdDestTextFieldActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_CallFwdDestTextFieldActionPerformed

private void postpaidRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postpaidRadioButtonActionPerformed
    // TODO add your handling code here:
    toggleServComboBox();
}//GEN-LAST:event_postpaidRadioButtonActionPerformed

private void accntRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accntRadioButtonActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_accntRadioButtonActionPerformed

private void callsRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_callsRadioButtonActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_callsRadioButtonActionPerformed

private void viewHistButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewHistButtonActionPerformed

    String subNum = dispSubNumField.getText();

    if (!verifyMSISDN(subNum)) {
        cosTextField.setText("Invalid Number");
    } else {
        try {
            if (accntRadioButton.isSelected()) {
                jTable1.setModel(INCmd.getResultSetTableModel((short) 0, subNum));
                appLog.setMSISDN(subNum);
                appLog.setLogEventID(AppLogger.ViewAccHist);
            } else if (callsRadioButton.isSelected()) {
                if (userRole != (short) 1) {
                    jTable1.setModel(INCmd.getResultSetTableModel((short) 1, subNum));
                } else {
                    jTable1.setModel(INCmd.getResultSetTableModel((short) 2, subNum));
                }
                appLog.setMSISDN(subNum);
                appLog.setLogEventID(AppLogger.ViewCallHist);
            } else if (evoucherRadioButton.isSelected()) {
                jTable1.setModel(INCmd.getResultSetTableModel((short) 3, subNum));
                appLog.setMSISDN(subNum);
                appLog.setLogEventID(AppLogger.evoucher);
            } else if (creditTransRadioButton.isSelected()) {
                jTable1.setModel(INCmd.getResultSetTableModel((short) 4, subNum));
                //appLog.setMSISDN(subNum);
                //appLog.setLogEventID(AppLogger.evoucher);
            }
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
    }
}//GEN-LAST:event_viewHistButtonActionPerformed

private void VASListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_VASListValueChanged
    // TODO add your handling code here:
}//GEN-LAST:event_VASListValueChanged

private void applyChngButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyChngButton1ActionPerformed
    // TODO add your handling code here:
    serviceTextArea.setText("");
    VASList.setListData(new java.util.Vector<String>());
    try {
        String subNum = subNumField2.getText();
        java.util.Vector<VASServices> v = INCmd.getVAS(subNum);
        if (!v.isEmpty()) {
            VASList.setListData(v);
            //VASList.s
        } else {
            serviceTextArea.setText("No service subscribed for " + subNum);
        }
    } catch (SQLException d) {
        System.out.println(d.getMessage());
    }
}//GEN-LAST:event_applyChngButton1ActionPerformed

private void removeServiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeServiceButtonActionPerformed
    // TODO add your handling code here:
    if (VASList.getLastVisibleIndex() != -1) {
        Object[] vlist = VASList.getSelectedValues();
        VASServices[] s = new VASServices[vlist.length];
        System.arraycopy(vlist, 0, s, 0, vlist.length);
        String subNum = subNumField2.getText();
        try {
            INCmd.removeVAS(subNum, s);
            java.util.Vector<VASServices> v = INCmd.getVAS(subNum);
            if (!v.isEmpty()) {
                VASList.setListData(v);
                if (v.contains(VASServices.CRBT)) {
                    serviceTextArea.setText("Fun Ring service for " + subNum + " will be deactivated by end of the day");
                    appLog.setMSISDN(subNum);
                    appLog.setLogEventID(AppLogger.funRingUnsub);
                }
            } else {
                VASList.setListData(new java.util.Vector<String>());
                serviceTextArea.setText("All services removed for " + subNum);
            }
            for (int i = 0; i < s.length; i++) //loop to log
            {
                switch (s[i]) {
                    case JAZEERA:
                        appLog.setLogEventID(AppLogger.jazeeraUnsub);
                        break;
                    case BOY_TIPS:
                        appLog.setLogEventID(AppLogger.boyTipsUnsub);
                        break;
                    case GIRL_TIPS:
                        appLog.setLogEventID(AppLogger.girlTipsUnsub);
                        break;
                    case CURRENCY:
                        appLog.setLogEventID(AppLogger.currencyUnsub);
                        break;
                    case HOROSCOPE:
                        appLog.setLogEventID(AppLogger.horoscopeUnsub);
                        break;
                    case PRAYER:
                        appLog.setLogEventID(AppLogger.prayerTimesUnsub);
                        break;
                    case SPORTS_NEWS:
                        appLog.setLogEventID(AppLogger.sportsNewsUnsub);
                        break;
                    case FACEBOOK:
                        appLog.setLogEventID(AppLogger.fbUnsub);
                        break;
                    case SMS:
                        appLog.setLogEventID(AppLogger.SMSExpressUnsub);
                        break;
                    case KOLAREH:
                        appLog.setLogEventID(AppLogger.kolarehUnsub);
                    default:
                        break;
                }
            }
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
    }
}//GEN-LAST:event_removeServiceButtonActionPerformed

private void clrMSC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clrMSC1ActionPerformed
    // TODO add your handling code here:
    subNumField2.setText("");
    TokNumField.setText("");
    if (TokNumAdjButton.isVisible()) {
        TokNumAdjButton.setEnabled(false);
    }
    setTokButton.setEnabled(false);
    serviceTextArea.setText("");
    buttonGroup1.clearSelection();
    VASList.setListData(new java.util.Vector<String>());
}//GEN-LAST:event_clrMSC1ActionPerformed

private void evoucherRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evoucherRadioButtonActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_evoucherRadioButtonActionPerformed

private void creditTransRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creditTransRadioButtonActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_creditTransRadioButtonActionPerformed

private void funRingSubButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_funRingSubButtonActionPerformed
    // TODO add your handling code here:
    String subNum = subNumField2.getText();
    if (verifyMSISDN(subNum)) {
        String ringCode = songCodeTextField.getText().isEmpty() ? INCmd.defaultRingID : songCodeTextField.getText();
        try {
            INCmd.setFunRingSong(subNum, ringCode);
            appLog.setMSISDN(subNum);
            appLog.setLogEventID(AppLogger.funRingSub);
            serviceTextArea.setText(subNum + " fun ring changed to song code " + ringCode);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(this, sqle, "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}//GEN-LAST:event_funRingSubButtonActionPerformed

    private void TokNumAdjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TokNumAdjButtonActionPerformed
        // TODO add your handling code here:
        try {
            String tokSub = subNumField2.getText();
            String tokSubDest = TokNumField.getText();

            Object[] refRes = INCmd.checkRefForSimReplace(tokSub, tokSubDest, tokSubDest, tokSubDest);

            if (refRes != null) {
                short[] rs = (short[]) refRes[0];

                if (rs[0] == (short) 0) {
                    INCmd.removeTOKNum(tokSub, tokSubDest);
                    appLog.setMSISDN(tokSub);
                    appLog.setLogActParam(tokSubDest);
                    appLog.setLogEventID(AppLogger.delCurrTOKNum);
                    JOptionPane.showMessageDialog(TOKPanel, tokSubDest + " has been cancelled from being the TOK number of " + tokSub, "Number Removed", JOptionPane.INFORMATION_MESSAGE);
                    TokNumField.setText("");
                    tokSubDest = null;
                    TokNumField.setEnabled(true);
                    TokNumField.setEditable(true);
                    setTokButton.setEnabled(true);
                    TokNumAdjButton.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(TOKPanel, tokSub + " previously called " + tokSubDest + ". You cannot modify the TOK number", "TOK Number Unchanged", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(TOKPanel, tokSub + ": Invalid number", "TOK Number Unchanged", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException sqle) {
            System.out.println("DB Error while removing TOK Number: " + sqle.getMessage());
        }
    }//GEN-LAST:event_TokNumAdjButtonActionPerformed

        private void SMSCheckDelivButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SMSCheckDelivButtonActionPerformed

            SMSDeliveryProgressBar.setValue(0);
            String SMSOrigNum = SMSOrigNumField.getText();

            if (verifyMSISDN(SMSOrigNum)) {
                String SMSDestNum = SMSDestNumField.getText();
                java.util.Date strtDate, endDate;

                if (!"".equals(SMSDestNum) && (strtDate = SMSStartDateChooser.getDate()) != null) {

                    endDate = SMSEndDateChooser.getDate();

                    SMSOrigNumField.setEnabled(false);
                    SMSDestNumField.setEnabled(false);
                    SMSCheckDelivButton.setEnabled(false);
                    clearSMSDelivButton.setEnabled(false);
                    SMSStartDateChooser.setEnabled(false);
                    SMSEndDateChooser.setEnabled(false);

                    SMSDeliveryWorker sWorker = new SMSDeliveryWorker(SMSOrigNum, SMSDestNum, strtDate, endDate);
                    /*
                     sWorker.addPropertyChangeListener(new PropertyChangeListener() {
                    
                     @Override
                     public void propertyChange(PropertyChangeEvent evt) {
                     if ("progress".equals(evt.getPropertyName())) {
                     System.out.println("Progress property catched.");
                     SMSDeliveryProgressBar.setValue((Integer)evt.getNewValue());
                     }
                     }
                     });
                     */
                    sWorker.execute();

                } else {
                    jTable2.setModel(new DefaultTableModel());
                    JOptionPane.showMessageDialog(SMSDeliveryPanel, "Sender Number and Start Date cannot be empty", "Missing Input", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(SMSDeliveryPanel, "Invalid Africell Originating Number", "", JOptionPane.ERROR_MESSAGE);
                jTable2.setModel(new DefaultTableModel());
            }
        // TODO add your handling code here:}//GEN-LAST:event_SMSCheckDelivButtonActionPerformed
    }
        private void clearSMSDelivButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSMSDelivButtonActionPerformed

        // TODO add your handling code here:}//GEN-LAST:event_clearSMSDelivButtonActionPerformed
        SMSOrigNumField.setText("");
        SMSDestNumField.setText("");
        SMSStartDateChooser.setDate(null);
        SMSEndDateChooser.setDate(null);
        SMSDeliveryProgressBar.setValue(0);
        jTable2.setModel(new DefaultTableModel());

    }
        private void SMSOrigNumFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SMSOrigNumFieldActionPerformed
        // TODO add your handling code here:}//GEN-LAST:event_SMSOrigNumFieldActionPerformed
    }
        private void SMSOrigNumFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SMSOrigNumFieldMouseReleased

        // TODO add your handling code here:}//GEN-LAST:event_SMSOrigNumFieldMouseReleased
        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }
        private void SMSOrigNumFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SMSOrigNumFieldMousePressed

        // TODO add your handling code here:}//GEN-LAST:event_SMSOrigNumFieldMousePressed
        mousePressComp = evt.getComponent();

        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
        }
    }

        private void EVCSenderFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EVCSenderFieldMousePressed
            // TODO add your handling code here:
            mousePressComp = evt.getComponent();

            if (evt.isPopupTrigger()) {
                jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
            }
    }//GEN-LAST:event_EVCSenderFieldMousePressed

    private void EVCSenderFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EVCSenderFieldMouseReleased

        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_EVCSenderFieldMouseReleased

    private void EVCSenderFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EVCSenderFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EVCSenderFieldActionPerformed

    private void clearEVCButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearEVCButtonActionPerformed
        // TODO add your handling code here:
        payBackEVCButton.setEnabled(false);
        normToEVCButton.setEnabled(false);
        retCrdtTransButton.setEnabled(false);
        normToEVCButton.setEnabled(false);
        EVCSenderField.setText("");
        EVCSenderField.setEditable(true);
        EVCRecipientField.setText("");
        EVCRecipientField.setEditable(true);
        EVCRecipientField.setText("");
        EVCAmount1Field.setEditable(true);
        EVCAmount1Field.setText("");
        EVCAmount2Field.setEditable(true);
        EVCAmount2Field.setText("");
        EVCAmount3Field.setEditable(true);
        EVCAmount3Field.setText("");
        EVCTransactionDateChooser.setDate(null);
        EVCTransactionDateChooser.setEnabled(true);
        jTable3.setModel(new DefaultTableModel());
        buttonGroupEVC.clearSelection();
    }//GEN-LAST:event_clearEVCButtonActionPerformed

    private void searchEVCButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchEVCButtonActionPerformed
        // TODO add your handling code here:
        String dealerNum = this.EVCSenderField.getText();
        String receipientNum = this.EVCRecipientField.getText();
        String minAmtStr = "250";
        float minTransAmnt;
        if (this.userRole == 1) {
            try {
                minTransAmnt = Float.parseFloat(this.minAmountField.getText());
            } catch (NumberFormatException nfe) {
                minTransAmnt = Float.parseFloat(minAmtStr);
                this.minAmountField.setText(minAmtStr);
            }
        } else {
            minTransAmnt = Float.parseFloat(minAmtStr);
        }
        float[] transAmts = new float[3];

        if ((verifyMSISDN(dealerNum)) && (verifyMSISDN(receipientNum))) {
            Float transAmt = Float.valueOf(this.EVCAmount1Field.getText().isEmpty() ? "0" : this.EVCAmount1Field.getText());
            transAmts[0] = (transAmt.isNaN() ? 0.0F : transAmt.floatValue());
            transAmt = Float.valueOf(this.EVCAmount2Field.getText().isEmpty() ? "0" : this.EVCAmount2Field.getText());
            transAmts[1] = (transAmt.isNaN() ? 0.0F : transAmt.floatValue());
            transAmt = Float.valueOf(this.EVCAmount3Field.getText().isEmpty() ? "0" : this.EVCAmount3Field.getText());
            transAmts[2] = (transAmt.isNaN() ? 0.0F : transAmt.floatValue());

            Date transDate = this.EVCTransactionDateChooser.getDate();

            if (transDate != null) {
                if (transAmts[0] + transAmts[1] + transAmts[2] >= minTransAmnt) {
                    if (this.dealerToSubRadioButton.isSelected()) {
                        try {
                            TableModel res = this.INCmd.searchEVCTrans(dealerNum, receipientNum, transAmts, transDate);
                            if (res != null) {
                                if (res.getClass().equals(ArrayListTableModel.class)) {
                                    this.jTable3.setModel(res);
                                    this.rsHandler = new ListSelectionHandler();
                                    ListSelectionModel listSelModel = this.jTable3.getSelectionModel();
                                    listSelModel.addListSelectionListener(this.rsHandler);
                                    this.jTable3.setSelectionModel(listSelModel);

                                    this.payBackEVCButton.setEnabled(true);
                                    this.normToEVCButton.setEnabled(true);
                                    this.retCrdtTransButton.setEnabled(false);
                                    this.retCrdtTransButton.setEnabled(false);
                                    this.EVCSenderField.setEditable(false);
                                    this.EVCRecipientField.setEditable(false);
                                    this.EVCAmount1Field.setEditable(false);
                                    this.EVCAmount2Field.setEditable(false);
                                    this.EVCAmount3Field.setEditable(false);
                                    this.EVCTransactionDateChooser.setEnabled(false);
                                } else {
                                    this.jTable3.setModel(new DefaultTableModel());
                                    this.payBackEVCButton.setEnabled(false);
                                    this.retCrdtTransButton.setEnabled(false);
                                    this.normToEVCButton.setEnabled(false);
                                    this.retCrdtTransButton.setEnabled(false);
                                    JOptionPane.showMessageDialog(this.EVCPanel, "Transaction occured but has already been reveresed", "", 1);
                                }

                                this.appLog.setMSISDN(dealerNum);
                                this.appLog.setLogActParam(receipientNum);
                                this.appLog.setLogEventID((short) 37);
                            } else {
                                this.jTable3.setModel(new DefaultTableModel());
                                this.payBackEVCButton.setEnabled(false);
                                this.retCrdtTransButton.setEnabled(false);
                                this.normToEVCButton.setEnabled(false);
                                this.retCrdtTransButton.setEnabled(false);
                                JOptionPane.showMessageDialog(this.EVCPanel, "Transaction Not Found", "Input Error", 1);
                            }
                        } catch (SQLException sqle) {
                            JOptionPane.showMessageDialog(this.EVCPanel, "Cannot check transaction - system error.", "DB Error", 0);

                            System.out.println(sqle.getMessage());
                        }
                    } else if (this.ordSubRadioButton.isSelected()) {
                        try {
                            TableModel res = this.INCmd.searchSMSCreditTrans(dealerNum, receipientNum, transAmts, transDate);
                            if (res != null) {
                                if (res.getClass().equals(ArrayListTableModel.class)) {
                                    this.jTable3.setModel(res);
                                    this.rsHandler = new ListSelectionHandler();
                                    ListSelectionModel listSelModel = this.jTable3.getSelectionModel();
                                    listSelModel.addListSelectionListener(this.rsHandler);
                                    this.jTable3.setSelectionModel(listSelModel);

                                    this.retCrdtTransButton.setEnabled(true);
                                    this.EVCSenderField.setEditable(false);
                                    this.EVCRecipientField.setEditable(false);
                                    this.EVCAmount1Field.setEditable(false);
                                    this.EVCTransactionDateChooser.setEnabled(false);
                                } else {
                                    this.jTable3.setModel(new DefaultTableModel());
                                    this.payBackEVCButton.setEnabled(false);
                                    this.retCrdtTransButton.setEnabled(false);
                                    this.normToEVCButton.setEnabled(false);
                                    this.retCrdtTransButton.setEnabled(false);

                                    JOptionPane.showMessageDialog(this.EVCPanel, "Transaction occured but has already been reveresed", "", 1);
                                }

                                this.appLog.setMSISDN(dealerNum);
                                this.appLog.setLogActParam(receipientNum);
                                this.appLog.setLogEventID((short) 37);
                            } else {
                                this.jTable3.setModel(new DefaultTableModel());
                                this.payBackEVCButton.setEnabled(false);
                                this.retCrdtTransButton.setEnabled(false);
                                this.normToEVCButton.setEnabled(false);
                                this.retCrdtTransButton.setEnabled(false);
                                JOptionPane.showMessageDialog(this.EVCPanel, "Transaction Not Found", "Input Error", 1);
                            }
                        } catch (SQLException sqle) {
                            JOptionPane.showMessageDialog(this.EVCPanel, "Cannot check transaction - system error.", "DB Error", 0);

                            System.out.println(sqle.getMessage());
                        }
                    } else if (this.dealerToDealerRadioButton.isSelected()) {
                        try {
                            TableModel res = this.INCmd.searchDealerTrans(dealerNum, receipientNum, transAmts, transDate);
                            if (res != null) {
                                if (res.getClass().equals(ArrayListTableModel.class)) {
                                    this.jTable3.setModel(res);
                                    this.rsHandler = new ListSelectionHandler();
                                    ListSelectionModel listSelModel = this.jTable3.getSelectionModel();
                                    listSelModel.addListSelectionListener(this.rsHandler);
                                    this.jTable3.setSelectionModel(listSelModel);

                                    this.payBackEVCButton.setEnabled(false);
                                    this.retCrdtTransButton.setEnabled(true);
                                    this.EVCSenderField.setEditable(false);
                                    this.EVCRecipientField.setEditable(false);
                                    this.EVCAmount1Field.setEditable(false);
                                    this.EVCTransactionDateChooser.setEnabled(false);
                                } else {
                                    this.jTable3.setModel(new DefaultTableModel());
                                    this.payBackEVCButton.setEnabled(false);
                                    this.retCrdtTransButton.setEnabled(false);
                                    this.normToEVCButton.setEnabled(false);
                                    this.retCrdtTransButton.setEnabled(false);
                                    JOptionPane.showMessageDialog(this.EVCPanel, "Transaction occured but has already been reveresed", "", 1);
                                }

                                this.appLog.setMSISDN(dealerNum);
                                this.appLog.setLogActParam(receipientNum);
                                this.appLog.setLogEventID((short) 37);
                            } else {
                                this.jTable3.setModel(new DefaultTableModel());
                                this.payBackEVCButton.setEnabled(false);
                                this.retCrdtTransButton.setEnabled(false);
                                this.normToEVCButton.setEnabled(false);
                                this.retCrdtTransButton.setEnabled(false);
                                JOptionPane.showMessageDialog(this.EVCPanel, "Transaction Not Found", "Input Error", 1);
                            }
                        } catch (SQLException sqle) {
                            JOptionPane.showMessageDialog(this.EVCPanel, "Cannot check transaction - system error.", "DB Error", 0);

                            System.out.println(sqle.getMessage() + "\n" + sqle.getSQLState());
                            sqle.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this.EVCPanel, "Please select transaction type.", "Input Error", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Minimum Transaction Amount to Check is " + minTransAmnt + " Dalasis.", "Input Error", 0);
                }
            } else {
                JOptionPane.showMessageDialog(this.EVCPanel, "Please select transaction date.", "Input Error", 0);
            }
        } else {
            JOptionPane.showMessageDialog(this.EVCPanel, "Invalid Africell Dealer/Receipient Numbers", "Input Error", 0);
        }
    }//GEN-LAST:event_searchEVCButtonActionPerformed

    private void EVCAmount1FieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EVCAmount1FieldMouseReleased

        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_EVCAmount1FieldMouseReleased

    private void EVCAmount1FieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EVCAmount1FieldMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EVCAmount1FieldMousePressed

    private void EVCAmount1FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EVCAmount1FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EVCAmount1FieldActionPerformed

    private void payBackEVCButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payBackEVCButtonActionPerformed
        try {
            if (this.jTable3.getModel().getRowCount() == 1) {
                short res = this.INCmd.adjustEVCTransfer(this.EVCSenderField.getText(), this.EVCRecipientField.getText(), Float.valueOf((String) this.jTable3.getModel().getValueAt(0, 3)), Float.valueOf((String) this.jTable3.getModel().getValueAt(0, 4)));
                if (res == 0) {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Subscriber does not exist in the system.", "Info", 0);
                } else if (res == 1) {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Transferred Amount already Consumed. Cannot be reversed.", "Info", 0);
                } else {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Transaction of " + this.jTable3.getModel().getValueAt(0, 3) + " GMD on " + this.jTable3.getModel().getValueAt(0, 2).toString() + " successfully reversed.", "Reversal Complete", 1);

                    this.appLog.setMSISDN(this.EVCSenderField.getText());
                    this.appLog.setLogActParam(this.EVCRecipientField.getText());
                    this.appLog.setLogActInvoice((String) this.jTable3.getModel().getValueAt(0, 3));
                    this.appLog.setLogActionComments((String) this.jTable3.getModel().getValueAt(0, 2));
                    this.appLog.setLogEventID((short) 38);
                    this.jTable3.setModel(new DefaultTableModel());
                }
            } else {
                String msg = "";
                if (this.rsHandler.getSelectedRowsPos() != null) {
                    for (Integer r : this.rsHandler.getSelectedRowsPos()) {
                        short res = this.INCmd.adjustEVCTransfer(this.EVCSenderField.getText(), this.EVCRecipientField.getText(), Float.valueOf((String) this.jTable3.getModel().getValueAt(r.intValue(), 3)), Float.valueOf((String) this.jTable3.getModel().getValueAt(r.intValue(), 4)));
                        if (res == 0) {
                            JOptionPane.showMessageDialog(this.EVCPanel, "Subscriber does not exist in the system.", "Info", 0);

                            break;
                        }
                        if (res == 1) {
                            msg = msg.concat("Transferred amount of " + this.jTable3.getModel().getValueAt(r.intValue(), 3) + " on " + this.jTable3.getModel().getValueAt(r.intValue(), 2) + " already Consumed. Cannot be reversed.\n");
                        } else {
                            this.appLog.setMSISDN(this.EVCSenderField.getText());
                            this.appLog.setLogActParam(this.EVCRecipientField.getText());
                            this.appLog.setLogActInvoice((String) this.jTable3.getModel().getValueAt(r.intValue(), 3));
                            this.appLog.setLogActionComments((String) this.jTable3.getModel().getValueAt(r.intValue(), 2));
                            this.appLog.setLogEventID((short) 38);
                            JOptionPane.showMessageDialog(this.EVCPanel, "Transaction of " + this.jTable3.getModel().getValueAt(r.intValue(), 3) + " GMD on " + this.jTable3.getModel().getValueAt(r.intValue(), 2).toString() + " successfully reversed.", "Reversal Complete", 1);
                        }

                    }

                    this.jTable3.setModel(new DefaultTableModel());
                    if (!msg.isEmpty()) {
                        JOptionPane.showMessageDialog(this.EVCPanel, msg, "Info", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Please choose which transactions to reverse", "Missing Input", 2);
                }
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(this.EVCPanel, "Cannot check transaction - system error.", "DB Error", 0);

            System.out.println(sqle.getMessage());
        }

        this.payBackEVCButton.setEnabled(false);
        this.normToEVCButton.setEnabled(false);
        this.EVCSenderField.setEditable(true);
        this.EVCRecipientField.setEditable(true);
        this.EVCAmount1Field.setEditable(true);
        this.EVCAmount2Field.setEditable(true);
        this.EVCAmount3Field.setEditable(true);
        this.EVCTransactionDateChooser.setEnabled(true);
    }//GEN-LAST:event_payBackEVCButtonActionPerformed

    private void EVCRecipientFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EVCRecipientFieldMousePressed
        // TODO add your handling code here:
        mousePressComp = evt.getComponent();

        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_EVCRecipientFieldMousePressed

    private void SMSDestNumFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SMSDestNumFieldMousePressed
        // TODO add your handling code here:
        mousePressComp = evt.getComponent();

        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_SMSDestNumFieldMousePressed

    private void normToEVCButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normToEVCButtonActionPerformed
        try {
            if (this.jTable3.getModel().getRowCount() == 1) {
                short res = this.INCmd.adjustNormaltoEVC(this.EVCSenderField.getText(), this.EVCRecipientField.getText(), Float.valueOf((String) this.jTable3.getModel().getValueAt(0, 3)), Float.valueOf((String) this.jTable3.getModel().getValueAt(0, 4)));
                if (res == 0) {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Subscriber does not exist in the system.", "Info", 0);
                } else if (res == 1) {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Transferred Amount already Consumed. Cannot be reversed.", "Info", 0);
                } else {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Transaction of " + this.jTable3.getModel().getValueAt(0, 3) + " GMD on " + this.jTable3.getModel().getValueAt(0, 2).toString() + " successfully reversed.", "Reversal Complete", 1);

                    this.appLog.setMSISDN(this.EVCSenderField.getText());
                    this.appLog.setLogActParam(this.EVCRecipientField.getText());
                    this.appLog.setLogActInvoice((String) this.jTable3.getModel().getValueAt(0, 3));
                    this.appLog.setLogActionComments((String) this.jTable3.getModel().getValueAt(0, 2));
                    this.appLog.setLogEventID((short) 38);
                    this.jTable3.setModel(new DefaultTableModel());
                }
            } else {
                String msg = "";
                if (this.rsHandler.getSelectedRowsPos() != null) {
                    for (Integer r : this.rsHandler.getSelectedRowsPos()) {
                        short res = this.INCmd.adjustNormaltoEVC(this.EVCSenderField.getText(), this.EVCRecipientField.getText(), Float.valueOf((String) this.jTable3.getModel().getValueAt(r.intValue(), 3)), Float.valueOf((String) this.jTable3.getModel().getValueAt(r.intValue(), 4)));
                        if (res == 0) {
                            JOptionPane.showMessageDialog(this.EVCPanel, "Subscriber does not exist in the system.", "Info", 0);

                            break;
                        }
                        if (res == 1) {
                            msg = msg.concat("Transferred amount of " + this.jTable3.getModel().getValueAt(r.intValue(), 3) + " on " + this.jTable3.getModel().getValueAt(r.intValue(), 2) + " already Consumed. Cannot be reversed.\n");
                        } else {
                            this.appLog.setMSISDN(this.EVCSenderField.getText());
                            this.appLog.setLogActParam(this.EVCRecipientField.getText());
                            this.appLog.setLogActInvoice((String) this.jTable3.getModel().getValueAt(r.intValue(), 3));
                            this.appLog.setLogActionComments((String) this.jTable3.getModel().getValueAt(r.intValue(), 2));
                            this.appLog.setLogEventID((short) 38);

                            JOptionPane.showMessageDialog(this.EVCPanel, "Transaction of " + this.jTable3.getModel().getValueAt(r.intValue(), 3) + " GMD on " + this.jTable3.getModel().getValueAt(r.intValue(), 2).toString() + " successfully reversed.", "Reversal Complete", 1);
                        }

                    }

                    this.jTable3.setModel(new DefaultTableModel());
                    if (!msg.isEmpty()) {
                        JOptionPane.showMessageDialog(this.EVCPanel, msg, "Info", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Please choose which transactions to reverse", "Missing Input", 2);
                }
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(this.EVCPanel, "Cannot check transaction - system error.", "DB Error", 0);

            System.out.println(sqle.getMessage());
        }

        this.payBackEVCButton.setEnabled(false);
        this.normToEVCButton.setEnabled(false);
        this.EVCSenderField.setEditable(true);
        this.EVCRecipientField.setEditable(true);
        this.EVCAmount1Field.setEditable(true);
        this.EVCAmount2Field.setEditable(true);
        this.EVCAmount3Field.setEditable(true);
        this.EVCTransactionDateChooser.setEnabled(true);
    }//GEN-LAST:event_normToEVCButtonActionPerformed

    private void crAdReceipientNumFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crAdReceipientNumFieldMousePressed
        // TODO add your handling code here:
        mousePressComp = evt.getComponent();

        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_crAdReceipientNumFieldMousePressed

    private void crAdReceipientNumFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crAdReceipientNumFieldMouseReleased
        // TODO add your handling code here:
        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_crAdReceipientNumFieldMouseReleased

    private void crAdReceipientNumFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crAdReceipientNumFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_crAdReceipientNumFieldActionPerformed

    private void crAdAmntFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crAdAmntFieldMousePressed
        // TODO add your handling code here:
        mousePressComp = evt.getComponent();

        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(mousePressComp, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_crAdAmntFieldMousePressed

    private void crAdAmntFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crAdAmntFieldMouseReleased
        // TODO add your handling code here:
        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_crAdAmntFieldMouseReleased

    private void crAdAmntFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crAdAmntFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_crAdAmntFieldActionPerformed

    private void clrCrdAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clrCrdAddButtonActionPerformed
        // TODO add your handling code here:
        crAdReceipientNumField.setText("");
        crAdAmntField.setText("");
        credAddCmntTextField.setText("");
        requesterComboBox.setSelectedIndex(-1);
    }//GEN-LAST:event_clrCrdAddButtonActionPerformed

    private void addCredButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCredButtonActionPerformed
        // TODO add your handling code here:
        String receipientNum = crAdReceipientNumField.getText();
        if (verifyMSISDN(receipientNum)) {
            if (!crAdAmntField.getText().isEmpty()) {
                if (requesterComboBox.getSelectedIndex() > -1) {
                    try {
                        float adjAmount = Float.parseFloat(crAdAmntField.getText());
                        if (realBalRadioButton.isSelected()) {//Real Balance Selected
                            INCmd.adjustSubscriberBalance(receipientNum, adjAmount, "Complimentary");
                            JOptionPane.showMessageDialog(servicePanel, adjAmount + " was adjusted on " + receipientNum + " real balance.", "Account Balance Change", JOptionPane.INFORMATION_MESSAGE);
                            appLog.setMSISDN(receipientNum);
                            appLog.setLogActInvoice((String) requesterComboBox.getSelectedItem());
                            appLog.setLogActParam(Float.toString(adjAmount));
                            appLog.setLogActionComments(credAddCmntTextField.getText());
                            appLog.setLogEventID(AppLogger.creditAdd);
                        } else { //Free Money Selected
                            INCmd.adjustFreeMoney(receipientNum, adjAmount, "Complimentary");
                            JOptionPane.showMessageDialog(servicePanel, adjAmount + " was adjusted on " + receipientNum + " free money balance.", "Free Money Change", JOptionPane.INFORMATION_MESSAGE);
                            appLog.setMSISDN(receipientNum);
                            appLog.setLogActInvoice((String) requesterComboBox.getSelectedItem());
                            appLog.setLogActParam(Float.toString(adjAmount));
                            appLog.setLogActionComments(credAddCmntTextField.getText());
                            appLog.setLogEventID(AppLogger.freeMoneyAdjust);
                        }
                    } catch (SQLException sqle) {
                        JOptionPane.showMessageDialog(credAddPanel, "DB Error at Event Log", "DB Error", JOptionPane.ERROR_MESSAGE);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(credAddPanel, crAdAmntField.getText() + " is not a valid amount.", "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(credAddPanel, "Unselected Credit Requester", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else //The amount field is empty
            {
                JOptionPane.showMessageDialog(credAddPanel, "Invalid Amount to Add", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else //The receipient number is empty
        {
            JOptionPane.showMessageDialog(credAddPanel, "Invalid Africell Subscriber Number", "Input Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_addCredButtonActionPerformed

    private void gprsSubButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gprsSubButtonActionPerformed
        // TODO add your handling code here:
        String dataserviceSubscriber = subNumField2.getText();
        if (verifyMSISDN(dataserviceSubscriber)) {
            try {
                short procOutput = INCmd.fixDataSubscription(dataserviceSubscriber);
                if (procOutput == (short) 0) {
                    JOptionPane.showMessageDialog(GPRSSubPanel, "Subscriber needs to purchase 3G tokens", "Subscription Expired", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(GPRSSubPanel, "3G Subsription fixed", "Subscription Valid", JOptionPane.INFORMATION_MESSAGE);
                }

                appLog.setMSISDN(dataserviceSubscriber);
                appLog.setLogActParam(Short.toString(procOutput));
                appLog.setLogEventID(AppLogger.dataSubscrComplain);

            } catch (SQLException sqle) {
                System.out.println("DB Error while call procedure to fix 3G subscription");
            }
        } else {
            JOptionPane.showMessageDialog(GPRSSubPanel, "Invalid Africell Subscriber Number", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_gprsSubButtonActionPerformed

    private void retCrdtTransButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retCrdtTransButtonActionPerformed
        try {
            if (this.jTable3.getModel().getRowCount() == 1) {
                short res = this.INCmd.adjustCrdtTransfer(this.EVCSenderField.getText(), this.EVCRecipientField.getText(), Float.valueOf((String) this.jTable3.getModel().getValueAt(0, 3)));
                if (res == 0) {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Subscriber does not exist in the system.", "Info", 0);
                } else if (res == 1) {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Transferred Amount already used. Cannot be reversed", "Info", 0);
                } else {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Transaction of " + this.jTable3.getModel().getValueAt(0, 3) + " GMD on " + this.jTable3.getModel().getValueAt(0, 2).toString() + " successfully reversed.", "Reversal Complete", 1);

                    this.appLog.setMSISDN(this.EVCSenderField.getText());
                    this.appLog.setLogActParam(this.EVCRecipientField.getText());
                    this.appLog.setLogActInvoice((String) this.jTable3.getModel().getValueAt(0, 3));
                    this.appLog.setLogActionComments((String) this.jTable3.getModel().getValueAt(0, 2));
                    this.appLog.setLogEventID((short) 38);
                    this.jTable3.setModel(new DefaultTableModel());
                }
            } else {
                String msg = "";
                if (this.rsHandler.getSelectedRowsPos() != null) {
                    for (Integer r : this.rsHandler.getSelectedRowsPos()) {
                        try {
                            short res = this.INCmd.adjustCrdtTransfer(this.EVCSenderField.getText(), this.EVCRecipientField.getText(), Float.valueOf((String) this.jTable3.getModel().getValueAt(r.intValue(), 3)));
                            if (res == 0) {
                                JOptionPane.showMessageDialog(this.EVCPanel, "Subscriber does not exist in the system.", "Info", 0);

                                break;
                            }
                            if (res == 1) {
                                msg = msg.concat("Transferred amount of " + this.jTable3.getModel().getValueAt(r.intValue(), 3) + " on " + this.jTable3.getModel().getValueAt(r.intValue(), 2) + " already Consumed. Cannot be reversed.\n");
                            } else {
                                this.appLog.setMSISDN(this.EVCSenderField.getText());
                                this.appLog.setLogActParam(this.EVCRecipientField.getText());
                                this.appLog.setLogActInvoice((String) this.jTable3.getModel().getValueAt(r.intValue(), 3));
                                this.appLog.setLogActionComments((String) this.jTable3.getModel().getValueAt(r.intValue(), 2));
                                this.appLog.setLogEventID((short) 38);

                                JOptionPane.showMessageDialog(this.EVCPanel, "Transaction of " + this.jTable3.getModel().getValueAt(r.intValue(), 3) + " GMD on " + this.jTable3.getModel().getValueAt(r.intValue(), 2).toString() + " successfully reversed.", "Reversal Complete", 1);
                            }
                        } catch (SQLException sqle) {
                            JOptionPane.showMessageDialog(this.EVCPanel, "Cannot check transaction - system error.", "DB Error", 0);

                            System.out.println(sqle.getMessage());
                        }
                    }
                    this.jTable3.setModel(new DefaultTableModel());
                    if (!msg.isEmpty()) {
                        JOptionPane.showMessageDialog(this.EVCPanel, msg, "Info", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Please choose which transactions to reverse", "Missing Input", 2);
                }
            }

            this.retCrdtTransButton.setEnabled(false);
            this.EVCSenderField.setEditable(true);
            this.EVCRecipientField.setEditable(true);
            this.EVCAmount1Field.setEditable(true);
            this.EVCAmount2Field.setEditable(true);
            this.EVCAmount3Field.setEditable(true);
            this.EVCTransactionDateChooser.setEnabled(true);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(this.EVCPanel, "Cannot check transaction - system error.", "DB Error", 0);

            System.out.println(sqle.getMessage());
        }
    }//GEN-LAST:event_retCrdtTransButtonActionPerformed

    private void EVCRecipientFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EVCRecipientFieldMouseReleased

        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_EVCRecipientFieldMouseReleased

    private void SMSDestNumFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SMSDestNumFieldMouseReleased
        // TODO add your handling code here:
        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_SMSDestNumFieldMouseReleased

    private void dataDispComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataDispComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataDispComboBoxActionPerformed

    private void EVCAmount2FieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EVCAmount2FieldMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EVCAmount2FieldMousePressed

    private void EVCAmount2FieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EVCAmount2FieldMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_EVCAmount2FieldMouseReleased

    private void EVCAmount2FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EVCAmount2FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EVCAmount2FieldActionPerformed

    private void EVCAmount3FieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EVCAmount3FieldMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EVCAmount3FieldMousePressed

    private void EVCAmount3FieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EVCAmount3FieldMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_EVCAmount3FieldMouseReleased

    private void EVCAmount3FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EVCAmount3FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EVCAmount3FieldActionPerformed

    private void minAmountFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minAmountFieldMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_minAmountFieldMousePressed

    private void minAmountFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minAmountFieldMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_minAmountFieldMouseReleased

    private void minAmountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minAmountFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minAmountFieldActionPerformed

    private void payBackDlrButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payBackDlrButtonActionPerformed
        // TODO add your handling code here:
        try {
            if (this.jTable3.getModel().getRowCount() == 1) {
                short res = this.INCmd.adjustDealerTransfer(this.EVCSenderField.getText(), this.EVCRecipientField.getText(), Float.valueOf((String) this.jTable3.getModel().getValueAt(0, 3)));
                if (res == 0) {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Subscriber does not exist in the system.", "Info", 0);
                } else if (res == 1) {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Transferred Amount already used. Cannot be reversed", "Info", 0);
                } else {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Transaction of " + this.jTable3.getModel().getValueAt(0, 3) + " GMD on " + this.jTable3.getModel().getValueAt(0, 2).toString() + " successfully reversed.", "Reversal Complete", 1);

                    this.appLog.setMSISDN(this.EVCSenderField.getText());
                    this.appLog.setLogActParam(this.EVCRecipientField.getText());
                    this.appLog.setLogActInvoice((String) this.jTable3.getModel().getValueAt(0, 3));
                    this.appLog.setLogActionComments((String) this.jTable3.getModel().getValueAt(0, 2));
                    this.appLog.setLogEventID((short) 38);
                    this.jTable3.setModel(new DefaultTableModel());
                }
            } else {
                String msg = "";
                if (this.rsHandler.getSelectedRowsPos() != null) {
                    for (Integer r : this.rsHandler.getSelectedRowsPos()) {
                        short res = this.INCmd.adjustDealerTransfer(this.EVCSenderField.getText(), this.EVCRecipientField.getText(), Float.valueOf((String) this.jTable3.getModel().getValueAt(r.intValue(), 3)));
                        if (res == 0) {
                            JOptionPane.showMessageDialog(this.EVCPanel, "Subscriber does not exist in the system.", "Info", 0);

                            break;
                        }
                        if (res == 1) {
                            msg = msg.concat("Transferred amount of " + this.jTable3.getModel().getValueAt(r.intValue(), 3) + " on " + this.jTable3.getModel().getValueAt(r.intValue(), 2) + " already Consumed. Cannot be reversed.\n");
                        } else {
                            this.appLog.setMSISDN(this.EVCSenderField.getText());
                            this.appLog.setLogActParam(this.EVCRecipientField.getText());
                            this.appLog.setLogActInvoice((String) this.jTable3.getModel().getValueAt(r.intValue(), 3));
                            this.appLog.setLogActionComments((String) this.jTable3.getModel().getValueAt(r.intValue(), 2));
                            this.appLog.setLogEventID((short) 38);

                            JOptionPane.showMessageDialog(this.EVCPanel, "Transaction of " + this.jTable3.getModel().getValueAt(r.intValue(), 3) + " GMD on " + this.jTable3.getModel().getValueAt(r.intValue(), 2).toString() + " successfully reversed.", "Reversal Complete", 1);
                        }

                    }

                    this.jTable3.setModel(new DefaultTableModel());
                    if (!msg.isEmpty()) {
                        JOptionPane.showMessageDialog(this.EVCPanel, msg, "Info", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(this.EVCPanel, "Please choose which transactions to reverse", "Missing Input", 2);
                }
            }

            this.payBackEVCButton.setEnabled(false);
            this.payBackDlrButton.setEnabled(false);
            this.EVCSenderField.setEditable(true);
            this.EVCRecipientField.setEditable(true);
            this.EVCAmount1Field.setEditable(true);
            this.EVCAmount2Field.setEditable(true);
            this.EVCAmount3Field.setEditable(true);
            this.EVCTransactionDateChooser.setEnabled(true);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(this.EVCPanel, "Cannot check transaction - system error.", "DB Error", 0);

            System.out.println(sqle.getMessage());
        }
    }//GEN-LAST:event_payBackDlrButtonActionPerformed

    private void SMSDestNumFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SMSDestNumFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SMSDestNumFieldActionPerformed

    private void KolarehCreditFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KolarehCreditFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KolarehCreditFieldActionPerformed

    private boolean verifyMSISDN(String num) {
        return ((num.matches("7[0-9][0-9][0-9][0-9][0-9][0-9]")) || (num.matches("2[0-3][0-9][0-9][0-9][0-9][0-9]"))) && (num.length() == 7);
    }

    private boolean verifyPrepaidMSISDN(String msisdn) {
        return ((!msisdn.matches("7[0-9][0-9][0-9][0-9][0-9][0-9]")) && (!msisdn.matches("2[0-3][0-9][0-9][0-9][0-9][0-9]"))) || ((msisdn.length() == 7) || (((msisdn.matches("1617[0-9][0-9][0-9][0-9][0-9][0-9]")) || (msisdn.matches("1612[0-3][0-9][0-9][0-9][0-9][0-9]"))) && (msisdn.length() == 10)));
    }

    private boolean verifyIMSI(String num) {
        return (num.matches("9[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]") && num.length() == 10);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ControlRoomUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane AppPane;
    private javax.swing.JPanel CRBTSubPanel;
    private javax.swing.JTextField CallFwdDestTextField;
    private javax.swing.JTextField EVCAmount1Field;
    private javax.swing.JTextField EVCAmount2Field;
    private javax.swing.JTextField EVCAmount3Field;
    private javax.swing.JPanel EVCPanel;
    private javax.swing.JTextField EVCRecipientField;
    private javax.swing.JTextField EVCSenderField;
    private com.toedter.calendar.JDateChooser EVCTransactionDateChooser;
    private javax.swing.JPanel GPRSSubPanel;
    private javax.swing.JTextField IMSItextField;
    private javax.swing.JTextField KolarehCreditField;
    private javax.swing.JTextField LoyaltyStatusField;
    private javax.swing.JTextArea MSCTextArea;
    private javax.swing.JTextField MSISDNtextField;
    private javax.swing.JButton SMSCheckDelivButton;
    private javax.swing.JPanel SMSDeliveryPanel;
    private javax.swing.JProgressBar SMSDeliveryProgressBar;
    private javax.swing.JTextField SMSDestNumField;
    private com.toedter.calendar.JDateChooser SMSEndDateChooser;
    private javax.swing.JTextField SMSOrigNumField;
    private com.toedter.calendar.JDateChooser SMSStartDateChooser;
    private javax.swing.JPanel TOKPanel;
    private javax.swing.JLabel ToLabel;
    private javax.swing.JButton TokNumAdjButton;
    private javax.swing.JTextField TokNumField;
    private javax.swing.JPanel VASDeactPanel;
    private javax.swing.JList VASList;
    private javax.swing.JPanel VASPanel;
    private javax.swing.JRadioButton accntRadioButton;
    private javax.swing.JButton addCredButton;
    private javax.swing.JButton applyChngButton;
    private javax.swing.JButton applyChngButton1;
    private javax.swing.JTextField balTextField;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroupCredAdd;
    private javax.swing.ButtonGroup buttonGroupEVC;
    private javax.swing.JRadioButton callsRadioButton;
    private javax.swing.JButton clearDispButton;
    private javax.swing.JButton clearEVCButton;
    private javax.swing.JButton clearSMSDelivButton;
    private javax.swing.JButton clearSimButton;
    private javax.swing.JButton clrCrdAddButton;
    private javax.swing.JButton clrMSC;
    private javax.swing.JButton clrMSC1;
    private javax.swing.JList commandList;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JTextField cosTextField;
    private javax.swing.JTextField crAdAmntField;
    private javax.swing.JTextField crAdReceipientNumField;
    private javax.swing.JTextField credAddCmntTextField;
    private javax.swing.JPanel credAddPanel;
    private javax.swing.JRadioButton creditTransRadioButton;
    private javax.swing.JComboBox dataDispComboBox;
    private javax.swing.JRadioButton dealerToDealerRadioButton;
    private javax.swing.JRadioButton dealerToSubRadioButton;
    private javax.swing.JTextField dispSubNumField;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JRadioButton evoucherRadioButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JTextField firstCallTextField;
    private javax.swing.JRadioButton freeMonRadioButton;
    private javax.swing.JTextField freeMonTextField;
    private javax.swing.JTextField freeSMSTextField;
    private javax.swing.JButton funRingSubButton;
    private javax.swing.JButton gprsSubButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JComboBox langComboBox;
    private javax.swing.JRadioButton langRadioButton;
    private javax.swing.JTextField lastCallTextField;
    private javax.swing.JTextField minAmountField;
    private javax.swing.JLabel minAmtLabel;
    private javax.swing.JPanel mscPanel;
    private javax.swing.JLabel mscParLabel;
    private javax.swing.JTextField mscParTextField;
    private javax.swing.JLabel newSIMLabel;
    private javax.swing.JTextField newSIMTextField;
    private javax.swing.JButton normToEVCButton;
    private javax.swing.JLabel oldIMSILabel;
    private javax.swing.JTextField oldIMSItextField;
    private javax.swing.JRadioButton ordSubRadioButton;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JButton payBackDlrButton;
    private javax.swing.JButton payBackEVCButton;
    private javax.swing.JComboBox postpaidComboBox;
    private javax.swing.JRadioButton postpaidRadioButton;
    private javax.swing.JComboBox prodComboBox;
    private javax.swing.JList prodList;
    private javax.swing.JRadioButton realBalRadioButton;
    private javax.swing.JButton refChkButton;
    private javax.swing.JTextField refNumField1;
    private javax.swing.JTextField refNumField2;
    private javax.swing.JTextField refNumField3;
    private javax.swing.JButton removeServiceButton;
    private javax.swing.JButton replaceSimButton;
    private javax.swing.JComboBox requesterComboBox;
    private javax.swing.JButton retCrdtTransButton;
    private javax.swing.JComboBox roamComboBox;
    private javax.swing.JRadioButton roamRadioButton;
    private javax.swing.JButton searchEVCButton;
    private javax.swing.JPanel servicePanel;
    private javax.swing.JTextArea serviceTextArea;
    private javax.swing.JButton setTokButton;
    private javax.swing.JPanel simRepPanel;
    private javax.swing.JTextArea simRepTextArea;
    private javax.swing.JLabel songCodeLabel;
    private javax.swing.JTextField songCodeTextField;
    private javax.swing.JTextField subNumField1;
    private javax.swing.JTextField subNumField2;
    private javax.swing.JButton submitMSCcmd;
    private javax.swing.JButton tokSubChkButton;
    private javax.swing.JComboBox ussdComboBox;
    private javax.swing.JRadioButton ussdRadioButton;
    private javax.swing.JComboBox validComboBox;
    private javax.swing.JRadioButton validRadioButton;
    private javax.swing.JButton viewHistButton;
    // End of variables declaration//GEN-END:variables
    private static final String wrgNumMsg = "Wrong Number Entry:\nOnly 7 digits numbers allowed, starting with 7.";
    private static final String[] prodMod = {"Incoming", "Outgoing"};
    private String subNumSimRep = null, subNumTOK = null, userName;
    private INCommander INCmd;
    private AppLogger appLog;
    private HuaweiHLR mscCmd;
    private java.awt.Component mousePressComp;
    private short userRole;

    class MSCCommandSender extends SwingWorker<String, Void> {

        int command;
        String MSISDN, IMSI, pgwReply;

        MSCCommandSender() {
        }

        MSCCommandSender(int cmd, String msisdn, String imsi) {
            this.command = cmd;
            this.MSISDN = msisdn;
            this.IMSI = imsi;
        }

        @Override
        protected String doInBackground() throws Exception {

            switch (command) {
                case 0: //Display
                    if (IMSI != null) {
                        MSISDNtextField.setEditable(false);
                        IMSItextField.setEditable(false);
                        submitMSCcmd.setEnabled(false);
                        commandList.setEnabled(false);
                        dataDispComboBox.setEnabled(false);
                        clrMSC.setEnabled(false);
                        appLog.setMSISDN(IMSI);
                        appLog.setLogEventID(AppLogger.DispNum);
                        mscCmd.setServedIMSI(IMSI);
                        mscCmd.executeCommand(HuaweiHLR.dispImsiDat);
                        pgwReply = mscCmd.getCmdOutput();
                    } else {
                        MSISDNtextField.setEditable(false);
                        IMSItextField.setEditable(false);
                        submitMSCcmd.setEnabled(false);
                        commandList.setEnabled(false);
                        dataDispComboBox.setEnabled(false);
                        clrMSC.setEnabled(false);
                        appLog.setMSISDN(MSISDN);
                        appLog.setLogEventID(AppLogger.DispNum);
                        mscCmd.setServedMSISDN(MSISDN);
                        mscCmd.executeCommand(HuaweiHLR.dispMsisdnDat);
                        pgwReply = mscCmd.getCmdOutput();
                    }
                    break;
                case 1: //Display AC
                    MSISDNtextField.setEditable(false);
                    IMSItextField.setEditable(false);
                    submitMSCcmd.setEnabled(false);
                    commandList.setEnabled(false);
                    clrMSC.setEnabled(false);
                    appLog.setMSISDN(IMSI);
                    appLog.setLogEventID(AppLogger.DispAC);
                    mscCmd.setServedIMSI(IMSI);
                    mscCmd.executeCommand(HuaweiHLR.dispAC);
                    pgwReply = mscCmd.getCmdOutput();
                    break;
                case 2: //Create AC
                    String a4ki = "";
                    if (!mscParTextField.isEditable()) {
                        try {
                            a4ki = INCmd.getA4K(IMSI);
                        } catch (SQLException se) {
                            return "DB Error in Create AC.";
                        }
                        if (a4ki == null) {
                            mscParTextField.setEditable(true);
                            return "Enter Authentication key.";
                        } else {
                            mscParTextField.setText(a4ki);
                        }
                    } else {
                        a4ki = mscParTextField.getText();
                    }

                    if (a4ki.length() == 32) {
                        MSISDNtextField.setEditable(false);
                        IMSItextField.setEditable(false);
                        submitMSCcmd.setEnabled(false);
                        commandList.setEnabled(false);
                        clrMSC.setEnabled(false);
                        appLog.setMSISDN(IMSI);
                        appLog.setLogActParam(a4ki);
                        appLog.setLogEventID(AppLogger.CreateAC);
                        mscCmd.setServedIMSI(IMSI);
                        mscCmd.setA4KI(a4ki);
                        mscCmd.executeCommand(HuaweiHLR.crtAC);
                        pgwReply = mscCmd.getCmdOutput();
                        mscParTextField.setEditable(false);
                    } else {
                        mscParTextField.setEditable(true);
                        return new String("Enter 32 digit Authentication key.");
                    }
                    break;
                case 3: //Cancel AC
                    MSISDNtextField.setEditable(false);
                    IMSItextField.setEditable(false);
                    submitMSCcmd.setEnabled(false);
                    commandList.setEnabled(false);
                    clrMSC.setEnabled(false);
                    appLog.setMSISDN(IMSI);
                    appLog.setLogEventID(AppLogger.CancAC);
                    mscCmd.setServedIMSI(IMSI);
                    mscCmd.executeCommand(HuaweiHLR.canAC);
                    pgwReply = mscCmd.getCmdOutput();
                    break;
                case 4: //Create sub
                    MSISDNtextField.setEditable(false);
                    IMSItextField.setEditable(false);
                    submitMSCcmd.setEnabled(false);
                    commandList.setEnabled(false);
                    clrMSC.setEnabled(false);
                    appLog.setMSISDN(MSISDN);
                    appLog.setLogActParam(IMSI);
                    appLog.setLogEventID(AppLogger.CreateSub);
                    mscCmd.setServedIMSI(IMSI);
                    mscCmd.setServedMSISDN(MSISDN);
                    mscCmd.executeCommand(HuaweiHLR.crtMSUB);
                    pgwReply = mscCmd.getCmdOutput();
                    break;
                case 5: //Cancel Sub
                    if (IMSI != null) {
                        MSISDNtextField.setEditable(false);
                        IMSItextField.setEditable(false);
                        submitMSCcmd.setEnabled(false);
                        commandList.setEnabled(false);
                        clrMSC.setEnabled(false);
                        appLog.setMSISDN(IMSI);
                        appLog.setLogEventID(AppLogger.CancSub);
                        mscCmd.setServedIMSI(IMSI);
                        mscCmd.executeCommand(HuaweiHLR.canMSUB);
                        pgwReply = mscCmd.getCmdOutput();
                    } else {
                        MSISDNtextField.setEditable(false);
                        IMSItextField.setEditable(false);
                        submitMSCcmd.setEnabled(false);
                        commandList.setEnabled(false);
                        clrMSC.setEnabled(false);
                        appLog.setMSISDN(MSISDN);
                        appLog.setLogEventID(AppLogger.CancSub);
                        mscCmd.setServedMSISDN(MSISDN);
                        mscCmd.executeCommand(HuaweiHLR.canISDN);
                        pgwReply = mscCmd.getCmdOutput();
                    }
                    break;
                case 6: //Product Modify
                    int prodInd = prodList.getSelectedIndex();
                    if (prodInd == -1) {
                        return "Select action to apply.";
                    } else {
                        if (IMSI != null) {
                            mscCmd.setServedIMSI(IMSI);
                            appLog.setMSISDN(IMSI);
                            switch (prodInd) {
                                case 0: //Incoming
                                    switch (prodComboBox.getSelectedIndex()) {
                                        case 0: //Open
                                            MSISDNtextField.setEditable(false);
                                            IMSItextField.setEditable(false);
                                            submitMSCcmd.setEnabled(false);
                                            commandList.setEnabled(false);
                                            prodList.setEnabled(false);
                                            prodComboBox.setEnabled(false);
                                            clrMSC.setEnabled(false);
                                            appLog.setLogActParam("Open");
                                            appLog.setLogEventID(AppLogger.IncCalls);
                                            mscCmd.setBAICval("NOBIC");
                                            break;
                                        case 1: //Block
                                            MSISDNtextField.setEditable(false);
                                            IMSItextField.setEditable(false);
                                            submitMSCcmd.setEnabled(false);
                                            commandList.setEnabled(false);
                                            prodList.setEnabled(false);
                                            prodComboBox.setEnabled(false);
                                            clrMSC.setEnabled(false);
                                            appLog.setLogActParam("Block");
                                            appLog.setLogEventID(AppLogger.IncCalls);
                                            mscCmd.setBAICval("BAIC");
                                            break;
                                        default:
                                            break;
                                    }
                                    mscCmd.executeCommand(HuaweiHLR.modifyBARIC);
                                    pgwReply = mscCmd.getCmdOutput();
                                    break;
                                case 1: //Outgoing
                                    switch (prodComboBox.getSelectedIndex()) {
                                        case 0: //Open
                                            MSISDNtextField.setEditable(false);
                                            IMSItextField.setEditable(false);
                                            submitMSCcmd.setEnabled(false);
                                            commandList.setEnabled(false);
                                            prodList.setEnabled(false);
                                            prodComboBox.setEnabled(false);
                                            clrMSC.setEnabled(false);
                                            appLog.setLogActParam("Open");
                                            appLog.setLogEventID(AppLogger.OutCalls);
                                            mscCmd.setBAOCval("NOBOC");
                                            break;
                                        case 1: //Block
                                            MSISDNtextField.setEditable(false);
                                            IMSItextField.setEditable(false);
                                            submitMSCcmd.setEnabled(false);
                                            commandList.setEnabled(false);
                                            prodList.setEnabled(false);
                                            prodComboBox.setEnabled(false);
                                            clrMSC.setEnabled(false);
                                            appLog.setLogActParam("Block");
                                            appLog.setLogEventID(AppLogger.OutCalls);
                                            mscCmd.setBAOCval("BAOC");
                                            break;
                                        default:
                                            break;
                                    }
                                    mscCmd.executeCommand(HuaweiHLR.modifyBAROC);
                                    pgwReply = mscCmd.getCmdOutput();
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            mscCmd.setServedMSISDN(MSISDN);
                            appLog.setMSISDN(MSISDN);
                            switch (prodInd) {
                                case 0: //Incoming
                                    switch (prodComboBox.getSelectedIndex()) {
                                        case 0: //Open
                                            MSISDNtextField.setEditable(false);
                                            IMSItextField.setEditable(false);
                                            submitMSCcmd.setEnabled(false);
                                            commandList.setEnabled(false);
                                            prodList.setEnabled(false);
                                            prodComboBox.setEnabled(false);
                                            clrMSC.setEnabled(false);
                                            appLog.setLogActParam("Open");
                                            appLog.setLogEventID(AppLogger.IncCalls);
                                            mscCmd.setBAICval("NOBIC");
                                            break;
                                        case 1: //Block
                                            MSISDNtextField.setEditable(false);
                                            IMSItextField.setEditable(false);
                                            submitMSCcmd.setEnabled(false);
                                            commandList.setEnabled(false);
                                            prodList.setEnabled(false);
                                            prodComboBox.setEnabled(false);
                                            clrMSC.setEnabled(false);
                                            appLog.setLogActParam("Block");
                                            appLog.setLogEventID(AppLogger.IncCalls);
                                            mscCmd.setBAICval("BAIC");
                                            break;
                                        default:
                                            break;
                                    }
                                    mscCmd.executeCommand(HuaweiHLR.modifyBARICISDN);
                                    pgwReply = mscCmd.getCmdOutput();
                                    break;
                                case 1: //Outgoing
                                    switch (prodComboBox.getSelectedIndex()) {
                                        case 0: //Open
                                            MSISDNtextField.setEditable(false);
                                            IMSItextField.setEditable(false);
                                            submitMSCcmd.setEnabled(false);
                                            commandList.setEnabled(false);
                                            prodList.setEnabled(false);
                                            prodComboBox.setEnabled(false);
                                            clrMSC.setEnabled(false);
                                            appLog.setLogActParam("Open");
                                            appLog.setLogEventID(AppLogger.OutCalls);
                                            mscCmd.setBAOCval("NOBOC");
                                            break;
                                        case 1: //Block
                                            MSISDNtextField.setEditable(false);
                                            IMSItextField.setEditable(false);
                                            submitMSCcmd.setEnabled(false);
                                            commandList.setEnabled(false);
                                            prodList.setEnabled(false);
                                            prodComboBox.setEnabled(false);
                                            clrMSC.setEnabled(false);
                                            appLog.setLogActParam("Block");
                                            appLog.setLogEventID(AppLogger.OutCalls);
                                            mscCmd.setBAOCval("BAOC");
                                            break;
                                        default:
                                            break;
                                    }
                                    mscCmd.executeCommand(HuaweiHLR.modifyBAROCISDN);
                                    pgwReply = mscCmd.getCmdOutput();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    break;
                case 7: //Call Fwd
                    prodInd = prodList.getSelectedIndex();
                    if (prodInd == -1) {
                        return "Select Call Fwd Option";
                    } else {
                        String callFwdTo = CallFwdDestTextField.getText();
                        if (IMSI != null) {
                            if (prodComboBox.getSelectedIndex() == 0) //OPEN CALL FWD
                            {
                                if (callFwdTo.isEmpty()) {
                                    return "Enter Call Fwd Destination Number";
                                } else {
                                    MSISDNtextField.setEditable(false);
                                    IMSItextField.setEditable(false);
                                    submitMSCcmd.setEnabled(false);
                                    commandList.setEnabled(false);
                                    prodList.setEnabled(false);
                                    prodComboBox.setEnabled(false);
                                    CallFwdDestTextField.setEnabled(false);
                                    clrMSC.setEnabled(false);
                                    mscCmd.setServedIMSI(IMSI);
                                    mscCmd.setFTN(callFwdTo);
                                    appLog.setMSISDN(IMSI);
                                    switch (prodInd) {
                                        case 0: //CFU
                                            appLog.setLogActParam("CallFwd-CFU: Open to " + callFwdTo);
                                            appLog.setLogEventID(AppLogger.CallFwd);
                                            mscCmd.executeCommand(HuaweiHLR.actCallFwdU);
                                            pgwReply = mscCmd.getCmdOutput();
                                            break;
                                        case 1: //CFBusy
                                            appLog.setLogActParam("CallFwd-CFBUSY: Open to " + callFwdTo);
                                            appLog.setLogEventID(AppLogger.CallFwd);
                                            mscCmd.executeCommand(HuaweiHLR.actCallFwdB);
                                            pgwReply = mscCmd.getCmdOutput();
                                            break;
                                        case 2: //CFNReply
                                            appLog.setLogActParam("CallFwd-CFNREPLY: Open to " + callFwdTo);
                                            appLog.setLogEventID(AppLogger.CallFwd);
                                            mscCmd.executeCommand(HuaweiHLR.actCallFwdNRY);
                                            pgwReply = mscCmd.getCmdOutput();
                                            break;
                                        case 3: //CFNReach
                                            appLog.setLogActParam("CallFwd-CFNREACH: Open to " + callFwdTo);
                                            appLog.setLogEventID(AppLogger.CallFwd);
                                            mscCmd.executeCommand(HuaweiHLR.actCallFwdNRC);
                                            pgwReply = mscCmd.getCmdOutput();
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            } else { //CANCEL CALL FWD
                                MSISDNtextField.setEditable(false);
                                IMSItextField.setEditable(false);
                                submitMSCcmd.setEnabled(false);
                                commandList.setEnabled(false);
                                prodList.setEnabled(false);
                                prodComboBox.setEnabled(false);
                                CallFwdDestTextField.setEnabled(false);
                                clrMSC.setEnabled(false);
                                mscCmd.setServedIMSI(IMSI);
                                appLog.setMSISDN(IMSI);
                                switch (prodInd) {
                                    case 0: //CFU
                                        appLog.setLogActParam("CallFwd-CFU: Cancel");
                                        appLog.setLogEventID(AppLogger.CallFwd);
                                        mscCmd.executeCommand(HuaweiHLR.deactCallFwdU);
                                        pgwReply = mscCmd.getCmdOutput();
                                        break;
                                    case 1: //CFBusy
                                        appLog.setLogActParam("CallFwd-CFBUSY: Cancel");
                                        appLog.setLogEventID(AppLogger.CallFwd);
                                        mscCmd.executeCommand(HuaweiHLR.deactCallFwdB);
                                        pgwReply = mscCmd.getCmdOutput();
                                        break;
                                    case 2: //CFNReply
                                        appLog.setLogActParam("CallFwd-CFNREPLY: Cancel");
                                        appLog.setLogEventID(AppLogger.CallFwd);
                                        mscCmd.executeCommand(HuaweiHLR.deactCallFwdNRY);
                                        pgwReply = mscCmd.getCmdOutput();
                                        break;
                                    case 3: //CFNReach
                                        appLog.setLogActParam("CallFwd-CFNREACH: Cancel");
                                        appLog.setLogEventID(AppLogger.CallFwd);
                                        mscCmd.executeCommand(HuaweiHLR.deactCallFwdNRC);
                                        pgwReply = mscCmd.getCmdOutput();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else {
                            if (prodComboBox.getSelectedIndex() == 0) //OPEN CALL FWD
                            {
                                if (callFwdTo.isEmpty()) {
                                    return "Enter Call Fwd Destination Number";
                                } else {
                                    MSISDNtextField.setEditable(false);
                                    IMSItextField.setEditable(false);
                                    submitMSCcmd.setEnabled(false);
                                    commandList.setEnabled(false);
                                    prodList.setEnabled(false);
                                    prodComboBox.setEnabled(false);
                                    CallFwdDestTextField.setEnabled(false);
                                    clrMSC.setEnabled(false);
                                    mscCmd.setServedMSISDN(MSISDN);
                                    mscCmd.setFTN(callFwdTo);
                                    appLog.setMSISDN(MSISDN);
                                    switch (prodInd) {
                                        case 0: //CFU
                                            appLog.setLogActParam("CallFwd-CFU: Open to " + callFwdTo);
                                            appLog.setLogEventID(AppLogger.CallFwd);
                                            mscCmd.executeCommand(HuaweiHLR.actCallFwdUISDN);
                                            pgwReply = mscCmd.getCmdOutput();
                                            break;
                                        case 1: //CFBusy
                                            appLog.setLogActParam("CallFwd-CFBUSY: Open to " + callFwdTo);
                                            appLog.setLogEventID(AppLogger.CallFwd);
                                            mscCmd.executeCommand(HuaweiHLR.actCallFwdBISDN);
                                            pgwReply = mscCmd.getCmdOutput();
                                            break;
                                        case 2: //CFNReply
                                            appLog.setLogActParam("CallFwd-CFNREPLY: Open to " + callFwdTo);
                                            appLog.setLogEventID(AppLogger.CallFwd);
                                            mscCmd.executeCommand(HuaweiHLR.actCallFwdNRYISDN);
                                            pgwReply = mscCmd.getCmdOutput();
                                            break;
                                        case 3: //CFNReach
                                            appLog.setLogActParam("CallFwd-CFNREACH: Open to " + callFwdTo);
                                            appLog.setLogEventID(AppLogger.CallFwd);
                                            mscCmd.executeCommand(HuaweiHLR.actCallFwdNRCISDN);
                                            pgwReply = mscCmd.getCmdOutput();
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            } else { //CANCEL CALL FWD
                                MSISDNtextField.setEditable(false);
                                IMSItextField.setEditable(false);
                                submitMSCcmd.setEnabled(false);
                                commandList.setEnabled(false);
                                prodList.setEnabled(false);
                                prodComboBox.setEnabled(false);
                                CallFwdDestTextField.setEnabled(false);
                                clrMSC.setEnabled(false);
                                mscCmd.setServedMSISDN(MSISDN);
                                appLog.setMSISDN(MSISDN);
                                switch (prodInd) {
                                    case 0: //CFU
                                        appLog.setLogActParam("CallFwd-CFU: Cancel");
                                        appLog.setLogEventID(AppLogger.CallFwd);
                                        mscCmd.executeCommand(HuaweiHLR.deactCallFwdUISDN);
                                        pgwReply = mscCmd.getCmdOutput();
                                        break;
                                    case 1: //CFBusy
                                        appLog.setLogActParam("CallFwd-CFBUSY: Cancel");
                                        appLog.setLogEventID(AppLogger.CallFwd);
                                        mscCmd.executeCommand(HuaweiHLR.deactCallFwdBISDN);
                                        pgwReply = mscCmd.getCmdOutput();
                                        break;
                                    case 2: //CFNReply
                                        appLog.setLogActParam("CallFwd-CFNREPLY: Cancel");
                                        appLog.setLogEventID(AppLogger.CallFwd);
                                        mscCmd.executeCommand(HuaweiHLR.deactCallFwdNRYISDN);
                                        pgwReply = mscCmd.getCmdOutput();
                                        break;
                                    case 3: //CFNReach
                                        appLog.setLogActParam("CallFwd-CFNREACH: Cancel");
                                        appLog.setLogEventID(AppLogger.CallFwd);
                                        mscCmd.executeCommand(HuaweiHLR.deactCallFwdNRCISDN);
                                        pgwReply = mscCmd.getCmdOutput();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                    break;
                default:
                    return "Enter action to do.";
            }

            return pgwReply;
        }

        @Override
        protected void done() {

            String cmdOutput = null;
            try {
                cmdOutput = get();
            } catch (Exception e) {
                System.out.println("Exception at done() of MSC-Commander: " + e.getMessage());
            }
            MSCTextArea.setText(cmdOutput);

            submitMSCcmd.setEnabled(true);
            commandList.setEnabled(true);
            prodList.setEnabled(true);
            dataDispComboBox.setEnabled(true);
            prodComboBox.setEnabled(true);
            CallFwdDestTextField.setEnabled(true);
            clrMSC.setEnabled(true);
            IMSItextField.setEditable(true);
            hideMSCFields();

            switch (commandList.getSelectedIndex()) {
                case 0:  //Display
                case 4:  //Create Sub
                case 5:  //Cancel Sub
                    MSISDNtextField.setEditable(true);
                    break;
                case 1:  //Display AC
                case 3:  //Create AC
                    MSISDNtextField.setEditable(false);
                    break;
                case 2: //Create AC
                    mscParLabel.setText("A4KI");
                    mscParLabel.setVisible(true);
                    mscParTextField.setVisible(true);
                    mscParTextField.setEditable(false);
                    MSISDNtextField.setEditable(false);
                    break;
                case 6: //Product Modify
                    prodList.setListData(prodMod);
                    prodList.setVisible(true);
                    prodList.clearSelection();
                    prodComboBox.setVisible(true);
                    MSISDNtextField.setEditable(true);
                    break;
                case 7: //Call Fwd
                    //prodList.setListData(HuaweiHLR.callFwdOpts);
                    prodList.setVisible(true);
                    prodList.clearSelection();
                    MSISDNtextField.setEditable(true);
                    prodComboBox.setVisible(true);
                    ToLabel.setVisible(true);
                    CallFwdDestTextField.setVisible(true);
                default:
                    break;
            }
            mscCmd.setCmdOutput("");
        }
    }

    class MSCSIMReplace extends SwingWorker<Void, Void> {

        String MSISDN, newMSIN;
        boolean isAttached = true;

        MSCSIMReplace(String msisdn, String msin) {

            this.MSISDN = msisdn;
            this.newMSIN = msin;
        }

        @Override
        protected Void doInBackground() throws Exception {

            mscCmd.setNewIMSI(this.newMSIN);
            mscCmd.setServedMSISDN(MSISDN);

            simRepTextArea.setText("Acquiring current SIM attached to " + MSISDN + ": ");
            //Execute Display Command to acquire old SIM
            mscCmd.executeCommand(HuaweiHLR.dispMsisdnDat);
            StringTokenizer strToken = new StringTokenizer(mscCmd.getCmdOutput(), "\n");
            String s = "";

            while (strToken.hasMoreTokens()) {
                s = strToken.nextToken();
                if (s.contains("IMSI".subSequence(0, 4))) {
                    break;
                }
            }

            if (!strToken.hasMoreTokens()) {
                simRepTextArea.append("None.\n" + MSISDN + " is not attached to a SIM!\n");
                isAttached = false;
                return null;
            }

            String oMSIN = s.substring(s.indexOf("IMSI = 60702") + 12);
            oldIMSItextField.setText(oMSIN);
            simRepTextArea.append(oMSIN + "\nCreating SIM in KI...");

            String strA4ki = INCmd.getA4K(newMSIN);

            if (strA4ki == null) {
                strA4ki = JOptionPane.showInputDialog(ControlRoomUI.this, "KI Not in Database", "KI for " + newMSIN + " is missing. Cannot Continue, choose another SIM.",
                        JOptionPane.QUESTION_MESSAGE);
                simRepTextArea.append("KI Not in Database. Contact Technical Department.");
                isAttached = false;
                return null;
            }

            mscCmd.setServedIMSI(newMSIN);
            mscCmd.setA4KI(strA4ki);
            mscCmd.executeCommand(HuaweiHLR.crtAC);

            appLog.setMSISDN(newMSIN);
            appLog.setLogActParam(strA4ki);
            appLog.setLogEventID(AppLogger.CreateAC);

            simRepTextArea.append("Done!\nReplacing current SIM with " + newMSIN + "...");

            mscCmd.executeCommand(HuaweiHLR.simRepISDN);
            strToken = new StringTokenizer(mscCmd.getCmdOutput(), "\n");
            s = "";
            while (strToken.hasMoreTokens()) {
                s = strToken.nextToken();
                if (s.contains("ERR".subSequence(0, 2))) {
                    break;
                }
            }

            if (strToken.hasMoreTokens()) {
                simRepTextArea.append("Operation Unsuccessful!\nCause: \n" + s);
                isAttached = false;
                return null;
            }

            appLog.setMSISDN(MSISDN);
            appLog.setLogActParam(newMSIN);
            appLog.setSIMRepResult(INCmd.simReplace(MSISDN, oMSIN, newMSIN));
            appLog.setLogEventID(AppLogger.SIMReplace);
            simRepTextArea.append("Done!\nCancelling old SIM from AC...");

            mscCmd.setServedIMSI(oMSIN);
            mscCmd.executeCommand(HuaweiHLR.canAC);

            appLog.setMSISDN(oMSIN);
            appLog.setLogEventID(AppLogger.CancAC);
            subNumSimRep = null;
            replaceSimButton.setEnabled(false);
            refChkButton.setEnabled(true);
            newSIMTextField.setEditable(false);
            subNumField1.setEditable(true);

            /*
             mscCmd.executeCommand(HuaweiHLR.dispMsisdnDat);
             do {
             try {
             Thread.sleep((long) 100);
             } catch (InterruptedException ie) {
             System.out.println("Read output interrupted.. " + ie.getMessage());
             }
             } while (mscCmd.readOutput() == null);
            
             //Fetching A4KI from Database
             String strA4ki = INCmd.getA4K(newMSIN);
             if (strA4ki == null) {
             strA4ki = JOptionPane.showInputDialog(ControlRoomUI.this, "A4KI Not in Database", "A4KI for " + newMSIN,
             JOptionPane.QUESTION_MESSAGE);
             }
             //If MSIN not in inventory, prompt user to enter A4KI
             if (strA4ki == null) {
             simRepTextArea.setText("User Action Cancelled.");
             return null;
             }
             //Create New MSIN in the AC
             mscCmd.resetOutput();
            
             mscCmd.setIMSI(newMSIN);
             mscCmd.setAuthKey(strA4ki);
             mscCmd.executeCommand(HuaweiHLR.crtAC);
            
             appLog.setMSISDN(newMSIN);
             appLog.setLogActParam(strA4ki);
             appLog.setLogEventID(AppLogger.CreateAC);
             do {
             try {
             Thread.sleep((long) 100);
             } catch (InterruptedException ie) {
             System.out.println("Read output interrupted.. " + ie.getMessage());
             }
             } while (mscCmd.readOutput() == null);
            
             simRepTextArea.append("Done!\nReplacing " + oMSIN + " with " + newMSIN + "...");
             mscCmd.resetOutput();
             mscCmd.setIMSI(oMSIN);
             mscCmd.setNewIMSI(newMSIN);
             mscCmd.executeCommand(HuaweiHLR.simRepCmd);
             do {
             try {
             Thread.sleep((long) 100);
             } catch (InterruptedException ie) {
             System.out.println("Read output interrupted.. " + ie.getMessage());
             }
             } while (mscCmd.readOutput() == null);
            
             //String simRepRes = ControlRoomUI.this.userRole<(short)3?INCmd.simReplace(subNumSimRep,oMSIN,newMSIN):INCmd.simReplace(subNumSimRep,oMSIN,newMSIN,rowIDSubInfo);
             String simRepRes = INCmd.simReplace(subNumSimRep, oMSIN, newMSIN);
             appLog.setMSISDN(subNumSimRep);
             appLog.setLogActParam(newMSIN);
             appLog.setSIMRepResult(simRepRes);
             appLog.setLogEventID(AppLogger.SIMReplace);
            
             //Canceling old MSIN from AC
             mscCmd.resetOutput();
             simRepTextArea.append("Done!\nCanceling " + oMSIN + " from AC...");
             mscCmd.executeCommand(HuaweiHLR.canAC);
             do {
             try {
             Thread.sleep((long) 100);
             } catch (InterruptedException ie) {
             System.out.println("Read output interrupted.. " + ie.getMessage());
             }
             } while (mscCmd.readOutput() == null);
            
             appLog.setMSISDN(oMSIN);
             appLog.setLogEventID(AppLogger.CancAC);
             subNumSimRep = null;
             replaceSimButton.setEnabled(false);
             refChkButton.setEnabled(userRole != (short) 3);
             newSIMTextField.setEditable(false);
             subNumField1.setEditable(true);
             * 
             */
            return null;
        }

        @Override
        protected void done() {

            try {
                get();
                if (isAttached) {
                    simRepTextArea.append("Done!\nSubsriber Balance Updated.\nProcess Complete.");
                    refChkButton.setEnabled(true);
                    clearSimButton.setEnabled(true);
                } else {
                    simRepTextArea.append("SIM not replaced. MSISDN should be created on the new SIM.");
                    clearSimButton.setEnabled(true);
                    refChkButton.setEnabled(true);
                }
            } catch (Exception e) {
                System.out.println("Exception at done of SIM-Replace Worker " + e.getMessage());
            }
        }
    }

    class MSCRoaming extends SwingWorker<Void, Void> {

        String MSISDN;
        boolean barRoam;
        boolean cmdSuccess;

        MSCRoaming(String msisdn, boolean openRoam) {
            this.MSISDN = msisdn;
            this.barRoam = openRoam;
        }

        @Override
        protected Void doInBackground()
                throws Exception {

            mscCmd.setServedMSISDN(this.MSISDN);
            mscCmd.setBARRAOM(this.barRoam == true ? "NOBAR" : "BROHPLMN");
            mscCmd.executeCommand(HuaweiHLR.modifyBARROAMISDN);
            System.out.println(mscCmd.getCmdOutput());
            StringTokenizer strToken = new StringTokenizer(mscCmd.getCmdOutput(), "\n");
            String s;
            while (strToken.hasMoreTokens()) {
                s = strToken.nextToken();
                
                if (s.contains("ERR".subSequence(0, 2))) {
                    this.cmdSuccess = false;
                    return null;
                }
            }
            //this.newCOS = this.barRoam == true ? INCommander.COS_ROAM_EN : INCommander.COS_AFRICELL_DEF;
            this.cmdSuccess = true;
            return null;
        }

        @Override
        protected void done() {
            if (this.barRoam)
                serviceTextArea.setText("Activating roaming facility of "+this.MSISDN+" on the HLR...");
            else
                serviceTextArea.setText("Deactivating roaming facility of "+this.MSISDN+" on the HLR...");
            try {
                get();
                if (cmdSuccess) {
                    serviceTextArea.append(new StringBuilder().append("done!\nRoaming facility has been ").append(this.barRoam == true ? "activated" : "deactivated").toString());
                    //cosNum = this.barRoam == true ? INCommander.COS_ROAM_EN : INCommander.COS_AFRICELL_DEF;
                    appLog.setLogActParam(this.barRoam == true ? "Open" : "Block");
                    appLog.setLogEventID((short) 18);
                    appLog.setLogActParam(this.barRoam == true ? "Enable" : "Disable");
                    appLog.setLogEventID((short) 5);
                    System.out.println("Roaming operation complete");
                } else {
                    serviceTextArea.append("Operation Unsuccessful due to fail in HLR command!");
                }
            } catch (Exception e) {
                System.out.println(new StringBuilder().append("Exception at done of Roaming Modify Worker ").append(e.getMessage()).toString());
            }
        }
    }

    class ListSelectionHandler
            implements ListSelectionListener {

        ArrayList<Integer> selectedRowsPos;

        ListSelectionHandler() {
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (!lsm.isSelectionEmpty()) {
                this.selectedRowsPos = new ArrayList();
                int minIdx = lsm.getMinSelectionIndex();
                int maxIdx = lsm.getMaxSelectionIndex();

                for (int j = minIdx; j <= maxIdx; j++) {
                    if (lsm.isSelectedIndex(j)) {
                        this.selectedRowsPos.add(Integer.valueOf(j));
                    }
                }
            }
        }

        public ArrayList<Integer> getSelectedRowsPos() {
            return this.selectedRowsPos;
        }

        public void setSelectedRowsPos(ArrayList<Integer> selectedRowsPos) {
            this.selectedRowsPos = selectedRowsPos;
        }
    }

    class SMSDeliveryWorker extends SwingWorker<ResultSetTableModel, Integer> {

        String Anumber, BNumber;
        java.util.Date searchFromDate, searchToDate;

        SMSDeliveryWorker(String SMSOrigNum, String SMSDestNum, java.util.Date fromDate, java.util.Date toDate) {

            this.Anumber = SMSOrigNum;
            this.BNumber = SMSDestNum;
            this.searchFromDate = fromDate;
            this.searchToDate = toDate;
        }

        @Override
        protected ResultSetTableModel doInBackground() throws Exception {

            SMSDeliveryProgressBar.setIndeterminate(true);
            ResultSetTableModel tblModel = INCmd.getSMSDeliveryTableModel(Anumber, BNumber, searchFromDate, searchToDate);
            /*
             int progress = 0;
            
             while (progress < 100) {
             Thread.sleep((long)((1/120)*1000)); //Estimating query takes 120 seconds to fetch the query results
             progress++;
             this.setProgress(progress);
             }
             * 
             */
            return tblModel;
        }

        @Override
        protected void done() {

            try {
                appLog.setMSISDN(Anumber);
                appLog.setLogActParam(BNumber);
                appLog.setLogEventID(AppLogger.chkSMSDelivery);

                SMSDeliveryProgressBar.setIndeterminate(false);
                SMSDeliveryProgressBar.setValue(100);
                java.awt.Toolkit.getDefaultToolkit().beep();

                jTable2.setModel(get());
                SMSOrigNumField.setEnabled(true);
                SMSDestNumField.setEnabled(true);
                SMSCheckDelivButton.setEnabled(true);
                clearSMSDelivButton.setEnabled(true);
                SMSStartDateChooser.setEnabled(true);
                SMSEndDateChooser.setEnabled(true);

            } catch (Exception ignore) {
                System.out.println("Exception at done of SMS Delivery Worker " + ignore.getMessage());
            }
        }
    }
}
