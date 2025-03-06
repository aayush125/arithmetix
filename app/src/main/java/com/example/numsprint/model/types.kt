package com.example.numsprint.model

enum class Operator(val symbol: String) {
    MULTIPLY("x"),
    DIVIDE("÷"),
    ADD("+"),
    SUBTRACT("-")
}

data class ProblemConfig(
    val operands: List<Int>,
    val operators: List<Operator>
)

fun ProblemConfig.toExpressionString(): String {
    if (operands.size != operators.size + 1) {
        throw IllegalArgumentException("Invalid ProblemConfig: Number of operands must be exactly one more than the number of operators.")
    }

    val expression = StringBuilder()
    for (i in operators.indices) {
        expression.append(formatOperand(operands[i]))
            .append(" ")
            .append(operators[i].symbol)
            .append(" ")
    }
    expression.append(formatOperand(operands.last()))

    return expression.toString()
}

private fun formatOperand(operand: Int): String {
    return operand.toString()
}

enum class DecimalPlaces(val value: Int) {
    ZERO(0),
    ONE(1)
}

enum class MaxOperands(val value: Int) {
    TWO(2),
    THREE(3)
}

data class DifficultyConfig(
    val maxNumber: Int,
    val operations: List<Operator>,
    val allowNegatives: Boolean,
    val maxOperands: MaxOperands
)

//data class DifficultyConfig(
//    val maxNumberAdditionSubtraction: Int, // Max number for addition/subtraction
//    val maxNumberMultiplication: Int,      // Max number for multiplication
//    val maxNumberDivision: Int,            // Max number for division
//    val operations: List<Operator>,        // Allowed operations
//    val maxOperands: Int,                  // Maximum number of operands
//    val allowNegatives: Boolean            // Whether negative operands are allowed
//)
