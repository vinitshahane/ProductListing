package com.example.productlistapp.presentation.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.productlistapp.domain.model.ProductItem
import coil.compose.rememberAsyncImagePainter


@Composable
fun listItem(category: ProductItem, onItemClick : (ProductItem) -> Unit) {
    Card (modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp).clickable {
            onItemClick(category)
        }) {
            Image(
                modifier = Modifier.size(200.dp).padding(8.dp).weight(0.4f),
                painter = rememberAsyncImagePainter(category.image),
                contentDescription = ""
            )
            userDescription(category, Modifier.weight(0.6f))
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp))
    }
}

@Composable
fun userDescription(category: ProductItem, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(category.title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        Text(category.description, fontSize = 12.sp, maxLines = 4, overflow = TextOverflow.Ellipsis)
    }
}