import dayjs from 'dayjs';
import { IOrder } from 'app/shared/model/order.model';
import { IAddress } from 'app/shared/model/address.model';
import { IBiddingDetails } from 'app/shared/model/bidding-details.model';
import { IActor } from 'app/shared/model/actor.model';
import { BidStatus } from 'app/shared/model/enumerations/bid-status.model';

export interface IBids {
  id?: number;
  bidPrice?: number | null;
  quantityKg?: number | null;
  bidStatus?: BidStatus | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  orders?: IOrder[] | null;
  buyerAddress?: IAddress | null;
  biddingDetails?: IBiddingDetails | null;
  buyer?: IActor | null;
}

export const defaultValue: Readonly<IBids> = {};
