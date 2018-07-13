package sample.onursaygili.assettracking.ui.tracking

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.*
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.preference.PreferenceManager
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.tracking_fragment.*
import kotlinx.android.synthetic.main.trip_list_item.*
import sample.onursaygili.assettracking.BuildConfig
import sample.onursaygili.assettracking.R
import sample.onursaygili.assettracking.data.LocationUpdatesService
import sample.onursaygili.assettracking.data.local.Trip
import sample.onursaygili.assettracking.databinding.TrackingFragmentBinding
import sample.onursaygili.assettracking.databinding.TripListFragmentBinding
import sample.onursaygili.assettracking.di.Injectable
import sample.onursaygili.assettracking.util.LocationUtils
import java.text.DateFormat
import javax.inject.Inject

private const val TAG = "TrackingFragment"
// Used in checking for runtime permissions.
private const val REQUEST_PERMISSIONS_REQUEST_CODE = 34

class TrackingFragment : Fragment(), Injectable, SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: TrackingViewModel

    private lateinit var binding: TrackingFragmentBinding

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private var locationBroadcastReceiver: LocationBroadcastReceiver? = null

    // A reference to the service used to get location updates.
    private var locationUpdatesService: LocationUpdatesService? = null

    // Tracks the bound state of the service.
    private var serviceBound = false

    // Monitors the state of the connection to the service.
    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as LocationUpdatesService.LocalBinder
            locationUpdatesService = binder.service
            serviceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            locationUpdatesService = null
            serviceBound = false
        }
    }

    companion object {
        fun newInstance(): TrackingFragment = TrackingFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<TrackingFragmentBinding>(inflater, R.layout.tracking_fragment, container, false
    ).also {
        binding = it
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(
                activity!!,
                viewModelFactory
        ).get(TrackingViewModel::class.java)

        locationBroadcastReceiver = LocationBroadcastReceiver()
        locationBroadcastReceiver!!.viewModel = viewModel

        // Check that the user hasn't revoked permissions by going to Settings.
        if (LocationUtils.requestingLocationUpdates(context!!)) {
            if (!checkPermissions()) {
                requestPermissions()
            }
        }

        viewModel.currentTrip.observe(activity!!, Observer {
            if (it != null) {
                displayTrip(it)
            }
        })

    }

    override fun onStart() {
        super.onStart()
        PreferenceManager.getDefaultSharedPreferences(context!!)
                .registerOnSharedPreferenceChangeListener(this)

        startButton!!.setOnClickListener {
            if (!checkPermissions()) {
                requestPermissions()
            } else {
                locationUpdatesService!!.requestLocationUpdates()
                viewModel.startTracking()
            }
        }

        stopButton!!.setOnClickListener {
            locationUpdatesService!!.removeLocationUpdates()
            viewModel.stopTracking()
        }

        // Restore the state of the buttons when the activity (re)launches.
        setButtonsState(LocationUtils.requestingLocationUpdates(context!!))

        // Bind to the service. If the service is in foreground mode, this signals to the service
        // that since this activity is in the foreground, the service can exit foreground mode.
        context!!.bindService(Intent(activity, LocationUpdatesService::class.java), mServiceConnection,
                Context.BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(context!!).registerReceiver(locationBroadcastReceiver!!,
                IntentFilter(LocationUpdatesService.ACTION_BROADCAST))
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(context!!).unregisterReceiver(locationBroadcastReceiver!!)
        super.onPause()
    }

    override fun onStop() {
        if (serviceBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            context!!.unbindService(mServiceConnection)
            serviceBound = false
        }
        PreferenceManager.getDefaultSharedPreferences(context!!)
                .unregisterOnSharedPreferenceChangeListener(this)
        super.onStop()
    }

    fun displayTrip(trip: Trip) {
        binding.setVariable(com.android.databinding.library.baseAdapters.BR.trip, trip)
        binding.executePendingBindings()
    }

    /**
     * Returns the current state of the permissions needed.
     */
    private fun checkPermissions(): Boolean {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(context!!,
                Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun requestPermissions() {
        val shouldProvideRationale = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            Snackbar.make(
                    fragment_tracking_root,
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok) {
                        // Request permission
                        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                REQUEST_PERMISSIONS_REQUEST_CODE)
                    }
                    .show()
        } else {
            Log.i(TAG, "Requesting permission")
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_REQUEST_CODE)
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.locationUpdatesService!!.requestLocationUpdates()
                locationUpdatesService!!.requestLocationUpdates()
                viewModel.startTracking()
            } else {
                // Permission denied.
                setButtonsState(false)
                Snackbar.make(
                        fragment_tracking_root,
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings) {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null)
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        .show()
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, s: String) {
        // Update the buttons state depending on whether location updates are being requested.
        if (s == LocationUtils.KEY_REQUESTING_LOCATION_UPDATES) {
            setButtonsState(sharedPreferences.getBoolean(LocationUtils.KEY_REQUESTING_LOCATION_UPDATES,
                    false))
        }
    }

    private fun setButtonsState(requestingLocationUpdates: Boolean) {
        if (requestingLocationUpdates) {
            startButton!!.isEnabled = false
            stopButton!!.isEnabled = true
            tripRecord.visibility = VISIBLE
        } else {
            startButton!!.isEnabled = true
            stopButton!!.isEnabled = false
            tripRecord.visibility = GONE
        }
    }


    class LocationBroadcastReceiver : BroadcastReceiver() {

        lateinit var viewModel: TrackingViewModel

        override fun onReceive(context: Context, intent: Intent) {
            val location = intent.getParcelableExtra<Location>(LocationUpdatesService.EXTRA_LOCATION)
            if (location != null) {
                Log.e(javaClass.name, LocationUtils.getLocationText(location))
                Toast.makeText(context, LocationUtils.getLocationText(location),
                        Toast.LENGTH_SHORT).show()
                viewModel.onLocationUpdate(location)
            }
        }

    }
}
