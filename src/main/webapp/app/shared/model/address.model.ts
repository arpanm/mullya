import dayjs from 'dayjs';
import { IActor } from 'app/shared/model/actor.model';
import { IRequirement } from 'app/shared/model/requirement.model';

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
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  actor?: IActor | null;
  requirements?: IRequirement[] | null;
  orderRequirements?: IRequirement[] | null;
}

export const defaultValue: Readonly<IAddress> = {};
