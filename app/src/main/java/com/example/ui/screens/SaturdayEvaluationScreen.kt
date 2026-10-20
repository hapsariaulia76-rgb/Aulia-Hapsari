package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Student
import com.example.data.model.UserRole
import com.example.data.model.WeeklyEvaluation
import com.example.ui.components.CharacterBadgePill
import com.example.ui.components.PointsBadgePill
import com.example.ui.components.SaturdayReminderCard
import com.example.ui.components.StarRatingDisplay
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberPrimary
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.OnAmberContainer
import com.example.ui.theme.OnEmeraldContainer
import com.example.ui.theme.SlateNavy

@Composable
fun SaturdayEvaluationScreen(
    currentRole: UserRole,
    students: List<Student>,
    selectedStudent: Student?,
    evaluations: List<WeeklyEvaluation>,
    filterStatus: String,
    onFilterChange: (String) -> Unit,
    onOpenAddEvaluation: () -> Unit,
    onOpenTeacherFeedback: (WeeklyEvaluation, Student) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredEvaluations = evaluations.filter { eval ->
        val matchesStudent = if (currentRole == UserRole.PARENT && selectedStudent != null) {
            eval.studentId == selectedStudent.id
        } else true

        val matchesFilter = when (filterStatus) {
            "Menunggu Review" -> !eval.isReviewedByTeacher
            "Sudah Dinilai" -> eval.isReviewedByTeacher
            else -> true
        }
        matchesStudent && matchesFilter
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                // Title and role context
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Evaluasi Aktivitas Rumah",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Diisi setiap hari Sabtu oleh wali murid & dinilai oleh guru.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Saturday schedule reminder
            item {
                SaturdayReminderCard()
            }

            // Filter Chips
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filters = listOf("Semua", "Menunggu Review", "Sudah Dinilai")
                    filters.forEach { filter ->
                        val isSelected = filterStatus == filter
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) AmberPrimary else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { onFilterChange(filter) }
                        ) {
                            Text(
                                text = filter,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            if (filteredEvaluations.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Belum Ada Laporan pada Filter Ini",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Wali murid dapat mengisi lembar evaluasi aktivitas rumah setiap hari Sabtu.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(filteredEvaluations, key = { it.id }) { eval ->
                    val student = students.firstOrNull { it.id == eval.studentId }
                    WeeklyEvaluationItemCard(
                        evaluation = eval,
                        student = student,
                        currentRole = currentRole,
                        onTeacherFeedbackClick = {
                            if (student != null) {
                                onOpenTeacherFeedback(eval, student)
                            }
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(72.dp))
            }
        }

        // Floating action button for Parent role
        if (currentRole == UserRole.PARENT && selectedStudent != null) {
            FloatingActionButton(
                onClick = onOpenAddEvaluation,
                containerColor = AmberPrimary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
                    .testTag("fab_add_saturday_evaluation")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Isi Evaluasi")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Isi Evaluasi Sabtu",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun WeeklyEvaluationItemCard(
    evaluation: WeeklyEvaluation,
    student: Student?,
    currentRole: UserRole,
    onTeacherFeedbackClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("evaluation_item_${evaluation.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Student Name + Date + Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = evaluation.weekTitle,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Siswa: ${student?.name ?: "Siswa"} (${student?.className ?: "-"}) • ${evaluation.submissionDate}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Status chip
                if (evaluation.isReviewedByTeacher) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = EmeraldContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = OnEmeraldContainer,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Dinilai Guru",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnEmeraldContainer
                            )
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFEF3C7)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.HourglassEmpty,
                                contentDescription = null,
                                tint = Color(0xFF92400E),
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Menunggu Guru",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Score breakdown 5 pilar
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFF8FAFC),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    EvaluationScoreRow("Ibadah & Doa Tepat Waktu", evaluation.prayerDisciplineScore)
                    EvaluationScoreRow("Jam Belajar & Mengerjakan PR", evaluation.studyAndHomeworkScore)
                    EvaluationScoreRow("Disiplin Batas Screen Time/HP", evaluation.screenTimeLimitScore)
                    EvaluationScoreRow("Mandiri Membantu Tugas Rumah", evaluation.houseChoreHelpScore)
                    EvaluationScoreRow("Sopan Santun & Adab Keluarga", evaluation.courtesyAndMannerScore)
                }
            }

            // Parent's note
            if (evaluation.parentNotes.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Catatan Orang Tua: \"${evaluation.parentNotes}\"",
                    fontSize = 12.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = Color(0xFF475569)
                )
                if (evaluation.parentSignature.isNotBlank()) {
                    Text(
                        text = "— Diisi oleh: ${evaluation.parentSignature}",
                        fontSize = 10.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }

            // Teacher Review Section
            Spacer(modifier = Modifier.height(12.dp))
            if (evaluation.isReviewedByTeacher) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Feedback & Penilaian Guru:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF166534)
                            )
                            if (evaluation.pointsAwarded > 0) {
                                PointsBadgePill(points = evaluation.pointsAwarded)
                            }
                        }

                        if (evaluation.characterBadge.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            CharacterBadgePill(badgeName = evaluation.characterBadge)
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "\"${evaluation.teacherFeedback}\"",
                            fontSize = 12.sp,
                            color = Color(0xFF14532D),
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Penilai: ${evaluation.teacherName} • ${evaluation.reviewDate}",
                            fontSize = 10.sp,
                            color = Color(0xFF15803D)
                        )
                    }
                }
            }

            // Action for Teacher: Give / Edit Feedback
            if (currentRole == UserRole.TEACHER) {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onTeacherFeedbackClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (evaluation.isReviewedByTeacher) SlateNavy else EmeraldSuccess
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("action_teacher_feedback_${evaluation.id}")
                ) {
                    Icon(
                        imageVector = if (evaluation.isReviewedByTeacher) Icons.Default.Edit else Icons.Default.RateReview,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (evaluation.isReviewedByTeacher) "Ubah Feedback & Poin Guru" else "Beri Feedback & Poin Kedisiplinan",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun EvaluationScoreRow(label: String, score: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color(0xFF334155),
            modifier = Modifier.weight(1f)
        )
        StarRatingDisplay(score = score)
    }
}
