/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainlibrary;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import org.apache.commons.validator.routines.IntegerValidator;
import javax.xml.bind.ValidationException;
import static mainlibrary.AppConstants.*;

/**
 * IssueBookForm class for issuing books.
 */
public class IssueBookForm extends javax.swing.JFrame {

    public IssueBookForm() {
        initComponents();
        setDefaultDates();
    }

    private void setDefaultDates() {
        Calendar cal = Calendar.getInstance();
        IYear.setText(String.valueOf(cal.get(Calendar.YEAR)));
        IMonth.setText(String.valueOf(cal.get(Calendar.MONTH) + 1));
        IDate.setText(String.valueOf(cal.get(Calendar.DATE)));
        cal.add(Calendar.DAY_OF_YEAR, 15);
        RYear.setText(String.valueOf(cal.get(Calendar.YEAR)));
        RMonth.setText(String.valueOf(cal.get(Calendar.MONTH) + 1));
        RDate.setText(String.valueOf(cal.get(Calendar.DATE)));
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        UserID = new javax.swing.JTextField();
        BookID = new javax.swing.JTextField();
        IMonth = new javax.swing.JTextField();
        RMonth = new javax.swing.JTextField();
        IYear = new javax.swing.JTextField();
        IDate = new javax.swing.JTextField();
        RYear = new javax.swing.JTextField();
        RDate = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(121, 22, 127));
        setForeground(new java.awt.Color(30, 51, 252));

        jLabel1.setFont(new java.awt.Font(AppConstants.UBUNTU, 0, 20)); // NOI18N
        jLabel1.setText("Book ID");

        jLabel2.setFont(new java.awt.Font(AppConstants.UBUNTU, 0, 20)); // NOI18N
        jLabel2.setText("User ID");

        jLabel3.setFont(new java.awt.Font(AppConstants.UBUNTU, 0, 20)); // NOI18N
        jLabel3.setText("Issue Date");

        jLabel4.setFont(new java.awt.Font(AppConstants.UBUNTU, 0, 20)); // NOI18N
        jLabel4.setText("Return Date");

