package az.red.domain.exception

interface ErrorConverter {
    fun convert(throwable: Throwable): Throwable
}