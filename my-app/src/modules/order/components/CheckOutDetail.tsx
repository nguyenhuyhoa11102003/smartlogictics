import { formatPrice } from '@/utils/formatPrice';
import { OrderItem } from '../models/OrderItem';
import { useEffect, useState } from 'react';

type Props = {
  orderItems: OrderItem[];
  disablePaymentProcess: boolean;
};

const CheckOutDetail = ({ orderItems, disablePaymentProcess }: Props) => {
  const [totalPrice, setTotalPrice] = useState(0);
  const [disableCheckout, setDisableCheckout] = useState<boolean>(true);

  useEffect(() => {
    const totalPrice = orderItems
      .map((item) => calculateProductPrice(item))
      .reduce((accumulator, currentValue) => accumulator + currentValue, 0);
    setTotalPrice(totalPrice);
  }, [orderItems]);

  const calculateProductPrice = (item: OrderItem) => {
    return item.productPrice * item.quantity;
  };

  const handleAgreeTerms = (e: any) => {
    if (e.target.checked) {
      setDisableCheckout(false);
    } else {
      setDisableCheckout(true);
    }
  };

  return (
    <>
      <div className="checkout__order">
        <h4>Your Order</h4>
        <div className="checkout__order__products row">
          <div className="col-lg-6">Products</div>
          <div className="col-lg-4">Quantity</div>
          <div className="col-lg-2">Price</div>
        </div>

        {orderItems?.map((item) => (
          <div key={item.productId} className="row">
            <div className="col-lg-6">{item.productName} </div>
            <div className="col-lg-3 d-flex justify-content-center">{item.quantity}</div>
            <div className="col-lg-3"> {formatPrice(item.productPrice)}</div>
          </div>
        ))}

        <div className="checkout__order__subtotal">
          Delivery Fee <span>$750.99</span>
        </div>
        <div className="checkout__order__subtotal">
          Tax <span>$750.99</span>
        </div>
        <div className="checkout__order__total">
          Total <span>{formatPrice(totalPrice)}</span>
        </div>
        <div className="checkout__input__checkbox">
          <label htmlFor="acc-or">
            Agree to Terms and Conditions
            <input type="checkbox" id="acc-or" onChange={handleAgreeTerms} />
            <span className="checkmark"></span>
          </label>
        </div>
        <p className="mb-2">
          “I agree to the terms and conditions as set out by the user agreement.{' '}
          <a className="text-primary" href="./conditions">
            Learn more
          </a>
          ”
        </p>

        <button
          type="submit"
          className="site-btn"
          disabled={disablePaymentProcess ? true : disableCheckout}
          style={
            disablePaymentProcess || disableCheckout
              ? { cursor: 'not-allowed', backgroundColor: 'gray' }
              : { cursor: 'pointer' }
          }
        >
          Process to Payment
        </button>
      </div>
    </>
  );
};

export default CheckOutDetail;
