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
/**
 *
 *
 */
package org.sola.clients.swing.desktop.administrative;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.administrative.DisputeBean;
import org.sola.clients.beans.validation.ValidationResultBean;
import org.sola.clients.swing.ui.cadastre.ParcelDialog;
import org.sola.clients.beans.administrative.DisputePartyBean;
import org.sola.clients.beans.administrative.DisputesCommentsBean;
import org.sola.clients.swing.desktop.cadastre.SearchParcelDialog;
import org.sola.clients.beans.application.ApplicationServiceBean;
import org.sola.clients.beans.referencedata.DisputeCategoryListBean;
import org.sola.clients.beans.referencedata.DisputeTypeListBean;
import org.sola.clients.beans.referencedata.OtherAuthoritiesListBean;
import org.sola.clients.beans.digitalarchive.DocumentBean;
import org.sola.clients.beans.party.PartyBean;
import org.sola.clients.beans.source.SourceBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.desktop.MainForm;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.common.WindowUtility;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JFormattedTextField;
import org.sola.clients.beans.administrative.*;
import org.sola.clients.swing.common.laf.LafManager;
import org.sola.clients.swing.desktop.party.PartyPanelForm;
import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.clients.swing.ui.administrative.DisputeSearchPanel;
import org.sola.clients.swing.ui.reports.ReportViewerForm;
import net.sf.jasperreports.engine.JasperPrint;
import org.sola.clients.beans.application.ApplicationBean;
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.source.SourceListBean;
import org.sola.clients.swing.ui.source.AddDocumentForm;
import org.sola.clients.swing.ui.source.DocumentsPanel;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.party.PartySummaryBean;
import org.sola.clients.reports.ReportManager;
import org.sola.clients.swing.common.controls.BrowseControlListener;
import org.sola.clients.swing.common.controls.CalendarForm;
import org.sola.clients.swing.common.utils.BindingTools;
import org.sola.clients.swing.desktop.party.DispPartyForm;
import org.sola.clients.swing.desktop.party.PartySearchPanelForm;
import org.sola.clients.swing.desktop.party.QuickSearchPartyForm;
import org.sola.clients.swing.desktop.source.DocumentsManagementExtPanel;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.administrative.DisputeCommentsTO;
import org.sola.webservices.transferobjects.administrative.DisputeTO;
import org.sola.webservices.transferobjects.administrative.DisputePartyTO;
import org.sola.webservices.transferobjects.casemanagement.ApplicationTO;

public class DisputePanelForm extends ContentPanel {

    public static final String SELECT_PARTY_PROPERTY = "selectParty";
    public static final String CREATE_NEW_PARTY_PROPERTY = "createNewParty";
    public static final String EDIT_PARTY_PROPERTY = "editParty";
    public static final String REMOVE_PARTY_PROPERTY = "removeParty";
    public static final String VIEW_PARTY_PROPERTY = "viewParty";
    public static final String VIEW_DOCUMENT = "viewDocument";
    public static final String EDIT_DOCUMENT = "editDocument";
    static String complainantString = "Complainant";
    static String respondentString = "Respondent";
    static String disputeString = "Dispute";
    static String courtProcessString = "Court Process";
    private String typeofCase;
    private ApplicationServiceBean applicationService;
    private CadastreObjectBean cadastreObjectBean;
    private DisputeSearchResultBean disputeSearchResultBean;
    public DocumentBean archiveDocument;
    private PartySummaryBean partySummaryBean;
    private String disputeID;
    private String applicationID;
    private String serviceID;
    private String partyRole;
    private String partyName;
    private String user;
    private boolean readOnly = false;
    private SourceBean document;
    private DisputeSearchPanel disputeSearchPanel;
    private DisputeSearchDialog disputeSearchDialog;
    private PartyPanelForm partyPanelForm;
    private AddDocumentForm addDocumentForm;
    private ApplicationBean applicationBean;
    private boolean allowAddingOfNewDocuments = true;
    private SolaList<SourceBean> sourceList;
    private DisputeBean disputeBean;
    java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/administrative/Bundle");
    private boolean toPrintConfirmation = false;
    ;
    
    public DisputePanelForm() {
        initComponents();
        switchModeRole(true);
        clearForm();
    }

    public DisputePanelForm(ApplicationBean appBean, ApplicationServiceBean appService) {

        this.applicationBean = appBean;
        this.applicationID = appBean.getId();
        this.serviceID = appService.getId();
        this.disputeBean1 = this.getDispute(this.serviceID);
        if (this.disputeBean1 != null) {
            this.disputeBean1.setApplicationId(applicationID);
            this.disputeBean1.setServiceId(this.serviceID);
            this.disputeID = this.disputeBean1.getNr();
        
        } else {
            this.disputeBean1 = new DisputeBean();
            this.disputeBean1.setApplicationId(applicationID);
            this.disputeBean1.setServiceId(this.serviceID);
        }

        initComponents();
        this.btnsearchDispute.setVisible(false);
        this.btnnewDispute.setVisible(false);
        this.btnnewDisputeComment.setVisible(false);
        this.btnAddComment1.setVisible(false);
        this.btnRemoveDisputeComment.setVisible(false);
        this.btnComplete.setVisible(false);

        this.pnlHeader.setTitleText(String.format("%s",
                String.format(resourceBundle.getString("PropertyPanel.applicationInfo.Text"),
                appService.getRequestType().getDisplayValue(),
                appBean.getNr())));


        if (this.disputeBean1.getNr() == null) {
            this.dbxdisputeType.setSelectedIndex(-1);
        }

        if (this.disputeBean1.getCaseType() != null) {
                switchModeRole(true);
                this.txtdisputeNumber.setText(this.disputeBean1.getNr());
           
        }
        LoadCommentsList();

            }
public DisputePanelForm(String disputeNr) {
            
        
        this.disputeBean1 = this.getDisputeByNr(disputeNr);
        if (this.disputeBean1 != null) {
            this.disputeID = this.disputeBean1.getNr();
        
        } else {
            this.disputeBean1 = new DisputeBean();
        }
        this.applicationID=this.disputeBean1.getApplicationId();
        this.serviceID = this.disputeBean1.getServiceId();
        ApplicationTO appTO = WSManager.getInstance().getCaseManagementService().getApplication(this.applicationID);
        this.applicationBean = TypeConverters.TransferObjectToBean(appTO, ApplicationBean.class, null);
       
        initComponents();
        this.btnsearchDispute.setVisible(false);
        this.btnnewDispute.setVisible(false);
        this.btnnewDisputeComment.setVisible(false);
        this.btnAddComment1.setVisible(false);
        this.btnRemoveDisputeComment.setVisible(false);
        this.btnComplete.setVisible(false);
         this.pnlHeader.setTitleText(String.format("%s",
                String.format(resourceBundle.getString("PropertyPanel.applicationInfo.Text"),
                "Dispute",
                this.applicationBean.getNr())));

        if (this.disputeBean1.getNr() == null) {
            this.dbxdisputeType.setSelectedIndex(-1);
        }

        if (this.disputeBean1.getCaseType() != null) {
                switchModeRole(true);
                this.txtdisputeNumber.setText(this.disputeBean1.getNr());
        }
        LoadCommentsList();

            }

    
    public DisputePanelForm(DisputeBean dispute) {
        this.disputeBean1 = dispute;
        initComponents();
        setupDisputeBean(disputeBean);
        this.btnsearchDispute.setVisible(false);
        this.btnnewDispute.setVisible(false);
        this.btnnewDisputeComment.setVisible(false);
        this.btnAddComment1.setVisible(false);
        this.btnRemoveDisputeComment.setVisible(false);
        this.btnComplete.setVisible(false);
        switchModeRole(true);
    }

    
    private void setupDisputeBean(DisputeBean disputeBean) {
        if (disputeBean != null) {
            this.disputeBean = disputeBean;
        } else {
            this.disputeBean = new DisputeBean();
        }
        disputeCategory.setExcludedCodes(this.disputeBean.getDisputeCategoryCode());
    }

