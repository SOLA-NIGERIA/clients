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
package org.sola.clients.swing.ui.administrative;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.administrative.BaUnitBean;
import org.sola.clients.beans.administrative.BaUnitSearchResultBean;
import org.sola.clients.beans.administrative.BaUnitSearchResultListBean;
import org.sola.clients.swing.common.laf.LafManager;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.ui.renderers.CellDelimitedListRenderer;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Allows to search BA Units.
 */
public class BaUnitSearchPanel extends javax.swing.JPanel {

    public static final String SELECTED_BAUNIT_SEARCH_RESULT = "selectedBaUnitSearchResultOpen";
    
    /**
     * Default constructor.
     */
    public BaUnitSearchPanel() {
        initComponents();

        customieOpenButton(null);
        baUnitSearchResults.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(BaUnitSearchResultListBean.SELECTED_BAUNIT_SEARCH_RESULT_PROPERTY)) {
                    firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
                    customieOpenButton((BaUnitSearchResultBean) evt.getNewValue());
                }
            }
        });
    }

    private void customieOpenButton(BaUnitSearchResultBean searchResult) {
        btnOpenBaUnit.setEnabled(searchResult != null);
        menuOpenBaUnit.setEnabled(btnOpenBaUnit.isEnabled());
    }

    /**
     * Indicates whether open button is shown or not.
     */
    public boolean isShowOpenButton() {
        return btnOpenBaUnit.isVisible();
    }

    /**
     * Sets visibility of open button.
     */
    public void setShowOpenButton(boolean showPrintButton) {
        btnOpenBaUnit.setVisible(showPrintButton);
        menuOpenBaUnit.setVisible(showPrintButton);
        separator1.setVisible(showPrintButton);
    }

    /**
     * Returns selected search result.
     */
    public BaUnitSearchResultBean getSelectedSearchResult() {
        return baUnitSearchResults.getSelectedBaUnitSearchResult();
    }

    private void clearSearchResults(){
        baUnitSearchParams.setNameFirstPart(null);
        baUnitSearchParams.setNameLastPart(null);
        baUnitSearchParams.setOwnerName(null);
        baUnitSearchParams.setName(null);
        baUnitSearch1.setText(null);
        baUnitSearch1.setSelectedElement("");
        baUnitSearchResults.getBaUnitSearchResults().clear();
        lblSearchResultCount.setText("0");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        baUnitSearchResults = new org.sola.clients.beans.administrative.BaUnitSearchResultListBean();
        baUnitSearchParams = new org.sola.clients.beans.administrative.BaUnitSearchParamsBean();
        popUpSearchResults = new javax.swing.JPopupMenu();
        menuOpenBaUnit = new javax.swing.JMenuItem();
        jPanel8 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSearchResults = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jToolBar1 = new javax.swing.JToolBar();
        btnOpenBaUnit = new javax.swing.JButton();
        separator1 = new javax.swing.JToolBar.Separator();
        lblSearchResult = new javax.swing.JLabel();
        lblSearchResultCount = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNameFirstPart = new javax.swing.JTextField();
        txtNameLastPart = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtRightholder = new javax.swing.JTextField();
        lblTitle = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        baUnitSearch1 = new org.sola.clients.swing.ui.administrative.BaUnitSearch();
        lblSearchString = new javax.swing.JLabel();

        popUpSearchResults.setName("popUpSearchResults"); // NOI18N

        menuOpenBaUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/folder-open-document.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/administrative/Bundle"); // NOI18N
        menuOpenBaUnit.setText(bundle.getString("BaUnitSearchPanel.menuOpenBaUnit.text")); // NOI18N
        menuOpenBaUnit.setName("menuOpenBaUnit"); // NOI18N
        menuOpenBaUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpenBaUnitActionPerformed(evt);
            }
        });
        popUpSearchResults.add(menuOpenBaUnit);

        jPanel8.setName(bundle.getString("BaUnitSearchPanel.jPanel8.name")); // NOI18N

        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        jPanel7.setName(bundle.getString("BaUnitSearchPanel.jPanel7.name")); // NOI18N

        jLabel5.setText(bundle.getString("BaUnitSearchPanel.jLabel5.text")); // NOI18N
        jLabel5.setName(bundle.getString("BaUnitSearchPanel.jLabel5.name")); // NOI18N

        btnClear.setText(bundle.getString("BaUnitSearchPanel.btnClear.text")); // NOI18N
        btnClear.setName(bundle.getString("BaUnitSearchPanel.btnClear.name")); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel7);

        jPanel6.setName(bundle.getString("BaUnitSearchPanel.jPanel6.name")); // NOI18N

        jLabel4.setText(bundle.getString("BaUnitSearchPanel.jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        btnSearch.setText(bundle.getString("BaUnitSearchPanel.btnSearch.text")); // NOI18N
        btnSearch.setName("btnSearch"); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 22, Short.MAX_VALUE))
            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableSearchResults.setComponentPopupMenu(popUpSearchResults);
        tableSearchResults.setName("tableSearchResults"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${baUnitSearchResults}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, baUnitSearchResults, eLProperty, tableSearchResults);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nameFirstPart} / ${nameLastPart}"));
        columnBinding.setColumnName("Name First Part} / ${name Last Part");
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        columnBinding.setColumnName("Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${rightholders}"));
        columnBinding.setColumnName("Rightholders");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${registrationStatus.displayValue}"));
        columnBinding.setColumnName("Registration Status.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, baUnitSearchResults, org.jdesktop.beansbinding.ELProperty.create("${selectedBaUnitSearchResult}"), tableSearchResults, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        tableSearchResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSearchResultsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableSearchResults);
        tableSearchResults.getColumnModel().getColumn(0).setMinWidth(130);
        tableSearchResults.getColumnModel().getColumn(0).setPreferredWidth(130);
        tableSearchResults.getColumnModel().getColumn(0).setMaxWidth(130);
        tableSearchResults.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("BaUnitSearchPanel.tableSearchResults.columnModel.title5")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(1).setMinWidth(70);
        tableSearchResults.getColumnModel().getColumn(1).setPreferredWidth(70);
        tableSearchResults.getColumnModel().getColumn(1).setMaxWidth(70);
        tableSearchResults.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("BaUnitSearchPanel.tableSearchResults.columnModel.title0")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("BaUnitSearchPanel.tableSearchResults.columnModel.title3")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(2).setCellRenderer(new CellDelimitedListRenderer("::::"));
        tableSearchResults.getColumnModel().getColumn(3).setMinWidth(60);
        tableSearchResults.getColumnModel().getColumn(3).setPreferredWidth(60);
        tableSearchResults.getColumnModel().getColumn(3).setMaxWidth(60);
        tableSearchResults.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("BaUnitSearchPanel.tableSearchResults.columnModel.title4")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnOpenBaUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/folder-open-document.png"))); // NOI18N
        btnOpenBaUnit.setText(bundle.getString("BaUnitSearchPanel.btnOpenBaUnit.text")); // NOI18N
        btnOpenBaUnit.setFocusable(false);
        btnOpenBaUnit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnOpenBaUnit.setName("btnOpenBaUnit"); // NOI18N
        btnOpenBaUnit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpenBaUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenBaUnitActionPerformed(evt);
            }
        });
        jToolBar1.add(btnOpenBaUnit);

        separator1.setName("separator1"); // NOI18N
        jToolBar1.add(separator1);

        lblSearchResult.setText(bundle.getString("BaUnitSearchPanel.lblSearchResult.text")); // NOI18N
        lblSearchResult.setName("lblSearchResult"); // NOI18N
        jToolBar1.add(lblSearchResult);

        lblSearchResultCount.setFont(LafManager.getUiFont().deriveFont(Font.BOLD));
        lblSearchResultCount.setText(bundle.getString("BaUnitSearchPanel.lblSearchResultCount.text")); // NOI18N
        lblSearchResultCount.setName("lblSearchResultCount"); // NOI18N
        jToolBar1.add(lblSearchResultCount);

        jPanel4.setName("jPanel4"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(bundle.getString("BaUnitSearchPanel.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        txtNameFirstPart.setName("txtNameFirstPart"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, baUnitSearchParams, org.jdesktop.beansbinding.ELProperty.create("${nameFirstPart}"), txtNameFirstPart, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtNameLastPart.setName(bundle.getString("BaUnitSearchPanel.txtNameLastPart.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, baUnitSearchParams, org.jdesktop.beansbinding.ELProperty.create("${nameLastPart}"), txtNameLastPart, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtNameLastPart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameLastPartActionPerformed(evt);
            }
        });

        jLabel3.setText(bundle.getString("BaUnitSearchPanel.jLabel3.text")); // NOI18N
        jLabel3.setName(bundle.getString("BaUnitSearchPanel.jLabel3.name")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtNameFirstPart, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNameLastPart)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNameFirstPart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNameLastPart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setName("jPanel2"); // NOI18N

        jLabel2.setText(bundle.getString("BaUnitSearchPanel.jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        txtRightholder.setName("txtRightholder"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, baUnitSearchParams, org.jdesktop.beansbinding.ELProperty.create("${ownerName}"), txtRightholder, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addContainerGap())
            .addComponent(txtRightholder)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRightholder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        lblTitle.setText(bundle.getString("BaUnitSearchPanel.lblTitle.text")); // NOI18N
        lblTitle.setName(bundle.getString("BaUnitSearchPanel.lblTitle.name")); // NOI18N

        txtTitle.setName(bundle.getString("BaUnitSearchPanel.txtTitle.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, baUnitSearchParams, org.jdesktop.beansbinding.ELProperty.create("${name}"), txtTitle, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        baUnitSearch1.setText(bundle.getString("BaUnitSearchPanel.baUnitSearch1.text")); // NOI18N
        baUnitSearch1.setName(bundle.getString("BaUnitSearchPanel.baUnitSearch1.name")); // NOI18N

        lblSearchString.setText(bundle.getString("BaUnitSearchPanel.lblSearchString.text")); // NOI18N
        lblSearchString.setName(bundle.getString("BaUnitSearchPanel.lblSearchString.name")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblSearchString, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(baUnitSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                        .addGap(23, 23, 23)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTitle)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSearchString)
                            .addComponent(lblTitle))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(baUnitSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents
    
    
     public void clickFind() {
        btnSearchActionPerformed(null);
    }
    
    
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        
        if(baUnitSearch1.getSelectedElement()!=null){
            baUnitSearchParams.setNameFirstPart(baUnitSearch1.getSelectedElement().toString());
            baUnitSearchParams.setNameLastPart(baUnitSearch1.getSelectedElement().toString());
         }
        
        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_PROPERTY_SEARCHING));
                baUnitSearchResults.search(baUnitSearchParams);
                return null;
            }

            @Override
            public void taskDone() {
                lblSearchResultCount.setText(Integer.toString(baUnitSearchResults.getBaUnitSearchResults().size()));
                if (baUnitSearchResults.getBaUnitSearchResults().size() < 1) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_NO_RESULTS);
                } else if (baUnitSearchResults.getBaUnitSearchResults().size() > 100) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_TOO_MANY_RESULTS, new String[]{"100"});
                }
            }
        };
        TaskManager.getInstance().runTask(t);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tableSearchResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSearchResultsMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            openBaUnit();
        }
    }//GEN-LAST:event_tableSearchResultsMouseClicked

    private void btnOpenBaUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenBaUnitActionPerformed
        openBaUnit();
    }//GEN-LAST:event_btnOpenBaUnitActionPerformed

    private void menuOpenBaUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOpenBaUnitActionPerformed
        openBaUnit();
    }//GEN-LAST:event_menuOpenBaUnitActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearSearchResults();
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtNameLastPartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameLastPartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameLastPartActionPerformed

    private void openBaUnit() {
        if (baUnitSearchResults.getSelectedBaUnitSearchResult() != null) {
            firePropertyChange(SELECTED_BAUNIT_SEARCH_RESULT, null, baUnitSearchResults.getSelectedBaUnitSearchResult());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.ui.administrative.BaUnitSearch baUnitSearch1;
    private org.sola.clients.beans.administrative.BaUnitSearchParamsBean baUnitSearchParams;
    private org.sola.clients.beans.administrative.BaUnitSearchResultListBean baUnitSearchResults;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnOpenBaUnit;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblSearchResult;
    private javax.swing.JLabel lblSearchResultCount;
    private javax.swing.JLabel lblSearchString;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JMenuItem menuOpenBaUnit;
    private javax.swing.JPopupMenu popUpSearchResults;
    private javax.swing.JToolBar.Separator separator1;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableSearchResults;
    private javax.swing.JTextField txtNameFirstPart;
    private javax.swing.JTextField txtNameLastPart;
    private javax.swing.JTextField txtRightholder;
    private javax.swing.JTextField txtTitle;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
