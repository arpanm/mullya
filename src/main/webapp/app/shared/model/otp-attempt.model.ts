import dayjs from 'dayjs';
import { IOTP } from 'app/shared/model/otp.model';

export interface IOTPAttempt {
  id?: number;
  otp?: number | null;
  email?: string | null;
  phone?: number | null;
  ip?: string | null;
  coookie?: string | null;
  createdBy?: string | null;
  createdAt?: string | null;
  otp?: IOTP | null;
}

export const defaultValue: Readonly<IOTPAttempt> = {};
