import { InformationOrder } from "./InformationOrder";

export type Order = {
  orderCreationStatus: string;  
  type: string;                 
  customerCode: string;        
  contractCode: string;        
  informationOrder: InformationOrder;
};
