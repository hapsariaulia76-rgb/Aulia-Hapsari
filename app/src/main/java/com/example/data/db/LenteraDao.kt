package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.DisciplineRecord
import com.example.data.model.Student
import com.example.data.model.WeeklyEvaluation
import kotlinx.coroutines.flow.Flow

@Dao
interface LenteraDao {

    // Students
    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getAllStudents(): Flow<List<Student>>

    @Query("SELECT * FROM students WHERE id = :id LIMIT 1")
    fun getStudentById(id: Long): Flow<Student?>

    @Query("SELECT COUNT(*) FROM students")
    suspend fun getStudentCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student): Long

    @Update
    suspend fun updateStudent(student: Student)

    @Query("UPDATE students SET disciplinePoints = :points, characterLevel = :level WHERE id = :id")
    suspend fun updateStudentPoints(id: Long, points: Int, level: String)

    // Weekly Evaluations (Setiap Hari Sabtu)
    @Query("SELECT * FROM weekly_evaluations ORDER BY id DESC")
    fun getAllEvaluations(): Flow<List<WeeklyEvaluation>>

    @Query("SELECT * FROM weekly_evaluations WHERE studentId = :studentId ORDER BY id DESC")
    fun getEvaluationsByStudent(studentId: Long): Flow<List<WeeklyEvaluation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvaluation(evaluation: WeeklyEvaluation): Long

    @Update
    suspend fun updateEvaluation(evaluation: WeeklyEvaluation)

    // Discipline & Character Points
    @Query("SELECT * FROM discipline_records ORDER BY id DESC")
    fun getAllDisciplineRecords(): Flow<List<DisciplineRecord>>

    @Query("SELECT * FROM discipline_records WHERE studentId = :studentId ORDER BY id DESC")
    fun getDisciplineRecordsByStudent(studentId: Long): Flow<List<DisciplineRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDisciplineRecord(record: DisciplineRecord): Long
}
