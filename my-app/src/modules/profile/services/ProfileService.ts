import { ProfileRequest } from '../models/ProfileRequest';

export async function getMyProfile() {
  const response = await fetch(`/api/customer/storefront/customer/profile`);
  if (response.status >= 200 && response.status < 300) {
    return await response.json();
  }
  return Promise.reject(response.status);
}

export async function updateCustomer(profile: ProfileRequest) {
  const response = await fetch(`/api/customer/`, {
    method: 'PUT',
    body: JSON.stringify(profile),
    headers: { 'Content-type': 'application/json; charset=UTF-8' },
  });

  if (response.status >= 200 && response.status < 300) {
    return await response.json();
  }

  return Promise.reject(response.status);
}
