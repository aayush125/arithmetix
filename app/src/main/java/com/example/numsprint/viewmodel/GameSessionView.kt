package com.example.numsprint.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.numsprint.model.toExpressionString
import com.example.numsprint.utils.generateProblem
import kotlin.math.abs

class GameSessionViewModel() : ViewModel() {
    // Current user-entered answer
    var currentAnswer by mutableStateOf("")
        private set

    // Current score
    var score by mutableStateOf(0)
        private set

    // Current problem
    var problem by mutableStateOf("")
        private set

    // Current problem's solution
    var solution by mutableStateOf(0)
        private set

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
            // Minus sign: only allow if string is empty
            "-" -> {
                if (currentAnswer.isEmpty()) {
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
            answerCorrect = true
            transitioning = true
        } else {
            score = 0
            answerCorrect = false
            // filling in for now, change to a proper incorrect answer animation and transition to a game over screen
            cleanUpAndNext()
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
    }

    fun onAnswerDeletePress() {
        Log.d("delete pressed", "delete was pressed")
        currentAnswer = when {
            currentAnswer.length == 1 || currentAnswer == "-0" || currentAnswer == "0." -> ""
            currentAnswer.length > 1 -> currentAnswer.dropLast(1)
            else -> currentAnswer
        }
    }
}