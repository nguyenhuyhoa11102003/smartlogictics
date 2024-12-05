import { Rating } from '../models/Rating';
import { RatingPost } from '../models/RatingPost';
import { concatQueryString } from '../../../utils/concatQueryString';

export async function getRatingsByProductId(
  productId: number,
  pageNo?: number,
  pageSize?: number
): Promise<{ ratingList: Rating[]; totalPages: number; totalElements: number }> {
  const url = `/api/rating/storefront/ratings/products/${productId}`;
  const queryString = [];
  if (pageNo) {
    queryString.push(`pageNo=${pageNo}`);
  }
  if (pageSize) {
    queryString.push(`pageSize=${pageSize}`);
  }
  const final_url = concatQueryString(queryString, url);

  const response = await fetch(final_url);
  return await response.json();
}

export async function createRating(rating: RatingPost): Promise<Rating | null> {
  const response = await fetch('/api/rating/storefront/ratings', {
    method: 'POST',
    body: JSON.stringify(rating),
    headers: { 'Content-type': 'application/json; charset=UTF-8' },
  });
  if (response.status >= 200 && response.status < 300) {
    return await response.json();
  }
  return Promise.reject(response.status);
}

export async function getAverageStarByProductId(productId: number): Promise<number> {
  const url = `/api/rating/storefront/ratings/product/${productId}/average-star`;

  const response = await fetch(url);

  if (response.status >= 200 && response.status < 300) {
    return await response.json();
  }

  return Promise.reject(response.status);
}
