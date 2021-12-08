import dayjs from 'dayjs';
import { IOrder } from 'app/shared/model/order.model';
import { PaymentType } from 'app/shared/model/enumerations/payment-type.model';
import { PGType } from 'app/shared/model/enumerations/pg-type.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export interface IPaymentDetails {
  id?: number;
  amount?: number | null;
  paymentType?: PaymentType | null;
  onlinePgType?: PGType | null;
  pgTxnId?: string | null;
  pgStatus?: string | null;
  offlineTxnId?: string | null;
  offlineTxnDetails?: string | null;
  offlineTxnCollectedBy?: string | null;
  offlineTxnClearingStatus?: string | null;
  paymentDate?: string | null;
  paymentInitTime?: string | null;
  paymentUpdateTime?: string | null;
  paymentStatus?: PaymentStatus | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  order?: IOrder | null;
}

export const defaultValue: Readonly<IPaymentDetails> = {};
