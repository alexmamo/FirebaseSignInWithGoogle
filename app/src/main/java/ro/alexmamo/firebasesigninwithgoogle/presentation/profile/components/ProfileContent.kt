package ro.alexmamo.firebasesigninwithgoogle.presentation.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ProfileContent(
    padding: PaddingValues,
    photoUrl: String,
    displayName: String
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(padding),
        horizontalAlignment = CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(48.dp)
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = Crop,
            modifier = Modifier.clip(CircleShape).width(96.dp).height(96.dp)
        )
        Text(
            text = displayName,
            fontSize = 24.sp
        )
    }
}