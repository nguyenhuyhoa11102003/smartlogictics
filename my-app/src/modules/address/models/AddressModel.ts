export type Address = {
  id?: string;
  contactName: string;
  phone: string;
  addressDetail: string;
  city?: string;
  zipCode?: string;
  districtId: number;
  districtName?: string;
  wardId: number;
  wardName?: string;
  stateOrProvinceId: number;
  stateOrProvinceName?: string;
  countryId: number;
  countryName?: string;
  isActive?: boolean;
};