    private void LoadCommentsList() {
        disputeBean1.getDisputeCommentsList().addObservableListListener(new ObservableListListener() {

            @Override
            public void listElementsAdded(ObservableList ol, int i, int i1) {
                disputeBean1.updateCheckList(disputeBean1.getDisputeCommentsList());
            }

            @Override
            public void listElementsRemoved(ObservableList list, int index, List oldElements) {
                disputeBean1.updateCheckList(disputeBean1.getDisputeCommentsList());
            }

            @Override
            public void listElementReplaced(ObservableList list, int index, Object oldElement) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void listElementPropertyChanged(ObservableList list, int index) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    private void switchModeRole(boolean isDispute) {
            typeofCase = disputeString;
            this.txtdisputeNumber.setText(this.disputeID);
            txtdisputeNumber.setEditable(false);
            txtdisputeNumber.setEnabled(false);
            disputeBean1.setCaseType(typeofCase);
            tabContainer.setSelectedIndex(0);
            this.dbxdisputeCategory.setSelectedIndex(0);
            this.dbxdisputeCategory.setEnabled(false);
           
    }

//    private void RefreshScreen() {
//        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/administrative/Bundle");
//        disputeID = disputeBean1.getNr();
//        if (disputeID != null) {
//            pnlHeader.setTitleText(bundle.getString("DisputePanelForm.pnlHeader.titleText") + " #" + disputeID);
//            disputesCommentsBean1.setDisputeNr(disputeID);
//            LoadCommentsList();
//
//            disputeBean1.getDisputeCommentsList();
//
//            setupDisputeBean(disputeBean1);
//        }
//    }

    private void NewComments() {
        disputesCommentsBean1 = new DisputesCommentsBean();
        txtDisputeComments.setText("");
        dbxotherAuthorities1.setSelectedIndex(-1);
        saveDisputeCommentsState();
    }

    private DisputeBean createDisputeBean() {
        if (this.serviceID != null) {
            this.disputeBean1 = this.getDispute(serviceID);
        }

        if (disputeBean1 == null) {
            disputeBean1 = new DisputeBean();
            this.disputeBean1.setApplicationId(applicationID);
            this.disputeBean1.setServiceId(this.serviceID);

        }
      
        return disputeBean1;
    }

    private DisputesCommentsBean createCommentsBean() {
        DisputeCommentsTO disputeCommentsTO = WSManager.getInstance().getAdministrative().getDisputeCommentsByDispute(this.disputeBean1.getNr());
        disputesCommentsBean1 = TypeConverters.TransferObjectToBean(disputeCommentsTO, DisputesCommentsBean.class, null);

        if (disputesCommentsBean1 == null) {
            disputesCommentsBean1 = new DisputesCommentsBean();
        }

         return disputesCommentsBean1;
    }

    private void saveDisputeState() {
        if (this.tabContainer.getSelectedComponent()!= this.tabParty) {
         MainForm.saveBeanState(disputeBean1);
        }
    }
    private void saveDisputeCommentsState() {
        MainForm.saveBeanState(disputesCommentsBean1);
    }

    private void CustomizeDisputeScreen() {
        if (disputeBean1 != null && !disputeBean1.isNew()) {
//           RefreshScreen();
        }

    }

    private void completeDispute(String statusCode) {
        if (disputeID != null && disputeBean1.getId() != null) {
            disputeBean1.setStatusCode(statusCode);
            disputeBean1.saveDispute();
            MessageUtility.displayMessage(ClientMessage.DISPUTE_CLOSED);
        }

    }

    private void addComment() {
        if (disputeID != null && !disputeID.equals("")) {
            SaveComments(true);
        } else {
            MessageUtility.displayMessage(ClientMessage.DISPUTE_SAVE_FIRST);
        }
    }

    private void saveDispute(final boolean showMessage, final boolean closeOnSave) {

        if (disputeBean1.validate(true).size() > 0) {
            return;
        }
       
        SolaTask<Void, Void> t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_SAVING));
                if (disputeID != null && !disputeID.equals("")) {
                    disputeBean1.saveDispute();
                } else {
                    disputeBean1.createDispute();
                    toPrintConfirmation = true;
                }

                if (closeOnSave) {
                    close();
                }

                return null;
            }

            @Override
            public void taskDone() {
                if (showMessage) {
                    CustomizeDisputeScreen();
                    MessageUtility.displayMessage(ClientMessage.DISPUTE_SAVED);
                }

                   saveDisputeState();
                
                SaveComments(false);

                if (toPrintConfirmation) {
                    printConfirmation();
                }

            }
        };
        TaskManager.getInstance().runTask(t);
            
    }

    private void AddPlot() {
        ParcelDialog form = new ParcelDialog(null, true, null, true);
        WindowUtility.centerForm(form);
        form.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(ParcelDialog.SELECTED_PARCEL)) {
                    //cadastreObjectBean1.addAddress((AddressBean) evt.getNewValue());
                }
            }
        });
        form.setVisible(true);
    }

    private void SaveComments(final boolean showMessage) {

        if (disputesCommentsBean1.validate(true).size() > 0) {
            return;
        }

        SolaTask<Void, Void> t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_SAVING));

                if (disputeID != null && !disputeID.equals("")) {
                    disputesCommentsBean1.setDisputeNr(disputeID);
                    disputesCommentsBean1.setComments(txtDisputeComments.getText());
                    disputesCommentsBean1.saveDisputeComments();
                    disputeBean1.addDisputeComment(disputesCommentsBean1);
                }
                return null;
            }

            @Override
            public void taskDone() {
                if (showMessage) {
                    //CustomizeDisputeScreen();
                    MessageUtility.displayMessage(ClientMessage.DISPUTE_COMMENTS_SAVED);
                }
                saveDisputeCommentsState();
            }
        };
        TaskManager.getInstance().runTask(t);

    }
     
    
    
    
    /**
     * Returns {@link DisputeBean} by first and last name part.
     */
    private DisputeBean getDispute(String service) {

        DisputeTO disputeTO = WSManager.getInstance().getAdministrative().getDisputeByService(service);
        disputeBean1 = TypeConverters.TransferObjectToBean(disputeTO, DisputeBean.class, null);
        return disputeBean1;
    }
    
    
     /**
     * Returns {@link DisputeBean} by first and last name part.
     */
    private DisputeBean getDisputeByNr(String disputeNr) {

        DisputeTO disputeTO = WSManager.getInstance().getAdministrative().getDisputeByNr(disputeNr);
        disputeBean1 = TypeConverters.TransferObjectToBean(disputeTO, DisputeBean.class, null);
        return disputeBean1;
    }
    
    private void removeComment() {
        disputeBean1.removeSelectedComment();
    }

    public SourceBean getDocument() {
        if (document == null) {
            document = new SourceBean();
        }
        return document;
    }

    public boolean validateDocument(boolean showMessage) {
        return getDocument().validate(showMessage).size() < 1;
    }

    private void SearchPlot() {
        SearchParcelDialog form = new SearchParcelDialog(null, true);
        WindowUtility.centerForm(form);
        form.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(SearchParcelDialog.SELECTED_PARCEL)) {

                    CadastreObjectBean cadastreObject = (CadastreObjectBean) evt.getNewValue();

                    disputeBean1.addChosenPlot(cadastreObject);
                }
            }
        });
        form.setVisible(true);
    }

    private void searchParty() {
        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_PERSON_SEARCHING));
                partySearchResult.search(partySearchParams);
                return null;
            }

            @Override
            public void taskDone() {
                if (partySearchResult.getPartySearchResults().size() > 100) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_TOO_MANY_RESULTS, new String[]{"100"});
                } else if (partySearchResult.getPartySearchResults().size() < 1) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_NO_RESULTS);
                }
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    

    private void clearForm() {
        dbxdisputeCategory.setSelectedIndex(0);
        dbxdisputeType.setSelectedIndex(-1);
        dbxotherAuthorities1.setSelectedIndex(-1);
    }

    
 
    private void addDisputeParty() {
         String partyName;
         String partyId;
         String partyRole;
         if (partySearch.getSelectedElement() != null) {
            partySummaryBean = (PartySummaryBean) partySearch.getSelectedElement();
            partyName = partySummaryBean.getFullName();
            partyId = partySummaryBean.getId();
        } else {
            MessageUtility.displayMessage(ClientMessage.CHECK_NOTNULL_NAME);
            return;
        }
        if (cbxPartyRole.getSelectedItem() != null) {
            partyRole = cbxPartyRole.getSelectedItem().toString();
        } else {
            MessageUtility.displayMessage(ClientMessage.CHECK_NOTNULL_FIELDS);
            return;
        }
        
        disputeBean1.addDisputeParty(partyId, this.disputeID , partyRole, partyName);
    }

    private void searchDispute() {

        if (disputeSearchDialog != null) {
            disputeSearchDialog.dispose();
        }

        disputeSearchDialog = new DisputeSearchDialog(null, null, true);
        disputeSearchDialog.setLocationRelativeTo(this);

        DisputeSearchFormListener listener = new DisputeSearchFormListener();
        disputeSearchDialog.addPropertyChangeListener(DisputeSearchResultListBean.SELECTED_DISPUTE_SEARCH_RESULT_PROPERTY, listener);
        disputeSearchDialog.setVisible(true);
        disputeSearchDialog.removePropertyChangeListener(DisputeSearchResultListBean.SELECTED_DISPUTE_SEARCH_RESULT_PROPERTY, listener);
//        RefreshScreen();
    }

    private DisputeCategoryListBean createDisputeCategory() {
        if (disputeCategory == null) {
            String categoryCode = null;
            if (disputeBean1 != null && disputeBean1.getDisputeCategoryCode() != null) {
                categoryCode = disputeBean1.getDisputeCategoryCode();
            }
            disputeCategory = new DisputeCategoryListBean();
        }

        return disputeCategory;
    }

    private DisputeTypeListBean createDisputeType() {
        if (disputeType == null) {
            String typeCode = null;
            if (disputeBean1 != null && disputeBean1.getDisputeTypeCode() != null) {
                typeCode = disputeBean1.getDisputeTypeCode();
            }
            disputeType = new DisputeTypeListBean();
        }

        return disputeType;
    }

 
    private OtherAuthoritiesListBean createOtherAuthorities() {
        if (otherAuthorities == null) {
            String authoritiesCode = null;
            if (disputesCommentsBean1 != null && disputesCommentsBean1.getOtherAuthoritiesCode() != null) {
                authoritiesCode = disputesCommentsBean1.getOtherAuthoritiesCode();
            }
            otherAuthorities = new OtherAuthoritiesListBean();
        }
        otherAuthorities.loadList(true);   
        return otherAuthorities;
    }

    private class DisputeSearchFormListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {

            if (evt.getNewValue() != null) {
                disputeSearchResultBean = (DisputeSearchResultBean) evt.getNewValue();

                disputeBean1.assignDispute(disputeSearchResultBean);
            }
        }
    }

    /**
     * Attach file to the selected source.
     */
    private void attachDocument(PropertyChangeEvent e) {
        SourceBean document = null;
        if (e.getPropertyName().equals(AddDocumentForm.SELECTED_SOURCE)
                && e.getNewValue() != null) {
            document = (SourceBean) e.getNewValue();
            document.setReferenceNr(disputeID);
            document.save();
        }
    }

    /**
     * Adds new source into the list.
     */
    private void addDocument(String RefNo) {
        if (addDocumentForm != null) {
            addDocumentForm.dispose();
        }

        PropertyChangeListener listener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent e) {
                attachDocument(e);
            }
        };


        addDocumentForm = new AddDocumentForm(applicationBean, null, true);
        addDocumentForm.setLocationRelativeTo(this);
        addDocumentForm.addPropertyChangeListener(SourceListBean.SELECTED_SOURCE_PROPERTY, listener);
        addDocumentForm.allowAddingOfNewDocuments(allowAddingOfNewDocuments);
        addDocumentForm.setVisible(true);
        addDocumentForm.removePropertyChangeListener(SourceListBean.SELECTED_SOURCE_PROPERTY, listener);
    }

      private DocumentsManagementExtPanel createDocumentsPanel() {
        if (disputeBean1 == null) {
            disputeBean1 = new DisputeBean();
        }
        if (applicationBean == null) {
            applicationBean = new ApplicationBean();
        }

        boolean allowEdit = true;
        DocumentsManagementExtPanel panel = new DocumentsManagementExtPanel(
                disputeBean1.getSourceList(), applicationBean, allowEdit);
        return panel;
    }

    /**
     * Opens {@link ReportViewerForm} to display report.
     */
    private void showReport(JasperPrint report) {
        ReportViewerForm form = new ReportViewerForm(report);
        form.setLocationRelativeTo(this);
        form.setVisible(true);
    }

    private void printConfirmation() {
        if (disputeID != null && !disputeID.equals("")) {
            showReport(ReportManager.getDisputeConfirmationReport(disputeBean1, this.disputesCommentsBean1.getComments()));
        }
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

        disputeBean1 = createDisputeBean();
        disputeCategory = createDisputeCategory();
        disputeStatus = new org.sola.clients.beans.referencedata.DisputeStatusListBean();
        disputeType = createDisputeType();
        otherAuthorities = createOtherAuthorities();
        cadastreObjectBean1 = new org.sola.clients.beans.cadastre.CadastreObjectBean();
        partyRoleTypes = new org.sola.clients.beans.referencedata.PartyRoleTypeListBean();
        idType = new org.sola.clients.beans.referencedata.IdTypeListBean();
        genderTypes = new org.sola.clients.beans.referencedata.GenderTypeListBean();
        communicationTypes = new org.sola.clients.beans.referencedata.CommunicationTypeListBean();
        disputesCommentsBean1 = createCommentsBean();
        partySearchParams = new org.sola.clients.beans.party.PartySearchParamsBean();
        partySearchResult = new org.sola.clients.beans.party.PartySearchResultListBean();
        cadastreObjectSearch = new org.sola.clients.beans.cadastre.CadastreObjectSearchResultListBean();
        disputeRoleType = new org.sola.clients.beans.referencedata.DisputeRoleTypeListBean();
        popUpDisputeResolution = new javax.swing.JPopupMenu();
        menuRejected = new javax.swing.JMenuItem();
        menuSolvedProClaimant = new javax.swing.JMenuItem();
        menuSolvedAgainstClaimant = new javax.swing.JMenuItem();
        menuUnsolved = new javax.swing.JMenuItem();
        popUpReport = new javax.swing.JPopupMenu();
        menuPrintConfirmation = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        btnnewDispute = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnSaveDispute = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnsearchDispute = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnComplete = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jPanel1 = new javax.swing.JPanel();
        tabContainer = new javax.swing.JTabbedPane();
        tabGeneralInfo = new javax.swing.JPanel();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        jToolBar3 = new javax.swing.JToolBar();
        btnnewDisputeComment = new javax.swing.JButton();
        btnAddComment1 = new javax.swing.JButton();
        btnRemoveDisputeComment = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtupdateDate = new javax.swing.JFormattedTextField();
        btnDate = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        dbxotherAuthorities1 = new javax.swing.JComboBox();
        jPanel26 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDisputeComments = new javax.swing.JEditorPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        lblPlotNumber = new javax.swing.JLabel();
        txtcadastreId = new javax.swing.JTextField();
        btnSearchPlot = new javax.swing.JButton();
        jPanel39 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        dbxdisputeCategory = new javax.swing.JComboBox();
        lblLeaseCategory1 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dbxdisputeType = new javax.swing.JComboBox();
        tabParty = new javax.swing.JPanel();
        cbxPartyRole = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDisputeParty = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        btnAddDisputeParty = new javax.swing.JButton();
        btnRemoveNotation = new javax.swing.JButton();
        partySearch = new org.sola.clients.swing.ui.party.PartyQuickSearch();
        txtPartyName = new javax.swing.JLabel();
        txtPartyRole = new javax.swing.JLabel();
        btnAddOwner = new javax.swing.JButton();
        tabDoc = new javax.swing.JPanel();
        documentsManagementPanel = createDocumentsPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        txtdisputeNumber = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        txtlodgementDate = new javax.swing.JFormattedTextField();
        lblLodgementDate = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtstatus = new javax.swing.JTextField();
        pnlHeader = new org.sola.clients.swing.ui.HeaderPanel();

        menuRejected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/reject.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/administrative/Bundle"); // NOI18N
        menuRejected.setText(bundle.getString("DisputePanelForm.menuRejected.text")); // NOI18N
        menuRejected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRejectedActionPerformed(evt);
            }
        });
        popUpDisputeResolution.add(menuRejected);

        menuSolvedProClaimant.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/revert.png"))); // NOI18N
        menuSolvedProClaimant.setText(bundle.getString("DisputePanelForm.menuSolvedProClaimant.text")); // NOI18N
        menuSolvedProClaimant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSolvedProClaimantActionPerformed(evt);
            }
        });
        popUpDisputeResolution.add(menuSolvedProClaimant);

        menuSolvedAgainstClaimant.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/requisition.png"))); // NOI18N
        menuSolvedAgainstClaimant.setText(bundle.getString("DisputePanelForm.menuSolvedAgainstClaimant.text")); // NOI18N
        menuSolvedAgainstClaimant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSolvedAgainstClaimantActionPerformed(evt);
            }
        });
        popUpDisputeResolution.add(menuSolvedAgainstClaimant);

        menuUnsolved.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/lapse.png"))); // NOI18N
        menuUnsolved.setText(bundle.getString("DisputePanelForm.menuUnsolved.text")); // NOI18N
        menuUnsolved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuUnsolvedActionPerformed(evt);
            }
        });
        popUpDisputeResolution.add(menuUnsolved);

        menuPrintConfirmation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/export.png"))); // NOI18N
        menuPrintConfirmation.setText(bundle.getString("DisputePanelForm.menuPrintConfirmation.text")); // NOI18N
        menuPrintConfirmation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPrintConfirmationActionPerformed(evt);
            }
        });
        popUpReport.add(menuPrintConfirmation);

        setHeaderPanel(pnlHeader);
        setHelpTopic(bundle.getString("DisputePanelForm.helpTopic")); // NOI18N
        setName(bundle.getString("DisputePanelForm.name")); // NOI18N

        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName(bundle.getString("DisputePanelForm.jToolBar1.name")); // NOI18N

        btnnewDispute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/new.png"))); // NOI18N
        btnnewDispute.setText(bundle.getString("DisputePanelForm.btnnewDispute.text")); // NOI18N
        btnnewDispute.setFocusable(false);
        btnnewDispute.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnewDispute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewDisputeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnnewDispute);

        jSeparator1.setName(bundle.getString("DisputePanelForm.jSeparator1.name")); // NOI18N
        jToolBar1.add(jSeparator1);

        btnSaveDispute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSaveDispute.setText(bundle.getString("DisputePanelForm.btnSaveDispute.text")); // NOI18N
        btnSaveDispute.setFocusable(false);
        btnSaveDispute.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveDispute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveDisputeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSaveDispute);

        jSeparator3.setName(bundle.getString("DisputePanelForm.jSeparator3.name")); // NOI18N
        jToolBar1.add(jSeparator3);

        btnsearchDispute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/search.png"))); // NOI18N
        btnsearchDispute.setText(bundle.getString("DisputePanelForm.btnsearchDispute.text")); // NOI18N
        btnsearchDispute.setFocusable(false);
        btnsearchDispute.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnsearchDispute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchDisputeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnsearchDispute);

        jSeparator4.setName(bundle.getString("DisputePanelForm.jSeparator4.name")); // NOI18N
        jToolBar1.add(jSeparator4);

        btnComplete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/lock.png"))); // NOI18N
        btnComplete.setText(bundle.getString("DisputePanelForm.btnComplete.text")); // NOI18N
        btnComplete.setFocusable(false);
        btnComplete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnComplete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnComplete);

        jSeparator5.setName(bundle.getString("DisputePanelForm.jSeparator5.name")); // NOI18N
        jToolBar1.add(jSeparator5);

        jPanel1.setName(bundle.getString("DisputePanelForm.jPanel1.name")); // NOI18N

        tabContainer.setName(bundle.getString("DisputePanelForm.tabContainer.name")); // NOI18N

        tabGeneralInfo.setName(bundle.getString("DisputePanelForm.tabGeneralInfo.name")); // NOI18N

        groupPanel1.setTitleText(bundle.getString("DisputePanelForm.groupPanel1.titleText")); // NOI18N

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);
        jToolBar3.setName(bundle.getString("DisputePanelForm.jToolBar3.name")); // NOI18N

        btnnewDisputeComment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/new.png"))); // NOI18N
        btnnewDisputeComment.setText(bundle.getString("DisputePanelForm.btnnewDisputeComment.text")); // NOI18N
        btnnewDisputeComment.setFocusable(false);
        btnnewDisputeComment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnewDisputeComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewDisputeCommentActionPerformed(evt);
            }
        });
        jToolBar3.add(btnnewDisputeComment);

        btnAddComment1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnAddComment1.setText(bundle.getString("DisputePanelForm.btnAddComment1.text")); // NOI18N
        btnAddComment1.setFocusable(false);
        btnAddComment1.setName(bundle.getString("DisputePanelForm.btnAddComment1.name")); // NOI18N
        btnAddComment1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddComment1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddComment1ActionPerformed(evt);
            }
        });
        jToolBar3.add(btnAddComment1);

        btnRemoveDisputeComment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveDisputeComment.setText(bundle.getString("DisputePanelForm.btnRemoveDisputeComment.text")); // NOI18N
        btnRemoveDisputeComment.setFocusable(false);
        btnRemoveDisputeComment.setName(bundle.getString("DisputePanelForm.btnRemoveDisputeComment.name")); // NOI18N
        btnRemoveDisputeComment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveDisputeComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveDisputeCommentActionPerformed(evt);
            }
        });
        jToolBar3.add(btnRemoveDisputeComment);

        jPanel6.setName(bundle.getString("DisputePanelForm.jPanel6.name")); // NOI18N

        jLabel4.setText(bundle.getString("DisputePanelForm.jLabel4.text")); // NOI18N

        txtupdateDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        txtupdateDate.setText(bundle.getString("DisputePanelForm.txtupdateDate.text")); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputesCommentsBean1, org.jdesktop.beansbinding.ELProperty.create("${updateDate}"), txtupdateDate, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        btnDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/calendar.png"))); // NOI18N
        btnDate.setText(bundle.getString("DisputePanelForm.btnDate.text_1")); // NOI18N
        btnDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtupdateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(186, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(151, 151, 151)
                    .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(151, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtupdateDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGap(18, 18, 18)))
        );

        jLabel6.setText(bundle.getString("DisputePanelForm.jLabel6.text")); // NOI18N
        jLabel6.setName(bundle.getString("DisputePanelForm.jLabel6.name")); // NOI18N

        dbxotherAuthorities1.setName(bundle.getString("DisputePanelForm.dbxotherAuthorities1.name")); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${otherAuthoritiesListBean}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, otherAuthorities, eLProperty, dbxotherAuthorities1);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputesCommentsBean1, org.jdesktop.beansbinding.ELProperty.create("${otherAuthorities}"), dbxotherAuthorities1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(dbxotherAuthorities1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 96, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dbxotherAuthorities1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel8.setText(bundle.getString("DisputePanelForm.jLabel8.text")); // NOI18N
        jLabel8.setName(bundle.getString("DisputePanelForm.jLabel8.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputesCommentsBean1, org.jdesktop.beansbinding.ELProperty.create("${comments}"), txtDisputeComments, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane3.setViewportView(txtDisputeComments);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE))))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        lblPlotNumber.setText(bundle.getString("DisputePanelForm.lblPlotNumber.text")); // NOI18N
        lblPlotNumber.setName(bundle.getString("DisputePanelForm.lblPlotNumber.name")); // NOI18N

        txtcadastreId.setName(bundle.getString("DisputePanelForm.txtcadastreId.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputeBean1, org.jdesktop.beansbinding.ELProperty.create("${plotNumber}"), txtcadastreId, org.jdesktop.beansbinding.BeanProperty.create("text"), "kjhjkhgn  bvvcvc");
        bindingGroup.addBinding(binding);

        txtcadastreId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcadastreIdActionPerformed(evt);
            }
        });

        btnSearchPlot.setText(bundle.getString("DisputePanelForm.btnSearchPlot.text")); // NOI18N
        btnSearchPlot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchPlotActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPlotNumber)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(txtcadastreId, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearchPlot))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPlotNumber)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearchPlot)
                    .addComponent(txtcadastreId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel39.setLayout(new java.awt.GridLayout(1, 2, 5, 5));

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${disputeCategoryListBean}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputeCategory, eLProperty, dbxdisputeCategory);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputeBean1, org.jdesktop.beansbinding.ELProperty.create("${disputeCategory}"), dbxdisputeCategory, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        lblLeaseCategory1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        lblLeaseCategory1.setText(bundle.getString("DisputePanelForm.lblLeaseCategory1.text")); // NOI18N
        lblLeaseCategory1.setName(bundle.getString("DisputePanelForm.lblLeaseCategory1.name")); // NOI18N

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dbxdisputeCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLeaseCategory1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addComponent(lblLeaseCategory1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dbxdisputeCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel3.setText(bundle.getString("DisputePanelForm.jLabel3.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${disputeTypeListBean}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputeType, eLProperty, dbxdisputeType);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputeBean1, org.jdesktop.beansbinding.ELProperty.create("${disputeType}"), dbxdisputeType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dbxdisputeType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dbxdisputeType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(339, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout tabGeneralInfoLayout = new javax.swing.GroupLayout(tabGeneralInfo);
        tabGeneralInfo.setLayout(tabGeneralInfoLayout);
        tabGeneralInfoLayout.setHorizontalGroup(
            tabGeneralInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(tabGeneralInfoLayout.createSequentialGroup()
                .addGroup(tabGeneralInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(groupPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(tabGeneralInfoLayout.createSequentialGroup()
                .addGroup(tabGeneralInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tabGeneralInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        tabGeneralInfoLayout.setVerticalGroup(
            tabGeneralInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabGeneralInfoLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabContainer.addTab(bundle.getString("DisputePanelForm.tabGeneralInfo.TabConstraints.tabTitle"), tabGeneralInfo); // NOI18N

        cbxPartyRole.setEnabled(false);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${disputeRoleTypeListBean}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputeRoleType, eLProperty, cbxPartyRole);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputeBean1, org.jdesktop.beansbinding.ELProperty.create("${disputeRoleType}"), cbxPartyRole, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${filteredDisputePartyList}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputeBean1, eLProperty, tblDisputeParty);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${partyName}"));
        columnBinding.setColumnName("Party Name");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${partyRole}"));
        columnBinding.setColumnName("Party Role");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputeBean1, org.jdesktop.beansbinding.ELProperty.create("${selectedParty}"), tblDisputeParty, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(tblDisputeParty);
        tblDisputeParty.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("DisputePanelForm.tblDisputeParty.columnModel.title0_2")); // NOI18N
        tblDisputeParty.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("DisputePanelForm.tblDisputeParty.columnModel.title1_2")); // NOI18N

        btnAddDisputeParty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnAddDisputeParty.setText(bundle.getString("DisputePanelForm.btnAddDisputeParty.text")); // NOI18N
        btnAddDisputeParty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDisputePartyActionPerformed(evt);
            }
        });

        btnRemoveNotation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveNotation.setText(bundle.getString("DisputePanelForm.btnRemoveNotation.text")); // NOI18N
        btnRemoveNotation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveNotationActionPerformed(evt);
            }
        });

        txtPartyName.setText(bundle.getString("DisputePanelForm.txtPartyName.text_1")); // NOI18N

        txtPartyRole.setText(bundle.getString("DisputePanelForm.txtPartyRole.text_1")); // NOI18N

        btnAddOwner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/new.png"))); // NOI18N
        btnAddOwner.setText(bundle.getString("DisputePanelForm.btnAddOwner.text")); // NOI18N
        btnAddOwner.setFocusable(false);
        btnAddOwner.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOwnerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabPartyLayout = new javax.swing.GroupLayout(tabParty);
        tabParty.setLayout(tabPartyLayout);
        tabPartyLayout.setHorizontalGroup(
            tabPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPartyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabPartyLayout.createSequentialGroup()
                        .addGroup(tabPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabPartyLayout.createSequentialGroup()
                                .addComponent(btnAddDisputeParty, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemoveNotation, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddOwner))
                            .addGroup(tabPartyLayout.createSequentialGroup()
                                .addGroup(tabPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(tabPartyLayout.createSequentialGroup()
                                        .addComponent(txtPartyName, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(99, 99, 99))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabPartyLayout.createSequentialGroup()
                                        .addComponent(partySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)))
                                .addGroup(tabPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxPartyRole, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPartyRole, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1317, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabPartyLayout.setVerticalGroup(
            tabPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPartyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tabPartyLayout.createSequentialGroup()
                        .addGroup(tabPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPartyName)
                            .addComponent(txtPartyRole))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(partySearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxPartyRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(tabPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddDisputeParty, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRemoveNotation, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnAddOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        tabContainer.addTab(bundle.getString("DisputePanelForm.tabParty.TabConstraints.tabTitle"), tabParty); // NOI18N

        javax.swing.GroupLayout tabDocLayout = new javax.swing.GroupLayout(tabDoc);
        tabDoc.setLayout(tabDocLayout);
        tabDocLayout.setHorizontalGroup(
            tabDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(documentsManagementPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE)
        );
        tabDocLayout.setVerticalGroup(
            tabDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(documentsManagementPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
        );

        tabContainer.addTab(bundle.getString("DisputePanelForm.tabDoc.TabConstraints.tabTitle_1"), tabDoc); // NOI18N

        jPanel2.setName(bundle.getString("DisputePanelForm.jPanel2.name")); // NOI18N

        txtdisputeNumber.setEditable(false);
        txtdisputeNumber.setEnabled(false);
        txtdisputeNumber.setName(bundle.getString("DisputePanelForm.txtdisputeNumber.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputeBean1, org.jdesktop.beansbinding.ELProperty.create("${nr}"), txtdisputeNumber, org.jdesktop.beansbinding.BeanProperty.create("text"), "disputeNr");
        bindingGroup.addBinding(binding);

        jLabel1.setText(bundle.getString("DisputePanelForm.jLabel1.text")); // NOI18N
        jLabel1.setName(bundle.getString("DisputePanelForm.jLabel1.name")); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtdisputeNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(txtdisputeNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txtlodgementDate.setEditable(false);
        txtlodgementDate.setEnabled(false);
        txtlodgementDate.setName(bundle.getString("DisputePanelForm.txtlodgementDate.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputeBean1, org.jdesktop.beansbinding.ELProperty.create("${lodgementDate}"), txtlodgementDate, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        lblLodgementDate.setText(bundle.getString("DisputePanelForm.lblLodgementDate.text")); // NOI18N
        lblLodgementDate.setName(bundle.getString("DisputePanelForm.lblLodgementDate.name")); // NOI18N

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLodgementDate)
                    .addComponent(txtlodgementDate, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addComponent(lblLodgementDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(txtlodgementDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel10.setText(bundle.getString("DisputePanelForm.jLabel10.text")); // NOI18N
        jLabel10.setName(bundle.getString("DisputePanelForm.jLabel10.name")); // NOI18N

        txtstatus.setEditable(false);
        txtstatus.setEnabled(false);
        txtstatus.setName(bundle.getString("DisputePanelForm.txtstatus.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, disputeBean1, org.jdesktop.beansbinding.ELProperty.create("${statusCode}"), txtstatus, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addComponent(tabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 1011, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pnlHeader.setName(bundle.getString("DisputePanelForm.pnlHeader.name")); // NOI18N
        pnlHeader.setTitleText(bundle.getString("DisputePanelForm.pnlHeader.titleText")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void txtcadastreIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcadastreIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcadastreIdActionPerformed

    private void btnSearchPlotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchPlotActionPerformed
        SearchPlot();
    }//GEN-LAST:event_btnSearchPlotActionPerformed

    private void btnAddComment1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddComment1ActionPerformed
        addComment();
    }//GEN-LAST:event_btnAddComment1ActionPerformed

    private void btnnewDisputeCommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewDisputeCommentActionPerformed
        NewComments();
    }//GEN-LAST:event_btnnewDisputeCommentActionPerformed

    private void removePartyDisp() {
        if (disputeBean1.getSelectedParty() != null)
//                && MessageUtility.displayMessage(ClientMessage.CONFIRM_DELETE_RECORD) == MessageUtility.BUTTON_ONE) 
        {
            disputeBean1.removeSelectedParty();
            
        }
    }

    private void newPartyDisp() {
        openDisputePartyForm(null, false);
    }

    
    private class DisputePartyFormListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(PartyPanelForm.PARTY_SAVED)) {
                partySearch.setSelectedElement((PartySummaryBean) ((PartyPanelForm) evt.getSource()).getParty());
                partySearch.setText((String) ((PartyPanelForm) evt.getSource()).getParty().getFullName()); 
            }
        }
    }
    
    private void openDisputePartyForm(final PartySummaryBean partySummaryBean, final boolean isReadOnly) {
        final DisputePartyFormListener listener = new DisputePartyFormListener();

        SolaTask t = new SolaTask<Void, Void>() {
            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_PERSON));
                PartyPanelForm partyForm;

                if (partySummaryBean != null) {
                    partyForm = new PartyPanelForm(true, partySummaryBean, isReadOnly, true);
                } else {
                    partyForm = new PartyPanelForm(true, null, isReadOnly, true);
                }
                partyForm.addPropertyChangeListener(listener);
                getMainContentPanel().addPanel(partyForm, MainContentPanel.CARD_PERSON, true);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    
    
    
    private void btnRemoveDisputeCommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveDisputeCommentActionPerformed
        removeComment();
    }//GEN-LAST:event_btnRemoveDisputeCommentActionPerformed

    private void btnCompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompleteActionPerformed
//        completeDispute();
    }//GEN-LAST:event_btnCompleteActionPerformed

    private void btnsearchDisputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchDisputeActionPerformed
        searchDispute();
    }//GEN-LAST:event_btnsearchDisputeActionPerformed

    private void btnnewDisputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewDisputeActionPerformed
//        createNewDispute();
    }//GEN-LAST:event_btnnewDisputeActionPerformed

    private void btnSaveDisputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveDisputeActionPerformed
        saveDispute(true, false);
    }//GEN-LAST:event_btnSaveDisputeActionPerformed

    private void btnAddDisputePartyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDisputePartyActionPerformed
        addDisputeParty();
    }//GEN-LAST:event_btnAddDisputePartyActionPerformed

    private void btnRemoveNotationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveNotationActionPerformed
        removePartyDisp();
    }//GEN-LAST:event_btnRemoveNotationActionPerformed

    private void btnAddOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOwnerActionPerformed
        newPartyDisp();
    }//GEN-LAST:event_btnAddOwnerActionPerformed

    private void menuRejectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRejectedActionPerformed
        completeDispute(DisputeBean.REJECTED_DISPUTE_PROPERTY);
    }//GEN-LAST:event_menuRejectedActionPerformed

    private void menuSolvedProClaimantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSolvedProClaimantActionPerformed
         completeDispute(DisputeBean.PROCLAIMANT_DISPUTE_PROPERTY);
    }//GEN-LAST:event_menuSolvedProClaimantActionPerformed

    private void menuSolvedAgainstClaimantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSolvedAgainstClaimantActionPerformed
         completeDispute(DisputeBean.AGAINSTCLAIMANT_DISPUTE_PROPERTY);
    }//GEN-LAST:event_menuSolvedAgainstClaimantActionPerformed

    private void menuUnsolvedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUnsolvedActionPerformed
         completeDispute(DisputeBean.UNSOLVED_DISPUTE_PROPERTY);
    }//GEN-LAST:event_menuUnsolvedActionPerformed

    private void menuPrintConfirmationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPrintConfirmationActionPerformed
        printConfirmation();
    }//GEN-LAST:event_menuPrintConfirmationActionPerformed

    private void btnDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDateActionPerformed
        // TODO add your handling code here:
        showCalendar(txtupdateDate);

    }//GEN-LAST:event_btnDateActionPerformed
    private void showCalendar(JFormattedTextField dateField) {
        CalendarForm calendar = new CalendarForm(null, true, dateField);
        calendar.setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddComment1;
    private javax.swing.JButton btnAddDisputeParty;
    private javax.swing.JButton btnAddOwner;
    private javax.swing.JButton btnComplete;
    private javax.swing.JButton btnDate;
    private javax.swing.JButton btnRemoveDisputeComment;
    private javax.swing.JButton btnRemoveNotation;
    private javax.swing.JButton btnSaveDispute;
    private javax.swing.JButton btnSearchPlot;
    private javax.swing.JButton btnnewDispute;
    private javax.swing.JButton btnnewDisputeComment;
    private javax.swing.JButton btnsearchDispute;
    private org.sola.clients.beans.cadastre.CadastreObjectBean cadastreObjectBean1;
    private org.sola.clients.beans.cadastre.CadastreObjectSearchResultListBean cadastreObjectSearch;
    private javax.swing.JComboBox cbxPartyRole;
    private org.sola.clients.beans.referencedata.CommunicationTypeListBean communicationTypes;
    public javax.swing.JComboBox dbxdisputeCategory;
    public javax.swing.JComboBox dbxdisputeType;
    private javax.swing.JComboBox dbxotherAuthorities1;
    private org.sola.clients.beans.administrative.DisputeBean disputeBean1;
    private org.sola.clients.beans.referencedata.DisputeCategoryListBean disputeCategory;
    private org.sola.clients.beans.referencedata.DisputeRoleTypeListBean disputeRoleType;
    private org.sola.clients.beans.referencedata.DisputeStatusListBean disputeStatus;
    private org.sola.clients.beans.referencedata.DisputeTypeListBean disputeType;
    private org.sola.clients.beans.administrative.DisputesCommentsBean disputesCommentsBean1;
    private org.sola.clients.swing.desktop.source.DocumentsManagementExtPanel documentsManagementPanel;
    private org.sola.clients.beans.referencedata.GenderTypeListBean genderTypes;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.beans.referencedata.IdTypeListBean idType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JLabel lblLeaseCategory1;
    private javax.swing.JLabel lblLodgementDate;
    private javax.swing.JLabel lblPlotNumber;
    private javax.swing.JMenuItem menuPrintConfirmation;
    private javax.swing.JMenuItem menuRejected;
    private javax.swing.JMenuItem menuSolvedAgainstClaimant;
    private javax.swing.JMenuItem menuSolvedProClaimant;
    private javax.swing.JMenuItem menuUnsolved;
    private org.sola.clients.beans.referencedata.OtherAuthoritiesListBean otherAuthorities;
    private org.sola.clients.beans.referencedata.PartyRoleTypeListBean partyRoleTypes;
    private org.sola.clients.swing.ui.party.PartyQuickSearch partySearch;
    private org.sola.clients.beans.party.PartySearchParamsBean partySearchParams;
    private org.sola.clients.beans.party.PartySearchResultListBean partySearchResult;
    private org.sola.clients.swing.ui.HeaderPanel pnlHeader;
    private javax.swing.JPopupMenu popUpDisputeResolution;
    private javax.swing.JPopupMenu popUpReport;
    private javax.swing.JTabbedPane tabContainer;
    private javax.swing.JPanel tabDoc;
    private javax.swing.JPanel tabGeneralInfo;
    private javax.swing.JPanel tabParty;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tblDisputeParty;
    private javax.swing.JEditorPane txtDisputeComments;
    private javax.swing.JLabel txtPartyName;
    private javax.swing.JLabel txtPartyRole;
    private javax.swing.JTextField txtcadastreId;
    private javax.swing.JTextField txtdisputeNumber;
    private javax.swing.JFormattedTextField txtlodgementDate;
    private javax.swing.JTextField txtstatus;
    private javax.swing.JFormattedTextField txtupdateDate;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
