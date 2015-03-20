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
package org.sola.clients.beans.systematicregistration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.AbstractTransactionedBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.cadastre.SysRegWorkUnitTO;

/**
 *
 * @author rizzom
 */


public class SysRegWorkUnitBean extends AbstractTransactionedBean {
  
    public static final String WORKUNIT_PROPERTY = "sysregworkunit";
    public static final String DISTRIBUTED_PROPERTY  = "distributedcertificates";
    public static final String  ESTIMATED_PROPERTY   = "estimatedparcel";
    public static final String  NAME_PROPERTY    = "name";
    public static final String  RECORDED_PARCEL_PROPERTY    = "recordedparcel";
    public static final String  RECORDED_CLAIMS_PROPERTY    = "recordedclaims";
    public static final String  SCANNED_PARCEL_PROPERTY    = "scanneddemarcation";
    public static final String  SCANNED_CLAIMS_PROPERTY    = "scannedclaims";
    public static final String  DATE_PROPERTY    = "publicdisplaystartdate";
    private ObservableList<SysRegWorkUnitBean> workUnitList;
    
    private String name;
        private BigDecimal estimatedparcel;
        private BigDecimal recordedparcel;
        private BigDecimal recordedclaims;
        private BigDecimal scanneddemarcation;
        private BigDecimal scannedclaims;
        private BigDecimal distributedcertificates;
        private Date publicdisplaystartdate;
        
        
        
        
    public SysRegWorkUnitBean () {
       super();
        workUnitList = ObservableCollections.observableList(new LinkedList<SysRegWorkUnitBean>());
   
    }
     

    public BigDecimal getDistributedcertificates() {
        return distributedcertificates;
    }

    public void setDistributedcertificates(BigDecimal distributedcertificates) {
        this.distributedcertificates = distributedcertificates;
          propertySupport.firePropertyChange(DISTRIBUTED_PROPERTY, null, this);
      
    }

    public BigDecimal getEstimatedparcel() {
        return estimatedparcel;
    }

    public void setEstimatedparcel(BigDecimal estimatedparcel) {
        this.estimatedparcel = estimatedparcel;
         propertySupport.firePropertyChange(ESTIMATED_PROPERTY, null, this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
         propertySupport.firePropertyChange(NAME_PROPERTY, null, this);
    }

    public Date getPublicdisplaystartdate() {
        return publicdisplaystartdate;
    }

    public void setPublicdisplaystartdate(Date publicdisplaystartdate) {
        this.publicdisplaystartdate = publicdisplaystartdate;
           propertySupport.firePropertyChange(DATE_PROPERTY, null, this);
 }

    public BigDecimal getRecordedclaims() {
        return recordedclaims;
    }

    public void setRecordedclaims(BigDecimal recordedclaims) {
        this.recordedclaims = recordedclaims;
        propertySupport.firePropertyChange(RECORDED_CLAIMS_PROPERTY, null, this);
 
    }

    public BigDecimal getRecordedparcel() {
        return recordedparcel;
    }

    public void setRecordedparcel(BigDecimal recordedparcel) {
        this.recordedparcel = recordedparcel;
        propertySupport.firePropertyChange(RECORDED_PARCEL_PROPERTY, null, this);
 
    }

    public BigDecimal getScannedclaims() {
        return scannedclaims;
    }

    public void setScannedclaims(BigDecimal scannedclaims) {
        this.scannedclaims = scannedclaims;
        propertySupport.firePropertyChange(SCANNED_CLAIMS_PROPERTY, null, this);
  
    }

    public BigDecimal getScanneddemarcation() {
        return scanneddemarcation;
    }

    public void setScanneddemarcation(BigDecimal scanneddemarcation) {
        this.scanneddemarcation = scanneddemarcation;
        propertySupport.firePropertyChange(SCANNED_PARCEL_PROPERTY, null, this);
 
    }

    public ObservableList<SysRegWorkUnitBean> getWorkUnitList() {
        return workUnitList;
    }

    public void setWorkUnitList(ObservableList<SysRegWorkUnitBean> workUnitList) {
        this.workUnitList = workUnitList;
    }
        //      /** Passes from date and to date search criteria. */
    public void passParameter(String params) {
        SysRegWorkUnitTO srwuTO =
                WSManager.getInstance().getCadastreService().getSysRegWorkUnitByAllParts(params);
               TypeConverters.TransferObjectToBean(srwuTO,SysRegWorkUnitBean.class,this);
    
    } 
    
     public boolean saveSysRegWorkUnit(SysRegWorkUnitBean value) {
        SysRegWorkUnitTO toWorkUnit = TypeConverters.BeanToTrasferObject(this, SysRegWorkUnitTO.class);
        toWorkUnit = WSManager.getInstance().getCadastreService().saveSysRegWorkUnit(toWorkUnit);
        TypeConverters.TransferObjectToBean(toWorkUnit, SysRegWorkUnitBean.class, this);
        propertySupport.firePropertyChange(WORKUNIT_PROPERTY, null, this);
       return true;
    }
}
