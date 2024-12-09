import httpx
import joblib
from fastapi import FastAPI, HTTPException, logger
from pydantic import BaseModel
import numpy as np
import requests

from config import HERE_API_KEY
from models import LocationRequest, PredictionRequest, RouteResponse
app = FastAPI()




model = joblib.load('random_forest_model.pkl')


@app.post("/delivery/predict/estimate")
async def predict(request: PredictionRequest):
    try:
        data = np.array([[request.serviceType, request.weight, request.shippingDistance]])
        prediction = model.predict(data)
        return {"prediction": prediction[0]}

    except Exception as e:
        return {"error": str(e)}
    
@app.get("/delivery/routes")
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
            # Extract the summary data from the response
            summary = data['routes'][0]['sections'][0]['summary']
            return {
                "duration": summary['duration'],
                "length": summary['length'],
                "baseDuration": summary['baseDuration']
            }

        # Handle errors in the response
        else:
            raise HTTPException(status_code=response.status_code, detail="Failed to fetch route data")

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@app.get("/delivery/geocode")
async def geocode_address(address : str):
    try:
        url = "https://geocode.search.hereapi.com/v1/geocode"
        params = {
            "q": address,
            "apiKey": HERE_API_KEY
        }
        
        async with httpx.AsyncClient() as client:
            response = await client.get( url , params=params)
            
        if response.status_code == 200:
            data = response.json()
            
            if "items" in data and data["items"]:
                location = data["items"][0]["position"]
                return {
                    "latitude,longitude": str(location["lat"]) +"," + str(location["lng"]),
                    "location"  :data 
                }
            else:
                raise HTTPException(status_code=404, detail="Address not found")
            
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    

if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)


