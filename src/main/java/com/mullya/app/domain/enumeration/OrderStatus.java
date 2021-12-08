package com.mullya.app.domain.enumeration;

/**
 * The OrderStatus enumeration.
 */
public enum OrderStatus {
    New,
    Paid,
    InTransit,
    Delivered,
    ReturnInitiatedInTransit,
    ReturnInitiatedAfterDelivery,
    Returned,
    Cancelled,
    Refunded,
}
