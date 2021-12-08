import dayjs from 'dayjs';
import { IBids } from 'app/shared/model/bids.model';
import { IStock } from 'app/shared/model/stock.model';
import { BiddingStatus } from 'app/shared/model/enumerations/bidding-status.model';

export interface IBiddingDetails {
  id?: number;
  startDate?: string | null;
  endDate?: string | null;
  biddingStatus?: BiddingStatus | null;
  minPrice?: number | null;
  maxPrice?: number | null;
  minQuantityKg?: number | null;
  maxQuantityKg?: number | null;
  isActive?: boolean | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  bids?: IBids[] | null;
  stock?: IStock | null;
}

export const defaultValue: Readonly<IBiddingDetails> = {
  isActive: false,
};
