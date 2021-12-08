import dayjs from 'dayjs';

export interface IBanner {
  id?: number;
  name?: string | null;
  imageUrl?: string | null;
  mobileImageUrl?: string | null;
  landingUrl?: string | null;
  description?: string | null;
  html?: string | null;
  mobileHtml?: string | null;
  isActive?: boolean | null;
  startDate?: string | null;
  endDate?: string | null;
  createdOn?: string | null;
  createdBy?: string | null;
  updatedOn?: string | null;
  updatedBy?: string | null;
}

export const defaultValue: Readonly<IBanner> = {
  isActive: false,
};
