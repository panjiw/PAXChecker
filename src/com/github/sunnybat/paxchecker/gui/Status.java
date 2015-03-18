package com.github.sunnybat.paxchecker.gui;

import com.github.sunnybat.paxchecker.Email;
import com.github.sunnybat.paxchecker.DataTracker;
import com.github.sunnybat.paxchecker.Audio;
import com.github.sunnybat.paxchecker.browser.Browser;
import com.github.sunnybat.paxchecker.check.CheckSetup;
import java.awt.*;

/**
 *
 * @author SunnyBat
 */
public class Status extends javax.swing.JFrame {

  private SystemTray tray;
  private TrayIcon myIcon;
  private IconMenu myMenu;

  /**
   * Creates new form Status
   */
  public Status() {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        initComponents();
        customComponents();
      }
    });
  }

  public void customComponents() {
    try {
      tray = SystemTray.getSystemTray();
    } catch (Exception e) {
      System.out.println("ERROR: System tray is not supported!");
    }
    myMenu = new IconMenu();
  }

  public void setupComponents() {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JLTitle.setText(Browser.getExpo() + " Website Status");
        if (!Email.shouldSendEmail()) {
          setInfoText("[TEXTING DISABLED]");
          setTextButtonState(false);
        } else if (Email.getAddressList().size() == 1) {
          setInfoText(Email.getUsername() + " -- " + Email.getAddressList().get(0).getCompleteAddress());
        } else {
          setInfoText(Email.getUsername() + " -- Multiple Numbers (Mouse Here to View)");
          String list = "<html>";
          String[] allAddresses = Email.convertToString(Email.getAddressList()).split(";");
          for (int a = 0; a < allAddresses.length; a++) {
            list += allAddresses[a].trim();
            if (a + 1 != allAddresses.length) {
              list += "<br>";
            }
          }
          list += "</html>";
          setLabelTooltipText(list);
        }
        if (!Audio.soundEnabled()) {
          setSoundButtonState(false);
        }
        setDataUsageText(DataTracker.getDataUsedMB());
      }
    });
  }

  public void showWindow() {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        setVisible(true);
      }
    });
  }

  public void minimizeWindow() {
    if (!SystemTray.isSupported() || myIcon == null) {
      return;
    }
    try {
      tray.add(myIcon);
    } catch (Exception e) {
      e.printStackTrace();
    }
    setVisible(false);
  }

  public void maximizeWindow() {
    setExtendedState(javax.swing.JFrame.NORMAL);
    setVisible(true);
    this.setLocationRelativeTo(null);
    this.toFront();
    tray.remove(myIcon); // Fine if myIcon == null or myIcon isn't in tray
  }

  public void setInformationText(final String text) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JLInformation.setText(text);
      }
    });
  }

  public void setLastCheckedText(final String text) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JLLastChecked.setText(text);
      }
    });
  }

  public void setLastCheckedText(final int seconds) {
    setLastCheckedText("Time until next check: " + seconds + " seconds");
  }

  public void setInfoText(final String text) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        jLabel2.setText(text);
      }
    });
  }

  public void setTextButtonState(final boolean enabled) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        jButton1.setEnabled(enabled);
        if (enabled) {
          myMenu.addTextButton();
        } else {
          myMenu.removeTextButton();
        }
      }
    });
  }

  public void setSoundButtonState(final boolean enabled) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        jButton2.setEnabled(enabled);
      }
    });
  }

  public void setLabelTooltipText(final String s) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        jLabel2.setToolTipText(s);
      }
    });
  }

  public void setTextButtonText(final String s) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        jButton1.setText(s);
      }
    });
  }

  public void updateJLabel(final javax.swing.JLabel label, final String text) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        label.setText(text);
        label.repaint();
      }
    });
  }

  private void openLabelLink(String text) {
    if (text.toLowerCase().contains("http")) {
      Browser.openLinkInBrowser(text.substring(text.toLowerCase().indexOf("http")));
    }
  }

  public javax.swing.JLabel addLinkJLabel() {
    final javax.swing.JLabel jL = new javax.swing.JLabel();
    jL.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        openLabelLink(jL.getText());
      }
    });
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        jL.setText(" ");
        JPLinks.add(jL);
        pack();
      }
    });
    return jL;
  }

  public void setDataUsageText(final String text) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JLDataUsage.setText(text);
      }
    });
  }

  public void setDataUsageText(long amount) {
    setDataUsageText("Data Used: " + amount + "MB");
  }

  public void setDataUsageText(double mb) {
    setDataUsageText("Data Used: " + mb + "MB");
  }

  public void setIcon(final java.awt.Image image) {
    if (image == null) {
      return;
    }
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          setIconImage(image);
          myIcon = new TrayIcon(image, "PAXChecker", myMenu);
          myIcon.setImageAutoSize(true);
          myIcon.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              maximizeWindow();
            }
          });
        } catch (Exception e) {
          System.out.println("ERROR setting status iconImage!");
        }
      }
    });
  }

  @Override
  public void dispose() {
    if (tray != null) {
      if (myIcon != null) {
        tray.remove(myIcon);
      }
    }
    super.dispose();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    JLTitle = new javax.swing.JLabel();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jButton3 = new javax.swing.JButton();
    JLLastChecked = new javax.swing.JLabel();
    JLInformation = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    JLDataUsage = new javax.swing.JLabel();
    JPLinks = new javax.swing.JPanel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("PAXChecker");
    setResizable(false);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowIconified(java.awt.event.WindowEvent evt) {
        formWindowIconified(evt);
      }
    });

    JLTitle.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
    JLTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    JLTitle.setText("PAX Website Status");

    jButton1.setText("Test Text");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });

    jButton2.setText("Test Alarm Sound");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });

    jButton3.setText("Force Check");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton3ActionPerformed(evt);
      }
    });

    JLLastChecked.setText("Time Until Check: [Initializing]");

    JLInformation.setText(" ");

    jLabel2.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel2.setText("Email -- Phone");

    JLDataUsage.setText("Data Usage: [Initializing]");
    JLDataUsage.setToolTipText("<html>\nNote that this does NOT include Twitter<br>\ndata usage.<br>\nThis is only a rough estimate of data used.<br>\nFor more accurate data usage, download<br>\nWireShark (or similar software) and monitor<br>\nusage through that.\n</html>");

    JPLinks.setLayout(new javax.swing.BoxLayout(JPLinks, javax.swing.BoxLayout.LINE_AXIS));
    JPLinks.setLayout(new javax.swing.BoxLayout(JPLinks, javax.swing.BoxLayout.Y_AXIS));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
            .addComponent(JLDataUsage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JLInformation, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JLLastChecked, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPLinks, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(JLTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGap(10, 10, 10))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(JLTitle)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel2)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(JPLinks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(JLLastChecked)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(JLDataUsage)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(JLInformation)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButton2)
          .addComponent(jButton1))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButton3)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    // TODO add your handling code here:
    CheckSetup.forceRefresh();
  }//GEN-LAST:event_jButton3ActionPerformed

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    // TODO add your handling code here:
    Email.sendBackgroundTestEmail();
  }//GEN-LAST:event_jButton1ActionPerformed

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    // TODO add your handling code here:
//    Browser.openLinkInBrowser("http://prime.paxsite.com");
//    setInformationText("PAX Prime site opened.");
    if (Audio.playAlarm()) {
      setInformationText("Alarm started.");
    } else {
      setInformationText("Unable to play alarm.");
    }
  }//GEN-LAST:event_jButton2ActionPerformed

  private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
    // TODO add your handling code here:
    minimizeWindow();
  }//GEN-LAST:event_formWindowIconified

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel JLDataUsage;
  private javax.swing.JLabel JLInformation;
  private javax.swing.JLabel JLLastChecked;
  private javax.swing.JLabel JLTitle;
  private javax.swing.JPanel JPLinks;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JLabel jLabel2;
  // End of variables declaration//GEN-END:variables
}