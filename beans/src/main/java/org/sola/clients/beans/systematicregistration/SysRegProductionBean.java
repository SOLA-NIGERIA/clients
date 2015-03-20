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
import org.sola.clients.beans.application.LodgementViewParamsBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.casemanagement.LodgementViewParamsTO;
import org.sola.webservices.transferobjects.casemanagement.SysRegProductionTO;

/**
 * Contains summary properties of the LodgementView object. Could be populated
 * from the {@link LodgementViewTO} object.<br /> For more information see UC
 * <b>Lodgement Report</b> schema.
 */
public class SysRegProductionBean extends AbstractIdBean {
    private String ownerName;
    private String typeCode;
    private BigDecimal monday;
    private BigDecimal tuesday;
    private BigDecimal wednesday;
    private BigDecimal thursday;
    private BigDecimal friday;
    private BigDecimal saturday;
    private BigDecimal sunday;
    private ObservableList<SysRegProductionBean> productionList;
    
     public SysRegProductionBean() {
        super();
        productionList = ObservableCollections.observableList(new LinkedList<SysRegProductionBean>());
    }

     public ObservableList<SysRegProductionBean> getProductionList() {
        return productionList;
    }

    public void setProductionList(ObservableList<SysRegProductionBean> productionList) {
        this.productionList = productionList;
    }

    public BigDecimal getFriday() {
        return friday;
    }

    public void setFriday(BigDecimal friday) {
        this.friday = friday;
    }

    public BigDecimal getMonday() {
        return monday;
    }

    public void setMonday(BigDecimal monday) {
        this.monday = monday;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public BigDecimal getSaturday() {
        return saturday;
    }

    public void setSaturday(BigDecimal saturday) {
        this.saturday = saturday;
    }

    public BigDecimal getSunday() {
        return sunday;
    }

    public void setSunday(BigDecimal sunday) {
        this.sunday = sunday;
    }

    public BigDecimal getThursday() {
        return thursday;
    }

    public void setThursday(BigDecimal thursday) {
        this.thursday = thursday;
    }

    public BigDecimal getTuesday() {
        return tuesday;
    }

    public void setTuesday(BigDecimal tuesday) {
        this.tuesday = tuesday;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public BigDecimal getWednesday() {
        return wednesday;
    }

    public void setWednesday(BigDecimal wednesday) {
        this.wednesday = wednesday;
    }
    
        /**
     * Returns collection of {@link ApplicationBean} objects. This method is
     * used by Jasper report designer to extract properties of application bean
     * to help design a report.
     */
    public static java.util.Collection generateCollection() {
        java.util.Vector collection = new java.util.Vector();
        SysRegProductionBean bean = new SysRegProductionBean();
        collection.add(bean);
        return collection;
    }

    //      /** Passes from date and to date search criteria. */
    public void passParameter(LodgementViewParamsBean params) {
        LodgementViewParamsTO paramsTO = TypeConverters.BeanToTrasferObject(params,
                LodgementViewParamsTO.class);
           
        List<SysRegProductionTO> productionViewTO =
                WSManager.getInstance().getCaseManagementService().getSysRegProduction(paramsTO);
        TypeConverters.TransferObjectListToBeanList(productionViewTO,
                SysRegProductionBean.class, (List) this.getProductionList());
    }
}
