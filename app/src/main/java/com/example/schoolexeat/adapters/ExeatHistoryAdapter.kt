package com.example.schoolexeat.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolexeat.StudentDetail
import com.example.schoolexeat.databinding.ExeatDetailsBinding
import com.example.schoolexeat.data.Exeat
import com.example.schoolexeat.data.Student
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

class ExeatHistoryAdapter(private val exeatHistoryList:ArrayList<Exeat>, )
    : RecyclerView.Adapter<ExeatHistoryAdapter.ViewHolder>(), Filterable {

    private val mainList = exeatHistoryList
    private val searchList = ArrayList<Exeat>(exeatHistoryList)
    inner class ViewHolder(val binding: ExeatDetailsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExeatDetailsBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(exeatHistoryList[position]){
                binding.tvExeatId.text = this.exeatId
                binding.tvStarttoEnd.text = "From ${this.startDate} to ${this.endDate}"
                binding.tvExeatStatus.text = this.status
                binding.tvStudentNamec.text = StudentDetail.student.studentName
                binding.tvClassc.text = StudentDetail.student.studentClass
                binding.tvHousec.text = StudentDetail.student.house
                binding.root.setOnClickListener {
                    Snackbar.make(it, this.reasonGranted, Snackbar.LENGTH_SHORT).show()
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return exeatHistoryList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence): Filter.FilterResults {
                val filteredList = ArrayList<Exeat>()

                if (p0.isBlank() or p0.isEmpty()) {
                    filteredList.addAll(searchList)
                } else {
                    val filterPattern = p0.toString().lowercase().trim()

                    searchList.forEach {
                        if (it.startDate.lowercase(Locale.ROOT)
                                .contains(filterPattern) or it.endDate.lowercase(Locale.ROOT)
                                .contains(filterPattern)
                        ) {
                            filteredList.add(it)
                        }

                    }
                }

                val result = Filter.FilterResults()
                result.values = filteredList
                return result
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                mainList.clear()
                mainList.addAll(p1!!.values as List<Exeat>)
                notifyDataSetChanged()
            }
        }
    }




}