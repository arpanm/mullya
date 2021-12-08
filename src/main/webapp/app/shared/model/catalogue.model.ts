import dayjs from 'dayjs';
import { IStock } from 'app/shared/model/stock.model';
import { IRequirement } from 'app/shared/model/requirement.model';

export interface ICatalogue {
  id?: number;
  name?: string | null;
  stockImageUrl?: string | null;
  landingUrl?: string | null;
  description?: string | null;
  isActive?: boolean | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
  catalogues?: ICatalogue[] | null;
  categoryStocks?: IStock[] | null;
  variantStocks?: IStock[] | null;
  subVariantStocks?: IStock[] | null;
  categoryRequirements?: IRequirement[] | null;
  variantRequirements?: IRequirement[] | null;
  subVariantRequirements?: IRequirement[] | null;
  parent?: ICatalogue | null;
}

export const defaultValue: Readonly<ICatalogue> = {
  isActive: false,
};
