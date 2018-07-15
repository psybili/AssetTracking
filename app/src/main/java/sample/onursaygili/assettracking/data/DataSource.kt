package sample.onursaygili.assettracking.data

import android.arch.lifecycle.LiveData
import sample.onursaygili.assettracking.data.local.Trip

interface  DataSource<T> {
    fun save(t: T)
    fun list(): LiveData<List<T>>
}
/**
    Single<List<City>> updateWeatherInfo();

    Maybe<List<City>> getAddedCitiesWeather();

    Single<City> saveOrUpdateCity(City city);

    Single<City> addCity(String cityName);

    Single<City> addCity(double lat, double lon);

    Completable removeCityById(int id);
*/