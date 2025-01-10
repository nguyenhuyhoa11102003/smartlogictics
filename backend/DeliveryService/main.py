from fastapi import FastAPI
from routes.delivery import router

app = FastAPI(title="FastAPI Demo Project")
context_path = "/delivery"
app.include_router(router=router, prefix=f"{context_path}/deliver", tags=["Deliveries"])

if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
