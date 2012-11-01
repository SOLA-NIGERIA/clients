/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.bulkoperations.spatialobjects;

import java.io.File;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.sola.clients.swing.bulkoperations.beans.SpatialSourceBean;
import org.jdesktop.beansbinding.*;
import org.jdesktop.swingbinding.*;
import org.sola.clients.swing.bulkoperations.beans.SpatialDestinationAttributeBean;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author Elton Manoku
 */
public class ImportSpatialPanel extends ContentPanel {

    private static String PANEL_NAME = "IMPORT_SPATIAL_PANEL";
    private SpatialDestinationAttributeBean selectedDestinationAttributeBean;

    /**
     * Creates new form ImportSpatialPanel
     */
    public ImportSpatialPanel() {
        initComponents();
        this.setName(PANEL_NAME);
    }

    public SpatialDestinationAttributeBean getSelectedDestinationAttributeBean() {
        return selectedDestinationAttributeBean;
    }

    public void setSelectedDestinationAttributeBean(SpatialDestinationAttributeBean selectedDestinationAttributeBean) {
        this.selectedDestinationAttributeBean = selectedDestinationAttributeBean;
        this.btnRemove.setEnabled(this.selectedDestinationAttributeBean != null);
    }

    private void findAndSetSource() {
        if (spatialSourceTypeList.getSelectedBean() == null) {
            return;
        }
        File sourceFile = JFileDataStoreChooser.showOpenFile(
                spatialSourceTypeList.getSelectedBean().getCode(), null);
        if (sourceFile == null) {
            return;
        }
        spatialBulkMove.setSource(
                SpatialSourceBean.getInstance(spatialSourceTypeList.getSelectedBean()));
        spatialBulkMove.getSource().setSourceFile(sourceFile);
        //txtSourcePath.setText(sourceFile.getAbsolutePath());
        refreshBindingOfSourceAttributes();
    }

