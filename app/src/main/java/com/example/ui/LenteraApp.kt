package com.example.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.model.Student
import com.example.data.model.UserRole
import com.example.data.model.WeeklyEvaluation
import com.example.ui.components.AddEvaluationDialog
import com.example.ui.components.AddStudentDialog
import com.example.ui.components.FeedbackDialog
import com.example.ui.components.RoleSwitcherChip
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.RulesScreen
import com.example.ui.screens.SaturdayEvaluationScreen
import com.example.ui.screens.StudentsScreen
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberPrimary
import com.example.ui.theme.OnAmberContainer
import com.example.ui.theme.SlateNavy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LenteraApp(
    viewModel: LenteraViewModel,
    modifier: Modifier = Modifier
) {
    val currentRole by viewModel.currentRole.collectAsStateWithLifecycle()
    val selectedStudentId by viewModel.selectedStudentId.collectAsStateWithLifecycle()
    val students by viewModel.students.collectAsStateWithLifecycle()
    val allEvaluations by viewModel.allEvaluations.collectAsStateWithLifecycle()
    val allDisciplineRecords by viewModel.allDisciplineRecords.collectAsStateWithLifecycle()
    val ruleCategory by viewModel.ruleCategory.collectAsStateWithLifecycle()
    val evaluationFilter by viewModel.evaluationFilter.collectAsStateWithLifecycle()

    var currentTab by remember { mutableIntStateOf(0) }
    var showAddEvaluationDialog by remember { mutableStateOf(false) }
    var showAddStudentDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }
    var evaluationForTeacherFeedback by remember { mutableStateOf<Pair<WeeklyEvaluation, Student>?>(null) }

    // If no student is selected yet, pick the first one by default
    val selectedStudent = students.firstOrNull { it.id == selectedStudentId }
        ?: students.firstOrNull()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(AmberContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Logo Lentera",
                                tint = OnAmberContainer,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "LENTERA",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = SlateNavy,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Buku Karakter Rumah & Sekolah",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                actions = {
                    RoleSwitcherChip(
                        currentRole = currentRole,
                        onRoleChanged = { viewModel.setRole(it) }
                    )
                    IconButton(
                        onClick = { showInfoDialog = true },
                        modifier = Modifier.testTag("info_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Panduan Lentera",
                            tint = SlateNavy
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = currentTab == 0,
                    onClick = { currentTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Beranda") },
                    label = { Text("Beranda", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmberPrimary,
                        selectedTextColor = AmberPrimary,
                        indicatorColor = AmberContainer
                    ),
                    modifier = Modifier.testTag("nav_home")
                )
                NavigationBarItem(
                    selected = currentTab == 1,
                    onClick = { currentTab = 1 },
                    icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "Evaluasi Sabtu") },
                    label = { Text("Sabtu", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmberPrimary,
                        selectedTextColor = AmberPrimary,
                        indicatorColor = AmberContainer
                    ),
                    modifier = Modifier.testTag("nav_saturday")
                )
                NavigationBarItem(
                    selected = currentTab == 2,
                    onClick = { currentTab = 2 },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = "Aturan") },
                    label = { Text("Aturan", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmberPrimary,
                        selectedTextColor = AmberPrimary,
                        indicatorColor = AmberContainer
                    ),
                    modifier = Modifier.testTag("nav_rules")
                )
                NavigationBarItem(
                    selected = currentTab == 3,
                    onClick = { currentTab = 3 },
                    icon = { Icon(Icons.Default.EmojiEvents, contentDescription = "Siswa") },
                    label = { Text("Siswa", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmberPrimary,
                        selectedTextColor = AmberPrimary,
                        indicatorColor = AmberContainer
                    ),
                    modifier = Modifier.testTag("nav_students")
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                0 -> HomeScreen(
                    currentRole = currentRole,
                    students = students,
                    selectedStudent = selectedStudent,
                    evaluations = allEvaluations,
                    onSelectStudent = { viewModel.selectStudent(it) },
                    onOpenAddEvaluation = { showAddEvaluationDialog = true },
                    onOpenSaturdayTab = { currentTab = 1 },
                    onOpenRulesTab = { currentTab = 2 }
                )
                1 -> SaturdayEvaluationScreen(
                    currentRole = currentRole,
                    students = students,
                    selectedStudent = selectedStudent,
                    evaluations = allEvaluations,
                    filterStatus = evaluationFilter,
                    onFilterChange = { viewModel.setEvaluationFilter(it) },
                    onOpenAddEvaluation = { showAddEvaluationDialog = true },
                    onOpenTeacherFeedback = { eval, student ->
                        evaluationForTeacherFeedback = Pair(eval, student)
                    }
                )
                2 -> RulesScreen(
                    selectedCategory = ruleCategory,
                    onCategoryChanged = { viewModel.setRuleCategory(it) }
                )
                3 -> StudentsScreen(
                    currentRole = currentRole,
                    students = students,
                    selectedStudent = selectedStudent,
                    disciplineRecords = allDisciplineRecords,
                    onSelectStudent = { viewModel.selectStudent(it) },
                    onOpenAddStudent = { showAddStudentDialog = true },
                    onAddDisciplinePoint = { student, title, cat, delta, note, giver ->
                        viewModel.addDisciplinePoints(student, title, cat, delta, note, giver)
                    }
                )
            }
        }
    }

    // Dialog: Add Saturday Evaluation (Wali Murid)
    if (showAddEvaluationDialog && selectedStudent != null) {
        AddEvaluationDialog(
            student = selectedStudent,
            onDismiss = { showAddEvaluationDialog = false },
            onSubmit = { title, date, prayer, study, gadget, chore, manner, summary, notes, signature ->
                viewModel.submitSaturdayEvaluation(
                    studentId = selectedStudent.id,
                    weekTitle = title,
                    submissionDate = date,
                    prayerScore = prayer,
                    studyScore = study,
                    gadgetScore = gadget,
                    choreScore = chore,
                    mannerScore = manner,
                    goodHabitsSummary = summary,
                    parentNotes = notes,
                    parentSignature = signature
                )
                showAddEvaluationDialog = false
                currentTab = 1 // Go to Saturday tab to see the submitted report!
            }
        )
    }

    // Dialog: Teacher Feedback & Discipline Points
    evaluationForTeacherFeedback?.let { (eval, student) ->
        FeedbackDialog(
            evaluation = eval,
            student = student,
            onDismiss = { evaluationForTeacherFeedback = null },
            onSubmitFeedback = { feedback, badge, points, teacherName ->
                viewModel.submitTeacherFeedback(
                    evaluation = eval,
                    teacherName = teacherName,
                    feedback = feedback,
                    badge = badge,
                    pointsAwarded = points,
                    student = student
                )
                evaluationForTeacherFeedback = null
            }
        )
    }

    // Dialog: Add Student
    if (showAddStudentDialog) {
        AddStudentDialog(
            onDismiss = { showAddStudentDialog = false },
            onAddStudent = { name, nis, className, parentName ->
                viewModel.addNewStudent(name, nis, className, parentName)
                showAddStudentDialog = false
            }
        )
    }

    // Dialog: Info App Guide
    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = {
                Text(
                    text = "Tentang Aplikasi LENTERA",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "LENTERA adalah buku penghubung digital untuk menyinergikan pendidikan karakter di rumah dan di sekolah.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "1. Aturan di Rumah & Sekolah:\nPanduan tata tertib, adab, batas gawai, dan tanggung jawab belajar.",
                        fontSize = 12.sp,
                        color = SlateNavy
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "2. Evaluasi Rutin Hari Sabtu:\nWali murid mengisi dan memantau 5 pilar aktivitas anak setiap hari Sabtu.",
                        fontSize = 12.sp,
                        color = Color(0xFFB45309),
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "3. Feedback & Poin Kedisiplinan Guru:\nGuru memeriksa laporan mingguan, memberikan arahan/apresiasi, serta menganugerahkan lencana dan poin karakter secara konsisten.",
                        fontSize = 12.sp,
                        color = Color(0xFF047857)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text("Tutup", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}
