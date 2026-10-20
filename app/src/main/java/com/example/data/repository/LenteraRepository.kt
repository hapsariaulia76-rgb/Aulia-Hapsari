package com.example.data.repository

import com.example.data.db.LenteraDao
import com.example.data.model.DisciplineRecord
import com.example.data.model.Student
import com.example.data.model.WeeklyEvaluation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class LenteraRepository(private val dao: LenteraDao) {

    val allStudents: Flow<List<Student>> = dao.getAllStudents()
    val allEvaluations: Flow<List<WeeklyEvaluation>> = dao.getAllEvaluations()
    val allDisciplineRecords: Flow<List<DisciplineRecord>> = dao.getAllDisciplineRecords()

    fun getEvaluationsForStudent(studentId: Long): Flow<List<WeeklyEvaluation>> =
        dao.getEvaluationsByStudent(studentId)

    fun getDisciplineRecordsForStudent(studentId: Long): Flow<List<DisciplineRecord>> =
        dao.getDisciplineRecordsByStudent(studentId)

    suspend fun addStudent(student: Student): Long {
        return dao.insertStudent(student)
    }

    suspend fun submitSaturdayEvaluation(evaluation: WeeklyEvaluation): Long {
        return dao.insertEvaluation(evaluation)
    }

    suspend fun submitTeacherFeedback(
        evaluation: WeeklyEvaluation,
        teacherName: String,
        feedback: String,
        badge: String,
        awardedPoints: Int,
        reviewDate: String,
        currentStudent: Student
    ) {
        val updatedEvaluation = evaluation.copy(
            isReviewedByTeacher = true,
            teacherName = teacherName,
            teacherFeedback = feedback,
            characterBadge = badge,
            pointsAwarded = awardedPoints,
            reviewDate = reviewDate
        )
        dao.updateEvaluation(updatedEvaluation)

        // Add discipline record
        val newRecord = DisciplineRecord(
            studentId = evaluation.studentId,
            title = "Apresiasi Evaluasi: ${evaluation.weekTitle}",
            category = "Karakter Rumah & Sekolah",
            pointDelta = awardedPoints,
            date = reviewDate,
            giverName = teacherName,
            description = feedback
        )
        dao.insertDisciplineRecord(newRecord)

        // Update student total points & level
        val newTotalPoints = currentStudent.disciplinePoints + awardedPoints
        val newLevel = when {
            newTotalPoints >= 150 -> "Teladan Karakter Utama"
            newTotalPoints >= 120 -> "Bintang Disiplin Emas"
            newTotalPoints >= 90 -> "Pejuang Karakter Baik"
            newTotalPoints >= 60 -> "Penjelajah Karakter Mandiri"
            else -> "Tahap Pendampingan"
        }
        dao.updateStudentPoints(currentStudent.id, newTotalPoints, newLevel)
    }

    suspend fun addManualDisciplinePoint(
        student: Student,
        title: String,
        category: String,
        delta: Int,
        giverName: String,
        date: String,
        note: String
    ) {
        val record = DisciplineRecord(
            studentId = student.id,
            title = title,
            category = category,
            pointDelta = delta,
            date = date,
            giverName = giverName,
            description = note
        )
        dao.insertDisciplineRecord(record)

        val newTotal = (student.disciplinePoints + delta).coerceAtLeast(0)
        val newLevel = when {
            newTotal >= 150 -> "Teladan Karakter Utama"
            newTotal >= 120 -> "Bintang Disiplin Emas"
            newTotal >= 90 -> "Pejuang Karakter Baik"
            newTotal >= 60 -> "Penjelajah Karakter Mandiri"
            else -> "Tahap Pendampingan"
        }
        dao.updateStudentPoints(student.id, newTotal, newLevel)
    }

    suspend fun checkAndSeedInitialData() {
        val count = dao.getStudentCount()
        if (count == 0) {
            // Seed initial students
            val student1Id = dao.insertStudent(
                Student(
                    name = "Ahmad Fauzi",
                    nis = "2026/041",
                    className = "Kelas 4-A",
                    parentName = "Bpk. Hendra & Ibu Nur",
                    avatarColorHex = "#D97706",
                    disciplinePoints = 125,
                    characterLevel = "Bintang Disiplin Emas"
                )
            )

            val student2Id = dao.insertStudent(
                Student(
                    name = "Siti Nur Aisyah",
                    nis = "2026/042",
                    className = "Kelas 4-A",
                    parentName = "Ibu Rahmawati",
                    avatarColorHex = "#2563EB",
                    disciplinePoints = 135,
                    characterLevel = "Bintang Disiplin Emas"
                )
            )

            val student3Id = dao.insertStudent(
                Student(
                    name = "Budi Pratama",
                    nis = "2026/043",
                    className = "Kelas 4-A",
                    parentName = "Bpk. Santoso",
                    avatarColorHex = "#059669",
                    disciplinePoints = 95,
                    characterLevel = "Pejuang Karakter Baik"
                )
            )

            val student4Id = dao.insertStudent(
                Student(
                    name = "Zahra Almira",
                    nis = "2026/044",
                    className = "Kelas 4-A",
                    parentName = "Ibu Dewi Lestari",
                    avatarColorHex = "#7C3AED",
                    disciplinePoints = 110,
                    characterLevel = "Pejuang Karakter Baik"
                )
            )

            // Seed sample past Saturday evaluations for Ahmad Fauzi
            dao.insertEvaluation(
                WeeklyEvaluation(
                    studentId = student1Id,
                    weekTitle = "Pekan 1 (Sabtu, 26 Sep 2026)",
                    submissionDate = "Sabtu, 26 Sep 2026",
                    isSaturdaySubmission = true,
                    prayerDisciplineScore = 5,
                    studyAndHomeworkScore = 4,
                    screenTimeLimitScore = 4,
                    houseChoreHelpScore = 4,
                    courtesyAndMannerScore = 5,
                    goodHabitsSummary = "Tertib sholat 5 waktu, merapikan kamar, belajar PR matematika tuntas.",
                    parentNotes = "Ahmad pekan ini sangat rajin membantu ibu menyiram tanaman dan tidur tepat pukul 21.00.",
                    parentSignature = "Ibu Nur",
                    isReviewedByTeacher = true,
                    teacherName = "Bpk. Rahmad, S.Pd.",
                    teacherFeedback = "Luar biasa Ahmad! Teruskan keteladanan di rumah. Di sekolah Ahmad juga aktif dan sopan saat berdiskusi.",
                    characterBadge = "Bintang Kejujuran & Bakti",
                    pointsAwarded = 15,
                    reviewDate = "Senin, 28 Sep 2026"
                )
            )

            dao.insertEvaluation(
                WeeklyEvaluation(
                    studentId = student1Id,
                    weekTitle = "Pekan 2 (Sabtu, 3 Okt 2026)",
                    submissionDate = "Sabtu, 3 Okt 2026",
                    isSaturdaySubmission = true,
                    prayerDisciplineScore = 4,
                    studyAndHomeworkScore = 4,
                    screenTimeLimitScore = 3,
                    houseChoreHelpScore = 5,
                    courtesyAndMannerScore = 5,
                    goodHabitsSummary = "Membatasi main HP max 1 jam, membantu cuci piring sendiri.",
                    parentNotes = "Sedikit ada godaan main game saat hari libur, namun mau diingatkan dengan baik.",
                    parentSignature = "Bpk. Hendra",
                    isReviewedByTeacher = true,
                    teacherName = "Bpk. Rahmad, S.Pd.",
                    teacherFeedback = "Apresiasi untuk kerja sama orang tua. Pengendalian diri saat diingatkan adalah bukti kedewasaan karakter.",
                    characterBadge = "Pejuang Pengendalian Diri",
                    pointsAwarded = 10,
                    reviewDate = "Senin, 5 Okt 2026"
                )
            )

            // Seed an evaluation waiting for teacher feedback (submitted on Saturday)
            dao.insertEvaluation(
                WeeklyEvaluation(
                    studentId = student1Id,
                    weekTitle = "Pekan 3 (Sabtu, 10 Okt 2026)",
                    submissionDate = "Sabtu, 10 Okt 2026",
                    isSaturdaySubmission = true,
                    prayerDisciplineScore = 5,
                    studyAndHomeworkScore = 5,
                    screenTimeLimitScore = 4,
                    houseChoreHelpScore = 4,
                    courtesyAndMannerScore = 5,
                    goodHabitsSummary = "Bangun subuh sendiri, merapikan tas sekolah, hafalan doa tuntas.",
                    parentNotes = "Alhamdulillah semakin mandiri bangun pagi dan tidak mengeluh saat belajar.",
                    parentSignature = "Ibu Nur",
                    isReviewedByTeacher = false
                )
            )

            // Seed evaluations for Siti Nur Aisyah
            dao.insertEvaluation(
                WeeklyEvaluation(
                    studentId = student2Id,
                    weekTitle = "Pekan 2 (Sabtu, 3 Okt 2026)",
                    submissionDate = "Sabtu, 3 Okt 2026",
                    isSaturdaySubmission = true,
                    prayerDisciplineScore = 5,
                    studyAndHomeworkScore = 5,
                    screenTimeLimitScore = 5,
                    houseChoreHelpScore = 5,
                    courtesyAndMannerScore = 5,
                    goodHabitsSummary = "Sholat tepat waktu, membaca buku cerita, membantu menjaga adik.",
                    parentNotes = "Sangat tertib mematuhi jadwal harian rumah.",
                    parentSignature = "Ibu Rahmawati",
                    isReviewedByTeacher = true,
                    teacherName = "Bpk. Rahmad, S.Pd.",
                    teacherFeedback = "Sempurna! Siti selalu menjadi contoh dalam ketertiban dan ketulusan berbagi.",
                    characterBadge = "Teladan Karakter Utama",
                    pointsAwarded = 20,
                    reviewDate = "Senin, 5 Okt 2026"
                )
            )

            // Seed discipline records
            dao.insertDisciplineRecord(
                DisciplineRecord(
                    studentId = student1Id,
                    title = "Modal Poin Kedisiplinan Awal Semester",
                    category = "Sekolah",
                    pointDelta = 100,
                    date = "Senin, 1 Sep 2026",
                    giverName = "Sekolah (Sistem LENTERA)",
                    description = "Poin awal penghargaan komitmen tata tertib semester ganjil."
                )
            )
            dao.insertDisciplineRecord(
                DisciplineRecord(
                    studentId = student1Id,
                    title = "Apresiasi Evaluasi Sabtu Pekan 1",
                    category = "Rumah & Sekolah",
                    pointDelta = 15,
                    date = "Senin, 28 Sep 2026",
                    giverName = "Bpk. Rahmad, S.Pd.",
                    description = "Konsistensi ibadah dan kepatuhan aturan rumah."
                )
            )
            dao.insertDisciplineRecord(
                DisciplineRecord(
                    studentId = student1Id,
                    title = "Apresiasi Evaluasi Sabtu Pekan 2",
                    category = "Rumah & Sekolah",
                    pointDelta = 10,
                    date = "Senin, 5 Okt 2026",
                    giverName = "Bpk. Rahmad, S.Pd.",
                    description = "Kemandirian mencuci piring dan pengendalian screen time."
                )
            )
        }
    }
}
