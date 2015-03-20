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

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.geotools.swing.extended.util.GeometryUtility;
import org.kabeja.dxf.DXFAttrib;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFPoint;
import org.kabeja.dxf.DXFPolyline;
import org.kabeja.dxf.DXFText;
import org.kabeja.dxf.DXFVertex;

/**
 * Represents an entity from a DXF document.
 *
 * @author Elton Manoku
 */
public class DxfEntityBean {

    private Geometry geometry;
    private String label="";

    public DxfEntityBean(DXFEntity entity) {
        if (entity.getType().equals(DXFConstants.ENTITY_TYPE_POLYLINE)) {
            geometry = getGeometryFromEntityPolyline((DXFPolyline) entity);
        }        if (entity.getType().equals(DXFConstants.ENTITY_TYPE_LWPOLYLINE)) {
            geometry = getGeometryFromEntityPolyline((DXFPolyline) entity);
        } else if (entity.getType().equals(DXFConstants.ENTITY_TYPE_POINT)) {
            geometry = getGeometryFromEntityWithPoint(
                    ((DXFPoint) entity).getX(),
                    ((DXFPoint) entity).getY());
        } else if (entity.getType().equals(DXFConstants.ENTITY_TYPE_TEXT)) {
            geometry = getGeometryFromEntityWithPoint(
                    ((DXFText) entity).getInsertPoint().getX(),
                    ((DXFText) entity).getInsertPoint().getY());
            label = ((DXFText) entity).getText();
        } else if (entity.getType().equals(DXFConstants.ENTITY_TYPE_ATTRIB)) {
            getGeometryFromEntityWithPoint(
                    ((DXFAttrib) entity).getInsertPoint().getX(),
                    ((DXFAttrib) entity).getInsertPoint().getY());
            label = ((DXFAttrib) entity).getText();
        }
    }

    private Geometry getGeometryFromEntityPolyline(DXFPolyline polyline) {
        Coordinate[] coordinates = new Coordinate[polyline.getVertexCount()];
        for (int i = 0; i < polyline.getVertexCount(); i++) {
            DXFVertex vertex = polyline.getVertex(i);
            coordinates[i] = new Coordinate(vertex.getX(), vertex.getY());
        }
        Geometry geom;
        if (coordinates[0].equals2D(coordinates[coordinates.length - 1])) {
            //It means it is a polygon
            geom = GeometryUtility.getGeometryFactory().createPolygon(
                    GeometryUtility.getGeometryFactory().createLinearRing(coordinates), null);
        } else {
            geom = GeometryUtility.getGeometryFactory().createLineString(coordinates);
        }
        return geom;
    }

    private Geometry getGeometryFromEntityWithPoint(double x, double y) {
        return GeometryUtility.getGeometryFactory().createPoint(new Coordinate(x, y));
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getGeometryType(){
        return geometry.getGeometryType();
    }
}
