package com.example.simpleapp.usecase

import com.example.simpleapp.action.Action
import com.example.simpleapp.action.AddCatFacts
import com.example.simpleapp.action.ShowError
import com.example.simpleapp.action.StartLoading
import com.example.simpleapp.data.CatFactRepository
import com.example.simpleapp.model.ScreenLoad
import io.reactivex.Observable

private const val INITIAL_REQUEST_LENGTH = 15

internal fun Observable<ScreenLoad>.loadSCreen(): Observable<out Action> = switchMap {
    CatFactRepository().getCatFacts(0, INITIAL_REQUEST_LENGTH)
        .map<Action> { catFacts -> AddCatFacts(catFacts) }
        .toObservable()
        .startWith(StartLoading())
        .onErrorReturnItem(ShowError("Cannot load the cat facts"))
}
