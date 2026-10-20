package com.example.data.model

enum class RuleCategory {
    HOME, SCHOOL
}

data class RuleItem(
    val id: String,
    val category: RuleCategory,
    val title: String,
    val subtitle: String,
    val description: String,
    val characterValue: String,
    val pointsGuide: String,
    val actionItems: List<String>,
    val tipsForParentsOrTeachers: String
)
