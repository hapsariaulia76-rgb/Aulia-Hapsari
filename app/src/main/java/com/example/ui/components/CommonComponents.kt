package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.data.model.UserRole
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberPrimary
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.OnAmberContainer
import com.example.ui.theme.OnEmeraldContainer
import com.example.ui.theme.SlateNavy

@Composable
fun RoleSwitcherChip(
    currentRole: UserRole,
    onRoleChanged: (UserRole) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.testTag("role_switcher_container")
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isParent = currentRole == UserRole.PARENT
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isParent) AmberPrimary else Color.Transparent)
                    .clickable { onRoleChanged(UserRole.PARENT) }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .testTag("role_parent_button"),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Wali Murid",
                        tint = if (isParent) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Wali Murid",
                        fontSize = 12.sp,
                        fontWeight = if (isParent) FontWeight.Bold else FontWeight.Medium,
                        color = if (isParent) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            val isTeacher = currentRole == UserRole.TEACHER
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isTeacher) SlateNavy else Color.Transparent)
                    .clickable { onRoleChanged(UserRole.TEACHER) }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .testTag("role_teacher_button"),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "Guru",
                        tint = if (isTeacher) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Guru",
                        fontSize = 12.sp,
                        fontWeight = if (isTeacher) FontWeight.Bold else FontWeight.Medium,
                        color = if (isTeacher) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun SaturdayReminderCard(
    modifier: Modifier = Modifier,
    onActionClick: (() -> Unit)? = null,
    actionButtonText: String = "Isi Evaluasi Sekarang"
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF7ED) // Warm orange/amber tint
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("saturday_reminder_card")
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF97316)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Jadwal Evaluasi Sabtu",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "JADWAL RUTIN SABTU",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC2410C)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFFFEDD5))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Mingguan",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF9A3412)
                        )
                    }
                }
                Text(
                    text = "Evaluasi Aktivitas Rumah",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = "Wali murid mengisi kepatuhan ibadah, belajar, gawai & adab setiap hari Sabtu untuk dipantau guru.",
                    fontSize = 12.sp,
                    color = Color(0xFF475569),
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun StarRatingDisplay(
    score: Int,
    maxScore: Int = 5,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        for (i in 1..maxScore) {
            Icon(
                imageVector = if (i <= score) Icons.Default.Star else Icons.Outlined.StarOutline,
                contentDescription = "Nilai $i",
                tint = if (i <= score) Color(0xFFF59E0B) else Color(0xFFCBD5E1),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun CharacterBadgePill(
    badgeName: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = AmberContainer,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = OnAmberContainer,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = badgeName,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = OnAmberContainer
            )
        }
    }
}

@Composable
fun PointsBadgePill(
    points: Int,
    modifier: Modifier = Modifier
) {
    val isPositive = points >= 0
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isPositive) EmeraldContainer else Color(0xFFFEE2E2),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = if (isPositive) OnEmeraldContainer else Color(0xFF991B1B),
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (isPositive) "+$points Poin" else "$points Poin",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (isPositive) OnEmeraldContainer else Color(0xFF991B1B)
            )
        }
    }
}
