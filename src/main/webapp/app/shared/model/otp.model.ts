import dayjs from 'dayjs';
import { IOTPAttempt } from 'app/shared/model/otp-attempt.model';
import { IUser } from 'app/shared/model/user.model';
import { OtpType } from 'app/shared/model/enumerations/otp-type.model';
import { OtpStatus } from 'app/shared/model/enumerations/otp-status.model';

export interface IOTP {
  id?: number;
  otpVal?: string | null;
  email?: string | null;
  phone?: number | null;
  type?: OtpType | null;
  expiryTime?: string | null;
  status?: OtpStatus | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  oTPAttempts?: IOTPAttempt[] | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IOTP> = {};
