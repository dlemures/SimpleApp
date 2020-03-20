package com.example.simpleapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.simpleapp.model.Event
import com.example.simpleapp.model.ScreenLoad
import com.example.simpleapp.model.ViewState
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CatFactsViewModel : ViewModel() {

    private val eventEmitter = PublishRelay.create<Event>()
    private val disposables = CompositeDisposable()

    val viewState: Observable<ViewState>

    init {
        val observable = eventEmitter
            .observeOn(Schedulers.io())
            .doOnNext { event -> Log.d(LOG_TAG, "----- event $event") }
            .eventToActions()
            .doOnNext { action -> Log.d(LOG_TAG, "----- action $action") }
            .actionToViewState()
            .doOnNext { state -> Log.d(LOG_TAG, "----- view state $state") }
            .replay(1)

        observable.connect { disposables.add(it) }
        viewState = observable.subscribeOn(Schedulers.io())

        // Initial event
        sendEvent(ScreenLoad)
    }

    override fun onCleared() {
        disposables.dispose()
    }

    fun sendEvent(event: Event) {
        eventEmitter.accept(event)
    }

    companion object {
        private const val LOG_TAG = "CatFacts MVI"
    }
}
