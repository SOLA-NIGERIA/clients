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
package org.sola.clients.swing.desktop.administrative;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import javax.swing.JFormattedTextField;
import javax.validation.groups.Default;
import org.sola.clients.beans.administrative.BaUnitBean;
import org.sola.clients.beans.administrative.LeaseConditionsTemplateBean;
import org.sola.clients.beans.administrative.RrrBean;
import org.sola.clients.beans.administrative.RrrShareBean;
import org.sola.clients.beans.administrative.validation.OwnershipValidationGroup;
import org.sola.clients.beans.application.ApplicationBean;
import org.sola.clients.beans.application.ApplicationServiceBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.party.PartyBean;
import org.sola.clients.beans.party.PartySummaryBean;
import org.sola.clients.beans.referencedata.RequestTypeBean;
import org.sola.clients.beans.referencedata.StatusConstants;
import org.sola.clients.beans.source.SourceBean;
import org.sola.clients.reports.ReportManager;
import org.sola.clients.swing.common.controls.CalendarForm;
import org.sola.clients.swing.desktop.MainForm;
import org.sola.clients.swing.desktop.source.DocumentsManagementExtPanel;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.clients.swing.ui.renderers.FormattersFactory;
import org.sola.clients.swing.ui.renderers.RightHolderType;
import org.sola.clients.swing.ui.renderers.TableCellListRenderer;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.casemanagement.PartyTO;

/**
 * Form for managing ownership right. {@link RrrBean} is used to bind the data
 * on the form.
 */
public class OwnershipPanel extends ContentPanel {

    private class ShareFormListener implements PropertyChangeListener {
        final ApplicationServiceBean whichService = appService;
        private String notationTxt = "";
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(SharePanel.UPDATED_RRR_SHARE)
                    && evt.getNewValue() != null) {
                rrrBean.updateListItem((RrrShareBean) evt.getNewValue(),
                        rrrBean.getRrrShareList(), true);
                tableShares.clearSelection();
            }
            
            if (whichService != null) {
                notationTxt=   whichService.getRequestType().getNotationTemplate().toString();
            }             
            
