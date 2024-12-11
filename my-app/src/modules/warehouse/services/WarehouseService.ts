import { Warehouse } from "../models/Warehouse";


export async function getAllWarehouses(pageNo: number = 0, pageSize: number = 10): Promise<Warehouse[]> {
  const url = `http://localhost:8083/warehouses/api/v1/warehouses/all?pageNo=${pageNo}&pageSize=${pageSize}`;
  try {
    console.log("Fetching warehouses from:", url);
    const res = await fetch(url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    });

    console.log("Response status:", res.status);
    if (!res.ok) {
      throw new Error(`Failed to fetch warehouses: ${res.status} ${res.statusText}`);
    }

    const data = await res.json();
    console.log("Fetched warehouses:", data);
    return data.result;
  } catch (error) {
    console.error("Error fetching warehouses:", error);
    return Promise.reject(error);
  }
}