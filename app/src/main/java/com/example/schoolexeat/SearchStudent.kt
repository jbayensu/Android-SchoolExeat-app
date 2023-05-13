package com.example.schoolexeat

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.schoolexeat.adapters.SearchResultAdapter
import com.example.schoolexeat.databinding.ActivitySearchStudentBinding
import com.example.schoolexeat.data.Student
import org.json.JSONObject

class SearchStudent : AppCompatActivity() {
    private lateinit var binding: ActivitySearchStudentBinding
    private lateinit var searchResultAdapter: SearchResultAdapter
    private var studentList: ArrayList<Student> = ArrayList()


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set toolbar as support action bar


        supportActionBar?.apply {
            title = "Search Student"

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)}

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.svSearchStudent.imeOptions = EditorInfo.IME_ACTION_DONE

        binding.svSearchStudent.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                searchResultAdapter.filter.filter(p0)
                return false
            }

        })


        getStudentDetails()
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvSearchResult.layoutManager = layoutManager


    }



    private fun getStudentDetails() {

        val url = StudentDetail.getUrl("getStudentDetails.php")
        //pbLoading.visibility = View.VISIBLE

        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->

                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("Student List")

                for(i in 0 until jsonArray.length()){
                    val jo = jsonArray.getJSONObject(i)
                    val id = jo.get("student_id").toString()
                    val name = jo.get("student_name").toString()
                    val house = jo.get("house_name").toString()
                    val sform = jo.get("form_name").toString()
                    val gfon = jo.get("guardian_ph").toString()
                    val stud = Student(id, name, house, sform, gfon)
                    studentList.add(stud)

                }
                searchResultAdapter = SearchResultAdapter(this, studentList)
                binding.rvSearchResult.adapter = searchResultAdapter

            }, { error ->
            })
        requestQueue.add(stringRequest)
    }

    fun finishSearch() {
        val intent = when(StudentDetail.view){
            0 -> Intent(this, MainActivity::class.java)
            else -> {
                Intent(this, ExeatInfo::class.java)}
        }
        startActivity(intent)
        finish()
    }









}