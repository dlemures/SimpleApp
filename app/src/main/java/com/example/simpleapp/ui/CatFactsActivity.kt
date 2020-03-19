package com.example.simpleapp.ui

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleapp.R
import com.example.simpleapp.model.*
import com.example.simpleapp.viewmodel.CatFactsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class CatFactsActivity : AppCompatActivity() {

    private val viewModel: CatFactsViewModel by lazy { ViewModelProvider(this)[CatFactsViewModel::class.java] }
    private val disposables = CompositeDisposable()

    private lateinit var progressBar: ProgressBar
    private lateinit var errorView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: CatFactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catfacts)

        listAdapter = CatFactListAdapter(viewModel::onEndOfListReached)

        progressBar = findViewById(R.id.loading)
        errorView = findViewById(R.id.error)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
    }

    override fun onStart() {
        super.onStart()
        disposables.add(
            viewModel.observeCatFacts()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateUI)
        )
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun updateUI(state: State) {
        when (state) {
            is Data -> updateList(state.catFacts)
            is Error -> showError(state.message, state.important)
            Loading -> showLoading()
        }
    }

    // To improve
    // * Support a way to refresh the whole list (not just append)
    private fun updateList(catFacts: List<CatFact>) {
        listAdapter.addElements(catFacts)
        recyclerView.visibility = VISIBLE
        progressBar.visibility = GONE
        errorView.visibility = GONE
    }

    // To improve
    // * A better way to display errors and not depend on a flag. MVI would improve
    private fun showError(errorMessage: String, important: Boolean) {
        if (important) {
            errorView.text = errorMessage
            errorView.visibility = VISIBLE
            recyclerView.visibility = GONE
            progressBar.visibility = GONE
        } else {
            Toast.makeText(this, errorMessage, LENGTH_SHORT).show()
        }
    }

    private fun showLoading() {
        progressBar.visibility = VISIBLE
        recyclerView.visibility = GONE
        errorView.visibility = GONE
    }
}
