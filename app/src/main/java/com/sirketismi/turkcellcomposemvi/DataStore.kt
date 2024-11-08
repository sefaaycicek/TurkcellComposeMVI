package com.sirketismi.turkcellcomposemvi

import com.sirketismi.turkcellcomposemvi.product.Product
import javax.inject.Singleton

@Singleton
class DataStore {
    var selectedProduct : Product? = null
}