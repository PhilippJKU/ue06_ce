package at.jku.restservice;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Date;

public class HandlebarConfig {
    private BigInteger orderId;
    private String handlebarType;
    private String handlebarMaterial;
    private String handlebarGearshift;
    private String handleMaterial;
    private Date deliveryDate;

    public HandlebarConfig() {
        //empty constructor
    }

    public HandlebarConfig(final BigInteger orderId, final String handlebarType,
        final String handlebarMaterial, final String handlebarGearshift,
        final String handleMaterial, final Date deliveryDate) {
        this.orderId = orderId;
        this.handlebarType = handlebarType;
        this.handlebarMaterial = handlebarMaterial;
        this.handlebarGearshift = handlebarGearshift;
        this.handleMaterial = handleMaterial;
        this.deliveryDate = deliveryDate;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(final BigInteger orderId) {
        this.orderId = orderId;
    }

    public String getHandlebarType() {
        return handlebarType;
    }

    public void setHandlebarType(final String handlebarType) {
        this.handlebarType = handlebarType;
    }

    public String getHandlebarMaterial() {
        return handlebarMaterial;
    }

    public void setHandlebarMaterial(final String handlebarMaterial) {
        this.handlebarMaterial = handlebarMaterial;
    }

    public String getHandlebarGearshift() {
        return handlebarGearshift;
    }

    public void setHandlebarGearshift(final String handlebarGearshift) {
        this.handlebarGearshift = handlebarGearshift;
    }

    public String getHandleMaterial() {
        return handleMaterial;
    }

    public void setHandleMaterial(final String handleMaterial) {
        this.handleMaterial = handleMaterial;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(final Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override public String toString() {
        return MessageFormat
            .format("Order Confirmation: {0} - {1} - {2} - {3} - {4} - {5}", orderId, handlebarType,
                handlebarMaterial, handlebarGearshift, handleMaterial, deliveryDate);
    }
}