            if (rrrBean.getFilteredRrrShareList().size() == 1 && rrrBean.getFilteredRrrShareList().get(0).getShare().contains("1/1")) {
                if (rrrBean.getFilteredRrrShareList().get(0).getRightHolderType().contains("Joint")) {
                    txtNotationText.setText(notationTxt+" - "+rrrBean.JOINT);
                } else {
                    txtNotationText.setText(notationTxt+" - "+rrrBean.UNDEVIDED_SHARES);
                }
            } else {
                txtNotationText.setText(notationTxt+" - "+rrrBean.DEFINED_SHARES);
            }
        }
    }

    private BaUnitBean baunitBean;
    private ApplicationBean applicationBean;
    private ApplicationServiceBean appService;
    private RrrBean.RRR_ACTION rrrAction;
    public static final String UPDATED_RRR = "updatedRRR";

    private DocumentsManagementExtPanel createDocumentsPanel() {
        if (rrrBean == null) {
            rrrBean = new RrrBean();
        }
        if (applicationBean == null) {
            applicationBean = new ApplicationBean();
        }

        boolean allowEdit = true;
        if (rrrAction == RrrBean.RRR_ACTION.VIEW) {
            allowEdit = false;
        }
        // FOR RETRIEVING THE CLAIMS FORM ATTACHED TO THE APPLICATION AS FIRST DOCUMENT IN THE LIST OF RRR DOCUMENTS        
        if (applicationBean.getSourceList().getFilteredList().size() > 0) {
            Boolean addSource = true;
            for (Iterator<SourceBean> it = applicationBean.getSourceList().getFilteredList().iterator(); it.hasNext();) {
                SourceBean appSource = it.next();
                if (appSource.getTypeCode().contentEquals("systematicRegn")) {
                    for (Iterator<SourceBean> itInt = rrrBean.getSourceList().getFilteredList().iterator(); itInt.hasNext();) {
                        SourceBean appSourceInt = itInt.next();
                        if (appSource.getId().contentEquals(appSourceInt.getId())) {
                            addSource = false;
                        }
                    }
                    if (addSource) {
                        rrrBean.getSourceList().getFilteredList().add(appSource);
                    }
                }
            }
        }

        DocumentsManagementExtPanel panel = new DocumentsManagementExtPanel(
                rrrBean.getSourceList(), applicationBean, allowEdit);
        return panel;
    }

    private RrrBean CreateRrrBean() {
        if (rrrBean == null) {
            rrrBean = new RrrBean();
        }
//        if (rrrBean.getNotation()!= null && rrrBean.getNotation().getNotationText().contains("Joint")) {
        if (rrrBean.getFilteredRrrShareList() != null && rrrBean.getFilteredRrrShareList().size() > 0) {

            rrrBean.getFilteredRrrShareList().get(0).setRightHolderType(rrrBean.getNotation().getNotationText());
//             System.out.println("RIGHT HOLDER TYPE  "+rrrBean.getFilteredRrrShareList().get(0).getRightHolderType());
//             rrrBean.getFilteredRrrShareList().get(0).setRightHolderType(this.txtNotationText.getText());
            System.out.println("RIGHT HOLDER TYPE IN CREATE RRRBEAN " + rrrBean.getFilteredRrrShareList().get(0).getRightHolderType());

        }
        return rrrBean;
    }

    public OwnershipPanel(RrrBean rrrBean, ApplicationBean applicationBean,
            ApplicationServiceBean applicationService, RrrBean.RRR_ACTION rrrAction) {

        this.applicationBean = applicationBean;
        this.appService = applicationService;
        this.rrrAction = rrrAction;
        prepareRrrBean(rrrBean, rrrAction);

        initComponents();
        postInit();
    }

    public OwnershipPanel(RrrBean rrrBean, ApplicationBean applicationBean,
            ApplicationServiceBean applicationService, RrrBean.RRR_ACTION rrrAction, BaUnitBean baunitBean) {

        this.applicationBean = applicationBean;
        this.baunitBean = baunitBean;
        this.appService = applicationService;
        this.rrrAction = rrrAction;
        prepareRrrBean(rrrBean, rrrAction);

        initComponents();
        postInit();
    }

    private void postInit() {
        this.cofoNumber.setText(baunitBean.getNameLastpart()+"/"+baunitBean.getNameFirstpart());
//        pnlZone.setVisible(false);
//        this.cbxZone.setVisible(false);
//        this.labZone.setVisible(false);
        
        templates.loadList(true, rrrBean.getRrrType().getCode());
//        this.txtCofO.setEnabled(false);
//        if (this.appService != null) {
//            if (this.appService.getRequestTypeCode().equalsIgnoreCase(RequestTypeBean.CODE_NEW_DIGITAL_TITLE)) {
//                this.txtCofO.setEnabled(true);
//            } else {
//                this.txtCofO.setEnabled(false);
//            }
//        }

        String state = ReportManager.getSettingValue("state");
//            pnlZone.setVisible(false);
//            this.cbxZone.setVisible(false);
//            this.labZone.setVisible(false);
        if (txtTerm.getText() == "" || txtTerm.getText() == null || txtTerm.getText().equalsIgnoreCase(null) || txtTerm.getText().equalsIgnoreCase("")) {
            txtTerm.setText("99");
        }
//System.out.println("IMPROVEMENT    "+this.rrrBean.getImprovementPremium());
        customizeForm();
        customizeSharesButtons(null);
        saveRrrState();
    }

    private void prepareRrrBean(RrrBean rrrBean, RrrBean.RRR_ACTION rrrAction) {
        if (rrrBean == null) {
            this.rrrBean = new RrrBean();
            this.rrrBean.setStatusCode(StatusConstants.PENDING);
        } else {
            this.rrrBean = rrrBean.makeCopyByAction(rrrAction);
        }
        this.rrrBean.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(RrrBean.SELECTED_SHARE_PROPERTY)) {
                    customizeSharesButtons((RrrShareBean) evt.getNewValue());
                }
            }
        });

    }

    private void customizeSharesButtons(RrrShareBean rrrShare) {
        boolean isChangesAllowed = false;
        if (rrrAction == RrrBean.RRR_ACTION.VARY || rrrAction == RrrBean.RRR_ACTION.EDIT
                || rrrAction == RrrBean.RRR_ACTION.NEW) {
            isChangesAllowed = true;
        }

        btnAddShare.setEnabled(isChangesAllowed);
        btnAddJoint.setEnabled(isChangesAllowed);
        if (rrrShare == null) {
            btnRemoveShare.setEnabled(false);
            btnChangeShare.setEnabled(false);
            btnViewShare.setEnabled(false);
        } else {
            btnRemoveShare.setEnabled(isChangesAllowed);
            btnChangeShare.setEnabled(isChangesAllowed);
            btnViewShare.setEnabled(true);
        }

        menuAddShare.setEnabled(btnAddShare.isEnabled());
        menuRemoveShare.setEnabled(btnRemoveShare.isEnabled());
        menuChangeShare.setEnabled(btnChangeShare.isEnabled());
        menuViewShare.setEnabled(btnViewShare.isEnabled());
        cbxIsPrimary.setSelected(true);
    }

    private void customizeForm() {
         
        String stringTitle = rrrBean.getRrrType().getDisplayValue();
        if (appService != null) {
            if (appService.getRequestType().getCode().contentEquals(RequestTypeBean.CODE_SYSTEMATIC_REGISTRATION)) {

                stringTitle = stringTitle + " " + MessageUtility.getLocalizedMessage(
                        ClientMessage.SYSTEMATIC_REGISTRATION_CLAIM).getMessage();
                this.groupPanel1.setTitleText(MessageUtility.getLocalizedMessage(
                        ClientMessage.SYSTEMATIC_REGISTRATION_CLAIMANTS).getMessage());
                this.tableShares.getColumnModel().getColumn(0).setHeaderValue(MessageUtility.getLocalizedMessage(
                        ClientMessage.SYSTEMATIC_REGISTRATION_CLAIMANT).getMessage());

                RrrShareBean rrrShareBean = new RrrShareBean();
                String pId = applicationBean.getContactPersonId();
                PartyTO partyTO = WSManager.getInstance().getCaseManagementService().getParty(pId);
                PartySummaryBean partySummary = TypeConverters.TransferObjectToBean(partyTO, PartyBean.class, null);
                partySummary.getLastName();
                if (rrrBean.getFilteredRrrShareList().size() == 0) {
                    Short labeldef = 1;
                    rrrShareBean.getRightHolderList().add(partySummary);
                    rrrShareBean.setDenominator(labeldef);
                    rrrShareBean.setNominator(labeldef);
                    rrrBean.getFilteredRrrShareList().add(rrrShareBean);
                }

            }
            if (appService.getRequestType().getCode().contentEquals(RequestTypeBean.CODE_NEW_DIGITAL_TITLE)) {
                this.txtRegDatetime.setValue(null);
                this.rrrBean.setExpirationDate(this.baunitBean.getExpirationDate());
            }
            if (!appService.getRequestTypeCode().contentEquals(RequestTypeBean.CODE_NEW_FREEHOLD)) {
                System.out.println("SERVICE   " + appService.getRequestTypeCode());
//                this.conditionsPanel.setVisible(false);
//                this.conditionsPanel.setEnabled(false);
//                this.mainTabbedPanel.remove(conditionsPanel);
            }
        }

        headerPanel.setTitleText(stringTitle);
        if (rrrAction == RrrBean.RRR_ACTION.NEW) {
            btnSave.setText(MessageUtility.getLocalizedMessage(
                    ClientMessage.GENERAL_LABELS_CREATE_AND_CLOSE).getMessage());
        }
        if (rrrAction == RrrBean.RRR_ACTION.CANCEL) {
            btnSave.setText(MessageUtility.getLocalizedMessage(
                    ClientMessage.GENERAL_LABELS_TERMINATE_AND_CLOSE).getMessage());
        }

        if (rrrAction != RrrBean.RRR_ACTION.EDIT && rrrAction != RrrBean.RRR_ACTION.VIEW
                && appService != null) {
            // Set default noation text from the selected application service
            txtNotationText.setText(appService.getRequestType().getNotationTemplate());
        }
        cbxIsPrimary.setSelected(true);

        if (rrrAction == RrrBean.RRR_ACTION.VIEW) {
            btnSave.setEnabled(false);
            txtNotationText.setEnabled(false);
            txtRegDatetime.setEditable(false);
            cbxIsPrimary.setEnabled(false);
            txtConditionsText.setEnabled(false);
            cbxConditionsTemplates.setEnabled(false);
            btnInsertConditionsText.setEnabled(false);
        }

        if (txtTerm.getText() == "" || txtTerm.getText() == null || txtTerm.getText().equalsIgnoreCase(null) || txtTerm.getText().equalsIgnoreCase("")) {
            txtTerm.setText("99");
        }
        if (txtRevPeriod.getText() == "" || txtRevPeriod.getText() == null || txtRevPeriod.getText().equalsIgnoreCase(null) || txtRevPeriod.getText().equalsIgnoreCase("")) {
            txtRevPeriod.setText("10");
        }

        System.out.println("NOTATION:::  " + rrrBean.getNotation().getNotationText());

//        if (rrrBean.getNotation()!= null && rrrBean.getNotation().getNotationText().contains("Joint")) {
//             rrrBean.getFilteredRrrShareList().get(0).setRightHolderType(rrrBean.getNotation().getNotationText());
//             System.out.println("RIGHT HOLDER TYPE  "+rrrBean.getFilteredRrrShareList().get(0).getRightHolderType());
//             
        if (appService != null && appService.getRequestType().getCode().contentEquals(RequestTypeBean.CODE_SYSTEMATIC_REGISTRATION)) {
            rrrBean.getFilteredRrrShareList().get(0).setRightHolderType(this.txtNotationText.getText());
            System.out.println("RIGHT HOLDER TYPE  " + rrrBean.getFilteredRrrShareList().get(0).getRightHolderType());
        }

//        }
    }

    private void openShareForm(RrrShareBean shareBean, RrrBean.RRR_ACTION rrrAction) {

        if (appService != null && appService.getRequestType().getCode().contentEquals(RequestTypeBean.CODE_SYSTEMATIC_REGISTRATION)) {
            SharePanel shareForm = new SharePanel(shareBean, rrrAction, appService, applicationBean, rrrBean);
            ShareFormListener listener = new ShareFormListener();
            shareForm.addPropertyChangeListener(SharePanel.UPDATED_RRR_SHARE, listener);
            getMainContentPanel().addPanel(shareForm, MainContentPanel.CARD_OWNERSHIP_SHARE, true);
        } else {
            SharePanel shareForm = new SharePanel(shareBean, rrrAction);
            ShareFormListener listener = new ShareFormListener();
            shareForm.addPropertyChangeListener(SharePanel.UPDATED_RRR_SHARE, listener);
            getMainContentPanel().addPanel(shareForm, MainContentPanel.CARD_OWNERSHIP_SHARE, true);
        }
    }

    private void openJointForm(RrrShareBean shareBean, RrrBean.RRR_ACTION rrrAction) {

        if (appService != null && appService.getRequestType().getCode().contentEquals(RequestTypeBean.CODE_SYSTEMATIC_REGISTRATION)) {
            SharePanel shareForm = new SharePanel(shareBean, rrrAction, appService, applicationBean, rrrBean, "Joint");
            ShareFormListener listener = new ShareFormListener();
            shareForm.addPropertyChangeListener(SharePanel.UPDATED_RRR_SHARE, listener);
            getMainContentPanel().addPanel(shareForm, MainContentPanel.CARD_OWNERSHIP_SHARE, true);
        } else {
            SharePanel shareForm = new SharePanel(shareBean, rrrAction);
            ShareFormListener listener = new ShareFormListener();
            shareForm.addPropertyChangeListener(SharePanel.UPDATED_RRR_SHARE, listener);
            getMainContentPanel().addPanel(shareForm, MainContentPanel.CARD_OWNERSHIP_SHARE, true);
        }
    }

    private boolean saveRrr() {
        
       
        if (rrrAction == RrrBean.RRR_ACTION.VIEW) {
            close();
            return true;
        } else if (rrrBean.validate(true, Default.class, OwnershipValidationGroup.class).size() < 1) {
            if (appService.getRequestTypeCode().contentEquals(RequestTypeBean.CODE_NEW_FREEHOLD)) {
                if (rrrBean.getLeaseConditions() == null || "".equals(rrrBean.getLeaseConditions())) {
                    MessageUtility.displayMessage(ClientMessage.CHECK_NOTNULL_LEASE_CONDITIONS);
                    return false;
                }
            }
            java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/administrative/Bundle"); // NOI18N
        
            if (appService.getRequestTypeCode().contentEquals(RequestTypeBean.CODE_SYSTEMATIC_REGISTRATION)) {
                if (rrrBean.getDateCommenced() == null || "".equals(rrrBean.getDateCommenced())) {
                    MessageUtility.displayMessage(ClientMessage.CHECK_NOTNULL_FIELDS,
                     new Object[]{bundle.getString("OwnershipPanel.jLabel5.text")}); 
                    return false;
                }
                if (rrrBean.getRotCode() == null || "".equals(rrrBean.getRotCode())) {
                    MessageUtility.displayMessage(ClientMessage.CHECK_NOTNULL_FIELDS,
                     new Object[]{bundle.getString("OwnershipPanel.labEstate.text")}); 
                    return false;
                }
                
                if (this.txtTerm.getText() == null || "".equals(this.txtTerm.getText())) {
                    MessageUtility.displayMessage(ClientMessage.CHECK_NOTNULL_FIELDS,
                     new Object[]{bundle.getString("OwnershipPanel.jLabel3.text")}); 
                    return false;
                }
                
                if (rrrBean.getCofoType() == null || "".equals(rrrBean.getCofoType())) {
                    MessageUtility.displayMessage(ClientMessage.CHECK_NOTNULL_FIELDS,
                     new Object[]{bundle.getString("OwnershipPanel.jLabel9.text")}); 
                    return false;
                }
                
                if (rrrBean.getLeaseConditions() == null || "".equals(rrrBean.getLeaseConditions())) {
                    MessageUtility.displayMessage(ClientMessage.CHECK_NOTNULL_LEASE_CONDITIONS);
                    return false;
                }
                rrrBean.setImprovementPremium(rrrBean.getImprovementPremiumValue());
                rrrBean.setStampDuty(rrrBean.getStampDutyValue());
                rrrBean.setYearlyRent(rrrBean.getYearlyRentValue());
            }

            firePropertyChange(UPDATED_RRR, null, rrrBean);
            close();
            return true;
        }
        return false;
    }

    private void saveRrrState() {
        MainForm.saveBeanState(rrrBean);
    }

    private void showCalendar(JFormattedTextField dateField) {
        CalendarForm calendar = new CalendarForm(null, true, dateField);
        calendar.setVisible(true);
    }

    @Override
    protected boolean panelClosing() {
        if (btnSave.isEnabled() && MainForm.checkSaveBeforeClose(rrrBean)) {
            return saveRrr();
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        rrrBean = CreateRrrBean();
        popUpShares = new javax.swing.JPopupMenu();
        menuAddShare = new javax.swing.JMenuItem();
        menuRemoveShare = new javax.swing.JMenuItem();
        menuChangeShare = new javax.swing.JMenuItem();
        menuViewShare = new javax.swing.JMenuItem();
        templates = new org.sola.clients.beans.administrative.LeaseConditionsTemplateSearchResultsListBean();
        cofoTypeListBean1 = new org.sola.clients.beans.referencedata.CofoTypeListBean();
        rotTypeListBean1 = new org.sola.clients.beans.referencedata.RotTypeListBean();
        zoneTypeListBean1 = new org.sola.clients.beans.referencedata.ZoneTypeListBean();
        headerPanel = new org.sola.clients.swing.ui.HeaderPanel();
        jToolBar2 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(7, 0), new java.awt.Dimension(7, 0), new java.awt.Dimension(7, 32767));
        jLabel1 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cofoNumber = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtimprPrem = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtAdvPayment = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtAnnualRent = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtRevPeriod = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtRegDatetime = new javax.swing.JFormattedTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnCommDate = new javax.swing.JButton();
        txtCommDatetime = new org.sola.clients.swing.common.controls.WatermarkDate();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtSignDatetime = new org.sola.clients.swing.common.controls.WatermarkDate();
        btnSignDate = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtTerm = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        labEstate = new javax.swing.JLabel();
        cbxEstate = new javax.swing.JComboBox();
        mainTabbedPanel = new javax.swing.JTabbedPane();
        jPanel15 = new javax.swing.JPanel();
        cbxIsPrimary = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        txtNotationText = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnAddJoint = new javax.swing.JButton();
        btnChangeShare = new javax.swing.JButton();
        btnAddShare = new javax.swing.JButton();
        btnRemoveShare = new javax.swing.JButton();
        btnViewShare = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableShares = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        jPanel1 = new javax.swing.JPanel();
        groupPanel2 = new org.sola.clients.swing.ui.GroupPanel();
        documentsPanel = createDocumentsPanel();
        conditionsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtConditionsText = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox();
        cbxConditionsTemplates = new javax.swing.JComboBox();
        btnInsertConditionsText = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        popUpShares.setName("popUpShares"); // NOI18N

        menuAddShare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/administrative/Bundle"); // NOI18N
        menuAddShare.setText(bundle.getString("OwnershipPanel.menuAddShare.text")); // NOI18N
        menuAddShare.setName("menuAddShare"); // NOI18N
        menuAddShare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddShareActionPerformed(evt);
            }
        });
        popUpShares.add(menuAddShare);

        menuRemoveShare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        menuRemoveShare.setText(bundle.getString("OwnershipPanel.menuRemoveShare.text")); // NOI18N
        menuRemoveShare.setName("menuRemoveShare"); // NOI18N
        menuRemoveShare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveShareActionPerformed(evt);
            }
        });
        popUpShares.add(menuRemoveShare);

        menuChangeShare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/change-share.png"))); // NOI18N
        menuChangeShare.setText(bundle.getString("OwnershipPanel.menuChangeShare.text")); // NOI18N
        menuChangeShare.setName("menuChangeShare"); // NOI18N
        menuChangeShare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuChangeShareActionPerformed(evt);
            }
        });
        popUpShares.add(menuChangeShare);

        menuViewShare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        menuViewShare.setText(bundle.getString("OwnershipPanel.menuViewShare.text")); // NOI18N
        menuViewShare.setName("menuViewShare"); // NOI18N
        menuViewShare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewShareActionPerformed(evt);
            }
        });
        popUpShares.add(menuViewShare);

        setHeaderPanel(headerPanel);
        setHelpTopic(bundle.getString("OwnershipPanel.helpTopic")); // NOI18N
        setName("Form"); // NOI18N

        headerPanel.setName("headerPanel"); // NOI18N
        headerPanel.setTitleText(bundle.getString("OwnershipPanel.headerPanel.titleText")); // NOI18N

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setName("jToolBar2"); // NOI18N

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSave.setText(bundle.getString("OwnershipPanel.btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar2.add(btnSave);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jToolBar2.add(jSeparator1);

        filler1.setName("filler1"); // NOI18N
        jToolBar2.add(filler1);

        jLabel1.setText(bundle.getString("OwnershipPanel.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jToolBar2.add(jLabel1);

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblStatus.setName("lblStatus"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${status.displayValue}"), lblStatus, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jToolBar2.add(lblStatus);

        jPanel14.setName("jPanel14"); // NOI18N
        jPanel14.setLayout(new java.awt.GridLayout(2, 5, 15, 15));

        jPanel5.setName("jPanel5"); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel2.setText(bundle.getString("OwnershipPanel.jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        cofoNumber.setText(bundle.getString("OwnershipPanel.cofoNumber.text")); // NOI18N
        cofoNumber.setName("cofoNumber"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 41, Short.MAX_VALUE))
            .addComponent(cofoNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cofoNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
        );

        jPanel14.add(jPanel5);

        jPanel6.setName("jPanel6"); // NOI18N

        jLabel11.setText(bundle.getString("OwnershipPanel.jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        txtimprPrem.setEditable(false);
        txtimprPrem.setName("txtimprPrem"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${improvementPremiumValue}"), txtimprPrem, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
            .addComponent(txtimprPrem)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(txtimprPrem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel14.add(jPanel6);

        jPanel7.setName("jPanel7"); // NOI18N

        jLabel4.setText(bundle.getString("OwnershipPanel.jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        txtAdvPayment.setEnabled(false);
        txtAdvPayment.setName("txtAdvPayment"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${stampDutyValue}"), txtAdvPayment, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtAdvPayment)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAdvPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel14.add(jPanel7);

        jPanel10.setName("jPanel10"); // NOI18N

        jLabel6.setText(bundle.getString("OwnershipPanel.jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        txtAnnualRent.setEnabled(false);
        txtAnnualRent.setName("txtAnnualRent"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${yearlyRentValue}"), txtAnnualRent, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtAnnualRent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnnualRentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtAnnualRent)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAnnualRent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel14.add(jPanel10);

        jPanel11.setName("jPanel11"); // NOI18N

        jLabel7.setText(bundle.getString("OwnershipPanel.jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        txtRevPeriod.setName("txtRevPeriod"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${reviewPeriod}"), txtRevPeriod, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtRevPeriod)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRevPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel14.add(jPanel11);

        jPanel3.setName("jPanel3"); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel13.setText(bundle.getString("OwnershipPanel.jLabel13.text")); // NOI18N
        jLabel13.setToolTipText(bundle.getString("OwnershipPanel.jLabel13.toolTipText")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        txtRegDatetime.setFormatterFactory(FormattersFactory.getInstance().getDateFormatterFactory());
        txtRegDatetime.setName("txtRegDatetime"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${registrationDate}"), txtRegDatetime, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txtRegDatetime)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRegDatetime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel14.add(jPanel3);

        jPanel8.setName("jPanel8"); // NOI18N

        jLabel5.setText(bundle.getString("OwnershipPanel.jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        btnCommDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/calendar.png"))); // NOI18N
        btnCommDate.setText(bundle.getString("OwnershipPanel.btnCommDate.text")); // NOI18N
        btnCommDate.setBorder(null);
        btnCommDate.setName("btnCommDate"); // NOI18N
        btnCommDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCommDateActionPerformed(evt);
            }
        });

        txtCommDatetime.setName("txtCommDatetime"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${dateCommenced}"), txtCommDatetime, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(txtCommDatetime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCommDate))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCommDatetime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCommDate)))
        );

        jPanel14.add(jPanel8);

        jPanel9.setName("jPanel9"); // NOI18N

        jLabel8.setText(bundle.getString("OwnershipPanel.jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        txtSignDatetime.setName("txtSignDatetime"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${dateSigned}"), txtSignDatetime, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        btnSignDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/calendar.png"))); // NOI18N
        btnSignDate.setText(bundle.getString("OwnershipPanel.btnSignDate.text")); // NOI18N
        btnSignDate.setBorder(null);
        btnSignDate.setName("btnSignDate"); // NOI18N
        btnSignDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(txtSignDatetime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSignDate))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSignDatetime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSignDate)))
        );

        jPanel14.add(jPanel9);

        jPanel12.setName("jPanel12"); // NOI18N

        jLabel3.setText(bundle.getString("OwnershipPanel.jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        txtTerm.setName("txtTerm"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${term}"), txtTerm, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 55, Short.MAX_VALUE))
            .addComponent(txtTerm)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel14.add(jPanel12);

        jPanel13.setName("jPanel13"); // NOI18N

        labEstate.setText(bundle.getString("OwnershipPanel.labEstate.text")); // NOI18N
        labEstate.setName("labEstate"); // NOI18N

        cbxEstate.setName("cbxEstate"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${rotTypeListBean}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rotTypeListBean1, eLProperty, cbxEstate);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${rotType}"), cbxEstate, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(labEstate, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cbxEstate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(labEstate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxEstate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel14.add(jPanel13);

        mainTabbedPanel.setName("mainTabbedPanel"); // NOI18N

        jPanel15.setName("jPanel15"); // NOI18N

        cbxIsPrimary.setText(bundle.getString("OwnershipPanel.cbxIsPrimary.text")); // NOI18N
        cbxIsPrimary.setToolTipText(bundle.getString("OwnershipPanel.cbxIsPrimary.toolTipText")); // NOI18N
        cbxIsPrimary.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        cbxIsPrimary.setName("cbxIsPrimary"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${primary}"), cbxIsPrimary, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel15.setText(bundle.getString("OwnershipPanel.jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        txtNotationText.setName("txtNotationText"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${notation.notationText}"), txtNotationText, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new java.awt.GridLayout(2, 1, 0, 20));

        jPanel2.setName("jPanel2"); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnAddJoint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/joint.png"))); // NOI18N
        btnAddJoint.setText(bundle.getString("OwnershipPanel.btnAddJoint.text")); // NOI18N
        btnAddJoint.setFocusable(false);
        btnAddJoint.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnAddJoint.setName(bundle.getString("OwnershipPanel.btnAddJoint.name")); // NOI18N
        btnAddJoint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddJoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddJointActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAddJoint);

        btnChangeShare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnChangeShare.setText(bundle.getString("OwnershipPanel.btnChangeShare.text")); // NOI18N
        btnChangeShare.setName("btnChangeShare"); // NOI18N
        btnChangeShare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeShareActionPerformed(evt);
            }
        });
        jToolBar1.add(btnChangeShare);

        btnAddShare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnAddShare.setText(bundle.getString("OwnershipPanel.btnAddShare.text")); // NOI18N
        btnAddShare.setName("btnAddShare"); // NOI18N
        btnAddShare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddShareActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAddShare);

        btnRemoveShare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveShare.setText(bundle.getString("OwnershipPanel.btnRemoveShare.text")); // NOI18N
        btnRemoveShare.setName("btnRemoveShare"); // NOI18N
        btnRemoveShare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveShareActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemoveShare);

        btnViewShare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        btnViewShare.setText(bundle.getString("OwnershipPanel.btnViewShare.text")); // NOI18N
        btnViewShare.setFocusable(false);
        btnViewShare.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnViewShare.setName("btnViewShare"); // NOI18N
        btnViewShare.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnViewShare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewShareActionPerformed(evt);
            }
        });
        jToolBar1.add(btnViewShare);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableShares.setComponentPopupMenu(popUpShares);
        tableShares.setName("tableShares"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${filteredRrrShareList}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, eLProperty, tableShares);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${rightHolderList}"));
        columnBinding.setColumnName("Right Holder List");
        columnBinding.setColumnClass(org.sola.clients.beans.controls.SolaList.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${share}"));
        columnBinding.setColumnName("Share");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${share}${rightHolderList}${rightHolderType}"));
        columnBinding.setColumnName("Share}${right Holder List}${right Holder Type");
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${selectedShare}"), tableShares, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        tableShares.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSharesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableShares);
        if (tableShares.getColumnModel().getColumnCount() > 0) {
            tableShares.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("OwnershipPanel.tableShares.columnModel.title0")); // NOI18N
            tableShares.getColumnModel().getColumn(0).setCellRenderer(new TableCellListRenderer("getName", "getLastName"));
            tableShares.getColumnModel().getColumn(1).setMinWidth(80);
            tableShares.getColumnModel().getColumn(1).setPreferredWidth(80);
            tableShares.getColumnModel().getColumn(1).setMaxWidth(80);
            tableShares.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("OwnershipPanel.tableShares.columnModel.title1")); // NOI18N
            tableShares.getColumnModel().getColumn(2).setMinWidth(30);
            tableShares.getColumnModel().getColumn(2).setPreferredWidth(30);
            tableShares.getColumnModel().getColumn(2).setMaxWidth(50);
            tableShares.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("OwnershipPanel.tableShares.columnModel.title2")); // NOI18N
            tableShares.getColumnModel().getColumn(2).setCellRenderer(new RightHolderType());
        }

        groupPanel1.setName("groupPanel1"); // NOI18N
        groupPanel1.setTitleText(bundle.getString("OwnershipPanel.groupPanel1.titleText")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel2);

        jPanel1.setName("jPanel1"); // NOI18N

        groupPanel2.setName("groupPanel2"); // NOI18N
        groupPanel2.setTitleText(bundle.getString("OwnershipPanel.groupPanel2.titleText")); // NOI18N

        documentsPanel.setName(bundle.getString("OwnershipPanel.documentsPanel.name")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(documentsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(documentsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel1);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtNotationText))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxIsPrimary, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNotationText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxIsPrimary))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainTabbedPanel.addTab(bundle.getString("OwnershipPanel.jPanel15.TabConstraints.tabTitle"), jPanel15); // NOI18N

        conditionsPanel.setName("conditionsPanel"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        txtConditionsText.setColumns(20);
        txtConditionsText.setRows(5);
        txtConditionsText.setName("txtConditionsText"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${leaseConditions}"), txtConditionsText, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(txtConditionsText);

        jComboBox1.setName("jComboBox1"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cofoTypeList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, cofoTypeListBean1, eLProperty, jComboBox1);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${cofoTypeBean}"), jComboBox1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbxConditionsTemplates.setName("cbxConditionsTemplates"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${templates}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, templates, eLProperty, cbxConditionsTemplates);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, templates, org.jdesktop.beansbinding.ELProperty.create("${selectedTemplate}"), cbxConditionsTemplates, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        btnInsertConditionsText.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/down.png"))); // NOI18N
        btnInsertConditionsText.setText(bundle.getString("OwnershipPanel.btnInsertConditionsText.text")); // NOI18N
        btnInsertConditionsText.setFocusable(false);
        btnInsertConditionsText.setName("btnInsertConditionsText"); // NOI18N
        btnInsertConditionsText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertConditionsTextActionPerformed(evt);
            }
        });

        jLabel9.setText(bundle.getString("OwnershipPanel.jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setText(bundle.getString("OwnershipPanel.jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        javax.swing.GroupLayout conditionsPanelLayout = new javax.swing.GroupLayout(conditionsPanel);
        conditionsPanel.setLayout(conditionsPanelLayout);
        conditionsPanelLayout.setHorizontalGroup(
            conditionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(conditionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(conditionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(conditionsPanelLayout.createSequentialGroup()
                        .addGroup(conditionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(conditionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(conditionsPanelLayout.createSequentialGroup()
                                .addComponent(cbxConditionsTemplates, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInsertConditionsText)))
                        .addGap(0, 400, Short.MAX_VALUE)))
                .addContainerGap())
        );
        conditionsPanelLayout.setVerticalGroup(
            conditionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, conditionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(conditionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(conditionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxConditionsTemplates, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsertConditionsText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainTabbedPanel.addTab(bundle.getString("OwnershipPanel.conditionsPanel.TabConstraints.tabTitle"), new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif")), conditionsPanel); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(mainTabbedPanel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(mainTabbedPanel)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void tableSharesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSharesMouseClicked
        if (evt.getClickCount() > 1) {
            viewShare();
        }
    }//GEN-LAST:event_tableSharesMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveRrr();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddShareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddShareActionPerformed

//       if (    (this.rrrBean.getFilteredRrrShareList()!= null && this.rrrBean.getFilteredRrrShareList().size()>0)
//               &&(this.rrrBean.getFilteredRrrShareList().get(0).getShareType()!= null && this.rrrBean.getFilteredRrrShareList().size()==1&&this.rrrBean.getFilteredRrrShareList().get(0).getShareType().contentEquals("Sole Land Holder"))
//               && rrrAction.equals(RrrBean.RRR_ACTION.NEW)){
//          MessageUtility.displayMessage(ClientMessage.ONLY_ONE_SOLEOWNER);
//        }
//        else {
        addShare();
//    }  
    }//GEN-LAST:event_btnAddShareActionPerformed

    private void btnRemoveShareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveShareActionPerformed
        removeShare();
    }//GEN-LAST:event_btnRemoveShareActionPerformed

    private void btnChangeShareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeShareActionPerformed
        changeShare();
    }//GEN-LAST:event_btnChangeShareActionPerformed

    private void btnViewShareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewShareActionPerformed
        viewShare();
    }//GEN-LAST:event_btnViewShareActionPerformed

    private void menuAddShareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddShareActionPerformed
        addShare();
    }//GEN-LAST:event_menuAddShareActionPerformed

    private void menuRemoveShareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveShareActionPerformed
        removeShare();
    }//GEN-LAST:event_menuRemoveShareActionPerformed

    private void menuChangeShareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuChangeShareActionPerformed
        changeShare();
    }//GEN-LAST:event_menuChangeShareActionPerformed

    private void menuViewShareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuViewShareActionPerformed
        viewShare();
    }//GEN-LAST:event_menuViewShareActionPerformed

    private void btnAddJointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddJointActionPerformed
        addJoint();
    }//GEN-LAST:event_btnAddJointActionPerformed

    private void btnSignDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignDateActionPerformed
        showCalendar(txtSignDatetime);
    }//GEN-LAST:event_btnSignDateActionPerformed

    private void btnCommDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCommDateActionPerformed
        showCalendar(txtCommDatetime);
    }//GEN-LAST:event_btnCommDateActionPerformed

    private void btnInsertConditionsTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertConditionsTextActionPerformed
        if (templates.getSelectedTemplate() != null && templates.getSelectedTemplate().getId() != null) {
            LeaseConditionsTemplateBean template = LeaseConditionsTemplateBean
                    .getLeaseConditionsTemplate(templates.getSelectedTemplate().getId());
            if (template != null) {
                txtConditionsText.setText(template.getTemplateText());
            }
        }
    }//GEN-LAST:event_btnInsertConditionsTextActionPerformed

    private void txtAnnualRentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnnualRentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnnualRentActionPerformed

    private void changeShare() {

        this.rrrBean.getFilteredRrrShareList().get(0).setRightHolderType("Share");
        System.out.println("LISTA SHARE   " + rrrBean.getFilteredRrrShareList().get(0).getShare());
        if (this.rrrBean.getFilteredRrrShareList().size() == 1 && this.rrrBean.getFilteredRrrShareList().get(0).getShare().contains("1/1")) {
            this.txtNotationText.setText(this.rrrBean.UNDEVIDED_SHARES);
        } else {
            this.txtNotationText.setText(this.rrrBean.DEFINED_SHARES);
        }
        if (rrrBean.getSelectedShare() != null) {
            openShareForm(rrrBean.getSelectedShare(), RrrBean.RRR_ACTION.VARY);
        }

    }

    private void removeShare() {
        if (rrrBean.getSelectedShare() != null
                && MessageUtility.displayMessage(ClientMessage.CONFIRM_DELETE_RECORD) == MessageUtility.BUTTON_ONE) {
            rrrBean.removeSelectedRrrShare();
        }
    }

    private void addShare() {
        if (appService != null && appService.getRequestType().getCode().contentEquals(RequestTypeBean.CODE_SYSTEMATIC_REGISTRATION)) {
            this.rrrBean.getFilteredRrrShareList().get(0).setRightHolderType("Share");
            System.out.println("LISTA SHARE   " + rrrBean.getFilteredRrrShareList().get(0).getShare());
            this.txtNotationText.setText(this.rrrBean.DEFINED_SHARES);
        }
        openShareForm(null, RrrBean.RRR_ACTION.NEW);
    }

    private void addJoint() {
        Short numden = 1;
        System.out.println("LISTA SHARE   " + rrrBean.getFilteredRrrShareList().get(0).getShare());
        rrrBean.getFilteredRrrShareList().get(0).setDenominator(numden);
        rrrBean.getFilteredRrrShareList().get(0).setNominator(numden);
        this.rrrBean.getFilteredRrrShareList().get(0).setRightHolderType("Joint");
        System.out.println("LISTA SHARE   " + rrrBean.getFilteredRrrShareList().get(0).getShare());
        this.txtNotationText.setText(this.rrrBean.JOINT);
        this.txtNotationText.setEditable(false);

        if (rrrBean.getFilteredRrrShareList().size() > 1) {
            MessageUtility.displayMessage(ClientMessage.ONLY_ONE_JOINTOWNER);
            return;
        } else {
            openJointForm(rrrBean.getFilteredRrrShareList().get(0), RrrBean.RRR_ACTION.NEW);
        }
    }

    private void viewShare() {
        if (rrrBean.getSelectedShare() != null) {
            openShareForm(rrrBean.getSelectedShare(), RrrBean.RRR_ACTION.VIEW);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddJoint;
    private javax.swing.JButton btnAddShare;
    private javax.swing.JButton btnChangeShare;
    private javax.swing.JButton btnCommDate;
    private javax.swing.JButton btnInsertConditionsText;
    private javax.swing.JButton btnRemoveShare;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSignDate;
    private javax.swing.JButton btnViewShare;
    private javax.swing.JComboBox cbxConditionsTemplates;
    private javax.swing.JComboBox cbxEstate;
    private javax.swing.JCheckBox cbxIsPrimary;
    private javax.swing.JLabel cofoNumber;
    private org.sola.clients.beans.referencedata.CofoTypeListBean cofoTypeListBean1;
    private javax.swing.JPanel conditionsPanel;
    private org.sola.clients.swing.desktop.source.DocumentsManagementExtPanel documentsPanel;
    private javax.swing.Box.Filler filler1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel2;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel labEstate;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTabbedPane mainTabbedPanel;
    private javax.swing.JMenuItem menuAddShare;
    private javax.swing.JMenuItem menuChangeShare;
    private javax.swing.JMenuItem menuRemoveShare;
    private javax.swing.JMenuItem menuViewShare;
    private javax.swing.JPopupMenu popUpShares;
    private org.sola.clients.beans.referencedata.RotTypeListBean rotTypeListBean1;
    private org.sola.clients.beans.administrative.RrrBean rrrBean;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableShares;
    private org.sola.clients.beans.administrative.LeaseConditionsTemplateSearchResultsListBean templates;
    private javax.swing.JTextField txtAdvPayment;
    private javax.swing.JTextField txtAnnualRent;
    private org.sola.clients.swing.common.controls.WatermarkDate txtCommDatetime;
    private javax.swing.JTextArea txtConditionsText;
    private javax.swing.JTextField txtNotationText;
    private javax.swing.JFormattedTextField txtRegDatetime;
    private javax.swing.JTextField txtRevPeriod;
    private org.sola.clients.swing.common.controls.WatermarkDate txtSignDatetime;
    private javax.swing.JTextField txtTerm;
    private javax.swing.JTextField txtimprPrem;
    private org.sola.clients.beans.referencedata.ZoneTypeListBean zoneTypeListBean1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
