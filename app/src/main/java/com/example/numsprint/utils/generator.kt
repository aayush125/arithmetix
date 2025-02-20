package com.example.numsprint.utils

import com.example.numsprint.model.DifficultyConfig
import com.example.numsprint.model.MaxOperands
import com.example.numsprint.model.Operator
import com.example.numsprint.model.ProblemConfig
import kotlin.random.Random

fun randomIntFromInterval(min: Int, max: Int): Int {
    return Random.nextInt(min, max + 1)
}

fun getDifficultyLevel(score: Int): DifficultyConfig {
    return when {
        score < 5 -> DifficultyConfig(
            9,
            listOf(Operator.ADD),
            false,
            MaxOperands.TWO
        )

        score < 10 -> DifficultyConfig(
            9,
            listOf(Operator.ADD, Operator.SUBTRACT),
            false,
            MaxOperands.TWO
        )

        score < 20 -> DifficultyConfig(
            50,
            listOf(Operator.ADD),
            true,
            MaxOperands.TWO
        )

        score < 25 -> DifficultyConfig(
            100,
            listOf(Operator.ADD, Operator.SUBTRACT, Operator.MULTIPLY),
            false,
            MaxOperands.TWO
        )

        score < 30 -> DifficultyConfig(
            50,
            listOf(Operator.ADD, Operator.SUBTRACT),
            true,
            MaxOperands.THREE
        )

        score < 35 -> DifficultyConfig(
            50,
            listOf(Operator.ADD, Operator.SUBTRACT, Operator.MULTIPLY),
            false,
            MaxOperands.THREE
        )

        else -> DifficultyConfig(
            100,
            listOf(Operator.ADD, Operator.SUBTRACT, Operator.MULTIPLY, Operator.DIVIDE),
            true,
            MaxOperands.THREE
        )
    }
}

//fun getDifficultyLevel(score: Int): DifficultyConfig {
//    return when {
//        score <= 3 -> DifficultyConfig(
//            maxNumberAdditionSubtraction = 9,
//            maxNumberMultiplication = 0,
//            maxNumberDivision = 0,
//            operations = listOf(Operator.ADD, Operator.SUBTRACT),
//            maxOperands = 2,
//            allowNegatives = false
//        )
//        score <= 7 -> DifficultyConfig(
//            maxNumberAdditionSubtraction = 20,
//            maxNumberMultiplication = 0,
//            maxNumberDivision = 0,
//            operations = listOf(Operator.ADD, Operator.SUBTRACT),
//            maxOperands = 2,
//            allowNegatives = false
//        )
//        score <= 14 -> DifficultyConfig(
//            maxNumberAdditionSubtraction = 40,
//            maxNumberMultiplication = 0,
//            maxNumberDivision = 0,
//            operations = listOf(Operator.ADD, Operator.SUBTRACT),
//            maxOperands = 2,
//            allowNegatives = false
//        )
//        score <= 20 -> DifficultyConfig(
//            maxNumberAdditionSubtraction = 0,
//            maxNumberMultiplication = 9,
//            maxNumberDivision = 0,
//            operations = listOf(Operator.MULTIPLY),
//            maxOperands = 2,
//            allowNegatives = false
//        )
//        score <= 34 -> DifficultyConfig(
//            maxNumberAdditionSubtraction = 99,
//            maxNumberMultiplication = 9,
//            maxNumberDivision = 0,
//            operations = listOf(Operator.ADD, Operator.SUBTRACT, Operator.MULTIPLY),
//            maxOperands = 2,
//            allowNegatives = true
//        )
//        score <= 40 -> DifficultyConfig(
//            maxNumberAdditionSubtraction = 0,
//            maxNumberMultiplication = 30,
//            maxNumberDivision = 0,
//            operations = listOf(Operator.MULTIPLY),
//            maxOperands = 2,
//            allowNegatives = true
//        )
//        score <= 50 -> DifficultyConfig(
//            maxNumberAdditionSubtraction = 99,
//            maxNumberMultiplication = 0,
//            maxNumberDivision = 0,
//            operations = listOf(Operator.ADD, Operator.SUBTRACT),
//            maxOperands = 3,
//            allowNegatives = true
//        )
//        score <= 65 -> DifficultyConfig(
//            maxNumberAdditionSubtraction = 0,
//            maxNumberMultiplication = 30,
//            maxNumberDivision = 20,
//            operations = listOf(Operator.MULTIPLY, Operator.DIVIDE),
//            maxOperands = 2,
//            allowNegatives = true
//        )
//        score <= 80 -> DifficultyConfig(
//            maxNumberAdditionSubtraction = 999,
//            maxNumberMultiplication = 99,
//            maxNumberDivision = 9,
//            operations = listOf(Operator.ADD, Operator.SUBTRACT, Operator.MULTIPLY, Operator.DIVIDE),
//            maxOperands = 3,
//            allowNegatives = true
//        )
//        else -> DifficultyConfig(
//            maxNumberAdditionSubtraction = 999,
//            maxNumberMultiplication = 99,
//            maxNumberDivision = 99,
//            operations = listOf(Operator.ADD, Operator.SUBTRACT, Operator.MULTIPLY, Operator.DIVIDE),
//            maxOperands = 3,
//            allowNegatives = true
//        )
//    }
//}

