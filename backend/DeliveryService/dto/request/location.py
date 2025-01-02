from pydantic import BaseModel

class LocationRequest(BaseModel):
    origin_lat: float
    origin_lng: float
    destination_lat: float
    destination_lng: float
    transportMode : str = "car"