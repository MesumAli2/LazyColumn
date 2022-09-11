package com.mesum.composeapplication

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mesum.composeapplication.ui.theme.ComposeApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                 MainApp()

                    }
                }
            }
        }
    }

@Composable
fun MainApp(){
    var shouldShowOnboarding by rememberSaveable{ mutableStateOf(true) }

    if (shouldShowOnboarding){

        OnBoardingScreen(onContinueClicked ={
            shouldShowOnboarding = false
        })
    }else{
        MyApp()
    }

}

@Composable
fun OnBoardingScreen(onContinueClicked:() -> Unit) {
Surface {
    Column(modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center
    , horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Welcome to onboarding screen")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text(text = "Continue")

        }
    }
}

}


@Composable
fun MyApp(names: List<String> = List(1000) { "$it" }
) {


    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
       items(items = names){ name ->
           Greeting(name = name)
           
       }

    }
}

@Composable
private fun Greeting(name: String) {
    val expanded = remember {
        mutableStateOf(false)
    }
    //val extraPadding = if (expanded.value) 48.dp else 0.dp
    
    val extraPadding by animateDpAsState(if (expanded.value) 48.dp else 0.dp, animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
        ))
    
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {


            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))

            ) {
                Text(text = "Hello, ")
                Text(text = name, style =  MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold ))
            }

            OutlinedButton(onClick = { expanded.value = !expanded.value }) {
                Text(if (expanded.value) "Show less" else "Show More")

            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, uiMode = UI_MODE_NIGHT_YES,  name = "DefaultPreviewDark" )
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ComposeApplicationTheme {
        Greeting("Android")
    }


}