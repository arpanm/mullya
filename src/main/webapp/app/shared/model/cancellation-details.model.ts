import dayjs from 'dayjs';
import { IOrder } from 'app/shared/model/order.model';
import { IActor } from 'app/shared/model/actor.model';
import { IDeliveryDetails } from 'app/shared/model/delivery-details.model';
import { CancelationType } from 'app/shared/model/enumerations/cancelation-type.model';
import { CancellationStatus } from 'app/shared/model/enumerations/cancellation-status.model';

export interface ICancellationDetails {
  id?: number;
  cancelationType?: CancelationType | null;
  cancellationReason?: string | null;
  cancellationDate?: string | null;
  cancellationTime?: string | null;
  refundId?: string | null;
  cancellationStatus?: CancellationStatus | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  order?: IOrder | null;
  approver?: IActor | null;
  initiator?: IActor | null;
  deliveryDetails?: IDeliveryDetails[] | null;
}

export const defaultValue: Readonly<ICancellationDetails> = {};
