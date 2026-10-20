package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekly_evaluations")
data class WeeklyEvaluation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val studentId: Long,
    val weekTitle: String, // e.g. "Pekan 1 (Sabtu, 3 Okt 2026)"
    val submissionDate: String, // e.g. "Sabtu, 3 Okt 2026"
    val isSaturdaySubmission: Boolean = true,
    
    // Scores from Parent (1 to 5)
    val prayerDisciplineScore: Int = 4,      // Disiplin Ibadah & Doa
    val studyAndHomeworkScore: Int = 4,      // Jam Belajar & Mengerjakan PR
    val screenTimeLimitScore: Int = 3,       // Pembatasan Gadget / Screen Time
    val houseChoreHelpScore: Int = 4,        // Membantu Tugas Rumah & Mandiri
    val courtesyAndMannerScore: Int = 5,     // Sopan Santun & Adab Keluarga
    
    val goodHabitsSummary: String = "Bangun pagi, merapikan kasur, sholat/ibadah tertib, membatasi HP",
    val parentNotes: String = "",
    val parentSignature: String = "",
    
    // Teacher Evaluation & Feedback
    val isReviewedByTeacher: Boolean = false,
    val teacherName: String = "",
    val teacherFeedback: String = "",
    val characterBadge: String = "", // e.g. "Bintang Kejujuran", "Pejuang Disiplin", "Mandiri Hebat"
    val pointsAwarded: Int = 0,      // e.g. +10, +15 points added to student's record
    val reviewDate: String = ""
) {
    val averageScore: Float
        get() = (prayerDisciplineScore + studyAndHomeworkScore + screenTimeLimitScore + houseChoreHelpScore + courtesyAndMannerScore) / 5f
}
