package com.example.simpleapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.simpleapp.R
import com.example.simpleapp.viewmodel.CatFactsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class CatFactsActivity : AppCompatActivity() {

    private val viewModel: CatFactsViewModel by lazy { ViewModelProvider(this)[CatFactsViewModel::class.java] }
    private val disposables = CompositeDisposable()

    private lateinit var template: CatFactsTemplate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catfacts)
        template = findViewById(R.id.catfactsTemplate)
    }

    override fun onStart() {
        super.onStart()

        disposables.add(
            viewModel.viewState
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(template::render) { e -> Log.e(LOG_TAG, e.message) }
        )

        disposables.add(
            template.observe()
                .subscribe(viewModel::sendEvent) { e -> Log.e(LOG_TAG, e.message) }
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

    companion object {
        private const val LOG_TAG = "CatFacts MVI Error"
    }
}