    private void refreshBindingOfSourceAttributes() {
        for (Binding binding : bindingGroup.getBindings()) {
            if (binding.getTargetObject().equals(listAttributes)
                    || binding.getTargetObject().equals(cmbDestinationAttributeSource)
                    || binding.getTargetObject().equals(txtSourceFeaturesNr)
                    || binding.getTargetObject().equals(txtSourceGeometryType)
                    || binding.getTargetObject().equals(txtSourcePath)
                    ) {
                binding.unbind();
                binding.bind();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        spatialSourceTypeList = new org.sola.clients.swing.bulkoperations.beans.SpatialSourceTypeListBean();
        spatialDestinationTypeList = new org.sola.clients.swing.bulkoperations.beans.SpatialDestinationTypeListBean();
        spatialBulkMove = new org.sola.clients.swing.bulkoperations.beans.SpatialBulkMoveBean();
        spatialDestinationAttribute = new org.sola.clients.swing.bulkoperations.beans.SpatialDestinationAttributeBean();
        cmbSourceType = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtSourcePath = new javax.swing.JTextField();
        cmdBrowse = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listAttributes = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cmbDestination = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        cmbDestinationAttributeName = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        cmbDestinationAttributeSource = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        txtDestinationAttributeSourceText = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        groupPanel2 = new org.sola.clients.swing.ui.GroupPanel();
        btnMove = new javax.swing.JButton();
        txtSourceGeometryType = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtSourceFeaturesNr = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${beanList}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialSourceTypeList, eLProperty, cmbSourceType);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialSourceTypeList, org.jdesktop.beansbinding.ELProperty.create("${selectedBean}"), cmbSourceType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/bulkoperations/spatialobjects/Bundle"); // NOI18N
        jLabel1.setText(bundle.getString("ImportSpatialPanel.jLabel1.text")); // NOI18N

        jLabel2.setText(bundle.getString("ImportSpatialPanel.jLabel2.text")); // NOI18N

        txtSourcePath.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialBulkMove, org.jdesktop.beansbinding.ELProperty.create("${source.sourceFile.absolutePath}"), txtSourcePath, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        cmdBrowse.setText(bundle.getString("ImportSpatialPanel.cmdBrowse.text")); // NOI18N
        cmdBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBrowseActionPerformed(evt);
            }
        });

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${source.attributes}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialBulkMove, eLProperty, listAttributes, "");
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        columnBinding.setColumnName("Name");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${dataType}"));
        columnBinding.setColumnName("Data Type");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(listAttributes);
        listAttributes.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ImportSpatialPanel.listAttributes.columnModel.title0")); // NOI18N
        listAttributes.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("ImportSpatialPanel.listAttributes.columnModel.title1")); // NOI18N

        jLabel3.setText(bundle.getString("ImportSpatialPanel.jLabel3.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${beanList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialDestinationTypeList, eLProperty, cmbDestination);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialBulkMove, org.jdesktop.beansbinding.ELProperty.create("${destinationType}"), cmbDestination, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jLabel4.setText(bundle.getString("ImportSpatialPanel.jLabel4.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${destinationAttributes}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialBulkMove, eLProperty, jTable1);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        columnBinding.setColumnName("Name");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${sourceAttribute}"));
        columnBinding.setColumnName("Source Attribute");
        columnBinding.setColumnClass(org.sola.clients.swing.bulkoperations.beans.SpatialAttributeBean.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${sourceText}"));
        columnBinding.setColumnName("Source Text");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${selectedDestinationAttributeBean}"), jTable1, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ImportSpatialPanel.jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("ImportSpatialPanel.jTable1.columnModel.title1")); // NOI18N
        jTable1.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("ImportSpatialPanel.jTable1.columnModel.title2")); // NOI18N

        jLabel5.setText(bundle.getString("ImportSpatialPanel.jLabel5.text")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnAdd.setText(bundle.getString("ImportSpatialPanel.btnAdd.text")); // NOI18N
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdd);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemove.setText(bundle.getString("ImportSpatialPanel.btnRemove.text")); // NOI18N
        btnRemove.setFocusable(false);
        btnRemove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnRemove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemove);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${destinationType.attributes}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialBulkMove, eLProperty, cmbDestinationAttributeName);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialDestinationAttribute, org.jdesktop.beansbinding.ELProperty.create("${attributeObjectName}"), cmbDestinationAttributeName, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jLabel6.setText(bundle.getString("ImportSpatialPanel.jLabel6.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${source.attributes}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialBulkMove, eLProperty, cmbDestinationAttributeSource);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialDestinationAttribute, org.jdesktop.beansbinding.ELProperty.create("${sourceAttribute}"), cmbDestinationAttributeSource, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jLabel7.setText(bundle.getString("ImportSpatialPanel.jLabel7.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialDestinationAttribute, org.jdesktop.beansbinding.ELProperty.create("${sourceText}"), txtDestinationAttributeSourceText, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel8.setText(bundle.getString("ImportSpatialPanel.jLabel8.text")); // NOI18N

        groupPanel1.setTitleText(bundle.getString("ImportSpatialPanel.groupPanel1.titleText")); // NOI18N

        groupPanel2.setTitleText(bundle.getString("ImportSpatialPanel.groupPanel2.titleText")); // NOI18N

        btnMove.setText(bundle.getString("ImportSpatialPanel.btnMove.text")); // NOI18N
        btnMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveActionPerformed(evt);
            }
        });

        txtSourceGeometryType.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialBulkMove, org.jdesktop.beansbinding.ELProperty.create("${source.geometryType}"), txtSourceGeometryType, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel9.setText(bundle.getString("ImportSpatialPanel.jLabel9.text")); // NOI18N

        txtSourceFeaturesNr.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spatialBulkMove, org.jdesktop.beansbinding.ELProperty.create("${source.featuresNumber}"), txtSourceFeaturesNr, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel10.setText(bundle.getString("ImportSpatialPanel.jLabel10.text")); // NOI18N

        headerPanel1.setTitleText(bundle.getString("ImportSpatialPanel.headerPanel1.titleText")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(cmbDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbDestinationAttributeName, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(66, 66, 66)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbDestinationAttributeSource, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(90, 90, 90)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(txtDestinationAttributeSourceText, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnMove))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbSourceType, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSourcePath)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdBrowse))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSourceFeaturesNr, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSourceGeometryType, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(groupPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbSourceType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSourcePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdBrowse))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSourceGeometryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtSourceFeaturesNr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(3, 3, 3)
                .addComponent(cmbDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbDestinationAttributeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDestinationAttributeSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDestinationAttributeSourceText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMove)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBrowseActionPerformed
        findAndSetSource();
    }//GEN-LAST:event_cmdBrowseActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (spatialDestinationAttribute.validate(true).size() > 0) {
            return;
        }
        if (spatialBulkMove.getDestinationAttributes().contains(spatialDestinationAttribute)) {
            MessageUtility.displayMessage(
                    ClientMessage.BULK_OPERATIONS_DESTINATION_ATTRIBUTE_EXISTS);
            return;
        }
        spatialBulkMove.getDestinationAttributes().add(
                (SpatialDestinationAttributeBean) spatialDestinationAttribute.copy());
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if (this.selectedDestinationAttributeBean == null) {
            return;
        }
        spatialBulkMove.getDestinationAttributes().remove(this.selectedDestinationAttributeBean);
        this.setSelectedDestinationAttributeBean(null);
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveActionPerformed
        MessageUtility.displayMessage(GisMessage.GENERAL_UNDER_CONSTRUCTION);
    }//GEN-LAST:event_btnMoveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnMove;
    private javax.swing.JButton btnRemove;
    private javax.swing.JComboBox cmbDestination;
    private javax.swing.JComboBox cmbDestinationAttributeName;
    private javax.swing.JComboBox cmbDestinationAttributeSource;
    private javax.swing.JComboBox cmbSourceType;
    private javax.swing.JButton cmdBrowse;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel2;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable listAttributes;
    private org.sola.clients.swing.bulkoperations.beans.SpatialBulkMoveBean spatialBulkMove;
    private org.sola.clients.swing.bulkoperations.beans.SpatialDestinationAttributeBean spatialDestinationAttribute;
    private org.sola.clients.swing.bulkoperations.beans.SpatialDestinationTypeListBean spatialDestinationTypeList;
    private org.sola.clients.swing.bulkoperations.beans.SpatialSourceTypeListBean spatialSourceTypeList;
    private javax.swing.JTextField txtDestinationAttributeSourceText;
    private javax.swing.JTextField txtSourceFeaturesNr;
    private javax.swing.JTextField txtSourceGeometryType;
    private javax.swing.JTextField txtSourcePath;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
