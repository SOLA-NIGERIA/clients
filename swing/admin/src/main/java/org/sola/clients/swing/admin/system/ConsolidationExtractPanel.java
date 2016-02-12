/**
 * ******************************************************************************************
 * Copyright (C) 2014 - Food and Agriculture Organization of the United Nations
 * (FAO). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,this
 * list of conditions and the following disclaimer. 2. Redistributions in binary
 * form must reproduce the above copyright notice,this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. 3. Neither the name of FAO nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT,STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.swing.admin.system;

import java.util.concurrent.TimeUnit;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.common.FileUtility;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.services.boundary.wsclients.exception.WebServiceClientException;

/**
 * It is the user interface where the extraction process can be started.
 *
 * @author Elton Manoku
 */
public class ConsolidationExtractPanel extends ContentPanel {

    public static final String PANEL_NAME = "ConsolidationExtractPanel";
    private String processName = "extract";
    private static final int PROGRESS_CHECK_INTERVAL_SECONDS = 1;

    /**
     * Creates new form ConsolidationExtractPanel
     */
    public ConsolidationExtractPanel() {
        initComponents();
    }

    private void run() {

        txtStatus.setText(MessageUtility.getLocalizedMessageText(
                ClientMessage.ADMIN_CONSOLIDATION_RUNNING));

        SolaTask taskExtract = new SolaTask<Void, Void>() {
            boolean failed = false;
            //The file name in the documents folder in the server.
            String fileOfExtract = "";

            @Override
            public Void doTask() {
                txtLog.setText("");
                try {
                    setMessage(MessageUtility.getLocalizedMessageText(
                            ClientMessage.ADMIN_CONSOLIDATION_EXTRACT));
                    processName = WSManager.getInstance().getAdminService().consolidationExtract(
                            chkGenerateConsolidationSchema.isSelected(), chkEverything.isSelected(), chkDumpToFile.isSelected());
                    fileOfExtract = processName + ".backup";
                } catch (WebServiceClientException ex) {
                    failed = true;
                    txtStatus.setText(ex.getMessage());
                }
                return null;
            }

            @Override
            protected void taskDone() {
                super.taskDone();
                if (failed) {
                    txtStatus.setText(String.format("%s:%s", txtStatus.getText(),
                            MessageUtility.getLocalizedMessageText(ClientMessage.ADMIN_CONSOLIDATION_FAILED)));
                } else {
                    txtStatus.setText(MessageUtility.getLocalizedMessage(
                            ClientMessage.ADMIN_CONSOLIDATION_EXTRACT_OUTPUT_FILE,
                            new String[]{WSManager.getInstance().getAdminService().getSetting("path-to-backup", "")
                        + "/" + fileOfExtract}).getMessage());
                }
                // It can be that even the process if finished, still the progress bar is not yet 100 percent.
                // This makes sure. The number 10000 is a number which is the infinite max the 
                // progress can have
                WSManager.getInstance().getAdminService().setProcessProgress(processName, 10000);
            }
        };
        TaskManager.getInstance().runTask(taskExtract);

        // A seperate task that updates the progressbar on the client
        SolaTask taskProcessProgress = new SolaTask<Void, Void>() {
            Integer progressValue = 0;

            @Override
            public Void doTask() {
                while (progressValue < 100) {
                    try {
                        progressValue = WSManager.getInstance().getAdminService().getProcessProgress(
                                processName, true);
                        progressBarProcess.setValue(progressValue);
                        TimeUnit.SECONDS.sleep(PROGRESS_CHECK_INTERVAL_SECONDS);
                    } catch (InterruptedException ex) {
                    }
                }
                return null;
            }

            @Override
            protected void taskDone() {
                super.taskDone();
            }
        };
        int maximumAllowedNumberOfTasks = TaskManager.getInstance().getMaximumAllowedNumberOfTasks();
        if (maximumAllowedNumberOfTasks <= TaskManager.getInstance().getNumberOfActiveTasks()) {
            TaskManager.getInstance().setMaximumAllowedNumberOfTasks(maximumAllowedNumberOfTasks + 1);
        }
        TaskManager.getInstance().runTask(taskProcessProgress);
        TaskManager.getInstance().setMaximumAllowedNumberOfTasks(maximumAllowedNumberOfTasks);

    }

