package com.sirketismi.turkcellcomposemvi.product

import android.os.Parcelable
import androidx.lifecycle.viewModelScope
import com.sirketismi.turkcellcomposemvi.BaseViewModel
import com.sirketismi.turkcellcomposemvi.ViewEvent
import com.sirketismi.turkcellcomposemvi.ViewSideEffect
import com.sirketismi.turkcellcomposemvi.ViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class ProductScreenViewModel : BaseViewModel<ProductContract.Event, ProductContract.State, ProductContract.Effect>() {
    override fun setInitialState() = ProductContract.State(
        isLoading = true,
        isError = false,
        products = emptyList(),
        error = null
    )

    override fun handleEvents(event: ProductContract.Event) {
        when (event) {
            is ProductContract.Event.Retry -> {
                setEffect { ProductContract.Effect.showToastMessage }
            }

            is ProductContract.Event.BackButtonClick -> {
                setEffect { ProductContract.Effect.Navigation.Back }
            }

            is ProductContract.Event.DeleteProduct -> deleteProduct()
            is ProductContract.Event.GetProducts -> getProducts()
            is ProductContract.Event.OnProductSelected -> {
                setEffect { ProductContract.Effect.Navigation.ProductDetail }
            }
        }
    }

    fun deleteProduct() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            delay(1000)

            setState { copy(isLoading = false, isError = false) }

            setState { copy(isLoading = false, isError = true, error =  "Bir hata alındı") }
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            delay(1000)

            var products = mutableListOf<Product>()
            products.add(Product(productId = "10", productName = "Elma"))
            products.add(Product(productId = "20", productName = "Armut"))
            products.add(Product(productId = "10", productName = "Elma"))
            products.add(Product(productId = "20", productName = "Armut"))
            products.add(Product(productId = "10", productName = "Elma"))
            products.add(Product(productId = "20", productName = "Armut"))
            products.add(Product(productId = "10", productName = "Elma"))
            products.add(Product(productId = "20", productName = "Armut"))

            //setState { copy(isLoading = false, isError = false, products = products) }

            setState { copy(isLoading = false, isError = true, products = products, error = "Bir hata oluştu") }
        }
    }
}


class ProductContract {
    sealed class Event : ViewEvent {
        object Retry : Event()
        object BackButtonClick : Event()
        object GetProducts: Event()
        object OnProductSelected: Event()
        data class DeleteProduct(val productId: String) : Event()
    }

    data class State(
        val isLoading: Boolean,
        val isError : Boolean,
        val products: List<Product>,
        val error: String?
    ) :  ViewState

    sealed class Effect : ViewSideEffect {
        object ReloadData : Effect()
        object showToastMessage: Effect()

        sealed class Navigation : Effect() {
            object Back : Navigation()
            object ProductDetail : Navigation()
        }
    }

}

@Parcelize
data class Product(var productId : String, var productName : String) : Parcelable