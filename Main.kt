fun main() {
    // Тестовые случаи
    val symmetricMatrix = arrayOf(
        intArrayOf(5, 9, 6, 7, 2),
        intArrayOf(9, 8, 4, 5, 3),
        intArrayOf(6, 4, 3, 8, 7),
        intArrayOf(7, 5, 8, 4, 8),
        intArrayOf(2, 3, 7, 8, 1)
    )

    val nonSymmetricMatrix = arrayOf(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5, 6),
        intArrayOf(7, 8, 9)
    )

    println("Тест 1 - Симметричная матрица:")
    testMatrix(symmetricMatrix)

    println("\nТест 2 - Несимметричная матрица:")
    testMatrix(nonSymmetricMatrix)

    // Интерактивный тест
    println("\n" + "=".repeat(40))
    interactiveTest()
}

fun testMatrix(matrix: Array<IntArray>) {
    println("Матрица:")
    printMatrix(matrix)

    val isSymmetric = isMatrixSymmetric(matrix)
    println("Симметрична: $isSymmetric")
}

fun interactiveTest() {
    println("ИНТЕРАКТИВНЫЙ ТЕСТ")
    println("Введите размер квадратной матрицы: ")
    val size = readLine()?.toIntOrNull() ?: 3

    println("Введите матрицу $size x $size:")
    val matrix = Array(size) { IntArray(size) }

    for (i in 0 until size) {
        var valid = false
        while (!valid) {
            print("Строка ${i + 1}: ")
            val row = readLine()?.split(" ")?.mapNotNull { it.toIntOrNull() }
            if (row != null && row.size == size) {
                matrix[i] = row.toIntArray()
                valid = true
            } else {
                println("Ошибка! Введите $size чисел через пробел")
            }
        }
    }

    println("\nВаша матрица:")
    printMatrix(matrix)

    val result = isMatrixSymmetric(matrix)
    println("\nРезультат: матрица ${if (result) "симметрична" else "не симметрична"} относительно главной диагонали")
}

// Функция проверки симметричности (компактная версия)
fun isMatrixSymmetric(matrix: Array<IntArray>): Boolean {
    for (i in matrix.indices) {
        for (j in i + 1 until matrix.size) {
            if (matrix[i][j] != matrix[j][i]) return false
        }
    }
    return true
}

fun printMatrix(matrix: Array<IntArray>) {
    for (row in matrix) {
        println(row.joinToString("\t"))
    }
}