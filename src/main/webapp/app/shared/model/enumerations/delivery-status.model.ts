export enum DeliveryStatus {
  PendingConfirmation = 'PendingConfirmation',

  Accepted = 'Accepted',

  Declined = 'Declined',

  InTransit = 'InTransit',

  Delivered = 'Delivered',

  ReturnInitiatedInTransit = 'ReturnInitiatedInTransit',

  ReturnInitiatedAfterDelivery = 'ReturnInitiatedAfterDelivery',

  Returned = 'Returned',
}
