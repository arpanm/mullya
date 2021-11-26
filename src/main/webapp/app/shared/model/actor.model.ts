import dayjs from 'dayjs';
import { IRequirement } from 'app/shared/model/requirement.model';
import { IOTP } from 'app/shared/model/otp.model';
import { ActorType } from 'app/shared/model/enumerations/actor-type.model';

export interface IActor {
  id?: number;
  email?: string | null;
  phone?: number | null;
  isEmailVerified?: boolean | null;
  isPhoneVerified?: boolean | null;
  isActive?: boolean | null;
  password?: string | null;
  type?: ActorType | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  requirements?: IRequirement[] | null;
  oTPS?: IOTP[] | null;
}

export const defaultValue: Readonly<IActor> = {
  isEmailVerified: false,
  isPhoneVerified: false,
  isActive: false,
};
