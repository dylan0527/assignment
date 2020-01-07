package com.example.assignment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="status")
data class Status(@PrimaryKey val status: String,
                  val name:String,
                  val job:String) {

}