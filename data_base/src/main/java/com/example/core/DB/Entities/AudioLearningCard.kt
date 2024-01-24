package com.example.core.DB.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AudioLearningCard")
class AudioLearningCard(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 1,
    @ColumnInfo(name = "themeId")
    val themeId: Int,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "answers")
    val answers: List<Answer>,
    @ColumnInfo(name = "RACurrentMonth")
    val RACurrentMonth: Double,
    @ColumnInfo(name = "RALastMonth")
    val RALastMonth: Double,
    @ColumnInfo(name = "AverageRA")
    val AverageRA: Double,
    @ColumnInfo(name = "dateCreation")
    val dateCreation: String,
    @ColumnInfo(name = "repetitionAmount")
    val repetitionAmount: Int,
    @ColumnInfo(name = "lastUpdateNumber")
    val lastUpdateNumber: Int,
    @ColumnInfo(name = "lastMonthRepetitionNumber")
    val lastMonthRepetitionNumber: Int,
)
