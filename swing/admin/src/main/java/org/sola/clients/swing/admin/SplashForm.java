/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations (FAO).
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice,this list
 *       of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above copyright notice,this list
 *       of conditions and the following disclaimer in the documentation and/or other
 *       materials provided with the distribution.
 *    3. Neither the name of FAO nor the names of its contributors may be used to endorse or
 *       promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT LIABILITY,OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.swing.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * Splash form for Admin application.
 */
public class SplashForm extends javax.swing.JWindow {
private ImageIcon imageSplash;
    private static String strconfFile = System.getProperty("user.home") + "/sola/configuration.properties";
    
    public SplashForm() {
        File confFile = new File (strconfFile);  
        FileInputStream   streamFile = null;
        try {
          streamFile = new FileInputStream  (confFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(org.sola.clients.swing.desktop.SplashForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        Properties prop = new Properties();
        try {
            prop.load(streamFile);
        } catch (IOException ex) {
            Logger.getLogger(org.sola.clients.swing.desktop.SplashForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        String prefix =prop.getProperty("state");
        initComponents();
        imageSplash = new ImageIcon(org.sola.clients.swing.desktop.SplashForm.class.getResource(
                            "/images/sola/"+prefix+"splash_admin_a.png"));
        
        lblSplash.setIcon(imageSplash);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSplash = new javax.swing.JLabel();

        lblSplash.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSplash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sola/splash_admin_a.png"))); // NOI18N
        lblSplash.setAlignmentY(0.0F);
        lblSplash.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblSplash.setName("lblSplash"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSplash, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSplash, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblSplash;
    // End of variables declaration//GEN-END:variables
}
