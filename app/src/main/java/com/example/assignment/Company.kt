package com.example.assignment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="company")
data class Company(@PrimaryKey val name: String,
                val description: String,
                   val rating:Int,
                   val job:String,
                   val jobDescription:String,
                   val review:String,
                   val salary:Int) {

}