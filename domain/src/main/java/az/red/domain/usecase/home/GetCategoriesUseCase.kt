package az.red.domain.usecase.home

import az.red.domain.repository.category.CategoryRepository

class GetCategoriesUseCase(private val repository: CategoryRepository) {
    suspend operator fun invoke() =
        repository.get()
}