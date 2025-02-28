package com.lum.core.data.usecases

import com.lum.core.domain.models.learningMethod
import com.lum.core.domain.repo.GameRepository

class getPredictedMnemoType(val repo: GameRepository) {
    suspend fun execute(): learningMethod {
        val res = repo.getAllLearningMethod()
        return res.last()
    }
}
