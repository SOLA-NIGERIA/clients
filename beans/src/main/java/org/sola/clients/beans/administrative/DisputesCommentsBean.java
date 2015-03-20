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
/**
 *
 * 
 */
package org.sola.clients.beans.administrative;

import java.util.Date;
import org.sola.clients.beans.AbstractTransactionedBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.administrative.DisputeCommentsTO;
import org.sola.clients.beans.referencedata.OtherAuthoritiesBean;

public class DisputesCommentsBean extends DisputeBean {

    public static final String DISPUTE_NR_PROPERTY = "disputeNr";
    public static final String UPDATE_DATE_PROPERTY = "updateDate";
    public static final String DISPUTE_ACTION_PROPERTY = "disputeAction";
    public static final String OTHER_AUTHORITIES_PROPERTY = "otherAuthorities";
    public static final String COMMENTS = "comments";
    public static final String UPDATED_BY = "updatedBy";
    public static final String SELECTED_AUTHORITY_PROPERTY = "selectedAuthority";
    
    
    private String disputeNr;
    private Date updateDate;
    private OtherAuthoritiesBean otherAuthoritiesCode;
    private String comments;
    private String updatedBy;
    private DisputeBean disputeBean;

    public DisputesCommentsBean() {
        super();
    }

    public void clean() {
        this.setDisputeNr(null);
        this.setUpdateDate(null);
        this.setOtherAuthoritiesCode(null);
        this.setComments(null);
        this.setUpdatedBy(null);

    }

    public String getComments() {
        return comments;
    }

    public void setComments(String value) {
        String old = comments;
        comments = value;
        propertySupport.firePropertyChange(COMMENTS, old, comments);
    }

    public String getDisputeNr() {
        return disputeNr;
    }

    public void setDisputeNr(String value) {
            String old = disputeNr;
            disputeNr = value;
            propertySupport.firePropertyChange(DISPUTE_NR_PROPERTY, old, disputeNr);
       
    }


    public String getOtherAuthoritiesCode() {
        if (otherAuthoritiesCode != null) {
            return otherAuthoritiesCode.getCode();
        } else {
            return null;
        }
    }

    public void setOtherAuthoritiesCode(String OtherAuthoritiesCode) {
        String oldValue = null;
        if (otherAuthoritiesCode != null) {
            oldValue = otherAuthoritiesCode.getCode();

            return;

        }
        setOtherAuthorities(CacheManager.getBeanByCode(
                CacheManager.getOtherAuthorities(), OtherAuthoritiesCode));

        propertySupport.firePropertyChange(OTHER_AUTHORITIES_PROPERTY,
                oldValue, OtherAuthoritiesCode);
    }

    public OtherAuthoritiesBean getOtherAuthorities() {
        if (this.otherAuthoritiesCode == null) {
            this.otherAuthoritiesCode = new OtherAuthoritiesBean();
        }
        return otherAuthoritiesCode;
    }

    public void setOtherAuthorities(OtherAuthoritiesBean otherAuthorities) {
        if (this.otherAuthoritiesCode == null) {
            this.otherAuthoritiesCode = new OtherAuthoritiesBean();
        }
        this.setJointRefDataBean(this.otherAuthoritiesCode, otherAuthorities, OTHER_AUTHORITIES_PROPERTY);
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date value) {
        Date old = updateDate;
        updateDate = value;
        propertySupport.firePropertyChange(UPDATE_DATE_PROPERTY, old, updateDate);
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String value) {
        String old = updatedBy;
        updatedBy = value;
        propertySupport.firePropertyChange(UPDATED_BY, old, updatedBy);
    }

    public boolean saveDisputeComments() {
        DisputeCommentsTO disputeComments = TypeConverters.BeanToTrasferObject(this, DisputeCommentsTO.class);
        disputeComments = WSManager.getInstance().getAdministrative().saveDisputeComments(disputeComments);
        TypeConverters.TransferObjectToBean(disputeComments, DisputesCommentsBean.class, this);
        return true;
    }

    public static DisputesCommentsBean getDisputeCommentsById(String disputeCommentsId) {
        return TypeConverters.TransferObjectToBean(
                WSManager.getInstance().getAdministrative().getDisputeCommentsById(disputeCommentsId),
                DisputesCommentsBean.class, null);
    }
}
