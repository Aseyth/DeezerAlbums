package com.dalbum.deezeralbums.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Run [body] in a [FragmentTransaction] which is committed
 *
 * @param body FragmentTransaction.() -> Unit
 */
inline fun FragmentManager.transaction(body: FragmentTransaction.() -> Unit) {
    beginTransaction().apply(body).commit()
}

/**
 * Replace [id] container with the [fragment]
 *
 * @param fragment Fragment
 * @param id Int
 * @param addToBackStack Boolean
 * @param body (FragmentTransaction.() -> Unit)?
 */
fun FragmentManager.replace(fragment: Fragment, @IdRes id: Int, addToBackStack: Boolean = true, body: (FragmentTransaction.() -> Unit)? = null) {
    transaction {
        body?.invoke(this)
        if (addToBackStack) {
            addToBackStack(fragment.javaClass.simpleName)
        }
        replace(id, fragment, fragment.javaClass.simpleName)
    }
}

/**
 * Add [id] container with the [fragment]
 *
 * @param fragment Fragment
 * @param id Int
 * @param addToBackStack Boolean
 * @param body (FragmentTransaction.() -> Unit)?
 */
fun FragmentManager.add(fragment: Fragment, @IdRes id: Int, addToBackStack: Boolean = true, body: (FragmentTransaction.() -> Unit)? = null) {
    transaction {
        body?.invoke(this)
        if (addToBackStack) {
            addToBackStack(fragment.javaClass.simpleName)
        }
        add(id, fragment, fragment.javaClass.simpleName)
    }
}