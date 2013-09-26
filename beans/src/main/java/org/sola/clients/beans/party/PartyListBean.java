/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations (FAO).
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
package org.sola.clients.beans.party;

import java.util.List;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.EntityAction;
import org.sola.webservices.transferobjects.casemanagement.PartyTO;

/**
 * Holds the list of {@link PartyBean} objects.
 */
public class PartyListBean extends AbstractBindingBean {
    
    public static final String SELECTED_PARTY_PROPERTY = "selectedParty";
    public static final String PARTY_LIST_PROPERTY = "partyBeanList";
    private SolaObservableList<PartyBean> partyListBean;
    private PartyBean selectedParty;
    
    /** Creates new instance of object and initializes {@link PartyBean} list.*/
    public PartyListBean() {
        partyListBean = new SolaObservableList<PartyBean>();
    }

    public ObservableList<PartyBean> getPartyBeanList()
    {
        return partyListBean;
    }

    public PartyBean getSelectedParty() {
        return selectedParty;
    }

    public void setSelectedParty(PartyBean value) {
        selectedParty = value;
        propertySupport.firePropertyChange(SELECTED_PARTY_PROPERTY, null, value);
    }
    
     /** 
     * Fills {@link ObservableList}&lt;{@link PartySummaryBean}&gt; with the list of agents.
     * @param createDummyAgent If true, creates empty {@link PartySummaryBean} agent 
     * to display empty option in the list.
     */
    public void FillAgents(boolean createDummyAgent) {
        List<PartyTO> lst = WSManager.getInstance().getCaseManagementService().getAgents();
        partyListBean.clear();
        TypeConverters.TransferObjectListToBeanList(lst, PartyBean.class, (List)partyListBean);
        if(createDummyAgent){
            PartyBean dummyAgent = new PartyBean();
            dummyAgent.setName(" ");
            dummyAgent.setEntityAction(EntityAction.DISASSOCIATE);
            partyListBean.add(0, dummyAgent);
        }
    }
    
    
       /** 
     * Fills {@link ObservableList}&lt;{@link PartySummaryBean}&gt; with the list of recording officers.
     * @param createDummyAgent If true, creates empty {@link PartySummaryBean} agent 
     * to display empty option in the list.
     */
    public void FillRecOfficers(boolean createDummyRecOff) {
        List<PartyTO> lst = WSManager.getInstance().getCaseManagementService().getRecOfficers();
        partyListBean.clear();
        TypeConverters.TransferObjectListToBeanList(lst, PartyBean.class, (List)partyListBean);
        if(createDummyRecOff){
            PartyBean dummyRecOff = new PartyBean();
            dummyRecOff.setName(" ");
            dummyRecOff.setEntityAction(EntityAction.DISASSOCIATE);
            partyListBean.add(0, dummyRecOff);
        }
    }
    
       /** 
     * Fills {@link ObservableList}&lt;{@link PartySummaryBean}&gt; with the list of recording officers.
     * @param createDummyAgent If true, creates empty {@link PartySummaryBean} agent 
     * to display empty option in the list.
     */
    public void FillDemOfficers(boolean createDummyDemOff) {
        List<PartyTO> lst = WSManager.getInstance().getCaseManagementService().getDemOfficers();
        partyListBean.clear();
        TypeConverters.TransferObjectListToBeanList(lst, PartyBean.class, (List)partyListBean);
        if(createDummyDemOff){
            PartyBean dummyDemOff = new PartyBean();
            dummyDemOff.setName(" ");
            dummyDemOff.setEntityAction(EntityAction.DISASSOCIATE);
            partyListBean.add(0, dummyDemOff);
        }
    }

}
