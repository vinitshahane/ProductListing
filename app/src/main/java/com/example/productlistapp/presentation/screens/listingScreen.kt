package com.example.productlistapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.productlistapp.common.navigation.Screen
import com.example.productlistapp.presentation.screens.component.listItem
import com.example.productlistapp.presentation.viewmodel.ProductListVewModel

@Composable
fun ListingScreen (navController: NavController){

    val viewModel : ProductListVewModel = hiltViewModel()
    val result = viewModel.productList.value

    if(result.isLoading){
        Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }

    }

    result.data?.let {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(it){ item->
                    listItem(item){product->
                        navController.navigate(route = Screen.Detail.route + "?id=${product.id}")
                        //Toast.makeText(context,product.title,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    if(result.error.isNotEmpty()){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = result.error.toString())
        }
    }

}