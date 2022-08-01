package ro.alexmamo.firebasesigninwithgoogle.presentation.profile.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.PROFILE_SCREEN
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.REVOKE_ACCESS
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.SIGN_OUT

@Composable
fun ProfileTopBar(
    signOut: () -> Unit,
    revokeAccess: () -> Unit
) {
    var openMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = PROFILE_SCREEN
            )
        },
        actions = {
            IconButton(
                onClick = {
                    openMenu = !openMenu
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = null,
                )
            }
            DropdownMenu(
                expanded = openMenu,
                onDismissRequest = {
                    openMenu = !openMenu
                }
            ) {
                DropdownMenuItem(
                    onClick = {
                        signOut()
                        openMenu = !openMenu
                    }
                ) {
                    Text(
                        text = SIGN_OUT
                    )
                }
                DropdownMenuItem(
                    onClick = {
                        revokeAccess()
                        openMenu = !openMenu
                    }
                ) {
                    Text(
                        text = REVOKE_ACCESS
                    )
                }
            }
        }
    )
}