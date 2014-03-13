/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations
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
package org.sola.clients.swing.desktop.reports;

import java.awt.ComponentOrientation;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.digitalarchive.DocumentBean;
import org.sola.clients.beans.source.SourceBean;
import org.sola.clients.beans.systematicregistration.SysRegStatusBean;
import org.sola.clients.beans.systematicregistration.SysRegWorkUnitBean;
import org.sola.clients.beans.validation.ValidationResultBean;
import org.sola.clients.reports.ReportManager;
import org.sola.clients.swing.common.controls.CalendarForm;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.common.utils.BindingTools;
import org.sola.clients.swing.desktop.ReportViewerForm;
import org.sola.clients.swing.ui.renderers.FormattersFactory;
import org.sola.clients.swing.ui.validation.ValidationResultForm;
import org.sola.common.FileUtility;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.cadastre.SysRegWorkUnitTO;

/**
 *
 * @author rizzom
 */
public class SysRegWorkUnitForm extends javax.swing.JDialog {

    private String location;
    private String tmpLocation = "";
    private ResourceBundle resourceBundle;
    public final static String SELECTED_PARCEL = "selectedParcel";

    /**
     * Creates new form SysRegWorkUnitForm
     */
    public SysRegWorkUnitForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
  
    
     /** 
     * Form constructor. 
     * @param savePartyOnAction Boolean flag to indicate whether to save party 
     * when Save button is clicked.
     * @param partyBean {@link PartyBean} instance to display.
     * @param readOnly Indicates whether to allow any changes on the form.
     */
   public SysRegWorkUnitForm(java.awt.Frame parent, boolean modal, String location) {
        super(parent, modal);
        this.location = location;
        resourceBundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/cadastre/Bundle");
        initComponents();
//          headerPanel.setTitleText(resourceBundle.getString("SysRegWorkUnitForm.headerPanel.titleText"));
            
          if (sysRegWorkUnitBean != null) {
            labHeader1.setText(sysRegWorkUnitBean.getName()+" - "+resourceBundle.getString("SysRegWorkUnitForm.labHeader1.text"));

          } 
          
          searchParams.setNameLastpart(location);          
          SysRegStatusBean sysRegStatusBeanAppo = new SysRegStatusBean ();
          sysRegStatusBeanAppo.passParameter(searchParams);
          
          sysRegStatusBean = sysRegStatusBeanAppo.getStatusList().get(0);
          this.txtEnteredParcels.setText(sysRegStatusBean.getDigitizedparcels().toString());
          this.txtEnteredClaims.setText(sysRegStatusBean.getClaimsentered().toString()); 
          this.txtReadyPD.setText(sysRegStatusBean.getParcelsreadyPD().toString()); 
          this.txtInPD.setText(sysRegStatusBean.getParcelsPD().toString()); 
          this.txtCompletedPD.setText(sysRegStatusBean.getParcelscompletedPD().toString()); 
          this.txtUnsolvedDisputes.setText(sysRegStatusBean.getUnsolveddisputes().toString()); 
          this.txtGeneratedCofO.setText(sysRegStatusBean.getGeneratedcertificates().toString()); 
  }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        sysRegWorkUnitBean = createSysRegWorkUnitBean();
        sysRegStatusBean = new org.sola.clients.beans.systematicregistration.SysRegStatusBean();
        searchParams = new org.sola.clients.beans.systematicregistration.SysRegManagementParamsBean();
        selectionPanel = new javax.swing.JPanel();
        cadastreObjectSearch = new org.sola.clients.swing.ui.cadastre.LocationSearch();
        configureSRWU = new javax.swing.JButton();
        labHeader = new javax.swing.JLabel();
        srwuPanel = new javax.swing.JPanel();
        srwuToolBar = new javax.swing.JToolBar();
        btnSave1 = new org.sola.clients.swing.common.buttons.BtnSave();
        labHeader1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelManuallyEntered = new javax.swing.JPanel();
        panelSltrOffice = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtScannedDemarcation = new javax.swing.JTextField();
        txtScannedClaims = new javax.swing.JTextField();
        txtDistributedCertificates = new javax.swing.JTextField();
        labNotificationFrom = new javax.swing.JLabel();
        txtFromDate = new javax.swing.JFormattedTextField();
        btnShowCalendarFrom = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtRecordedClaims = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtRecordedParcel = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtEstimatedParcel = new javax.swing.JTextField();
        panelSolaCalculated = new javax.swing.JPanel();
        txtEnteredParcels = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEnteredClaims = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtReadyPD = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtInPD = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCompletedPD = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtGeneratedCofO = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtUnsolvedDisputes = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Work Unit Details");

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/reports/Bundle"); // NOI18N
        cadastreObjectSearch.setText(bundle.getString("SysRegListingParamsForm.cadastreObjectSearch.text")); // NOI18N

