fun main() {
    println("=== ГРУППИРОВКА СЛОВ ПО ОДИНАКОВЫМ БУКВАМ ===")

    // Получаем массив слов от пользователя
    val words = getWordsFromUser()

    // Группируем слова
    val groupedWords = groupWordsByLetters(words)

    // Выводим результаты
    printResults(words, groupedWords)
}

// Функция для получения массива слов от пользователя
fun getWordsFromUser(): List<String> {
    println("\nВведите слова через пробел или запятую:")

    while (true) {
        print("Слова: ")
        val input = readLine()

        if (input.isNullOrBlank()) {
            println("❌ Ошибка: введите хотя бы одно слово")
            continue
        }

        // Разделяем ввод на слова (поддерживаем пробелы и запятые)
        val words = input.split(",", " ")
            .map { it.trim() }
            .filter { it.isNotBlank() }

        if (words.isEmpty()) {
            println("❌ Ошибка: не найдено валидных слов")
            continue
        }

        println("✅ Получено слов: ${words.size}")
        return words
    }
}

// Основная функция для группировки слов
fun groupWordsByLetters(words: List<String>): Map<String, List<String>> {
    val groups = mutableMapOf<String, MutableList<String>>()

    for (word in words) {
        // Создаем ключ - отсортированная версия букв слова
        val key = createLetterKey(word)

        // Добавляем слово в соответствующую группу
        if (key !in groups) {
            groups[key] = mutableListOf()
        }
        groups[key]?.add(word)
    }

    return groups
}

// Создание ключа для группировки (отсортированные буквы слова)
fun createLetterKey(word: String): String {
    return word.lowercase()
        .toCharArray()
        .sorted()
        .joinToString("")
}

// Вывод результатов
fun printResults(originalWords: List<String>, groupedWords: Map<String, List<String>>) {
    println("\n" + "=".repeat(60))
    println("РЕЗУЛЬТАТЫ ГРУППИРОВКИ:")
    println("=".repeat(60))

    println("Исходные слова: ${originalWords}")
    println("Найдено групп: ${groupedWords.size}")

    println("\n" + "-".repeat(40))
    println("ГРУППЫ СЛОВ:")
    println("-".repeat(40))

    // Сортируем группы по размеру (от больших к меньшим) и затем по первому слову
    val sortedGroups = groupedWords.entries.sortedWith(
        compareByDescending<Map.Entry<String, List<String>>> { it.value.size }
            .thenBy { it.value.first() }
    )

    for ((index, entry) in sortedGroups.withIndex()) {
        val key = entry.key
        val group = entry.value
        println("Группа ${index + 1} (ключ: '$key'): ${group.sorted()}")
    }

    // Подробная информация о процессе
    printDetailedAnalysis(originalWords, groupedWords)
}

// Подробный анализ процесса группировки
fun printDetailedAnalysis(words: List<String>, groupedWords: Map<String, List<String>>) {
    println("\n" + "-".repeat(40))
    println("ПОДРОБНЫЙ АНАЛИЗ:")
    println("-".repeat(40))

    for (word in words.sorted()) {
        val key = createLetterKey(word)
        val group = groupedWords[key] ?: listOf()
        println("'$word' → ключ: '$key' → группа: ${group.sorted()}")
    }
}

// Альтернативный способ группировки с использованием группировки Kotlin
fun groupWordsByLettersAlternative(words: List<String>): Map<String, List<String>> {
    return words.groupBy { createLetterKey(it) }
}

// Функция для проверки, являются ли слова анаграммами
fun areAnagrams(word1: String, word2: String): Boolean {
    return createLetterKey(word1) == createLetterKey(word2)
}

// Дополнительная функция для тестирования
fun runTests() {
    println("\n" + "=".repeat(60))
    println("ТЕСТОВЫЕ ПРИМЕРЫ:")
    println("=".repeat(60))

    val testCases = listOf(
        listOf("eat", "tea", "tan", "ate", "nat", "bat"),
        listOf("hello", "world", "dlrow", "olelh", "test", "sett"),
        listOf("abc", "bca", "cab", "cba", "acb", "bac"),
        listOf("кот", "ток", "окт", "кто", "отк"),
        listOf("один", "два", "три")
    )

    for ((index, testWords) in testCases.withIndex()) {
        println("\nТест ${index + 1}:")
        println("Слова: $testWords")

        val grouped = groupWordsByLetters(testWords)
        println("Результат группировки:")

        val sortedEntries = grouped.entries.sortedBy { it.value.first() }
        for ((groupIndex, entry) in sortedEntries.withIndex()) {
            val group = entry.value
            println("  Группа ${groupIndex + 1}: ${group.sorted()}")
        }
        println("-".repeat(40))
    }
}