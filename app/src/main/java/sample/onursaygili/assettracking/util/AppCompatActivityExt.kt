package sample.onursaygili.assettracking.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

inline fun FragmentActivity.setContentFragment(containerViewId: Int, f: () -> Fragment): Fragment? {
    val manager = supportFragmentManager
    val fragment = manager?.findFragmentById(containerViewId)
    fragment?.let { return it }
    return f().apply { manager?.beginTransaction()?.add(containerViewId, this)?.commit() }
}

fun FragmentActivity.replaceContentFragment(containerViewId: Int, fragment: Fragment) {
    val manager = supportFragmentManager
    manager?.beginTransaction()?.replace(containerViewId, fragment)?.commit()
}