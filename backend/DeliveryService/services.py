
from models import LocationRequest
from utils import calculate_distance_here


def get_distance(data: LocationRequest):
    origins = [data.origin_lat, data.origin_lng]
    destinations = [data.destination_lat, data.destination_lng]
    
    distance, travel_time = calculate_distance_here(origins, destinations)
    if distance is not None and travel_time is not None:
        return {
            "distance_km": distance / 1000,  # Convert to kilometers
            "travel_time_minutes": travel_time / 60  # Convert to minutes
        }
    return None
