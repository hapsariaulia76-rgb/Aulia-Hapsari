package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val nis: String,
    val className: String,
    val parentName: String,
    val avatarColorHex: String = "#D97706",
    val disciplinePoints: Int = 100,
    val characterLevel: String = "Teladan Disiplin"
)
