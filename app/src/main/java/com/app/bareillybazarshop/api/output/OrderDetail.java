package com.app.bareillybazarshop.api.output;

public class OrderDetail {

    private String orderId;
    private String orderPlacedBy;
    private String orderPlacedTo;
    private String orderQuotedAmount;
    private String orderDeliveryType;
    private String orderPaymentMethod;
    private String orderStatus;
    private String orderCustomMessage; //
    private String orderExpectedDeliveryTimestamp;//
    private String orderDeliveryAddressIdentifier;
    private String orderCreationTimestamp;//

    /// new field
    private String orderAcceptedExpectedDeliveryTimestamp;
    private String orderBeingShippedBy;
    private String orderPickedUpForDeliveryTimestamp;
    private String orderDeliveryTimestamp;
    private String orderReceiptAcknowledgement;
    private String orderInvoiceAmount;
    private String orderReceiptAcknowledgementCollected;
    private String orderInvoiceAmountCollected;
    private String orderDisputeResolutionComment;
    private String orderCancellationReason;
    private Object orderDisputeResolvedBy;
    private String orderMarkedDisputedBy;
    private String orderMarkedAsDisputed;


    /**
     * @return The orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId The orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return The orderPlacedBy
     */
    public String getOrderPlacedBy() {
        return orderPlacedBy;
    }

    /**
     * @param orderPlacedBy The orderPlacedBy
     */
    public void setOrderPlacedBy(String orderPlacedBy) {
        this.orderPlacedBy = orderPlacedBy;
    }

    /**
     * @return The orderPlacedTo
     */
    public String getOrderPlacedTo() {
        return orderPlacedTo;
    }

    /**
     * @param orderPlacedTo The orderPlacedTo
     */
    public void setOrderPlacedTo(String orderPlacedTo) {
        this.orderPlacedTo = orderPlacedTo;
    }

    /**
     * @return The orderQuotedAmount
     */
    public String getOrderQuotedAmount() {
        return orderQuotedAmount;
    }

    /**
     * @param orderQuotedAmount The orderQuotedAmount
     */
    public void setOrderQuotedAmount(String orderQuotedAmount) {
        this.orderQuotedAmount = orderQuotedAmount;
    }

    /**
     * @return The orderDeliveryType
     */
    public String getOrderDeliveryType() {
        return orderDeliveryType;
    }

    /**
     * @param orderDeliveryType The orderDeliveryType
     */
    public void setOrderDeliveryType(String orderDeliveryType) {
        this.orderDeliveryType = orderDeliveryType;
    }

    /**
     * @return The orderPaymentMethod
     */
    public String getOrderPaymentMethod() {
        return orderPaymentMethod;
    }

    /**
     * @param orderPaymentMethod The orderPaymentMethod
     */
    public void setOrderPaymentMethod(String orderPaymentMethod) {
        this.orderPaymentMethod = orderPaymentMethod;
    }

    /**
     * @return The orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus The orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return The orderDeliveryAddressIdentifier
     */
    public String getOrderDeliveryAddressIdentifier() {
        return orderDeliveryAddressIdentifier;
    }

    /**
     * @param orderDeliveryAddressIdentifier The orderDeliveryAddressIdentifier
     */
    public void setOrderDeliveryAddressIdentifier(String orderDeliveryAddressIdentifier) {
        this.orderDeliveryAddressIdentifier = orderDeliveryAddressIdentifier;
    }

    public String getOrderCustomMessage() {
        return orderCustomMessage;
    }

    public void setOrderCustomMessage(String orderCustomMessage) {
        this.orderCustomMessage = orderCustomMessage;
    }

    public String getOrderExpectedDeliveryTimestamp() {
        return orderExpectedDeliveryTimestamp;
    }

    public void setOrderExpectedDeliveryTimestamp(String orderExpectedDeliveryTimestamp) {
        this.orderExpectedDeliveryTimestamp = orderExpectedDeliveryTimestamp;
    }

