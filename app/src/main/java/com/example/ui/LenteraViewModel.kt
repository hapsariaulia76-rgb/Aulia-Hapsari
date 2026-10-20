package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.LenteraDatabase
import com.example.data.model.DisciplineRecord
import com.example.data.model.RuleCategory
import com.example.data.model.Student
import com.example.data.model.UserRole
import com.example.data.model.WeeklyEvaluation
import com.example.data.repository.LenteraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LenteraViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LenteraRepository

    private val _currentRole = MutableStateFlow(UserRole.PARENT)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    private val _selectedStudentId = MutableStateFlow<Long?>(null)
    val selectedStudentId: StateFlow<Long?> = _selectedStudentId.asStateFlow()

    private val _ruleCategory = MutableStateFlow(RuleCategory.HOME)
    val ruleCategory: StateFlow<RuleCategory> = _ruleCategory.asStateFlow()

    private val _evaluationFilter = MutableStateFlow("Semua")
    val evaluationFilter: StateFlow<String> = _evaluationFilter.asStateFlow()

    val students: StateFlow<List<Student>>
    val allEvaluations: StateFlow<List<WeeklyEvaluation>>
    val allDisciplineRecords: StateFlow<List<DisciplineRecord>>

    init {
        val database = LenteraDatabase.getDatabase(application)
        repository = LenteraRepository(database.lenteraDao())

        students = repository.allStudents.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        allEvaluations = repository.allEvaluations.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        allDisciplineRecords = repository.allDisciplineRecords.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        viewModelScope.launch {
            repository.checkAndSeedInitialData()
        }
    }

    fun setRole(role: UserRole) {
        _currentRole.value = role
    }

    fun selectStudent(id: Long) {
        _selectedStudentId.value = id
    }

    fun setRuleCategory(category: RuleCategory) {
        _ruleCategory.value = category
    }

    fun setEvaluationFilter(filter: String) {
        _evaluationFilter.value = filter
    }

    fun submitSaturdayEvaluation(
        studentId: Long,
        weekTitle: String,
        submissionDate: String,
        prayerScore: Int,
        studyScore: Int,
        gadgetScore: Int,
        choreScore: Int,
        mannerScore: Int,
        goodHabitsSummary: String,
        parentNotes: String,
        parentSignature: String
    ) {
        viewModelScope.launch {
            val eval = WeeklyEvaluation(
                studentId = studentId,
                weekTitle = weekTitle,
                submissionDate = submissionDate,
                isSaturdaySubmission = true,
                prayerDisciplineScore = prayerScore,
                studyAndHomeworkScore = studyScore,
                screenTimeLimitScore = gadgetScore,
                houseChoreHelpScore = choreScore,
                courtesyAndMannerScore = mannerScore,
                goodHabitsSummary = goodHabitsSummary,
                parentNotes = parentNotes,
                parentSignature = parentSignature,
                isReviewedByTeacher = false
            )
            repository.submitSaturdayEvaluation(eval)
        }
    }

    fun submitTeacherFeedback(
        evaluation: WeeklyEvaluation,
        teacherName: String,
        feedback: String,
        badge: String,
        pointsAwarded: Int,
        student: Student
    ) {
        viewModelScope.launch {
            val dateStr = SimpleDateFormat("EEEE, d MMM yyyy", Locale("id", "ID")).format(Date())
            repository.submitTeacherFeedback(
                evaluation = evaluation,
                teacherName = teacherName.ifBlank { "Bpk. Rahmad, S.Pd. (Wali Kelas)" },
                feedback = feedback,
                badge = badge,
                awardedPoints = pointsAwarded,
                reviewDate = dateStr,
                currentStudent = student
            )
        }
    }

    fun addNewStudent(
        name: String,
        nis: String,
        className: String,
        parentName: String
    ) {
        viewModelScope.launch {
            val colors = listOf("#D97706", "#2563EB", "#059669", "#7C3AED", "#DB2777", "#0891B2")
            val chosenColor = colors.random()
            val newStudent = Student(
                name = name,
                nis = nis,
                className = className,
                parentName = parentName,
                avatarColorHex = chosenColor,
                disciplinePoints = 100,
                characterLevel = "Pejuang Karakter Baik"
            )
            val id = repository.addStudent(newStudent)
            if (_selectedStudentId.value == null) {
                _selectedStudentId.value = id
            }
        }
    }

    fun addDisciplinePoints(
        student: Student,
        title: String,
        category: String,
        delta: Int,
        note: String,
        giverName: String
    ) {
        viewModelScope.launch {
            val dateStr = SimpleDateFormat("d MMM yyyy", Locale("id", "ID")).format(Date())
            repository.addManualDisciplinePoint(
                student = student,
                title = title,
                category = category,
                delta = delta,
                giverName = giverName,
                date = dateStr,
                note = note
            )
        }
    }
}
