package com.sirketismi.turkcellcomposemvi.product

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun ProductScreen(backStackEntry : NavBackStackEntry, modifier: Modifier = Modifier, onProductDetail : ()->Unit)  {
    val productViewModel : ProductScreenViewModel = viewModel()

    var isAlertMessageShown by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        productViewModel.effect.onEach {
            when (it) {
                is ProductContract.Effect.Navigation.Back -> {}
                is ProductContract.Effect.Navigation.ProductDetail -> {
                    onProductDetail()
                }
                is ProductContract.Effect.showToastMessage -> {
                    isAlertMessageShown = true
                }
                is ProductContract.Effect.ReloadData -> {}
            }
        }.collect()
    }

    LaunchedEffect(Unit) {
        productViewModel.setEvent(ProductContract.Event.GetProducts)
    }

    val state = productViewModel.viewState.collectAsState()
    when {
        state.value.isLoading -> Progress()
        state.value.isError -> NetworkError {
            productViewModel.setEvent(ProductContract.Event.Retry)
        }
        else -> {
            if(state.value.products.isNotEmpty()) {
                ProductList(state.value.products)
            }
        }
    }

    if(isAlertMessageShown) {
        showAlertMessage(onApprove = {
            isAlertMessageShown = false
        }, onCancel = {
            isAlertMessageShown = false
        })
    }

    Button(onClick = {
        productViewModel.setEvent(ProductContract.Event.OnProductSelected)
    }
    ) { Text("Ürün Detayına Git") }


}

@Composable
fun ProductList(products : List<Product>) {
    Column(modifier = Modifier.fillMaxSize()
        .background(Color.Red)
    ) {

        Spacer(Modifier.size(100.dp))
        LazyColumn {
            items(products) {
                Text(it.productName)
            }
        }
    }
}

@Composable
fun Progress() {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun NetworkError(onRetryButtonClick : ()->Unit) {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Hata Oluştu")
        Button(onClick = {
            onRetryButtonClick()
        }) {
            Text(text = "Tekrar Dene")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showAlertMessage(onApprove : ()->Unit, onCancel : ()->Unit) {

    BasicAlertDialog(
      onDismissRequest =  {

      }
    ) {
        Surface(modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large) {

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Hata Oluştu Yeni demek için tıklayın")
                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.align(Alignment.End)) {

                    TextButton(onClick =  {
                        onApprove()
                    }) {
                        Text("Tamam")
                    }

                    TextButton(onClick =  {
                        onCancel()
                    }) {
                        Text("Vazgeç")
                    }


                }

            }
        }

    }


}
