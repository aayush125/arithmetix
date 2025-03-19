package com.arithmetix.arithmetix.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.arithmetix.arithmetix.model.DifficultyConfig
import com.arithmetix.arithmetix.model.GameModes
import com.arithmetix.arithmetix.model.MaxOperands
import com.arithmetix.arithmetix.model.toExpressionString
import com.arithmetix.arithmetix.utils.generateProblem
import kotlin.math.abs

data class PreviousProblem(
    val problem: String,
    val solution: String,
    val wasCorrect: Boolean
)

class GameSessionViewModel(private var gameMode: GameModes) : ViewModel() {
    // Current user-entered answer
    var currentAnswer by mutableStateOf("")
        private set

    // Current score
    var score by mutableStateOf(0)
        private set

    // Current problem
    var problem by mutableStateOf("")
        private set

    var previousProblem by mutableStateOf(PreviousProblem("", "", false))
        private set

    // Current problem's solution
    var solution by mutableStateOf(0)
        private set

    var difficultyConfig by mutableStateOf(DifficultyConfig(0, listOf(), false, MaxOperands.TWO))

    var answerCorrect: Boolean? by mutableStateOf(null)
        private set

    // Flag to check if the game is transitioning to a new problem
    private var transitioning by mutableStateOf(false)

    init {
        generateNewProblem()
    }

    fun onKeyPressed(key: String) {
        if (currentAnswer.length >= solution.toString().length) return

        when (key) {
            // Minus sign: only allow if string is empty and the solution length is more than 1
            "-" -> {
                if (currentAnswer.isEmpty() && solution.toString().length > 1) {
                    currentAnswer = "-"
                }
            }
            // Decimal point: prevent multiple decimals
            "." -> {
                if (!currentAnswer.contains(".")) {
                    // If empty, prepend "0."
                    // If only "-", append "0."
                    currentAnswer = when {
                        currentAnswer.isEmpty() -> "0."
                        currentAnswer == "-" -> "-0."
                        else -> currentAnswer + "."
                    }
                }
            }
            // Zero handling: allow zero in more scenarios
            "0" -> {
                currentAnswer = when {
                    // Allow first zero
                    currentAnswer.isEmpty() -> "0"
                    // Allow zero after minus sign
                    currentAnswer == "-" -> "-0"
                    // Allow zero in decimal context
                    currentAnswer.endsWith(".") -> currentAnswer + "0"
                    // Allow zero in existing number that's not just "0"
                    currentAnswer != "0" && currentAnswer != "-0" -> currentAnswer + "0"
                    // Keep existing "0" or "-0"
                    else -> currentAnswer
                }
            }
            // Digit handling
            else -> {
                // Prevent multiple leading zeros
                currentAnswer = when {
                    currentAnswer == "0" -> key
                    currentAnswer == "-0" -> "-$key"
                    else -> currentAnswer + key
                }
            }
        }

        if (gameMode == GameModes.TIME_ATTACK && currentAnswer.length == solution.toString().length) {
            onNextClicked()
        }
    }

    private fun checkAnswer(solution: Int, userAnswer: String): Boolean {
        if (userAnswer.isBlank()) return false

        return try {
            val ans = userAnswer.toDouble()
            val epsilon = 0.01
            abs(ans - solution) < epsilon
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun onNextClicked() {
        if (transitioning || currentAnswer.isEmpty()) return
        val correct = checkAnswer(solution, currentAnswer)
        if (correct) {
            score += 1
            previousProblem = PreviousProblem(problem, currentAnswer, true)
            answerCorrect = true
            transitioning = true
            cleanUpAndNext()
        } else {
            when (gameMode) {
                GameModes.ENDLESS -> {
                    score = 0
                    // filling in for now, change to a proper incorrect answer animation and transition to a game over screen
                    previousProblem = PreviousProblem(problem, currentAnswer, wasCorrect = false)
                    answerCorrect = false
                    cleanUpAndNext()
                }

                GameModes.TIME_ATTACK -> {
                    // For time attack mode, if the answer is incorrect, simply mark it as incorrect and move on
                    previousProblem = PreviousProblem(problem, currentAnswer, wasCorrect = false)
                    answerCorrect = false
                    cleanUpAndNext()
                }
            }
        }
    }

    fun cleanUpAndNext() {
        Log.d("cleanUpAndNext", "Generate a new problem now...")
        currentAnswer = ""
        answerCorrect = null
        transitioning = false
        generateNewProblem()
    }

    private fun generateNewProblem() {
        val (newProblem, newSolution, newProblemConfig) = generateProblem(score)
        problem = newProblem.toExpressionString()
        solution = newSolution
        difficultyConfig = newProblemConfig
    }

    fun onAnswerDeletePress() {
        Log.d("delete pressed", "delete was pressed")
        currentAnswer = when {
            currentAnswer.length == 1 || currentAnswer == "-0" || currentAnswer == "0." -> ""
            currentAnswer.length > 1 -> currentAnswer.dropLast(1)
            else -> currentAnswer
        }
    }

    fun resetState() {
        score = 0
        previousProblem = PreviousProblem("", "", false)
        cleanUpAndNext()
    }

    fun solve() {
        currentAnswer = solution.toString()
        onNextClicked()
    }
}

class GameSessionViewModelFactory(private val gameMode: GameModes) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(GameSessionViewModel::class.java)) {
            return GameSessionViewModel(gameMode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}