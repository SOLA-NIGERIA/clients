/*
 * Copyright 2012 Food and Agriculture Organization of the United Nations (FAO).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sola.clients.beans.systematicregistration;

import java.math.BigDecimal;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import org.sola.clients.beans.AbstractIdBean;

/**
 *
 * @author RizzoM
 */
public class SysRegCertificatesBean extends AbstractIdBean {

    public static final String NAME_FIRSTPART_PROPERTY = "nameFirstpart";
    public static final String NAME_LASTPART_PROPERTY = "nameLastpart";
    public static final String BAUNIT_PROPERTY = "baUnitId";
    public static final String NR_PROPERTY = "nr";
    public static final String NAME_PROPERTY = "name";
    private String nameFirstpart;
    private String nameLastpart;
    private String baUnitId;
    private String nr;
    private String name;
    private String appId;
    private Date commencingDate;
    private String landUse;
    private String propLocation;
    private BigDecimal size;
    private BigDecimal groundRent;
    private String owners;
    private String title;
    private String state;
    private String ward;
    private String imageryDate;
    private String imageryResolution;
    private String imagerySource;
    private String sheetNr;
    private Integer CofO;
    private String surveyor;
    private String rank;
    private Integer term;
    private BigDecimal rent;
    private BigDecimal stampDuty;
    private Integer premiumStateLand;
    private Integer premiumNonState;

    public BigDecimal getStampDuty() {
        return stampDuty;
    }

    public void setStampDuty(BigDecimal stampDuty) {
        this.stampDuty = stampDuty;
    }

    public Integer getPremiumStateLand() {
        return premiumStateLand;
    }

    public void setPremiumStateLand(Integer premiumStateLand) {
        this.premiumStateLand = premiumStateLand;
    }

    public Integer getPremiumNonState() {
        return premiumNonState;
    }

    public void setPremiumNonState(Integer premiumNonState) {
        this.premiumNonState = premiumNonState;
    }
    
    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }
    
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSurveyor() {
        return surveyor;
    }

    public void setSurveyor(String surveyor) {
        this.surveyor = surveyor;
    }

    public String getImageryResolution() {
        return imageryResolution;
    }

    public void setImageryResolution(String imageryResolution) {
        this.imageryResolution = imageryResolution;
    }

    public String getImagerySource() {
        return imagerySource;
    }

    public void setImagerySource(String imagerySource) {
        this.imagerySource = imagerySource;
    }

    public String getSheetNr() {
        return sheetNr;
    }

    public void setSheetNr(String sheetNr) {
        this.sheetNr = sheetNr;
    }
    
    public Integer getCofO() {
        return CofO;
    }

    public void setCofO(Integer CofO) {
        this.CofO = CofO;
    }
    public String getImageryDate() {
        return imageryDate;
    }

    public void setImageryDate(String imageryDate) {
        this.imageryDate = imageryDate;
    }
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }
      
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }
    
    public BigDecimal getGroundRent() {
        return groundRent;
    }

    public void setGroundRent(BigDecimal groundRent) {
        this.groundRent = groundRent;
    }
    
    
    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }
    
    public String getPropLocation() {
        return propLocation;
    }

    public void setPropLocation(String propLocation) {
        this.propLocation = propLocation;
    }

    public String getLandUse() {
        return landUse;
    }

    public void setLandUse(String landUse) {
        this.landUse = landUse;
    }
       
    public Date getCommencingDate() {
        return commencingDate;
    }

    public void setCommencingDate(Date commencingDate) {
        this.commencingDate = commencingDate;
    }
    
    
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
    
    
    public String getBaUnitId() {
        return baUnitId;
    }

    public void setBaUnitId(String baUnitId) {
        this.baUnitId = baUnitId;
    }

    public String getNameFirstpart() {
        return nameFirstpart;
    }

    public void setNameFirstpart(String nameFirstpart) {
        this.nameFirstpart = nameFirstpart;
    }

    public String getNameLastpart() {
        return nameLastpart;
    }

    public void setNameLastpart(String nameLastpart) {
        this.nameLastpart = nameLastpart;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
