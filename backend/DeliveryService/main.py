import asyncio
import random
from fastapi import FastAPI, WebSocket, WebSocketDisconnect
from typing import List
from routes.delivery import router

app = FastAPI(title="FastAPI Demo Project")
context_path = "/delivery"

app.include_router(
    router=router, prefix=f"{context_path}/deliver", tags=["Deliveries"])
connected_clients: List[WebSocket] = []

route = [
    {"latitude": 10.7769, "longitude": 106.7009},
    {"latitude": 10.7775, "longitude": 106.7020},
    {"latitude": 10.7780, "longitude": 106.7035},
    {"latitude": 10.7788, "longitude": 106.7050},
    {"latitude": 10.7795, "longitude": 106.7065},
]

@app.websocket("/ws")
async def websocket_endpoint(websocket: WebSocket):
    await websocket.accept()
    connected_clients.append(websocket)
    try:
        for point in route:  # Lặp qua từng điểm trên tuyến đường
            data = {"latitude": point["latitude"],
                    "longitude": point["longitude"], "vehicleId": "ABC123"}

            # Gửi tọa độ đến tất cả client
            for client in connected_clients:
                await client.send_json(data)

            await asyncio.sleep(2)  # Chờ 2 giây trước khi gửi điểm tiếp theo

    except WebSocketDisconnect:
        connected_clients.remove(websocket)
        print("🔌 Client disconnected")

@app.get("/")
async def root():
    return {"message": "🚀 WebSocket Server is running"}

if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
