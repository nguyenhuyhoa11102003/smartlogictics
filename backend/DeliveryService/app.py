import joblib
from fastapi import FastAPI
from pydantic import BaseModel
import numpy as np

app = FastAPI()


class PredictionRequest(BaseModel):
    serviceType: int
    weight: float
    shippingDistance: float


model = joblib.load('random_forest_model.pkl')


@app.post("/predict/")
async def predict(request: PredictionRequest):
    try:
        data = np.array([[request.serviceType, request.weight, request.shippingDistance]])
        prediction = model.predict(data)
        return {"prediction": prediction[0]}

    except Exception as e:
        return {"error": str(e)}


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
