package com.github.sunnybat.paxchecker.update;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author SunnyBat
 */
public class Update extends javax.swing.JFrame {

  private PatchNotes patchNotes;
  private final CountDownLatch countdown = new CountDownLatch(1);
  private volatile boolean updateProgram;

  /**
   * Creates a new Update form. Note that the CountDownLatch is only ticked down by one.
   *
   * @param cdl The CountDownLatch object to tick down.
   */
  public Update() {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        initComponents();
        customComponents();
      }
    });
  }

  private void customComponents() {
    //JLStatus.setVisible(false);
    JPBProgressBar.setVisible(false);
    pack();
    setLocationRelativeTo(null);
    setStatusLabelText("Update Size: " + ((double) ((int) ((double) UpdateHandler.getUpdateSize() * 100 / 1024 / 1024)) / 100) + "MB");
    setYesButtonText(UpdateHandler.getUpdateLevel());
  }

  public void showWindow() {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        setVisible(true);
        setStatusLabelText("Update Size: " + ((double) ((int) ((double) UpdateHandler.getUpdateSize() * 100 / 1024 / 1024)) / 100) + "MB");
      }
    });
  }

  public void await() throws InterruptedException {
    countdown.await();
  }

  public boolean shouldUpdateProgram() {
    return updateProgram;
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    JBYes = new javax.swing.JButton();
    JBNo = new javax.swing.JButton();
    JLStatus = new javax.swing.JLabel();
    JPBProgressBar = new javax.swing.JProgressBar();
    JBPatchNotes = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("PAX Checker Update");
    setAlwaysOnTop(true);
    setResizable(false);

    jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setText("PAXChecker Update Found!");

    jLabel2.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel2.setText("Would you like to update to the most recent version?");

    JBYes.setText("Yes! (Recommended)");
    JBYes.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        JBYesActionPerformed(evt);
      }
    });

    JBNo.setText("No!");
    JBNo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        JBNoActionPerformed(evt);
      }
    });

    JLStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    JLStatus.setText("Downloading Update:");

    JPBProgressBar.setToolTipText("Please wait, downloading the latest version...");

    JBPatchNotes.setText("View Patch Notes");
    JBPatchNotes.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        JBPatchNotesActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(JBPatchNotes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(JLStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
          .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(JBYes, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(JBNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addComponent(JPBProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabel1)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jLabel2)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(JBYes)
          .addComponent(JBNo))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(JBPatchNotes)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(JLStatus)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(JPBProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void JBYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBYesActionPerformed
    // TODO add your handling code here:
    //UpdateHandler.startUpdatingProgram();
    updateProgram = true;
    updateInit();
    JBYes.setVisible(false);
    JBNo.setVisible(false);
    JBPatchNotes.setVisible(false);
    countdown.countDown();
    //setVisible(false);
  }//GEN-LAST:event_JBYesActionPerformed

  private void updateInit() {
    //JLStatus.setVisible(true);
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JPBProgressBar.setVisible(true);
        pack();
      }
    });
  }

  public void setPatchNotesButtonState(final boolean enabled) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JBPatchNotes.setEnabled(enabled);
        pack();
      }
    });
  }

  public void setStatusLabelText(final String text) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JLStatus.setText(text);
      }
    });
  }

  public void updateProgress(final int percent) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JPBProgressBar.setValue(percent);
        setStatusLabelText("Progress: " + percent + "%");
      }
    });
  }

  @Override
  public void dispose() {
    countdown.countDown();
    if (patchNotes != null) {
      if (patchNotes.isVisible()) {
        patchNotes.dispose();
      }
    }
    super.dispose();
  }

  public void setYesButtonText(final String text) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JBYes.setText(text);
      }
    });
  }

  public void setYesButtonText(final int updateLevel) {
    if (updateLevel == 0) {
      setYesButtonText("Yes (Recommended)");
    } else if (updateLevel == 1) {
      setYesButtonText("Yes (BETA Version)");
    } else if (updateLevel == 2) {
      setYesButtonText("Yes (Minor Update)");
    } else if (updateLevel == 3) {
      setYesButtonText("Yes (MAJOR Update)");
    }
  }

  private void JBNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBNoActionPerformed
    // TODO add your handling code here:
    dispose();
  }//GEN-LAST:event_JBNoActionPerformed

  private void JBPatchNotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBPatchNotesActionPerformed
    // TODO add your handling code here:
    if (patchNotes == null) {
      patchNotes = new PatchNotes(this);
    }
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        patchNotes.setVisible(true);
        setPatchNotesButtonState(false);
      }
    });
  }//GEN-LAST:event_JBPatchNotesActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton JBNo;
  private javax.swing.JButton JBPatchNotes;
  private javax.swing.JButton JBYes;
  private javax.swing.JLabel JLStatus;
  private javax.swing.JProgressBar JPBProgressBar;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  // End of variables declaration//GEN-END:variables
}
