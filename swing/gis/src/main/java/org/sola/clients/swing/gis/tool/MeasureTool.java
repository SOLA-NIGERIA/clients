package org.sola.clients.swing.gis.tool;

import com.vividsolutions.jts.geom.Geometry;
import java.text.DecimalFormat;
import org.geotools.geometry.jts.Geometries;
import org.geotools.swing.extended.util.Messaging;
import org.geotools.swing.tool.extended.ExtendedDrawTool;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Map Tool used to measure distance on the map. Pan and Zoom can 
 * also be used while using this tool. 
 * @author soladev
 */
public class MeasureTool extends ExtendedDrawTool {

    private String toolTip = MessageUtility.getLocalizedMessage(
            GisMessage.MEASURE_TOOLTIP).getMessage();

    public MeasureTool() {
        super();
        this.setGeometryType(Geometries.LINESTRING);
        this.setToolName("measure");
        this.setToolTip(toolTip);
        this.setIconImage("resources/calculate.png");
    }

    /** Triggered when the user double clicks to complete the measure
     * line. Formats the display value as meters or kilometers depending
     * on the length of the measure line and displays a message to inform
     * the user. 
     * @param geometry Geometry representing the measure line
     */
    @Override
    protected void treatFinalizedGeometry(Geometry geometry) {
        Double length = geometry.getLength();
        DecimalFormat formatter = new DecimalFormat("#,###,###.0");
        String messageId = GisMessage.MEASURE_DISTANCE_METERS;
        if (length > new Double(5000)) {
            length = length / 1000;
            messageId = GisMessage.MEASURE_DISTANCE_KILOMETERS;
        }
        Messaging.getInstance().show(messageId, formatter.format(length));
    }

    /**
     * Triggered when the user selects/activates the Measure Tool in the Map
     * toolbar.
     *
     * @param selected true if tool selected/activated, false otherwise.
     */
    @Override
    public void onSelectionChanged(boolean selected) {
        if (selected) {
            // If the MeasureTool is selected, force the redraw of 
            // any existing measure line.
            afterRendering();
        }
    }
}