fun generateOperands(config: DifficultyConfig): List<Int> {
    return List(config.maxOperands.value) {
        val num = randomIntFromInterval(
            if (config.allowNegatives) -config.maxNumber else 0,
            config.maxNumber
        )
        num
    }
}

//fun generateOperands(config: DifficultyConfig, operations: List<Operator>): List<Int> {
//    return List(config.maxOperands) { j ->
//        val operationIndex = if (j == 0) 0 else j - 1
//        val operation = operations.getOrElse(operationIndex) { Operator.ADD }
//
//        // Determine max number based on operation type
//        val max = when (operation) {
//            Operator.ADD, Operator.SUBTRACT -> config.maxNumberAdditionSubtraction
//            Operator.MULTIPLY -> config.maxNumberMultiplication
//            Operator.DIVIDE -> config.maxNumberDivision
//        }
//
//        // Handle division denominators
//        val isDenominator = j > 0 && operations[j - 1] == Operator.DIVIDE
//        val (min, maxAdjusted) = when {
//            isDenominator && config.allowNegatives -> Pair(-max, max)
//            isDenominator -> Pair(1, max)
//            else -> Pair(if (config.allowNegatives) -max else 0, max)
//        }
//
//        var num: Int
//        do {
//            num = randomIntFromInterval(min, maxAdjusted)
//        } while (isDenominator && num == 0)
//
//        num
//    }
//}

// Returns a random nonzero integer in the interval.
// For negative-allowed configurations the range is symmetric, otherwise only positive numbers.
fun generateNonZero(maxNumber: Int, allowNegatives: Boolean): Int {
    var num = 0
    while (num == 0) {
        num = if (allowNegatives) {
            randomIntFromInterval(-maxNumber, maxNumber)
        } else {
            randomIntFromInterval(1, maxNumber)
        }
    }
    return num
}

// Given a divisor and the configuration, compute the bounds for a multiplier
// so that dividend = divisor * multiplier remains in the allowed range.
fun multiplierBounds(divisor: Int, config: DifficultyConfig): Pair<Int, Int> {
    val max = config.maxNumber.toDouble()
    return if (config.allowNegatives) {
        // When negatives are allowed, the dividend must lie between -M and M.
        val lowerBound = if (divisor > 0)
            kotlin.math.ceil(-max / divisor).toInt()
        else
            kotlin.math.ceil(max / divisor).toInt()
        val upperBound = if (divisor > 0)
            kotlin.math.floor(max / divisor).toInt()
        else
            kotlin.math.floor(-max / divisor).toInt()
        Pair(minOf(lowerBound, upperBound), maxOf(lowerBound, upperBound))
    } else {
        // For nonnegative numbers, the dividend should be in [0, M].
        val lowerBound = 0
        val upperBound = kotlin.math.floor(max / divisor).toInt()
        Pair(lowerBound, upperBound)
    }
}

