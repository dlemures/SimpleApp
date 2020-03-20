package com.example.simpleapp.usecase

import com.example.simpleapp.action.Action
import com.example.simpleapp.action.AddCatFacts
import com.example.simpleapp.data.CatFactRepository
import com.example.simpleapp.model.EndOfListReached
import io.reactivex.Observable
import io.reactivex.Observable.empty

private const val REQUEST_LENGTH = 10

// To improve:
//   * Making sure that a user cannot repeat the same API multiple times
//     if they scroll crazy at the end of the list
internal fun Observable<EndOfListReached>.fetchMoreCatFacts(): Observable<out Action> = switchMap { eventInfo ->
    CatFactRepository().getCatFacts(eventInfo.listSize, REQUEST_LENGTH)
        .map<Action> { catFacts -> AddCatFacts(catFacts) }
        .toObservable()
        // To improve:
        //   * Add a new stream to handle one time events (notifications for instance)
        //     that way we handle errors in a better way and not just failing
        //     silently.
        //   * We should also implement retry policy
        .onErrorResumeNext(empty())
}
