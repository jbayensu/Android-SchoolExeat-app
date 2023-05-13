package com.example.schoolexeat

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.schoolexeat.databinding.ActivityRevokeExeatBinding

class RevokeExeat : AppCompatActivity() {
    private lateinit var binding:ActivityRevokeExeatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRevokeExeatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvStudentName.text = StudentDetail.student.studentName
        binding.tvHouse.text = StudentDetail.student.house
        binding.tvClass.text = StudentDetail.student.studentClass
        binding.tvGuardianContact.text = StudentDetail.student.guardianPhone
    }
}