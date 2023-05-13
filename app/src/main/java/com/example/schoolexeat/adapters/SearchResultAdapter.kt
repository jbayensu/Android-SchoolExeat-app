package com.example.schoolexeat.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolexeat.*
import com.example.schoolexeat.databinding.StudentDetailBinding
import com.example.schoolexeat.data.Student
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

class SearchResultAdapter (val context: Context, private val studentList:ArrayList<Student>, )
: RecyclerView.Adapter<SearchResultAdapter.ViewHolder>(), Filterable {

    private val mainList = studentList
    private val searchList = ArrayList<Student>(studentList)
    private val activity : SearchStudent = context as SearchStudent
    inner class ViewHolder(val binding: StudentDetailBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StudentDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(studentList[position]){
                binding.tvStudentNamec.text = this.studentName
                binding.tvHousec.text = this.house
                binding.tvClassc.text = this.studentClass
                binding.root.setOnClickListener {
                    val data = Intent()
                    data.putExtra(STUDENT_NAME, studentName)
                    data.putExtra(STUDENT_CLASS, studentClass)
                    data.putExtra(STUDENT_HOUSE, house)
                    StudentDetail.student.studentName = studentName
                    StudentDetail.student.studentClass = studentClass
                    StudentDetail.student.studentId = studentId
                    StudentDetail.student.house = house
                    StudentDetail.student.guardianPhone = this.guardianPhone
                    activity.finishSearch()
                    Snackbar.make(it, this.studentName, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun getFilter(): Filter {
        return object:Filter(){
            override fun performFiltering(p0: CharSequence): FilterResults {
                val filteredList = ArrayList<Student>()


                if(p0.isBlank() or p0.isEmpty()){
                    filteredList.addAll(searchList)
                }else{
                    val filterPattern = p0.toString().lowercase().trim()

                    searchList.forEach {
                        if(it.studentName.lowercase(Locale.ROOT).contains(filterPattern)){
                            filteredList.add(it)
                        }
                        
                    }
                }

                val result = FilterResults()
                result.values = filteredList
                return  result

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                mainList.clear()
                mainList.addAll(p1!!.values as List<Student>)
                notifyDataSetChanged()
            }

        }
    }

}