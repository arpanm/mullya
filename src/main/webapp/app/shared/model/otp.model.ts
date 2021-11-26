import dayjs from 'dayjs';
import { IOTPAttempt } from 'app/shared/model/otp-attempt.model';
import { IActor } from 'app/shared/model/actor.model';
import { OtpType } from 'app/shared/model/enumerations/otp-type.model';
import { OtpStatus } from 'app/shared/model/enumerations/otp-status.model';

export interface IOTP {
  id?: number;
  otpVal?: number | null;
  email?: string | null;
  phone?: number | null;
  type?: OtpType | null;
  expiryTime?: string | null;
  status?: OtpStatus | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  oTPAttempts?: IOTPAttempt[] | null;
  actor?: IActor | null;
}

export const defaultValue: Readonly<IOTP> = {};
