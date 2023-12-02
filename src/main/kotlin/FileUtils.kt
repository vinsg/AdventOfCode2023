/**
 * Read a file from resources
 * @return a list of lines
 */
fun readFile(path: String): List<String> {
    return object {}.javaClass.getResourceAsStream(path)?.bufferedReader()?.readLines() ?: emptyList()
}