        configureSRWU.setText(bundle.getString("SysRegListingParamsForm.viewReport.text")); // NOI18N
        configureSRWU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configureSRWUActionPerformed(evt);
            }
        });

        labHeader.setBackground(new java.awt.Color(153, 153, 153));
        labHeader.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labHeader.setForeground(new java.awt.Color(255, 255, 255));
        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/cadastre/Bundle"); // NOI18N
        labHeader.setText(bundle1.getString("SysRegWorkUnitForm.labHeader.text")); // NOI18N
        labHeader.setOpaque(true);

        javax.swing.GroupLayout selectionPanelLayout = new javax.swing.GroupLayout(selectionPanel);
        selectionPanel.setLayout(selectionPanelLayout);
        selectionPanelLayout.setHorizontalGroup(
            selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectionPanelLayout.createSequentialGroup()
                .addComponent(cadastreObjectSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(configureSRWU)
                .addContainerGap())
            .addComponent(labHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        selectionPanelLayout.setVerticalGroup(
            selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectionPanelLayout.createSequentialGroup()
                .addComponent(labHeader)
                .addGap(10, 10, 10)
                .addGroup(selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cadastreObjectSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(configureSRWU))
                .addContainerGap(197, Short.MAX_VALUE))
        );

        srwuToolBar.setFloatable(false);
        srwuToolBar.setRollover(true);
        srwuToolBar.setName("srwuToolBar");

        btnSave1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSave1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave1ActionPerformed(evt);
            }
        });
        srwuToolBar.add(btnSave1);

        labHeader1.setBackground(new java.awt.Color(153, 153, 153));
        labHeader1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labHeader1.setForeground(new java.awt.Color(255, 255, 255));
        labHeader1.setText(bundle1.getString("SysRegWorkUnitForm.labHeader1.text")); // NOI18N
        labHeader1.setOpaque(true);

        panelManuallyEntered.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        panelSltrOffice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sltr Office", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, java.awt.Color.gray));

        jLabel7.setText("Scanned Demarcation");

        jLabel6.setText("Scanned Claims");

        jLabel4.setText("Distributed Certificates");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sysRegWorkUnitBean, org.jdesktop.beansbinding.ELProperty.create("${scanneddemarcation}"), txtScannedDemarcation, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sysRegWorkUnitBean, org.jdesktop.beansbinding.ELProperty.create("${scannedclaims}"), txtScannedClaims, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sysRegWorkUnitBean, org.jdesktop.beansbinding.ELProperty.create("${distributedcertificates}"), txtDistributedCertificates, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        labNotificationFrom.setText(bundle.getString("SysRegListingParamsForm.labNotificationFrom.text")); // NOI18N

        txtFromDate.setFont(new java.awt.Font("Tahoma", 0, 12));
        txtFromDate.setFormatterFactory(FormattersFactory.getInstance().getDateFormatterFactory());
        txtFromDate.setToolTipText(bundle.getString("SysRegListingParamsForm.txtFromDate.toolTipText")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sysRegWorkUnitBean, org.jdesktop.beansbinding.ELProperty.create("${publicdisplaystartdate}"), txtFromDate, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        txtFromDate.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        txtFromDate.setHorizontalAlignment(JTextField.LEADING);

        btnShowCalendarFrom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/calendar.png"))); // NOI18N
        btnShowCalendarFrom.setText(bundle.getString("SysRegListingParamsForm.btnShowCalendarFrom.text")); // NOI18N
        btnShowCalendarFrom.setBorder(null);
        btnShowCalendarFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowCalendarFromActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSltrOfficeLayout = new javax.swing.GroupLayout(panelSltrOffice);
        panelSltrOffice.setLayout(panelSltrOfficeLayout);
        panelSltrOfficeLayout.setHorizontalGroup(
            panelSltrOfficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSltrOfficeLayout.createSequentialGroup()
                .addGroup(panelSltrOfficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSltrOfficeLayout.createSequentialGroup()
                        .addGroup(panelSltrOfficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFromDate)
                            .addComponent(labNotificationFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnShowCalendarFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSltrOfficeLayout.createSequentialGroup()
                        .addGroup(panelSltrOfficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtScannedDemarcation, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelSltrOfficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtScannedClaims, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelSltrOfficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDistributedCertificates, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(67, Short.MAX_VALUE))
        );

        panelSltrOfficeLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel7, txtScannedDemarcation});

        panelSltrOfficeLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel6, txtScannedClaims});

        panelSltrOfficeLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel4, txtDistributedCertificates});

        panelSltrOfficeLayout.setVerticalGroup(
            panelSltrOfficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSltrOfficeLayout.createSequentialGroup()
                .addGroup(panelSltrOfficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4))
                .addGap(5, 5, 5)
                .addGroup(panelSltrOfficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtScannedDemarcation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtScannedClaims, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDistributedCertificates, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labNotificationFrom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSltrOfficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFromDate)
                    .addComponent(btnShowCalendarFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "On Field", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, java.awt.Color.gray));

        jLabel3.setText("Recorded Claims");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sysRegWorkUnitBean, org.jdesktop.beansbinding.ELProperty.create("${recordedclaims}"), txtRecordedClaims, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel2.setText("Recorded Parcels");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sysRegWorkUnitBean, org.jdesktop.beansbinding.ELProperty.create("${recordedparcel}"), txtRecordedParcel, org.jdesktop.beansbinding.BeanProperty.create("text"), "workunitGroup");
        bindingGroup.addBinding(binding);

        jLabel1.setText("Estimated Parcel");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sysRegWorkUnitBean, org.jdesktop.beansbinding.ELProperty.create("${estimatedparcel}"), txtEstimatedParcel, org.jdesktop.beansbinding.BeanProperty.create("text"), "estimatedGroup");
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstimatedParcel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRecordedParcel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtRecordedClaims))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, txtRecordedParcel});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, txtEstimatedParcel});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRecordedParcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRecordedClaims, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEstimatedParcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelManuallyEnteredLayout = new javax.swing.GroupLayout(panelManuallyEntered);
        panelManuallyEntered.setLayout(panelManuallyEnteredLayout);
        panelManuallyEnteredLayout.setHorizontalGroup(
            panelManuallyEnteredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSltrOffice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelManuallyEnteredLayout.setVerticalGroup(
            panelManuallyEnteredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelManuallyEnteredLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSltrOffice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(" Input", panelManuallyEntered);

        panelSolaCalculated.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtEnteredParcels.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sysRegStatusBean, org.jdesktop.beansbinding.ELProperty.create("${estimatedparcel}"), txtEnteredParcels, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel5.setText("Mapped Parcels");

        txtEnteredClaims.setEnabled(false);

        jLabel8.setText("Entered Claims");

        txtReadyPD.setEnabled(false);

        jLabel9.setText("Ready for Public Display");

        txtInPD.setEnabled(false);

        jLabel10.setText("In Public Display");

        txtCompletedPD.setEnabled(false);

        jLabel11.setText("Completed PD");

        txtGeneratedCofO.setEnabled(false);

        jLabel12.setText("Generated CofO");

        txtUnsolvedDisputes.setEnabled(false);

        jLabel13.setText("Unsolved Disputes");

        javax.swing.GroupLayout panelSolaCalculatedLayout = new javax.swing.GroupLayout(panelSolaCalculated);
        panelSolaCalculated.setLayout(panelSolaCalculatedLayout);
        panelSolaCalculatedLayout.setHorizontalGroup(
            panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSolaCalculatedLayout.createSequentialGroup()
                .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelSolaCalculatedLayout.createSequentialGroup()
                        .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtEnteredParcels, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtEnteredClaims, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtReadyPD, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelSolaCalculatedLayout.createSequentialGroup()
                        .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtGeneratedCofO, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelSolaCalculatedLayout.createSequentialGroup()
                        .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(txtInPD, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txtCompletedPD, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(txtUnsolvedDisputes, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(33, 33, 33))
        );

        panelSolaCalculatedLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel5, txtEnteredParcels});

        panelSolaCalculatedLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel8, txtEnteredClaims});

        panelSolaCalculatedLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel9, txtReadyPD});

        panelSolaCalculatedLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, txtInPD});

        panelSolaCalculatedLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel11, txtCompletedPD});

        panelSolaCalculatedLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel13, txtUnsolvedDisputes});

        panelSolaCalculatedLayout.setVerticalGroup(
            panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSolaCalculatedLayout.createSequentialGroup()
                .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEnteredParcels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEnteredClaims, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReadyPD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSolaCalculatedLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInPD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolaCalculatedLayout.createSequentialGroup()
                        .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSolaCalculatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCompletedPD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUnsolvedDisputes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGeneratedCofO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 276, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("View", panelSolaCalculated);

        javax.swing.GroupLayout srwuPanelLayout = new javax.swing.GroupLayout(srwuPanel);
        srwuPanel.setLayout(srwuPanelLayout);
        srwuPanelLayout.setHorizontalGroup(
            srwuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labHeader1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(srwuPanelLayout.createSequentialGroup()
                .addGroup(srwuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(srwuToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        srwuPanelLayout.setVerticalGroup(
            srwuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(srwuPanelLayout.createSequentialGroup()
                .addComponent(labHeader1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(srwuToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(srwuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(selectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(srwuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShowCalendarFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowCalendarFromActionPerformed
        showCalendar(txtFromDate);
    }//GEN-LAST:event_btnShowCalendarFromActionPerformed

    private void configureSRWUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configureSRWUActionPerformed
        if (cadastreObjectSearch.getSelectedElement() != null) {
            this.firePropertyChange(SELECTED_PARCEL, null,
                    cadastreObjectSearch.getSelectedElement());
            this.location = cadastreObjectSearch.getSelectedElement().toString();
            tmpLocation = (this.location);
            System.out.println("LOCATION     " + tmpLocation);
            createSysRegWorkUnitBean();
            SysRegWorkUnitForm srwu = new SysRegWorkUnitForm(null, true, tmpLocation);
            this.dispose();
            srwu.setVisible(true);
        } else {
            MessageUtility.displayMessage(ClientMessage.CHECK_SELECT_LOCATION);
            return;
        }
    }//GEN-LAST:event_configureSRWUActionPerformed

    private void btnSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave1ActionPerformed
        
        if (this.location!= null){
           saveSRWU();
        }
         else {
            MessageUtility.displayMessage(ClientMessage.CHECK_SELECT_LOCATION);
            return;
        }
    }//GEN-LAST:event_btnSave1ActionPerformed
   
      private SysRegWorkUnitBean createSysRegWorkUnitBean() {
        if (this.location != null) {
            this.sysRegWorkUnitBean = getSysRegWorkUnitBean(location);
        } else {
            this.sysRegWorkUnitBean = new SysRegWorkUnitBean();
        }
        
        return this.sysRegWorkUnitBean;
      }
     /**
     * Returns {@link BaUnitBean} by first and last name part.
     */
    private SysRegWorkUnitBean getSysRegWorkUnitBean(String location) {
        SysRegWorkUnitTO workUnitTO = WSManager.getInstance().getCadastreService().getSysRegWorkUnitByAllParts(location);
        return TypeConverters.TransferObjectToBean(workUnitTO, SysRegWorkUnitBean.class, null);
    }
      
      
     /**
     * Returns {@link BaUnitBean} by first and last name part.
     */
    private void saveSRWU(){
    
        
        SolaTask<Boolean, Boolean> t = new SolaTask<Boolean, Boolean>() {

                @Override
                public Boolean doTask() {
                    setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_SAVING));
                    return  sysRegWorkUnitBean.saveSysRegWorkUnit(sysRegWorkUnitBean);
                }

                @Override
                public void taskDone() {
                       if (get() != null && get()) {
                            MessageUtility.displayMessage(ClientMessage.SYSTEMATIC_REGISTRATION_SRWU_SAVED);
                    }
                }
            };
            TaskManager.getInstance().runTask(t);
    }
            
    private void showCalendar(JFormattedTextField dateField) {
        CalendarForm calendar = new CalendarForm(null, true, dateField);
        calendar.setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.common.buttons.BtnSave btnSave1;
    private javax.swing.JButton btnShowCalendarFrom;
    private org.sola.clients.swing.ui.cadastre.LocationSearch cadastreObjectSearch;
    private javax.swing.JButton configureSRWU;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labHeader;
    private javax.swing.JLabel labHeader1;
    private javax.swing.JLabel labNotificationFrom;
    private javax.swing.JPanel panelManuallyEntered;
    private javax.swing.JPanel panelSltrOffice;
    private javax.swing.JPanel panelSolaCalculated;
    private org.sola.clients.beans.systematicregistration.SysRegManagementParamsBean searchParams;
    private javax.swing.JPanel selectionPanel;
    private javax.swing.JPanel srwuPanel;
    private javax.swing.JToolBar srwuToolBar;
    private org.sola.clients.beans.systematicregistration.SysRegStatusBean sysRegStatusBean;
    private org.sola.clients.beans.systematicregistration.SysRegWorkUnitBean sysRegWorkUnitBean;
    private javax.swing.JTextField txtCompletedPD;
    private javax.swing.JTextField txtDistributedCertificates;
    private javax.swing.JTextField txtEnteredClaims;
    private javax.swing.JTextField txtEnteredParcels;
    private javax.swing.JTextField txtEstimatedParcel;
    private javax.swing.JFormattedTextField txtFromDate;
    private javax.swing.JTextField txtGeneratedCofO;
    private javax.swing.JTextField txtInPD;
    private javax.swing.JTextField txtReadyPD;
    private javax.swing.JTextField txtRecordedClaims;
    private javax.swing.JTextField txtRecordedParcel;
    private javax.swing.JTextField txtScannedClaims;
    private javax.swing.JTextField txtScannedDemarcation;
    private javax.swing.JTextField txtUnsolvedDisputes;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