// Generates operands that are "division-safe" when a division operator is present.
// For each division operator, we ensure the divisor (operand at index+1) is nonzero,
// then adjust the dividend (operand at index) to be a multiple of the divisor.
fun generateValidOperands(config: DifficultyConfig, operations: List<Operator>): List<Int> {
    val operands = generateOperands(config).toMutableList()
    operations.forEachIndexed { index, operator ->
        if (operator == Operator.DIVIDE) {
            // Ensure the divisor is nonzero.
            var divisor = operands[index + 1]
            if (divisor == 0) {
                divisor = generateNonZero(config.maxNumber, config.allowNegatives)
                operands[index + 1] = divisor
            }
            // Compute allowed multiplier bounds so that dividend is within range.
            val (lb, ub) = multiplierBounds(divisor, config)
            // Pick a multiplier randomly (if no valid range, default to 0).
            val multiplier = if (lb <= ub) randomIntFromInterval(lb, ub) else 0
            operands[index] = divisor * multiplier
        }
    }
    return operands
}

// Generates a problem, ensuring that if division is used, the operands guarantee an integer result.
fun generateProblem(score: Int): Triple<ProblemConfig, Int, DifficultyConfig> {
    val config = getDifficultyLevel(score)
    val operations = List(config.maxOperands.value - 1) {
        config.operations.random()
    }

    // Use the division-safe generator if any operation is division.
    val operands = if (Operator.DIVIDE in operations) {
        generateValidOperands(config, operations)
    } else {
        generateOperands(config)
    }

    val solution = ExpressionEvaluator.calculate(
        ProblemConfig(
            operands = operands,
            operators = operations
        )
    )
    val problem = ProblemConfig(operands, operations)
    return Triple(problem, solution, config)
}


// Old generateProblem
//fun generateProblem(score: Int): Triple<ProblemConfig, Int, DifficultyConfig> {
//    val config = getDifficultyLevel(score)
//    var operands = generateOperands(config)
//    val operations = List(config.maxOperands.value - 1) {
//        config.operations.random()
//    }
//
//    var solution: Int? = null
//
//    while (Operator.DIVIDE in operations) {
//        try {
//            // evaluate
//            solution = ExpressionEvaluator.calculate(
//                ProblemConfig(
//                    operands = operands,
//                    operators = operations
//                )
//            )
//            break
//        } catch (e: ArithmeticException) {
//            operands = generateOperands(config)
//        }
//    }
//
//    if (solution == null) {
//        // evaluate
//        solution = ExpressionEvaluator.calculate(
//            ProblemConfig(
//                operands = operands,
//                operators = operations
//            )
//        )
//    }
//
//    val problem = ProblemConfig(operands, operations)
//    return Triple(
//        problem,
////        String.format("%.${1}f", solution).toInt(), // trim to 1 decimal place
//        solution,
//        config
//    )
//}

//fun generateProblem(score: Int): Triple<ProblemConfig, Double, DifficultyConfig> {
//    val config = getDifficultyLevel(score)
//    var operands: List<Int>
//    var operations: List<Operator>
//    var solution: Double? = null
//    var attempts = 0
//
//    do {
//        // Generate operations first to determine operand constraints
//        operations = List(config.maxOperands - 1) { config.operations.random() }
//        operands = generateOperands(config, operations)
//
//        try {
//            solution = ExpressionEvaluator.calculate(ProblemConfig(operands, operations))
//            attempts = 0
//        } catch (e: ArithmeticException) {
//            attempts++
//            if (attempts > 10) throw IllegalStateException("Failed to generate valid problem after 10 attempts")
//        }
//    } while (attempts > 0)
//
//    return Triple(
//        ProblemConfig(operands, operations),
//        "%.1f".format(solution).toDouble(),
//        config
//    )
//}