    /**
     * It retrieves the log of the process.
     *
     */
    private void showLog() {
        SolaTask showLog = new SolaTask<Void, Void>() {
            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.ADMIN_CONSOLIDATION_EXTRACT));
                try{
                    txtLog.setText(
                        WSManager.getInstance().getAdminService().getProcessLog(processName));
                }catch(WebServiceClientException ex){
                    
                }
                return null;
            }

            @Override
            protected void taskDone() {
                super.taskDone();
            }
        };


        int maximumAllowedNumberOfTasks = TaskManager.getInstance().getMaximumAllowedNumberOfTasks();
        if (maximumAllowedNumberOfTasks <= TaskManager.getInstance().getNumberOfActiveTasks()) {
            TaskManager.getInstance().setMaximumAllowedNumberOfTasks(
                    TaskManager.getInstance().getNumberOfActiveTasks());
        }
        TaskManager.getInstance().runTask(showLog);
        TaskManager.getInstance().setMaximumAllowedNumberOfTasks(maximumAllowedNumberOfTasks);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new org.sola.clients.swing.ui.HeaderPanel();
        btnStart = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        chkEverything = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        progressBarProcess = new javax.swing.JProgressBar();
        cmdShowLog = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        chkGenerateConsolidationSchema = new javax.swing.JCheckBox();
        chkDumpToFile = new javax.swing.JCheckBox();

        setHeaderPanel(pnlHeader);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/admin/system/Bundle"); // NOI18N
        pnlHeader.setTitleText(bundle.getString("ConsolidationExtractPanel.pnlHeader.titleText")); // NOI18N

        btnStart.setText(bundle.getString("ConsolidationExtractPanel.btnStart.text")); // NOI18N
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        txtLog.setEditable(false);
        txtLog.setColumns(20);
        txtLog.setRows(5);
        jScrollPane1.setViewportView(txtLog);

        chkEverything.setText(bundle.getString("ConsolidationExtractPanel.chkEverything.text_1")); // NOI18N

        jLabel3.setText(bundle.getString("ConsolidationExtractPanel.jLabel3.text")); // NOI18N

        jLabel4.setText(bundle.getString("ConsolidationExtractPanel.jLabel4.text")); // NOI18N

        jLabel5.setText(bundle.getString("ConsolidationExtractPanel.jLabel5.text")); // NOI18N

        cmdShowLog.setText(bundle.getString("ConsolidationExtractPanel.cmdShowLog.text")); // NOI18N
        cmdShowLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdShowLogActionPerformed(evt);
            }
        });

        jLabel1.setText(bundle.getString("ConsolidationExtractPanel.jLabel1.text")); // NOI18N

        txtStatus.setEditable(false);
        txtStatus.setText(bundle.getString("ConsolidationExtractPanel.txtStatus.text")); // NOI18N

        chkGenerateConsolidationSchema.setSelected(true);
        chkGenerateConsolidationSchema.setText(bundle.getString("ConsolidationExtractPanel.chkGenerateConsolidationSchema.text")); // NOI18N
        chkGenerateConsolidationSchema.setToolTipText(bundle.getString("ConsolidationExtractPanel.chkGenerateConsolidationSchema.toolTipText")); // NOI18N
        chkGenerateConsolidationSchema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkGenerateConsolidationSchemaActionPerformed(evt);
            }
        });

        chkDumpToFile.setSelected(true);
        chkDumpToFile.setText(bundle.getString("ConsolidationExtractPanel.chkDumpToFile.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkGenerateConsolidationSchema)
                        .addGap(18, 18, 18)
                        .addComponent(chkEverything)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cmdShowLog))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnStart)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(progressBarProcess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtStatus))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(chkDumpToFile)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkEverything)
                    .addComponent(jLabel3)
                    .addComponent(chkGenerateConsolidationSchema))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(chkDumpToFile))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnStart)
                        .addComponent(jLabel5))
                    .addComponent(progressBarProcess, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdShowLog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        if (!chkGenerateConsolidationSchema.isSelected()
                && !chkDumpToFile.isSelected()) {
            MessageUtility.displayMessage(ClientMessage.ADMIN_CONSOLIDATION_NO_ACTION_SELECTED);
            return;
        }
        run();
    }//GEN-LAST:event_btnStartActionPerformed

    private void cmdShowLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdShowLogActionPerformed
        showLog();
    }//GEN-LAST:event_cmdShowLogActionPerformed

    private void chkGenerateConsolidationSchemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkGenerateConsolidationSchemaActionPerformed
        chkEverything.setEnabled(chkGenerateConsolidationSchema.isSelected());
        if (!chkEverything.isEnabled()) {
            chkEverything.setSelected(false);
        }
    }//GEN-LAST:event_chkGenerateConsolidationSchemaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JCheckBox chkDumpToFile;
    private javax.swing.JCheckBox chkEverything;
    private javax.swing.JCheckBox chkGenerateConsolidationSchema;
    private javax.swing.JButton cmdShowLog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private org.sola.clients.swing.ui.HeaderPanel pnlHeader;
    private javax.swing.JProgressBar progressBarProcess;
    private javax.swing.JTextArea txtLog;
    private javax.swing.JTextField txtStatus;
    // End of variables declaration//GEN-END:variables
}
