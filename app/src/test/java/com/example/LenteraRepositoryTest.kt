package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.db.LenteraDao
import com.example.data.db.LenteraDatabase
import com.example.data.model.Student
import com.example.data.model.WeeklyEvaluation
import com.example.data.repository.LenteraRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class LenteraRepositoryTest {

    private lateinit var database: LenteraDatabase
    private lateinit var dao: LenteraDao
    private lateinit var repository: LenteraRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, LenteraDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.lenteraDao()
        repository = LenteraRepository(dao)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testSeedInitialDataCreatesStudentsAndEvaluations() = runTest {
        repository.checkAndSeedInitialData()

        val students = repository.allStudents.first()
        assertTrue(students.isNotEmpty())
        val student = students.first()
        assertNotNull(student)

        val evaluations = repository.allEvaluations.first()
        assertTrue(evaluations.isNotEmpty())
    }

    @Test
    fun testSubmitTeacherFeedbackIncreasesStudentPoints() = runTest {
        val student = Student(
            name = "Test Siswa",
            nis = "123",
            className = "4-A",
            parentName = "Ortu Test",
            disciplinePoints = 100
        )
        val studentId = repository.addStudent(student)
        val savedStudent = student.copy(id = studentId)

        val evaluation = WeeklyEvaluation(
            studentId = studentId,
            weekTitle = "Pekan 1 (Sabtu)",
            submissionDate = "Sabtu, 10 Okt 2026",
            isSaturdaySubmission = true
        )
        val evalId = repository.submitSaturdayEvaluation(evaluation)
        val savedEvaluation = evaluation.copy(id = evalId)

        // Teacher awards feedback and 15 points
        repository.submitTeacherFeedback(
            evaluation = savedEvaluation,
            teacherName = "Pak Guru",
            feedback = "Sangat rajin dan tertib!",
            badge = "Bintang Disiplin",
            awardedPoints = 15,
            reviewDate = "Senin, 12 Okt 2026",
            currentStudent = savedStudent
        )

        val updatedStudents = repository.allStudents.first()
        val updatedStudent = updatedStudents.first { it.id == studentId }
        assertEquals(115, updatedStudent.disciplinePoints)
    }
}
