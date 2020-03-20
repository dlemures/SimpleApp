package com.example.simpleapp.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleapp.R
import com.example.simpleapp.model.EndOfListReached
import com.example.simpleapp.model.Event
import com.example.simpleapp.model.ViewState
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

class CatFactsTemplate : ConstraintLayout {

    private val uiEventEmitter = PublishRelay.create<Event>()

    private val progressBar: ProgressBar
    private val errorView: TextView
    private val recyclerView: RecyclerView
    private val listAdapter: CatFactListAdapter

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.template_catfacts, this)

        listAdapter = CatFactListAdapter { listSize -> uiEventEmitter.accept(EndOfListReached(listSize) ) }

        progressBar = findViewById(R.id.loading)
        errorView = findViewById(R.id.error)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    // This is a very simple view, so render is just going element by element of the ViewState.
    // If it was more complicated, the layout would be composed by smaller views and each of them
    // would be using its own UI model. So here, we would just render each view of the screen based
    // on the UI models given to us within the ViewState, using a smart mapping.
    fun render(state: ViewState) {
        progressBar.visibility = if (state.loading) VISIBLE else GONE
        errorView.visibility =  if (state.errorMessage != null) VISIBLE else GONE
        recyclerView.visibility = if (state.catFacts.isNotEmpty()) VISIBLE else GONE
        listAdapter.updateList(state.catFacts)
        errorView.text = state.errorMessage ?: ""
    }

    fun observe(): Observable<Event> = uiEventEmitter.hide()
}
