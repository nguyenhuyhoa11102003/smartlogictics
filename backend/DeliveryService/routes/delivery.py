from datetime import datetime, date
from http.client import HTTPException

import httpx
import requests
from fastapi import APIRouter, Depends

from core.config import HERE_API_KEY, OPENCAGEDATA_URL, OPENCAGEDATA_API_KEY
from dto.request.predict import PredictionRequest
import pandas as pd

from dto.response.ApiResponse import ApiResponse
from dto.response.coordinatesResponse import CoordinatesResponse

# from services.services import predict

router = APIRouter()


@router.post("/estimate")
async def estimate(request: PredictionRequest):
    try:
        new_data = pd.DataFrame([request.dict()])
        test = pd.DataFrame(new_data)
        # prediction = predict(test)
        return {"prediction": None}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get("/routes" )
async def get_routes(
        origin: str,
        destination: str,
        return_summary: str = "summary",
        transport_mode: str = "car",
):
    try:
        # Parse the origin and destination coordinates from the query string
        origin_lat, origin_lng = map(float, origin.split(","))
        destination_lat, destination_lng = map(float, destination.split(","))

        # URL for the GET request to HERE API
        url = "https://router.hereapi.com/v8/routes"
        params = {
            "transportMode": transport_mode,
            "origin": f"{origin_lat},{origin_lng}",
            "destination": f"{destination_lat},{destination_lng}",
            "return": return_summary,
            "apiKey": HERE_API_KEY
        }

        # Make the GET request using httpx
        async with httpx.AsyncClient() as client:
            response = await client.get(url, params=params)

        # Check if the response was successful
        if response.status_code == 200:
            data = response.json()
            return ApiResponse(
                isSuccess=True,
                message="Route data fetched successfully",
                data=data,
                result=data["routes"]
            )            
        else:
            return ApiResponse(
                isSuccess=False,
                message=f"Failed to fetch route data: {response.text}",
                data=None
            )

    except Exception as e:
        raise HTTPException(
            status_code=500,
            detail=ApiResponse(
                success=False,
                message="An unexpected error occurred",
                data=str(e)
            ).dict()  # Convert ApiResponse to dictionary for HTTPException
        )


@router.get("/geocode")
async def geocode_address(address: str):
    try:
        url = "https://geocode.search.hereapi.com/v1/geocode"
        params = {
            "q": address,
            "apiKey": HERE_API_KEY
        }

        async with httpx.AsyncClient() as client:
            response = await client.get(url, params=params)

        if response.status_code == 200:
            data = response.json()

            if "items" in data and data["items"]:
                location = data["items"][0]["position"]
                return {
                    "latitude,longitude": str(location["lat"]) + "," + str(location["lng"]),
                    "location": data
                }
            else:
                raise HTTPException(status_code=404, detail="Address not found")

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get("/get_coordinates", response_model=ApiResponse[CoordinatesResponse])
async def get_coordinates(address: str):
    url = f"{OPENCAGEDATA_URL}?q={address}&key={OPENCAGEDATA_API_KEY}"
    response = requests.get(url)

    if response.status_code == 200:
        data = response.json()

        if data['results']:
            latitude = data['results'][0]['geometry']['lat']
            longitude = data['results'][0]['geometry']['lng']
            return ApiResponse(
                code=200,
                isSuccess=True,
                result=CoordinatesResponse(
                    latitude=str(latitude),
                    longitude=str(longitude)),
                message="Success",
                timestamp=date.today(),
            )
        else:
            return ApiResponse(
                code=404,
                isSuccess=False,
                result=None,
                message="Không tìm thấy tọa độ cho địa chỉ này",
                timestamp=date.today(),
            )

    else:
        return ApiResponse(
            code=500,
            isSuccess=False,
            result=None,
            message="Lỗi khi truy cập API Geocoding",
            timestamp=date.today(),
        )
