from pydantic import BaseModel


class RouteResponse(BaseModel):
    duration: int
    length: int
    baseDuration: int