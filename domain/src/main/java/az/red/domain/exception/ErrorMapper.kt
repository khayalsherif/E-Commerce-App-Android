package az.red.domain.exception

fun interface ErrorMapper {
    fun mapError(throwable: Throwable): Throwable
}