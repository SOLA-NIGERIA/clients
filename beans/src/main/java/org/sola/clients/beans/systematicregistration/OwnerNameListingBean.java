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
import org.hibernate.validator.constraints.NotEmpty;
import org.sola.clients.beans.AbstractIdBean;

/**
 *
 * @author RizzoM
 */
public class OwnerNameListingBean extends AbstractIdBean {

    public static final String NAME_FIRSTPART_PROPERTY = "nameFirstpart";
    public static final String NAME_LASTPART_PROPERTY = "nameLastpart";
    public static final String LAND_USECODE_PROPERTY = "landUsecode";
    public static final String LOCATION_PROPERTY = "location";
    public static final String RRR_PROPERTY = "rrr";
    public static final String BAUNIT_PROPERTY = "baUnitId";
    public static final String PUBLIC_NOTIFICATION_DURATION_PROPERTY = "publicNotificationDuration";
    public static final String SIZE_PROPERTY = "size";
    public static final String OBJECTIONS_PROPERTY = "objections";
    public static final String NAME_PROPERTY = "name";
    
    
    private String nameFirstpart;
    private String nameLastpart;
    private String name;
    private String landUsecode;
    private String baUnitId;
    private String value;
    private String objections;
    private BigDecimal size;
    private String publicNotificationDuration;
    private String location;
    private String rrr;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRrr() {
        return rrr;
    }

    public void setRrr(String rrr) {
        this.rrr = rrr;
    }

    public String getPublicNotificationDuration() {
        return publicNotificationDuration;
    }

    public void setPublicNotificationDuration(String publicNotificationDuration) {
        this.publicNotificationDuration = publicNotificationDuration;
    }

    
    public String getBaUnitId() {
        return baUnitId;
    }

    public void setBaUnitId(String baUnitId) {
        this.baUnitId = baUnitId;
    }

    
    public String getLandUsecode() {
        return landUsecode;
    }

    public void setLandUsecode(String landUsecode) {
        this.landUsecode = landUsecode;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
     public String getObjections() {
        return objections;
    }

    public void setObjections(String objections) {
        this.objections = objections;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
