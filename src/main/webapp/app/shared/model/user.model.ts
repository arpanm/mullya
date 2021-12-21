import { IRequirement } from 'app/shared/model/requirement.model';
import { IOTP } from 'app/shared/model/otp.model';
import { IAddress } from 'app/shared/model/address.model';
import { IStock } from 'app/shared/model/stock.model';
import { IBids } from 'app/shared/model/bids.model';
import { IOrder } from 'app/shared/model/order.model';
import { IRemittanceDetails } from 'app/shared/model/remittance-details.model';
import { ActorType } from 'app/shared/model/enumerations/actor-type.model';

export interface IUser {
  id?: any;
  login?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: any[];
  createdBy?: string;
  createdDate?: Date | null;
  lastModifiedBy?: string;
  lastModifiedDate?: Date | null;
  password?: string;
  phone?: number | null;
  isEmailVerified?: boolean | null;
  isPhoneVerified?: boolean | null;
  isActive?: boolean | null;
  type?: ActorType | null;
  requirements?: IRequirement[] | null;
  oTPS?: IOTP[] | null;
  addresses?: IAddress[] | null;
  stocks?: IStock[] | null;
  bids?: IBids[] | null;
  orders?: IOrder[] | null;
  remittanceDetails?: IRemittanceDetails[] | null;
}

export const defaultValue: Readonly<IUser> = {
  id: '',
  login: '',
  firstName: '',
  lastName: '',
  email: '',
  activated: true,
  langKey: '',
  authorities: [],
  createdBy: '',
  createdDate: null,
  lastModifiedBy: '',
  lastModifiedDate: null,
  password: '',
  isEmailVerified: false,
  isPhoneVerified: false,
  isActive: false,
};
