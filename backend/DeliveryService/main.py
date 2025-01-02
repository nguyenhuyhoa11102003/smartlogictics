from fastapi import FastAPI

from routes import delivery

app = FastAPI(title="FastAPI Demo Project")

context_path = "/delivery"

app.include_router(router=delivery.router, prefix=f"{context_path}/deliver", tags=["Deliveries"])


@app.get("/")
async def root():
    return {"message": "Welcome to FastAPI Project"}


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
