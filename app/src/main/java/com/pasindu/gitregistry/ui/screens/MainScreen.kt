package com.pasindu.gitregistry.ui.screens

import RepoListItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.btest.viewmodel.SearchVM
import com.pasindu.gitregistry.ui.components.ErrorState
import com.pasindu.gitregistry.ui.components.getErrorMessage
import com.pasindu.gitregistry.ui.theme.Primary
import com.pasindu.gitregistry.ui.theme.SearchBarBG
import com.pasindu.gitregistry.ui.theme.BG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: SearchVM = viewModel())

{
    val searchQuery by viewModel.searchQuery.collectAsState()
    val lazyPagingItems = viewModel.searchResults.collectAsLazyPagingItems()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(

                            painter = painterResource(id = com.pasindu.gitregistry.R.drawable.repo_logo,),
                            contentDescription = "logo",
                            modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "GitRegistry",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BG,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        containerColor = BG
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = {
                    Text(
                        text = "Search repos...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(28.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SearchBarBG,
                    unfocusedContainerColor = SearchBarBG,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Primary
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { focusManager.clearFocus() }
                )
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    searchQuery.isBlank() -> {
                        EmptyStateMessage("Search for github repos")
                    }

                    lazyPagingItems.loadState.refresh is LoadState.Error -> {
                        val error = (lazyPagingItems.loadState.refresh as LoadState.Error).error
                        ErrorState(
                            error = error,
                            onRetry = { lazyPagingItems.retry() }
                        )
                    }

                    lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Primary)
                        }
                    }

                    lazyPagingItems.itemCount == 0 && lazyPagingItems.loadState.refresh is LoadState.NotLoading -> {
                        EmptyStateMessage("No repositories found for \"$searchQuery\"")
                    }

                    else -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(
                                count = lazyPagingItems.itemCount,
                                key = { index -> lazyPagingItems[index]?.id ?: index }
                            ) { index ->
                                val repo = lazyPagingItems[index]
                                if (repo != null) {
                                    RepoListItem(repo = repo)
                                }
                            }


                            if (lazyPagingItems.loadState.append is LoadState.Loading) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(24.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            CircularProgressIndicator(
                                                color = Primary,
                                                modifier = Modifier.size(32.dp),
                                                strokeWidth = 3.dp
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = "Loading more...",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }

                            if (lazyPagingItems.loadState.append is LoadState.Error) {
                                item {
                                    val error = (lazyPagingItems.loadState.append as LoadState.Error).error
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = getErrorMessage(error),
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Button(
                                                onClick = { lazyPagingItems.retry() },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Primary
                                                )
                                            ) {
                                                Text("Retry")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun EmptyStateMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,

            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}



