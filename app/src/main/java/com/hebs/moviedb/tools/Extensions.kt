package com.hebs.moviedb.tools

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.hebs.moviedb.data.mappers.MapperBase
import com.hebs.moviedb.data.model.ResourceEntity
import com.hebs.moviedb.data.model.ResourceEntityResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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
            this@viewBinding
                .viewLifecycleOwnerLiveData
                .observe(
                    this@viewBinding
                ) { owner: LifecycleOwner ->
                    owner.lifecycle.addObserver(this)
                }
        }

        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }

        override fun getValue(
            thisRef: Fragment,
            property: KProperty<*>
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
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T : Any, P : Any> Single<List<T>>.mapResultApplySchedules(mapper: MapperBase<T, P>): Single<List<P>> {
    return this.map { resource ->
        resource.map { resourceEntity ->
            mapper.fromEntity(resourceEntity)
        }
    }.applySchedulers()
}

fun <P : Any> Single<ResourceEntityResponse>.mapResourceResultApplySchedules(mapper: MapperBase<ResourceEntity, P>): Single<List<P>> =
    this.map {
        it.results.map { resourceEntity ->
            mapper.fromEntity(resourceEntity)
        }
    }.applySchedulers()
