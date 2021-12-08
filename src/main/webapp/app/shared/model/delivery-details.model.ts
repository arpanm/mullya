import dayjs from 'dayjs';
import { IAddress } from 'app/shared/model/address.model';
import { IOrder } from 'app/shared/model/order.model';
import { ICancellationDetails } from 'app/shared/model/cancellation-details.model';
import { DeliveryType } from 'app/shared/model/enumerations/delivery-type.model';
import { DeliveryStatus } from 'app/shared/model/enumerations/delivery-status.model';

export interface IDeliveryDetails {
  id?: number;
  deliveryType?: DeliveryType | null;
  pickupDate?: string | null;
  deliveryDate?: string | null;
  truckDetails?: string | null;
  deliveryAgentPhone?: number | null;
  pickupTime?: string | null;
  deliveryTime?: string | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  deliveryStatus?: DeliveryStatus | null;
  fromAddress?: IAddress | null;
  toAddress?: IAddress | null;
  order?: IOrder | null;
  cancellation?: ICancellationDetails | null;
}

export const defaultValue: Readonly<IDeliveryDetails> = {};
