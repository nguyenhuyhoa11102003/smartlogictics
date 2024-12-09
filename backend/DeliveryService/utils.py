import requests
from config import HERE_API_KEY

def calculate_distance_here(origins, destinations, transportMode="car"):
    url = "https://matrix.router.hereapi.com/v8/routes"
    headers = {"Content-Type": "application/json"}
    data = {
        "origins": [{"lat": origins[0], "lng": origins[1]}],
        "destinations": [{"lat": destinations[0], "lng": destinations[1]}],
        "regionDefinition": {"type": "world"},
        "transportMode": transportMode,
        "return" : "summary"
    }
    params = {"apikey": HERE_API_KEY}
    response = requests.post(url, json=data, headers=headers, params=params)
    if response.status_code == 200:
        distances = response.json().get("routes")
        if distances:
            return distances[0][0]["length"], distances[0][0]["travelTime"]
    return None, None
