package az.red.domain.usecase.home

import az.red.domain.repository.category.CategoryRepository

class GetCategoriesUseCase(private val repo: CategoryRepository) {
    suspend operator fun invoke() =
        repo.get()
}