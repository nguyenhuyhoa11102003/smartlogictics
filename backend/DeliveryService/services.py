import warnings;

warnings.filterwarnings("ignore")
import numpy as np
import pandas as pd
from geopy.distance import geodesic

pd.set_option('display.max_columns', None)

selected_feat_ = [
    'Delivery_person_Ratings',
    'Weatherconditions',
    'Road_traffic_density',
    'Vehicle_condition',
    'multiple_deliveries',
    'Festival',
    'City',
    'distance_diff_KM',
    'Delivery_person_Age_missing',
    'Delivery_person_Ratings_missing',
    'Time_Orderd_missing',
    'Weatherconditions_missing',
    'Road_traffic_density_missing',
    'multiple_deliveries_missing',
    'Festival_missing',
    'City_missing',
    'days_in_month',
    'is_weekend',
    'feat9',
    'feat12']


def preprocess_data(test, encoder, imp_feat=None):
    if imp_feat is None:
        imp_feat = selected_feat_[:12]

    test['Weatherconditions'] = test['Weatherconditions'].str.split(" ", expand=True)[1]
    num_cols_test = [
        'Delivery_person_Age',
        'Delivery_person_Ratings',
        'Restaurant_latitude',
        'Restaurant_longitude',
        'Delivery_location_latitude',
        'Delivery_location_longitude',
        'Vehicle_condition',
        'multiple_deliveries', ]
    for col in num_cols_test:
        test[col] = test[col].astype('float64')
    test['Order_Date'] = pd.to_datetime(test['Order_Date'], format="%d-%m-%Y")

    test['Time_Orderd'] = pd.to_timedelta(test['Time_Orderd'])
    test['Time_Order_picked'] = pd.to_timedelta(test['Time_Order_picked'])

    test['Time_Order_picked_formatted'] = np.where(test['Time_Order_picked'] < test['Time_Orderd'],
                                                   test['Order_Date'] + pd.DateOffset(1) + test['Time_Order_picked'],
                                                   test['Order_Date'] + test['Time_Order_picked'])
    test['Time_Ordered_formatted'] = test['Order_Date'] + test['Time_Orderd']
    test['order_prepare_time_diff_mins'] = ((test['Time_Order_picked_formatted'] - test[
        'Time_Ordered_formatted']).dt.total_seconds()) / 60

    cols = ['Restaurant_latitude', 'Restaurant_longitude', 'Delivery_location_latitude', 'Delivery_location_longitude']
    for col in cols:
        test[col] = abs(test[col])

    test['distance_diff_KM'] = np.zeros(len(test))
    restaurant_cordinates_test = test[['Restaurant_latitude', 'Restaurant_longitude']].to_numpy()
    delivery_location_cordinates_test = test[['Delivery_location_latitude', 'Delivery_location_longitude']].to_numpy()

    for i in range(len(test)):
        test['distance_diff_KM'].loc[i] = geodesic(restaurant_cordinates_test[i], delivery_location_cordinates_test[i])

    test["day"] = test.Order_Date.dt.day
    test["week"] = test.Order_Date.dt.isocalendar().week
    test["month"] = test.Order_Date.dt.month
    test["quarter"] = test.Order_Date.dt.quarter
    test["year"] = test.Order_Date.dt.year
    test["hour"] = test.Time_Orderd.dt.components['hours']
    test["dayofyear"] = test.Order_Date.dt.dayofyear
    test['day_of_week'] = test.Order_Date.dt.day_of_week.astype(int)
    test["is_month_start"] = test.Order_Date.dt.is_month_start.astype(int)
    test["is_month_end"] = test.Order_Date.dt.is_month_end.astype(int)
    test["is_quarter_start"] = test.Order_Date.dt.is_quarter_start.astype(int)
    test["is_quarter_end"] = test.Order_Date.dt.is_quarter_end.astype(int)
    test["is_year_start"] = test.Order_Date.dt.is_year_start.astype(int)
    test["is_year_end"] = test.Order_Date.dt.is_year_end.astype(int)
    test["is_leap_year"] = test.Order_Date.dt.is_leap_year.astype(int)
    test["days_in_month"] = test.Order_Date.dt.days_in_month
    test['is_weekend'] = np.where(test['day_of_week'].isin([5, 6]), 1, 0)

    test = test.drop(columns=["ID"], axis=1)

    test['distance_diff_KM'] = test['distance_diff_KM'].astype("str").str.extract('(\d+)')
    test['distance_diff_KM'] = test['distance_diff_KM'].astype("int64")

    test = test.drop(columns=['Order_Date', 'Time_Orderd', 'Time_Order_picked'], axis=1)
    test = test.drop(columns=['Time_Order_picked_formatted', 'Time_Ordered_formatted'])

    test['week'] = test['week'].astype("int64")

    for col in test.columns:
        if test[col].dtype == 'uint8':
            test[col] = test[col].astype("int64")

    test['city_code'] = test['Delivery_person_ID'].str.split("RES", expand=True)[0]

    test = test.drop(columns=["Delivery_person_ID"], axis=1)

    test = test[imp_feat]
    test = encoder.transform(test)
    return test


from joblib import load

reg = load('regression_model.pkl')  # Linear Regression
lgb = load('lightgbm_model.pkl')  # LightGBM
cat = load('catboost_model.pkl')  # CatBoost
encoder = load('mestimate_encoder.pkl')  # Encoder đã lưu
xgb_model = load('xgboost_model.pkl')  # XGBoost


def predict(test, encoder_=encoder):
    test = preprocess_data(test, encoder_)

    reg_pred_test = reg.predict(test)  # Linear Regression
    lgb_pred_test = lgb.predict(test)  # LightGBM
    cat_pred_test = cat.predict(test)  # CatBoost
    xgb_pred_test = xgb_model.predict(test)  # XGBoost
    test['time_taken'] = (
            reg_pred_test * 0.1 +  # Ví dụ cập nhật trọng số của Linear Regression
            lgb_pred_test * 0.4 +  # Ví dụ cập nhật trọng số của LightGBM
            cat_pred_test * 0.3 +  # Ví dụ cập nhật trọng số của CatBoost
            xgb_pred_test * 0.2  # Thêm tỷ lệ cho XGBoost
    )
    return test['time_taken'].values[0]
