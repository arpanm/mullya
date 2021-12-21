import dayjs from 'dayjs';
import { IOrder } from 'app/shared/model/order.model';
import { IAddress } from 'app/shared/model/address.model';
import { IUser } from 'app/shared/model/user.model';
import { ICatalogue } from 'app/shared/model/catalogue.model';
import { RequirementStatus } from 'app/shared/model/enumerations/requirement-status.model';

export interface IRequirement {
  id?: number;
  minPrice?: number | null;
  maxPrice?: number | null;
  quantityKg?: number | null;
  neededBy?: string | null;
  description?: string | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  status?: RequirementStatus | null;
  orders?: IOrder[] | null;
  buyerAddress?: IAddress | null;
  buyerUser?: IUser | null;
  category?: ICatalogue | null;
  variant?: ICatalogue | null;
  subVariant?: ICatalogue | null;
}

export const defaultValue: Readonly<IRequirement> = {};
