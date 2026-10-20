package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "discipline_records")
data class DisciplineRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val studentId: Long,
    val title: String,
    val category: String, // "Rumah" or "Sekolah"
    val pointDelta: Int,  // e.g. +10, +15, -5
    val date: String,
    val giverName: String,
    val description: String
)
