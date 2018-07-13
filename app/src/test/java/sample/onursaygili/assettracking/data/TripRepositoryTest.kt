package sample.onursaygili.assettracking.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock
import sample.onursaygili.assettracking.data.local.LocationDao
import sample.onursaygili.assettracking.util.LiveDataTestUtil.getValue
import sample.onursaygili.assettracking.util.TripTestUtil
import sample.onursaygili.assettracking.data.local.Trip
import sample.onursaygili.assettracking.data.local.TripDao
import sample.onursaygili.assettracking.data.local.TripDatabase
import sample.onursaygili.assettracking.util.mock

@RunWith(JUnit4::class)
class TripRepositoryTest {

    private lateinit var repository: TripRepository
    private var tripDao: TripDao = mock()
    private var locationDao: LocationDao = mock()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    @Throws
    fun setUp() {
        val db = mock(TripDatabase::class.java)
        Mockito.`when`(db.tripDao()).thenReturn(tripDao)
        repository = TripRepository(tripDao, locationDao)

    }

    @Test
    fun getLocalTrips_shouldReturnLocalListOfTrips() {
        val testList = MutableLiveData<List<Trip>>()
        testList.value = TripTestUtil.getTripList()

        Mockito.`when`(tripDao.getTripList()).thenReturn(testList)

        val data = repository.getLocalTrips()
        Mockito.verify(tripDao).getTripList()
        Mockito.verifyNoMoreInteractions(tripDao)

        val observer = mock<Observer<List<Trip>>>()
        data.observeForever(observer)
        Mockito.verify(observer).onChanged(getValue(testList))
    }

    @Test
    fun addAllLocalTrips_shouldAddListOfTripsToLocalDB() {
        val testList = TripTestUtil.getTripList()

        repository.addAllLocalTrips(testList)

        Mockito.verify(tripDao).addAll(testList)
        Mockito.verifyNoMoreInteractions(tripDao)
    }

    @Test
    fun getCurrentTrip() {
    }

    @Test
    fun saveLocalTrip() {
    }
}