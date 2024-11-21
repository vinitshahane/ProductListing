package com.example.productlistapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import com.example.productlistapp.R
import com.example.productlistapp.domain.model.ProductDetail
import com.example.productlistapp.presentation.screens.component.DetailProperty
import com.example.productlistapp.presentation.viewmodel.ProductDetailVewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Composable
fun DetailScreen(id: String?) {
    val viewModel : ProductDetailVewModel = hiltViewModel()
    val result = viewModel.productDetail.value
    val composableScope = rememberCoroutineScope()
    if (id != null && result.data == null) {
            viewModel.getProductDetailAPi(id)
    }
    if(result.isLoading){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }

    }
    if(result.error.isNotEmpty()){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = result.error.toString())
        }
    }
    result.data?.let {
        val scrollState = rememberScrollState()

        Column(modifier = Modifier.fillMaxSize()) {
            BoxWithConstraints {
                Surface {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                    ) {
                        ProductHeader(
                            it,
                            this@BoxWithConstraints.maxHeight
                        )
                        DetailContent(it,200.dp)
                    }
                }
            }
        }
    }
}
@Composable
private fun DetailContent(product: ProductDetail, containerHeight: Dp) {
    Column {
        Title(product)
        DetailProperty(stringResource(R.string.price), product.price)
        DetailProperty(stringResource(R.string.category), product.category)
        DetailProperty(stringResource(R.string.description), product.description)

        Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
    }
}
@Composable
private fun Title(
    product: ProductDetail
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)) {
        Text(
            text = product.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ProductHeader(
    product: ProductDetail,
    containerHeight: Dp
) {
    Image(
        modifier = Modifier
            .heightIn(max = containerHeight / 2)
            .fillMaxWidth(),
        painter = rememberAsyncImagePainter(product.image),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}