    public String getOrderCreationTimestamp() {
        return orderCreationTimestamp;
    }

    public void setOrderCreationTimestamp(String orderCreationTimestamp) {
        this.orderCreationTimestamp = orderCreationTimestamp;
    }

    public String getOrderAcceptedExpectedDeliveryTimestamp() {
        return orderAcceptedExpectedDeliveryTimestamp;
    }

    public void setOrderAcceptedExpectedDeliveryTimestamp(String orderAcceptedExpectedDeliveryTimestamp) {
        this.orderAcceptedExpectedDeliveryTimestamp = orderAcceptedExpectedDeliveryTimestamp;
    }

    public String getOrderBeingShippedBy() {
        return orderBeingShippedBy;
    }

    public void setOrderBeingShippedBy(String orderBeingShippedBy) {
        this.orderBeingShippedBy = orderBeingShippedBy;
    }

    public String getOrderPickedUpForDeliveryTimestamp() {
        return orderPickedUpForDeliveryTimestamp;
    }

    public void setOrderPickedUpForDeliveryTimestamp(String orderPickedUpForDeliveryTimestamp) {
        this.orderPickedUpForDeliveryTimestamp = orderPickedUpForDeliveryTimestamp;
    }

    public String getOrderDeliveryTimestamp() {
        return orderDeliveryTimestamp;
    }

    public void setOrderDeliveryTimestamp(String orderDeliveryTimestamp) {
        this.orderDeliveryTimestamp = orderDeliveryTimestamp;
    }

    public String getOrderReceiptAcknowledgement() {
        return orderReceiptAcknowledgement;
    }

    public void setOrderReceiptAcknowledgement(String orderReceiptAcknowledgement) {
        this.orderReceiptAcknowledgement = orderReceiptAcknowledgement;
    }

    public String getOrderInvoiceAmount() {
        return orderInvoiceAmount;
    }

    public void setOrderInvoiceAmount(String orderInvoiceAmount) {
        this.orderInvoiceAmount = orderInvoiceAmount;
    }

    public String getOrderReceiptAcknowledgementCollected() {
        return orderReceiptAcknowledgementCollected;
    }

    public void setOrderReceiptAcknowledgementCollected(String orderReceiptAcknowledgementCollected) {
        this.orderReceiptAcknowledgementCollected = orderReceiptAcknowledgementCollected;
    }

    public String getOrderInvoiceAmountCollected() {
        return orderInvoiceAmountCollected;
    }

    public void setOrderInvoiceAmountCollected(String orderInvoiceAmountCollected) {
        this.orderInvoiceAmountCollected = orderInvoiceAmountCollected;
    }

    public String getOrderDisputeResolutionComment() {
        return orderDisputeResolutionComment;
    }

    public void setOrderDisputeResolutionComment(String orderDisputeResolutionComment) {
        this.orderDisputeResolutionComment = orderDisputeResolutionComment;
    }

    public String getOrderCancellationReason() {
        return orderCancellationReason;
    }

    public void setOrderCancellationReason(String orderCancellationReason) {
        this.orderCancellationReason = orderCancellationReason;
    }

    public Object getOrderDisputeResolvedBy() {
        return orderDisputeResolvedBy;
    }

    public void setOrderDisputeResolvedBy(Object orderDisputeResolvedBy) {
        this.orderDisputeResolvedBy = orderDisputeResolvedBy;
    }

    public String getOrderMarkedDisputedBy() {
        return orderMarkedDisputedBy;
    }

    public void setOrderMarkedDisputedBy(String orderMarkedDisputedBy) {
        this.orderMarkedDisputedBy = orderMarkedDisputedBy;
    }

    public String getOrderMarkedAsDisputed() {
        return orderMarkedAsDisputed;
    }

    public void setOrderMarkedAsDisputed(String orderMarkedAsDisputed) {
        this.orderMarkedAsDisputed = orderMarkedAsDisputed;
    }
}