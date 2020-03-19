package com.example.simpleapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.simpleapp.data.CatFactRepository
import com.example.simpleapp.model.Data
import com.example.simpleapp.model.Error
import com.example.simpleapp.model.Loading
import com.example.simpleapp.model.State
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CatFactsViewModel : ViewModel() {

    private val catFactRepository = CatFactRepository()

    private val catFactsRelay = BehaviorRelay.create<State>()
    private val disposables = CompositeDisposable()

    init {
        disposables.add(
            catFactRepository.getCatFacts(0, REQUEST_LENGTH)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { catFactsRelay.accept(Loading) }
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { catFacts -> catFactsRelay.accept(Data(catFacts)) },
                    { throwable -> catFactsRelay.accept(Error(true, "Cannot load the cat facts", throwable)) }
                )
        )
    }

    override fun onCleared() {
        disposables.dispose()
    }

    fun observeCatFacts(): Observable<State> = catFactsRelay.hide()

    // To improve
    // * Retry if it fails
    // * Avoid repeating requests when you scroll crazy at the end of the list
    fun onEndOfListReached(listSize: Int) {
        disposables.add(
            catFactRepository.getCatFacts(listSize, REQUEST_LENGTH)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { catFacts -> catFactsRelay.accept(Data(catFacts)) },
                    { throwable -> catFactsRelay.accept(Error(false, "Cannot load more cat facts", throwable)) }
                )
        )
    }

    companion object {
        private const val REQUEST_LENGTH = 15
    }
}
