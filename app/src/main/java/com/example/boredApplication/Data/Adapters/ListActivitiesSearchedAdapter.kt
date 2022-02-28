package com.example.boredApplication.Data.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.boredApplication.Data.Constantes
import com.example.boredApplication.Data.Utils.Companion.getRandomColor
import com.example.boredApplication.Data.Models.ActivityEntity
import com.example.boredApplication.R
import org.jetbrains.anko.backgroundColor

class ListActivitiesSearchedAdapter(private var context: Context): RecyclerView.Adapter<ListActivitiesSearchedAdapter.ActivityiewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityiewHolder {
        val suggestItemView = LayoutInflater.from(parent.context).inflate(R.layout.item_activity, parent, false)

        return ActivityiewHolder(suggestItemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ActivityiewHolder, position: Int) {
        val currentActivity: ActivityEntity = listActivities[position]

        if (currentActivity.activity.isNotBlank()) {
            holder.activityTextview?.text =
                "${context.getString(R.string.activity_text)} ${Constantes.CONST_SEPARATOR} ${currentActivity.activity}"
            holder.activityTextview?.visibility = View.VISIBLE
        } else {
            holder.activityTextview?.visibility = View.GONE
        }
        if (!currentActivity.type.isNullOrEmpty()) {
            holder.typeTextview?.text =
                "${context.getString(R.string.type_text)} ${Constantes.CONST_SEPARATOR} ${currentActivity.type}"
            holder.typeTextview?.visibility = View.VISIBLE
        } else {
            holder.typeTextview?.visibility = View.GONE
        }
        holder.participantsTextview?.text = "${context.getString(R.string.participants_text)} ${Constantes.CONST_SEPARATOR} ${currentActivity.participants.toString()}"
        holder.priceTextview?.text = "${context.getString(R.string.price_text)} ${Constantes.CONST_SEPARATOR} ${currentActivity.price.toString()}"
        if (!currentActivity.link.isNullOrEmpty()) {
            holder.linkTextview?.text =
                "${context.getString(R.string.link_text)} ${Constantes.CONST_SEPARATOR} ${currentActivity.link}"
            holder.linkTextview?.visibility = View.VISIBLE
        } else {
            holder.linkTextview?.visibility = View.GONE
        }
        if (!currentActivity.key.isNullOrEmpty()) {
            holder.keyTextview?.text =
                "${context.getString(R.string.key_text)} ${Constantes.CONST_SEPARATOR} ${currentActivity.key}"
            holder.keyTextview?.visibility = View.GONE
        } else {
            holder.keyTextview?.visibility = View.GONE
        }
        holder.accessibilityTextview?.text = "${context.getString(R.string.accessibility_text)} ${Constantes.CONST_SEPARATOR} ${currentActivity.accessibility.toString()}"
        holder.itemView.backgroundColor = getRandomColor()
    }

    override fun getItemCount(): Int {
        return listActivities.size
    }

    fun setActivities(activities: List<ActivityEntity>) {
        this.listActivities = activities
        this.listFiltered = activities
    }

    fun getActivity(position: Int): ActivityEntity {
        return listActivities[position]
    }

    inner class ActivityiewHolder(itemView: View) : ViewHolder(itemView) {
        var activityTextview: TextView? = itemView.findViewById(R.id.activity_materialTextView)
        var typeTextview: TextView? = itemView.findViewById(R.id.type_materialTextView)
        var participantsTextview: TextView? = itemView.findViewById(R.id.participants_materialTextView)
        var priceTextview: TextView? = itemView.findViewById(R.id.price_materialTextView)
        var linkTextview: TextView? = itemView.findViewById(R.id.link_materialTextView)
        var keyTextview: TextView? = itemView.findViewById(R.id.key_materialTextView)
        var accessibilityTextview: TextView? = itemView.findViewById(R.id.accessibility_materialTextView)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()

                if (constraint == null || constraint.isEmpty()) {
                    //no constraint given, just return all the data. (no search)
                    results.count = listFiltered.size
                    results.values = listFiltered
                } else {
                    val resultsData = ArrayList<ActivityEntity>()
                    val searchStr = constraint.toString().lowercase()

                    for (s in listFiltered)
                        if (s.type!!.lowercase().contains(searchStr) || s.activity.lowercase().contains(searchStr)) resultsData.add(s)
                    results.count = resultsData.size
                    results.values = resultsData
                }
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                listActivities = results.values as ArrayList<ActivityEntity>
            }
        }
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private var listActivities: List<ActivityEntity> = arrayListOf()
    private var listFiltered = listActivities
}