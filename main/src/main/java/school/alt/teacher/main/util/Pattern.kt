package school.alt.teacher.main.util

object Pattern {
    val passwordRegex =
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*()_+{}|:<>?])[A-Za-z\\d!@#\$%^&*()_+{}|:<>?]{7,}\$".toRegex()
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()
}