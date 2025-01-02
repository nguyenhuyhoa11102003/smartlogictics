from pydantic import BaseModel

class PredictionRequest(BaseModel):
    ID: str
    Delivery_person_ID: str
    Delivery_person_Age: int
    Delivery_person_Ratings: float
    Restaurant_latitude: float
    Restaurant_longitude: float
    Delivery_location_latitude: float
    Delivery_location_longitude: float
    Order_Date: str
    Time_Orderd: str
    Time_Order_picked: str
    Weatherconditions: str
    Road_traffic_density: str
    Vehicle_condition: int
    Type_of_order: str
    Type_of_vehicle: str
    multiple_deliveries: int
    Festival: str
    Delivery_person_Ratings_missing: int
    Delivery_person_Age_missing: int
    Time_Orderd_missing: int
    Weatherconditions_missing: int
    City: str