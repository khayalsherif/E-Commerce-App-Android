package az.red.presentation.content.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import az.red.domain.common.NetworkResult
import az.red.domain.model.category.Category
import az.red.domain.model.product.Product
import az.red.domain.usecase.home.GetCategoriesUseCase
import az.red.domain.usecase.home.GetProductsFilteredPaginatedUseCase
import az.red.domain.usecase.home.GetProductsFilteredUseCase
import az.red.domain.usecase.wishList.AddToWishListUseCase
import az.red.presentation.base.BaseViewModel
import az.red.presentation.content.home.enums.ViewOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsFilteredUseCase: GetProductsFilteredUseCase,
    private val getProductsFilteredPaginatedUseCase: GetProductsFilteredPaginatedUseCase,
    private val addToWishListUseCase: AddToWishListUseCase
) : BaseViewModel() {

    val viewOption = MutableStateFlow(ViewOption.FILTER)
    private val _categories = MutableStateFlow(listOf<Category>())
    val categories = _categories.asStateFlow()
    private val _products = MutableStateFlow(listOf<Product>())
    val products = _products.asStateFlow()
    lateinit var data: Flow<PagingData<Product>>

    init {
        getCategories()
        getProducts()
        getProductsPaginated()
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect {
                when (it) {
                    is NetworkResult.Empty -> println("Empty")
                    is NetworkResult.Error -> println("Error")
                    is NetworkResult.Exception -> println("Exception ${it.message}")
                    is NetworkResult.Loading -> println("Loading")
                    is NetworkResult.Success -> _categories.value = it.data!!
                }
            }
        }
    }

    fun selectCategory(selectedCategory: String) {
        if (_categories.value.singleOrNull { it.isSelected }?.name == selectedCategory) {
            _categories.value = _categories.value.map { it.copy(isSelected = false) }.toList()
        } else {
            _categories.value = _categories.value.map {
                if (it.name == selectedCategory) it.copy(isSelected = true) else it.copy(isSelected = false)
            }.toList()
        }
        getProducts()
    }

    private fun getProducts() {
        val selectedCategory = _categories.value.singleOrNull { it.isSelected }?.name
        viewModelScope.launch {
            getProductsFilteredUseCase(selectedCategory).collect {
                when (it) {
                    is NetworkResult.Empty -> println("Empty")
                    is NetworkResult.Error -> println("Error")
                    is NetworkResult.Exception -> println("Exception ${it.message}")
                    is NetworkResult.Loading -> println("Loading")
                    is NetworkResult.Success -> _products.value = it.data!!
                }
            }
        }
    }

    private fun getProductsPaginated() {
        viewModelScope.launch {
            data =
                getProductsFilteredPaginatedUseCase()
                    .cachedIn(viewModelScope)
        }
    }

    fun addToWishList(productId: String) {
        viewModelScope.launch {
            addToWishListUseCase(productId).collect {
                when (it) {
                    is NetworkResult.Empty -> println("Empty")
                    is NetworkResult.Error -> println("Error")
                    is NetworkResult.Exception -> println("Exception ${it.message}")
                    is NetworkResult.Loading -> println("Loading")
                    is NetworkResult.Success ->  println("Success")
                }
            }
        }
    }
}