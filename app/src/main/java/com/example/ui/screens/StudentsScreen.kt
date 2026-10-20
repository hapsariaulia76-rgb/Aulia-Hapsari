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
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DisciplineRecord
import com.example.data.model.Student
import com.example.data.model.UserRole
import com.example.ui.components.PointsBadgePill
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberPrimary
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.OnAmberContainer
import com.example.ui.theme.OnEmeraldContainer
import com.example.ui.theme.SlateNavy

@Composable
fun StudentsScreen(
    currentRole: UserRole,
    students: List<Student>,
    selectedStudent: Student?,
    disciplineRecords: List<DisciplineRecord>,
    onSelectStudent: (Long) -> Unit,
    onOpenAddStudent: () -> Unit,
    onAddDisciplinePoint: (student: Student, title: String, category: String, delta: Int, note: String, giverName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAwardDialog by remember { mutableStateOf(false) }

    val currentRecords = disciplineRecords.filter { record ->
        selectedStudent == null || record.studentId == selectedStudent.id
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Perkembangan Karakter Siswa",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Pemantauan poin kedisiplinan dan apresiasi konsistensi karakter.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Action button to add student
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Daftar Siswa Kelas 4-A (${students.size})",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = onOpenAddStudent,
                    colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("add_student_button")
                ) {
                    Icon(imageVector = Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Tambah Siswa", fontSize = 12.sp, color = Color.White)
                }
            }
        }

        // Leaderboard / Student Cards List
        items(students.sortedByDescending { it.disciplinePoints }, key = { it.id }) { student ->
            val isSelected = selectedStudent?.id == student.id
            StudentCard(
                student = student,
                isSelected = isSelected,
                onClick = { onSelectStudent(student.id) }
            )
        }

        // Selected Student Details & Log
        if (selectedStudent != null) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Riwayat Poin Kedisiplinan",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Siswa: ${selectedStudent.name}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            if (currentRole == UserRole.TEACHER) {
                                Button(
                                    onClick = { showAwardDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = SlateNavy),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.testTag("award_points_button")
                                ) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Beri Poin", fontSize = 11.sp, color = Color.White)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (currentRecords.isEmpty()) {
                            Text(
                                text = "Belum ada catatan poin untuk siswa ini.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            currentRecords.forEach { record ->
                                DisciplineRecordRow(record = record)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showAwardDialog && selectedStudent != null) {
        AwardPointsDialog(
            student = selectedStudent,
            onDismiss = { showAwardDialog = false },
            onConfirm = { title, cat, delta, note, giver ->
                onAddDisciplinePoint(selectedStudent, title, cat, delta, note, giver)
                showAwardDialog = false
            }
        )
    }
}

@Composable
fun StudentCard(
    student: Student,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFFFBEB) else MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, AmberPrimary) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("student_card_${student.id}")
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFEF3C7)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = student.name.take(1),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AmberPrimary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${student.className} • Wali: ${student.parentName}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = student.characterLevel,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AmberPrimary
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = EmeraldContainer
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${student.disciplinePoints}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnEmeraldContainer
                    )
                    Text(
                        text = "Poin",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnEmeraldContainer
                    )
                }
            }
        }
    }
}

@Composable
fun DisciplineRecordRow(record: DisciplineRecord) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFF8FAFC),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SlateNavy
                )
                Text(
                    text = "${record.category} • ${record.date} • ${record.giverName}",
                    fontSize = 10.sp,
                    color = Color(0xFF64748B)
                )
                if (record.description.isNotBlank()) {
                    Text(
                        text = record.description,
                        fontSize = 11.sp,
                        color = Color(0xFF334155)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            PointsBadgePill(points = record.pointDelta)
        }
    }
}

@Composable
fun AwardPointsDialog(
    student: Student,
    onDismiss: () -> Unit,
    onConfirm: (title: String, category: String, delta: Int, note: String, giver: String) -> Unit
) {
    var title by remember { mutableStateOf("Apresiasi Sikap Teladan di Sekolah") }
    var category by remember { mutableStateOf("Sekolah") }
    var delta by remember { mutableIntStateOf(10) }
    var note by remember { mutableStateOf("Sikap tertib dan membantu teman piket kelas.") }
    var giver by remember { mutableStateOf("Bpk. Rahmad, S.Pd.") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Beri Poin Kedisiplinan",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Siswa: ${student.name} (${student.className})",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Judul Apresiasi / Catatan") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val pts = listOf(5, 10, 15, -5)
                    pts.forEach { p ->
                        val isSel = delta == p
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) EmeraldSuccess else Color(0xFFE2E8F0))
                                .clickable { delta = p }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (p > 0) "+$p" else "$p",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSel) Color.White else Color(0xFF334155)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Keterangan Tambahan") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = giver,
                    onValueChange = { giver = it },
                    label = { Text("Nama Guru Penilai") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(title, category, delta, note, giver) },
                colors = ButtonDefaults.buttonColors(containerColor = SlateNavy)
            ) {
                Text("Simpan Poin", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}
