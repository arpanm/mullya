import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IOrder } from 'app/shared/model/order.model';
import { PaymentType } from 'app/shared/model/enumerations/payment-type.model';
import { PGType } from 'app/shared/model/enumerations/pg-type.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export interface IRemittanceDetails {
  id?: number;
  paymentType?: PaymentType | null;
  onlinePgType?: PGType | null;
  pgTxnId?: string | null;
  pgStatus?: string | null;
  offlineTxnId?: string | null;
  offlineTxnDetails?: string | null;
  offlineTxnGivenBy?: string | null;
  offlineTxnClearingStatus?: string | null;
  remittanceDate?: string | null;
  remittanceInitTime?: string | null;
  remittanceUpdateTime?: string | null;
  paymentStatus?: PaymentStatus | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  farmer?: IUser | null;
  orders?: IOrder[] | null;
}

export const defaultValue: Readonly<IRemittanceDetails> = {};
