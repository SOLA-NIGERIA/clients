/*
 * Copyright 2013 Food and Agriculture Organization of the United Nations (FAO).
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
package org.sola.clients.beans.referencedata;

import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingListBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaCodeList;

/**
 * Holds the list of {@link RrrConditionBean} objects and used to bound the
 * data in the combobox on the forms.
 */
public class RrrConditionListBean extends AbstractBindingListBean {
    
    public static final String SELECTED_LEASE_CONDITION_PROPERTY = "selectedRrrCondition";
    private SolaCodeList<RrrConditionBean> RrrConditionList;
    private RrrConditionBean selectedRrrCondition;
    
    public RrrConditionListBean(){
        this(false);
    }

    /** 
     * Creates object instance.
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public RrrConditionListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }
    
    /** 
     * Creates object instance.
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public RrrConditionListBean(boolean createDummy, String ... excludedCodes) {
        super();
        RrrConditionList = new SolaCodeList<RrrConditionBean>(excludedCodes);
        loadList(createDummy);
    }
    
    /** 
     * Loads list of {@link RrrConditionBean}.
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(RrrConditionBean.class, RrrConditionList, 
                CacheManager.getRrrConditions(), createDummy);
    }
    
    public ObservableList<RrrConditionBean> getRrrConditionList() {
        return RrrConditionList.getFilteredList();
    }

    public void setExcludedCodes(String ... codes){
        RrrConditionList.setExcludedCodes(codes);
    }
    
    public RrrConditionBean getSelectedRrrCondition() {
        return selectedRrrCondition;
    }

    public void setSelectedRrrCondition(RrrConditionBean selectedRrrCondition) {
        this.selectedRrrCondition = selectedRrrCondition;
    }
}
