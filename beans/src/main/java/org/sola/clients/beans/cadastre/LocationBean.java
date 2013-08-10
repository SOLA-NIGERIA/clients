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
package org.sola.clients.beans.cadastre;

/**
 *
 * @author RizzoM
 */
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.sola.clients.beans.AbstractTransactionedBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.referencedata.CadastreObjectTypeBean;
import org.sola.clients.beans.validation.Localized;
import org.sola.common.messaging.ClientMessage;
//import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;
import org.sola.webservices.transferobjects.cadastre.SpatialUnitGroupTO;

/** 
 * Contains properties and methods to manage <b>Cadastre</b> object of the 
 * domain model. Could be populated from the {@link SpatialUnitGroupTO} object.
 */
public class LocationBean extends AbstractTransactionedBean {

    
    public static final String NAME_PROPERTY = "name";
    public static final String GEOM_PROPERTY = "geom";
   
    
    @Length(max = 50, message =  ClientMessage.CHECK_FIELD_INVALID_LENGTH_NAME, payload=Localized.class)
    @NotEmpty(message =  ClientMessage.CHECK_NOTNULL_NAME, payload=Localized.class)
    private String name;
    private byte[] geom;
    
     public LocationBean() {
        super();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     
    @Override
    public String toString() {
        String result = "";
        result += name; 
        return result;
    }
    
}
