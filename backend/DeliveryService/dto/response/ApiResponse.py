from datetime import date
from typing import Optional, TypeVar, Generic

from pydantic import BaseModel

from dto.response.coordinatesResponse import CoordinatesResponse

T = TypeVar('T')


class ApiResponse(BaseModel, Generic[T]):
    code: int = 200
    isSuccess: bool = True
    result: Optional[T]
    message: str
    timestamp: date=date.today()
