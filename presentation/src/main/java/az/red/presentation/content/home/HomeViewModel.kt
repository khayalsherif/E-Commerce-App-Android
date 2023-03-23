package az.red.presentation.content.home

import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.category.Category
import az.red.domain.usecase.home.GetCategoriesUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val getCategoriesUseCase: GetCategoriesUseCase) : BaseViewModel() {

    private val _categories = MutableStateFlow(listOf<Category>())
    val categories = _categories.asStateFlow()
    private val _subCategories = MutableStateFlow(listOf<Category>())
    val subCategories = _categories.asStateFlow()

    init {
        getCategories()
        getSubCategories()
        getProducts()
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect {
                when (it) {
                    is NetworkResult.Empty -> TODO()
                    is NetworkResult.Error -> TODO()
                    is NetworkResult.Exception -> TODO()
                    is NetworkResult.Loading -> TODO()
                    is NetworkResult.Success -> _categories.value = it.data!!
                }
            }
        }
    }

    private fun getSubCategories() {
        viewModelScope.launch {
            _categories.collect { cat ->
                if (cat.isNotEmpty()) {
                    val selectedParent =
                        _categories.value.singleOrNull { p -> p.parentId == "null" && p.isSelected }
                    if (selectedParent != null) {
                        _subCategories.value =
                            _categories.value.filter { c -> c.parentId == selectedParent.id }
                    } else {
                        _subCategories.value =
                            _categories.value.filter { c -> c.parentId != "null" }
                    }
                }
            }
        }
    }

    private fun getProducts() {

    }
}