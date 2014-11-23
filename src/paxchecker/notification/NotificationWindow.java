/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paxchecker.notification;

import java.util.concurrent.CountDownLatch;
import paxchecker.browser.Browser;

/**
 *
 * @author Sunny
 */
public class NotificationWindow extends javax.swing.JFrame {

  private final Notification myNote;
  private final CountDownLatch countDown;

  /**
   * Creates new form Notification
   *
   * @param n The Notification information object
   * @param cdl The CountDownLatch to count down when window is closed
   */
  public NotificationWindow(Notification n, CountDownLatch cdl) {
    myNote = n;
    countDown = cdl;
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        initComponents();
        customComponents();
        setVisible(true);
      }
    });
  }

  private void customComponents() {
    JLTitle.setText(myNote.getTitle());
    JBClose.setText(myNote.getButtonText());
    JTAInformation.setText(myNote.getInfo());
    JBMoreInfo.setVisible(false);
  }

  @Override
  public void dispose() {
    if (countDown != null) {
      countDown.countDown();
    }
    super.dispose();
  }

  public void setMoreInfoButtonLink(final String link) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        //JBMoreInfo.setVisible(true);
        JBMoreInfo.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            Browser.openLinkInBrowser(link);
          }
        });
      }
    });
  }

  public void setCloseButtonText(final String text) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JBClose.setText(text);
      }
    });
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    JLTitle = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    JTAInformation = new javax.swing.JTextArea();
    JBClose = new javax.swing.JButton();
    JBMoreInfo = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Notification");
    setResizable(false);

    JLTitle.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
    JLTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    JLTitle.setText("Notification Title");

    JTAInformation.setEditable(false);
    JTAInformation.setColumns(20);
    JTAInformation.setLineWrap(true);
    JTAInformation.setRows(5);
    JTAInformation.setWrapStyleWord(true);
    jScrollPane1.setViewportView(JTAInformation);

    JBClose.setText("Close");
    JBClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        JBCloseActionPerformed(evt);
      }
    });

    JBMoreInfo.setText("More Info");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane1)
          .addGroup(layout.createSequentialGroup()
            .addComponent(JLTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(JBClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(JBMoreInfo)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(JLTitle)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(JBClose)
          .addComponent(JBMoreInfo))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void JBCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBCloseActionPerformed
    // TODO add your handling code here:
    dispose();
  }//GEN-LAST:event_JBCloseActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton JBClose;
  private javax.swing.JButton JBMoreInfo;
  private javax.swing.JLabel JLTitle;
  private javax.swing.JTextArea JTAInformation;
  private javax.swing.JScrollPane jScrollPane1;
  // End of variables declaration//GEN-END:variables
}
