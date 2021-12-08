import dayjs from 'dayjs';
import { IPaymentDetails } from 'app/shared/model/payment-details.model';
import { IRemittanceDetails } from 'app/shared/model/remittance-details.model';
import { IRequirement } from 'app/shared/model/requirement.model';
import { IBids } from 'app/shared/model/bids.model';
import { IActor } from 'app/shared/model/actor.model';
import { IStock } from 'app/shared/model/stock.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';

export interface IOrder {
  id?: number;
  acceptedPrice?: number | null;
  codAmount?: number | null;
  quantityKg?: number | null;
  neededBy?: string | null;
  acceptedDeliveryDate?: string | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  status?: OrderStatus | null;
  paymentDetails?: IPaymentDetails[] | null;
  remittances?: IRemittanceDetails[] | null;
  requirement?: IRequirement | null;
  bid?: IBids | null;
  assignedAgent?: IActor | null;
  stock?: IStock | null;
}

export const defaultValue: Readonly<IOrder> = {};
