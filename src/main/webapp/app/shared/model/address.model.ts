import dayjs from 'dayjs';
import { IHub } from 'app/shared/model/hub.model';
import { IActor } from 'app/shared/model/actor.model';

export interface IAddress {
  id?: number;
  streetAddress?: string | null;
  postalCode?: number | null;
  city?: string | null;
  stateProvince?: string | null;
  country?: string | null;
  createdBy?: string | null;
  lat?: number | null;
  lon?: number | null;
  mapLocation?: string | null;
  createdOn?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  hub?: IHub | null;
  actor?: IActor | null;
}

export const defaultValue: Readonly<IAddress> = {};
