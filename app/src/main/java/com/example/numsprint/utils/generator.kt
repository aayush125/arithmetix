package com.example.numsprint.utils

import com.example.numsprint.model.DifficultyConfig
import com.example.numsprint.model.MaxOperands
import com.example.numsprint.model.Operator
import com.example.numsprint.model.ProblemConfig
import kotlin.random.Random

fun randomIntFromInterval(min: Int, max: Int): Int {
    return Random.nextInt(min, max + 1)
}

// NOTES: First, positive number additions only. Then, additions and subtractions, followed by multiplications only.
// ...Then, divisions only. Then, maybe a fix of multiplication and addition/subtraction. Then, a mix of multiplication division.
// ... Finally, a mix of all these operations.

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

// Generates random operands in the allowed interval.
fun generateOperands(config: DifficultyConfig): List<Int> {
    return List(config.maxOperands.value) {
        randomIntFromInterval(
            if (config.allowNegatives) -config.maxNumber else 0,
            config.maxNumber
        )
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

// Generates a random nonzero integer in the allowed range.
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

// Given a nonzero divisor and the configuration, compute bounds for a multiplier
// so that dividend = divisor * multiplier remains within the allowed range.
fun multiplierBounds(divisor: Int, config: DifficultyConfig): Pair<Int, Int> {
    val M = config.maxNumber.toDouble()
    return if (config.allowNegatives) {
        val lowerBound = if (divisor > 0)
            kotlin.math.ceil(-M / divisor).toInt()
        else
            kotlin.math.ceil(M / divisor).toInt()
        val upperBound = if (divisor > 0)
            kotlin.math.floor(M / divisor).toInt()
        else
            kotlin.math.floor(-M / divisor).toInt()
        Pair(minOf(lowerBound, upperBound), maxOf(lowerBound, upperBound))
    } else {
        Pair(0, kotlin.math.floor(M / divisor).toInt())
    }
}

// Helper to compute the factors of n (ignoring 0).
fun factors(n: Int, config: DifficultyConfig): List<Int> {
    val absN = kotlin.math.abs(n)
    val factorList = mutableListOf<Int>()
    if (absN == 0) return listOf(generateNonZero(config.maxNumber, config.allowNegatives))
    for (k in 1..absN) {
        if (absN % k == 0) {
            factorList.add(k)
            if (config.allowNegatives) factorList.add(-k)
        }
    }
    return factorList.filter { kotlin.math.abs(it) <= config.maxNumber }
}

// Generates operands ensuring that any chain of divisions will yield an integer result.
fun generateValidOperands(config: DifficultyConfig, operations: List<Operator>): List<Int> {
    val operands = generateOperands(config).toMutableList()
    var i = 0
    while (i < operations.size) {
        if (operations[i] == Operator.DIVIDE) {
            // Detect chain of consecutive divisions.
            val chainStart = i
            var chainEnd = i
            while (chainEnd < operations.size && operations[chainEnd] == Operator.DIVIDE) {
                chainEnd++
            }
            // The chain involves operands from chainStart to chainEnd (inclusive).
            // --- First division in the chain ---
            // Ensure the divisor (operand at chainStart+1) is nonzero.
            var divisor = operands[chainStart + 1]
            if (divisor == 0) {
                divisor = generateNonZero(config.maxNumber, config.allowNegatives)
                operands[chainStart + 1] = divisor
            }
            // Adjust the dividend (operand at chainStart) to be a multiple of divisor.
            val (lb, ub) = multiplierBounds(divisor, config)
            val multiplier = if (lb <= ub) randomIntFromInterval(lb, ub) else 0
            operands[chainStart] = divisor * multiplier
            // Compute the intermediate result.
            var intermediate = operands[chainStart] / divisor

            // --- Subsequent divisions in the chain ---
            for (j in (chainStart + 1) until chainEnd) {
                // For operator at index j, the divisor is operand at index j+1.
                var currentDivisor = operands[j + 1]
                if (currentDivisor == 0) {
                    currentDivisor = generateNonZero(config.maxNumber, config.allowNegatives)
                    operands[j + 1] = currentDivisor
                }
                if (intermediate == 0) {
                    // If the intermediate result is 0, any nonzero divisor works.
                    currentDivisor = generateNonZero(config.maxNumber, config.allowNegatives)
                    operands[j + 1] = currentDivisor
                    // 0 divided by any nonzero number is still 0.
                } else {
                    // Choose a divisor that evenly divides the intermediate result.
                    val possibleDivisors = factors(intermediate, config)
                    if (possibleDivisors.isNotEmpty()) {
                        currentDivisor = possibleDivisors.random()
                        operands[j + 1] = currentDivisor
                    }
                }
                // Update the intermediate result.
                intermediate /= currentDivisor
            }
            // Move past the entire division chain.
            i = chainEnd
        } else {
            // For non-division operators, nothing to adjust.
            i++
        }
    }
    return operands
}

// Generates a problem ensuring that division operations yield integer results.
fun generateProblem(score: Int): Triple<ProblemConfig, Int, DifficultyConfig> {
    val config = getDifficultyLevel(score)
    val operations = List(config.maxOperands.value - 1) {
        config.operations.random()
    }

    // If any division operator is used, generate operands safely.
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