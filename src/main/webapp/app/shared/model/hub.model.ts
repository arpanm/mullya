import dayjs from 'dayjs';

export interface IHub {
  id?: number;
  tag?: string | null;
  isActive?: boolean | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
}

export const defaultValue: Readonly<IHub> = {
  isActive: false,
};
