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
import java.util.LinkedList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractIdBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.webservices.transferobjects.administrative.SysRegStatusTO;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.administrative.SysRegManagementParamsTO;

/**
 * Contains summary properties of the LodgementView object. Could be populated
 * from the {@link LodgementViewTO} object.<br /> For more information see UC
 * <b>Lodgement Report</b> schema.
 */
public class SysRegStatusBean extends AbstractIdBean {

    private ObservableList<SysRegStatusBean> statusList;
    private String     srwu;
    private BigDecimal estimatedparcel;
    private BigDecimal recordedparcel;
    private BigDecimal recordedclaims;
    private BigDecimal scanneddemarcation;
    private BigDecimal scannedclaims;
    private BigDecimal digitizedparcels;
    private BigDecimal claimsentered;
    private BigDecimal parcelsreadyPD;
    private BigDecimal parcelsPD;
    private BigDecimal 	parcelscompletedPD;
    private BigDecimal unsolveddisputes;
    private BigDecimal generatedcertificates;
    private BigDecimal distributedcertificates;

 
    public SysRegStatusBean() {
        super();
        statusList = ObservableCollections.observableList(new LinkedList<SysRegStatusBean>());
    }

    public ObservableList<SysRegStatusBean> getStatusList() {
        return statusList;
    }

    public void setStatusList(ObservableList<SysRegStatusBean> statusList) {
        this.statusList = statusList;
    }

    public BigDecimal getClaimsentered() {
        return claimsentered;
    }

    public void setClaimsentered(BigDecimal claimsentered) {
        this.claimsentered = claimsentered;
    }

    public BigDecimal getDigitizedparcels() {
        return digitizedparcels;
    }
  
    public void setDigitizedparcels(BigDecimal digitizedparcels) {
        this.digitizedparcels = digitizedparcels;
    }

    public BigDecimal getDistributedcertificates() {
        return distributedcertificates;
    }

    public void setDistributedcertificates(BigDecimal distributedcertificates) {
        this.distributedcertificates = distributedcertificates;
    }

    public BigDecimal getEstimatedparcel() {
        return estimatedparcel;
    }

    public void setEstimatedparcel(BigDecimal estimatedparcel) {
        this.estimatedparcel = estimatedparcel;
    }

    public BigDecimal getGeneratedcertificates() {
        return generatedcertificates;
    }

    public void setGeneratedcertificates(BigDecimal generatedcertificates) {
        this.generatedcertificates = generatedcertificates;
    }

    public BigDecimal getParcelsPD() {
        return parcelsPD;
    }

    public void setParcelsPD(BigDecimal parcelsPD) {
        this.parcelsPD = parcelsPD;
    }

    public BigDecimal getParcelscompletedPD() {
        return parcelscompletedPD;
    }

    public void setParcelscompletedPD(BigDecimal parcelscompletedPD) {
        this.parcelscompletedPD = parcelscompletedPD;
    }

    public BigDecimal getParcelsreadyPD() {
        return parcelsreadyPD;
    }

    public void setParcelsreadyPD(BigDecimal parcelsreadyPD) {
        this.parcelsreadyPD = parcelsreadyPD;
    }

    public BigDecimal getRecordedclaims() {
        return recordedclaims;
    }

    public void setRecordedclaims(BigDecimal recordedclaims) {
        this.recordedclaims = recordedclaims;
    }

    public BigDecimal getRecordedparcel() {
        return recordedparcel;
    }

    public void setRecordedparcel(BigDecimal recordedparcel) {
        this.recordedparcel = recordedparcel;
    }

    public BigDecimal getScannedclaims() {
        return scannedclaims;
    }

    public void setScannedclaims(BigDecimal scannedclaims) {
        this.scannedclaims = scannedclaims;
    }

    public BigDecimal getScanneddemarcation() {
        return scanneddemarcation;
    }

    public void setScanneddemarcation(BigDecimal scanneddemarcation) {
        this.scanneddemarcation = scanneddemarcation;
    }

    public String getSrwu() {
        return srwu;
    }

    public void setSrwu(String srwu) {
        this.srwu = srwu;
    }

    public BigDecimal getUnsolveddisputes() {
        return unsolveddisputes;
    }

    public void setUnsolveddisputes(BigDecimal unsolveddisputes) {
        this.unsolveddisputes = unsolveddisputes;
    }

    
    /**
     * Returns collection of {@link ApplicationBean} objects. This method is
     * used by Jasper report designer to extract properties of application bean
     * to help design a report.
     */
    public static java.util.Collection generateCollection() {
        java.util.Vector collection = new java.util.Vector();
        SysRegStatusBean bean = new SysRegStatusBean();
        collection.add(bean);
        return collection;
    }

    //      /** Passes from date and to date search criteria. */
    public void passParameter(SysRegManagementParamsBean params) {
        SysRegManagementParamsTO paramsTO = TypeConverters.BeanToTrasferObject(params,
                SysRegManagementParamsTO.class);
           
        List<SysRegStatusTO> statusViewTO =
                WSManager.getInstance().getAdministrative().getSysRegStatus(paramsTO);
        TypeConverters.TransferObjectListToBeanList(statusViewTO,
                SysRegStatusBean.class, (List) this.getStatusList());
    }
}