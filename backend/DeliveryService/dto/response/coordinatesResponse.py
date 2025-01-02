from pydantic import BaseModel


class CoordinatesResponse(BaseModel):
    latitude: str
    longitude: str
