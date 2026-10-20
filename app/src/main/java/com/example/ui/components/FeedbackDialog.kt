package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.RateReview
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
import com.example.data.model.Student
import com.example.data.model.WeeklyEvaluation
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.OnEmeraldContainer
import com.example.ui.theme.SlateNavy

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeedbackDialog(
    evaluation: WeeklyEvaluation,
    student: Student,
    onDismiss: () -> Unit,
    onSubmitFeedback: (feedback: String, badge: String, points: Int, teacherName: String) -> Unit
) {
    var teacherName by remember { mutableStateOf("Bpk. Rahmad, S.Pd.") }
    var feedbackText by remember {
        mutableStateOf(
            if (evaluation.teacherFeedback.isNotBlank()) evaluation.teacherFeedback
            else "Terima kasih kepada orang tua atas pantauan konsisten di rumah. Pertahankan kebiasaan baik ini di sekolah maupun di rumah!"
        )
    }

    val badges = listOf(
        "Bintang Kejujuran",
        "Pejuang Disiplin Emas",
        "Mandiri Hebat",
        "Anak Berbakti",
        "Teladan Karakter Utama",
        "Sahabat Peduli"
    )
    var selectedBadge by remember {
        mutableStateOf(if (evaluation.characterBadge.isNotBlank()) evaluation.characterBadge else badges[1])
    }

    val pointOptions = listOf(5, 10, 15, 20)
    var selectedPoints by remember {
        mutableIntStateOf(if (evaluation.pointsAwarded > 0) evaluation.pointsAwarded else 15)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.RateReview,
                    contentDescription = null,
                    tint = SlateNavy,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Feedback & Poin Guru",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${student.name} - ${evaluation.weekTitle}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            ) {
                // Summary of parent's report
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Ringkasan Laporan Orang Tua:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateNavy
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Rata-rata Skor: ${String.format("%.1f", evaluation.averageScore)} / 5.0",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFB45309)
                        )
                        if (evaluation.parentNotes.isNotBlank()) {
                            Text(
                                text = "Catatan: \"${evaluation.parentNotes}\"",
                                fontSize = 11.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                color = Color(0xFF334155)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Apresiasi Lencana Karakter:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    badges.forEach { badge ->
                        val isSelected = selectedBadge == badge
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) Color(0xFFFEF3C7) else Color(0xFFF1F5F9),
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { selectedBadge = badge }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.EmojiEvents,
                                        contentDescription = null,
                                        tint = Color(0xFFB45309),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                Text(
                                    text = badge,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color(0xFF92400E) else Color(0xFF475569)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Tambahkan Poin Kedisiplinan:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    pointOptions.forEach { pts ->
                        val isSelected = selectedPoints == pts
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) EmeraldSuccess else Color(0xFFE2E8F0))
                                .clickable { selectedPoints = pts }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+$pts Poin",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Color(0xFF334155)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                OutlinedTextField(
                    value = feedbackText,
                    onValueChange = { feedbackText = it },
                    label = { Text("Catatan Feedback & Arahan Guru") },
                    placeholder = { Text("Tuliskan evaluasi karakter, saran perbaikan, atau kata apresiasi...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("teacher_feedback_input"),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = teacherName,
                    onValueChange = { teacherName = it },
                    label = { Text("Nama Guru / Wali Kelas") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("teacher_name_input"),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmitFeedback(feedbackText, selectedBadge, selectedPoints, teacherName)
                },
                colors = ButtonDefaults.buttonColors(containerColor = SlateNavy),
                modifier = Modifier.testTag("submit_feedback_button")
            ) {
                Text("Simpan Feedback & Poin", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Tutup")
            }
        }
    )
}
