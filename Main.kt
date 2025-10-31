fun main() {
    // Запрашиваем количество строк и столбцов
    print("Введите количество строк: ")
    val rows = readLine()?.toIntOrNull() ?: 0

    print("Введите количество столбцов: ")
    val cols = readLine()?.toIntOrNull() ?: 0

    if (rows <= 0 || cols <= 0) {
        println("Некорректные размеры массива")
        return
    }

    // Создаем двумерный массив
    val matrix = Array(rows) { IntArray(cols) }

    println("\nВведите $rows строк по $cols трехзначных чисел:")

    // Заполняем массив числами
    for (i in 0 until rows) {
        var validInput = false
        while (!validInput) {
            print("Строка ${i + 1}: ")
            val input = readLine()
            val numbers = input?.split("\\s+".toRegex()) ?: emptyList()

            if (numbers.size == cols) {
                var allValid = true
                for (j in 0 until cols) {
                    val num = numbers[j].toIntOrNull()
                    if (num == null || num < 100 || num > 999) {
                        println("Ошибка: '$num' не является трехзначным числом")
                        allValid = false
                        break
                    }
                    matrix[i][j] = num
                }
                if (allValid) {
                    validInput = true
                }
            } else {
                println("Ошибка: введено ${numbers.size} чисел вместо $cols")
            }
        }
    }

    // Подсчитываем количество различных цифр
    val uniqueDigits = countUniqueDigits(matrix)

    // Выводим массив
    println("\nДвумерный массив:")
    printMatrix(matrix)

    // Выводим результат
    println("\nВ массиве использовано ${uniqueDigits.size} различных цифр")
    println("Использованные цифры: ${uniqueDigits.sorted().joinToString(", ")}")
}

fun printMatrix(matrix: Array<IntArray>) {
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            print("${matrix[i][j]}\t")
        }
        println()
    }
}

fun countUniqueDigits(matrix: Array<IntArray>): Set<Int> {
    val uniqueDigits = mutableSetOf<Int>()

    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            val number = matrix[i][j]

            // Разбиваем трехзначное число на цифры
            val hundreds = number / 100
            val tens = (number % 100) / 10
            val units = number % 10

            // Добавляем цифры в множество
            uniqueDigits.add(hundreds)
            uniqueDigits.add(tens)
            uniqueDigits.add(units)
        }
    }

    return uniqueDigits
}