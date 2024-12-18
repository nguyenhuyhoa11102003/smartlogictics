import httpx
import pandas as pd
from fastapi import FastAPI, HTTPException

from config import HERE_API_KEY
from models import PredictionRequest
from services import predict

app = FastAPI()


@app.post("/delivery/estimate")
async def estimate(request: PredictionRequest):
    try:
        new_data = pd.DataFrame([request.dict()])
        test = pd.DataFrame(new_data)
        prediction = predict(test)
        return {"prediction": prediction}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


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


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)


# {
#     "ID": "0x4607",
#     "Delivery_person_ID": "INDORES13DEL02",
#     "Delivery_person_Age": 37,
#     "Delivery_person_Ratings": 4.9,
#     "Restaurant_latitude": 22.745049,
#     "Restaurant_longitude": 75.892471,
#     "Delivery_location_latitude": 22.765049,
#     "Delivery_location_longitude": 75.912471,
#     "Order_Date": "19-03-2022",
#     "Time_Orderd": "11:30:00",
#     "Time_Order_picked": "11:45:00",
#     "Weatherconditions": "conditions Sunny",
#     "Road_traffic_density": "High",
#     "Vehicle_condition": 2,
#     "Type_of_order": "Snack",
#     "Type_of_vehicle": "motorcycle",
#     "multiple_deliveries": 0,
#     "Festival": "No",
#     "Delivery_person_Ratings_missing": 0,
#     "Delivery_person_Age_missing": 0,
#     "Time_Orderd_missing": 0,
#     "Weatherconditions_missing": 0,
#     "City": "Urban"
# }