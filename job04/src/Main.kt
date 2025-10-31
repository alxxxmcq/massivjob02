fun main() {
    println("=== НАХОЖДЕНИЕ ПЕРЕСЕЧЕНИЯ МАССИВОВ ===")

    // Получаем массивы от пользователя
    val array1 = getArrayFromUser("первый")
    val array2 = getArrayFromUser("второй")

    // Находим пересечение
    val intersection = findIntersection(array1, array2)

    // Выводим результаты
    printResults(array1, array2, intersection)
}

// Функция для получения массива от пользователя
fun getArrayFromUser(arrayName: String): IntArray {
    println("\nВведите $arrayName массив (числа через пробел):")

    while (true) {
        print("Массив: ")
        val input = readLine()

        if (input.isNullOrBlank()) {
            println("Ошибка: введите хотя бы одно число")
            continue
        }

        try {
            val numbers = input.trim().split("\\s+".toRegex())
                .map { it.toInt() }
                .toIntArray()

            println("✅ Получен массив: ${numbers.contentToString()}")
            return numbers

        } catch (e: NumberFormatException) {
            println("❌ Ошибка: введите только целые числа, разделенные пробелами")
        }
    }
}

// Основная функция для нахождения пересечения
fun findIntersection(array1: IntArray, array2: IntArray): List<Int> {
    // Создаем частотные словари для обоих массивов
    val frequency1 = createFrequencyMap(array1)
    val frequency2 = createFrequencyMap(array2)

    val result = mutableListOf<Int>()

    // Проходим по всем элементам первого массива
    for ((number, count1) in frequency1) {
        // Если число есть во втором массиве
        if (number in frequency2) {
            val count2 = frequency2[number]!!
            // Берем минимальное количество повторений
            val minCount = minOf(count1, count2)
            // Добавляем число minCount раз в результат
            repeat(minCount) {
                result.add(number)
            }
        }
    }

    // Сортируем результат для удобства чтения
    return result.sorted()
}

// Создание частотного словаря (число -> количество повторений)
fun createFrequencyMap(array: IntArray): Map<Int, Int> {
    val frequencyMap = mutableMapOf<Int, Int>()

    for (number in array) {
        frequencyMap[number] = frequencyMap.getOrDefault(number, 0) + 1
    }

    return frequencyMap
}

// Вывод результатов
fun printResults(array1: IntArray, array2: IntArray, intersection: List<Int>) {
    println("\n" + "=".repeat(60))
    println("РЕЗУЛЬТАТЫ:")
    println("=".repeat(60))

    println("Первый массив:  ${array1.contentToString()}")
    println("Второй массив:  ${array2.contentToString()}")
    println("Пересечение:    ${intersection}")

    // Подробная информация о частотах
    println("\n" + "-".repeat(60))
    println("ПОДРОБНЫЙ АНАЛИЗ:")
    println("-".repeat(60))

    val freq1 = createFrequencyMap(array1)
    val freq2 = createFrequencyMap(array2)

    println("Частоты в первом массиве:  $freq1")
    println("Частоты во втором массиве: $freq2")

    // Показываем процесс вычисления для каждого числа
    println("\nПроцесс вычисления пересечения:")
    for ((number, count1) in freq1) {
        if (number in freq2) {
            val count2 = freq2[number]!!
            val minCount = minOf(count1, count2)
            println("Число $number: min($count1, $count2) = $minCount → добавляем $minCount раз")
        }
    }
}

// Дополнительные функции для разных подходов

// Альтернативный способ 1: Использование мутабельных списков
fun findIntersectionAlternative1(array1: IntArray, array2: IntArray): List<Int> {
    val list1 = array1.toMutableList()
    val list2 = array2.toMutableList()
    val result = mutableListOf<Int>()

    for (number in list1) {
        if (list2.contains(number)) {
            result.add(number)
            list2.remove(number)
        }
    }

    return result.sorted()
}

// Альтернативный способ 2: Функциональный подход
fun findIntersectionAlternative2(array1: IntArray, array2: IntArray): List<Int> {
    val freq1 = array1.groupBy { it }.mapValues { it.value.size }
    val freq2 = array2.groupBy { it }.mapValues { it.value.size }

    return freq1.flatMap { (number, count1) ->
        val count2 = freq2[number] ?: 0
        List(minOf(count1, count2)) { number }
    }.sorted()
}

// Функция для тестирования
fun testIntersection() {
    println("\n" + "=".repeat(60))
    println("ТЕСТОВЫЕ ПРИМЕРЫ:")
    println("=".repeat(60))

    val testCases = listOf(
        Pair(
            intArrayOf(1, 2, 3, 2, 0),
            intArrayOf(5, 1, 2, 7, 3, 2, 2)
        ),
        Pair(
            intArrayOf(1, 1, 1),
            intArrayOf(1, 1, 2, 2)
        ),
        Pair(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6)
        ),
        Pair(
            intArrayOf(1, 2, 3, 4, 5),
            intArrayOf(3, 4, 5, 6, 7)
        )
    )

    for ((index, testCase) in testCases.withIndex()) {
        val (arr1, arr2) = testCase
        val intersection = findIntersection(arr1, arr2)

        println("\nТест ${index + 1}:")
        println("Массив 1: ${arr1.contentToString()}")
        println("Массив 2: ${arr2.contentToString()}")
        println("Пересечение: $intersection")
        println("Ожидаемый результат: ${getExpectedResult(arr1, arr2)}")
        println("-".repeat(40))
    }
}

// Вспомогательная функция для получения ожидаемого результата
fun getExpectedResult(array1: IntArray, array2: IntArray): List<Int> {
    val freq1 = createFrequencyMap(array1)
    val freq2 = createFrequencyMap(array2)

    return freq1.flatMap { (number, count1) ->
        val count2 = freq2[number] ?: 0
        List(minOf(count1, count2)) { number }
    }.sorted()
}