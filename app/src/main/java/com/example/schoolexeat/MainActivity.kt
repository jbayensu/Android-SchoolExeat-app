package com.example.schoolexeat

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.schoolexeat.data.Student
import com.example.schoolexeat.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var studentName: String
    private lateinit var studentClass: String
    private lateinit var studentHouse: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StudentDetail.view = 0


        studentName = StudentDetail.student.studentName
        studentClass = StudentDetail.student.studentClass
        studentHouse = StudentDetail.student.house

        val studentDetail = "$studentName, $studentHouse and $studentClass"
        binding.tvStudentDetails.text = studentDetail

        binding.btnSearch.setOnClickListener {
            val intent = Intent(this, SearchStudent::class.java)
            startActivity(intent)
            this.finish()
        }
        binding.btnGrantExeat.setOnClickListener {
            if(isStudentSelected()){
                val intent = Intent(this, GrantExeat::class.java)
                startActivity(intent)
            }else{
                displayMessage(it)
            }
        }
        binding.btnRevokeExeat.setOnClickListener {
            if(isStudentSelected()) {
                val intent = Intent(this, RevokeExeat::class.java)
                startActivity(intent)
            }else{
                displayMessage(it)
            }
        }

        binding.btnViewDetails.setOnClickListener {
            if (isStudentSelected()) {
                val intent = Intent(this, ExeatHistory::class.java)
                startActivity(intent)
            }else{
                displayMessage(it)
            }
        }

    }

    private fun displayMessage(it: View) {
        Snackbar.make(it, "Please search and select Student", Snackbar.LENGTH_SHORT).show()
    }


    private fun isStudentSelected(): Boolean{
        if(StudentDetail.student.studentId == ""){
            return false
        }
        return true
    }

}