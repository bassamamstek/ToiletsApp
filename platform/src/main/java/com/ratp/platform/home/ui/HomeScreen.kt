package com.ratp.platform.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ratp.platform.R
import com.ratp.platform.home.model.HomeViewElement
import com.ratp.platform.home.model.OnFilterSwitched
import com.ratp.platform.home.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateDetail: (id: String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        val viewElements = uiState.homeElements
        val showEmptyView = viewElements.isEmpty()
        if (showEmptyView.not()) {
            Column {
                FilterSwitch(viewModel = viewModel)
                LazyColumn {
                    items(items = viewElements) {
                        CreateCardContent(it) {
                            onNavigateDetail(it.id)
                        }
                    }
                }
            }

        }
    }
}


@Composable
private fun CreateCardContent(element: HomeViewElement, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.surface)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (image, hour, address, additionalAddress, distance) = createRefs()

            if (element.accessPRM) {
                Image(
                    modifier = Modifier
                        .constrainAs(image) {
                            end.linkTo(parent.end)
                        }
                        .height(40.dp),
                    painter = painterResource(id = R.drawable.prm),
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier.constrainAs(hour) {
                    start.linkTo(parent.start)
                },
                text = element.openingHour
            )

            Text(
                modifier = Modifier.constrainAs(additionalAddress) {
                    start.linkTo(parent.start)
                    top.linkTo(hour.bottom, margin = 8.dp)
                },
                text = element.additionalAddress
            )

            Text(
                modifier = Modifier.constrainAs(address) {
                    start.linkTo(parent.start)
                    top.linkTo(additionalAddress.bottom, margin = 2.dp)
                },
                text = element.address
            )

            Text(
                modifier = Modifier.constrainAs(distance) {
                    top.linkTo(address.bottom, margin = 16.dp)
                },
                text = element.distance
            )
        }
    }
}

@Composable
private fun FilterSwitch(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val checkedState = remember { mutableStateOf(uiState.prmFilter) }

    Row {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(16.dp),
            text = "Afficher que les accessibles aux PMR"
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                viewModel.sendAction(OnFilterSwitched)
            }
        )
    }

}
