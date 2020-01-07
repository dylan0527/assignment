package com.example.assignment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="user")
data class User(@PrimaryKey val email: String,
                val name: String,
                val password:String) {

}