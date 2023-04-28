package com.hebs.moviedb.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.hebs.moviedb.BuildConfig
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Complement to View Binding usage.
 * It's not a "solution", it's just a way to avoid creating a null Binding instance using
 * a delegated property which is life cycle aware in order to null the binding instance when
 * fragment is destroyed.
 * Also you don't have to initialize the instance inside onCreateView, you only have to
 * return root view from the binding object.
 *
 * Usage:
 *  private val binding by viewBinding() {
 *      ExampleBinding.inflate(LayoutInflater.from(requireContext()))
 *  }
 *
 *  onCreateView(...) {
 *      ...
 *      return binding.root
 *  }
 *
 */
fun <T> Fragment.viewBinding(initialize: () -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

        private var binding: T? = null

        init {
            this@viewBinding.viewLifecycleOwnerLiveData.observe(
                this@viewBinding
            ) { owner: LifecycleOwner ->
                owner.lifecycle.addObserver(this)
            }
        }

        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }

        override fun getValue(
            thisRef: Fragment, property: KProperty<*>
        ): T {
            return this.binding
                ?: if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                    error("Called before onCreateView or after onDestroyView.")
                } else {
                    initialize().also {
                        this.binding = it
                    }
                }
        }
    }


fun <T : Any> Single<T>.applySchedulers(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T : Any> Observable<T>.applySchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T : Any> Flowable<T>.applySchedulers(): Flowable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T : Any> Single<T>.applyIoSchedulers(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
}

fun <T : Any> Observable<T>.applyIoSchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
}

private fun String?.getImageFullPath(): String? {
    return this.takeIf { it?.isNotBlank() == true }
        ?.let { "${BuildConfig.TMDB_IMAGE_BASE_URL}/$this" }
}

fun ImageView.loadImage(url: String, useFullPath: Boolean = true) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop()

    val shimmer = Shimmer.AlphaHighlightBuilder().setDuration(1500).setBaseAlpha(0.9f)
        .setHighlightAlpha(0.85f).setDirection(Shimmer.Direction.LEFT_TO_RIGHT).setAutoStart(true)
        .build()

    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }

    val imageFullPath = if (useFullPath) url.getImageFullPath() else url
    Glide.with(this.context).load(imageFullPath).placeholder(shimmerDrawable).apply(requestOptions)
        .into(this)
}


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun openUrl(url: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        addCategory(Intent.CATEGORY_BROWSABLE)
        data = Uri.parse(url)
    }
    context.startActivity(intent)
}


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.showKeyboard() {
    view?.let { activity?.showKeyboard() }
}

fun Context.showKeyboard() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(
        InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY
    )
}