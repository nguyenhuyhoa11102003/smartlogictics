import { InformationOrder } from "./InformationOrder";

export type Order = {
  orderCreationStatus: string;  // Status of the order (e.g., RECEIVED, SHIPPED)
  type: string;                 // Type of the order (e.g., string like "string")
  customerCode: string;        // Customer's unique code
  contractCode: string;        // Associated contract code
  informationOrder: InformationOrder; // InformationOrder associated with the order
};
