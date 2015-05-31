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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.bulkoperations.beans;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.swing.extended.util.GeometryUtility;
import org.kabeja.dxf.Bounds;
import org.kabeja.dxf.DXFConstants;

import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;

/**
 *
 * @author Elton Manoku
 */
public class SpatialSourceDxfBean extends SpatialSourceBean {

    private DXFDocument doc = null;
    private final String ATTRIBUTE_LABEL = "label";
    private final String ATTRIBUTE_AREA = "area";
    private List<DxfEntityBean> dxfEntityBeans;

    public SpatialSourceDxfBean() {
        super();
        setCode("dxf");
        setDisplayValue("Dxf");
    }

    private void parseDxfDocument() {
        dxfEntityBeans = new ArrayList<DxfEntityBean>();
        if (getSourceFile() == null) {
            return;
        }
        Parser parser = ParserBuilder.createDefaultParser();
        try {
            //parse
            parser.parse(new FileInputStream(getSourceFile()), DXFParser.DEFAULT_ENCODING);

            //get the documnet
            doc = parser.getDocument();
            Iterator layerIterator = doc.getDXFLayerIterator();
            while (layerIterator.hasNext()) {
                DXFLayer layer = (DXFLayer) layerIterator.next();
                List<DXFEntity> entities = new ArrayList<DXFEntity>();
                if (layer.hasDXFEntities(DXFConstants.ENTITY_TYPE_POLYLINE)) {
                    entities.addAll(layer.getDXFEntities(DXFConstants.ENTITY_TYPE_POLYLINE));
                }
                if (layer.hasDXFEntities(DXFConstants.ENTITY_TYPE_LWPOLYLINE)) {
                    entities.addAll(layer.getDXFEntities(DXFConstants.ENTITY_TYPE_LWPOLYLINE));
                }
                if (layer.hasDXFEntities(DXFConstants.ENTITY_TYPE_POINT)) {
                    entities.addAll(layer.getDXFEntities(DXFConstants.ENTITY_TYPE_POINT));
                }
                if (layer.hasDXFEntities(DXFConstants.ENTITY_TYPE_TEXT)) {
                    entities.addAll(layer.getDXFEntities(DXFConstants.ENTITY_TYPE_TEXT));
                }
                if (layer.hasDXFEntities(DXFConstants.ENTITY_TYPE_ATTRIB)) {
                    entities.addAll(layer.getDXFEntities(DXFConstants.ENTITY_TYPE_ATTRIB));
                }
                setGeometryType("Point");

                for (DXFEntity entity : entities) {
                    DxfEntityBean bean = new DxfEntityBean(entity);
                    if (!getGeometryType().equals("Polygon")) {
                        if (bean.getGeometryType().equals("Polygon")) {
                            setGeometryType("Polygon");
                        } else if (bean.getGeometryType().equals("LineString")) {
                            setGeometryType("LineString");
                        }
                    }
                    dxfEntityBeans.add(bean);
                }
            }

        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        List<DxfEntityBean> tmpList = new ArrayList();
        int label_generator = 1;

        for (DxfEntityBean bean : dxfEntityBeans) {
            if (bean.getGeometryType().equals(getGeometryType())) {
                if (bean.getLabel().isEmpty()) {
                    bean.setLabel(String.format("%s", label_generator++));
                }
                tmpList.add(bean);
            }
        }
        dxfEntityBeans = tmpList;
    }

    @Override
    protected void loadAttributes() {
        parseDxfDocument();
        setFeaturesNumber(dxfEntityBeans.size());
        getAttributes().clear();
        SpatialAttributeBean attributeBean = new SpatialAttributeBean();
        attributeBean.setName(ATTRIBUTE_LABEL);
        attributeBean.setDataType("String");
        getAttributes().add(attributeBean);
        if (getGeometryType().equals("Polygon")) {
            attributeBean = new SpatialAttributeBean();
            attributeBean.setName(ATTRIBUTE_AREA);
            attributeBean.setDataType("Double");
            getAttributes().add(attributeBean);
        }
    }

    @Override
    protected List<SpatialSourceObjectBean> getFeatures(List<SpatialAttributeBean> onlyAttributes) {
        List<SpatialSourceObjectBean> spatialObjectList = new ArrayList<SpatialSourceObjectBean>();
        for (DxfEntityBean bean : dxfEntityBeans) {
            SpatialSourceObjectBean spatialObject = new SpatialSourceObjectBean();
            bean.getGeometry().setSRID(getSrid());
            spatialObject.setTheGeom(GeometryUtility.getWkbFromGeometry(bean.getGeometry()));
            spatialObject.getFieldsWithValues().put(ATTRIBUTE_LABEL, bean.getLabel());
            if (getGeometryType().equals("Polygon")) {
                spatialObject.getFieldsWithValues().put(
                        ATTRIBUTE_AREA, String.format("%s", bean.getGeometry().getArea()));
            }
            spatialObjectList.add(spatialObject);
        }
        return spatialObjectList;
    }

    @Override
    public ReferencedEnvelope getExtent() {
        Bounds bounds = doc.getBounds();
        return new ReferencedEnvelope(
                bounds.getMinimumX(), bounds.getMaximumX(),
                bounds.getMinimumY(), bounds.getMaximumY(),
                null);
    }
}
