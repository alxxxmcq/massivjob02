fun main() {
    println("=== ШИФРОВАНИЕ СООБЩЕНИЙ ===")

    // Инициализируем алфавит (пример из задачи)
    val alphabet = initAlphabet()

    println("Русский алфавит с номерами:")
    printAlphabet(alphabet)

    // Получаем данные от пользователя
    val message = getInputMessage()
    val keyword = getInputKeyword()

    // Шифруем сообщение
    val encryptedMessage = encryptMessage(message, keyword, alphabet)

    // Выводим результаты
    printResults(message, keyword, encryptedMessage, alphabet)
}

// Инициализация алфавита согласно примеру из задачи
fun initAlphabet(): Map<Char, Int> {
    val alphabetChars = listOf(
        'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М',
        'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь',
        'Ы', 'Ъ', 'Э', 'Ю', 'Я'
    )

    val numbers = listOf(
        21, 13, 4, 20, 22, 1, 25, 12, 24, 14, 2, 28, 9, 23,
        3, 29, 6, 16, 15, 11, 26, 5, 30, 27, 8, 18, 10, 33,
        31, 32, 19, 7, 17
    )

    return alphabetChars.zip(numbers).toMap()
}

// Создаем обратное отображение (номер -> символ)
fun createReverseAlphabet(alphabet: Map<Char, Int>): Map<Int, Char> {
    return alphabet.entries.associate { (char, number) -> number to char }
}

// Получение сообщения от пользователя
fun getInputMessage(): String {
    print("Введите сообщение для шифрования: ")
    return readLine()?.uppercase() ?: ""
}

// Получение ключевого слова от пользователя
fun getInputKeyword(): String {
    print("Введите ключевое слово: ")
    return readLine()?.uppercase() ?: ""
}

// Основная функция шифрования
fun encryptMessage(message: String, keyword: String, alphabet: Map<Char, Int>): String {
    val reverseAlphabet = createReverseAlphabet(alphabet)
    val result = StringBuilder()
    val keywordLength = keyword.length

    for ((index, char) in message.withIndex()) {
        // Пропускаем пробелы и не-буквы
        if (!char.isLetter() || char !in alphabet) {
            result.append(char)
            continue
        }

        // Получаем номер символа ключа для текущей позиции
        val keyChar = keyword[index % keywordLength]
        val shift = alphabet[keyChar] ?: 1

        // Получаем номер текущего символа сообщения
        val currentCharNumber = alphabet[char] ?: continue

        // Вычисляем новый номер с учетом сдвига (закольцованный)
        val newNumber = ((currentCharNumber + shift - 1) % 33) + 1

        // Получаем зашифрованный символ
        val encryptedChar = reverseAlphabet[newNumber] ?: char

        result.append(encryptedChar)
    }

    return result.toString()
}

// Функция дешифрования (дополнительно)
fun decryptMessage(encryptedMessage: String, keyword: String, alphabet: Map<Char, Int>): String {
    val reverseAlphabet = createReverseAlphabet(alphabet)
    val result = StringBuilder()
    val keywordLength = keyword.length

    for ((index, char) in encryptedMessage.withIndex()) {
        // Пропускаем пробелы и не-буквы
        if (!char.isLetter() || char !in alphabet) {
            result.append(char)
            continue
        }

        // Получаем номер символа ключа для текущей позиции
        val keyChar = keyword[index % keywordLength]
        val shift = alphabet[keyChar] ?: 1

        // Получаем номер текущего зашифрованного символа
        val currentCharNumber = alphabet[char] ?: continue

        // Вычисляем исходный номер с учетом обратного сдвига (закольцованный)
        val originalNumber = ((currentCharNumber - shift - 1 + 33) % 33) + 1

        // Получаем расшифрованный символ
        val decryptedChar = reverseAlphabet[originalNumber] ?: char

        result.append(decryptedChar)
    }

    return result.toString()
}

// Вывод алфавита в табличном виде
fun printAlphabet(alphabet: Map<Char, Int>) {
    println("\n" + "=".repeat(80))
    var count = 0
    for ((char, number) in alphabet.entries.sortedBy { it.value }) {
        print("$char:${number.toString().padStart(2)}  ")
        count++
        if (count % 8 == 0) println()
    }
    println("\n" + "=".repeat(80))
}

// Вывод результатов
fun printResults(message: String, keyword: String, encryptedMessage: String, alphabet: Map<Char, Int>) {
    println("\n" + "=".repeat(50))
    println("РЕЗУЛЬТАТЫ ШИФРОВАНИЯ:")
    println("=".repeat(50))

    println("Исходное сообщение: $message")
    println("Ключевое слово: $keyword")
    println("Зашифрованное сообщение: $encryptedMessage")

    // Демонстрация процесса шифрования для первых нескольких символов
    println("\nПроцесс шифрования (первые 5 символов):")
    println("-".repeat(40))

    val keywordLength = keyword.length
    for (i in 0 until minOf(5, message.length)) {
        val char = message[i]
        if (char.isLetter() && char in alphabet) {
            val keyChar = keyword[i % keywordLength]
            val shift = alphabet[keyChar] ?: 1
            val currentNumber = alphabet[char] ?: 0
            val newNumber = ((currentNumber + shift - 1) % 33) + 1
            val encryptedChar = createReverseAlphabet(alphabet)[newNumber]

            println("'$char' (№$currentNumber) + '$keyChar' (сдвиг $shift) = '$encryptedChar' (№$newNumber)")
        }
    }

    // Демонстрация дешифрования
    val decryptedMessage = decryptMessage(encryptedMessage, keyword, alphabet)
    println("\nПроверка дешифрования: $decryptedMessage")
    println("Сообщение восстановлено корректно: ${message == decryptedMessage}")
}

// Дополнительная функция для тестирования
fun testEncryption() {
    println("\n" + "=".repeat(50))
    println("ТЕСТОВЫЕ ПРИМЕРЫ:")
    println("=".repeat(50))

    val alphabet = initAlphabet()

    val testCases = listOf(
        "ПРИВЕТ" to "КОД",
        "ШИФРОВАНИЕ" to "KEY",
        "КОТ" to "МЫШЬ"
    )

    for ((message, keyword) in testCases) {
        val encrypted = encryptMessage(message.uppercase(), keyword.uppercase(), alphabet)
        val decrypted = decryptMessage(encrypted, keyword.uppercase(), alphabet)

        println("Сообщение: '${message.uppercase()}'")
        println("Ключ: '${keyword.uppercase()}'")
        println("Зашифровано: '$encrypted'")
        println("Расшифровано: '$decrypted'")
        println("Успех: ${message.uppercase() == decrypted}")
        println("-".repeat(30))
    }
}