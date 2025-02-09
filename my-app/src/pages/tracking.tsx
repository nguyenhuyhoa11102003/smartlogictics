"use client";

import { useEffect, useState, useRef, useCallback } from "react";
import dynamic from "next/dynamic";

// Load các thành phần Leaflet chỉ khi client render
const MapContainer = dynamic(() => import("react-leaflet").then((mod) => mod.MapContainer), { ssr: false });
const TileLayer = dynamic(() => import("react-leaflet").then((mod) => mod.TileLayer), { ssr: false });
const Marker = dynamic(() => import("react-leaflet").then((mod) => mod.Marker), { ssr: false });
const Popup = dynamic(() => import("react-leaflet").then((mod) => mod.Popup), { ssr: false });

import "leaflet/dist/leaflet.css";

const TrackingMap = () => {
    const [vehicleData, setVehicleData] = useState<{ latitude: number; longitude: number; vehicleId: string } | null>(
        null
    );

    const connectWebSocket = useCallback(() => {
        const ws = new WebSocket("ws://localhost:8000/ws");

        ws.onopen = () => {
            console.log("✅ WebSocket connected");
            ws.send("Hello Server!");
        };

        ws.onmessage = (event) => {
            try {
                console.log("📩 Nhận dữ liệu từ server:", event.data);
                const data = JSON.parse(event.data);
                setVehicleData(data);
            } catch (error) {
                console.error("❌ Lỗi khi parse dữ liệu:", error);
            }
        };

        ws.onclose = () => {
            console.log("🔌 WebSocket disconnected");
        };

        ws.onerror = (error) => {
            console.error("❌ WebSocket error:", error);
        };

        return ws;
    }, []);

    useEffect(() => {
        const ws = connectWebSocket();
        return () => ws.close();
    }, [connectWebSocket]);

    return (
        <MapContainer center={[10.762622, 106.660172]} zoom={13} style={{ height: "100vh", width: "100%" }}>
            {/* Tile Layer sử dụng OpenStreetMap */}
            <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
            {vehicleData ? (
                <Marker position={[vehicleData.latitude, vehicleData.longitude]}>
                    <Popup>🚗 Xe {vehicleData.vehicleId} đang ở đây!</Popup>
                </Marker>
            ) : (
                <p style={{ position: "absolute", top: "10px", left: "10px", background: "white", padding: "5px", borderRadius: "5px" }}>
                    ⏳ Đang chờ dữ liệu...
                </p>
            )}

        </MapContainer>
    );
};

export default TrackingMap;
