package com.example.simpleapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleapp.R
import com.example.simpleapp.model.CatFact

class CatFactListAdapter(private val endOfListLambda: (Int) -> Unit) : RecyclerView.Adapter<CatFactListAdapter.CatFactViewHolder>() {

    private var dataSet = emptyList<CatFact>()

    class CatFactViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatFactViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_catfact, parent, false
        ) as TextView

        return CatFactViewHolder(textView)
    }

    override fun onBindViewHolder(holder: CatFactViewHolder, position: Int) {
        holder.textView.text = dataSet[position].text
        if (position + LIST_END_THRESHOLD == dataSet.size) endOfListLambda(dataSet.size)
    }

    override fun getItemCount() = dataSet.size

    fun updateList(catFacts: List<CatFact>) {
        dataSet = catFacts
        notifyDataSetChanged()
    }

    companion object {
        private const val LIST_END_THRESHOLD = 3
    }
}
