package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Student
import com.example.ui.theme.AmberPrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddEvaluationDialog(
    student: Student,
    onDismiss: () -> Unit,
    onSubmit: (
        weekTitle: String,
        date: String,
        prayer: Int,
        study: Int,
        gadget: Int,
        chore: Int,
        manner: Int,
        summary: String,
        notes: String,
        signature: String
    ) -> Unit
) {
    val currentDateStr = remember {
        val sdf = SimpleDateFormat("EEEE, d MMM yyyy", Locale("id", "ID"))
        sdf.format(Date())
    }

    var weekTitle by remember { mutableStateOf("Evaluasi Pekanan (Sabtu)") }
    var prayerScore by remember { mutableIntStateOf(5) }
    var studyScore by remember { mutableIntStateOf(4) }
    var gadgetScore by remember { mutableIntStateOf(4) }
    var choreScore by remember { mutableIntStateOf(4) }
    var mannerScore by remember { mutableIntStateOf(5) }
    var notes by remember { mutableStateOf("") }
    var parentSignature by remember { mutableStateOf(student.parentName) }

    // Habit checklist
    val habitOptions = remember {
        listOf(
            "Sholat / ibadah 5 waktu tertib",
            "Merapikan tempat tidur & kamar sendiri",
            "Mengerjakan PR tuntas sebelum jam 20.30",
            "Screen time HP / game tidak lebih dari 1 jam",
            "Mencuci piring sendiri setelah makan",
            "Mencium tangan orang tua saat pamit / pulang"
        )
    }
    val selectedHabits = remember { mutableStateListOf(*habitOptions.take(4).toTypedArray()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = AmberPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Evaluasi Sabtu: Aktivitas di Rumah",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Siswa: ${student.name} (${student.className})",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFFFBEB),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "📌 Sesuai panduan LENTERA, evaluasi diisi oleh orang tua/wali murid setiap hari Sabtu untuk memantau konsistensi karakter di rumah.",
                        fontSize = 12.sp,
                        color = Color(0xFF92400E),
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = weekTitle,
                    onValueChange = { weekTitle = it },
                    label = { Text("Judul Evaluasi Pekan") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("evaluation_title_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tanggal: $currentDateStr",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Penilaian 5 Pilar Aktivitas di Rumah",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                ScoreRatingRow(
                    label = "1. Ibadah & Doa Tepat Waktu",
                    score = prayerScore,
                    onScoreChange = { prayerScore = it }
                )

                ScoreRatingRow(
                    label = "2. Belajar Mandiri & Tugas/PR",
                    score = studyScore,
                    onScoreChange = { studyScore = it }
                )

                ScoreRatingRow(
                    label = "3. Disiplin Batas Screen Time / HP",
                    score = gadgetScore,
                    onScoreChange = { gadgetScore = it }
                )

                ScoreRatingRow(
                    label = "4. Mandiri & Membantu Tugas Rumah",
                    score = choreScore,
                    onScoreChange = { choreScore = it }
                )

                ScoreRatingRow(
                    label = "5. Sopan Santun & Adab Berbicara",
                    score = mannerScore,
                    onScoreChange = { mannerScore = it }
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Kebiasaan Baik yang Terlaksana Sepekan:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )

                habitOptions.forEach { habit ->
                    val isChecked = selectedHabits.contains(habit)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (isChecked) selectedHabits.remove(habit)
                                else selectedHabits.add(habit)
                            }
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                if (checked) selectedHabits.add(habit)
                                else selectedHabits.remove(habit)
                            },
                            colors = CheckboxDefaults.colors(checkedColor = AmberPrimary)
                        )
                        Text(
                            text = habit,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Catatan Perkembangan Anak dari Orang Tua") },
                    placeholder = { Text("Contoh: Pekan ini rajin bangun pagi dan membantu cuci piring...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("evaluation_notes_input"),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = parentSignature,
                    onValueChange = { parentSignature = it },
                    label = { Text("Nama Orang Tua / Wali yang Mengisi") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("evaluation_signature_input"),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val summary = selectedHabits.joinToString(", ")
                    onSubmit(
                        weekTitle,
                        currentDateStr,
                        prayerScore,
                        studyScore,
                        gadgetScore,
                        choreScore,
                        mannerScore,
                        summary,
                        notes,
                        parentSignature
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary),
                modifier = Modifier.testTag("submit_evaluation_button")
            ) {
                Text("Kirim Laporan Sabtu", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}

@Composable
private fun ScoreRatingRow(
    label: String,
    score: Int,
    onScoreChange: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Row {
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= score) Icons.Default.Star else Icons.Outlined.StarOutline,
                        contentDescription = "Bintang $i",
                        tint = if (i <= score) Color(0xFFF59E0B) else Color(0xFFCBD5E1),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onScoreChange(i) }
                            .padding(2.dp)
                    )
                }
            }
        }
    }
}
