/**
 * ******************************************************************************************
 * Copyright (C) 2015 - Food and Agriculture Organization of the United Nations (FAO).
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
package org.sola.clients.swing.ui.reports;

import java.awt.Dialog;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JasperPrint;
import org.sola.clients.swing.common.LocalizationManager;

/**
 * Displays reports.
 */
public class ReportViewerForm extends javax.swing.JFrame {

    public ReportViewerForm(JasperPrint jasperPrint) {
        initComponents();
        this.setIconImage(new ImageIcon(ReportViewerForm.class.getResource("/images/sola/logo.png")).getImage());
           // shift the title text on the right of the Registry Icon Image
      
        postInit(jasperPrint);
    }
    
    /** 
     * Runs post initialization tasks to set form bounds and form title image.
     * @param jasperPrint {@link JasperPrint} instance to define form size and 
     * extract image from underlying control.
     */
    private void postInit(JasperPrint jasperPrint) {
        reportViewerPanel.initReport(jasperPrint);
        this.setBounds(reportViewerPanel.getBounds());
        this.setResizable(true);
        
        if(reportViewerPanel.getJasperViewer() !=null){
            this.setIconImage(reportViewerPanel.getJasperViewer().getIconImage());
        }
        
        this.setIconImage(new ImageIcon(ReportViewerForm.class.getResource("/images/sola/logo.png")).getImage());
           // shift the title text on the right of the Registry Icon Image
        String pre = "";
        pre = String.format("%" + 8 + "s", pre);
        //  put the obtained number of blanks before the title text
        this.setTitle(pre + this.getTitle()+" - " + LocalizationManager.getVersionNumber());
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reportViewerPanel = new org.sola.clients.swing.ui.reports.ReportViewerPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setName("Form"); // NOI18N

        reportViewerPanel.setName("reportViewerPanel"); // NOI18N

        javax.swing.GroupLayout reportViewerPanelLayout = new javax.swing.GroupLayout(reportViewerPanel);
        reportViewerPanel.setLayout(reportViewerPanelLayout);
        reportViewerPanelLayout.setHorizontalGroup(
            reportViewerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 323, Short.MAX_VALUE)
        );
        reportViewerPanelLayout.setVerticalGroup(
            reportViewerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 210, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(reportViewerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(reportViewerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.ui.reports.ReportViewerPanel reportViewerPanel;
    // End of variables declaration//GEN-END:variables
}
