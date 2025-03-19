package com.arithmetix.arithmetix.utils

import com.arithmetix.arithmetix.model.Operator
import com.arithmetix.arithmetix.model.ProblemConfig

object ExpressionEvaluator {
    private val OPERATOR_PRECEDENCE = mapOf(
        Operator.MULTIPLY to 2,
        Operator.DIVIDE to 2,
        Operator.ADD to 1,
        Operator.SUBTRACT to 1
    )

    private fun evaluateOperation(num1: Number, operator: Operator, num2: Number): Int {
        val a = num1.toInt()
        val b = num2.toInt()
        return when (operator) {
            Operator.MULTIPLY -> a * b
            Operator.DIVIDE -> {
                require(b != 0) { "Division by zero" }
                a / b
            }

            Operator.ADD -> a + b
            Operator.SUBTRACT -> a - b
        }
    }

    fun calculate(config: ProblemConfig): Int {
        val (operands, operators) = config

        // Validate input
        require(operands.size == operators.size + 1) { "Invalid number of operands and operators" }

        // If only one operator, simple calculation
        if (operators.size == 1) {
            return evaluateOperation(operands[0], operators[0], operands[1])
        }

        // Handle two operators with precedence
        val (firstOp, secondOp) = operators
        val (firstNum, secondNum, thirdNum) = operands

        // Compare operator precedence
        return if (OPERATOR_PRECEDENCE[firstOp]!! >= OPERATOR_PRECEDENCE[secondOp]!!) {
            // Evaluate first operation first
            val intermediateResult = evaluateOperation(firstNum, firstOp, secondNum)
            evaluateOperation(intermediateResult, secondOp, thirdNum)
        } else {
            // Evaluate second operation first
            val intermediateResult = evaluateOperation(secondNum, secondOp, thirdNum)
            evaluateOperation(firstNum, firstOp, intermediateResult)
        }
    }
}