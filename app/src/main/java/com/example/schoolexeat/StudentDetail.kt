package com.example.schoolexeat

import com.example.schoolexeat.data.Student

object StudentDetail {
    var students: ArrayList<Student> = ArrayList()
    var student: Student = Student("", "Student Name", "House", "Class","")
    var view = 0
    var url = "http://192.168.0.144:82/school_exeat/API/"

    fun addStudent(student: Student){
        students = (students + arrayListOf(students)) as ArrayList<Student>
    }

    fun clear(){
        students = ArrayList()
    }

    fun getUrl(fileName: String):String{
        return "$url$fileName"
    }
}