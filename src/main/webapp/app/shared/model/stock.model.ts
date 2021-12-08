import dayjs from 'dayjs';
import { IBiddingDetails } from 'app/shared/model/bidding-details.model';
import { IOrder } from 'app/shared/model/order.model';
import { IAddress } from 'app/shared/model/address.model';
import { IActor } from 'app/shared/model/actor.model';
import { ICatalogue } from 'app/shared/model/catalogue.model';
import { StockStatus } from 'app/shared/model/enumerations/stock-status.model';

export interface IStock {
  id?: number;
  minPrice?: number | null;
  maxPrice?: number | null;
  quantityKg?: number | null;
  expiry?: string | null;
  avialableFrom?: string | null;
  description?: string | null;
  stockStatus?: StockStatus | null;
  isOpenForBidding?: boolean | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  biddingDetails?: IBiddingDetails[] | null;
  orders?: IOrder[] | null;
  farmerAddress?: IAddress | null;
  farmer?: IActor | null;
  category?: ICatalogue | null;
  variant?: ICatalogue | null;
  subVariant?: ICatalogue | null;
}

export const defaultValue: Readonly<IStock> = {
  isOpenForBidding: false,
};