        jButton1.setFont(new java.awt.Font(AppConstants.UBUNTU, 0, 20)); // NOI18N
        jButton1.setText("Issue");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font(AppConstants.UBUNTU, 0, 18)); // NOI18N
        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        UserID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserIDActionPerformed(evt);
            }
        });

        BookID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookIDActionPerformed(evt);
            }
        });

        IMonth.setEditable(false);
        IMonth.setBackground(new java.awt.Color(193, 193, 193));
        IMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IMonthActionPerformed(evt);
            }
        });

        IYear.setEditable(false);
        IYear.setBackground(new java.awt.Color(193, 193, 193));

        IDate.setEditable(false);
        IDate.setBackground(new java.awt.Color(193, 193, 193));
        IDate.setToolTipText("");
        IDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDateActionPerformed(evt);
            }
        });

        RYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RYearActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font(AppConstants.UBUNTU, 1, 22)); // NOI18N
        jLabel5.setText("-");

        jLabel6.setFont(new java.awt.Font(AppConstants.UBUNTU, 1, 22)); // NOI18N
        jLabel6.setText("-");

        jLabel7.setFont(new java.awt.Font(AppConstants.UBUNTU, 1, 22)); // NOI18N
        jLabel7.setText("-");

        jLabel8.setFont(new java.awt.Font(AppConstants.UBUNTU, 1, 22)); // NOI18N
        jLabel8.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(124, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(BookID, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(RDate,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        59,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(jLabel7,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        16,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(RMonth,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        59,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(IDate,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        59,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(jLabel5,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        16,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(IMonth,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        59,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel6,
                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                16,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel8,
                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                16,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(4, 4, 4)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(RYear,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                59,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(IYear,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                59,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(UserID, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                335, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(79, 79, 79))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButton2))
                                                .addGap(290, 290, 290)))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(121, 121, 121)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(BookID, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(UserID, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(IMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(IYear, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(IDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(RMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(RDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel8)
                                        .addComponent(RYear, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                .addGap(32, 32, 32)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71,
                                        Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addGap(37, 37, 37)));

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String bookIdInput = BookID.getText();
            String userIdInput = UserID.getText();
            String issueDateStr = IYear.getText() + "-" + IMonth.getText() + "-" + IDate.getText();
            String returnDateStr = RYear.getText() + "-" + RMonth.getText() + "-" + RDate.getText();

            if (!validateAndShowError(bookIdInput, userIdInput, issueDateStr, returnDateStr)) return;

            int bookId = Integer.parseInt(bookIdInput);
            int userId = Integer.parseInt(userIdInput);
            java.sql.Date issueDate = java.sql.Date.valueOf(issueDateStr);
            java.sql.Date returnDate = java.sql.Date.valueOf(returnDateStr);

            if (!validateBookAndUser(bookId, userId)) return;
            if (!canUserIssue(userId)) return;

            if (issueBook(bookId, userId, issueDate, returnDate)) {
                JOptionPane.showMessageDialog(this, "The book has been issued successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                BookID.setText("");
                UserID.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Unable to issue the book.", ERROR_MSG, JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Book ID and User ID.", ERROR_MSG, JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateAndShowError(String bookIdInput, String userIdInput, String issueDateStr, String returnDateStr) {
        try {
            validateInput(bookIdInput);
            validateInput(userIdInput);
            validateDate(issueDateStr);
            validateDate(returnDateStr);
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), VALIDATION_ERROR, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        IntegerValidator validator = IntegerValidator.getInstance();
        if (!validator.isValid(bookIdInput)) {
            JOptionPane.showMessageDialog(this, "Invalid Book ID format.", VALIDATION_ERROR, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validator.isValid(userIdInput)) {
            JOptionPane.showMessageDialog(this, "Invalid User ID format.", VALIDATION_ERROR, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateBookAndUser(int bookId, int userId) {
        Optional<Boolean> isBookValid = TransBookDao.bookValidate(bookId);
        Optional<Boolean> isUserValid = TransBookDao.userValidate(userId);
        if (!isBookValid.orElse(false) || !isUserValid.orElse(false)) {
            JOptionPane.showMessageDialog(this, "Invalid Book ID or User ID.", VALIDATION_ERROR, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean canUserIssue(int userId) {
        Optional<Integer> canIssue = TransBookDao.check(userId);
        if (canIssue.isPresent() && canIssue.get() == 0) {
            JOptionPane.showMessageDialog(this, "User has already issued the maximum number of books.", ERROR_MSG, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean issueBook(int bookId, int userId, java.sql.Date issueDate, java.sql.Date returnDate) {
        Optional<Integer> issueResult = TransBookDao.issueBook(bookId, userId, issueDate, returnDate);
        return issueResult.isPresent() && issueResult.get() > 0;
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        // Replace ThisLogined with the correct reference or remove it
        // Example: new MainLibrary().setVisible(true);
    }

    private void UserIDActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void IDateActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void IMonthActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void RYearActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void BookIDActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * Validate input for SQL injection and emptiness.
     * @see <a href="https://cwe.mitre.org/data/definitions/20.html">CWE-20: Improper Input Validation</a>
     */
    public static void validateInput(String input) throws ValidationException {
        if (input == null || input.trim().isEmpty() || input.contains(";")) {
            throw new ValidationException("Invalid characters in input");
        }
    }

    /**
     * Validate date string.
     * @see <a href="https://cwe.mitre.org/data/definitions/20.html">CWE-20: Improper Input Validation</a>
     */
    public static void validateDate(String date) throws ValidationException {
        try {
            java.sql.Date.valueOf(date);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid date format");
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IssueBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IssueBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IssueBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IssueBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IssueBookForm().setVisible(true);
            }
        });
    }

    private javax.swing.JTextField BookID;
    private javax.swing.JTextField IDate;
    private javax.swing.JTextField IMonth;
    private javax.swing.JTextField IYear;
    private javax.swing.JTextField RDate;
    private javax.swing.JTextField RMonth;
    private javax.swing.JTextField RYear;
    private javax.swing.JTextField UserID;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
}
