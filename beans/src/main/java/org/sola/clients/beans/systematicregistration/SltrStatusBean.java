/*
 * Copyright 2016 Food and Agriculture Organization of the United Nations (FAO).
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

import java.util.List;
import org.sola.clients.beans.AbstractIdBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.casemanagement.SltrStatusTO;

/**
 *
 * @author rizzom
 */
public class SltrStatusBean extends AbstractIdBean {
    private String sltrStatus;
    private String appid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
    
    public String getSltrStatus() {
        return sltrStatus;
    }

    public void setSltrStatus(String sltrStatus) {
        this.sltrStatus = sltrStatus;
    }
    
     public SltrStatusBean passParameter(String params) {
        System.out.println("PASS PARAMETER    "+params);
         SltrStatusBean sltrStatus;
         List<SltrStatusTO> sltrStatusTO =
                WSManager.getInstance().getCaseManagementService().getSltrStatus(params);
              
               sltrStatus= TypeConverters.TransferObjectToBean(sltrStatusTO,SltrStatusBean.class,this);
           return sltrStatus;
    } 
    
}
